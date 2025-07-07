package code.hotelservice.hotelservice.dto.auth;

import code.hotelservice.hotelservice.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {
    private Long id;
    private String role;
    private UserDto userDto;
}
