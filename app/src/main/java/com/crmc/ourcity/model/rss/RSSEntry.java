package com.crmc.ourcity.model.rss;

import java.net.URL;

/**
 * Created by podo on 01.09.15.
 */
public class RSSEntry {

    private String guid;
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String author;
    private URL url;
    private String encodedContent;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = extractCData(description);
    }

    public String getDescription() {
        return description;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setEncodedContent(String encodedContent) {
        this.encodedContent = extractCData(encodedContent);
    }

    public String getEncodedContent() {
        return encodedContent;
    }

    private String extractCData(String data){
        data = data.replaceAll("<!\\[CDATA\\[", "");
        data = data.replaceAll("\\]\\]>", "");
        return data;
    }
}
