package com.example.matchup.controller;

import com.example.matchup.entity.User;
import com.example.matchup.dto.UserDto;
import com.example.matchup.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

  private UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/index")
  public String home() {
    return "/index";
  }
  
  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    UserDto user = new UserDto();
    model.addAttribute("user", user);

    return "auth/register";
  }

  @GetMapping("/login")
  public String login() {
    return "auth/login";
  }

  @PostMapping("/register/save")
  public String registration(@ModelAttribute("user") UserDto userDto,
      BindingResult result,
      Model model) {
    User existingUser = userService.findUserByEmail(userDto.getEmail());

    if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail()
        .isEmpty()) {
      result.rejectValue("email", null,
          "There is already an account registered with the same email");
    }

    if (result.hasErrors()) {
      model.addAttribute("user", userDto);
      return "/register";
    }

    userService.saveUser(userDto);
    return "redirect:/register?success";
  }
}
