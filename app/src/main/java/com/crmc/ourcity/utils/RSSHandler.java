package com.crmc.ourcity.utils;


import com.crmc.ourcity.model.rss.RSSEntry;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class RSSHandler extends DefaultHandler {

    // Feed and RSSEntry objects to use for temporary storage
    private RSSEntry currentRSSEntry = new RSSEntry();
    private List<RSSEntry> RSSEntryList = new ArrayList<RSSEntry>();

    // Number of articles added so far
    private int articlesAdded = 0;

    // Number of articles to download
    private static final int ARTICLES_LIMIT = 15;

    //Current characters being accumulated
    StringBuffer chars = new StringBuffer();


    public List<RSSEntry> getRSSEntryList() {
        return RSSEntryList;
    }



    /*
     * This method is called everytime a start element is found (an opening XML marker)
     * here we always reset the characters StringBuffer as we are only currently interested
     * in the the text values stored at leaf nodes
     *
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        chars = new StringBuffer();
    }



    /*
     * This method is called everytime an end element is found (a closing XML marker)
     * here we check what element is being closed, if it is a relevant leaf node that we are
     * checking, such as Title, then we get the characters we have accumulated in the StringBuffer
     * and set the current RSSEntry's title to the value
     *
     * If this is closing the "entry", it means it is the end of the article, so we add that to the list
     * and then reset our RSSEntry object for the next one on the stream
     *
     *
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (localName.equalsIgnoreCase("title")){
            currentRSSEntry.setTitle(chars.toString());
        } else if (localName.equalsIgnoreCase("title")){
            currentRSSEntry.setDescription(chars.toString());
        } else if (localName.equalsIgnoreCase("published")){
            currentRSSEntry.setPubDate(chars.toString());
        } else if (localName.equalsIgnoreCase("link")){
            currentRSSEntry.setLink(chars.toString());
        } else if (localName.equalsIgnoreCase("pubDate")){
            currentRSSEntry.setPubDate(chars.toString());
        } else if (localName.equalsIgnoreCase("guid")){
            currentRSSEntry.setGuid(chars.toString());
        } else if (localName.equalsIgnoreCase("entry")){

        }


        // Check if looking for article, and if article is complete
        if (localName.equalsIgnoreCase("guid")) {

            RSSEntryList.add(currentRSSEntry);

            currentRSSEntry = new RSSEntry();

            // Lets check if we've hit our limit on number of articles
            articlesAdded++;
            if (articlesAdded >= ARTICLES_LIMIT)
            {
                //throw new SAXException();
            }
        }
    }


    /*
     * This method is called when characters are found in between XML markers, however, there is no
     * guarante that this will be called at the end of the node, or that it will be called only once
     * , so we just accumulate these and then deal with them in endElement() to be sure we have all the
     * text
     *
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    public void characters(char ch[], int start, int length) {
        chars.append(new String(ch, start, length));
    }
}