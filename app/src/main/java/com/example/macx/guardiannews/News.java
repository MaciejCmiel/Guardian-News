package com.example.macx.guardiannews;

/**
 * Created by MacX on 2017-11-07.
 */

public class News
{

    private String sectionName;

    private String webTitle;

    private String webPublicationDate;

    private String webUrl;

    public News(String sectionName, String webTitle, String webPublicationDate, String webUrl)
    {
        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.webPublicationDate = webPublicationDate;
        this.webUrl = webUrl;
    }

    public String getSectionName()
    {
        return sectionName;
    }

    public String getWebTitle()
    {
        return webTitle;
    }

    public String getWebPublicationDate()
    {
        return webPublicationDate;
    }

    public String getWebUrl()
    {
        return webUrl;
    }
}
