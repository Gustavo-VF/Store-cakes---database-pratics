# 🎂 Loja de Bolos (Store-cakes)

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue?style=for-the-badge&logo=gradle)](https://gradle.org/)
[![SQL Server](https://img.shields.io/badge/SQL%20Server-2022-red?style=for-the-badge&logo=microsoft-sql-server)](https://www.microsoft.com/sql-server)
[![Docker](https://img.shields.io/badge/Docker-🐳-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)

Projeto prático desenvolvido para a disciplina de **Banco de Dados** do curso de **Tecnologia em Análise e
Desenvolvimento de Sistemas** da **FATEC**. O sistema consiste em uma aplicação desktop para gerenciamento e venda de
bolos, aplicando conceitos de Orientação a Objetos (POO), arquitetura MVC e persistência com o padrão DAO.

---

## 🛠️ Tecnologias e Ferramentas

* **Linguagem:** Java (versão 17 ou superior)
* **Gerenciador de Dependências:** Gradle (Kotlin DSL - `build.gradle.kts`)
* **Banco de Dados:** Microsoft SQL Server
* **Ambiente de BD:** Docker (opcional/recomendado)
* **Modelagem:** Visual Paradigm / Astah (arquivos `.vpp`)

---

## 🏗️ Arquitetura do Sistema

O projeto adota a arquitetura **MVC (Model-View-Controller)** para separação de responsabilidades, além de isolar a
camada de persistência através de interfaces **DAO (Data Access Object)**.

### Estrutura de Pastas Principal

```text
edu.fatec.poo
├── App.java                   # Classe principal (Ponto de entrada do sistema)
├── Contexto.java              # Gerenciamento de estado/sessão global da aplicação
├── model                      # Entidades de negócio (Cliente, Produto, Pedido, etc.)
├── view                       # Interfaces gráficas da aplicação
├── controller                 # Regras de negócio e mediação entre View e Model
└── persistence                # Camada de banco de dados
    ├── connection             # Classes e interfaces de conexão (Abstrações)
    ├── daoInterfaces          # Contratos (Interfaces) para operações CRUD
    └── sqlServer              # Implementação específica para SQL Server
        ├── create             # Scripts automatizados de criação de DB e Tabelas
        └── daoImplementations # Implementações concretas dos DAOs SQL

```

---

## 💾 Persistência e Inicialização Automática

O sistema conta com um mecanismo de **bootstrap** automatizado para o banco de dados. Ao iniciar a aplicação através da
classe `ConfiguredSqlConnector`, o sistema:

1. Conecta-se ao banco nativo `master` do SQL Server.
2. Verifica e cria o banco de dados principal (`store_cakes`) caso ele não exista.
3. Cria todas as tabelas necessárias de forma automatizada (`createTableAll`).

> ⚠️ **Nota de Configuração:** Por padrão, as credenciais configuradas em `ConfiguredSqlConnector.java` são:
> * **Host/Porta:** `localhost:1433`
> * **User:** `sa`
> * **Password:** `Origami123!`
>
>

---

### 🐳 Uso com Docker (Recomendado)

Para facilitar o ambiente de desenvolvimento e evitar a necessidade de instalar o SQL Server nativamente na sua máquina,
você pode subir o banco de dados rapidamente utilizando o **Docker**.

Certifique-se de que o Docker está instalado e execute o seguinte comando no terminal para iniciar o container já
configurado com as credenciais padrão do projeto:

```bash
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=Origami123!" \
   -p 1433:1433 --name mssql-store-cakes \
   -d [mcr.microsoft.com/mssql/server:2022-latest](https://mcr.microsoft.com/mssql/server:2022-latest)

```

*O sistema se encarregará de criar o banco de dados `store_cakes` e todas as tabelas estruturais assim que for executado
pela primeira vez com o container ativo.*

---

## 📂 Documentação do Projeto

Toda a fundamentação teórica, levantamento de requisitos e engenharia de software estão centralizados no diretório
`/documentacao`. Você pode encontrar:

* **Requisitos:** Matriz de Rastreabilidade, Diagrama de Casos de Uso e Especificação de Casos de Uso (
  `DescUseCases.docx`).
* **Modelagem do Sistema:** Diagrama de Classes, Diagramas de Sequência (DS) de fluxos críticos (Login, Compras,
  Carrinho) e Diagrama de Transição de Estados.
* **Modelagem de Dados:** Diagrama Entidade-Relacionamento (DER) oficial do banco de dados.
* **Acesso Remoto:** Caso prefira, a documentação completa também está disponível
  no [Google Drive](https://drive.google.com/file/d/1mJSYd5J9cR1Ya2h4zYrj5MSOTzprL6ee/view?usp=sharing).

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

1. Possuir o **Java 17+** instalado e configurado nas variáveis de ambiente.
2. Possuir uma instância do **SQL Server** rodando localmente (seja via instalação nativa ou via o comando Docker
   acima).

### Passos para Execução

1. Clone o repositório em sua máquina local:

```bash
git clone [https://github.com/seu-usuario/Store-cakes---database-pratics.git](https://github.com/seu-usuario/Store-cakes---database-pratics.git)
cd Store-cakes---database-pratics

```

2. Compile o projeto utilizando o wrapper do Gradle:

```bash
./gradlew build

```

3. Execute a aplicação:

```bash
./gradlew run

```

---

## 👥 Desenvolvedores / Integrantes

* **FATEC - Faculdade de Tecnologia**
* Disciplina: Banco de Dados & Programação Orientada a Objetos
