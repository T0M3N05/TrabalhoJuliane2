-- 1) Criar o banco (rodar uma vez, conectado ao banco "postgres")
CREATE DATABASE estoque;

-- 2) Depois de rodar a aplicação, conectar no banco "estoque" e consultar:
SELECT * FROM livros ORDER BY id;

-- Buscar um registro específico (troque o 4 pelo ID que o Postman retornou)
SELECT * FROM livros WHERE id = 4;

-- Mesmo filtro combinado que a API faz em
-- GET /api/livros?autor=J.R.R. Tolkien&categoria=Fantasia&precoMaximo=70
SELECT * FROM livros
WHERE LOWER(autor) = LOWER('J.R.R. Tolkien')
  AND LOWER(categoria) = LOWER('Fantasia')
  AND preco <= 70;
