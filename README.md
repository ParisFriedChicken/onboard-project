# 🎲 Onboard — Application de rencontres amicales par le biais des jeux de société

Onboard est une API Spring Boot permettant de planifier des parties de jeux de société : joueurs, parties.  
Le projet utilise **Spring Boot**, **Spring Data JPA**, et une base de données **PostgreSQL**.

---

## 🚀 Fonctionnalités principales

- ✅ CRUD complet sur les joueurs et les parties  
- 🧩 Stockage des données avec PostgreSQL  
- 🔒 Authentification JWT (Spring Security)  
- 🧪 Tests unitaires et d’intégration (JUnit / MockMvc)

---

## ⚙️ Prérequis

- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/) ou H2 (par défaut)
- IDE recommandé : [Spring Tools for Eclipse](https://spring.io/tools)

---

## 🛠️ Installation

Clone le projet :
```bash
git clone https://github.com/moncompte/boardly.git
cd boardly
```

Compile et lance :
```bash
mvn spring-boot:run
```

L’application démarre sur :  
👉 [http://localhost:8080](http://localhost:8080)

---

## 🧩 Configuration de la base de données

### PostgreSQL
Modifie le fichier `application.properties` :
```
spring.datasource.url=jdbc:postgresql://localhost:5432/onboard
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

---

## 🔍 Exemples d’utilisation

**Voir collection Postman**

postman/Test Onboard API.postman_collection.json

---

## 🧠 Structure du projet

```
src/
 ├── main/java/com/example/boardly
 │     ├── controller/      → REST Controllers
 │     ├── service/         → Logique métier
 │     ├── repository/      → Accès aux données JPA
 │     ├── model/           → Entités JPA
 │     └── OnboardApplication.java
 └── test/java/...          → Tests unitaires et d’intégration
```

---

## 🧰 Technologies utilisées

- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Maven**
- **JUnit 5 + MockMvc**

---

## 👨‍💻 Auteur

Projet développé par **[Sébastien Lemaitre](https://github.com/ParisFriedChicken/onboard-project)**  
📧 Contact : sebastien.lemaitre@gmail.com

---

## 📝 Licence

Ce projet est sous licence MIT — voir le fichier [LICENSE](LICENSE) pour plus de détails.
