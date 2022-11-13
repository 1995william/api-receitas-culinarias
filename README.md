# Api receitas culinárias

![Java](https://img.shields.io/badge/OpenJDK-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring-Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Postgresql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)
![JUnit](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Swagger](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)

## Introdução

Projeto receitas culinárias com 1179 receitas cadastras, utilizando Spring Boot versão 2.7.5 e Java 17.

## Pré-requitos

 - Docker
 - Java 11
 - Intellij IDEA

## Docker

As configurações para utilizar o banco de dados Postgres estão no arquivo [docker-compose.yml](docker-compose.yml) que está na raiz do projeto.

1. No mesmo diretório do `docker-compose.yml`, digite:

- No Windows
```
wsl POSTGRES_USER=postgres POSTGRES_PASSWORD=admin docker compose up -d
```

- Linux
```
POSTGRES_USER=postgres POSTGRES_PASSWORD=admin docker compose up -d
```

## Variáveis de ambiente

Para editar as variáveis de ambiente no Intellij. 
1. Basta ir na classe [ReceitasCulinariasApplication](./src/main/java/br/com/receitasculinarias/ReceitasCulinariasApplication.java).
2. Clique com botão direito do mouse na classe acesse opção `Modify Run Configuration...`
3. Vá até `Environment variables`
4. Adicione os valores `Name` e `Value`
```
Name                    Value
POSTGRES_URL            jdbc:postgresql://localhost:5432/culinary_recipes
POSTGRES_USER           postgres
POSTGRES_PASSWORD       admin
```
5. Clique em `Ok` para salvar as variavéis.
6. Execute o projeto com botão direito do mouse na mesma classe anterior e vá na opção `Run`.

## Testes

Aplicação possui testes unitários Junit5 e Mockito com foco em testar service.

## Documentação Swagger

Para acessar o swagger clique [aqui](http://localhost:8080/swagger-ui/index.html).