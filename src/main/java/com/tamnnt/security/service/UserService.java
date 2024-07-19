package com.tamnnt.security.service;

import com.tamnnt.security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User createUser(User user);
    List<User> getUsers();
    boolean checkUserExist(String email);

    User getUserById(Long userId);

    User updateUser(Long userId, User user);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException ;
}
