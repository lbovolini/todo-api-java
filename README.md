### Requerimentos
 - Maven
 - JDK 15
 - MongoDB

### Instruções de Autenticação

- Obs: O usuário deve estar registrado no sistema. Caso não, registre-se em http://localhost:8080/todo/register ou com o comando a seguir. 

```bash
curl --location --request POST 'http://localhost:8080/todo/register' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "lucas",
"password": "pass11word"
}'
```

##### 1. Envie uma requisição POST para a URL http://localhost:8080/todo/login contendo as credenciais do usuário (username, password) no corpo da requisição, o conteúdo deve, obrigatóriamente, estar no formato JSON (application/json)

Exemplo de credencial de usuário:

```json
{
  "username": "lucas",
  "password": "pass11word"
} 
```

 Exemplo de requisição:

```bash
curl -X POST "http://localhost:8080/todo/login" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"username\":\"user1\",\"password\":\"pass11word\"}"
```

##### 2. Feita a requisição e esta sendo bem sucedida, o servidor deve retornar uma resposta no seguinte formato:

```json
{
  "id": "5ff610e2f6727779c6e6e8b8",
  "username": "user1",
  "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTYxMDEyNzUxNCwiZXhwIjoxNjEyNzE5NTE0fQ.aHt_XxLUo94ZMQBPKcSfgfcz08VNS3BfgDcXqf4tlaWYt1Oyl5RWdm9PgmWLBOlNyg7L0XtOIz1u-tMVb9I_IQ",
  "expiration": "2021-02-07T14:38:34.398616856-03:00"
}
```

Tendo a resposta da requisição, a aplicação (front-end) deve pegar o conteúdo da chave "token" e, se necessário, remover o prefixo "Bearer ". Com isso, tem-se a chave de autenticação, o token.

##### 3. Tendo a chave de autenticação, o sistema (front-end) deve incluir o mesmo no cabeçalho da requisição de autorização (Autorization), precedido do prefixo "Bearer ", a cada requisição para uma rota que requer autenticação.

Exemplo de Cabeçalho de autenticação:

```
Authorization: Bearer  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTYxMDEyNzUxNCwiZXhwIjoxNjEyNzE5NTE0fQ.aHt_XxLUo94ZMQBPKcSfgfcz08VNS3BfgDcXqf4tlaWYt1Oyl5RWdm9PgmWLBOlNyg7L0XtOIz1u-tMVb9I_IQ
```

Exemplo de requisição para uma rota que requer autenticação:

```bash
curl --location --request GET 'http://localhost:8080/todo/api/v1/users/' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTYwOTk2MTcyMCwiZXhwIjoxNjEyNTUzNzIwfQ.oCdsONW1Tpi94MsYoSfwLv9aR7ktEbB8UXaUiNc1Ye4r3jq28xmDtaxQHRWs_jAztqLDGEtb0bmrBYtXXTBuTw' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "lucas",
"password": "pass11word"
}'
```

