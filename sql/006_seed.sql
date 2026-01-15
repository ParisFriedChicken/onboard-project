-- 1. Players with different reliability profiles
INSERT INTO player (city, created_at, last_active_at, email, full_name, password)
SELECT
	'City ' || i,
	now() - (random() * interval '180 days'),
	CASE
	  WHEN i % 5 = 0 THEN now() - interval '90 days'   -- last activity long ago -> less reliable (20% of the players)
	  ELSE now() - interval '7 days'                   -- last activity not long ago -> more reliable
	END,
    'player' || i || '@example.com',
    'John Doe ' || i,
    'pwd'
FROM generate_series(1, 50) i;


-- 2. Games with more or less risky timing
INSERT INTO game (created_at, date, min_players, max_players, address, status, version, host_player_id, game_type)
SELECT
  now() - (random() * interval '30 days'),
  CASE
    WHEN i % 4 = 0 THEN now() + interval '1 day'        -- created late -> risky (25% of the games)
    ELSE now() + interval '10 days'
  END,
  2,
  CASE
    WHEN i % 3 = 0 THEN 8							-- large game -> more risky (33% of the games)
    ELSE 4
  END,
  '',
  'created',  									-- initialize status to 'created'
  0,
  -- host_player_id will come from the lateral subselect below (one random host per game row)
  (SELECT id FROM player WHERE i = i ORDER BY random() LIMIT 1),
  -- Randomly assign a game_type among common types, fallback to '0'
  CASE
    WHEN random() < 0.8 THEN 'board_game'
    WHEN random() < 1 THEN 'outdoor_game'
  END
FROM generate_series(1, 80) i;


-- 3. Participations with derivated status (not random)
--    Be careful not to exceed max_players per game
INSERT INTO participation (player_id, game_id, created_at, status)
SELECT
  player_id,
  game_id,
  created_at2,
  status2
FROM (
  SELECT
    p.id AS player_id,
    g.id AS game_id,
    g.created_at + interval '1 hour' AS created_at2,
    CASE
      WHEN p.last_active_at < now() - interval '60 days'
           AND g.date < now() + interval '2 days'
        THEN 'no_show'
      ELSE 'attended'
    END AS status2,
    ROW_NUMBER() OVER (
      PARTITION BY g.id
      ORDER BY random()
    ) AS rn
  FROM player p
  JOIN game g ON random() < 0.15
) ranked_participations
JOIN game g ON g.id = ranked_participations.game_id
WHERE rn <= g.max_players;