package models;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtToken {
    public String token;
    public int userid;
    public String username;
    private String secret_key = "secret";

    public JwtToken(Account acc, String username) {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        try {
            this.token = Jwts.builder()
                    .setSubject("user/"+acc.getID())
                    .setIssuedAt(now)
                    .setIssuer("JEA_API_SERVER")
                    .signWith(SignatureAlgorithm.HS512, "secret".getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.userid = acc.getID();
        this.username = username;
    }

    public JwtToken(String token) {
        this.token = token;
    }

    public boolean complete(){
        if(token != null && token != "" && userid != 0){
            return true;
        }
        return false;
    }

    public boolean VefifyToken(){
        System.out.println("starting to verify token: " + token);
        try {
            Jwts.parser()
                    .setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(token).getBody();
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        System.out.println("token verified");
        return true;
    }
}
