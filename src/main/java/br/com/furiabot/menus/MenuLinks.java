package br.com.furiabot.menus;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.util.Collections;

/**
 * A classe {@code MenuLinks} é responsável por criar menus de teclado inline utilizados no chatbot.
 * Esses menus incluem botões que interagem com o usuário via callback data ou links de URL.
 */
public class MenuLinks {

    /**
     * Cria o menu principal com botões para acessar notícias, próximos jogos, quiz e links úteis.
     * Cada botão está associado a uma callback data específica.
     * 
     * @return Um objeto {@link InlineKeyboardMarkup} contendo o layout do menu principal.
     */
    public static InlineKeyboardMarkup criarMenu() {
        InlineKeyboardMarkup marcacao = new InlineKeyboardMarkup();

        InlineKeyboardButton btnNoticias = new InlineKeyboardButton();
        btnNoticias.setText("Notícias");
        btnNoticias.setCallbackData("noticias");

        InlineKeyboardButton btnJogos = new InlineKeyboardButton();
        btnJogos.setText("Próximos Jogos");
        btnJogos.setCallbackData("jogos");

        InlineKeyboardButton btnQuiz = new InlineKeyboardButton();
        btnQuiz.setText("Quiz");
        btnQuiz.setCallbackData("quiz");

        InlineKeyboardButton btnLinks = new InlineKeyboardButton();
        btnLinks.setText("Links Uteis");
        btnLinks.setCallbackData("Links Uteis");

        List<InlineKeyboardButton> linha1 = new ArrayList<>();
        linha1.add(btnNoticias);
        linha1.add(btnJogos);

        List<InlineKeyboardButton> linha2 = new ArrayList<>();
        linha2.add(btnQuiz);

        List<InlineKeyboardButton> linha3 = new ArrayList<>();
        linha3.add(btnLinks);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(linha1);
        rows.add(linha2);
        rows.add(linha3);

        marcacao.setKeyboard(rows);
        return marcacao;
    }

    /**
     * Cria um menu com botões para acessar links externos, como loja, Instagram, e Twitter,
     * além de um botão "Voltar" associado a uma callback data.
     * 
     * @return Um objeto {@link InlineKeyboardMarkup} contendo o layout do menu de links externos.
     */
    public static InlineKeyboardMarkup links() {
        InlineKeyboardMarkup marcacaoLinks = new InlineKeyboardMarkup();

        InlineKeyboardButton btnLoja = new InlineKeyboardButton();
        btnLoja.setText("Lojinha.gg");
        btnLoja.setUrl("https://www.furia.gg/");

        InlineKeyboardButton btnInstagram = new InlineKeyboardButton();
        btnInstagram.setText("Instagram.gg");
        btnInstagram.setUrl("https://www.instagram.com/furiagg/?hl=pt-br");

        InlineKeyboardButton btnTwitter = new InlineKeyboardButton();
        btnTwitter.setText("Twitter.gg");
        btnTwitter.setUrl("https://twitter.com/furiagg");

        InlineKeyboardButton btnVoltar = new InlineKeyboardButton();
        btnVoltar.setText("Voltar");
        btnVoltar.setCallbackData("Voltar");

        List<InlineKeyboardButton> linhaLinks1 = new ArrayList<>();
        linhaLinks1.add(btnInstagram);
        linhaLinks1.add(btnTwitter);
        linhaLinks1.add(btnLoja);

        List<InlineKeyboardButton> linhaLinks2 = new ArrayList<>();
        linhaLinks2.add(btnVoltar);

        List<List<InlineKeyboardButton>> linhasLinks = new ArrayList<>();
        linhasLinks.add(linhaLinks1);
        linhasLinks.add(linhaLinks2);

        marcacaoLinks.setKeyboard(linhasLinks);

        return marcacaoLinks;
    }

    /**
     * Cria um menu com apenas um botão para retornar ao menu principal.
     * 
     * @return Um objeto {@link InlineKeyboardMarkup} contendo o botão de voltar.
     */
    public static InlineKeyboardMarkup botaoVoltar() {
        InlineKeyboardMarkup marcacao = new InlineKeyboardMarkup();
        InlineKeyboardButton btnVoltar = new InlineKeyboardButton();
        btnVoltar.setText("🔙 Voltar ao Menu");
        btnVoltar.setCallbackData("voltar");

        // Adiciona o botão ao teclado
        List<List<InlineKeyboardButton>> linhas = new ArrayList<>();
        linhas.add(Collections.singletonList(btnVoltar));
        marcacao.setKeyboard(linhas);

        return marcacao;
    }

    /**
     * Cria um menu com botões para selecionar diferentes equipes ou modalidades esportivas,
     * incluindo CS, Valorant, PUBG, Kings League, e um botão para voltar ao menu principal.
     * 
     * @return Um objeto {@link InlineKeyboardMarkup} contendo o layout dos botões das equipes.
     */
    public static InlineKeyboardMarkup botaoEquipes() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton cs = new InlineKeyboardButton();
        cs.setText("🔫 CS");
        cs.setCallbackData("jogos_cs");

        InlineKeyboardButton valorant = new InlineKeyboardButton();
        valorant.setText("🎯 Valorant");
        valorant.setCallbackData("jogos_valorant");

        InlineKeyboardButton pubg = new InlineKeyboardButton();
        pubg.setText("🏹 PUBG");
        pubg.setCallbackData("jogos_pubg");

        InlineKeyboardButton kingsLeague = new InlineKeyboardButton();
        kingsLeague.setText("⚽ Kings League");
        kingsLeague.setCallbackData("jogos_kingsleague");

        InlineKeyboardButton btnVoltar = new InlineKeyboardButton();
        btnVoltar.setText("🔙 Voltar ao Menu");
        btnVoltar.setCallbackData("voltar");

        // Adiciona todos os botões em linhas separadas
        List<List<InlineKeyboardButton>> linhas = new ArrayList<>();
        linhas.add(Collections.singletonList(cs));
        linhas.add(Collections.singletonList(valorant));
        linhas.add(Collections.singletonList(pubg));
        linhas.add(Collections.singletonList(kingsLeague));
        linhas.add(Collections.singletonList(btnVoltar));

        markup.setKeyboard(linhas);
        return markup;
    }
}