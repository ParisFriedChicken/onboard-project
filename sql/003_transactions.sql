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
SELECT * FROM participation WHERE id = 1 FOR UPDATE;
-- Simule du traitement long
SELECT pg_sleep(15);  -- rester bloqué 15s avant COMMIT
COMMIT;  


-- 1b) Session B: tenter de modifier la même ligne (pendant que A dort)
-- (Exécute ceci dans la seconde connexion pendant que Session A est en pg_sleep)
BEGIN;
UPDATE participation SET no_flake = false WHERE id = 1;  -- restera bloqué jusqu'au COMMIT de A
COMMIT;

