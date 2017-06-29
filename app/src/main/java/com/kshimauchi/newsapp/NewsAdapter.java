package com.kshimauchi.newsapp;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kshimauchi.newsapp.Model.NewsItem;

import java.util.ArrayList;

import static com.kshimauchi.newsapp.R.id.author;

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
     * ************************Inner Class: Item Holder  ****************************/
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Constructor
        public ItemHolder(View itemView) {
            super(itemView);
        }

        public void bind(int pos) {
            NewsItem item = data.get(pos);
            author.setText(item.getAuthor());
            /**/


        /* more information here */

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}
