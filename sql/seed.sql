-- 1 000 fake users
INSERT INTO users (id, name, email, created_at)
SELECT
    gen_random_uuid(),
    'User ' || i,
    'user' || i || '@example.com',
    NOW() - (i || ' days')::interval
FROM generate_series(1, 1000) AS s(i);