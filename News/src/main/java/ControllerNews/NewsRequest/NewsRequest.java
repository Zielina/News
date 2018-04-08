package ControllerNews.NewsRequest;


import lib.RequestFactory.TemplateRequest.TemplateRequest;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;

public class NewsRequest extends TemplateRequest {

    private final String endpoint;
    static Logger log = Logger.getLogger(NewsRequest.class);

    public NewsRequest(String endpoint) {
        log.info("Endpoint : "+endpoint);
        this.endpoint = endpoint;
    }

    @Override
    public void sendRequest() {

        log.info("Send Request +++");
        super.response = super.client.target(this.endpoint)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Cache-Control", "no-cache")
                .get();
    }
}
