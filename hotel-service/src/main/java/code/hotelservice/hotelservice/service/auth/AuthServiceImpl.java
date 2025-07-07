package code.hotelservice.hotelservice.service.auth;

import code.hotelservice.hotelservice.config.auth.JwtHandler;
import code.hotelservice.hotelservice.dao.auth.RoleRepo;
import code.hotelservice.hotelservice.dao.auth.UserRepo;
import code.hotelservice.hotelservice.dto.auth.TokenDto;
import code.hotelservice.hotelservice.dto.auth.UserDto;
import code.hotelservice.hotelservice.dto.auth.UserLoginDto;
import code.hotelservice.hotelservice.model.auth.Roles;
import code.hotelservice.hotelservice.model.auth.User;
import code.hotelservice.hotelservice.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHandler jwtHandler;

    @Override
    public TokenDto login(UserLoginDto userLoginDto) {
        if(Objects.isNull(userLoginDto)){
            throw new RuntimeException("invalid.data");
        }
        User user = userRepo.getUsersByEmail(userLoginDto.getEmail());
        if (user == null){
            throw new RuntimeException("email.notfound");
        }
        if(!passwordEncoder.matches(userLoginDto.getPassword(),user.getPassword())){
            throw new BadCredentialsException("error.pass");
        }
        List<String> roles = user.getRoles().stream().map(role -> role.getRole().substring(5)).collect(Collectors.toList());

        return new TokenDto(jwtHandler.createToken(user),roles);
    }

    @Override
    public void createAccount(UserDto userDto) {
        if(Objects.isNull(userDto)){
            throw new RuntimeException("invalid.data");
        }
        if(Objects.nonNull(userDto.getId())){
            throw new RuntimeException("invalid.data");
        }

        User user =  UserMapper.userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Roles role = roleRepo.getByRole("ROLE_USER");
        if (role == null){
            throw new RuntimeException("role.notfound");
        }
        List<Roles> roles = List.of(role);
        user.setRoles(roles);

        userRepo.save(user);
    }
}
