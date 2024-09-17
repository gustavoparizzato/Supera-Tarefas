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
