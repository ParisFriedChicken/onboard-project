# AI Use case

## Problem statement

Some games are cancelled by hosts because lack of players, which cause a bad experience for the user

## Decision supported

Assessing the risk that a game will be cancelled due to low participation.

## Inputs

| Input | Source |
| :--- | :--- |
| Max number of players | DB |
| Number of days between the creation of the game and the start of the game | DB (Calculated) |
| Number of current registered players | DB |
| No-show history of the host | DB(Calculated) |
| Game type | DB | 

## Outputs

| Output | Description |
| :--- | :--- |
| Probability of cancellation | A score between 0 and 1 indicating the likelihood of the game being cancelled due to low participation. |
| Risk level | Categorical risk level (e.g., Low, Medium, High) based on the probability of cancellation. |

## Insights

Here are the feature weights as defined by the model :
| Feature | Weight |
| :--- | :--- |
| game_id | -0.001086421488462285 |
| host_no_show_rate | 0.32485869227339403 |
| host_total_games | 0.0034531877308004444 |
| max_players | -0.09800726822628178 |
| registered_players | 0.10951766724552181 |
| fill_ratio | -0.6668395718120782 |
| days_before_event | 0.002078490062121935 |
| game_type -0.035280789469800206 |

The data suggests that :
- the Fill ratio of the games
- the Host history of not showing to the game
influence the most the participation rate to a game
- The number of registered players has a moderate impact on reliability

## Limitations

- The dataset is small
- There are possible biases 
- This is only an indicator, it does not trigger any actions
