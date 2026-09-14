# API Cursos - Atividade Prática Integrada

API REST desenvolvida com Spring Boot, evoluindo ao longo das Aulas 01 a 05 da disciplina Back-End Frameworks: da criação do projeto até a persistência de dados em PostgreSQL.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven

## Fluxo Cliente-Servidor (GET /cursos)

1. **Cliente**: o navegador, app ou Postman que faz a solicitação.
2. **Requisição HTTP**: o cliente envia `GET /cursos` pedindo a lista de cursos.
3. **Back-end (servidor)**: recebe a requisição, processa (Controller chama o Service, que consulta o Repository).
4. **Resposta HTTP**: o servidor devolve os dados em JSON com status `200 OK`.
5. **Cliente**: recebe e exibe a lista de cursos.

`Cliente -> requisição HTTP -> back-end -> resposta HTTP -> cliente`

## Responsabilidades do back-end ao cadastrar um curso

1. Receber os dados enviados pelo cliente (JSON no body da requisição)
2. Transformar o JSON em um objeto Java (`Curso`), via `@RequestBody`
3. Verificar as regras de negócio (nome obrigatório, carga horária > 0)
4. Salvar o curso (Repository)
5. Devolver uma resposta ao cliente (o curso criado + status 201)

## Estrutura do projeto

```
src/main/java/br/edu/nassau/api_cursos/
├── controller/
│   └── CursoController.java
├── service/
│   └── CursoService.java
├── repository/
│   └── CursoRepository.java
└── model/
    └── Curso.java
```


## Endpoints da API

| Método | Endpoint | Operação | Resposta |
|---|---|---|---|
| GET | `/cursos` | Lista todos os cursos | 200 OK |
| GET | `/cursos/{id}` | Busca um curso pelo ID | 200 OK ou 404 Not Found |
| POST | `/cursos` | Cadastra um curso | 201 Created |
| PUT | `/cursos/{id}` | Atualiza um curso | 200 OK ou 404 Not Found |
| DELETE | `/cursos/{id}` | Remove um curso | 204 No Content ou 404 Not Found |

### Exemplo de JSON para cadastro

```json
{
  "nome": "Back-End Frameworks",
  "cargaHoraria": 60
}
```

## Regras de negócio

- `nome` não pode ser nulo nem ficar em branco
- `cargaHoraria` deve ser maior que zero
- O identificador (`id`) é gerado automaticamente pelo banco de dados
- O Controller não acessa diretamente o banco — todo acesso passa pelo Service e pelo Repository

## Arquitetura em camadas


- **Controller**: recebe a requisição HTTP, delega ao Service e devolve a resposta. Não contém regra de negócio.
- **Service**: valida os dados e aplica as regras de negócio antes de acessar o Repository.
- **Repository**: interface `JpaRepository`, responsável pelo acesso aos dados no PostgreSQL.
- **Model**: representa a entidade `Curso`, mapeada como tabela no banco via JPA (`@Entity`).

## Persistência de dados

O projeto usa Spring Data JPA com PostgreSQL. A tabela `curso` é criada e atualizada automaticamente (`spring.jpa.hibernate.ddl-auto=update`).

Persistência comprovada: cursos cadastrados via `POST /cursos` permanecem no banco mesmo após reiniciar a aplicação — validado via consulta direta na tabela pelo pgAdmin e via `GET /cursos` após reinício.

## Como executar

1. Clone o repositório
2. Crie o banco de dados no PostgreSQL: `CREATE DATABASE api_cursos;`
3. Configure a variável de ambiente `DB_PASSWORD` com a senha do seu usuário PostgreSQL
4. Abra o projeto no IntelliJ IDEA
5. Execute a classe `ApiCursosApplication`
6. Teste os endpoints em `http://localhost:8080/cursos` (Postman, Insomnia ou navegador para GET)

## Segurança

A senha do banco de dados não é armazenada no código-fonte. Ela é lida através da variável de ambiente `DB_PASSWORD`, configurada localmente em cada máquina (`spring.datasource.password=${DB_PASSWORD}`).