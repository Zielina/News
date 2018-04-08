package lib.RequestFactory.TemplateRequest;

import java.io.IOException;

public interface BasicRequest {

    void sendRequest();
    <T> T readResponseAs(Class<T> classType) throws IOException;
}
