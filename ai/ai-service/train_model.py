import pandas as pd

df = pd.read_csv("exports/games_dataset_final.csv")
y = df["participation_rate"]
X = df.drop(columns=["game_id", "participation_rate"])

from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

from sklearn.linear_model import LinearRegression

model = LinearRegression()
model.fit(X_train, y_train)

predictions = model.predict(X_test)
predictions = predictions.clip(0, 1)

print(predictions[:10])

import joblib

joblib.dump(model, "models/participation_model.joblib")

for feature, coef in zip(X.columns, model.coef_):
    print(feature, coef)