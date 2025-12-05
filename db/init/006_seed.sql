-- 1 000 fake players
INSERT INTO player (city, created_at, email, full_name, password)
SELECT
	'City ' || i,
    NOW() - (i || ' days')::interval,
    'player' || i || '@example.com',
    'John Doe ' || i,
    'pwd'
FROM generate_series(1, 1000) AS s(i);

-- 3000 fake games 
INSERT INTO game (city, created_at, email, full_name, password)
SELECT
	'City ' || i,
    NOW() - (i || ' days')::interval,
    'player' || i || '@example.com',
    'John Doe ' || i,
    'pwd'
FROM generate_series(1, 1000) AS s(i);

--Met a jour aleatoirement des hosts dans les games
WITH random_game_players AS (
	SELECT rn1, game_id, player_id
	FROM 
	(	SELECT ROW_NUMBER() OVER () as rn1, id as player_id
		FROM 
		(
			(SELECT id FROM player ORDER BY random()) 
			UNION ALL
			(SELECT id FROM player ORDER BY random()) 	
			UNION ALL
			(SELECT id FROM player ORDER BY random()) 	
		)) AS random_players,
		
		(SELECT ROW_NUMBER() OVER () as rn2, id as game_id FROM game) AS random_games
	WHERE random_players.rn1 = random_games.rn2
)
UPDATE game 
SET host_player_id = random_game_players.player_id
FROM random_game_players
WHERE game.id = random_game_players.game_id

--Met a jour aleatoirement des adresses dans les games
WITH random_game_addresses AS (
  SELECT game_id, address
		FROM 
		(
		SELECT ROW_NUMBER() OVER () as rn1, nom_rue || nom_ville as address
		FROM (
			SELECT 
				round((random()*6+1)::numeric, 0) ||
				', rue ' ||
			  CASE
				WHEN random() < 0.33 THEN 'de l''eglise'
				WHEN random() < 0.50 THEN 'des blanchisseurs'
				WHEN random() < 0.66 THEN 'du jeu de paume'
				WHEN random() < 0.80 THEN 'Foch'
				ELSE 'de la paix'
			  END AS nom_rue ,
			  CASE
				WHEN random() < 0.50 THEN ', 44000 Nantes'
				WHEN random() < 0.80 THEN ', 75011 Paris'
				ELSE ', 69000 Bordeaux'
			  END AS nom_ville
			FROM
				generate_series(1, 3004)
			)
		) AS random_address,
	(SELECT ROW_NUMBER() OVER () as rn2, id as game_id FROM game) AS random_games
  WHERE random_address.rn1 = random_games.rn2
  )
UPDATE game 
SET address = random_game_addresses.address
FROM random_game_addresses
WHERE game.id = random_game_addresses.game_id

-- 10000 fake participations
INSERT INTO participation (amount, no_flake, game_id, player_id)
SELECT 
	round((random() * 5)::numeric, 0),
	CASE 
		WHEN random() < 0.25 THEN false
		ELSE true
	END AS flake,
	1 as game_id,
	252 as player_id
FROM generate_series(1, 10000)

--Met a jour aleatoirement des players dans les participations
WITH random_participation_players AS (
	SELECT rn1, participation_id, player_id
	FROM 
	(	SELECT ROW_NUMBER() OVER () as rn1, id as player_id
		FROM 
		(
			(SELECT id FROM player ORDER BY random()) UNION ALL (SELECT id FROM player ORDER BY random()) UNION ALL
			(SELECT id FROM player ORDER BY random()) UNION ALL (SELECT id FROM player ORDER BY random()) UNION ALL
			(SELECT id FROM player ORDER BY random()) UNION ALL (SELECT id FROM player ORDER BY random()) UNION ALL
			(SELECT id FROM player ORDER BY random()) UNION ALL (SELECT id FROM player ORDER BY random()) UNION ALL
			(SELECT id FROM player ORDER BY random()) UNION ALL (SELECT id FROM player ORDER BY random()) 
			
			)) AS random_players,
		
		(SELECT ROW_NUMBER() OVER () as rn2, id as participation_id FROM participation) AS random_participations
	WHERE random_players.rn1 = random_participations.rn2
)
UPDATE participation 
SET player_id = random_participation_players.player_id
FROM random_participation_players
WHERE participation.id = random_participation_players.participation_id


--Met a jour aleatoirement des games dans les participations
WITH random_participation_games AS (
	SELECT rn1, participation_id, game_id
	FROM 
	(	SELECT ROW_NUMBER() OVER () as rn1, id as game_id
		FROM 
		(
			(SELECT id FROM game ORDER BY random()) UNION ALL (SELECT id FROM game ORDER BY random()) UNION ALL
			(SELECT id FROM game ORDER BY random()) UNION ALL (SELECT id FROM game ORDER BY random()) UNION ALL
			(SELECT id FROM game ORDER BY random()) UNION ALL (SELECT id FROM game ORDER BY random()) UNION ALL
			(SELECT id FROM game ORDER BY random()) UNION ALL (SELECT id FROM game ORDER BY random()) UNION ALL
			(SELECT id FROM game ORDER BY random()) UNION ALL (SELECT id FROM game ORDER BY random()) 
			
			)) AS random_games,
		
		(SELECT ROW_NUMBER() OVER () as rn2, id as participation_id FROM participation) AS random_participations
	WHERE random_games.rn1 = random_participations.rn2
)
UPDATE participation 
SET game_id = random_participation_games.game_id
FROM random_participation_games
WHERE participation.id = random_participation_games.participation_id
