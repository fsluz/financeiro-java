**Projeto de Finanças feito em Java**

Este é um projeto simples de gerenciamento de receitas e despesas utilizando Java, PostgreSQL e o padrão DAO (Data Access Object).

------
**📁 Estrutura de pacotes**

    model — Classes de modelo (Receita, Despesa, Categoria)

    dao — Classes DAO com operações CRUD

    db — Classe ConnectionFactory para conexão com o banco

    app — Classe Main para testar o projeto

**⚙️ Pré-requisitos**

    Java JDK 8 ou superior

    PostgreSQL instalado e com banco configurado

    Driver JDBC do PostgreSQL (postgresql-42.7.3.jar) em uma pasta lib/

**🛠️ Compilar o projeto**

    javac -cp ".;lib/postgresql-42.7.3.jar" app/Main.java db/ConnectionFactory.java dao/*.java model/*.java

**▶️ Executar o projeto**

    java -cp ".;lib/postgresql-42.7.3.jar" app.Main


**🧪 O que o projeto faz**

    Insere, lista, atualiza e exclui registros de receitas e despesas no banco de dados PostgreSQL

    Conecta-se ao banco usando ConnectionFactory

    Teste completo no método main()
    
 💸 Sistema de Controle de Finanças Pessoais

Este projeto possui uma **interface gráfica desenvolvida com Java Swing** e gráficos gerados com a **biblioteca JFreeChart**. Ele oferece uma visualização intuitiva e interativa das finanças pessoais.

---

## ✨ Funcionalidades da Interface

- ✅ **Cadastro de receitas e despesas**
- 🧾 **Tabela de lançamentos** com listagem detalhada (valor, categoria, data)
- 📊 **Gráfico de pizza**: visualiza a distribuição de gastos por categoria
- 📈 **Gráfico de linha**: exibe a evolução do saldo ao longo do tempo
- 💰 **Cálculo automático do saldo atual**
- 🎨 **Interface responsiva**, com cores suaves e layout amigável

---

## ⚙️ Requisitos para Execução

- ✅ Biblioteca **JFreeChart** adicionada ao classpath  
- ✅ Driver **JDBC do PostgreSQL**

---

Sinta-se à vontade para contribuir ou reportar melhorias!

    

