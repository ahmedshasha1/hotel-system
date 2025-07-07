package code.hotelservice.hotelservice.controller.auth;

import code.hotelservice.hotelservice.dto.auth.TokenDto;
import code.hotelservice.hotelservice.dto.auth.UserDto;
import code.hotelservice.hotelservice.dto.auth.UserLoginDto;
import code.hotelservice.hotelservice.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    ResponseEntity<TokenDto> login(@RequestBody @Validated UserLoginDto userLoginDto){
        return ResponseEntity.ok(authService.login(userLoginDto));
    }

    @PostMapping("/sign-up")
    ResponseEntity<Void> signUp(@RequestBody @Validated UserDto userDto) {
        authService.createAccount(userDto);
        return ResponseEntity.created(URI.create("/auth/create-user")).build();
    }
}
