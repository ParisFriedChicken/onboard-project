"""
This script prepares a clean, production-like dataset
from historical game participation data for a predictive use case.
"""
import pandas as pd

df = pd.read_csv("../exports/games_dataset.csv")

print(df.head())
print(df.describe())

df = df.rename(columns={
    "game_type_encoded": "game_type",
    "actual_participation_rate": "participation_rate"
})

df.to_csv("../exports/games_dataset_final.csv", index=False)