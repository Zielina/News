import ControllerNews.Controller.Controller;
import ControllerNews.Controller.ControllerMvc;
import ControllerNews.Main;
import lib.RequestFactory.OutputDataFront.Countries;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static java.util.EnumSet.allOf;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.hasProperty;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestRepo {

    Controller controller = new Controller();
    ControllerMvc controllerMvc =  new ControllerMvc();


   @Test
    public void testNewsOk() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/news/us/general"))
                .andExpect(status().isOk());

    }

    @Test
    public void testNewsNot() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/news/an/general"))
                .andExpect(status().isNotFound())
                .andExpect((ResultMatcher) content().string("{\"cause\":null,\"stackTrace\":[],\"localizedMessage\":\"News [ Country = an Category = general ] Not Found\",\"message\":\"News [ Country = an Category = general ] Not Found\",\"suppressed\":[]}"));
    }

    @Test
    public void testBeginMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllerMvc).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
//                .andExpect(model().attribute("keyCategories", hasSize(7)));
    }

    @Test
    public void testMVCRedirectionSearchNews() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllerMvc).build();
        mockMvc.perform(get("/searchNews?q=Bitcoin"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/newsSearch/Bitcoin/1"));
    }

    @Test
    public void testMVCRedirectionNews() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllerMvc).build();
        mockMvc.perform(post("/news")
                .param("countries","US").param("categories","general"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/news/US/general/1"));
    }
}
