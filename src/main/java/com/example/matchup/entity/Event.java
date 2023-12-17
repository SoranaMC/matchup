package com.example.matchup.entity;

import com.example.matchup.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Events")
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String eventName;
  private LocalDate eventDate;
  private LocalTime eventTime;
  private String location;
  private String eventDescription;
  private int playersNeeded;

  private String rule1;
  private String rule2;
  private String rule3;

  @ManyToMany()
  @JoinTable(
      name = "event_attenders",
      joinColumns = @JoinColumn(name = "event_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  @Exclude
  private Set<User> eventAttenders;


}


