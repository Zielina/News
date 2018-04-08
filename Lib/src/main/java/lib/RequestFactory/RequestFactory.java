package lib.RequestFactory;

import lib.RequestFactory.ClassRequest.Configuration.GetDatasets;
import lib.RequestFactory.ClassRequest.Configuration.RequestParametrization;
import lib.RequestFactory.ClassRequest.NewsPredict.Article;
import lib.RequestFactory.ClassRequest.NewsPredict.Headlines;
import lib.RequestFactory.TemplateRequest.TemplateRequest;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class RequestFactory {

    static Logger log = Logger.getLogger(RequestFactory.class);
    public static final String TOP_HEADLINES = "https://newsapi.org/v2/top-headlines?";
    public static final String SOURCES = "https://newsapi.org/v2/sources?";
    public static final String EVERYTHING = "https://newsapi.org/v2/everything?";


    public static List<Article> getNewsObject(String obj, RequestParametrization requestParam) throws TemplateRequest.RequestServiceException, IOException {
        if(obj.equals(TOP_HEADLINES)){
            return preperedRequest(TOP_HEADLINES,requestParam);
        }else if(obj.equals(SOURCES)){
            return null;
        }else if (obj.equals(EVERYTHING)){
            return preperedRequest(EVERYTHING,requestParam);
        }
        return null;
    }

    private static List<Article> preperedRequest(String url, RequestParametrization requestParam) throws IOException, TemplateRequest.RequestServiceException {
        StringBuffer param = new StringBuffer(url);
        LinkedHashMap<String,String> mapParam = requestParam.getLinkedHashMap();
        mapParam.entrySet().stream().forEach(c->{
            param.append(c).append("&");
        });
        param.delete(param.length()-1,param.length());
        log.info("Param :"+param.toString());
        TemplateRequest getDatasets = new GetDatasets(param.toString());
        Headlines headlines = getDatasets.submit(Headlines.class);
        return  headlines.getArticles();
    }
}
