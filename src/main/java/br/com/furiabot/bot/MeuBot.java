package br.com.furiabot.bot;

import br.com.furiabot.jogos.CsScrapping;
import br.com.furiabot.menus.MenuLinks;
import br.com.furiabot.menus.MenuLinksUteis;
import br.com.furiabot.menus.MenuPrincipal;
//import br.com.furiabot.noticias.NoticiasFuria;
import br.com.furiabot.quiz.Quiz;
import br.com.furiabot.quiz.QuizSession;
import br.com.furiabot.util.Constantes;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe MeuBot é uma implementação de um bot do Telegram que utiliza a API LongPolling.
 * Este bot oferece funcionalidades como quiz, notícias, informações de jogos e outros menus interativos.
 */
public class MeuBot extends TelegramLongPollingBot {

    /** 
     * Mapeia o ID dos chats para suas respectivas sessões do quiz.
     * Cada usuário tem uma instância de {@link QuizSession}.
     */
    private Map<Long, QuizSession> sessoesQuiz = new HashMap<>(); // Armazena cada sessão de cada usuário

    /**
     * Retorna o nome do bot.
     * 
     * @return Nome configurado do bot.
     */
    @Override
    public String getBotUsername() {
        return "Furia_Bot";
    }

    /**
     * Retorna o token usado para autenticação do bot no Telegram.
     * 
     * @return Token de autenticação do bot (é necessário configurá-lo manualmente).
     */
    @Override
    public String getBotToken() {
        return "TOKEN DO BOT"; // <-- Coloque seu token aqui
    }

    /**
     * Método que processa as atualizações recebidas pelo bot.
     * É acionado automaticamente pelo Telegram sempre que uma mensagem ou callback é enviada.
     * 
     * @param update Um objeto {@link Update} contendo a atualização recebida.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String mensagem = update.getMessage().getText(); // Texto da mensagem do usuário
            Long chatId = update.getMessage().getChatId(); // ID do chat associado à mensagem

            System.out.printf("\n Mensagem Recebida!!!: %s \n Id do chat: %s \n", mensagem, chatId);

            // Verifica se já há uma sessão de quiz ativa para o usuário
            if (sessoesQuiz.containsKey(chatId)) {
                tratarRespostaQuiz(mensagem, chatId); // Trata a resposta do quiz
            } else {
                enviarMensagemInicial(chatId); // Envia a mensagem inicial, caso não haja sessão ativa
            }
        } else if (update.hasCallbackQuery()) {
            tratarCallback(update); // Trata as interações de callback (botões clicados)
        }
    }

    /**
     * Envia a mensagem inicial para o usuário, geralmente com o menu principal.
     * 
     * @param chatId ID do chat para onde a mensagem será enviada.
     */
    private void enviarMensagemInicial(Long chatId) {
        SendMessage mensagem = new SendMessage(); // Criação do objeto para envio de mensagem
        mensagem.setChatId(chatId.toString()); // Define o chat ID destinatário
        mensagem.setText(Constantes.MENSAGEM_INICIAL); // Define o texto da mensagem inicial
        mensagem.setReplyMarkup(MenuPrincipal.criar()); // Adiciona botões do menu principal

        try {
            execute(mensagem); // Envia a mensagem ao usuário
        } catch (TelegramApiException e) {
            e.printStackTrace(); // Lança um erro caso falhe
        }
    }

