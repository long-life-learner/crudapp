package spring.pgt.crudapp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Websocket;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import spring.pgt.crudapp.entity.User;
import spring.pgt.crudapp.model.LoginUserRequest;
import spring.pgt.crudapp.model.TokenResponse;
import spring.pgt.crudapp.model.WebResponse;
import spring.pgt.crudapp.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository
                .findByNomorInduk(request.getNomorInduk())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nomor Induk atau Password salah"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(get3DayMillis());
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nomor Induk atau Password salah");
        }

    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    private Long get3DayMillis() {
        return System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 7);
    }
}
