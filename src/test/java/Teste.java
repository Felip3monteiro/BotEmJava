import br.com.furiabot.noticias.Noticias;
import br.com.furiabot.services.NoticiasGeral;
import br.com.furiabot.rss.RssService;

public class Teste {
    public static void main(String[] args) {
       NoticiasGeral api = new NoticiasGeral();
        RssService rss = new RssService();

        //System.out.println(rss.buscarNoticiasFuria());
        //System.out.println(api.buscaNoticias());

        System.out.println(api.buscaNoticias());
        api.processarNoticias();
        api.salvarJson();
        Noticias teste = new Noticias();

        String respostaNoticias = Noticias.montarMensagemNoticias("noticias");

        NoticiasGeral teste2 = new NoticiasGeral();

        teste2.buscaNoticias();




    }
}
