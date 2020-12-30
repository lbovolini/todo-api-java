package com.gitlab.lbovolini.todo.service;

import com.gitlab.lbovolini.todo.model.User;
import com.gitlab.lbovolini.todo.repository.UserRepository;
import com.gitlab.lbovolini.todo.security.UserCredentials;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationService {

    public static final int VALID_DAYS = 30;
    public static final String BEARER_PREFIX = "Bearer";
    // !todo mover para variavel de ambiente
    public static String HASH_SHA512 = "D31F9BB81B68134704060B4EE6FC772CF98F69D699A8456B296BD2D69AAF276E4AF927D0E5C62A8A4C85EC463B30ECB18D96D994A1B72D07A5D8503A9206080B";

    // !todo UserRepository ou UserService ?
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(UserCredentials userCredentials) {
        authenticate(userCredentials);

        return generateToken(userCredentials.getUsername());
    }

    public void authenticate(UserCredentials userCredentials) {
        Optional<User> user = userRepository.findByUsername(userCredentials.getUsername());

        user.stream()
                .map(u -> BCrypt.checkpw(userCredentials.getPassword(), u.getPassword()))
                .filter(pass -> pass.equals(true))
                .findFirst()
                // !todo criar excecao InvalidCredentialsException e adicionar ao tratador de excecao global
                .orElseThrow(() -> new RuntimeException("Invalid username and/or password."));
    }

    public String generateToken(String subject) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(HASH_SHA512);

        SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.DAY_OF_MONTH, VALID_DAYS);

        return BEARER_PREFIX + " " + Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date()) // gerado em
                .setExpiration(expiration.getTime()) // expira em
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public void logout(final User user) {

    }

    public Claims decode(String token) {
        String tokenString = extract(token);
        return Jwts.parserBuilder()
                .setSigningKey(HASH_SHA512)
                .build()
                .parseClaimsJws(tokenString)
                .getBody();
    }

    private static String extract(String token) {
        String tokenString = token;

        if (tokenString.startsWith(BEARER_PREFIX)) {
            tokenString = tokenString.replace(BEARER_PREFIX, "");
        }

        return tokenString;
    }

}
