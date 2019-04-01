# Mercado de Ações

PUC Minas - Pós graduação em Arquitetura de Sistemas Distribuídos

Disciplina de Arquitetura de Software na Plataforma Java EE

Este projeto consiste no trabalho final de conclusão  da disciplina de de Arquitetura de Software na Plataforma Java EE. Trata-se de uma API Rest para gerenciamento de compra de ações por pessoas físicas.

### Pré-requisitos

- Eclipse Photon ou superior;
- Java JDK 8 ou superior;
- Apache Maven versão 3.6.0 ou superior;
- Lombok;
- Docker;
- RabbitMQ;
- PostgreSQL;
- Conta de e-mail válida para configuração de remetente;

### Instalação e execução da aplicação em ambiente local

- Clonar o projeto em um diretório desejado;
- Importar o código fonte no Eclipse, selecionar a opção Existing Maven Projects;
- Editar o arquivo application.yml e configurar os dados remetente para envio automatico de email pela aplicação:
	* addressFrom: Necessário informar uma conta de email google válida;
	* personal: Necessário informar um alias para a conta de email, exemplo: Sistema de Mercado de Ações;
	* password: Necessário informar a senha da conta de email;
- Na aba Project Explorer, clicar no projeto com o botão direito do mouse e selecionar a opção run ou debug, Java Aplicaton e selecionar o arquivo StockMarketApplication.java;

## Sobre o Mercado de Ações

### Requisitos do sistema

O sistema deverá tratar da compra de ações para pessoas físicas.
- Uma Empresa possui um número limitado de ações para serem vendidas;
- As Empresas podem emitir novas ações porém não podemos diminuir o número de ações atuais;
- Cada ação pode pertencer a somente um Comprador;
- Uma Ação deve possuir a informação de quando foi comprada e de qual seu valor inicial e atual, juntamente das informações do seu Comprador;
- Um Comprador pode possuir várias Ações;
- O sistema precisa tratar de forma assíncrona a compra e venda das Ações;
- Durante uma compra ou venda, seu Comprador antigo e o novo precisam receber um email com a informação adequada sobre a operação;

### Detalhamento de fluxos do sistema

### Sobre as tecnologias utilizadas

- Eclipse: https://www.eclipse.org/
- Java JDK:  https://www.oracle.com/technetwork/java/javase/overview/index.html
- Apache Maven: https://maven.apache.org/
- Lombok: https://projectlombok.org/
- Spring Framework: https://spring.io/projects/spring-framework
- Spring Boot: https://spring.io/projects/spring-boot
- Spring Data: https://spring.io/projects/spring-data
- JavaMail: https://www.oracle.com/technetwork/java/javamail/index.html
- Swagger: https://swagger.io/
- Docker: https://www.docker.com/
- RabbitMQ: https://www.rabbitmq.com/
- PostgreSQL: https://www.postgresql.org/

### Documentação da API

* A documentação da API gerada pelo Swagger está disponivel após start da aplicação no endereço:

```sh
http://localhost:8080/swagger-ui.html
```


