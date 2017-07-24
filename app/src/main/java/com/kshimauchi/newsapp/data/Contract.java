package com.kshimauchi.newsapp.data;

import android.provider.BaseColumns;

public class Contract {
    //interface basecolumns
    public static class TABLE_ARTICLES implements BaseColumns {
        //collection of names for very common columns
        //is a reflection of the NewsItem:  what will be storing instead of using an ArrayList<NewsItem> we are using a database
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_NAME_PUBLISHED_AT = "publishedAt";
        public static final String COLUMN_NAME_URL = "url";
        }
    }

