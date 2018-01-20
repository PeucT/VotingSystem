package com.anel.vote.service;

import com.anel.vote.model.User;
import com.anel.vote.repository.UserRepo;
import com.anel.vote.AuthorizedUser;
import com.anel.vote.to.UserTo;
import com.anel.vote.util.UserUtil;
import com.anel.vote.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.anel.vote.util.UserUtil.prepareToSave;
import static com.anel.vote.util.UserUtil.updateFromTo;
import static com.anel.vote.util.ValidationUtil.checkNotFound;
import static com.anel.vote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by PeucT on 14.01.2018.
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepo repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public User getByNickname(String nickname) throws NotFoundException {
        Assert.notNull(nickname, "nickname must not be null");
        return checkNotFound(repository.getByNickname(nickname), "nickname=" + nickname);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(prepareToSave(user, passwordEncoder)), user.getId());
    }

    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = updateFromTo(get(userTo.getId()), userTo);
        repository.save(prepareToSave(user, passwordEncoder));
    }


    @Override
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = repository.getByNickname(nickname);
        if (user == null) {
            throw new UsernameNotFoundException("User " + nickname + " is not found");
        }

        return new AuthorizedUser(user);
    }
}
