package br.com.furiabot.noticias;

import br.com.furiabot.services.NoticiasGeral;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Noticias {

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

    public static String montarMensagemNoticias(String chave) {
        NoticiasGeral api = new NoticiasGeral();
        api.buscaNoticias();
        api.processarNoticias();
        api.salvarJson();

        LocalDateTime data = LocalDateTime.now();

        try {
            String nomeArquivo = "src/main/resources/" + data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".json";

            InputStream is = new FileInputStream(nomeArquivo);
            byte[] jsonBytes = lerBytes(is);
            String jsonTxt = new String(jsonBytes, StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(jsonTxt);

            if (!json.has(chave)) return "❌ Nenhuma informação encontrada.";

            JSONObject dados = json.getJSONObject(chave);
            StringBuilder sb = new StringBuilder();

            switch (chave) {
                case "noticias":
                    JSONArray artigos = dados.getJSONArray("articles"); // Pegando os artigos da API
                    for (int i = 0; i < artigos.length(); i++) {
                        JSONObject artigo = artigos.getJSONObject(i);
                        sb.append("📰 Título: ").append(artigo.getString("title")).append("\n");
                        sb.append("🔗 Link: ").append(artigo.getString("url")).append("\n\n");
                    }
                    break;
                default:
                    return "❌ Chave inválida.";
            }

            return sb.toString();

        } catch (Exception e) {
            return "⚠️ Problemas técnicos ao carregar notícias: " + e.getMessage();
        }
    }
}

