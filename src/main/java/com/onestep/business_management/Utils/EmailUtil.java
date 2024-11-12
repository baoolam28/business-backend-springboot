package com.onestep.business_management.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.UUID;


@Component
public class EmailUtil {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSetPasswordEmail(String email) throws MessagingException{

        String randomToken = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        String concatenatedEmailToken = randomToken + ":" + email;

        // Mã hóa email bằng Base64
        String encodedEmail = Base64.getUrlEncoder().encodeToString(concatenatedEmailToken.getBytes());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =  new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Reset Your Password");

        String htmlContext =getHtmlContent(encodedEmail);
               

        mimeMessageHelper.setText(htmlContext, true);
        javaMailSender.send(mimeMessage);
        
    }

    private String getHtmlContent(String encodedEmail){
        return """
            <html>
                <head>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f3f3f3;
                            margin: 0;
                            padding: 20px;
                        }
                        .container {
                            max-width: 600px;
                            margin: auto;
                            background: white;
                            padding: 30px;
                            border-radius: 10px;
                            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                            text-align: center;
                        }
                        .content {
                            text-align: left;
                            font-size: 16px;
                            line-height: 1.6;
                            color: #444;
                        }
                        .content p {
                            margin: 16px 0;
                        }
                        .button {
                            display: inline-block;
                            margin-top: 25px;
                            padding: 14px 30px;
        
                            color: #ffffff;
                            text-decoration: none;
                            border-radius: 8px;
                            font-weight: bold;
                            font-size: 16px;
                            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
                            transition: all 0.3s ease;
                        }
                        .button:hover {
                           
                            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
                            transform: translateY(-2px);
                        }
                        .button:active {
                            background: #003366;
                            box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.2);
                            transform: translateY(1px);
                        }
                        .footer {
                            font-size: 13px;
                            color: #888;
                            margin-top: 30px;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="content">
                            <p>Chào bạn,</p>
                            <p>Bạn đã yêu cầu thiết lập lại mật khẩu của mình. Để tiếp tục, vui lòng nhấp vào nút bên dưới:</p>
                            <a href="http://localhost:3000/reset-password?email=%s" class="button" target="_blank">Thiết lập lại mật khẩu</a>
                            <p>Nếu bạn không yêu cầu điều này, bạn có thể bỏ qua email này một cách an toàn.</p>
                        </div>
                        <div class="footer">
                            <p>Trân trọng,<br>Đội ngũ Hỗ trợ của Chúng tôi</p>
                        </div>
                    </div>
                </body>
            </html>
            """.formatted(encodedEmail);
    }
}
