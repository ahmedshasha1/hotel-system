package code.hotelservice.hotelservice.service.auth;

import code.hotelservice.hotelservice.dto.auth.TokenDto;
import code.hotelservice.hotelservice.dto.auth.UserDto;
import code.hotelservice.hotelservice.dto.auth.UserLoginDto;
import code.hotelservice.hotelservice.model.auth.User;

public interface AuthService {
    TokenDto login(UserLoginDto userLoginDto);
    void createAccount(UserDto user);
}
