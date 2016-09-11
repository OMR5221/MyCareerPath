package com.appsforprogress.android.mycareerpath;

/**
 * Created by ORamirez on 7/4/2016.
 */
public class WikiPage
{
    private String mId;
    private String mUrl;
    private String mExtract;
    private String mTitle;
    private String mCategory;
    private String mPortal;
    private String[] mLinks;
    private String mSummary;

    @Override
    public String toString()
    {
        return mSummary;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getExtract() {
        return mExtract;
    }

    public void setExtract(String extract) {
        mExtract = extract;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getPortal() {
        return mPortal;
    }

    public void setPortal(String portal) {
        mPortal = portal;
    }

    public String[] getLinks() {
        return mLinks;
    }

    public void setLinks(String[] links) {
        mLinks = links;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
