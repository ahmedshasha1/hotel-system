package code.hotelservice.hotelservice.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class TokenDto {
    private String token;

    private List<String> role;
}
