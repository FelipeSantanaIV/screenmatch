# ScreenMatch

![ScreenMatch](https://github.com/FelipeSantanaIV/screenmatch/assets/35671903/4eb4ef2a-e661-446c-ba2c-d27a18bcb0f1)

## Introdução ao projeto

Projeto criado junto ao curso de formação da Alura "JAVA WEB: CRIE APLICAÇÕES USANDO SPRING BOOT". Este projeto consome a API https://www.omdbapi.com para popular o banco de dados PostgreSQL.

## Tecnologias usadas

[![My Skills](https://skillicons.dev/icons?i=java,spring,postgres)](https://skillicons.dev)

Java, Spring Framework, JPA, JPQL, PostgreSQL, Model View Controller (MVC), API REST

## End Points

Estes são os EndPoints utilizados no projetos:
- http://localhost:8080/series - Séries
- http://localhost:8080/series/top5 - Top 5 Séries
- http://localhost:8080/series/lancamentos - Obter últimos lançamentos
- http://localhost:8080/series/1 - Série por ID
- http://localhost:8080/series/1/temporadas/1 - Obter temporadas pelo número 
- http://localhost:8080/series/1/temporadas/todas - Obter todos os epísódios da Temporada
- http://localhost:8080/series/categoria/comédia - Procurar por categoria
- http://localhost:8080/series/1/temporadas/top - Top 5 Episódios

Todos são metódos GET, pode ser utilizado no PostMan, Insomnia.

## Projeto Front-End

Foi utilizado o projeto do Front-End disponiblizado pela propria Alura: https://github.com/alura-cursos/3356-java-web-front, se estiver indisponível, pode acessar também este link: https://github.com/FelipeSantanaIV/ScreenMatchFrontEnd
