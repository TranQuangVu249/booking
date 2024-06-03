package nlu.fit.bookinghotel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.UserDTO;
import nlu.fit.bookinghotel.DTO.UserLoginDTO;
import nlu.fit.bookinghotel.entity.User;
import nlu.fit.bookinghotel.reponses.LoginResponse;
import nlu.fit.bookinghotel.reponses.RegisterResponse;
import nlu.fit.bookinghotel.service.IUserSevice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserSevice userService;

    @PostMapping("/register")
    //can we register an "admin" user ?
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            registerResponse.setMessage("RetypePassword that does not match");
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            User user = userService.createUser(userDTO);
            registerResponse.setMessage("REGISTER_SUCCESSFULLY");
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ) {
        // Kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.login(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId()
            );
            // Trả về token trong response
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("LOGIN_SUCCESSFULLY")
                    .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message( e.getMessage())
                            .build()
            );
        }
    }
}
