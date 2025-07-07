package code.hotelservice.hotelservice.dao.auth;

import code.hotelservice.hotelservice.model.auth.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepo extends JpaRepository<Roles,Long> {

    Roles getByRole(String role);
}
