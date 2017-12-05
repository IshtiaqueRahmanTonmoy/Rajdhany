package info.rajdhany.rajdhanyall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.rajdhany.rajdhanyall.R;
import info.rajdhany.rajdhanyall.adapter.CallbackItemTouch;
import info.rajdhany.rajdhanyall.adapter.MyAdapterRecyclerView;
import info.rajdhany.rajdhanyall.adapter.RecyclerViewClickListener;
import info.rajdhany.rajdhanyall.adapter.RecyclerViewTouchListener;
import info.rajdhany.rajdhanyall.model.Item;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener,CallbackItemTouch {

    private static String TAG = MainActivity.class.getSimpleName();
    List<Item> arraylist;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    private RecyclerView mRecyclerView; // RecyclerVIew
    private MyAdapterRecyclerView myAdapterRecyclerView; //The Adapter for RecyclerVIew
    private List<Item> mList; // My List the object 'Item'.
    private String keyword;
    // Array images
    private int images[] = new int[]{

            R.drawable.news,
            R.drawable.fm,
            R.drawable.tv
    };

    // Array names
    private String names[] = new String[]{
            "Rajdhany Daily",
            "Rajdhany FM",
            "Rajdhany TV"
    };

    private String textDescription[] = new String[]{
            "Daily Rajdhany link",
            "Rajdhany Fm link",
            "Rajdhany TV link"
    };

    Intent intent;
    //private String textDescription = "Subtitle description,lorem ipsum text generic etc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        initList(); //call method
        
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        //displayView(0);
    }

    private void initList() {
        arraylist = new ArrayList<>();

        mList = new ArrayList<>();
        for (int i = 0; i<names.length;i++){
            mList.add(new Item(images[i],names[i],textDescription[i]));
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView
        myAdapterRecyclerView = new MyAdapterRecyclerView(mList); // Create Instance of MyAdapterRecyclerView
        mRecyclerView.setAdapter(myAdapterRecyclerView); // Set Adapter for RecyclerView
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), mRecyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch(position) {
                    case 0:
                        intent = new Intent(MainActivity.this,DailyRajdhaniActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this,FmRajdhaniActvity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this,TvRajdhanyActivity.class);
                        startActivity(intent);
                        break;
                    default:

                }
                //Toast.makeText(getApplicationContext(), position + " is clicked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(getApplicationContext(), bookList.get(position).getTitle() + " is long pressed!", Toast.LENGTH_SHORT).show();

            }
        }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search_item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {

                //clear the previous data in search arraylist if exist
                arraylist.clear();

                keyword = s.toUpperCase();

                //checking language arraylist for items containing search keyword


                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getName().contains(keyword)) {
                        arraylist.add(new Item(mList.get(i).getIdImage(),mList.get(i).getName(),mList.get(i).getDescription()));
                    }
                }

                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this)); // Set LayoutManager in the RecyclerView
                myAdapterRecyclerView = new MyAdapterRecyclerView(arraylist); // Create Instance of MyAdapterRecyclerView
                mRecyclerView.setAdapter(myAdapterRecyclerView); // Set Adapter for RecyclerView
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;

    }

            @Override
              public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            MainActivity.this.finish();
            return true;
        }

        if(id == R.id.action_search){
            //Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                intent = new Intent(MainActivity.this,DailyRajdhaniActivity.class);
                startActivity(intent);
                //fragment = new DailyFragment();
                title = getString(R.string.title_daily);
                break;
            case 1:
                intent = new Intent(MainActivity.this,FmRajdhaniActvity.class);
                startActivity(intent);
                //fragment = new FmFragment();
                title = getString(R.string.title_fm);
                break;
            case 2:
                intent = new Intent(MainActivity.this,TvRajdhanyActivity.class);
                startActivity(intent);
                //fragment = new TvFragment();
                title = getString(R.string.title_tv);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        mList.add(newPosition,mList.remove(oldPosition));// change position
        myAdapterRecyclerView.notifyItemMoved(oldPosition, newPosition); //notifies changes in adapter, in this case use the notifyItemMoved
    }
}