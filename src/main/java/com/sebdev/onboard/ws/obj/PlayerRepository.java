package com.sebdev.onboard.ws.obj;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

  List<Player> findByLastName(String lastName);

  Optional<Player> findById(Long id);
}