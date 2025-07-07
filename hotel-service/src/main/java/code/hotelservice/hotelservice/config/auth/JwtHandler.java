package code.hotelservice.hotelservice.config.auth;

import code.hotelservice.hotelservice.model.auth.Roles;
import code.hotelservice.hotelservice.model.auth.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtHandler {
    private JwtBuilder jwtBuilder;
    private JwtParser jwtParser;
    private String token;
    private Duration duration;

    public JwtHandler(TokenConfig tokenConfig){
        this.token = tokenConfig.getSecret();
        this.duration = tokenConfig.getTime();
        Key key= Keys.hmacShaKeyFor(token.getBytes(StandardCharsets.UTF_8));
        jwtBuilder = Jwts.builder().signWith(key);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(User user){
        for (Roles role: user.getRoles()) {
            role.setUsers(null);
        }
        Date issueTime= new Date();
        Date timeOfExpire = Date.from(issueTime.toInstant().plus(duration));

        return jwtBuilder.setIssuedAt(issueTime)
                .setExpiration(timeOfExpire)
                .setSubject(user.getEmail())
                .claim("role", user.getRoles()).compact() ;
    }

    public boolean validateToken(String token){
        if(jwtParser.isSigned(token)){
            try{
                Claims claims = jwtParser.parseClaimsJws(token).getBody();
                Date issue = claims.getIssuedAt();
                Date expire = claims.getExpiration();

                return expire.after(new Date()) && issue.before(expire);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    public String getSubject(String token){
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }
}


