-- Migration: add max_players, min_players, game_type to game table with safe defaults
BEGIN;

ALTER TABLE public.game
  ADD COLUMN IF NOT EXISTS max_players integer DEFAULT 0;

ALTER TABLE public.game
  ADD COLUMN IF NOT EXISTS min_players integer DEFAULT 0;

ALTER TABLE public.game
  ADD COLUMN IF NOT EXISTS game_type varchar(255) DEFAULT '0';

-- Backfill NULLs (defensive)
UPDATE public.game SET max_players = 0 WHERE max_players IS NULL;
UPDATE public.game SET min_players = 0 WHERE min_players IS NULL;
UPDATE public.game SET game_type = '0' WHERE game_type IS NULL;

-- Make columns NOT NULL
ALTER TABLE public.game ALTER COLUMN max_players SET NOT NULL;
ALTER TABLE public.game ALTER COLUMN min_players SET NOT NULL;
ALTER TABLE public.game ALTER COLUMN game_type SET NOT NULL;

COMMIT;