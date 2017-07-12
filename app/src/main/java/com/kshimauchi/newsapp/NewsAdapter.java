package com.kshimauchi.newsapp;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kshimauchi.newsapp.Model.NewsItem;

import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {
    private ArrayList<NewsItem> data;
    ItemClickListener listener;


    public NewsAdapter(ArrayList<NewsItem> data, ItemClickListener listener) {
        this.data = data;
        this.listener = listener;

    }
    interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
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
    public void onBindViewHolder(NewsAdapter.ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * ************************Inner Class: ItemHolder  ****************************/
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author;
        TextView title;
        TextView description;
        TextView url;
        //TextView urlToImage;
        TextView publishedAt;

        //Constructor for the itemHolder
        public ItemHolder(  View itemView   ) {
            super(itemView);
            author =(TextView)itemView.findViewById(R.id.author);
            title=(TextView)itemView.findViewById(R.id.title);
            description=(TextView)itemView.findViewById(R.id.description);
            url = (TextView)itemView.findViewById((R.id.url));
            //urlToImage = (TextView)itemView.findViewById(R.id.urlToImage);
            publishedAt = (TextView)itemView.findViewById(R.id.publishedAt);

            itemView.setOnClickListener(this);
        }
        /* more information here  binds the items to a textView defined in item.xml*/
        public void bind(int pos) {
            NewsItem item = data.get(pos);

            author.setText( item.getAuthor()    );

            title.setText(  item.getTitle() );
            description.setText((   item.getDescription()   ));
            url.setText(item.getUrl());
           // urlToImage.setText(item.getUrlToImage());
            publishedAt.setText(    item.getPublishedAt()   );
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
        //
        public void  setNewsData(ArrayList<NewsItem> newsData) {
            data = newsData;
            notifyDataSetChanged();
        }
    }
}
