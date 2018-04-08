package lib.RequestFactory.TemplateRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lib.RequestFactory.ClassRequest.Configuration.AccessLayer.AccessLayer;
import org.apache.log4j.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

public abstract class TemplateRequest implements BasicRequest {

    public Client client = ClientBuilder.newClient();
    public Response response;

    public class RequestServiceException extends Exception {
        public RequestServiceException(String msg) {
            super(msg);
        }
    }

    static Logger log = Logger.getLogger(TemplateRequest.class);

    public abstract void sendRequest();

    public String setAccessToken() throws IOException {
        return  AccessLayer.getApiKey();
    }

    public <T> T submit(Class<T> classType) throws IOException, RequestServiceException {

        log.info("Submit +++ "+classType);
        sendRequest();
        int status = new Integer(this.response.getStatus());
        log.info("Status Request Code :" + status);
        if (status == 200) {
            return readResponseAs(classType);
        }else{
            String error = new String(this.response.readEntity(String.class));
            log.error("Status Request Cause:" + error);
            throw new RequestServiceException(error);
        }
    }


    public <T> T readResponseAs(Class<T> classType) throws IOException {
        try {
            return new ObjectMapper().readValue(this.response.readEntity(InputStream.class), classType);
        } finally {
            this.response.close();
        }
    }

}

