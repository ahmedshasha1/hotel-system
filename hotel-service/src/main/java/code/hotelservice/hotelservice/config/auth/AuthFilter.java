package code.hotelservice.hotelservice.config.auth;

import code.hotelservice.hotelservice.model.auth.Roles;
import code.hotelservice.hotelservice.model.auth.User;
import code.hotelservice.hotelservice.service.auth.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHandler jwtHandler;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        if (path.contains("login")|| path.contains("sign-up") ||
                path.contains("/v3/api-docs") || path.contains("/swagger-ui") ){
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null || !token.contains("Bearer ")) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        token = token.substring(7);
        if (!jwtHandler.validateToken(token)) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            User user = userService.checkUserExistByToken(token);
            if (user == null) {
                response.reset();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Roles roles : user.getRoles()) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roles.getRole());
                grantedAuthorities.add(simpleGrantedAuthority);
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);

        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        }
    }
}
