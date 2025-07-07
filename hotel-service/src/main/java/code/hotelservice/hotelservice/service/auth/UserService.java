package code.hotelservice.hotelservice.service.auth;

import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.dto.auth.UserDto;
import code.hotelservice.hotelservice.model.auth.User;

public interface UserService {
    Response getAllUser();
    UserDto getUserByEmail(String email);
    void deleteUser(Long id);
    User checkUserExistByToken(String token) throws RuntimeException;

    Response getUserBookingHistory(Long id);
    Response getUserById(Long userId);

    Response getMyInfo(String email);
}
