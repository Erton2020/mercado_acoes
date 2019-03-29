# Mercado de Ações

PUC Minas - Pós graduação em Arquitetura de Sistemas Distribuídos
Disciplina de Arquitetura de Software na Plataforma Java EE

Trabalho final 

### Pré-requisitos

- Eclipse Photon ou superior;
- Java JDK 8 ou superior;
- Apache Maven versão 3.6.0 ou superior;
- Docker;
- Lombok;
- RabbitMQ;
- PostgreSQL;
- Conta de e-mail válida para configuração de remetente;

### Instalação e execução da aplicação em ambiente local

- Clonar o projeto em um diretório desejado;
- Importar o código fonte no Eclipse, selecionar a opção Existing Maven Projects;
- Editar o arquivo application.yml e informar preencher os campos abaixo:
	addressFrom: 
	personal: 
	password: 
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
