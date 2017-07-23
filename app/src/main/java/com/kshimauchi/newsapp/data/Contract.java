package com.kshimauchi.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by kshim on 7/21/2017.
 */

public class Contract {
    //interface basecolumns
    public static class TABLE_ARTICLES implements BaseColumns {
        //collection of names for very common columns
        //is a reflection of the NewsItem:  what will be storing instead of using an ArrayList<NewsItem> we are using a database
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String  COLUMN_NAME_PUBLISHED_DATE= "published_date";
        public static final String COLUMN_NAME_ABSTRACT ="abstract";
        public static final String COLUMN_NAME_IMAGEURL= "imageURL";
        public static final String COLUMN_NAME_URL = "url";
    }
    }
