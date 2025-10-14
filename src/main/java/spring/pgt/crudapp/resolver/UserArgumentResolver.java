package spring.pgt.crudapp.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import spring.pgt.crudapp.entity.User;
import spring.pgt.crudapp.model.TokenResponse;
import spring.pgt.crudapp.repository.UserRepository;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Nullable
    public Object resolveArgument(
            MethodParameter arg0,
            @Nullable ModelAndViewContainer arg1,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory arg3) throws Exception {

        HttpServletRequest httpServletReq = (HttpServletRequest) webRequest.getNativeRequest();

        String token = httpServletReq.getHeader("X-API-TOKEN");

        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Tidak ada token yang dikirim");
        }

        User user = userRepository.findFirstByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token tidak valid"));

        if (user.getTokenExpiredAt() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token Expired");
        }

        return user;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.equals(parameter.getParameterType());
    }

}
