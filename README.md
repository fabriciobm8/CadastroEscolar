# Cadastro Escolar - CRUD com Spring Boot, Hibernate, PostgreSQL e Docker

## Objetivo
Este projeto tem como objetivo desenvolver uma aplicação CRUD utilizando as tecnologias Spring Boot, Hibernate, PostgreSQL, Docker e Postman.
O sistema de cadastro escolar permite o gerenciamento de alunos, professores, disciplinas, turmas e notas. O foco principal é a criação, leitura, atualização e exclusão (CRUD) das entidades que compõem o sistema de cadastro escolar, mostrando o relacionamento das entidades.

## Tecnologias Utilizadas
- **Spring Boot**: Framework Java para desenvolvimento de aplicações web.
- **Hibernate**: Framework de mapeamento objeto-relacional (ORM) utilizado para interagir com o banco de dados.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar as informações.
- **Docker**: Plataforma de containerização usada para isolar e empacotar a aplicação e o banco de dados.
- **Docker Compose**: Ferramenta que facilita a definição e execução de aplicações multi-container.
- **Postman**: Ferramenta para teste de APIs.

## Funcionalidades
O sistema permite realizar operações CRUD para as seguintes entidades:

### 1. **Aluno**
- **Atributos**:
    - `id`: Identificador único do aluno (tipo `long`).
    - `nome`: Nome completo do aluno (tipo `String`).
    - `matricula`: Número de matrícula do aluno (tipo `String`).
    - `email`: E-mail de contato do aluno (tipo `String`).
    - `dataNascimento`: Data de nascimento do aluno (tipo `LocalDate`).
- **Relacionamento**: O aluno está associado a uma turma e pode ter várias notas.

### 2. **Professor**
- **Atributos**:
    - `id`: Identificador único do professor (tipo `long`).
    - `nome`: Nome completo do professor (tipo `String`).
    - `email`: E-mail de contato do professor (tipo `String`).
    - `disciplinaPrincipal`: Principal disciplina que o professor leciona (tipo `String`).
- **Relacionamento**: O professor pode ensinar várias disciplinas e pode estar associado a uma ou mais turmas.

### 3. **Disciplina**
- **Atributos**:
    - `id`: Identificador único da disciplina (tipo `long`).
    - `nome`: Nome da disciplina (tipo `String`).
    - `cargaHoraria`: Carga horária total da disciplina em horas (tipo `int`).
    - `professor`: Referência ao professor responsável pela disciplina (tipo `Professor`).
- **Relacionamento**: A disciplina é ensinada por um professor e possui várias notas relacionadas a alunos.

### 4. **Turma**
- **Atributos**:
    - `id`: Identificador único da turma (tipo `long`).
    - `nome`: Nome ou identificação da turma (tipo `String`).
    - `ano`: Ano letivo da turma (tipo `int`).
    - `alunos`: Lista de alunos matriculados na turma (tipo `List<Aluno>`).
    - `disciplinas`: Lista de disciplinas oferecidas na turma (tipo `List<Disciplina>`).
- **Relacionamento**: A turma possui vários alunos e várias disciplinas.

### 5. **Nota**
- **Atributos**:
    - `id`: Identificador único da nota (tipo `long`).
    - `aluno`: Referência ao aluno que recebeu a nota (tipo `Aluno`).
    - `disciplina`: Referência à disciplina na qual a nota foi atribuída (tipo `Disciplina`).
    - `valor`: Valor numérico da nota (ex: 0 a 10) (tipo `double`).
    - `dataAvaliacao`: Data em que a avaliação foi realizada (tipo `LocalDate`).
- **Relacionamento**: A nota registra a avaliação de um aluno em uma disciplina.

## Configuração do Banco de Dados e Docker

### PostgreSQL
A aplicação usa o PostgreSQL como banco de dados para persistência dos dados. A conexão com o banco é configurada através do arquivo `application.properties` da aplicação Spring Boot.

### Docker e Docker Compose
A aplicação e o banco de dados PostgreSQL são executados dentro de containers Docker utilizando o Docker Compose para orquestrar os containers.

#### Arquivo `docker-compose.yml`:
```yaml
version: '3.8'

services:
  java-api:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: java-api
    ports:
      - "8080:8080"
    depends_on:
      - postgres-db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/escola
      - SPRING_DATASOURCE_USERNAME=user
      - SPRING_DATASOURCE_PASSWORD=password
    networks:
      - app-network

  postgres-db:
    image: postgres:15
    container_name: postgres-db
    environment:
      - POSTGRES_DB=escola
      - POSTGRES_USER=user
      - POSTGRES_PASSWORD=password
    ports:
      - "5432:5432"
    networks:
      - app-network
    volumes:
      - postgres-data:/var/lib/postgresql/data

networks:
  app-network:
    driver: bridge

volumes:
  postgres-data:

````
#### Dockerfile para a aplicação Spring Boot:
```dockerfile
# Etapa 1: Build usando Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho para o build
WORKDIR /app

# Copia o código fonte para o contêiner
COPY . .

# Executa o Maven para gerar o .jar
RUN mvn clean package -DskipTests

# Etapa 2: Execução da aplicação
FROM eclipse-temurin:21-jdk-alpine

# Cria usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Define o diretório de trabalho para a aplicação
WORKDIR /app

# Copia o .jar gerado na etapa anterior
COPY --from=build /app/target/escola-0.0.1-SNAPSHOT.jar app-v1.jar

# Expondo a porta
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app-v1.jar"]

