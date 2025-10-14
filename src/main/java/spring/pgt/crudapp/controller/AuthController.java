package spring.pgt.crudapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.subst.Token;
import spring.pgt.crudapp.entity.User;
import spring.pgt.crudapp.model.LoginUserRequest;
import spring.pgt.crudapp.model.TokenResponse;
import spring.pgt.crudapp.model.WebResponse;
import spring.pgt.crudapp.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/login")
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse token = authService.login(request);
        return WebResponse.<TokenResponse>builder()
                .data(token).build();
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/logout")
    public WebResponse<String> logout(User user) {
        authService.logout(user);
        return WebResponse.<String>builder()
                .data("OK").build();
    }
}
