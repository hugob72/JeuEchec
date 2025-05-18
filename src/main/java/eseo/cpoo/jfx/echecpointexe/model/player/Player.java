package eseo.cpoo.jfx.echecpointexe.model.player;

import eseo.cpoo.jfx.echecpointexe.model.article.Article;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String nickname;

    private int elo;

    private League league;

    private int coins;

    private List<Article> articles;

    public Player(String nickname) {
        this.nickname = nickname;
        this.elo = 0;
        this.league = League.BRONZE;
        this.coins = 0;
        this.articles = new ArrayList<Article>();
    }

    public Player(String nickname, int elo, League league, int coins, List<Article> articles) {
        this.nickname = nickname;
        this.elo = elo;
        this.league = league;
        this.coins = coins;
        this.articles = articles;

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }
}
