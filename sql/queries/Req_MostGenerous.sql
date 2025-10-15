-- 2 Most Generous Players
SELECT player_id, SUM(amount) total_amount
FROM public.participation
GROUP BY player_id
ORDER BY total_amount DESC 
LIMIT 2
