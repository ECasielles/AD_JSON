package com.mercacortex.ad_json.model;

/**
 * Created by usuario on 10/01/17.
 */

public class NewsFeed {
    private String title, link, description, image, pubDate;
    public NewsFeed(String title, String link, String description, String image, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.image = image;
        this.pubDate = pubDate;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getPubDate() { return pubDate; }
    public void setPubDate(String pubDate) { this.pubDate = pubDate; }
}