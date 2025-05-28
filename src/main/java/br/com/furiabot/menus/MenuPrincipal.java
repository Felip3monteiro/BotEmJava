package br.com.furiabot.menus;

import br.com.furiabot.util.Constantes;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

public class MenuPrincipal {

    public static InlineKeyboardMarkup criar() {

        InlineKeyboardButton noticias = new InlineKeyboardButton(Constantes.BOTAO_NOTICIAS);
        noticias.setCallbackData("noticias");

        InlineKeyboardButton jogos = new InlineKeyboardButton(Constantes.BOTAO_JOGOS);
        jogos.setCallbackData("jogos");

        InlineKeyboardButton quiz = new InlineKeyboardButton(Constantes.BOTAO_QUIZ);
        quiz.setCallbackData("quiz");

        InlineKeyboardButton links = new InlineKeyboardButton(Constantes.BOTAO_LINKS_UTEIS);
        links.setCallbackData("links_uteis");

        List<List<InlineKeyboardButton>> rows = Arrays.asList(
                Arrays.asList(noticias, jogos),
                Arrays.asList(quiz),
                Arrays.asList(links)
        );

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
