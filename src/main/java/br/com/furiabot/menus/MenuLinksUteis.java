package br.com.furiabot.menus;

import br.com.furiabot.util.Constantes;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

public class MenuLinksUteis {

    /**
     * Cria um objeto {@link InlineKeyboardMarkup} que representa um menu com botões
     * para acessar a loja, Instagram, Twitter e uma opção de voltar.
     * Cada botão está vinculado a uma URL ou associado a um dado de callback.
     *
     * @return um objeto InlineKeyboardMarkup contendo o layout do teclado definido
     */
    public static InlineKeyboardMarkup criar() {
        InlineKeyboardButton loja = new InlineKeyboardButton(Constantes.BOTAO_LOJA);
        loja.setUrl(Constantes.URL_LOJA);

        InlineKeyboardButton instagram = new InlineKeyboardButton(Constantes.BOTAO_INSTAGRAM);
        instagram.setUrl(Constantes.URL_INSTAGRAM);

        InlineKeyboardButton twitter = new InlineKeyboardButton(Constantes.BOTAO_TWITTER);
        twitter.setUrl(Constantes.URL_TWITTER);

        InlineKeyboardButton voltar = new InlineKeyboardButton(Constantes.BOTAO_VOLTAR);
        voltar.setCallbackData("voltar");

        List<List<InlineKeyboardButton>> rows = Arrays.asList(
                Arrays.asList(instagram, twitter, loja),
                Arrays.asList(voltar)
        );

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
