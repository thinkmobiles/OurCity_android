package com.crmc.ourcity.model.rss;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

/**
 * Created by podo on 01.09.15.
 */
public class RSSEntry implements Parcelable {

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

    private String extractCData(String data) {
        data = data.replaceAll("<!\\[CDATA\\[", "");
        data = data.replaceAll("\\]\\]>", "");
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.guid);
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.description);
        dest.writeString(this.pubDate);
        dest.writeString(this.author);
        dest.writeSerializable(this.url);
        dest.writeString(this.encodedContent);
    }

    public RSSEntry() {
    }

    protected RSSEntry(Parcel in) {
        this.guid = in.readString();
        this.title = in.readString();
        this.link = in.readString();
        this.description = in.readString();
        this.pubDate = in.readString();
        this.author = in.readString();
        this.url = (URL) in.readSerializable();
        this.encodedContent = in.readString();
    }

    public static final Creator<RSSEntry> CREATOR = new Creator<RSSEntry>() {
        public RSSEntry createFromParcel(Parcel source) {
            return new RSSEntry(source);
        }

        public RSSEntry[] newArray(int size) {
            return new RSSEntry[size];
        }
    };
}
