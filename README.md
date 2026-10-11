# Baozi Store API

API REST em Java 21 com Spring Boot, Spring Data JPA e banco relacional H2 em memória. O projeto está organizado nos pacotes `model`, `repository` e `controller`.

## Executar

Na pasta do projeto, execute:

```bash
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`. O H2 em memória é recriado quando a aplicação é reiniciada. Console do banco: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:baozistore`, usuário `sa`, senha em branco).

### Abrir no VS Code

Abra a pasta raiz do projeto, a mesma que contém `pom.xml`. O workspace recomenda as extensões Extension Pack for Java e Maven for Java, importa as dependências do Maven automaticamente e formata arquivos Java ao salvar. Se o editor ainda mostrar diagnósticos antigos, execute `Java: Reload Projects` pela paleta de comandos (`Ctrl+Shift+P`).

## Endpoints

| Recurso | Criar | Listar | Consultar por ID | Excluir |
| --- | --- | --- | --- | --- |
| Clientes | `POST /api/clientes` | `GET /api/clientes` | `GET /api/clientes/{id}` | `DELETE /api/clientes/{id}` |
| Produtos | `POST /api/produtos` | `GET /api/produtos` | `GET /api/produtos/{id}` | `DELETE /api/produtos/{id}` |
| Pedidos | `POST /api/pedidos` | `GET /api/pedidos` | `GET /api/pedidos/{id}` | `DELETE /api/pedidos/{id}` |

As criações retornam HTTP `201`; exclusões bem-sucedidas retornam `204`; recursos ou referências inexistentes retornam `404`. Cliente ou produto associado a um pedido não pode ser excluído e retorna `409`. Campos obrigatórios e quantidades/preços inválidos são rejeitados pela validação.

## Exemplos de JSON

Criar cliente:

```json
{
  "nome": "Ana Baozi",
  "clienteDesde": "2026-10-11"
}
```

Criar produto:

```json
{
  "nome": "Paozinho chines",
  "preco": 8.50,
  "estoque": true
}
```

Criar pedido (use IDs existentes):

```json
{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 3
}
```

## Testar pelo Postman

Importe `postman/Baozi-Store-API.postman_collection.json` no Postman e execute as requisições de criação antes das consultas de pedido. A coleção salva automaticamente os IDs retornados durante a execução. Para atender à entrega da atividade, execute as requisições e capture no Postman as telas de criação, listagem geral e consulta por ID; as capturas devem ser anexadas separadamente à entrega.

## Diagrama de casos de uso UML

O diagrama das operações implementadas está em [`docs/diagrama-casos-de-uso.puml`](docs/diagrama-casos-de-uso.puml). Abra o arquivo com uma extensão PlantUML no VS Code ou cole o conteúdo em um renderizador PlantUML para visualizar ou exportar o diagrama. A API implementa criação, listagem, consulta por ID e exclusão para clientes, produtos e pedidos; atualização (PUT/PATCH) ainda não está implementada.
