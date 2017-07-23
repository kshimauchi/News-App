package com.kshimauchi.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kshimauchi.newsapp.Model.NewsItem;
import com.kshimauchi.newsapp.data.Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {
    //must create a cursor for database
    private Cursor cursor;       //allows read and write access to the result set returned in a database
    private Context context;  //allows access to application-specific

    private ArrayList<NewsItem> data;
    ItemClickListener listener;
    public static final String TAG = "Adapter: ";

    //modified the newsadapter constructor took out the ArrayList<NewsItem> data and replaced with Cursor object which can be used to point
    //records in a database
    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;

    }
    interface ItemClickListener {
        void onItemClick(Cursor cursor,int clickedItemIndex);
    }
    @Override
    public NewsAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;
        //Add to the layout
        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();  //returns the number of rows in the cursor
    }  //rather then returning the arraylist we return the cursor count

    /**
     * ************************Inner Class: ItemHolder  ****************************/
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView abstr;
        TextView published_date;
        ImageView img;

        public ItemHolder(  View itemView   ) {
            super(itemView);
            //these define a single item they are a collection of views which make 1 row on the visible screen of a phone
            title =(TextView) itemView.findViewById(R.id.title);

            abstr=(TextView) itemView.findViewById(R.id.abstr);

            published_date =(TextView)itemView.findViewById(R.id.date);

            img = (ImageView)itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }
        /* more information here  binds the items to a textView defined in item.xml*/
        public void bind(int pos) {
            //NewsItem item = data.get(pos);
            cursor.moveToPosition(pos);
            //need to create a contract database
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            abstr.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_ABSTRACT)));
            published_date.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_IMAGEURL));
            if(url!=null){
                //@Link: http://square.github.io/picasso/ version 2.5.2
                //loads the image into the imageview
                Picasso.with(context).load(url).into(img);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor,pos);
        }
    }
}
//        public void  setNewsData(ArrayList<NewsItem> newsData) {
//            data = newsData;
//            notifyDataSetChanged();


