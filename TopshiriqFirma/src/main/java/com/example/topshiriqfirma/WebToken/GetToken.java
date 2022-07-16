package com.example.topshiriqfirma.WebToken;

import com.example.topshiriqfirma.model.Roles;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GetToken {
    long time=360_000_000;
    Date expirationDate=new Date(System.currentTimeMillis()+time);
    String parol="tamurbek";
    public String getToken(String username, Roles rols){
        String token= Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .claim("ROLS",rols)
                .signWith(SignatureAlgorithm.HS512,parol)
                .compact();
        return token;
    }
    public boolean tokenCheck(String token){
        Jwts
                .parser()
                .setSigningKey(parol)
                .parseClaimsJws(token);
        return true;
    }
    public String userCheck(String username){
        String s = Jwts
                .parser()
                .setSigningKey(parol)
                .parseClaimsJws(username)
                .getBody()
                .getSubject();
        return s;

    }
}
