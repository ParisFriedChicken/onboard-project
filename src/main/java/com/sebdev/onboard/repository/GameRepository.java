package com.sebdev.onboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sebdev.onboard.model.Game;

public interface GameRepository extends CrudRepository<Game, Long> {

    Optional<Game> findById(Long id);

    // Return the number of registered players for a game from the vw_full_game_features view.
    // Uses a native query against the DB view. Returns null if the view has no row for the given game id.
    // Cast numeric to integer and coalesce to 0 to ensure the result maps to Integer reliably at runtime.
    @Query(value = "SELECT COALESCE(registered_players::integer, 0) FROM vw_full_game_features WHERE game_id = :gameId", nativeQuery = true)
    Integer findRegisteredPlayersByGameId(@Param("gameId") Long gameId);

    // Host features are stored in the vw_player_features view keyed by player_id (the host id).
    // Use the player/host id to fetch host_total_games and host_no_show_rate from vw_player_features.
    @Query(value = "SELECT COALESCE(host_total_games::integer, 0) FROM vw_player_features WHERE player_id = :playerId", nativeQuery = true)
    Integer findHostTotalGamesByPlayerId(@Param("playerId") Long playerId);

    @Query(value = "SELECT COALESCE(host_no_show_rate::numeric, 0) FROM vw_player_features WHERE player_id = :playerId", nativeQuery = true)
    Double findHostNoShowRateByPlayerId(@Param("playerId") Long playerId);

}