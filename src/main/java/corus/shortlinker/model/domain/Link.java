package corus.shortlinker.model.domain;

import java.util.Date;

public class Link {

    private long id;
    private String link;
    private Date creationTime;
    private String shortLink;


    public Link(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public Date getCreationTime() {
        return creationTime;
    }

    public String getLink() {
        return link;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}