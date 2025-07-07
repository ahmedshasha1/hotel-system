package code.hotelservice.hotelservice.service.auth;

import code.hotelservice.hotelservice.config.auth.JwtHandler;
import code.hotelservice.hotelservice.dao.auth.UserRepo;
import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.dto.auth.UserDto;
import code.hotelservice.hotelservice.model.auth.User;
import code.hotelservice.hotelservice.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtHandler jwtHandler;
    @Override
    public Response getAllUser() {
        Response response = new Response();

        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = UserMapper.userMapper.toDtoList(users);
        if (userDtos.isEmpty()){
            throw new RuntimeException("List.isempty");
        }
        response.setUserList(userDtos);
        return response;

    }

    @Override
    public UserDto getUserByEmail(String email) {
        if(Objects.isNull(email)){
            throw new RuntimeException("email.notnull");
        }
        User user = userRepo.getUsersByEmail(email);
        if (user == null){
            throw new RuntimeException("email.notfound");
        }

        return UserMapper.userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        if(Objects.isNull(id)){
            throw new RuntimeException("invalid.id");
        }
        User user = userRepo.findUserById(id);
        if (user == null){
            throw new RuntimeException("user.notfound");
        }
        userRepo.delete(user);
    }

    @Override
    public User checkUserExistByToken(String token) throws RuntimeException {
        String email=jwtHandler.getSubject(token);
        if(Objects.isNull(email)){
            throw new RuntimeException("email.null");
        }

        return userRepo.getUsersByEmail(email);
    }

    @Override
    public Response getUserBookingHistory(Long id) {

        if(Objects.isNull(id)){
            throw new RuntimeException("invalid.id");
        }

        Response response = new Response();
        UserDto userDto = UserMapper.userMapper.toDto(userRepo.findUserById(id));
        if(userDto == null){
            throw new RuntimeException("user.notfound");
        }
        response.setUser(userDto);

        return response;
    }

    @Override
    public Response getUserById(Long userId) {
        if(Objects.isNull(userId)){
            throw new RuntimeException("invalid.id");
        }

        Response response = new Response();
        UserDto userDto = UserMapper.userMapper.toDto(userRepo.findUserById(userId));
        if(userDto == null){
            throw new RuntimeException("user.notfound");
        }
        response.setUser(userDto);

        return response;
    }
    @Override
    public Response getMyInfo(String email) {
        if(Objects.isNull(email)){
            throw new RuntimeException("invalid.id");
        }

        Response response = new Response();
        UserDto userDto = UserMapper.userMapper.toDto(userRepo.getUsersByEmail(email));
        if(userDto == null){
            throw new RuntimeException("user.notfound");
        }
        response.setUser(userDto);

        return response;
    }
}
