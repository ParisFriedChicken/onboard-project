-- View 1 : Total Gain Last 30 Days
CREATE OR REPLACE VIEW vw_total_gain_last_30_days AS
	SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_30_days
	FROM participation p INNER JOIN game g ON p.game_id = g.id
	WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days';

-- View 2 : Total Gain Last Year
CREATE OR REPLACE VIEW vw_total_gain_last_year AS
	SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_year
	FROM participation p INNER JOIN game g ON p.game_id = g.id
	WHERE date > CURRENT_TIMESTAMP - INTERVAL '1 year';

--- View 3 : average Fee Collected by participation
CREATE OR REPLACE VIEW vw_average_fee AS 
	SELECT ROUND(AVG(amount) * 0.1, 2) AS average_fee
	FROM participation;

--- View 4 : top 50 Hosts
CREATE OR REPLACE VIEW vw_top_50_hosts AS 
	SELECT pl.full_name, ROUND(SUM(amount) * 0.1, 2) host_fee_amount
	FROM participation p INNER JOIN game g ON g.id=p.game_id
		INNER JOIN player pl on pl.id = g.host_player_id
	GROUP BY pl.full_name
	ORDER BY host_fee_amount DESC
	LIMIT 50;

--- View 5 : top 5 Players
CREATE OR REPLACE VIEW vw_top_5_players AS 
	SELECT pl.full_name, ROUND(SUM(amount) * 0.1, 2) player_fee_amount
	FROM participation p INNER JOIN player pl ON pl.id = p.player_id
	GROUP BY pl.full_name
	ORDER BY player_fee_amount DESC
	LIMIT 5;
	
-- View 6 : Number of active players (having participated in at least one game during last 30 days)
CREATE OR REPLACE VIEW vw_active_players_last_30_days AS
	SELECT COUNT(DISTINCT player_id) AS active_players_last_30_days
	FROM participation
	WHERE game_id IN (
	    SELECT id FROM game
	    WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days'
	);

	
-- View 7 : Best Buddies (by how many games they played together)
CREATE MATERIALIZED VIEW mv_best_buddies AS
	WITH players_duos AS (
		SELECT DISTINCT p1.game_id,
			CASE WHEN p1.player_id < p2.player_id THEN p1.player_id ELSE p2.player_id END AS player1,
			CASE WHEN p1.player_id < p2.player_id THEN p2.player_id ELSE p1.player_id END AS player2
		FROM participation p1 
			INNER JOIN participation p2 ON (p1.game_id = p2.game_id and p1.player_id <> p2.player_id)
		WHERE p1.no_flake = true and p2.no_flake = true
		)
	SELECT p1.full_name as buddy1, p2.full_name as buddy2, count(game_id) as count_game
	FROM players_duos 
		INNER JOIN player p1 ON player1 = p1.id
		INNER JOIN player p2 ON player2 = p2.id
	GROUP BY p1.full_name , p2.full_name 
	ORDER BY count(game_id) DESC;

-- Index to speed up refresh
CREATE UNIQUE INDEX ON mv_best_buddies (buddy1, buddy2, count_game);
ANALYZE mv_best_buddies;

-- Create read-only user and grant select on views
CREATE ROLE read_only_user WITH
	LOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	NOINHERIT
	NOREPLICATION
	NOBYPASSRLS
	CONNECTION LIMIT -1
	PASSWORD 'read_only_user';
	
	
GRANT SELECT ON vw_total_gain_last_30_days TO readonly_user;
GRANT SELECT ON vw_total_gain_last_year TO readonly_user;
GRANT SELECT ON vw_average_fee TO readonly_user;
GRANT SELECT ON vw_top_50_hosts TO readonly_user;
GRANT SELECT ON vw_top_5_players TO readonly_user;
GRANT SELECT ON vw_active_players_last_30_days TO readonly_user;

GRANT SELECT ON mv_best_buddies TO readonly_user;