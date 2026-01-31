# Scenarios
## 1. Risky Game
- Few registered players
- Low reliability host
- Short delay
## 2. Low risk game
- Reliable host
- Reasonnable number of participants to the game
- Reasonnable delay
## 3. Insufficient data
- New host (first time at hosting)

## Results
| Scenario          | Input Summary                              | Expected  | Observed  |
|----------         | -----------------------------------------  | --------  | --------  |
| Risky Game        | Few players, host unreliable, short delay  | High risk | High risk |
| Healthy Game      | Reliable host, good fill ratio, long delay | Low risk  | Low risk  |
| Insufficient data | Host with no participations yet            | Unknown   | Low risk  |

### Conclusions
- The prediction seems to be reliable on "extreme scenarios"
- When the model has not enough data, it interprets the game as "Low risk", which is optimistic
- The model lacks of subtlity with the delays of game creation, for instance a 15 days delay is considered highly risky
- The data must be enriched with broader and better quality data
- The prediction call never blocks the user's actions