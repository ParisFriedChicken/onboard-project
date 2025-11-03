package com.sebdev.onboard.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sebdev.onboard.model.Game;

public interface GameRepository extends CrudRepository<Game, Long> {

    Optional<Game> findById(Long id);

}
