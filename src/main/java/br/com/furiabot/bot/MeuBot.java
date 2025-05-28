package br.com.furiabot.bot;


import br.com.furiabot.jogos.CsScrapping;
import br.com.furiabot.noticias.Noticias;
import br.com.furiabot.menus.MenuLinks;
import br.com.furiabot.menus.MenuLinksUteis;
import br.com.furiabot.menus.MenuPrincipal;
import br.com.furiabot.quiz.Quiz;
import br.com.furiabot.quiz.QuizSession;
import br.com.furiabot.services.NoticiasGeral;
import br.com.furiabot.util.Constantes;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class MeuBot extends TelegramLongPollingBot {
    private Map<Long, QuizSession> sessoesQuiz = new HashMap<>(); // Armazena cada sessão de cada usuario

    @Override
    public String getBotUsername() {
        return "Bot do Felipe";
    }

    @Override
    public String getBotToken() {
        return "7909045230:AAEg4UYfImAtBj0wfXbaS13UVDcYVqrfd-A"; // <-- Coloque seu token aqui
    }

    @Override
    public void clearWebhook() {
        // Evita o erro 404
    }

    @Override
    public void onUpdateReceived(Update update) { //acionado sempre que receber mensagem
        if (update.hasMessage() && update.getMessage().hasText()) {

            String mensagem = update.getMessage().getText(); //Extrai texto de Usuario

            Long chatId = update.getMessage().getChatId(); //Extrai Id da conversa (Diferente para cada Usuario

            System.out.printf("\n Mensagem Recebida!!!: %s \n Id do chat: %s \n", mensagem, chatId);

            if (sessoesQuiz.containsKey(chatId)) { //Verifica se sessão já esta ativa
                tratarRespostaQuiz(mensagem, chatId); //Se sim continuar Quiz
            } else {
                enviarMensagemInicial(chatId); //Se não iniciar nova conversa
            }

        } else if (update.hasCallbackQuery()) { //Chamar
            tratarCallback(update);
        }
    }

    private void enviarMensagemInicial(Long chatId) {
        SendMessage mensagem = new SendMessage(); //Cria o objeto mensagem para envio de mensagem

        mensagem.setChatId(chatId.toString()); //Pega Id da conversa

        mensagem.setText(Constantes.MENSAGEM_INICIAL); //Envia a mensagem principal para a conversa

        mensagem.setReplyMarkup(MenuPrincipal.criar()); //Chama os Botoes do Menu

        try {
            execute(mensagem); //Executa mensagem
        } catch (TelegramApiException e) {
            e.printStackTrace(); //se falhar o envio retorna Erro
        }
    }

    private void tratarCallback(@NotNull Update update) { //Tratamento de interação do Usuario

        String data = update.getCallbackQuery().getData();  // Obtém a nome do Botão Clicado

        Long chatId = update.getCallbackQuery().getMessage().getChatId(); //Obtem Id do chat

        if (sessoesQuiz.containsKey(chatId)) { // Verifica se o usuário está no meio de uma sessão de quiz
            tratarRespostaQuiz(data, chatId);  // Chama para tratar a resposta
            return;  // Não precisa continuar o código, já tratou o evento
        }

        // Se não estiver em uma sessão de quiz, continua com as outras opções
        SendMessage mensagem = new SendMessage(); //Cria um objeto mensgem

        mensagem.setChatId(chatId.toString());//Obtem Id do chat

        switch (data) { //Pega valor do que foi clicado pelo usuario

            case "noticias": // Em construção
                mensagem.setText("Principais topicos");
                mensagem.setReplyMarkup(MenuLinks.noticias());
                System.out.printf("Botão Clicado:t in %s\nChatId: %s\n", data ,chatId);
                break;

            case "jogos": //Em Construção
                mensagem.setText("🎮 Lista de times: ");
//                mensagem.setReplyMarkup(MenuLinks.botaoEquipes());
                System.out.printf("Botão Clicado: %s\nChatId: %s\n", data ,chatId);
                break;

            case "negocios": // Novo caso para exibir notícias de negócios
                String respostaNoticias = Noticias.montarMensagemNoticias("noticias");
                mensagem.setText(respostaNoticias);
                System.out.printf("Botão Clicado: %s\nChatId: %s\n", data ,chatId);
                break;


            case "jogos_cs":
                mensagem.setText("Proximos Jogos da Furia no CsGo");

//                mensagem.setReplyMarkup(MenuLinks.botaoEquipes());
                break;


            case "links_uteis":
                mensagem.setText("🔗 Links úteis:");
                mensagem.setReplyMarkup(MenuLinksUteis.criar());
                System.out.printf("Botão Clicado: %s\nChatId: %s\n", data ,chatId);
                break;

            case "voltar":
                mensagem.setText(Constantes.MENSAGEM_INICIAL);
                mensagem.setReplyMarkup(MenuPrincipal.criar());//Ao apertar botao voltar ele chama botoes do Menu Principal
                System.out.printf("Botão Clicado: %s\nChatId: %s\n", data ,chatId);
                break;

            case "Voltar ao Menu":
                mensagem.setText(Constantes.MENSAGEM_INICIAL);
                mensagem.setReplyMarkup(MenuPrincipal.criar());
                System.out.printf("Botão Clicado: %s\nChatId: %s\n", data ,chatId);

            default:
                mensagem.setText("❓ Opção desconhecida.");
                mensagem.setReplyMarkup(MenuPrincipal.criar());
                break;
        }

        try {
            execute(mensagem);  // Envia a mensagem
        } catch (TelegramApiException e) {
            e.printStackTrace(); //Caso falhe o envio retorna mensagem de erro
        }
    }

    private void iniciarQuiz(Long chatId) {

        JSONObject primeiraPergunta = Quiz.obterPerguntaAleatoria(); //Cria um objeto Primeira pergunta e armazena o metodo "obterPerguntaAleatoria" da classe QUIZ

        QuizSession sessao = new QuizSession(primeiraPergunta);//Cria uma sessão do quiz

        sessoesQuiz.put(chatId, sessao); //Passa as informações do id da conversa e pergunta atual para a variavel SessoesQuiz

        SendMessage mensagem = new SendMessage(); //Cria um objeto mensagem para envio de mensagem

        mensagem.setChatId(chatId.toString());//Obtem Id do chat

        mensagem.setText("🧠 Quiz iniciado!\n\n" + primeiraPergunta.getString("pergunta"));//Imprime a primeira Pergunta

        mensagem.setReplyMarkup(Quiz.criarBotoesOpcoes(primeiraPergunta)); // Adiciona os botões das opções

        //Nessa linha configurar botao de Voltar

        try {
            execute(mensagem); //manda a mensagem
        } catch (TelegramApiException e) {
            e.printStackTrace();//caso o envio de mensagem falhe ele retorna erro
        }
    }

    private void tratarRespostaQuiz(String respostaUsuario, Long chatId) {
        if (respostaUsuario.equalsIgnoreCase("/sair")) { // Caso a resposta seja "/sair", encerra o quiz
            sessoesQuiz.remove(chatId);
            enviarMensagemInicial(chatId);
            return;
        } else if (respostaUsuario.equals("VoltarMenu")) { // Se usuário clicar para voltar ao menu
            enviarMensagemInicial(chatId);
            return;
        }

        // Obtém a sessão do quiz
        QuizSession sessao = sessoesQuiz.get(chatId);

        // Se a sessão for nula, encerra
        if (sessao == null) {
            enviarMensagemInicial(chatId);
            return;
        }

        JSONObject perguntaAtual = sessao.getPerguntaAtual();
        Quiz quiz = new Quiz();

        boolean acertou = quiz.verificarResposta(perguntaAtual, respostaUsuario);

        SendMessage mensagem = new SendMessage();
        mensagem.setChatId(chatId.toString());

        if (acertou) {
            mensagem.setText("✅ Correto!\n");
        } else {
            mensagem.setText("❌ Errado!\nResposta certa: " + perguntaAtual.getString("resposta") + "\n");
            sessao.incrementarErro(); // Incrementa número de erros
        }

        // Verifica se o usuário atingiu o limite de perguntas ou erros
        if (sessao.quizFinalizado()) {
            mensagem.setText(mensagem.getText() + "\n🏁 Quiz finalizado!\nVocê respondeu "
                    + sessao.getContadorPerguntas() + " perguntas e teve "
                    + sessao.getContadorErros() + " erros.");

            // Adiciona um botão de "Voltar ao Menu"
            mensagem.setReplyMarkup(MenuLinks.botaoVoltar());
            sessoesQuiz.remove(chatId); // Remove a sessão do quiz
        } else {
            sessao.novaPergunta();
            JSONObject novaPergunta = sessao.getPerguntaAtual();
            mensagem.setText(mensagem.getText() + "\n🔢 Pergunta " + sessao.getContadorPerguntas() + " de 5" +
                    "\n❌ Erros: " + sessao.getContadorErros() + " de 2" +
                    "\n\n🧠 Nova pergunta:\n" + novaPergunta.getString("pergunta"));
            mensagem.setReplyMarkup(Quiz.criarBotoesOpcoes(novaPergunta));
        }

        try {
            execute(mensagem);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
