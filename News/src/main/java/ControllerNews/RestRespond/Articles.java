package ControllerNews.RestRespond;

public class Articles {

    private String author;
    private String title;
    private String description ;
    private String date;
    private String sourceName ;
    private String articleUrl;
    private String imageUrl;

    public Articles() {
    }

    public Articles(String author, String title, String description, String date, String sourceName, String articleUrl, String imageUrl) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.date = date;
        this.sourceName = sourceName;
        this.articleUrl = articleUrl;
        this.imageUrl = imageUrl;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
