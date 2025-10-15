--- KPI 1 : Amount of fees collected on behalf of players during last 30 days
SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_30_days
FROM participation p INNER JOIN game g ON p.game_id = g.id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days';

--- KPI 2 : Amount of fees collected on behalf of players during last year
SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_30_days
FROM participation p INNER JOIN game g ON p.game_id = g.id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '1 year';

--- KPI 3 : average Fee Collected by participation
SELECT ROUND(AVG(amount) * 0.1, 2) AS average_fee
FROM participation;

--- KPI 4 : top 5 Hosts
SELECT g.host_player_id, ROUND(SUM(amount) * 0.1, 2) host_fee_amount
FROM participation p INNER JOIN game g ON g.id=p.game_id
GROUP BY g.host_player_id
ORDER BY host_fee_amount DESC
LIMIT 5;
