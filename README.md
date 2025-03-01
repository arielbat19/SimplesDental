# Simples Dental API

Este projeto é uma API para gerenciamento de profissionais na área de saúde, utilizando Spring Boot.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 6.1.0**
- **Jakarta Persistence (JPA)**
- **PostgreSQL**
- **Lombok**
- **JUnit 5 para testes**

## 📂 Estrutura do Projeto

```
/src/main/java/com/teste/api/simples/dental
├── controllers      # Controladores REST
├── dtos            # Data Transfer Objects (DTOs)
├── entities        # Entidades do banco de dados
├── enums           # Enumerações utilizadas
├── repositories    # Interfaces de acesso ao banco de dados
├── services        # Regras de negócio e lógica da aplicação
├── mapper          # Conversores entre entidades e DTOs
└── SimplesDentalApplication.java  # Classe principal
```

## 🔧 Configuração do Ambiente

1. **Clone o repositório**
   ```sh
   git clone https://github.com/arielbat19/SimplesDental.git
   ```
2. **Acesse o diretório**
   ```sh
   cd NOME-DO-REPOSITORIO
   ```
3. **Configure o banco de dados** no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/simples_dental
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```
   
4. **Para subir o banco de dados com o docker-compose**
      ```sh
      docker-compose up -d
      ```
   **Para parar o banco de dados com o docker-compose**
      ```sh
      docker-compose down
      ```
   
5. **Execute o projeto**
   ```sh
   mvn spring-boot:run
   ```

## 🧪 Executando os Testes

Para rodar todos os testes unitarios, use:

   ```sh
   mvn test
   ```

## 📌 Endpoints Principais

### **Profissionais**

| Método   | Endpoint              | Descrição                            |
| -------- | --------------------- | ------------------------------------ |
| `GET`    | `/profissionais`      | Lista todos os profissionais         |
| `GET`    | `/profissionais/{id}` | Obtém um profissional por ID         |
| `POST`   | `/profissionais`      | Cria um novo profissional            |
| `PUT`    | `/profissionais/{id}` | Atualiza os dados de um profissional |
| `DELETE` | `/profissionais/{id}` | Remove um profissional               |


### **Contato**

| Método   | Endpoint         | Descrição                       |
| -------- | -----------------|---------------------------------|
| `GET`    | `/contatos`      | Busca todos os contatos         |
| `GET`    | `/contatos/{id}` | Obtém um contato por ID         |
| `POST`   | `/contatos`      | Cria um novo contato            |
| `PUT`    | `/contatos/{id}` | Atualiza os dados de um contato |
| `DELETE` | `/contatos/{id}` | Remove um contato               |
