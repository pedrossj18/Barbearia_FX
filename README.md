# 💈 Sistema de Barbearia - Java + MySQL + JavaFX

Sistema de gestão para barbearias desenvolvido em **Java 17**, interface gráfica construída com **JavaFX** e **Scene Builder**, e persistência de dados em **MySQL** com JDBC.

---

## Funcionalidades

- 🔐 Login com autenticação de usuário
- 👨‍🦰 Cadastro, listagem e exclusão de **Clientes**
- ✂️ Cadastro, listagem e exclusão de **Barbeiros**
- 🗓️ Cadastro e controle de **Agendamentos**
- 🔍 Filtros de pesquisa por nome de cliente e barbeiro
- 📅 Exibição de agendamentos futuros
- ✅ Confirmação segura antes de exclusões
- 💾 Integração total com banco de dados MySQL
- 🎨 Interface gráfica construída com **Scene Builder**

---

## Tecnologias Utilizadas

- Java 17
- Maven
- JavaFX 21 (FXML)
- Scene Builder (interface visual)
- MySQL 8.x
- JDBC (MySQL Connector/J)

---

## Telas Desenvolvidas com Scene Builder

- Tela de Login
- Tela Principal com menu (Clientes, Barbeiros, Agendamentos)
- Formulários de cadastro e edição
- Listagens em `TableView`
- Alertas de confirmação e mensagens de erro

> As telas são desenvolvidas com **FXML** e controladas por classes `Controller` Java integradas via JavaFX.

---

## Funcionamento do Banco 

---

## 🔌 Conexão com o Banco de Dados

Arquivo: `Conexao.java`

private static final String URL = "jdbc:mysql://localhost:3306/barbearia_db";
private static final String USUARIO = "root";
private static final String SENHA = "SUA_SENHA_AQUI";

---

🛠️ Requisitos
JDK 17 ou superior

MySQL Server (localhost:3306)

Scene Builder instalado

IDE com suporte JavaFX (Escrito no Eclipse)

---
Script SQL (Criação do Banco de Dados)

CREATE DATABASE IF NOT EXISTS barbearia_db;
USE barbearia_db;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    nome_barbearia VARCHAR(100)
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE barbeiros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    especialidade VARCHAR(100),
    telefone VARCHAR(20)
);

CREATE TABLE agendamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    barbeiro_id INT,
    servico VARCHAR(100),
    observacoes TEXT,
    data_hora DATETIME,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (barbeiro_id) REFERENCES barbeiros(id)
);

-- Usuário de teste
INSERT INTO usuarios (login, senha, nome_barbearia)
VALUES ('admin', 'admin', 'Barbearia do Teste');


Em produção...

