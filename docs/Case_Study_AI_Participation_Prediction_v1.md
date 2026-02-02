# AI Participation Prediction — Case Study v1

## 1. Product context

This platform was designed to support the organization of board game events between players.
Beyond core operational needs, its main goal is to provide reliable participation data
to support product and operational decision-making.
The main problem of our users is to help end users to host and play games that will be a success, ie the games won't be canceled nor too few players participate to these games.

## 2. Business problem

The risk of such a problem is the low user adoption of our app, and for internal user not too react quickly enough. Before having a prediction signal, the assessment of the games probability to succeed was guessed by intuition. 

## 3. Solution overview

The architecture of the platform is relatively simple : the end-user app produces business data history, that is consumed by the ai service to train the prediction model, then the end-user app backend consumes the ai signal, by providing data about a planned game, and getting the participation prediction result (indicator of game success).

The signal is a predictive ratio of the game's participation, enriched by a risk level (High/Medium/Low). Each time a game is updated by a player or an host, the signal is recalculated and returned to the product backend. 

The signal rendering never blocks nor disturbs the end-user experience : the calls to the signal are time restricted (3 seconds max), and the potential signal service dysfunctions are protected by a fallback process. 

## 4. Observed results

The prediction seems to be reliable on "extreme scenarios" such as hosts reliability or delay for game creation. 
When the model has not enough historic data about the host, it interprets the game as "Low risk", which is optimistic. 
The model lacks of subtlity with the delays of game creation, for instance a 15 days delay is considered highly risky, which doesn't seems to be right intuitively.
The data must be enriched with broader and better quality data.
  
## 5. Limitations & next steps

### Current limitations

This first version of the prediction is intentionally simple and exploratory.
- The dataset is limited in size and mainly based on historical operational data. Some contextual factors (user motivation, external constraints, last-minute changes) are not captured.
- The prediction does not automate any decision. Final actions always remain in the hands of product and operations teams. These limitations are assumed and aligned with the objective of learning before scaling.

### Potential next steps

Several improvements could be considered in future iterations:
- Add monitoring to compare predicted participation with actual outcomes over time.
- Introduce feedback loops from internal teams to refine thresholds and interpretations.
- Recalibrate or retrain the model as more data becomes available.
- Extend the signal to additional use cases once data maturity increases.