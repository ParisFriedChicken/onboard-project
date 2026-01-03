-- Migration: add mandatory status column to participation
BEGIN;

-- Add column if missing with a safe default
ALTER TABLE public.participation
  ADD COLUMN IF NOT EXISTS status varchar(255) DEFAULT 'confirmed';

-- Backfill any NULLs (defensive)
UPDATE public.participation
  SET status = 'confirmed'
  WHERE status IS NULL;

-- Make column NOT NULL
ALTER TABLE public.participation
  ALTER COLUMN status SET NOT NULL;

COMMIT;
