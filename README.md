
# **Backend - Sistema de Transações**

## **Descrição**
Este é o backend de um sistema de gerenciamento de transações financeiras. O sistema permite que usuários criem, atualizem, listem e excluam transações, além de gerenciar categorias personalizadas vinculadas às suas transações. Cada usuário possui suas próprias categorias e transações, garantindo isolamento de dados.

O sistema utiliza Spring Boot, JPA, Hibernate e MySQL como banco de dados relacional. A autenticação é feita com tokens JWT (JSON Web Token), garantindo segurança nas operações.

---

## **Funcionalidades Principais**

### 1. **Usuários**
- Cadastro e autenticação de usuários.
- Recuperação de informações do usuário pelo token JWT.

### 2. **Categorias**
- Criação de categorias personalizadas por usuário.
- Listagem de categorias associadas ao usuário.
- Atualização e exclusão de categorias.
- Cada categoria possui um código sequencial único por usuário.

### 3. **Transações**
- Criação de transações vinculadas a categorias.
- Listagem de transações associadas ao usuário.
- Atualização, exclusão e cancelamento de transações.
- Cada transação possui um código sequencial único por usuário.

---

## **Tecnologias Utilizadas**

- **Linguagem**: Java 17
- **Framework**: Spring Boot
- **Banco de Dados**: MySQL
- **Autenticação**: JWT (JSON Web Token)
- **ORM**: Hibernate (JPA)
- **Validação**: Bean Validation (`@NotBlank`, `@NotNull`, etc.)
- **Build Tool**: Maven

---

## **Configuração do Projeto**

### 1. **Pré-requisitos**
- JDK 17 instalado.
- Maven instalado.
- MySQL instalado e configurado.
- Um cliente REST como Postman ou Insomnia para testar os endpoints.

### 2. **Configuração do Banco de Dados**
Edite o arquivo `application.properties` (ou `application.yml`) para configurar a conexão com o banco de dados MySQL:

```properties
# Configurações do banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/wise_wallet?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3. **Executar o Projeto**
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/wisewallet-backend.git
   cd wisewallet-backend
   ```
2. Compile o projeto:
   ```bash
   mvn clean install
   ```
3. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

4. A API estará disponível em:
   ```
   http://localhost:8080
   ```

---

## **Endpoints**

### 1. **Autenticação**
#### Login
- **URL**: `POST /api/auth/login`
- **Descrição**: Autentica o usuário e retorna um token JWT.
- **Body**:
  ```json
  {
    "email": "usuario@example.com",
    "password": "senha123"
  }
  ```
