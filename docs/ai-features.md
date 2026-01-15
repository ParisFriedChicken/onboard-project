# Features

## Raw -> Features

| Raw Input                     | Derivated feature                          | Why                            |
| ----------------------------- | ------------------------------------------ | ------------------------------ |
| max_players, current_players  | fill_ratio = current_players / max_players | More predicting potential      |
| days_before_event             | days_before_event                          | Already a signal               |
| host_no_show_rate             | host_no_show_rate                          | Already a signal               |
| game_type                     | game_type_encoded                          | Usable category                |

## Features

##### fill_ratio
- Formula: current_players / max_players
- Range: 0 -> 1

##### days_before_event
- Formula: date - created_at
- Range: 0 -> 100

##### host_no_show_rate
- Formula: count(game) where participation(host_player_id) = 'no_show' / count(game) 
- Range: 0 → 1

##### game_type_encoded
- Formula: board_game = 1, outdoor_game = 2
- Range: 1 → 2

