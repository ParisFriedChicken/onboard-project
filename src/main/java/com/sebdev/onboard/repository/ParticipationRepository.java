package com.sebdev.onboard.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sebdev.onboard.model.Participation;
import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.model.Player;

public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    long countByGame(Game game);

    Optional<Participation> findByGameAndPlayer(Game game, Player player);

    // Return all participations for a given game
    List<Participation> findByGame(Game game);

}