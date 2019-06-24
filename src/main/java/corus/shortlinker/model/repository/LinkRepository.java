package corus.shortlinker.model.repository;

import corus.shortlinker.model.domain.Link;

public interface LinkRepository {
    void saveShortLink(Link link);
    Link findByShortLink(String shortLink);
    Link findByFullLink(String link);
}