from fastapi import FastAPI
from pydantic import BaseModel
import joblib
import numpy as np

app = FastAPI(title="AI Participation Prediction Service")

model = joblib.load("models/participation_model.joblib")

class PredictionInput(BaseModel):
	fill_ratio : float
	host_no_show_rate : float
	registered_players : int
	max_players : int
	game_type : int
	host_total_games : int
	days_before_event : int

class PredictionOutput(BaseModel):
    participation_prediction: float
    risk_level: str

@app.post("/ai/predict-participation", response_model=PredictionOutput)
def predict(data: PredictionInput):

    X = np.array([[
        data.fill_ratio,
        data.host_no_show_rate,
        data.registered_players,
        data.max_players,
        data.game_type,
        data.host_total_games,
        data.days_before_event
    ]])

    part_pred = model.predict(X)[0]

    if part_pred < 0.6:
        risk = "high"
    elif part_pred < 0.7:
        risk = "medium"
    else:
        risk = "low"

    return {
        "participation_prediction": round(part_pred, 2),
        "risk_level": risk
    }
