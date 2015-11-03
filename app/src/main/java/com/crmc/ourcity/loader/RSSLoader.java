package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.utils.rss.RssFeed;
import com.crmc.ourcity.utils.rss.RssItem;
import com.crmc.ourcity.utils.rss.RssReader;

import org.xml.sax.SAXException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RSSLoader extends BaseLoader<List<RssItem>> {

    private String link;

    public RSSLoader(final Context _context, final Bundle args) {
        super(_context);
        link = args.getString(Constants.BUNDLE_CONSTANT_RSS_LINK, "");
    }

    @Override
    public List<RssItem> loadInBackground() {

        URL url;

        try {
//            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
//            SAXParser parser = parserFactory.newSAXParser();
//            XMLReader reader = parser.getXMLReader();
//
//            url = new URL(link);
//            RSSHandler RSSHandler = new RSSHandler();
//            reader.setContentHandler(RSSHandler);
//            InputSource inputSource = new InputSource(url.openStream());
//            inputSource.setEncoding("UTF-8");
//            reader.parse(inputSource);

            url = new URL(link);
            RssFeed feed = RssReader.read(url);


            return feed.getRssItems();

        } catch ( SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
