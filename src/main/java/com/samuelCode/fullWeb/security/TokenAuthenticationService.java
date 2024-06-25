package com.samuelCode.fullWeb.security;

import com.samuelCode.fullWeb.entity.UserReg;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenAuthenticationService {
   // private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

    // EXPIRATION_TIME = 10 dias 1 minute = 60 seconds = 60 × 1000 milliseconds = 60,000 ms
    static final long EXPIRATION_TIME = 432_000_000; //432_000_000 //300_000 // 860_000_000

    static final String SECRET = "MySecret";
    //static final String TOKEN_PREFIX = "Bearer";

    public static final String TOKEN_PREFIX = "serial ";
    static final String HEADER_STRING = "Authorization";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private Key key;

    public String generate(UserReg userRegVO, String type){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userRegVO.getId());
        claims.put("role", userRegVO.getRole());
        return doGenerateToken(claims, userRegVO.getUsername(), type);
    }

    public String doGenerateToken(Map<String, Object> claims, String username, String type){
        long expirationTimeLong;
        if("ACCESS".equals(type)){
            expirationTimeLong = Long.parseLong(expirationTime) * 1000;
        }else{
            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
        }
        final Date createdDate =new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);
        String token = TOKEN_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
        return token;

    }
}
