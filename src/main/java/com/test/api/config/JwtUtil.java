package com.test.api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    private final static  String SECRET_KEY = "asdf";

    public static String generateToken(String username, String role){
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuer("api-test")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 360_000))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static DecodedJWT verifyToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withIssuer("api-test")
                .build();

        return verifier.verify(token);
    }

}
