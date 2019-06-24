package corus.shortlinker.web.page;


import corus.shortlinker.model.service.LinkService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {


    private LinkService linkService = new LinkService();


    LinkService getLinkService() {
        return linkService;

    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        //No operations.
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}





