## Features

#### fill_ratio

##### Description
Ratio between the current number of registered players to a game and the maximum players allowed to a game.

##### Formula 
fill_ratio = registered_players_at_creation / max_players

##### Rationale
This feature indicates if the players are registering early to this game. If it's not filling fast, it can explain why players are not incentivized to register more. 

##### Limitations
- Can be misleading for very small group size
- Depends on the accuracy of max_players


#### days_before_event

##### Description
Delay between the creation date of a game and the effective date of the game.

##### Formula 
days_before_event = game_date - game_creation_date

##### Rationale
The games that are planned a long time in advance have an increased probability to have a lot of participants. If the game is created very late, players don't have enough time to register.

##### Limitations
- A too long delay can also be bad for the participation rate
- Players who know each other well can create games with a small delay and still be successful


#### game_type_encoded

##### Description
type of game (board game, outdoor game, soccer match..)

##### Formula 
game_type_encoded :
- "board_games" -> 1
- "outdoor_game" -> 2

##### Rationale
Some types of games are more or less popular for players, and some game types can be correlated to an easy cancellation by players

##### Limitations
- There's not a lot of game types : it's mostly board games


#### host_no_show_rate

##### Description
Ratio between the number of games where the host didn't show up, and the total games he registered

##### Formula 
host_no_show_rate = count(no_show_games) / count(total_games)

##### Rationale
If the player often don't show up to the game he registered, we can assume he will easily cancel a game he hosts.

##### Limitations
- Some hosts haven't participate to a game yet