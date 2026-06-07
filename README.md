# MeuMelhorAmigo

Sistema de cadastro e adoção de animais resgatados.

## Tecnologias
- Java 21
- Spring Boot 3.5.14
- Spring MVC + Thymeleaf
- Spring Security
- Spring Data JPA + Hibernate
- H2 Database (desenvolvimento)
- Padrão GoF: Observer

## Como rodar

### Pré-requisitos
- Java 21 instalado
- IntelliJ IDEA ou VS Code

### Passos
1. Clone o repositório: `git clone <url-do-repositorio>`
2. Abra a pasta no IntelliJ
3. Aguarde o Maven baixar as dependências
4. Rode o arquivo `MeumelhoramigoApplication.java`
5. Acesse `http://localhost:8080/login` no navegador

### Usuário admin padrão
- Email: `admin@meumelhoramigo.com`
- Senha: `admin123`

### Console do banco de dados
- Acesse `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:meumelhoramigobd`
- User: `sa`
- Senha: (vazio)

## Estrutura do projeto
- `modelo` — entidades JPA
- `repository` — acesso ao banco
- `service` — lógica de negócio
- `controller` — rotas HTTP
- `config` — configurações de segurança
- `events` — padrão Observer (GoF)