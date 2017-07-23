package com.kshimauchi.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kshimauchi.newsapp.data.Contract;
import com.squareup.picasso.Picasso;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {
    //private ArrayList<NewsItem> data;
    ItemClickListener listener;
    private Context context;
    private Cursor cursor;
    final static String TAG = "newsAdapter: ";
//remove the ArrayList for the use of a Cursor to db scheme         ArrayList<NewsItem> data
    public NewsAdapter(Context context,Cursor cursor, ItemClickListener listener) {
        this.context = context;
        this.cursor = cursor;
        this.listener = listener;

    }
    interface ItemClickListener {
        //adds the cursor to the interface
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }
    @Override
    public NewsAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        //Add to the layout
        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }  //counts the rows

    /**
     * ************************Inner Class: ItemHolder  ****************************/
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author;
        TextView title;
        TextView description;
        TextView url;
        TextView urlToImage;
        //adding and image view for the urlToImage
        ImageView img;
        TextView publishedAt;

        //Constructor for the itemHolder  :To the grader:  Why doesn't marks code for the ny.times doesn't  cast ?
        public ItemHolder(  View itemView   ) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            title= itemView.findViewById(R.id.title);
            description= itemView.findViewById(R.id.description);
            url = itemView.findViewById((R.id.url));
           urlToImage = itemView.findViewById(R.id.urlToImage);
            img = itemView.findViewById(R.id.img);
            publishedAt = itemView.findViewById(R.id.publishedAt);

            itemView.setOnClickListener(this);
        }
        /* more information here  binds the items to a textView defined in item.xml*/
        public void bind(int pos) {
            //NewsItem item = data.get(pos);
            cursor.moveToPosition(pos);

            author.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR)));
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            urlToImage.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE)));
            publishedAt.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT)));
            url.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL)));
            //dealing with the image with picasso  from database
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE));
            Log.d(TAG, "#######################  url " + url );

                     if(url != null){
                         //gave me some issue with just using context:  so i called getcontext on the imageView to make sure
                        Picasso.with(img.getContext()).load(url).into(img);
                     }
        }

        @Override
        public void onClick(View v) {
                int pos = getAdapterPosition();
                listener.onItemClick(cursor,pos);
        }
        //removed below setNewsData  not really using the ArrayList here since we have a db
      }
    }
/**
 *                                          public void  setNewsData(ArrayList<NewsItem> newsData) {
                                            data = newsData;
                                            notifyDataSetChanged();
                                            }
 *
 *
 * **/