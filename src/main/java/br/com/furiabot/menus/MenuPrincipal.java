package br.com.furiabot.menus;

import br.com.furiabot.util.Constantes;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

/**
 * A classe {@code MenuPrincipal} é responsável por criar o teclado principal do chatbot,
 * apresentando as principais opções para interação com o usuário.
 */
public class MenuPrincipal {

    /**
     * Cria o menu principal com botões para exibir notícias, próximos jogos, quiz e links úteis.
     * Os textos e callback data dos botões são definidos utilizando as constantes da classe {@link Constantes}.
     * 
     * @return Um objeto {@link InlineKeyboardMarkup} contendo o layout do menu principal.
     */
    public static InlineKeyboardMarkup criar() {
        // Criação dos botões do menu principal
        InlineKeyboardButton noticias = new InlineKeyboardButton(Constantes.BOTAO_NOTICIAS);
        noticias.setCallbackData("noticias");

        InlineKeyboardButton jogos = new InlineKeyboardButton(Constantes.BOTAO_JOGOS);
        jogos.setCallbackData("jogos");

        InlineKeyboardButton quiz = new InlineKeyboardButton(Constantes.BOTAO_QUIZ);
        quiz.setCallbackData("quiz");

        InlineKeyboardButton links = new InlineKeyboardButton(Constantes.BOTAO_LINKS_UTEIS);
        links.setCallbackData("links_uteis");

        // Organização dos botões em linhas
        List<List<InlineKeyboardButton>> rows = Arrays.asList(
                Arrays.asList(noticias, jogos),
                Arrays.asList(quiz),
                Arrays.asList(links)
        );

        // Configuração do teclado com os botões organizados
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}