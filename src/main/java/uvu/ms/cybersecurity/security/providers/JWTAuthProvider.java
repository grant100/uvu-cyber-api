package uvu.ms.cybersecurity.security.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uvu.ms.cybersecurity.security.token.JWTAuthenticationToken;

import java.util.ArrayList;
import java.util.Collections;


public class JWTAuthProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(JWTAuthProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{

            String jwt = ((JWTAuthenticationToken) authentication).getToken();
            DecodedJWT decodedJWT = JWT.decode(jwt);

            logger.debug("Verifying token with subject {}", decodedJWT.getSubject());
            Algorithm algorithm = Algorithm.HMAC256("this-is-a-secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("uvu-cyber-security")
                    .withSubject(decodedJWT.getSubject())
                    .build();

            verifier.verify(jwt);

            ArrayList<GrantedAuthority> authorityArrayList = new ArrayList<>();
            Collections.addAll(authorityArrayList, new SimpleGrantedAuthority("API_ROLE"));
            return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(),decodedJWT.getToken(), authorityArrayList);
        }catch(JWTVerificationException e){
            throw new BadCredentialsException("Invalid token");
        }catch(Exception e){
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
