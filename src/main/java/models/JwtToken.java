package models;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtToken {
    public String token;
    public int userid;
    public String username;
    private final static String secret_key = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";

    public JwtToken(Account acc, String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        this.token = Jwts.builder()
                .setSubject("user/" + acc.getID())
                .setIssuedAt(now)
                .setIssuer("JEA_API_SERVER")
                .signWith(SignatureAlgorithm.HS512, secret_key.getBytes(StandardCharsets.UTF_8))
                .compact();
        this.userid = acc.getID();
        this.username = username;
    }

    public JwtToken(String token) {
        this.token = token;
    }

    public boolean VerifyToken() {
        System.out.println("starting to verify token: " + token);
        try {
            Jwts.parser()
                    .setSigningKey(secret_key.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        System.out.println("token verified");
        return true;
    }
}
