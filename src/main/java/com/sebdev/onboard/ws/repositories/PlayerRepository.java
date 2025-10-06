package com.sebdev.onboard.ws.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sebdev.onboard.ws.entities.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

  List<Player> findByLastName(String lastName);

  Optional<Player> findById(Long id);
  
  Optional<Player> findByEmail(String email);
}