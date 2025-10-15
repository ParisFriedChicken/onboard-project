--Count of real participation in games
WITH nb_real_participations AS (
    SELECT player_id, COUNT(no_flake) as nb_no_flake
	FROM participation
	WHERE no_flake = true
	GROUP BY player_id
)
SELECT p.full_name, rp.nb_no_flake
FROM player p LEFT JOIN nb_real_participations rp 
ON p.id = rp.player_id ;