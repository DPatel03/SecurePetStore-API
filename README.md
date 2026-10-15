# SecurePetStore API

A pet store backend I built to practice Spring Security. Users register and log in, get a JWT, and what they can do depends on their role: customer, employee, or manager. The endpoint layout follows the Swagger Petstore sample (`/pet`, `/store`, `/user`).

## What's in it

- JWT login with a stateless filter (`JwtFilter`) in front of Spring Security, HTTP Basic also enabled
- BCrypt password hashing (cost 10)
- Three roles (`CUSTOMER`, `EMPLOYEE`, `MANAGER`) enforced per endpoint in `SecurityConfig`
- REST endpoints for pets, orders and inventory, tags, and users
- Spring Data JPA on MySQL, with Hibernate creating the tables
- JUnit 5 and Spring Boot Test suites for the controllers, services, JWT code and models

**Stack:** Java 17, Spring Boot 3.4.2, Spring Security 6, Spring Data JPA, MySQL, jjwt 0.12.5, Lombok, Maven

## Run it

You need JDK 17 and a MySQL 8+ server with an empty database:

```sql
CREATE DATABASE petstore_db;
```

The datasource settings live in `src/main/resources/application.properties`. Override them with environment variables instead of editing the file:

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/petstore_db"
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD="your-password"
./mvnw spring-boot:run
```

Then, in another terminal:

```bash
# register (the default role is CUSTOMER)
curl -X POST localhost:8080/user/register -H 'Content-Type: application/json' \
  -d '{"id":1,"username":"alice","password":"change-me","email":"alice@example.com"}'

# log in, the response body is the JWT
TOKEN=$(curl -s -X POST localhost:8080/user/login -H 'Content-Type: application/json' \
  -d '{"username":"alice","password":"change-me"}')

# call a protected endpoint
curl -H "Authorization: Bearer $TOKEN" localhost:8080/api/hello
```

Tokens last about two minutes, so log in again if you start getting 401s.

## Who can do what

| Endpoint | Who |
|---|---|
| `POST /user/register`, `POST /user/login` | anyone |
| `GET /pet/{id}`, `GET /store/order/{orderId}`, `GET /tag/{id}` | customer, employee, manager |
| `POST /pet`, `POST /store/order`, `POST /tag`, `GET /store/inventory`, `GET /user/{username}` | employee, manager |
| `DELETE /pet/{id}`, `DELETE /store/order/{orderId}`, `DELETE /tag/{id}`, `DELETE /user/{username}` | manager |
| everything else (including `PUT` routes) | any logged-in user |

## Tests

```bash
./mvnw test
```

Most tests mock the repositories. `PetStoreApplicationTests` loads the whole application, so it needs the MySQL connection above to work.

## Known gaps

I'd fix these before calling it production-ready:

- The registration request can set its own `role`, so anyone can sign up as a manager.
- User ids come from the client, and registering with an existing id overwrites that user.
- `PUT /user/{username}`, `PUT /pet` and `GET /store/all` have no role rule, so any logged-in user can call them.
- `GET /user/all` is meant for managers, but the `/user/{username}` rule is listed first and also matches it, so employees can call it too.
- User responses include the password hash.
- Error responses come back as 401 because `/error` isn't permitted, which hides real 403s and 500s.
- The JWT signing key is generated at startup, so tokens stop working after a restart.
- Database credentials are still in `application.properties` and belong in environment variables.
