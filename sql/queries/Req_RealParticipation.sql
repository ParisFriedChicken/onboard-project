--Count of confirmed participation in games
WITH nb_confirmed_participations AS (
    SELECT player_id, COUNT(*) as nb_confirmed
    FROM participation
    WHERE status = 'confirmed'
    GROUP BY player_id
)
SELECT p.full_name, rp.nb_confirmed
FROM player p LEFT JOIN nb_confirmed_participations rp 
ON p.id = rp.player_id ;