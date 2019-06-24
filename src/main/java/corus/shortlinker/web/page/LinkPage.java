package corus.shortlinker.web.page;


import corus.shortlinker.model.domain.Link;
import corus.shortlinker.model.exception.ValidationException;


import javax.servlet.http.HttpServletRequest;

import java.util.Map;


public class LinkPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {

    }


    private void postlink(HttpServletRequest request, Map<String, Object> view) throws InterruptedException, ValidationException {
        Link link = new Link();

        String fullLink = request.getParameter("link");

        if (fullLink == null || fullLink.isEmpty()) {
            view.put("error", "Link is required");
        } else if (!getLinkService().validateLink(fullLink).equals(fullLink)) {

            view.put("shortLink", getLinkService().validateLink(fullLink));
        } else {
            String shortLink = getLinkService().generateShortLink(fullLink);
            link.setLink(fullLink);
            link.setShortLink(shortLink);
            getLinkService().createShortLink(link);

            view.put("shortLink", shortLink);

        }
    }

    private void getlink(HttpServletRequest request, Map<String, Object> view) throws InterruptedException, ValidationException {
        String shortLink = request.getParameter("shortLink");

        if (shortLink == null || shortLink.isEmpty()) {
            view.put("errorget", "Short link is required");
        }
        try {
            getLinkService().getFullLink(shortLink);
        } catch (ValidationException e) {
            view.put("errorget", "Short Link doesn't exist or expired");


        }
        Link link = new Link();
        link = getLinkService().getLink(shortLink);
        view.put("link", link.getLink());

    }


    //TODO CREATE GET QUERY TO GET FULLLINK BY SHORTLINK
    private void action() {
        // No operations.
    }
}

