package com.example.e_commerce.Configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token, String claim) {
        // Remplacez "yourSecretKey" par votre clé de signature utilisée pour signer le JWT
        String secretKey = "yourSecretKey";

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes()) // Utilisez la clé de signature
                .parseClaimsJws(token)
                .getBody();

        // Retourne la valeur de la revendication "sub" ou toute autre revendication
        return claims.get(claim, String.class);
    }
}
