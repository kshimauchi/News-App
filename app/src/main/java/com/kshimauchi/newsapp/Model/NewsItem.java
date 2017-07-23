package com.kshimauchi.newsapp.Model;

/**
 * Created by kshim on 6/27/2017.
 */
//This has been converted to make a decent object for the data at the given url
public class NewsItem {


    private String title;
    private String abstractKeyword;
    private String imageURL;
    private String published_date;
    private String Url;
    //adx_keywords unused
    public NewsItem(String title, String published_date, String abstractKeyword,String imageURL,String Url)
    {
        this.title = title;
        this.published_date = published_date;
        this.abstractKeyword = abstractKeyword;
        this.imageURL = imageURL;
        this.Url = Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractKeyword() {
        return abstractKeyword;
    }

    public void setAbstractKeyword(String abstractKeyword) {
        this.abstractKeyword = abstractKeyword;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
