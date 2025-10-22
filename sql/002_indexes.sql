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
-- Cas 1 : Filtre sur player dans la table participation
-- ---------------------------------------------------------------
-- Requête typique : lister les participations d'un player
-- Avant : full scan de la table player.
-- ================================================================

DROP INDEX IF EXISTS idx_participation_player;

-- Avant index : mesurer le plan
EXPLAIN ANALYZE 
SELECT amount, no_flake, game_id, player_id FROM participation WHERE player_id = 107;

-- Création de l’index
CREATE INDEX IF NOT EXISTS idx_participation_player 
	ON participation(player_id);

-- Après index : mesurer le nouveau plan
EXPLAIN ANALYZE 
SELECT amount, no_flake, game_id, player_id FROM participation WHERE player_id = 107;


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

-- Index sur la colonne temporelle pour accélérer le tri & group by
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
SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_30_days
FROM participation p INNER JOIN game g ON p.game_id = g.id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days';

-- Création d’un index 
CREATE INDEX IF NOT EXISTS idx_game
    ON game(id);

-- Après index
EXPLAIN ANALYZE
SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_30_days
FROM participation p INNER JOIN game g ON p.game_id = g.id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days';


-- Met à jour les stats après ajout :
ANALYZE;

-- ================================================================
-- Notes de performance 
-- ---------------------------------------------------------------
-- Exemple de gain observé :
--   Requête 1 : 4.12 ms → 0.05 ms  (~98.8 % gain)
--   Requête 2 : 15.3 ms → 2.1 ms   (~86 % gain)
--   Requête 3 : 120 ms → 8.5 ms    (~92.9 % gain)
--
-- Capture des plans avant/après :
--   https://explain.depesz.com/
--   (copie le plan EXPLAIN ANALYZE, sauvegarde les liens)
-- ================================================================
