package spring.pgt.crudapp.controller;

import spring.pgt.crudapp.entity.User;
import spring.pgt.crudapp.model.RegisterUserRequest;
import spring.pgt.crudapp.model.UserResponse;
import spring.pgt.crudapp.model.WebResponse;
import spring.pgt.crudapp.service.UserService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);

        return WebResponse.<String>builder()
                .data("User Berhasil Ditambahkan")
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> get(User user) {
        UserResponse userResponse = userService.get(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

}
