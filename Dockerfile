# Sử dụng image cơ bản OpenJDK 17
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc bên trong container
WORKDIR /app

# Định nghĩa biến ARG cho file JAR
ARG JAR_FILE=target/*.jar

# Sao chép file JAR vào container
COPY ${JAR_FILE} app.jar

# Mở cổng 8080 để chạy ứng dụng
EXPOSE 8080

# Chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
