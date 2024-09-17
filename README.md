# Gerenciamento de Listas e Itens

## Descrição
Esta aplicação web permite aos usuários gerenciar listas e itens associados de forma eficiente. As funcionalidades incluem a criação de listas, adição e gerenciamento de itens, e a possibilidade de atribuir estados e prioridades aos itens.

## Funcionalidades Principais
1. **Criação de Listas**: O usuário pode criar listas de tarefas e adicionar itens a essas listas.
2. **Gerenciamento de Itens**: O usuário pode adicionar, editar, remover e alterar o estado de itens dentro de cada lista.
3. **Visualização e Filtragem**: As listas e itens podem ser visualizados com filtros, como título e estado.
4. **Prioridade de Itens**: Itens podem ser destacados com prioridade dentro de cada lista.

## Tecnologias Utilizadas
- Java com Spring Boot
- Spring Data JPA para persistência
- H2 ou SQL Server (escolha configurável) como banco de dados
- Maven para gerenciamento de dependências
- JUnit e Mockito para testes automatizados

## Requisitos do Sistema
- Java 11+ instalado
- Maven 3+ instalado
- Banco de dados configurado (H2 ou SQL Server)

## Configuração

Para configurar o banco de dados SQL Server, adicione as seguintes propriedades ao arquivo `application.properties` ou `application.yml`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=tarefas_db
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
```

## Executar a Aplicação

mvn spring-boot:run

## Endpoints Principais:

### Listas de Tarefas
- GET /tarefas - Retorna todas as listas de tarefas (com filtros opcionais por título).
- POST /tarefas - Cria uma nova lista de tarefas.
- PUT /tarefas/{id} - Atualiza uma lista de tarefas existente.
- DELETE /tarefas/{id} - Remove uma lista de tarefas pelo ID.

### Itens de Tarefas
- GET /tarefas/{tarefaId}/itens - Retorna todos os itens de uma lista de tarefas (com filtros opcionais por título e estado).
- POST /tarefas/{tarefaId}/itens - Adiciona um novo item à lista de tarefas.
- PUT /tarefas/itens/{itemId} - Atualiza um item de uma lista.
- DELETE /tarefas/itens/{itemId} - Remove um item de uma lista.

## Testes Automatizados

mvn test

## Considerações Finais
- Certifique-se de ajustar o banco de dados e credenciais de acordo com seu ambiente.
- O cache foi configurado para melhorar a performance com Spring Cache.
- Para personalizar as configurações da aplicação, edite os arquivos application.properties ou application.yml.
