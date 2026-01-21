# Product Requirements Document

## 1. Personas

### Product Team

#### Role and context 

- Decides and prioritize the evolutions of the product to improve end user experience
- Permits the implementation of internal tools for internal users
- Communicate with internal teams

#### Primary Goal

- Improve the player's experience
- Reduce games cancellations
- Increase participations at games

#### Pain point

- Don't know what measures to take to reduce failing games
- No metric to assess probability of failing games

#### Decision supported by the prediction

- Identification of the risky games earlier
- Prioritization of functionnalities that have a leverage on those characteristics
  
---
### Ops Team

#### Role and context 

- Configures notifications, mailings to the end user
- Adjust system resources regarding to the platform needs

#### Primary Goal

- Dedicate the infrastructure's resources primarly to games with a chance of success
- Apply strategies to the system events regarding to the right games

#### Pain point

- Determine the risk level of the games that are going to occur soon

#### Decision supported by the prediction

- Adjust timing of the notifications/mailings 
- Decide to do nothing for the games that are going to fail

## 2. AI Use Case Definition and Scope

### Business problem

We are managing a board-games meetup app, and we notice that often, games are cancelled by hosts, and also games are often at a low participation rate. These problems tend to disengage users of the app. We don't know exactly what's behind these cancellations, neither how we can counter-attack. Also, our system infrastructure dedicates resources to events that don't materialize.

### Available data

In the end-user app, we have a database that contains data concerning users (aka players), games and participation to these games.
The kind of data we can leverage is :
- Historical game data
- Historical players data
- Players and hosts behaviour data (participations, cancellations, no shows..)
- Time-related data, delays in the game and participation lifecycle

### Expected signal about product

The signal that would help us to better assess the risk of game cancellation could be the participation rate probability to a game. It would reflect the engagement expectation regarding to some parameters.

### Usage moment

This signal is used at the game creation. We can compare the historical data with the game data and assess his probability of participation. It helps the team to react before the game due date

### Supported decisions

- Product decisions : helps the product team to decide what functionnality to promote
- Ops decisions : helps the Ops team to configure the platform in order to optimize resources consumption

### In scope (V1)

- Implementation of a simple predictive feature : the probability of participation to a game. 
- Signal exposed by an API endpoint. 
- Use of existing historical game and participation data
- Batch trained simple model
- Simple Dashboard

### Out of scope

- Advanced model techniques
- Automatic changes to the end user app
- Complex Dashboards

## 3. Functional Backlog and Prioritization

### Must have

- Generate a signal that predicts the participation rate of a simulated game
- Create an API endpoint that enables the internal team to access this signal
- Translate the signal in a readable risk level (low/medium/high)

### Should have

- Generate a signal that predicts the participation rate of a real game that's been just created
- Create a simple dashboard that exposes the prediction signal to promote its usage to the teams
- Enable the signal risk level (low/medium/high) to be configurable

### Could have

- Compare the signal with the real participation rate once the games have occured
- Keep a minimal history of the predictions made
- Enable a simple manual feedback on the signal for the team

### User Story 1 (Example)

#### Title
Generate a signal that predicts the participation rate of a simulated game
#### Description
As a member of the product team, 
I want to have access to a signal that predicts the participation rate of a simulated game
So that I can get help making data-driven product decision
#### Acceptance criteria
- The raw signal must be between 0 and 1
- The signal must accept relevant parameters for input
- The signal must reflect obvious assumption about a game (ie if a game is created 1 day before, the participation rate prediction should be low)