# hotchat

Chat com WebSocket

**Importar a aplicação**

Ter o maven instalado na máquina

Instalar o Lombok na IDE a ser utilizada. No caso, foi utilizado o Eclipse. Abaixo os passos para instalação:

- Na pasta .m2, navegar até a pasta do projeto Lombok (org.projectlombok.lombok.VERSION)
- Seguir o tutorial deste [link](https://projectlombok.org/setup/eclipse)

Importar o hotchat como projeto maven

**Configurar o banco de dados**

O banco de dados utilizado na aplicação foi o MySQL. Deverá atualizar as informações de login no seguinte arquivo, que se encontra na pasta:

src/main/resources/application.properties

Criar o banco de dados **hotchat** (create database hotchat). As tabelas do banco serão criadas automaticamente ao iniciar a aplicação.

Não há necessidade de configuração de banco de dados para os testes unintários.

**Executar a aplicação**

Na pasta do projeto, executar o seguinte comando

mvn spring-boot:run

**URLs**

Acesso a aplicação:

http://localhost:8080/

Acesso ao swagger (necessário estar logado)

http://localhost:8080/swagger-ui.html
