package br.com.furiabot.jogos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import br.com.furiabot.menus.MenuPrincipal;
import br.com.furiabot.util.Constantes;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * A classe {@code CsScrapping} é responsável por processar dados relacionados a jogos
 * e notícias da equipe FURIA.
 *
 * Ela contém métodos para ler e manipular arquivos JSON e retornar informações formatadas
 * que podem ser usadas no bot, como mensagens de jogos e notícias.
 */
public class CsScrapping {


    /**
     * Método utilitário para ler bytes de um {@link InputStream}.
     *
     * @param inputStream O fluxo de entrada contendo os dados a serem lidos.
     * @return Um array de {@code byte[]} contendo os dados lidos.
     * @throws IOException Caso ocorra um erro ao ler os dados do fluxo.
     */
    public static byte[] lerBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }



    /**
     * Gera uma mensagem formatada relacionada a uma categoria de jogo ou campeonato.
     * Os dados são obtidos de um arquivo JSON interno chamado "furia_campeonatos_2025.json".
     *
     * @param chave A categoria de jogo ou campeonato (por exemplo: "CS_GO", "Valorant", "PUBG",
     *              "Kings_League_Brazil").
     * @return Uma string contendo a mensagem formatada. Caso a chave ou o arquivo não sejam encontrados,
     * retorna uma mensagem de erro.
     */

    public static String montarMensagem(String chave) {
        SendMessage mensagem = new SendMessage();
        try (
                InputStream is = CsScrapping.class.getClassLoader().getResourceAsStream("furia_campeonatos_2025.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
        ) {
            if (is == null) {
                return "❌ Arquivo de dados não encontrado.";
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String jsonTxt = sb.toString();
            JSONObject json = new JSONObject(jsonTxt);

            if (!json.has(chave)) return "❌ Nenhuma informação encontrada.";

            JSONObject dados = json.getJSONObject(chave);
            StringBuilder message = new StringBuilder();

            switch (chave) {
                case "CS_GO":
                case "Valorant":
                case "PUBG":
                    message.append("📅 Jogos da FURIA em: ").append(chave.replace("_", " ")).append("\n\n");
                    JSONArray campeonatos = dados.getJSONArray("campeonatos");
                    for (int i = 0; i < campeonatos.length(); i++) {
                        JSONObject camp = campeonatos.getJSONObject(i);
                        message.append("🏆 ").append(camp.optString("nome")).append("\n");
                        message.append("📌 Fase: ").append(camp.optString("fase")).append("\n");
                        message.append("📅 Início: ").append(camp.optString("data_inicio")).append("\n");
                        message.append("✅ Resultado: ").append(camp.optString("resultado")).append("\n\n");
                    }
                    break;

                case "Kings_League_Brazil":
                    message.append("⚽ Kings League Brazil - FURIA FC\n");
                    message.append("👑 Presidente: ").append(dados.optString("presidente")).append("\n");
                    message.append("📅 Início: ").append(dados.optString("data_inicio")).append("\n");
                    message.append("📅 Fim: ").append(dados.optString("data_fim")).append("\n\n");
                    message.append("📊 Resultados:\n");

                    JSONArray resultados = dados.getJSONArray("resultados");
                    for (int i = 0; i < resultados.length(); i++) {
                        JSONObject res = resultados.getJSONObject(i);
                        message.append("🗓️ ").append(res.optString("data")).append(" vs ")
                                .append(res.optString("oponente")).append(" ➜ ")
                                .append(res.optString("resultado")).append("\n");
                    }

                    JSONObject playoffs = dados.getJSONObject("playoffs");
                    message.append("\n🎯 Playoffs: de ").append(playoffs.optString("data_inicio"))
                            .append(" até ").append(playoffs.optString("data_fim")).append("\n");
                    break;
                case "voltar":
                    mensagem.setText(Constantes.MENSAGEM_INICIAL);
                    mensagem.setReplyMarkup(MenuPrincipal.criar()); //Ao apertar botao voltar ele chama botoes do Menu Principal
                    break;

                default:
                    return "❌ Chave inválida.";
            }

            return message.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "❌ Erro ao ler o arquivo de dados.";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Erro ao processar informações dos jogos.";
        }
    }


    /**
     * Gera uma mensagem formatada com as últimas notícias relacionadas a FURIA.
     * Os dados são carregados de um arquivo JSON chamado "noticias.json".
     *
     * @return Uma string contendo informações formatadas das notícias mais recentes.
     * Caso o arquivo não seja encontrado ou ocorra um erro, uma mensagem de erro será retornada.
     */

    public static String montarNoticias() {
        try {
            InputStream is = CsScrapping.class.getClassLoader().getResourceAsStream("noticias.json");
            if (is == null) return "❌ Arquivo de notícias não encontrado.";

            byte[] jsonBytes = lerBytes(is);
            String jsonTxt = new String(jsonBytes, StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(jsonTxt);

            JSONArray noticias = json.getJSONArray("noticias");
            StringBuilder sb = new StringBuilder("📰 Notícias da FURIA:\n\n");

            for (int i = 0; i < noticias.length(); i++) {
                JSONObject noticia = noticias.getJSONObject(i);
                sb.append("🗞️ ").append(noticia.optString("titulo")).append("\n");
                sb.append("📅 ").append(noticia.optString("data")).append("\n");
                sb.append("📝 ").append(noticia.optString("descricao")).append("\n\n");
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Erro ao carregar as notícias.";
        }
    }

}
