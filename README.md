# Estate Backend Server for Chatop

# Dependancies

- Maven
- Mysql
- npm

# Setup
## Configuring

The Application is configured using Environnement variables:
- `JWT_SECRET`: Secret used to encrypt the JWT Token. 
- `DB_NAME`: Name of the database to use.
- `DB_USERNAME`: User to use for the database.
- `DB_PASSWORD`: Password to use for the database.

## Launch the application
Make sure the database is running and created.
If the database isn't created use this command:
```bash
mysql -D"${DB_NAME}" < script.sql
```

Launch the back-end:
```bash
cd backend 
mvn spring-boot:run
```
Launch frontend:
```bash
cd frontend
npm install
npm run start
```

Navigate to `https://localhost:4200`.

<!--
### Attendus.

- Backend fonctione sans erreures.
- Toute les routes presentes.
- Swagger.
- Decouper les code Controller/Service/Repository. 
- Security.
- Encrypter les mdp en base.
-->
