import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression

df = pd.read_csv("exports/games_dataset_final.csv")
df["is_participation_high"] = (df["participation_rate"] >= 0.7).astype(int)

y = df["is_participation_high"]
X = df.drop(columns=["game_id", "participation_rate", "is_participation_high"])

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

model = LogisticRegression()
model.fit(X_train, y_train)

probas = model.predict_proba(X_test)[:, 1]  # probabilité entre 0 et 1

print(probas[:10])

import joblib

joblib.dump(model, "models/participation_model.joblib")

for feature, coef in zip(X.columns, model.coef_):
    print(feature, coef)