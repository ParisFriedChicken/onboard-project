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
-- Cas 3 : Recherche textuelle produit
-- ---------------------------------------------------------------
-- Requête : trouver un produit par nom partiel.
-- Exemple : "SELECT * FROM products WHERE name ILIKE '%controller%';"
-- ================================================================

-- Avant index
EXPLAIN ANALYZE
SELECT ROUND(SUM(amount) * 0.1, 2) AS total_gain_last_30_days
FROM participation p INNER JOIN game g ON p.game_id = g.id
WHERE date > CURRENT_TIMESTAMP - INTERVAL '30 days';

-- ✅ Création d’un index trigram (pg_trgm nécessaire)
CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS idx_products_name_trgm
    ON products USING gin (name gin_trgm_ops);

-- Après index
EXPLAIN ANALYZE
SELECT * FROM products WHERE name ILIKE '%controller%';


-- ================================================================
-- 4️⃣ Cas 4 : Index partiel (tickets ouverts)
-- ---------------------------------------------------------------
-- Si ta table tickets contient status = 'OPEN' / 'CLOSED'...
-- On indexe uniquement les tickets ouverts.
-- ================================================================

CREATE INDEX IF NOT EXISTS idx_tickets_open_only
    ON tickets (priority)
    WHERE status = 'OPEN';


-- ================================================================
-- 5️⃣ Cas 5 : Index composite (user_id + created_at)
-- ---------------------------------------------------------------
-- Pour les historiques d’achats par utilisateur triés dans le temps.
-- ================================================================

CREATE INDEX IF NOT EXISTS idx_orders_user_date
    ON orders(user_id, created_at DESC);


-- ================================================================
-- 🧹 Nettoyage / maintenance
-- ---------------------------------------------------------------
-- Vérifie les index existants :
-- \d orders
-- \d products
--
-- Met à jour les stats après ajout :
ANALYZE;

-- ================================================================
-- 📈 Notes de performance (à documenter dans rapport perfs)
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
