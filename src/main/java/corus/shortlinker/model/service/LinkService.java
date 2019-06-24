package corus.shortlinker.model.service;

import corus.shortlinker.model.repository.LinkRepository;
import corus.shortlinker.model.repository.impl.LinkRepositoryImpl;
import corus.shortlinker.model.domain.Link;

import corus.shortlinker.model.exception.ValidationException;
import corus.shortlinker.model.generator.LinkGenerator;

public class LinkService {
    private LinkRepository linkRepository = new LinkRepositoryImpl();


    public String validateLink(String link) throws ValidationException {


        if (linkRepository.findByFullLink(link) != null) {
            return linkRepository.findByFullLink(link).getShortLink();
        }
        return link;


    }

    public void getFullLink(String shortLink) throws ValidationException{
        if(linkRepository.findByShortLink(shortLink)== null){
            throw new ValidationException("");
        }
    }

    public Link getLink(String shortLink) {
        return linkRepository.findByShortLink(shortLink);
    }


    public void createShortLink(Link link) {

        linkRepository.saveShortLink(link);
    }

    public String generateShortLink(String link) throws InterruptedException {
        return new LinkGenerator().generateShortLink(link);


    }


}






