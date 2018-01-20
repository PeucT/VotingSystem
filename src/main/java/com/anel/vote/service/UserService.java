package com.anel.vote.service;

import com.anel.vote.model.User;
import com.anel.vote.to.UserTo;
import com.anel.vote.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by PeucT on 11.01.2018.
 */
public interface UserService {
    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByNickname(String nickname) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll();

    void enable(int id, boolean enable);
}
