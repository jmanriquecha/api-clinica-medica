package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final String secretJwt = System.getProperty("JWT_SECRET");

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretJwt);
            return JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getLogin()) // Retorna nombre de usuario
                    .withClaim("id", usuario.getId()) // Retorna id
                    .withExpiresAt(generaFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretJwt); // Validando la firma
            verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("voll med")
                    // reusable verifier instance
                    .build()
                    .verify(token);
            verifier.getSubject();

        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
        }

        if(verifier.getSubject() == null){
            throw new RuntimeException("Vefifier inválido");
        }
        return verifier.getSubject();
    }

    private Instant generaFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
