package corus.shortlinker.web.page;


import corus.shortlinker.model.domain.Link;
import corus.shortlinker.model.exception.ValidationException;

import corus.shortlinker.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public class RedirectPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {

        String shortLink = request.getParameter("link");
        if (shortLink == null || shortLink.isEmpty()) {
            view.put("errorredirect", "Short link is required");
        }
        try {
            getLinkService().getFullLink(shortLink);
        } catch (ValidationException e) {
            view.put("errorredirect", "Short Link doesn't exist or expired");
            return;

        }
        Link link;
        link = getLinkService().getLink(shortLink);
        String fullLink = link.getLink();
        if (!link.getShortLink().startsWith("http://")) {
            fullLink = "http://" + fullLink;
        }

        throw new RedirectException(fullLink);
    }

    private void action() {
        // No operations.
    }


}
