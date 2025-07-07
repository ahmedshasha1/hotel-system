package code.hotelservice.hotelservice.dao.auth;

import code.hotelservice.hotelservice.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User getUsersByEmail(String email);

    User findUserById(Long id);
}
