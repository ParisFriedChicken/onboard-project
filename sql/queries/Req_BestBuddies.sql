---Best Buddies (by how many games they played together)
WITH players_duos AS (
	SELECT DISTINCT p1.game_id,
		CASE WHEN p1.player_id < p2.player_id THEN p1.player_id ELSE p2.player_id END AS player1,
		CASE WHEN p1.player_id < p2.player_id THEN p2.player_id ELSE p1.player_id END AS player2
	FROM participation p1 
		INNER JOIN participation p2 ON (p1.game_id = p2.game_id and p1.player_id <> p2.player_id)
	WHERE p1.status = 'confirmed' and p2.status = 'confirmed'
	)
SELECT p1.full_name, p2.full_name, count(game_id)
FROM players_duos 
	INNER JOIN player p1 ON player1 = p1.id
	INNER JOIN player p2 ON player2 = p2.id
GROUP BY p1.full_name, p2.full_name
ORDER BY count(game_id) DESC;