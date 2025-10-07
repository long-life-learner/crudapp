package spring.pgt.crudapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import spring.pgt.crudapp.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import spring.pgt.crudapp.entity.User;
import spring.pgt.crudapp.model.RegisterUserRequest;
import spring.pgt.crudapp.model.UserResponse;
import spring.pgt.crudapp.repository.UserRepository;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void register(RegisterUserRequest request) {

        validationService.validate(request);

        if (userRepository.existsByNomorInduk(request.getNomorInduk())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User sudah terdaftar");
        }

        User user = new User();

        user.setNomorInduk(request.getNomorInduk());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole(request.getRole());

        userRepository.save(user);

    }

    public UserResponse get(User user) {
        return UserResponse.builder()
                .nomorInduk(user.getNomorInduk())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
