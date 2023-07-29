package com.jwtProject.jwtProject.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("jwtProjectSecret".equals(username)){
            //$2a$10$QlJK2Vu7IsNZwJ37wy4mPOGFnVCzbcQdfQ755IiwAO6OS7J7N.urK
//            return new User("{$jwt.secret}", "{$jwt.secret}",  new ArrayList<>());
            return new User("jwtProjectSecret", "$2a$10$QlJK2Vu7IsNZwJ37wy4mPOGFnVCzbcQdfQ755IiwAO6OS7J7N.urK", new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User not found with the username: " + username);
        }
    }
}
