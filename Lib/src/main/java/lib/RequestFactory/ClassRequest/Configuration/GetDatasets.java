package lib.RequestFactory.ClassRequest.Configuration;

import lib.RequestFactory.TemplateRequest.TemplateRequest;
import org.apache.log4j.Logger;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


public class GetDatasets extends TemplateRequest {

    private final String endpoint;
    static Logger log = Logger.getLogger(GetDatasets.class);

    public GetDatasets(String endpoint) {
        log.info("Endpoint GetDatasets:"+endpoint);
        this.endpoint = endpoint;
    }

    @Override
    public void sendRequest() {
        try {
            log.info("Send Request +++");
            super.response = super.client.target(this.endpoint)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "Bearer " + super.setAccessToken())
                    .header("Cache-Control", "no-cache")
                    .get();
        } catch (IOException e) {
            log.error(e.getStackTrace());
        }
    }

}
