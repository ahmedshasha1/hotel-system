package code.hotelservice.hotelservice.dto.auth;

import code.hotelservice.hotelservice.dto.BaseDto;
import code.hotelservice.hotelservice.dto.BookingDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends BaseDto {


    @NotBlank(message = "name.notnull")
    private String name;
    @NotBlank(message = "email.notnull")
    private String email;
    @NotBlank(message = "password.notnull")
    private String password;
    @NotBlank(message = "phone.notnull")
    private String phoneNumber;
    private List<RoleDto> roles;
    private List<BookingDto> booking;

}