import br.com.furiabot.services.NoticiasFuriaService;
import br.com.furiabot.rss.RssService;

public class Teste {
    public static void main(String[] args) {
       NoticiasFuriaService api = new NoticiasFuriaService();
        RssService rss = new RssService();

        //System.out.println(rss.buscarNoticiasFuria());
        System.out.println(api.buscaNoticias());




    }
}
