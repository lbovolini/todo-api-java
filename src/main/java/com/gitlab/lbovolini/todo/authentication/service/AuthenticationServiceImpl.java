package com.gitlab.lbovolini.todo.authentication.service;

import com.gitlab.lbovolini.todo.authentication.AuthenticatedUser;
import com.gitlab.lbovolini.todo.authentication.UserCredentials;
import com.gitlab.lbovolini.todo.authentication.repository.AuthenticationRepository;
import com.gitlab.lbovolini.todo.common.DefaultUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final int VALID_DAYS = 30;
    public static final String BEARER_PREFIX = "Bearer";
    // !todo mover para variavel de ambiente
    public static String HASH_SHA512 = "D31F9BB81B68134704060B4EE6FC772CF98F69D699A8456B296BD2D69AAF276E4AF927D0E5C62A8A4C85EC463B30ECB18D96D994A1B72D07A5D8503A9206080B";

    private final AuthenticationRepository authenticationRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public AuthenticatedUser login(UserCredentials userCredentials) {
        DefaultUser defaultUser = authenticate(userCredentials);
        String token = generateToken(userCredentials.getUsername());

        return new AuthenticatedUser(defaultUser.getId(), defaultUser.getUsername(), token, ZonedDateTime.now().plusDays(VALID_DAYS));
    }

    public void logout(User user) {

    }

    // !todo retornar Map<String, Object>
    public Claims decode(String token) {
        String tokenString = extract(token);

        return Jwts.parserBuilder()
                .setSigningKey(HASH_SHA512)
                .build()
                .parseClaimsJws(tokenString)
                .getBody();
    }

    private DefaultUser authenticate(UserCredentials userCredentials) {
        Optional<DefaultUser> optionalUser = authenticationRepository.findByUsername(userCredentials.getUsername());

        return optionalUser.stream()
                .filter(user -> BCrypt.checkpw(userCredentials.getPassword(), user.getPassword()))
                .findFirst()
                // !todo criar excecao InvalidCredentialsException e adicionar ao tratador de excecao global
                .orElseThrow(() -> new BadCredentialsException("Invalid username and/or password"));
    }

    private String generateToken(String subject) {
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

    private String extract(String token) {
        String tokenString = token;

        if (tokenString.startsWith(BEARER_PREFIX)) {
            tokenString = tokenString.replace(BEARER_PREFIX, "");
        }

        return tokenString;
    }

}
