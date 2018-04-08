package ControllerNews.Controller;

import ControllerNews.RestRespond.Articles;
import ControllerNews.RestRespond.News;
import lib.RequestFactory.ClassRequest.Configuration.RequestParametrization;
import lib.RequestFactory.ClassRequest.DispatcherRequest.CacheDispatcherRequest;
import lib.RequestFactory.ClassRequest.NewsPredict.Article;
import lib.RequestFactory.RequestFactory;
import lib.RequestFactory.TemplateRequest.TemplateRequest;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.NestedServletException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    static Logger log = Logger.getLogger(Controller.class);
    @RequestMapping( method= RequestMethod.GET, value="/news/{lang}/{category}")
    public Object getNews (@PathVariable("lang") String lang ,
                           @PathVariable("category")  String category ) {

        try {

            List<Article>  articles = CacheDispatcherRequest.getRepoNews(RequestFactory.TOP_HEADLINES +"&&"+lang+category, new RequestParametrization.RequestParamBuilder().addCategory(category).addCoutry(lang).addPageSize(100).build());
            log.info("Size :"+articles.size());
            if(articles.size() >0){
                return new ResponseEntity<News>(setArticlesRespond(lang,category,articles), HttpStatus.OK);
            }else {
                throw new NewsNotFoundException("Country = " +lang +" Category = "+ category);
            }
        } catch (IOException e) {
            log.error(e.getStackTrace());
        } catch (ExecutionException e) {
            log.error(e.getStackTrace());
        }

        return null;
    }

    @RequestMapping( method= RequestMethod.GET, value="/news/search")
    public Object getNews ( @RequestParam(value="q" )String q) {
            log.info("KeyWord Controller Rest : "+q);
        try {

            List<Article>  articles = CacheDispatcherRequest.getRepoNews(RequestFactory.EVERYTHING +"&&"+q, new RequestParametrization.RequestParamBuilder().addkeyWords(q).addPageSize(100).build());
            log.info("Size :"+articles.size());
            if(articles.size() >0) {
                return new ResponseEntity<News>(setArticlesRespond("","",articles), HttpStatus.OK);
            }else {
                throw new NewsNotFoundException(q);
            }
        } catch (IOException e) {
            log.error(e.getStackTrace());
        }  catch (ExecutionException e) {
            log.error(e.getStackTrace());
        }

        return null;
    }

    @ExceptionHandler(NewsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Error newsNotFound(NewsNotFoundException e) {
        String keySearch = e.getKeySearch();
        Error error = new Error("News [ "+keySearch+" ] Not Found");
        /** Without section stackTrace in Respond */
        error.setStackTrace(new StackTraceElement[0]);
        return error;
    }

    private static  News setArticlesRespond(String lang, String category, List<Article>  articles){
        News news = new News();
        news.setCountry(lang);
        news.setCategory(category);
        List<Articles> articlesList = new LinkedList<>();
        articles.stream().forEach(c->{
            Articles articlesNews = new Articles();
            articlesNews.setTitle(c.getTitle());
            articlesNews.setAuthor(c.getAuthor());
            articlesNews.setArticleUrl(c.getUrl().toString());
            articlesNews.setDate(c.getPublishedAt().toString());
            articlesNews.setSourceName(c.getSource().getName());
            articlesNews.setDescription(c.getDescription());
            articlesNews.setImageUrl(c.getUrlToImage());
            articlesList.add(articlesNews);
        });
        news.setArticles(articlesList);
        return news;
    }

    public class NewsNotFoundException extends RuntimeException {
        private String q;

        public NewsNotFoundException(String q) {
            this.q = q;
        }

        public String getKeySearch() {
            return q;
        }
    }


}