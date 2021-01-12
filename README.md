### Descrição

API para criação de lista de afazeres (todo list)

- Maven
- JDK 11
- MongoDB
- Spring Boot
- JJWT
- Amazon S3
- Swagger UI
- Mockito

##### Endereço: http://ec2-52-67-246-130.sa-east-1.compute.amazonaws.com:8080/todo/

### Instruções

Para executar a aplicação localmente, primeiramente faça o clone do repositório.

```
git clone https://gitlab.com/lbovolini/todo.git
```

Acesse o repositório:

```bash
cd todo
```

Execute o docker compose:

```
docker-compose up
```

A aplicação estará disponível localmente no seguinte endereço:

http://localhost:8080/todo/

Acesse a documentação para mais detalhes.

### Documentação

Utilize o seguinte link para acessar a documentação localmente:

[Documentação](http://localhost:8080/todo/swagger-ui/index.html?url=http://localhost:8080/todo/resources/docs/1.0.0/swagger/todo-1.0.0-swagger.json)

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

### Banco de dados

##### Usuário (user)

- Um usuário (user) deve possuir um identificador único (id), um nome de usuário único (username) e deve possuir uma senha (password) com no 
  - O nome de usuário pode conter somente letras ou número, com no mínimo 3 e no máximo 20 caracteres. 
    - Regex: ``` "^(\\w){3,20}\\b"```
  - A senha deve possuir no mínimo 8 caracteres, com no mínimo 1 letra e 1 número. 
    - Regex:  ```"^(?=.*[\\d])(?=.*[a-z])[\\w!@#$%^&*()-=+,.;:]{8,}$"```
  
- Campos:
  - id
  - username
  - password
  - authorities
  - accountNonLocked
  
##### Lista de afazer (todo)

- Uma lista de afazeres (todo) deve possuir um identificador único (id) e o id do usuário (userId) a quem pertence 
- Uma lista de afazeres (todo) deve pertencer a um e somente um usuário
- Uma lista de afazeres (todo) pode pussuir nenhuma (0) ou muitas (n) tarefas (tasks)
- Uma lista de afazeres (todo) pode possuir nenhum (0) ou muitos (n) anexos (attachments)

- Campos:
  - id
  - userId
  - tasks
  - attachments

##### Tarefa (task)

- Uma tarefa (task) deve possuir um nome (name), uma descrição (description) e uma data (date)

- Campos:
  - name
  - description
  - date

##### Anexo (attachment)

- Um anexo (attachment) deve possuir um caminho (path)
- Um anexo (attachment) pode ser de qualquer tipo de extensão
- Um anexo (attachment) deve possuir um tamanho inferior a ?

- Campos:
  - name
  - path
  - type
  - extension
  - size
