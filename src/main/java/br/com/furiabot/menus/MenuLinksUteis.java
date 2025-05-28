package br.com.furiabot.menus;

import br.com.furiabot.util.Constantes;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

public class MenuLinksUteis {

    public static InlineKeyboardMarkup criar() {
        InlineKeyboardButton loja = new InlineKeyboardButton(Constantes.BOTAO_LOJA);
        loja.setUrl(Constantes.URL_LOJA);

        InlineKeyboardButton instagram = new InlineKeyboardButton(Constantes.BOTAO_INSTAGRAM);
        instagram.setUrl(Constantes.URL_INSTAGRAM);

        InlineKeyboardButton gitHub = new InlineKeyboardButton(Constantes.BOTAO_GITHUB);
        gitHub.setUrl(Constantes.URL_GITHUB);

        //InlineKeyboardButton twitter = new InlineKeyboardButton(Constantes.BOTAO_TWITTER);
        //twitter.setUrl(Constantes.URL_TWITTER);

        InlineKeyboardButton voltar = new InlineKeyboardButton(Constantes.BOTAO_VOLTAR);
        voltar.setCallbackData("voltar");

        List<List<InlineKeyboardButton>> rows = Arrays.asList(
                Arrays.asList(instagram,gitHub, loja),
                Arrays.asList(voltar)
        );

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup noticias(){
        InlineKeyboardMarkup marcacao = new InlineKeyboardMarkup();

        InlineKeyboardButton noticiaNegocio = new InlineKeyboardButton();


        return marcacao;
    }
}
