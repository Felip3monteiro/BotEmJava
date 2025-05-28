package br.com.furiabot.rss;

public class RssFuria {
    private String title;
    private String link;

    public RssFuria(String titulo, String link){
        this.title = titulo;
        this.link = link;
    }

    public String getTitle(){
        return title;
    }

    public String getLink(){
     return link;
    }
}
