# Estoque de Livros — Trabalho II (Spring Boot + PostgreSQL)

Continuação do Trabalho I: mesma API de livros (campos `titulo`, `autor`, `categoria`, `preco`),
mesmas rotas CRUD e os mesmos três filtros combináveis, mas agora os dados ficam salvos no PostgreSQL.

## O que mudou em relação ao Trabalho I

| Arquivo | Mudança |
|---|---|
| `pom.xml` | Adicionadas as dependências **Spring Data JPA** e **PostgreSQL Driver** |
| `application.properties` | Configuração de `spring.datasource.url`, `username`, `password` e do JPA |
| `model/Livro.java` | Virou entidade JPA: `@Entity`, `@Table`, `@Id`, `@GeneratedValue(IDENTITY)` |
| `repository/LivroRepository.java` | **Novo.** Estende `JpaRepository<Livro, Long>` + método `filtrar` com `@Query` |
| `controller/LivroController.java` | A `List` em memória e o `AtomicLong` saíram; agora usa `save`, `findAll`, `findById` e `delete` |
| `EstoqueApplication.java` | Cadastra os 3 livros iniciais só se a tabela estiver vazia (antes ficavam no construtor do controller) |

## Conceitos

- **`@Entity`** — diz ao JPA que a classe representa uma tabela do banco.
- **`@Id`** — marca o campo que é a chave primária.
- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`** — o próprio PostgreSQL gera o ID
  (coluna auto incremento). Por isso o JSON de cadastro não precisa de `id`.
- **`JpaRepository<Livro, Long>`** — interface do Spring Data que já traz `save`, `findAll`,
  `findById`, `delete`, `count` etc. O Spring cria a implementação sozinho.
- **Filtros combináveis com `@Query`** — cada condição tem a forma `(:param IS NULL OR ...)`.
  Se o filtro não for enviado, o parâmetro chega `null` e a condição é ignorada; assim a mesma
  consulta funciona com nenhum, um, dois ou os três filtros.
- **`ddl-auto=update`** — o Hibernate cria a tabela `livros` na primeira execução e **não apaga**
  os dados ao reiniciar (diferente de `create` ou `create-drop`).

## Como rodar

1. Instale o PostgreSQL e crie o banco (pgAdmin ou psql):
   ```sql
   CREATE DATABASE estoque;
   ```
2. Em `src/main/resources/application.properties`, ajuste usuário e senha se não forem `postgres`/`1234`.
3. Rode a aplicação (`EstoqueApplication` na IDE, ou `./mvnw spring-boot:run`).
   A tabela `livros` é criada automaticamente.

## Rotas

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/livros` | Lista todos |
| GET | `/api/livros?autor=&categoria=&precoMaximo=` | Filtros (qualquer combinação) |
| GET | `/api/livros/{id}` | Busca por ID |
| POST | `/api/livros` | Cadastra (sem `id` no JSON) |
| PUT | `/api/livros/{id}` | Atualiza |
| DELETE | `/api/livros/{id}` | Exclui |

Exemplo de cadastro:
```json
{
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "categoria": "Romance",
  "preco": 39.90
}
```

## Roteiro da demonstração (para os prints)

Importe `postman/Estoque-Livros.postman_collection.json` no Postman.

1. **Cadastrar** — rode "1. Cadastrar livro". Resposta `201 Created` com o `id` gerado pelo banco
   (ex.: `4`). A coleção guarda esse ID na variável `{{id}}`. 📸
2. **Buscar** — rode "3. Buscar por ID" e confirme que o livro aparece. 📸
3. **Reiniciar a aplicação** — pare (botão Stop na IDE ou `Ctrl+C`) e rode de novo.
4. **Buscar de novo** — rode "3. Buscar por ID" / "2. Listar todos": o livro **continua lá**,
   provando que está salvo no PostgreSQL e não mais na memória. 📸
5. **Consulta SQL** — no pgAdmin (Query Tool no banco `estoque`) ou no psql:
   ```sql
   SELECT * FROM livros ORDER BY id;
   ```
   O mesmo registro aparece na tabela. 📸
6. Teste também os filtros (requisições 5 a 8), o PUT (4) e o DELETE (9).

As consultas SQL estão em `consultas.sql`.
