package br.com.furiabot.jogos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CsScrapping {

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

    public static String montarMensagemTimes(String chave) {
        try {
            InputStream is = new FileInputStream("caminho/do/arquivo/jogos.json");
            byte[] jsonBytes = lerBytes(is);
            String jsonTxt = new String(jsonBytes, StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(jsonTxt);

            if (!json.has(chave)) return "❌ Nenhuma informação encontrada.";

            JSONObject dados = json.getJSONObject(chave);
            StringBuilder sb = new StringBuilder();

            switch (chave) {
                case "CS_GO":
                case "Valorant":
                case "PUBG":
                    sb.append("📅 Jogos da FURIA em: ").append(chave.replace("_", " ")).append("\n\n");
                    JSONArray campeonatos = dados.getJSONArray("campeonatos");
                    for (int i = 0; i < campeonatos.length(); i++) {
                        JSONObject camp = campeonatos.getJSONObject(i);
                        sb.append("🏆 ").append(camp.optString("nome")).append("\n");
                        sb.append("📌 Fase: ").append(camp.optString("fase")).append("\n");
                        sb.append("📅 Início: ").append(camp.optString("data_inicio")).append("\n");
                        sb.append("✅ Resultado: ").append(camp.optString("resultado")).append("\n\n");
                    }
                    break;

                case "Kings_League_Brazil":
                    sb.append("⚽ Kings League Brazil - FURIA FC\n");
                    sb.append("👑 Presidente: ").append(dados.optString("presidente")).append("\n");
                    sb.append("📅 Início: ").append(dados.optString("data_inicio")).append("\n");
                    sb.append("📅 Fim: ").append(dados.optString("data_fim")).append("\n\n");
                    sb.append("📊 Resultados:\n");

                    JSONArray resultados = dados.getJSONArray("resultados");
                    for (int i = 0; i < resultados.length(); i++) {
                        JSONObject res = resultados.getJSONObject(i);
                        sb.append("🗓️ ").append(res.optString("data")).append(" vs ")
                                .append(res.optString("oponente")).append(" ➜ ")
                                .append(res.optString("resultado")).append("\n");
                    }

                    JSONObject playoffs = dados.getJSONObject("playoffs");
                    sb.append("\n🎯 Playoffs: de ").append(playoffs.optString("data_inicio"))
                            .append(" até ").append(playoffs.optString("data_fim")).append("\n");
                    break;
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Erro ao carregar informações dos jogos.";
        }
    }

}
