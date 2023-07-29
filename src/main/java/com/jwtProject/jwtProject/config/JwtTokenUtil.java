package com.jwtProject.jwtProject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2551085165626007488l;
    public static final long JWT_TOKEN_VALIDITY = 5*60*60;
    
    @Value("${jwt.secret}")
    private String secret;


    //TODO : username retrieval from jwt token
    public String getUsernameFromToken(String token){
        return getClaimFrommToken(token, Claims::getSubject);
    }

    //TODO : expiration data retrieval from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFrommToken(token, Claims::getExpiration);
    }
    private <T> T getClaimFrommToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    //TODO : the secret key is needed for the retrieval of any information from the token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //TODO : checking whether the token has expired or not
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //TODO : generating token for user
    public  String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }


    //TODO : following should be considered while generating token:
    /*
    * 1. Define claims of the token, e.g. Issuer, Expiration, Subject, and the ID
    * 2. Sign the JWT using the HS512 algo and secret key
    * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1) compaction of the JWT to a URL-safe string
    * */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().
                setClaims(claims).
                setSubject(subject).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).
                signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //TODO : token validation
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
