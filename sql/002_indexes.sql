-- ================================================================
-- 002_indexes.sql
-- Objectif : Optimiser les performances de requêtes clés
-- en ajoutant des index ciblés et en mesurant les gains.
-- ================================================================

-- =================================================================
-- CONTEXTE
-- Tables concernées : player, game, participation
-- Objectif : améliorer les filtres fréquents et les jointures
-- =================================================================


-- ================================================================
-- Request#1 : Filtre sur player dans la table participation
-- ---------------------------------------------------------------
-- Requête typique : lister les participations d'un player
-- Avant : full scan de la table player.
-- ================================================================

DROP INDEX IF EXISTS idx_participation_player;

-- Avant index : mesurer le plan
EXPLAIN ANALYZE 
SELECT amount, status, game_id, player_id FROM participation WHERE player_id = 107;

-- Création de l’index
CREATE INDEX IF NOT EXISTS idx_participation_player 
	ON participation(player_id);

-- Après index : mesurer le nouveau plan
EXPLAIN ANALYZE 
SELECT amount, status, game_id, player_id FROM participation WHERE player_id = 107;


-- ================================================================
-- Cas 2 : Sélection d'un player avec son email
-- ---------------------------------------------------------------
-- Requête fréquente : Utilisé à chaque connexion.
-- Avant : connexion lente.
-- ================================================================

DROP INDEX IF EXISTS idx_player_email;

-- Avant index
EXPLAIN ANALYZE SELECT id, city, created_at, email, full_name, password, updated_at
	FROM public.player 
	WHERE email = 'eg2@gmail.com';

-- Index sur la colonne email pour accélérer la selection
CREATE INDEX IF NOT EXISTS idx_player_email ON player(email);

-- Après index
EXPLAIN ANALYZE SELECT id, city, created_at, email, full_name, password, updated_at
	FROM public.player 
	WHERE email = 'eg2@gmail.com';


-- ================================================================
-- Cas 3 : KPI 1 : Quantité de commissions sur les participations 
-- des 30 derniers jours
-- ---------------------------------------------------------------
-- Requête : Somme de toutes les participations sur une plage de 
-- date nécéssitant une jointure avec la table game
-- ================================================================

DROP INDEX IF EXISTS idx_game;

-- Avant index
EXPLAIN ANALYZE
SELECT p.full_name, count(g.id)
FROM game g INNER JOIN player p ON p.id = g.host_player_id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days'
GROUP BY p.full_name
ORDER BY count(g.id) DESC
LIMIT 1;

-- Création d’un index 
CREATE INDEX IF NOT EXISTS idx_game
    ON game(date, host_player_id);

-- Après index
EXPLAIN ANALYZE
SELECT p.full_name, count(g.id)
FROM game g INNER JOIN player p ON p.id = g.host_player_id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days'
GROUP BY p.full_name
ORDER BY count(g.id) DESC
LIMIT 1;


-- Met à jour les stats après ajout :
ANALYZE;

-- ================================================================
-- Notes de performance 
-- ---------------------------------------------------------------
-- Exemple de gain observé :
--   Requête 1 : 1.597 ms → 0.058 ms  (96.3~ % gain)
--   Requête 2 : 0.031 ms → 0.025 ms   (~19.3 % gain -> not really effective) 
--   Requête 3 : 2.161 ms → 0.709 ms    (~67.1 % gain)
--
-- Capture des plans avant/après :
--   https://explain.depesz.com/
--   (copie le plan EXPLAIN ANALYZE, sauvegarde les liens)
-- ================================================================