package lib.RequestFactory.ClassRequest.Configuration;

import javax.ws.rs.core.Form;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestParametrization {

    private final String country;
    private final String category;
    private final String sources;
    private final String keyWords;
    private final int pageSize;
    private final int page;
    private final LinkedHashMap linkedHashMap;

    public RequestParametrization(RequestParamBuilder requestParamBuilder) {
        this.country = requestParamBuilder.country;
        this.category = requestParamBuilder.category;
        this.sources = requestParamBuilder.sources;
        this.keyWords = requestParamBuilder.keyWords;
        this.pageSize = requestParamBuilder.pageSize;
        this.page = requestParamBuilder.page;
        this.linkedHashMap = requestParamBuilder.linkedHashMap;
    }

    public String getCountry() {
        return country;
    }

    public LinkedHashMap getLinkedHashMap() {
        return linkedHashMap;
    }

    public String getCategory() {
        return category;
    }

    public String getSources() {
        return sources;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public static class RequestParamBuilder {

        private  String country;
        private  String category;
        private  String sources;
        private  String keyWords;
        private  int pageSize;
        private  int page;
        private LinkedHashMap linkedHashMap = new LinkedHashMap();

        public RequestParamBuilder addCoutry(String country){
            this.country=country;
            this.linkedHashMap.put("country",country);
            return this;
        }

        public RequestParamBuilder addCategory(String category){
            this.category=category;
            this.linkedHashMap.put("category",category);
            return this;
        }

        public RequestParamBuilder addSource(String sources){
            this.sources = sources;
            this.linkedHashMap.put("sources",sources);
            return this;
        }

        public RequestParamBuilder addkeyWords(String keyWords){
            this.keyWords=keyWords;
            this.linkedHashMap.put("q",keyWords);
            return this;
        }

        public RequestParamBuilder addPageSize(int pageSize){
            this.pageSize=pageSize;
            this.linkedHashMap.put("pageSize",pageSize);
            return this;
        }

        public RequestParamBuilder addPage(int page){
            this.page=page;
            this.linkedHashMap.put("page",page);
            return this;
        }

        public RequestParametrization build(){
            RequestParametrization requestParam = new RequestParametrization(this);
            return requestParam;
        }


    }
}
