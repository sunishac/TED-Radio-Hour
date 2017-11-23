package com.example.chala.group12_hw07;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.text.TextUtils.substring;

public class MainActivity extends AppCompatActivity implements GetPodCastAsyncTask.IActivity{

    private ProgressDialog pg;
    private ArrayList<Podcast> myPodsArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int noofcols=2;
    int s=0;
    static LinearLayout md;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        String t="TED Radio Hour Podcast";

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        md=(LinearLayout) findViewById(R.id.media);
        md.setVisibility(View.INVISIBLE);

        setTitle(t);
        getSupportActionBar().setIcon(R.drawable.ted);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pg = new ProgressDialog(this);
        pg.setMessage("Loading Episodes...");
        pg.setCancelable(false);
        pg.show();
        new GetPodCastAsyncTask(MainActivity.this).execute("http://www.npr.org/rss/podcast.php?id=510298");
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                if(s==0){
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, noofcols));
                    mAdapter = new MyRecyclerViewAdapter(myPodsArrayList);
                    mRecyclerView.setAdapter(mAdapter);
                    s=1;
                }
                else{
                    mLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new MyAdapter( myPodsArrayList);
                    mRecyclerView.setAdapter(mAdapter);
                    s=0;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setList(ArrayList<Podcast> podcasts) {
        if(podcasts!=null) {
            myPodsArrayList = podcasts;
            pg.dismiss();
            Log.d("demo",""+podcasts);

            Collections.sort(myPodsArrayList, new Comparator<Podcast>() {
                @Override
                public int compare(Podcast o1, Podcast o2) {
                    String myFormat= "yyyy-MM-dd";
                    String finalString1 = "";
                    String finalString2 = "";
                    Date date=null,date1=null ;

                    try {
                        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                        SimpleDateFormat newFormat = new SimpleDateFormat(myFormat);
                        date = (Date) formatter .parse(substring(o1.getPdate(),5,16));
                        finalString1= newFormat .format(date );
                        date1 = (Date) formatter .parse(substring(o2.getPdate(),5,16));
                        finalString2= newFormat .format(date1 );
                        Log.d("demo","date: "+date+" "+ (substring(o1.getPdate(),5,16))+" "+finalString1);

                    } catch (Exception e) {
                        Log.d("demo","exception");
                    }
                    return finalString2.compareTo(finalString1);
                }
            });

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyAdapter( myPodsArrayList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
