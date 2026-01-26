from fastapi import FastAPI
from pydantic import BaseModel, Field
import joblib
import numpy as np

app = FastAPI(title="AI Participation Prediction Service")

model = joblib.load("models/participation_model.joblib")

class PredictionInput(BaseModel):
    fill_ratio: float = Field(..., alias="fillRatio")
    host_no_show_rate: float = Field(..., alias="hostNoShowRate")
    registered_players: int = Field(..., alias="registeredPlayers")
    max_players: int = Field(..., alias="maxPlayers")
    game_type: int = Field(..., alias="gameType")
    host_total_games: int = Field(..., alias="hostTotalGames")
    days_before_event: int = Field(..., alias="daysBeforeEvent")

    class Config:
        allow_population_by_field_name = True

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

    part_pred = float(model.predict_proba(X)[:, 1][0])


    if part_pred < 0.90:
        risk = "high"
    elif part_pred < 0.95:
        risk = "medium"
    else:
        risk = "low"

    print(round(part_pred, 2))
    print(risk)
    
    return {
        "participation_prediction": round(part_pred, 2),
        "risk_level": risk
    }
