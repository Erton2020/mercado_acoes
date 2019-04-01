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
- Para executar os próximos passos é necessario possuir o Docker instalado:
	* Iniciar a imagem docker do RabbitMQ com o console Web, para isso executar o seguinte comando:

	```sh
	docker run -d --hostname rabbitmq --name rabbitmq-management -p 15672:15672 -p 5671:5671 -p 5672:5672 rabbitmq:management
	```
- No Eclipse, acessar a aba Project Explorer, clicar no projeto com o botão direito do mouse e selecionar a opção run ou debug, Java Aplicaton e selecionar o arquivo StockMarketApplication.java;

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

### Documentação da API

* A documentação da API gerada pelo Swagger está disponivel após start da aplicação no endereço:

```sh
http://localhost:8080/swagger-ui.html
```

### Detalhamento de fluxos do sistema

A API para o Mercado de Ações permite realizar operações de cadastro de empresa, investidores, disponibilização de uma ação de uma empresa para venda no mercado e compra e venda de ações por um investidor.
Para o cenário de compra e venda esta API restringiu-se ao escopo de simular a compra direta da ação pelo investidor junto a empresa proprietária da ação. Quando o mesmo desejar vender esta ação o mesmo deve emitir uma ordem de venda, onde
a ação será recomprada pelo valor corrente pela empresa proprietária.

Abaixo um exemplo de fluxo para simulação no cenário descrito acima

1. Cadastrar uma nova Empresa;
2. Cadastrar uma Acão para Empresa criada anteriormente;
3. Cadastro de um Investidor;
4. Consultar as ações a venda da Empresa criada no passo 1;
5. Cadastrar uma ordem de compra para ação criada no passo 2;
6. Verificar no email do investidor o recebimento de email informando o registro de uma solicitação de compra;
7. Verificar no email da empresa o recebimento de email informando o registro de uma solicitação de compra;
8. Verificar no email do investidor o recebimento de email confirmando a compra de ações;
9. Verificar no email da empresa o recebimento de email confirmando a venda de ações;
10. Consultar os investimentos do Investidor criado no passo 3;

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