- **Resposta**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ```

---

### 2. **Categorias**
#### Criar Categoria
- **URL**: `POST /api/categories`
- **Descrição**: Cria uma nova categoria para o usuário autenticado.
- **Headers**:
  ```
  Authorization: Bearer <token>
  ```
- **Body**:
  ```json
  {
    "name": "Alimentação"
  }
  ```
- **Resposta**:
  ```json
  {
    "message": "Categoria criada com sucesso!"
  }
  ```

#### Listar Categorias
- **URL**: `GET /api/categories`
- **Descrição**: Lista todas as categorias do usuário autenticado.
- **Headers**:
  ```
  Authorization: Bearer <token>
  ```
- **Resposta**:
  ```json
  [
    {
      "id": 1,
      "name": "Alimentação",
      "code": 1
    },
    {
      "id": 2,
      "name": "Transporte",
      "code": 2
    }
  ]
  ```

#### Atualizar Categoria
- **URL**: `PUT /api/categories/{id}`
- **Descrição**: Atualiza o nome de uma categoria específica.
- **Headers**:
  ```
  Authorization: Bearer <token>
  ```
- **Body**:
  ```json
  {
    "name": "Alimentação e Supermercado"
  }
  ```

#### Excluir Categoria
- **URL**: `DELETE /api/categories/{id}`
- **Descrição**: Exclui uma categoria específica.

---

### 3. **Transações**
#### Criar Transação
- **URL**: `POST /api/transactions`
- **Descrição**: Cria uma nova transação vinculada a uma categoria.
- **Headers**:
  ```
  Authorization: Bearer <token>
  ```
- **Body**:
  ```json
  {
    "description": "Compra de mercado",
    "recipient": "Supermercado XYZ",
    "categoryId": 1,
    "paymentType": "DINHEIRO",
    "type": "SAIDA",
    "stats": "PAGO",
    "date": "2023-10-15",
    "amount": 150.00
  }
  ```
- **Resposta**:
  ```json
  {
    "message": "Transação criada com sucesso!"
  }
  ```

#### Listar Transações
- **URL**: `GET /api/transactions`
- **Descrição**: Lista todas as transações do usuário autenticado.
- **Headers**:
  ```
  Authorization: Bearer <token>
  ```
- **Resposta**:
  ```json
  [
    {
      "id": 1,
      "description": "Compra de mercado",
      "recipient": "Supermercado XYZ",
      "categoryId": 1,
      "categoryName": "Alimentação",
      "paymentType": "DINHEIRO",
      "type": "SAIDA",
      "stats": "PAGO",
      "date": "2023-10-15",
      "amount": 150.00,
      "createdAt": "2023-10-15T10:00:00",
      "code": 1
    }
  ]
  ```

#### Atualizar Transação
- **URL**: `PUT /api/transactions/{id}`
- **Descrição**: Atualiza os detalhes de uma transação específica.

#### Cancelar Transação
- **URL**: `PATCH /api/transactions/{id}/cancel`
- **Descrição**: Altera o status da transação para "CANCELADO".

#### Pagar Transação
- **URL**: `PATCH /api/transactions/{id}/pay`
- **Descrição**: Altera o status da transação para "PAGO".

#### Excluir Transação
- **URL**: `DELETE /api/transactions/{id}`
- **Descrição**: Exclui uma transação específica.

---

## **Modelos de Dados**

### 1. **User**
- `id`: Identificador único do usuário.
- `name`: Nome do usuário.
- `email`: E-mail do usuário.
- `password`: Senha do usuário (criptografada).

### 2. **Category**
- `id`: Identificador único da categoria.
- `name`: Nome da categoria.
- `code`: Código sequencial único por usuário.
- `user`: Usuário ao qual a categoria pertence.

### 3. **Transaction**
- `id`: Identificador único da transação.
- `description`: Descrição da transação.
- `recipient`: Destinatário da transação.
- `category`: Categoria vinculada à transação.
- `paymentType`: Tipo de pagamento (`BOLETO`, `CREDITO`, `DEBITO`, `DINHEIRO`, `PIX`).
- `type`: Tipo de transação (`ENTRADA`, `SAIDA`).
- `stats`: Status da transação (`ATRASADO`, `CANCELADO`, `PAGO`, `PENDENTE`).
- `date`: Data da transação.
- `amount`: Valor da transação.
- `code`: Código sequencial único por usuário.
- `user`: Usuário ao qual a transação pertence.

---

## **Estrutura do Projeto**

```
src/
├── main/
│   ├── java/com/artTech/wisewallet/
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── CategoryController.java
│   │   │   ├── TransactionController.java
│   │   │   └── UserController.java
│   │   ├── dto/
│   │   │   ├── CategoryDTO.java
│   │   │   ├── TransactionDTO.java
│   │   │   ├── TransactionResponseDTO.java
│   │   │   ├── UserDTO.java
│   │   │   └── UserResponseDTO.java
│   │   ├── model/
│   │   │   ├── Category.java
│   │   │   ├── Transaction.java
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   ├── CategoryRepository.java
│   │   │   ├── TransactionRepository.java
│   │   │   └── UserRepository.java
│   │   ├── service/
│   │   │   ├── CategoryService.java
│   │   │   ├── TransactionService.java
│   │   │   └── UserService.java
│   │   └── WisewalletApplication.java
│   └── resources/
│       ├── application.properties
│       └── data.sql (opcional, para inicialização do banco)
└── test/
    └── java/com/artTech/wisewallet/
        └── WisewalletApplicationTests.java
```

---

## **Contribuição**
Se desejar contribuir com melhorias ou correções, sinta-se à vontade para abrir uma issue ou enviar um pull request.

