package com.example.matchup.service;

import com.example.matchup.entity.User;
import com.example.matchup.repository.UserRepository;
import java.util.HashSet;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);

    if (user != null) {
      return new org.springframework.security.core.userdetails.User(user.getEmail(),
          user.getPassword(),
          new HashSet<>());
    } else {
      throw new UsernameNotFoundException("Invalid username or password.");
    }
  }

}
