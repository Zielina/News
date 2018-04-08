import lib.RequestFactory.ClassRequest.Configuration.RequestParametrization;
import lib.RequestFactory.ClassRequest.NewsPredict.Article;
import lib.RequestFactory.OutputDataFront.Countries;
import lib.RequestFactory.OutputDataFront.OutputDataFront;
import lib.RequestFactory.RequestFactory;
import lib.RequestFactory.TemplateRequest.TemplateRequest;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


public class TestLib {

    @Test
    public void testCountriesAndCategories() throws TemplateRequest.RequestServiceException, IOException {
        List<String> listCategories = OutputDataFront.getCategories();
        List<Countries> listCountries = OutputDataFront.getCountries();
        Assert.assertEquals(listCategories.size(),7);
        Assert.assertEquals(listCountries.get(0).getCode(),"AF");
        Assert.assertEquals(listCountries.get(0).getName(),"Afghanistan");
    }
}
