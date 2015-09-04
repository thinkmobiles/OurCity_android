package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.model.rss.RSSEntry;
import com.crmc.ourcity.utils.RSSHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by podo on 01.09.15.
 */
public class RSSLoader extends BaseLoader<List<RSSEntry>> {

    private String link;

    public RSSLoader(final Context _context, final Bundle args) {
        super(_context);
        link = args.getString(Constants.BUNDLE_CONSTANT_RSS_LINK, "");
    }

    @Override
    public List<RSSEntry> loadInBackground() {

        URL url;

        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader reader = parser.getXMLReader();

            url = new URL(link);
            RSSHandler RSSHandler = new RSSHandler();
            reader.setContentHandler(RSSHandler);
            InputSource inputSource = new InputSource(url.openStream());
            inputSource.setEncoding("UTF-8");
            reader.parse(inputSource);

            return RSSHandler.getRSSEntryList();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
