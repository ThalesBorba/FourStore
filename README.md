# FourStore

<h2 align="center">FourStore em construção... 🚧</h2>

<h2 align="center">FourStore 👚</h2>
<br>
<p align="center">
 <a href="#-sobre-o-projeto">Sobre</a> •
 <a href="#features">Features</a> •  
 <a href="#Ferramentas">Ferramentas</a> •
 <a href="#To-do">To-do</a> •
 <a href="#Observações">Observações</a>

## ✅ Sobre

- [ ] Api de cadastro de clientes e produtos, usada majoritariamente para praticar técnicas e ferramentas 
que vou achando interessantes

## ✅ Features

- [ ] Cadastrar Estoque de Produtos, Clientes e Transações
- [ ] Listar os acima
- [ ] Validar Venda e Sku
- [ ] Verificar disponibilidade no estoque.
- [ ] Incluir e validar métodos de pagamento
- [ ] Cadastrar Novos Produtos.

## ✅ Ferramentas

- [ ] Exceções personalizadas e centralizadas com ResourceException Handler
- [ ] Testes unitários com Mockito
- [ ] Customização de retornos usando ResponseEntity
- [ ] Programação funcional
- [ ] Dtos para melhorar legibilidade, manuseabilidade e segurança
- [ ] Swagger também para fácil manuseabilidade
- [ ] Banco Relacional MariaDB com jpa
- [ ] Lombok para legibilidade
- [ ] Seguindo princípios Clean Code e SOLID
- [ ] Dotenv para extrair configurações sensíveis

## ✅ To-do

- [ ] Implementar security com JWT

## ✅ Observações

- [ ] Para testar com um banco diferente do MariaDB, é necessário adicionar a depeência no POM.xml e as configurações
necessárias em resources -> application.properties
- [ ] Caso deseje usar o MariaDB basta adicionar um arquivo .env (não coloque nada antes do ponto) com as seguintes
informações:

DB_HOST= {o ip do servidor, ou "localhost" caso seja local}

DB_PORT= {porta}

DB_NAME= {nome da sua tabela}

DB_USER= {usuário}

DB_PASSWORD= {senha}
<br>
