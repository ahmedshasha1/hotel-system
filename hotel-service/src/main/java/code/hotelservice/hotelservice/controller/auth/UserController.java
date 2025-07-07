package code.hotelservice.hotelservice.controller.auth;

import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Response> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUser());
    }
    @DeleteMapping("/delete-user/user-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> deleteUser(Long id){
        userService.deleteUser(id);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/get-logged-in-profile-info")
    public ResponseEntity<Response> getLoggedInUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return ResponseEntity.ok(userService.getMyInfo(email));
    }

    @GetMapping("/get-user-bookings/{userId}")
    public ResponseEntity<Response> getUserBookingHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserBookingHistory(userId));
    }
}
