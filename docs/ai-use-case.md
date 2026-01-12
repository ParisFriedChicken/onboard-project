# AI Use case

## Problem statement

Some games are cancelled by hosts because lack of players, which cause a bad experience for the user

## Decision supported

Assessing the risk that a game will be cancelled due to low participation.

## Inputs

| Input | Source |
| :--- | :--- |
| Final status of the game | DB |
| Max number of players | DB/Parameter |
| Number of days between the creation of the game and the start of the game | DB (Calculated) |
| Number of current registered players | DB/Parameter |
| No-show history of the host | DB |
| Game type | DB/Parameter |