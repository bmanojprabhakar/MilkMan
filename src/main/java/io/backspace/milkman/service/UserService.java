package io.backspace.milkman.service;

import io.backspace.milkman.model.Users;
import io.backspace.milkman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public Users registerUser(Users users) {
        List<Users> registeredUser = userRepository.findByEmail(users.getEmail());
        if(!registeredUser.isEmpty())
            throw new IllegalArgumentException("User is registered already");
        return userRepository.save(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Users> registeredUser = userRepository.findByEmail(username);
        if(registeredUser.isEmpty())
            throw new UsernameNotFoundException("No user registered with this mobile number");
        return new User(registeredUser.get(0).getMobile(),
                registeredUser.get(0).getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(registeredUser.get(0).getRole())));
    }
}
