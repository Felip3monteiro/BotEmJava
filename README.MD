# 🚧 Importante

**⚠️ Projeto em Desenvolvimento.**

👀 Fiz este bot como parte de um desafio pessoal e para uma **entrevista de emprego**. Também vi isso como uma oportunidade para me aprofundar no aprendizado de Java.

### 🛠️ Contexto
- Este projeto foi implementado utilizando **Java 8**.
- Encontrei alguns desafios durante o desenvolvimento:
    - O método `lerBytes` da classe `CsScrapping` apresentou problemas. Essa era minha ideia principal mas infelizmente não deu certo.
      - Por limitações, resolvi utilizar uma **IA** para gerar as informações necessárias, que depois revisei e validei manualmente.

⬆️ **Minha missão futura neste projeto**:
1. Acrescentar diversas funcionalidades novas. 🚀
2. Refatorar partes do código para torná-lo mais eficiente e escalável. 🧹
3. Mudar várias coisas para alcançar meus objetivos e aperfeiçoar o bot. 🔧

---

# 📝 **Fiquem Atentos**
As melhores mudanças estão por vir...  
**_Aguardem!_** ✨



# 🤖 FuriaBot

FuriaBot é um chatbot interativo desenvolvido para oferecer funcionalidades como notícias, próximos jogos, quizzes e links úteis. Ele é projetado para engajar usuários de forma simples e intuitiva, utilizando a biblioteca Telegram Bots.

---

## ✨ Funcionalidades

O FuriaBot possui as seguintes funcionalidades principais:

1. 📰 **Exibição de Notícias**: Mostra as últimas notícias relevantes relacionadas ao contexto do bot.
2. 🎮 **Próximos Jogos**: Lista informações sobre próximos jogos e eventos.
3. ❓ **Quiz Interativo**: Um quiz divertido que apresenta perguntas dinâmicas para os usuários responderem:
   - Encerra com base em um limite de perguntas ou número de respostas erradas.
4. 🔗 **Links Úteis**: Oferece atalhos para recursos externos, como a loja oficial, Instagram e Twitter.
5. 🛒 **Loja Oficial**: Botão dedicado para redirecionar usuários à loja da organização.

---

## 🛠️ Estrutura do Projeto

A estrutura básica do projeto está organizada da seguinte forma:

- **`menus`**: 📋 Contém classes responsáveis por criar os menus principais (ex.: `MenuPrincipal`), que constrói o layout do menu do chatbot.
- **`quiz`**: 🤓 Inclui classes relacionadas à lógica do quiz (ex.: `QuizSession`), que gerencia sessões de perguntas para os usuários responderem.
- **`util`**: ⚙️ Contém classes utilitárias como `Constantes`, com textos, mensagens e URLs usados no bot.

---

## 💻 Tecnologias Utilizadas

- **Linguagem**: Java ☕
- **Telegram Bots API**: Para interação com a API do Telegram 🤖
- **JSON**: Para manipulação de perguntas e outros dados 📂
- **Design Modular**: Estrutura organizada para facilitar manutenção e escalabilidade 🚀

---

## 🚀 Instalação e Configuração

1. Clone o repositório para sua máquina local:
   ```bash
   git clone https://github.com/seu-usuario/furiabot.git
   cd furiabot
   ```

2. Certifique-se de ter o **Java JDK 11+** instalado.

3. Adicione a dependência da biblioteca Telegram Bots no seu projeto. Caso esteja usando Maven, inclua no arquivo `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.telegram</groupId>
       <artifactId>telegrambots</artifactId>
       <version>SUA_VERSAO</version>
   </dependency>
   ```

4. Configure o arquivo de token do bot:
   - Crie um arquivo de configuração para armazenar o token do Telegram fornecido pelo BotFather.

5. Execute o projeto e interaja com o FuriaBot através do Telegram! 🥳

---

## 🎯 Como Funciona

1. **Mensagem Inicial**:
   Quando o bot é iniciado, o usuário é recebido com a seguinte mensagem:
   ```
   Olá! Eu sou o FuriaBot, escolha uma opção:
   ```

2. **Menu Principal**:
   Apresenta botões como:
   - 📰 Notícias
   - 🎮 Próximos Jogos
   - ❓ Quiz
   - 🔗 Links Úteis
   - 🛒 Loja

3. **Fluxo de Interação**:
   - **Notícias/Jogos**: Exibe informações atualizadas.
   - **Quiz**: Inicia um quiz com perguntas dinâmicas (encerra após 5 perguntas ou 2 erros).
   - **Links Úteis/Loja**: Redireciona o usuário para URLs externas relevantes.

---

## 🤔 Estrutura Interna do Quiz

- A sessão do quiz é gerenciada pela classe **`QuizSession`**, que acompanha:
   - A pergunta atual sendo exibida.
   - O número de perguntas respondidas pelo usuário.
   - Contagem de erros cometidos.
   - Condição de encerramento ao atingir 5 perguntas ou 2 erros.

---

## 📢 Importante!

> **⚠️ Projeto em Desenvolvimento.**

Fiz esse bot como parte de um processo seletivo de **entrevista de emprego** e planejo realizar muitas mudanças 🔧:
- **Trocar o nome do bot.**
- **Incluir diversas funcionalidades novas.**
- **Refatorar grande parte do código.**

Acompanhe o progresso e **_aguardem as novidades!_** 🎉👀

---

## 🤝 Contribuindo

Adoraríamos sua colaboração com o projeto! Para contribuir:

1. Faça um fork deste repositório.
2. Crie um branch com sua funcionalidade:
   ```bash
   git checkout -b minha-nova-funcionalidade
   ```
3. Após implementar as alterações, envie um pull request detalhando-as.

---

## 👨‍💻 Autor

Desenvolvido por **[Felipe Monteiro Silveira](https://github.com/Felip3monteiro)**.

---

## 📜 Licença

Este projeto está licenciado sob a licença **MIT**. Consulte o arquivo `LICENSE` para mais detalhes.
