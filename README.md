# Kepos Backend

Eloah Baracho, João Raposo, Dayvid Dalton e Alberto Marques
## Sobre o Projeto
Backend da aplicação Kepos desenvolvido em Spring Boot.

## Especificações Técnicas

### Requisitos
- **Java**: 21 (LTS)
- **Spring Boot**: 3.4.0
- **Maven**: 3.6.3+

### Dependências Principais
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Validation
- H2 Database (desenvolvimento)
- Auth0 Java JWT 4.4.0

## Como Executar

### Pré-requisitos
Certifique-se de ter o Java 21 instalado:
```bash
java -version
```

### Compilação e Execução
```bash
# Compilar o projeto
./mvnw clean compile

# Executar testes
./mvnw test

# Executar a aplicação
./mvnw spring-boot:run
```

### Banco de Dados
O projeto utiliza H2 Database em memória para desenvolvimento. O console H2 está disponível em:
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:db_kepos
- Username: SA
- Password: (vazio)

## Histórico de Versões

### Upgrade para Java 21 (2025-10-11)
- Atualizado Java 17 → 21
- Atualizado Spring Boot 3.2.4 → 3.4.0
- Atualizado java-jwt 4.2.1 → 4.4.0
- Reconfigurado Maven Wrapper
- Todos os testes passando ✅
