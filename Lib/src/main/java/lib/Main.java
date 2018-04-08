package lib;

import lib.RequestFactory.ClassRequest.Configuration.RequestParametrization;
import lib.RequestFactory.ClassRequest.NewsPredict.Article;
import lib.RequestFactory.RequestFactory;
import lib.RequestFactory.TemplateRequest.TemplateRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            List<Article> articles =RequestFactory.getNewsObject(RequestFactory.TOP_HEADLINES, new RequestParametrization.RequestParamBuilder().addCategory("health").addCoutry("us").build());
            articles.stream().forEach(c->{
                System.out.println("Desc :"+c.getDescription()+'\n');
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateRequest.RequestServiceException e) {
            e.printStackTrace();
        }
    }
}
