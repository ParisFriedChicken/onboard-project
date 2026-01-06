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
INSERT INTO game (created_at, date, max_players, address, status, version, host_player_id)
SELECT
  now() - (random() * interval '30 days'),
  CASE
    WHEN i % 4 = 0 THEN now() + interval '1 day'        -- created late -> risky (25% of the games)
    ELSE now() + interval '10 days'
  END,
  CASE
    WHEN i % 3 = 0 THEN 8								-- large game -> more risky (33% of the games)
    ELSE 4
  END,
  '',
  'created', 											-- initialize status to 'completed'
  0,													
  (SELECT id FROM player ORDER BY random() LIMIT 1)		-- initialize player randomly
FROM generate_series(1, 80) i;


-- 3. Participations with derivated status (not random)
INSERT INTO participation (player_id, game_id, created_at, status)
SELECT
  p.id,
  g.id,
  g.created_at + interval '1 hour',
  CASE
    WHEN p.last_active_at < now() - interval '60 days'
         AND g.date < now() + interval '2 days'
      THEN 'no_show'
    ELSE 'attended'
  END
FROM player p
JOIN game g ON random() < 0.15;
