package lib.RequestFactory.ClassRequest.DispatcherRequest;

import jersey.repackaged.com.google.common.cache.CacheBuilder;
import jersey.repackaged.com.google.common.cache.CacheLoader;
import jersey.repackaged.com.google.common.cache.LoadingCache;
import lib.RequestFactory.ClassRequest.Configuration.RequestParametrization;
import lib.RequestFactory.ClassRequest.NewsPredict.Article;
import lib.RequestFactory.RequestFactory;
import lib.RequestFactory.TemplateRequest.TemplateRequest;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class CacheDispatcherRequest {

    private static LoadingCache<String, List<Article>> cacheRepo;
    private static RequestParametrization requestParametrization;
    static Logger log = Logger.getLogger(CacheDispatcherRequest.class);


    public static List<Article> getRepoNews(String name, RequestParametrization requestParametrization) throws IOException, ExecutionException {
        if (name != null && requestParametrization != null) {
            log.info("Cache to " + name );
            setRequestParametrization(requestParametrization);
            return cacheRepo.get(name);
        }
        return null;
    }

    public static RequestParametrization getRequestParametrization() {
        return requestParametrization;
    }

    public static void setRequestParametrization(RequestParametrization requestParametrization) {
        CacheDispatcherRequest.requestParametrization = requestParametrization;
    }

    static {
        cacheRepo = CacheBuilder.newBuilder().maximumSize(10L).expireAfterAccess(10L, TimeUnit.MINUTES).recordStats().build(new CacheLoader<String, List<Article>>() {
            public List<Article> load(String obj) throws IOException, TemplateRequest.RequestServiceException {
                log.info("Empty Cache send request " + obj);
                String [] valueEndpoint = obj.split("&&");
                return RequestFactory.getNewsObject(valueEndpoint[0], getRequestParametrization());
            }
        });
    }

}
