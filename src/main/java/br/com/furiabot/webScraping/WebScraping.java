package br.com.furiabot.webScraping;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class WebScraping {

    public class FuriaNewsScraping {

        public void main(String[] args) {
            // URL do site que contém as notícias sobre a FURIA
            String url = "https://x.com/search?q=FURIA&src=typed_query"; // Substitua pela URL real

            try {
                // Conectando à página e obtendo o documento HTML
                Document doc = Jsoup.connect(url).get();

                // Extraindo os títulos das notícias
                Elements newsElements = doc.select(".news-title"); // Ajuste o seletor de acordo com o site

                // Iterando sobre as notícias e imprimindo os títulos
                for (Element news : newsElements) {
                    String title = news.text();  // Pegando o texto do título
                    String link = news.absUrl("href");  // Pegando o link da notícia

                    System.out.println("Título: " + title);
                    System.out.println("Link: " + link);
                    System.out.println("-----------------------------");
                }
            } catch (IOException e) {
                System.err.println("Erro ao acessar o site: " + e.getMessage());
            }
        }
    }

}
