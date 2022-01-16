// Edward Liang

package com.example.Edward_Liang_Project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;

    private ArrayList<String> Movies;
    private ArrayList<String> Directors;
    private ArrayList<String> Movies_Wikipedia;
    private ArrayList<String> Directors_Wikipedia;
    private List<Integer> Thumbnails;
    private MovieItemListener listener;

    public MovieAdapter(ArrayList<String> Movies,
                        ArrayList<String> Directors,
                        ArrayList<String> Movies_Wikipedia,
                        ArrayList<String> Directors_Wikipedia,
                        ArrayList<Integer> Thumbnails,
                        MovieItemListener listener,
                        Context c){

        this.Movies = Movies;
        this.Directors = Directors;
        this.Movies_Wikipedia = Movies_Wikipedia;
        this.Directors_Wikipedia = Directors_Wikipedia;
        this.Thumbnails = Thumbnails;
        this.listener = listener;
        context = c;
    }

    // inflating a new movie_item from xml
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(listView, listener);
    }

    /*
        binding the necessary info to the movie items (director name, movie title, and image)
    */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDirector.setText(Directors.get(position));
        holder.tvName.setText(Movies.get(position));
        holder.image.setImageResource(Thumbnails.get(position));

    }

    @Override
    public int getItemCount() {
        return Movies.size();
    }

    /*
        This class creates a wrapper object around a view that contains the layout for
        an individual item in the list. It also implements the onClickListener so each ViewHolder in the list is clickable.
        It's onclick method will call the onClick method of the MovieItemListener defined in
        the main activity.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        public TextView tvName;
        public TextView tvDirector;
        public ImageView image;
        private MovieItemListener listener;
        private View itemView;


        public ViewHolder(@NonNull View itemView, MovieItemListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.title);
            tvDirector = (TextView) itemView.findViewById(R.id.director);
            image = (ImageView) itemView.findViewById(R.id.imageView);

            this.itemView = itemView;
            this.listener = listener;
            itemView.setOnCreateContextMenuListener(this); //set context menu for each list item (long click)
            itemView.setOnClickListener(this); //set short click listener
        }

        // implements the View.OnClickListener and the listener passed from main activity will handle it
        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //inflate menu from xml
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.context_menu, menu );
            menu.getItem(0).setOnMenuItemClickListener(onMenu1);
            menu.getItem(1).setOnMenuItemClickListener(onMenu2);
            menu.getItem(2).setOnMenuItemClickListener(onMenu3);
        }

        // this listener is for playing a short video clip (similar to short click on the movie item)
        private final MenuItem.OnMenuItemClickListener onMenu1 = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                listener.onClick(itemView, getAdapterPosition());
                return true;
            }
        };

        // this listener is for showing a wiki page about the movie
        private final MenuItem.OnMenuItemClickListener onMenu2 = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(Movies_Wikipedia.get(getAdapterPosition())));
                context.startActivity(implicit);
                return true;
            }
        };

        // this listener is for showing a wiki page about the director of a movie
        private final MenuItem.OnMenuItemClickListener onMenu3 = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(Directors_Wikipedia.get(getAdapterPosition())));
                context.startActivity(implicit);
                return true;
            }
        };
    }
}
