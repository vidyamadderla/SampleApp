package com.example.android.sampleproject.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.sampleproject.R;
import com.example.android.sampleproject.model.Person;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("People List");
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.person_list_recyclerview);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mFAB = (FloatingActionButton) findViewById(R.id.fab);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddPersonActivity.class);
                String position = String.valueOf(mAdapter.getItemCount());
                intent.putExtra(AddPersonActivity.EXTRA_NEW_DATA_POSITION, position);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFAB.setVisibility(View.GONE);;
        new GetPeopleAsyncTask(this).execute(getString(R.string.firebase_persondata_url));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class GetPeopleAsyncTask extends AsyncTask<String,Void,ArrayList<Person>> {

        WeakReference<MainActivity> mainActivityWeakReference;

        public GetPeopleAsyncTask(MainActivity activity){
            mainActivityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected ArrayList<Person> doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            ArrayList<Person> persons = new ArrayList<>();

            try {
                Response response = client.newCall(request).execute();
                JSONArray jsonResponse = new JSONArray(response.body().string());

                for(int i=0; i < jsonResponse.length(); i++){
                    JSONObject jsonObject = jsonResponse.getJSONObject(i);
                    Person p = new Person();
                    p.parseJson(jsonObject);
                    persons.add(p);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (JSONException jsonException) {
                Log.e(TAG, jsonException.getMessage());
            }
            return persons;
        }

        @Override
        protected void onPostExecute(ArrayList<Person> persons) {
            super.onPostExecute(persons);
            if(mainActivityWeakReference.get() != null){
                mainActivityWeakReference.get().mAdapter.updatePersons(persons);
                mainActivityWeakReference.get().mFAB.setVisibility(View.VISIBLE);
            }
        }
    }
}