    /**
     * Trata as interações do usuário com botões (callbacks).
     * Identifica a ação associada ao botão clicado e realiza as operações correspondentes.
     * 
     * @param update Objeto {@link Update} contendo a interação do usuário.
     */
    private void tratarCallback(@NotNull Update update) {
        String data = update.getCallbackQuery().getData();  // Nome do botão clicado
        Long chatId = update.getCallbackQuery().getMessage().getChatId(); // ID do chat

        // Se o usuário já estiver em uma sessão de quiz
        if (sessoesQuiz.containsKey(chatId)) {
            tratarRespostaQuiz(data, chatId);
            return; // Interrompe para evitar execução desnecessária
        }

        // Caso contrário, trata outras interações com o menu
        SendMessage mensagem = new SendMessage(); // Criação do objeto de mensagem
        mensagem.setChatId(chatId.toString()); // Define para onde a mensagem será enviada

        switch (data) {
            case "noticias":
                mensagem.setText(CsScrapping.montarNoticias()); // Em desenvolvimento
                mensagem.setReplyMarkup(MenuPrincipal.criar());
                break;

            case "jogos":
                mensagem.setText("🎮 Lista de times: ");
                mensagem.setReplyMarkup(MenuLinks.botaoEquipes()); // Botões de times
                break;

            case "quiz":
                iniciarQuiz(chatId); // Inicia o quiz
                return;

            case "links_uteis":
                mensagem.setText("🔗 Links úteis:");
                mensagem.setReplyMarkup(MenuLinksUteis.criar()); // Botões de links úteis
                break;

            case "voltar":
                mensagem.setText(Constantes.MENSAGEM_INICIAL);
                mensagem.setReplyMarkup(MenuPrincipal.criar()); // Retorna ao menu principal
                break;

            default:
                mensagem.setText("❓ Opção desconhecida.");
                mensagem.setReplyMarkup(MenuPrincipal.criar());
                break;
        }

        try {
            execute(mensagem); // Envia a mensagem com o texto e botões definidos
        } catch (TelegramApiException e) {
            e.printStackTrace(); // Lança um erro caso algo falhe
        }
    }

    /**
     * Inicia uma nova sessão de quiz para o usuário.
     * Este método cria uma nova pergunta e apresenta as opções no chat.
     * 
     * @param chatId ID do chat do usuário que iniciou o quiz.
     */
    private void iniciarQuiz(Long chatId) {
        JSONObject primeiraPergunta = Quiz.obterPerguntaAleatoria(); // Obtém uma pergunta aleatória
        QuizSession sessao = new QuizSession(primeiraPergunta); // Instancia uma nova sessão de quiz
        sessoesQuiz.put(chatId, sessao); // Mapeia a sessão ao ID do chat

        SendMessage mensagem = new SendMessage(); // Cria o objeto de mensagem
        mensagem.setChatId(chatId.toString());
        mensagem.setText("🧠 Quiz iniciado!\n\n" + primeiraPergunta.getString("pergunta")); // Apresenta a primeira pergunta
        mensagem.setReplyMarkup(Quiz.criarBotoesOpcoes(primeiraPergunta)); // Define os botões com as opções de resposta

        try {
            execute(mensagem); // Envia a mensagem para o chat
        } catch (TelegramApiException e) {
            e.printStackTrace(); // Erro em caso de falha no envio
        }
    }

    /**
     * Processa as respostas do usuário durante uma sessão de quiz.
     * Verifica se a resposta está correta e, dependendo do resultado, continua ou finaliza o quiz.
     * 
     * @param respostaUsuario Texto da resposta enviada pelo usuário
     * @param chatId ID do chat do usuário
     */
    private void tratarRespostaQuiz(String respostaUsuario, Long chatId) {
        if (respostaUsuario.equalsIgnoreCase("/sair")) {
            sessoesQuiz.remove(chatId); // Encerra a sessão atual
            enviarMensagemInicial(chatId); // Retorna ao menu principal
            return;
        }

        QuizSession sessao = sessoesQuiz.get(chatId); // Obtém a sessão de quiz do usuário
        if (sessao == null) {
            enviarMensagemInicial(chatId); // Caso não encontre a sessão, apresenta o menu principal
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
            sessao.incrementarErro(); // Incrementa o número de erros do usuário
        }

        if (sessao.quizFinalizado()) {
            mensagem.setText(mensagem.getText() + "\n🏁 Quiz finalizado!");
            sessoesQuiz.remove(chatId);
        } else {
            sessao.novaPergunta();
            mensagem.setText(mensagem.getText() + "\n🧠 Nova pergunta:\n" + sessao.getPerguntaAtual().getString("pergunta"));
        }

        try {
            execute(mensagem);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}