# Sprint4 API (Java 21 + Spring Boot + JWT)

API com autenticação JWT, gerenciamento de Persons e Investments.

---

## Como rodar em DEV (H2)

```bash
mvn spring-boot:run
```

✅ Roda em: `http://localhost:8080`  
✅ Usa banco H2 em memória  
✅ Swagger: `http://localhost:8080/swagger-ui.html`

---

## Como rodar em PROD (Oracle)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## Como rodar os testes

```bash
mvn test
```

---

# ENDPOINTS (Exemplos para Postman/Insomnia)

## AUTH

### Registrar  
**POST** `/api/v1/auth/register`
```json
{
  "username": "bruno",
  "password": "123456"
}
```

---

### Login  
**POST** `/api/v1/auth/login`
```json
{
  "username": "bruno",
  "password": "123456"
}
```

✅ Resposta exemplo:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

👉 Copiar o token e usar no header:
```
Authorization: Bearer <TOKEN>
```

---

## PERSONS

### Criar Person  
**POST** `/api/v1/persons`  
Headers:
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```
Body:
```json
{
  "nome": "Fulano da Silva",
  "telefone": "1199999-0000",
  "email": "fulano@mail.com",
  "perfil": "MEDIO",
  "nascimento": "2000-01-01T00:00:00"
}
```

---

### Listar Persons  
**GET** `/api/v1/persons`  
Headers:
```
Authorization: Bearer <TOKEN>
```

---

### Atualizar Person  
**PUT** `/api/v1/persons/{id}`  
Headers:
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```
Body:
```json
{
  "nome": "Fulano Atualizado",
  "telefone": "1188888-0000",
  "email": "fulano2@mail.com",
  "perfil": "ALTO",
  "nascimento": "1999-05-10T12:00:00"
}
```

---

### Deletar Person  
**DELETE** `/api/v1/persons/{id}`  
Headers:
```
Authorization: Bearer <TOKEN>
```

⚠ Necessita ROLE_ADMIN

---

## INVESTMENTS

### Criar Investment  
**POST** `/api/v1/investments`  
Headers:
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```
Body:
```json
{
  "nome": "Tesouro IPCA",
  "data": "2025-01-01T10:00:00",
  "valor": 1500.75
}
```

---

### Listar Investments  
**GET** `/api/v1/investments`  
Headers:
```
Authorization: Bearer <TOKEN>
```

---

### Atualizar Investment  
**PUT** `/api/v1/investments/{id}`  
Headers:
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```
Body:
```json
{
  "nome": "CDB Banco X",
  "data": "2025-03-15T09:30:00",
  "valor": 2000.00
}
```

---

### Deletar Investment  
**DELETE** `/api/v1/investments/{id}`  
Headers:
```
Authorization: Bearer <TOKEN>
```

---

## Swagger UI (Documentação interativa)

```
http://localhost:8080/swagger-ui.html
```

---

## Observações

- Sempre fazer login e usar o token nas rotas protegidas (Persons / Investments).
- Header esperado:
  ```
  Authorization: Bearer <TOKEN>
  ```
- DEV usa H2 automaticamente.
- PROD usa Oracle (configurado em `application-prod.properties`).

---

## Como gerar o .jar

```bash
mvn clean package
```

Resultado:
```
target/sprint4j-0.0.1-SNAPSHOT.jar
```

Rodar:
```bash
java -jar target/sprint4j-0.0.1-SNAPSHOT.jar
```

---

## Pronto!  
