// Edward Liang

package com.example.Edward_Liang_Project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    ArrayList<String> Movies;
    ArrayList<String> Directors;
    ArrayList<String> Movies_Wikipedia;
    ArrayList<String> Directors_Wikipedia;
    ArrayList<String> Clips;
    RecyclerView nameView;
    Object layout;

    private ArrayList<Integer> Thumbnails = new ArrayList<Integer>(
            Arrays.asList(R.drawable.image1, R.drawable.image2,
                          R.drawable.image3, R.drawable.image4,
                          R.drawable.image5,  R.drawable.image6,
                          R.drawable.image7, R.drawable.image8));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameView = (RecyclerView) findViewById(R.id.recycler_view);

        List<String> m = Arrays.asList(getResources().getStringArray(R.array.Movies));
        Movies = new ArrayList<>();
        Movies.addAll(m);

        List<String> d = Arrays.asList(getResources().getStringArray(R.array.Directors));
        Directors = new ArrayList<>();
        Directors.addAll(d);

        List<String> m_wiki = Arrays.asList(getResources().getStringArray(R.array.Movies_Wikipedia));
        Movies_Wikipedia = new ArrayList<>();
        Movies_Wikipedia.addAll(m_wiki);

        List<String> d_wiki = Arrays.asList(getResources().getStringArray(R.array.Directors_Wikipedia));
        Directors_Wikipedia = new ArrayList<>();
        Directors_Wikipedia.addAll(d_wiki);

        List<String> c = Arrays.asList(getResources().getStringArray(R.array.Clips));
        Clips = new ArrayList<>();
        Clips.addAll(c);

        // Define the listener with a lambda and access the name of the list item from the view
        MovieItemListener listener = (view,position)->{
            Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(Clips.get(position)));
            startActivity(implicit);
        };

        /*
            create a new recycler view, passing in the movie titles, wiki page url (director, movie),
            video clip url, listener for handling movie item clicks, and the context of main activity
         */
        MovieAdapter adapter = new MovieAdapter(Movies, Directors, Movies_Wikipedia, Directors_Wikipedia, Thumbnails, listener, this);
        nameView.setHasFixedSize(true);
        nameView.setAdapter(adapter);

        // default layout is list view
        nameView.setLayoutManager(new LinearLayoutManager(this));
        // store the current layout of items
        layout = nameView.getLayoutManager().getClass();
    }

    // inflating a new option menu layout from xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    // handle the option menu clicks to switch to different displays
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.list_display:
                switchList();
                return true;
            case R.id.grid_display:
                switchGrid();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // switching to list view using layout out manager
    void switchList(){
        if (layout != LinearLayoutManager.class){
            nameView.setLayoutManager(new LinearLayoutManager(this));
            layout = LinearLayoutManager.class;
        }
    }

    // switching to grid view using layout out manager
    void switchGrid(){
        if (layout != GridLayoutManager.class){
            nameView.setLayoutManager(new GridLayoutManager(this,2));
            layout = GridLayoutManager.class;
        }
    }
}