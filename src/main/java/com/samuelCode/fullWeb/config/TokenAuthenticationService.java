package com.samuelCode.fullWeb.config;

import com.samuelCode.fullWeb.entity.UserReg;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenAuthenticationService {
   // private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

    // EXPIRATION_TIME = 10 dias 1 minute = 60 seconds = 60 × 1000 milliseconds = 60,000 ms
    static final long EXPIRATION_TIME = 432_000_000; //432_000_000 //300_000 // 860_000_000

    //static final String SECRET = "MySecret";
    //static final String TOKEN_PREFIX = "Bearer";

    public static final String TOKEN_PREFIX = "serial ";
    static final String HEADER_STRING = "Authorization";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private Key key;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generate(UserReg userRegVO, String type){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userRegVO.getId());
        claims.put("role", userRegVO.getRole());
        return doGenerateToken(claims, userRegVO.getUsername(), type);
    }


   public String doGenerateToken(Map<String, Object> claims, String username, String type){
        long expirationTimeLong;
        if("ACCESS".equals(type)){
            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 3 ;
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
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;

    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

   private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
