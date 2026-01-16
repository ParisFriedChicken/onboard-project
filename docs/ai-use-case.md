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
