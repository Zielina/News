package ControllerNews.RestRespond;

import java.util.List;

public class News {

    private String country;
    private String category;
    private List<Articles> articles;

    public News() {
    }

    public News(String country, String category, List<Articles> articles) {
        this.country = country;
        this.category = category;
        this.articles = articles;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
