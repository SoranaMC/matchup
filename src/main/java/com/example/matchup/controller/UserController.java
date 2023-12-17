package com.example.matchup.controller;

import com.example.matchup.entity.User;
import com.example.matchup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;


  @GetMapping("/{id}")
  public String getUserById(@PathVariable Long id, Model model) {
    User currentUser = userRepository.getReferenceById(id);
    model.addAttribute("user", currentUser);
    return "user/user-details";
  }

  @GetMapping("/list")
  public String listUsers(Model model) {
    List<User> users = userRepository.findAll();
    model.addAttribute("users", users);
    return "user/user-list";
  }

}
