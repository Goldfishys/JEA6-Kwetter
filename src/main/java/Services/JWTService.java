package Services;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTService {

    public String createToken(String username, int id){
        String jwt = Jwts.builder()
                .setSubject(username + ";"+id)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
        verifyToken(jwt);
        return jwt;
    }

    public boolean verifyToken(String token){
        System.out.println("starting to verify token: " + token);
        Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token);
        return true;
    }
}
