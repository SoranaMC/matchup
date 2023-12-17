package com.example.matchup.controller;

import com.example.matchup.entity.Event;
import com.example.matchup.repository.EventRepository;
import com.example.matchup.entity.User;
import com.example.matchup.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/event")
public class EventController {

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/{id}")
  public String getEvent(@PathVariable Long id, Model model) {
    Event currentEvent = eventRepository.getReferenceById(id);

    System.out.println(currentEvent.getEventAttenders().toString());

    model.addAttribute("event", currentEvent);
    return "event/event-details";
  }

  @GetMapping("/list")
  public String listEvents(Model model) {
    List<Event> events = eventRepository.findAll();
    model.addAttribute("events", events);
    return "event/event-list";
  }

  @GetMapping("/create")
  public String showCreateForm(Model model) {
    Event event = new Event();
    model.addAttribute("event", event);

    return "event/create-event";
  }

  @PostMapping("/create")
  public String createEvent(@ModelAttribute Event event) {
    Event createdEvent = eventRepository.save(event);
    return "redirect:/event/" + createdEvent.getId();
  }

  @PostMapping("/join-event/{eventId}")
  public String joinToEvent(@PathVariable("eventId") Long eventId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String currentUserName = "";

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      currentUserName = authentication.getName();
      System.out.println(currentUserName);
    }

    if (currentUserName == "") {
      return "redirect:/login";
    }

    Event currentEvent = eventRepository.getReferenceById(eventId);
    User currentUser = userRepository.findByEmail(currentUserName);

    if (currentEvent.getEventAttenders().stream()
        .anyMatch(user -> user.getUserId() == currentUser.getUserId())) {
      return "redirect:/event/" + eventId;
    }

    Set<User> users = new HashSet<>(currentEvent.getEventAttenders());

    users.add(currentUser);

    currentEvent.setEventAttenders(users);
    eventRepository.save(currentEvent);

    return "redirect:/event/" + eventId;
  }
}

