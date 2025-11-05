--- KPI 1 : Amount of fees collected on behalf of players during last 30 days
SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_30_days
FROM participation p INNER JOIN game g ON p.game_id = g.id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days';

--- KPI 2 : Amount of fees collected on behalf of players during last year
SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_year
FROM participation p INNER JOIN game g ON p.game_id = g.id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '1 year';

--- KPI 3 : average Fee Collected by participation
SELECT ROUND(AVG(amount) * 0.1, 2) AS average_fee
FROM participation;

--- KPI 4 : top 50 Hosts
SELECT pl.full_name, ROUND(SUM(amount) * 0.1, 2) host_fee_amount
FROM participation p INNER JOIN game g ON g.id=p.game_id
	INNER JOIN player pl on pl.id = g.host_player_id
GROUP BY pl.full_name
ORDER BY host_fee_amount DESC
LIMIT 50;

--- KPI 5 : top 5 Players
SELECT pl.full_name, ROUND(SUM(amount) * 0.1, 2) player_fee_amount
FROM participation p INNER JOIN player pl ON pl.id = p.player_id
GROUP BY pl.full_name
ORDER BY player_fee_amount DESC
LIMIT 5;

--- KPI 6 : Number of active players (having participated in at least one game during last 30 days)
SELECT COUNT(DISTINCT player_id) AS active_players_last_30_days
FROM participation
WHERE game_id IN (
    SELECT id FROM game
    WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days'
);


