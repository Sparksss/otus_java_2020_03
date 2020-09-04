package ru.otus.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.domain.User;
import ru.otus.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findAll() {
        return userRepository.getAll();
    }

    @Override
    public User findById(long id) {
        if(id < 0) return null;
        return userRepository.getById(id);
    }

    @Override
    public User save(User user) {
        if(user != null) {
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public void editUser(User user) {
        if(user != null) userRepository.update(user);
    }
}
