-- ================================================================
-- 004_transactions.sql
-- Day 4 - Transactions, Concurrency & Locks (PostgreSQL)
-- WARNING: For demo only. Do NOT run on production.
-- ================================================================

-- =========================
-- 0) Setup minimal tables
-- =========================
DROP TABLE IF EXISTS game_test;
CREATE TABLE game_test (
  id serial PRIMARY KEY,
  host text NOT NULL,
  address text NOT NULL
);

INSERT INTO game_test (host, address) VALUES
('Alice', '3, rue des chats gris'),
('Bob',   '10, allée des bois fleuris'),
('Cathy', '8, impasse Guillaume Appolinaire');

COMMIT;
-- ================================================================
-- 1) Simple long transaction using SELECT ... FOR UPDATE + pg_sleep()
-- ================================================================
-- Goal: To show that a line is locked during a transaction.
-- Instructions:
--  - Open Session A (terminal/pgAdmin)
--  - Open Session B (another terminal/pgAdmin)
-- Session A runs 1a, Session B runs then 1b and we can observe the locking.
-- ================================================================

-- 1a) Session A: commencer la transaction et verrouiller la ligne
-- (Exécute ceci dans une première connexion)
BEGIN;
-- verrou exclusif sur la ligne id=1
SELECT * FROM game_test WHERE id = 1 FOR UPDATE;
-- Simule du traitement long
SELECT pg_sleep(15);  -- rester bloqué 15s avant COMMIT
COMMIT;  


-- 1b) Session B: tenter de modifier la même ligne (pendant que A dort)
-- (Exécute ceci dans la seconde connexion pendant que Session A est en pg_sleep)
BEGIN;
UPDATE game_test SET host = 'Paul' WHERE id = 1;  -- restera bloqué jusqu'au COMMIT de A
COMMIT;


-- ================================================================
-- 2) Deadlock demo (2 sessions, order inversion)
-- ================================================================
-- Goal: provoke a deadlock by locking two resources in an inverse order
-- Instructions:
--  - Session C: run 2a
--  - Session D: run 2b
--  - Then Session C run 2a-step2 (ou vice versa) -> deadlock 
-- ================================================================

-- 2a) Session C:
BEGIN;
-- lock on game_test 1
UPDATE game_test SET host = 'Paul' WHERE id = 1;
-- pause (laisser le temps à la session D de verrouiller l'autre)
SELECT pg_sleep(5);
-- trying to lock game_test 2 (can produce deadlock if D locked 2 and waits for 1)
UPDATE game_test SET host = 'Anna' WHERE id = 2;
-- COMMIT;

-- 2b) Session D:
BEGIN;
-- lock on account 2
UPDATE game_test SET host = 'Eric' WHERE id = 2;
SELECT pg_sleep(5);
-- essayer de verrouiller account 1 -> deadlock possible
UPDATE game_test SET host = 'Michael' WHERE id = 1;
-- COMMIT;

-- PostgreSQL devrait détecter le deadlock et annuler l'une des transactions (error: deadlock detected).


-- ================================================================
-- 3) SKIP LOCKED and consumer pattern (processing queue safely)
-- ================================================================
-- Goal: to show how multiple consumers can select a line without conflict
-- Typical usage : players wants to participate to the first game 
-- that isn't fully booked
-- ================================================================

-- Table creation for demo
DROP TABLE IF EXISTS game_test;
CREATE TABLE game_test (
  id serial PRIMARY KEY,
  game text,
  fully_booked boolean DEFAULT false
);

INSERT INTO game_test (game) VALUES ('Paul''s game'),('Anna''s game'),('Chris''s game'),('Seb''s game');

-- Consumer example (in several sessions, run in parallel) :
-- Session E (worker 1)
BEGIN;
WITH avbl_games AS (
  SELECT id FROM game_test WHERE NOT fully_booked ORDER BY id LIMIT 1 FOR UPDATE SKIP LOCKED
)
UPDATE game_test
SET fully_booked = true
FROM avbl_games
WHERE game_test.id = avbl_games.id
RETURNING game_test.*;
-- COMMIT;
