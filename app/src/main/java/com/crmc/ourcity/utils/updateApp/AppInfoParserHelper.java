package com.crmc.ourcity.utils.updateApp;

import org.jsoup.nodes.Element;

public class AppInfoParserHelper {

    public Element mElement;
    AppInfo projectTmp;

    public AppInfoParserHelper(Element element) {
        this.mElement = element;
        this.projectTmp = new AppInfo();
    }

    private String getFirstElementByAttributeValue(String attrKey, String arrtVal) {
        return mElement.getElementsByAttributeValue(attrKey, arrtVal).text();
    }

    public AppInfo getAppInfo() {
        projectTmp.currentVersion = mElement.getElementsByAttributeValue(VersionManagerConstants.KEY_ITEM_PROPERTY, VersionManagerConstants.VAL_SOFTWARE_VERSION).text();
        return projectTmp;
    }

    public void setStatusCode(int statusCode) {
        projectTmp.statusCode = statusCode;
    }
}
