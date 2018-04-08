package ControllerNews.Controller;

import ControllerNews.NewsRequest.NewsRequest;
import ControllerNews.RestRespond.Articles;
import ControllerNews.RestRespond.News;
import lib.RequestFactory.OutputDataFront.OutputDataFront;
import lib.RequestFactory.TemplateRequest.TemplateRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

@Controller
public class ControllerMvc {

    static Logger log = Logger.getLogger(ControllerMvc.class);


    @RequestMapping("/")
    public ModelAndView index() {
        return PreparedModelAndView.build().addStart("home");

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView requestPost(
            @RequestParam("countries") String countries,
            @RequestParam("categories") String categories) {
        if (!countries.equals("") && !categories.equals("")) {
            try {
                log.info("Countries :" + countries + " Categories :" + categories);
                TemplateRequest newsRequest = new NewsRequest(getEndpoint() + "/news/" + countries + "/" + categories);
                News restRespond = newsRequest.submit(News.class);
                if (!(restRespond == null)) {
                    return PreparedModelAndView.build().addRespond(restRespond, "home");
                } else {
                    return PreparedModelAndView.build().addError("No result", "home");
                }
            } catch (IOException e) {
                log.error(e.getStackTrace());
                return null;
            } catch (TemplateRequest.RequestServiceException e) {
                log.error(e.getStackTrace());
                return PreparedModelAndView.build().addError(e.getMessage(), "home");
            }
        }
        return null;
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public String requestPostTest(HttpServletRequest request,
                                  @RequestParam("countries") String countries,
                                  @RequestParam("categories") String categories) {
        request.getSession().setAttribute("newsList", null);
        return "redirect:/news/" + countries + "/" + categories + "/1";
    }

    @RequestMapping(value = "/news/{countries}/{categories}/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView showPagedProductsPage(HttpServletRequest request,
                                              @PathVariable Integer pageNumber,
                                              @PathVariable("countries") String countries,
                                              @PathVariable("categories") String categories) {
        try {
            log.info("Showing News page " + pageNumber );
            if (!countries.equals("") && !categories.equals("")) {
                PagedListHolder<?> pagedListHolder = (PagedListHolder<?>) request.getSession().getAttribute("newsList");
                String baseUrl = getEndpoint() + "/news/" + countries + "/" + categories + "/";
                log.info("Countries :" + countries + " Categories :" + categories);
                if (pagedListHolder == null) {
                    TemplateRequest newsRequest = new NewsRequest(getEndpoint() + "/news/" + countries + "/" + categories);
                    News restRespond = newsRequest.submit(News.class);
                    if (!(restRespond == null)) {
                        pagedListHolder = new PagedListHolder<Articles>(restRespond.getArticles());
                        pagedListHolder.setPageSize(5);
                    } else {
                        return PreparedModelAndView.build().addError("No result", "home");
                    }

                } else {
                    final int goToPage = pageNumber - 1;
                    if (goToPage <= pagedListHolder.getPageCount() && goToPage >= 0) {
                        pagedListHolder.setPage(goToPage);
                    }
                    log.info("Page List" + pagedListHolder.getPageList().get(1));
                }
                request.getSession().setAttribute("newsList", pagedListHolder);
                return PreparedModelAndView.build().addPage(pagedListHolder, "home", baseUrl);
            }
        } catch (IOException e) {
            log.error(e.getStackTrace());
            return null;
        } catch (TemplateRequest.RequestServiceException e) {
            log.error(e.getStackTrace());
            return PreparedModelAndView.build().addError(e.getMessage(), "home");
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public Object searchRepo(@RequestParam(value = "q") String q) {
        try {
            log.info("KeyWord :" + q);
            TemplateRequest newsRequest = new NewsRequest(getEndpoint() + "/news/search?q=" + URLEncoder.encode(q, "UTF-8"));
            News restRespond = newsRequest.submit(News.class);

            if (!(restRespond == null)) {
                return PreparedModelAndView.build().addRespond(restRespond, "home");
            } else {
                return PreparedModelAndView.build().addError("No result", "home");
            }
        } catch (IOException e) {
            log.error(e.getStackTrace());
            return null;
        } catch (TemplateRequest.RequestServiceException e) {
            log.error(e.getStackTrace());
            return PreparedModelAndView.build().addError(e.getMessage(), "home");
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchNews")
    public String searchRepoNews(HttpServletRequest request, @RequestParam(value = "q") String q) throws UnsupportedEncodingException {
        log.info("New Search News : " + q);
        request.getSession().setAttribute("newsList", null);
        String value =  URLEncoder.encode(q, "UTF-8");
        return "redirect:/newsSearch/"+ value+"/1";

    }

    @RequestMapping(value = "/newsSearch/{q}/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView showPagedSearchesNews(HttpServletRequest request,
                                              @PathVariable Integer pageNumber,
                                              @PathVariable(value = "q") String q) {
        try {
            log.info("Showing News page " + pageNumber +"  "+ q);
            if (q != null) {
                log.info("KeyWord :" + q);
                PagedListHolder<?> pagedListHolder = (PagedListHolder<?>) request.getSession().getAttribute("newsList");
                String baseUrl = getEndpoint() + "/newsSearch/"+q+"/";
                if (pagedListHolder == null) {
                    log.info("PageListHolder Empty");
                    TemplateRequest newsRequest = new NewsRequest(getEndpoint() + "/news/search?q=" + URLEncoder.encode(q, "UTF-8"));
                    News restRespond = newsRequest.submit(News.class);
                    if (!(restRespond == null)) {
                        pagedListHolder = new PagedListHolder<Articles>(restRespond.getArticles());
                        pagedListHolder.setPageSize(5);
                    } else {
                        return PreparedModelAndView.build().addError("No result", "home");
                    }

                } else {
                    final int goToPage = pageNumber - 1;
                    if (goToPage <= pagedListHolder.getPageCount() && goToPage >= 0) {
                        pagedListHolder.setPage(goToPage);
                    }
                    log.info("Page List Number " + pagedListHolder.getPage() +1);
                }
                request.getSession().setAttribute("newsList", pagedListHolder);
                return PreparedModelAndView.build().addPage(pagedListHolder, "home", baseUrl);
            }

        } catch (IOException e) {
            log.error(e.getStackTrace());
            return null;
        } catch (TemplateRequest.RequestServiceException e) {
            log.error(e.getStackTrace());
            return PreparedModelAndView.build().addError(e.getMessage(), "home");
        }

        return null;
    }

    private static String getEndpoint() throws IOException {
        File prFile = new File("./News/config.properties");
        Properties properties = new Properties();
        if (prFile.exists()) {
            FileInputStream fileInput = new FileInputStream(prFile);
            properties.load(fileInput);
            fileInput.close();
            String endpoint = properties.getProperty("endpoint");
            log.info("Endpoint :" + endpoint);
            return endpoint;
        }
        return null;
    }

    static class PreparedModelAndView {

        private News respond;
        private String error;
        private PagedListHolder<?> pagedListHolder;
        private int current;
        private int begin;
        private int end;
        private int totalPageCount;
        private String baseUrl;

        public ModelAndView addStart(String url) {
            return executeModelAndView(url);
        }

        public ModelAndView addRespond(News respond, String url) {
            this.respond = respond;
            return executeModelAndView(url);
        }

        public ModelAndView addPage(PagedListHolder<?> pagedListHolder, String url, String baseUrl) {
            this.pagedListHolder = pagedListHolder;
            this.current = pagedListHolder.getPage() + 1;
            this.begin = Math.max(1, current - 5);
            this.end = Math.min(begin + 5, pagedListHolder.getPageCount());
            this.totalPageCount = pagedListHolder.getPageCount();
            this.baseUrl = baseUrl;
            return executeModelAndView(url);
        }


        public ModelAndView addError(String error, String url) {
            this.error = error;
            return executeModelAndView(url);
        }

        public static PreparedModelAndView build() {
            return new PreparedModelAndView();
        }

        private ModelAndView executeModelAndView(String url) {
            ModelAndView modelAndView = new ModelAndView(url);
            modelAndView.addObject("keyCountries", OutputDataFront.getCountries());
            modelAndView.addObject("keyCategories", OutputDataFront.getCategories());
            modelAndView.addObject("error", this.error);
            if (this.pagedListHolder != null) {
                modelAndView.addObject("keyNews", this.pagedListHolder.getPageList());
                modelAndView.addObject("beginIndex", begin);
                modelAndView.addObject("endIndex", end);
                modelAndView.addObject("currentIndex", current);
                modelAndView.addObject("totalPageCount", totalPageCount);
                modelAndView.addObject("baseUrl", baseUrl);
            }
            modelAndView.addObject("keyVisionDatasets", this.respond);

            return modelAndView;

        }
    }
}
