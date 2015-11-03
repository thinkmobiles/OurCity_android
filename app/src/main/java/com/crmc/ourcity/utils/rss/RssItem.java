package com.crmc.ourcity.utils.rss;

import android.os.Bundle;
import android.os.Parcel;

public class RssItem implements Comparable<RssItem> {

    private RssFeed feed;
    private String title;
    private String link;
    private String pubDate;
    private String description;
    private String content;

    public RssItem() {}

    public RssItem(Parcel source) {
        Bundle data = source.readBundle();
        title = data.getString("title");
        link = data.getString("link");
        pubDate = (String) data.getSerializable("pubDate");
        description = data.getString("description");
        content = data.getString("content");
        feed = data.getParcelable("feed");
    }

    public RssFeed getFeed() {
        return feed;
    }

    public void setFeed(RssFeed feed) {
        this.feed = feed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(RssItem another) {
        if (getPubDate() != null && another.getPubDate() != null) {
            return getPubDate().compareTo(another.getPubDate());
        } else {
            return 0;
        }
    }
}
