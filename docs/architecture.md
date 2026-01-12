
## AI Service 

### Contract

```
POST /ai/predict-participation

Input:
{
  "game_type": "board_game",
  "max_players": 6,
  "current_players": 4,
  "host_no_show_rate": 0.2,
  "days_before_event": 5
}

Output:
{
  "probability": 0.72,
  "risk_level": "medium"
}
```