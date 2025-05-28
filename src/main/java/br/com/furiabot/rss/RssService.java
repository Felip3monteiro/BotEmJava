package br.com.furiabot.rss;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssService {

    private static final String RSS_URL = "https://www.hltv.org/rss/news";

    public List<RssFuria> buscarNoticiasFuria() {
        List<RssFuria> noticias = new ArrayList<>();

        try {
            // Acessa o RSS
            InputStream input = new URL(RSS_URL).openStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);

            NodeList items = doc.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);

                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;

                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String link = element.getElementsByTagName("link").item(0).getTextContent();

                    // Filtra notícias que falam da FURIA
                    if (title.toLowerCase().contains("furia")) {
                        noticias.add(new RssFuria(title, link));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return noticias;
    }
}
