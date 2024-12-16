package com.onestep.business_management.Service.AuthService;

import com.onestep.business_management.DTO.AuthDTO.*;
import com.onestep.business_management.DTO.ForgotPasswordDTO.ResetPasswordWithEmailRequest;
import com.onestep.business_management.DTO.ForgotPasswordDTO.ResetPasswordWithPhoneNumberRequest;
import com.onestep.business_management.DTO.ForgotPasswordDTO.ResetPasswordResponse;
import com.onestep.business_management.Entity.Image;
import com.onestep.business_management.Entity.Role;
import com.onestep.business_management.Entity.Staff;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.InvalidAccountExeption;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.RoleRepository;
import com.onestep.business_management.Repository.UserRepository;
import com.onestep.business_management.Scurity.JWTService;
import com.onestep.business_management.Service.OTPService.OtpService;
import com.onestep.business_management.Service.OrderService.OrderMapper;
import com.onestep.business_management.Service.StoreService.StoreMapper;
import com.onestep.business_management.Utils.EmailUtil;
import com.onestep.business_management.Utils.MapperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTService jwtService;

    public AuthenticationService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Phương thức kiểm tra mật khẩu
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Autowired
    MapperService mapperService;

    private Map<String, Integer> roleCode = new HashMap<>() {
        {
            put("ROLE_ADMIN", 1);
            put("ROLE_SELLER", 2);
            put("ROLE_BUYER", 3);
            put("ROLE_STAFF", 4);
        }
    };

    public void create_account(User user) {

        User findUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        System.out.println(findUser);
        if (findUser != null) {
            throw new RuntimeException("Username already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public BuyerRegistrationResponse buyer_register(BuyerRegistrationRequest request) {
        User exitsUser = userRepository.findByUsername(request.getUsername()).orElse(null);

        if (exitsUser != null) {
            throw new IllegalArgumentException("Account already exists");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());

        // Encoding password
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);

        // Set role buyer to user
        int roleBuyer = roleCode.get("ROLE_BUYER");
        Role newRole = roleRepository.findById(roleBuyer).orElseThrow(
                () -> new RuntimeException("Buyer role not found!"));

        System.out.println(newRole);

        // Corrected: Create a set and add the role to it
        Set<Role> roles = new HashSet<>();
        roles.add(newRole);
        newUser.setRoles(roles);

        // Setting other fields
        newUser.setFullName(request.getFullName());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setEmail(request.getEmail());

        System.out.println("new User: " + newUser.toString());
        try {
            User savedUser = userRepository.save(newUser);
            return UserMapper.INSTANCE.buyerToResponse(savedUser);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException("An error occurred while registering the buyer.");
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {

        System.out.println(loginRequest.toString());
        String userName = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        var authProvider = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));

        if (authProvider == null) {
            throw new InvalidAccountExeption("Invalid username or password");
        }

        var userDetails = userDetailsService.loadUserByUsername(userName);

        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));

        String fullName = user.getFullName();
        Set<Role> roles = user.getRoles();
        String roleName = roles.toString();

        boolean isActive = user.isDisabled();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setUsername(userName);
        userInfo.setRole(roleName);
        userInfo.setFullName(fullName);
        Image image = user.getImage();
        if (image != null) {
            userInfo.setImage(image.getFileName());
        }
        userInfo.setActive(isActive);

        String accessToken = jwtService.generateToken(userDetails);

        return new LoginResponse(accessToken, userInfo);
    }

    public LoginResponse google_sign_in(GoogleSignInRequest request) {
        User existUser = userRepository.findByUsername(request.getEmail()).orElse(null);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(request.getEmail());
            newUser.setPassword(request.getAt_hash());
            newUser.setFullName(request.getName());
            newUser.setDisabled(false);
            int roleBuyer = roleCode.get("ROLE_BUYER");
            Role newRole = roleRepository.findById(roleBuyer).orElseThrow(
                    () -> new RuntimeException("Buyer role not found!"));
            Set<Role> roles = new HashSet<>();
            roles.add(newRole);
            newUser.setRoles(roles);

            ;
            return convertToResponse(userRepository.save(newUser));
        }

        return convertToResponse(existUser);
    }

    private LoginResponse convertToResponse(User newUser) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(newUser.getUserId());
        userInfo.setUsername(newUser.getUsername());
        userInfo.setRole(newUser.getRoles().toString());
        userInfo.setFullName(newUser.getFullName());
        userInfo.setActive(newUser.isDisabled());

        String accessToken = jwtService.generateToken(newUser);

        return new LoginResponse(accessToken, userInfo);
    }

    public UserBuyerInfoRespont getUserInfo(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Map User entity to UserBuyerInfoRespont DTO
        return UserMapper.INSTANCE.userToBuyerInfoRespont(user);
    }

    public UserBuyerInfoRespont updateUserInfo(UUID userId, UserBuyerInfoRequest userBuyerInfoRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        // Map request to user entity
        User updatedUser = UserMapper.INSTANCE.buyerInfoRequestToEntity(userBuyerInfoRequest);

        // Update user fields
        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        userRepository.save(user);

        // Return updated user info
        return UserMapper.INSTANCE.userToBuyerInfoRespont(user);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserBuyerInfoRespont changePassword(UUID userId, UserchangesPassRequest userchangesPassRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String oldPasswordDB = user.getPassword();

        // Check if the old password matches the stored encoded password
        if (passwordEncoder.matches(userchangesPassRequest.getOldPassword(), oldPasswordDB)) {
            // Encode the new password and set it
            String newPassword = passwordEncoder.encode(userchangesPassRequest.getPassword());
            user.setPassword(newPassword);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
        return UserMapper.INSTANCE.userToBuyerInfoRespont(user);
    }

    public StaffResgitrationResponse staffRegister(StaffRegistrationRequest staffRegistrationRequest) {
        // TODO Auto-generated method stub
        User exitsUser = userRepository.findByUsername(staffRegistrationRequest.getUsername()).orElse(null);

        if (exitsUser != null) {
            throw new IllegalArgumentException("Account already exists");
        }

        User staffUser = new User();
        staffUser.setUsername(staffRegistrationRequest.getUsername());
        staffUser.setFullName(staffRegistrationRequest.getFullName());
        staffUser.setEmail(staffRegistrationRequest.getEmail());
        String passwordEncode = passwordEncoder.encode((staffRegistrationRequest.getPassword()));
        staffUser.setPassword(passwordEncode);
        staffUser.setPhoneNumber(staffRegistrationRequest.getPhoneNumber());

        int roleStaff = roleCode.get("ROLE_STAFF");
        Role newRole = roleRepository.findById(roleStaff).orElseThrow(
                () -> new ResourceNotFoundException("Seller role not found!"));

        Set<Role> roles = new HashSet<>();
        roles.add(newRole);
        staffUser.setRoles(roles);

        Store store = mapperService.findStoreById(staffRegistrationRequest.getStoreId());

        if (store == null) {
            throw new ResourceNotFoundException("Store not found");
        } else {
            if (store.getStaffMembers().size() >= 3) {
                throw new IllegalArgumentException("Full staff");
            }
        }
        staffUser.setStore(store);
        User newStaffUser = userRepository.save(staffUser);
        return UserMapper.INSTANCE.staffToResponse(newStaffUser);
    }

    public void deleteStaff(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Staff with ID " + userId + " not found"));

        if (user.getRoles().stream().noneMatch(role -> "ROLE_STAFF".equals(role.getRoleName()))) {
            throw new IllegalArgumentException("User is not a staff member");
        }

        userRepository.delete(user);
    }

    public List<StaffResgitrationResponse> getAllStaffByStoreId(UUID storeId) {
        return userRepository.findByStore_StoreIdAndRoles_RoleName(storeId, "ROLE_STAFF").stream()
                .map(user -> new StaffResgitrationResponse(
                        user.getUserId(),
                        user.getUsername(),
                        user.getPhoneNumber(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(role -> role.getRoleName()) // Lấy danh sách roleName
                                .collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }

    public AdminRegisterRespone adminRegister(AdminRegisterRequest adminRegisterRequest) {
        // Kiểm tra nếu tài khoản đã tồn tại
        User existingUser = userRepository.findByUsername(adminRegisterRequest.getUsername()).orElse(null);
        if (existingUser != null) {
            throw new IllegalArgumentException("Account already exists");
        }

        // Tạo người dùng mới
        User adminUser = new User();
        adminUser.setUsername(adminRegisterRequest.getUsername());
        adminUser.setFullName(adminRegisterRequest.getFullName());
        adminUser.setEmail(adminRegisterRequest.getEmail());
        adminUser.setPhoneNumber(adminRegisterRequest.getPhoneNumber());

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(adminRegisterRequest.getPassword());
        adminUser.setPassword(encodedPassword);

        // Gán vai trò
        int roleAdminId = roleCode.get("ROLE_ADMIN");
        Role adminRole = roleRepository.findById(roleAdminId).orElseThrow(
                () -> new ResourceNotFoundException("Admin role not found!"));

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        adminUser.setRoles(roles);

        // Lưu người dùng mới vào cơ sở dữ liệu
        User newAdminUser = userRepository.save(adminUser);
        return UserMapper.INSTANCE.adminToRegisterRespone(newAdminUser);
    }

    public List<AdminRegisterRespone> getAllAdminAccounts() {
        // Lấy danh sách người dùng với vai trò Admin từ cơ sở dữ liệu
        List<User> adminUsers = userRepository.findByRoles_RoleName("ROLE_ADMIN");

        // Chuyển đổi danh sách User sang danh sách AdminRegisterRespone
        return adminUsers.stream()
                .map(user -> new AdminRegisterRespone(
                        user.getUserId(),
                        user.getUsername(),
                        user.getPhoneNumber(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(roles -> roles.getRoleName()) // Lấy danh sách roleName
                                .collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }

    public void deleteAdmin(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Admin with ID " + userId + " not found"));

        if (user.getRoles().stream().noneMatch(role -> "ROLE_ADMIN".equals(role.getRoleName()))) {
            throw new IllegalArgumentException("User is not a staff member");
        }

        userRepository.delete(user);
    }
}
