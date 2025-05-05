package br.com.furiabot.quiz;

import br.com.furiabot.menus.MenuLinks;
import br.com.furiabot.menus.MenuPrincipal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import java.io.ByteArrayOutputStream;

import java.util.Arrays;
import java.util.List;


public class Quiz {

    private static JSONArray perguntas; //Recebe todas as perguntas do quiz em formato Json

    public static void carregarQuiz() {

        if (perguntas == null) { //Se pergunta nao foi iniciado ele inicia

            try (InputStream arquivoJson = Quiz.class.getClassLoader().getResourceAsStream("furia_quiz_enumerado.json")) { //Abre o arquivo direto no diretorio resoucers
                if (arquivoJson == null) { //Verifica se o arquivo foi encontrado
                    throw new IOException("Arquivo furia_quiz_enumerado.json não encontrado em resources!"); //Se nao foi lança um exeção
                }

                byte[] bytes = lerBytes(arquivoJson); //ler arquivo json
                String content = new String(bytes, StandardCharsets.UTF_8);//converte o arquivo para String

                JSONObject json = new JSONObject(content);//Duvida
                perguntas = json.getJSONArray("quiz");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] lerBytes(InputStream input) throws IOException {
        byte[] buffer = new byte[8192]; //Armazena os bytes do arquivo em pedaços de 8192
        int bytesRead;

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) { //Armazena os dados em uma matriz de Bytes
            while ((bytesRead = input.read(buffer)) != -1) { //DUVIDA
                output.write(buffer, 0, bytesRead);
            }
            return output.toByteArray(); //Recupera os dados e forma uma matriz de Bytes
        }
    }

    public static JSONObject obterPerguntaAleatoria() {
        carregarQuiz(); //Pega o arquivo carregado
        Random random = new Random();
        int index = random.nextInt(perguntas.length());//Gera um numero aleatorio
        return perguntas.getJSONObject(index); //Retorna uma pergunta aleatoria
    }

    public static JSONArray obterOpcoes(JSONObject pergunta){
        return pergunta.getJSONArray("opcoes"); //Extrai o array de opçoes ´ç
    }

    public boolean verificarResposta(JSONObject pergunta, String respostaUsuario) {
        try {
            // Verifica se a chave "resposta" existe
            if (pergunta.has("resposta")) { //Verifica resposta da pergunta
                String respostaCorreta = pergunta.getString("resposta"); //Armazena resposta da pergunta
                return respostaCorreta.equalsIgnoreCase(respostaUsuario); //Compara a resposta da pergunta com a resposta do Usuario

            } else {
                System.out.println("Chave 'resposta' não encontrada na pergunta: " + pergunta); // caso nao ache a resposta da pergunta
                return false;
            }
        } catch (JSONException e) { //Tratamento de exeção
            System.out.println("Erro ao acessar 'resposta': " + e.getMessage() );
            return false;
        }
    }

    public static InlineKeyboardMarkup criarBotoesOpcoes(JSONObject pergunta) {
        JSONArray opcoes = pergunta.getJSONArray("opcoes");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardMarkup markupVoltar = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> linhas = new java.util.ArrayList<>();
        List<List<InlineKeyboardButton>> linhaVoltar = new java.util.ArrayList<>();


        for (int i = 0; i < opcoes.length(); i++) {
            String textoOpcao = opcoes.getString(i);
            InlineKeyboardButton botao = new InlineKeyboardButton();
            botao.setText(textoOpcao);
            botao.setCallbackData(textoOpcao); // Aqui você decide se quer usar algo como "quiz_resposta:A" etc

            // Cada botão em sua própria linha (ou use Arrays.asList(botaoA, botaoB) se quiser na mesma linha)
            linhas.add(java.util.Collections.singletonList(botao));
        }

        InlineKeyboardButton voltar = new InlineKeyboardButton();
        voltar.setText("🔙 Voltar ao Menu");
        voltar.setCallbackData("VoltarMenu");

        linhas.add(java.util.Collections.singletonList(voltar));

        markup.setKeyboard(linhas);
        //markupVoltar.setKeyboard(linhaVoltar);

        return markup;
    }

    public static InlineKeyboardMarkup botaoVoltar(){
        InlineKeyboardMarkup marcacaoVoltar = new InlineKeyboardMarkup();

        InlineKeyboardButton voltar = new InlineKeyboardButton();
        voltar.setText("Voltar");
        voltar.setCallbackData("Voltar");

        List<List<InlineKeyboardButton>> rows = Arrays.asList(
                Arrays.asList(voltar)
        );


        return marcacaoVoltar;
    }

}
