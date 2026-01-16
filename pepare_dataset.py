import pandas as pd

df = pd.read_csv("sql/exports/games_dataset.csv")

print(df.head())
print(df.describe())