```
#### Dockerfile para o PostgreSQL (Dockerfile_db)
```dockerfile
# Use a imagem oficial do PostgreSQL 15
FROM postgres:15

# Defina as variáveis de ambiente para o usuário, senha e banco de dados
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_DB=escola
ENV POSTGRES_INITDB_ARGS="--data-checksums"

# Exponha a porta padrão do PostgreSQL
EXPOSE 5432

```

## Como Rodar a Aplicação
Certifique-se de ter o Docker e Docker Compose instalados na sua máquina.

Clone o repositório do projeto.

Construa e inicie os containers Docker utilizando o comando:

```bash
docker-compose up --build
```
A aplicação Spring Boot estará disponível na URL: **`http://localhost:8080`**.

O banco de dados PostgreSQL estará disponível na URL: **`jdbc:postgresql://localhost:5432/usuarios`**.

## Endpoints da API

### 1. **Aluno**
  - `GET /aluno/alunos`: Listar todos os alunos.
  - `POST /aluno`: Criar um novo aluno.
  - `GET /aluno/{id}`: Buscar um aluno pelo ID.
  - `GET /aluno/email/{email}`: Buscar um aluno pelo email.
  - `GET /aluno/matricula/{matricula}`: Buscar um aluno pela matricula.
  - `PUT /aluno/{id}`: Atualizar os dados de um aluno.
  - `DELETE /aluno/{id}`: Deletar um aluno.
### 2. **Professor**
  - `GET /professor/professors`: Listar todos os professores.
  - `POST /professor`: Criar um novo professor.
  - `GET /professor/{id}`: Buscar um professor pelo ID.
  - `PUT /professor/{id}`: Atualizar os dados de um professor.
  - `DELETE /professor/{id}`: Deletar um professor.
### 3. **Disciplina**
  - `GET /disciplina/disciplinas`: Listar todas as disciplinas.
  - `POST /disciplina`: Criar uma nova disciplina.
  - `GET /disciplina/{id}`: Buscar uma disciplina pelo ID.
  - `PUT /disciplina/{id}`: Atualizar os dados de uma disciplina.
  - `DELETE /disciplina/{id}`: Deletar uma disciplina.
### 4. **Turma**
  - `GET /turma/turmas`: Listar todas as turmas.
  - `POST /turma`: Criar uma nova turma.
  - `GET /turma/{id}`: Buscar uma turma pelo ID.
  - `PUT /turma/{id}`: Atualizar os dados de uma turma.
  - `DELETE /turma/{id}`: Deletar uma turma.
### 5. **Nota**
  - `GET /nota/notas`: Listar todas as notas.
  - `POST /nota`: Criar uma nova nota.
  - `GET /nota/{id}`: Buscar uma nota pelo ID.
  - `PUT /nota/{id}`: Atualizar os dados de uma nota.
  - `DELETE /nota/{id}`: Deletar uma nota.

## Arquivos JSON para testar a aplicação

### Exemplos de Requisições POST
### POSTMAN - Ferramenta para teste de APIs
1. **ALUNO**
  - **Endpoint:** `POST http://localhost:8080/aluno`
  - **Descrição:** Cria um novo aluno no sistema. O corpo da requisição deve incluir os dados do aluno, como nome, data de nascimento e outros detalhes relevantes.
  - **Body:** (raw / JSON)
   ```json
   {
       "nome": "Jose",
       "matricula": "202210021",
       "email": "jose@email.com",
       "dataNascimento": "2000-12-28"
   }
   ```
2. **PROFESSOR**
- **Endpoint:** `POST http://localhost:8080/professor`
- **Descrição:** Cria um novo professor no sistema. O corpo da requisição deve incluir os dados do professor, como nome, especialidade e outros detalhes relevantes.
- **Body:** (raw / JSON)
   ```json
   {
       "nome": "Rodrigo",
       "email": "rodrigo@email.com",
       "disciplinaPrincipal":"P.O.O.3"
   }
   ```
3. **DISCIPLINA**
- **Endpoint:** `POST http://localhost:8080/disciplina?professorId={id}`
- **Descrição:** Substitua `{id}` pelo ID do professor ao qual a disciplina será atribuída.
- **Body:** (raw / JSON)
   ```json
   {
       "nome": "P.O.O.3",
       "cargaHoraria": 60
   }
   ```
4. **TURMA**
  - **Endpoint:** `POST http://localhost:8080/turma`
  - **Descrição:** Cria uma nova turma. O corpo da requisição deve incluir o nome da turma, o ano letivo, e os IDs dos alunos e disciplinas associados.
  - **Body:** (raw / JSON)
   ```json
   {
       "nome": "4º Ano A",
       "ano": 2024,
       "alunoIds": [1],
       "disciplinaIds": [1]
   }
   ```
5. **NOTA**
  - **Endpoint:** `POST http://localhost:8080/nota`
  - **Descrição:** Adiciona uma nova nota para um aluno em uma disciplina específica. O corpo da requisição deve incluir o ID do aluno, o ID da disciplina, o valor da nota e a data da avaliação.
  - **Body:** (raw / JSON)
   ```json
   {
       "aluno": {
           "id": 1
       },
       "disciplina": {
           "id": 1
       },
       "valor": 9.0,
       "dataAvaliacao": "2024-03-20"
   }
   ```

## Conclusão:

Este projeto oferece uma API RESTful robusta para gerenciar alunos, professores, disciplinas, turmas e notas, proporcionando uma interface simples e eficiente para operações CRUD.