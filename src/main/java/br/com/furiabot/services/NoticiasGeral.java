package br.com.furiabot.services;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class NoticiasGeral {

    JSONObject arquivoJson = new JSONObject();

    LocalDateTime data = LocalDateTime.now();

    private static final String API_KEY = ""; // Armazenando o código da API
    private static final String API_URL = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey="+API_KEY; // URL da API

    public String buscaNoticias() {
        StringBuilder resultados = new StringBuilder();
        //System.out.println("URL da Api"+API_URL);
        System.out.println("restultado "+resultados);

        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Tempo de timeout para conexão
            connection.setReadTimeout(5000); // Tempo de timeout para leitura

            int status = connection.getResponseCode(); // Obtém o código de resposta HTTP

            if (status == 200) { // Se a resposta for 200 (OK)
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linha;

                while ((linha = rd.readLine()) != null) {
                    resultados.append(linha);
                }
                rd.close();
            } else {
                System.out.println("Erro ao acessar a API. Código de status: " + status);
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(data.toLocalDate());

        return resultados.toString();
    }

    public void processarNoticias() {
        String respostaApi = buscaNoticias();
        JSONObject jsonResponse = new JSONObject(respostaApi);
        if (respostaApi != null && !respostaApi.isEmpty()) {
            try {
                arquivoJson.put("noticias", jsonResponse);
            } catch (Exception e) {
                System.out.println("Erro ao processar resposta da API: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhuma notícia foi encontrada ou houve um erro na requisição.");
        }
    }


    public void salvarJson() {
        // Formatando a data para um nome de arquivo
        String nomeArquivo = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".json";

        // Definindo o caminho da pasta resources dentro do projeto
        String caminhoPasta = "src/main/resources/";

        // Criando o diretório caso ele não exista
        File diretorio = new File(caminhoPasta);
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // Cria a pasta se não existir
        }

        // Caminho completo do arquivo
        File arquivo = new File(caminhoPasta + nomeArquivo);

        try (FileWriter fileWriter = new FileWriter(arquivo)) {
            fileWriter.write(arquivoJson.toString(4)); // Indentação para melhor leitura
            System.out.println("Arquivo JSON salvo em: " + arquivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo JSON: " + e.getMessage());
        }
    }

}
