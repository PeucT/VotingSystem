package com.anel.vote.web.rest.user;

import com.anel.vote.AuthorizedUser;
import com.anel.vote.model.Restaurant;
import com.anel.vote.model.User;
import com.anel.vote.service.UserService;
import com.anel.vote.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by PeucT on 14.01.2018.
 */
@RestController
@RequestMapping(CommonUserController.REST_URL)
public class CommonUserController extends AbstractUserController {
    static final String REST_URL = "/rest/v1/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {

        return super.get(authorizedUser.getUserTo().getId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser) {

        super.delete(authorizedUser.getUserTo().getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        super.update(userTo, authorizedUser.getUserTo().getId());
    }
}
