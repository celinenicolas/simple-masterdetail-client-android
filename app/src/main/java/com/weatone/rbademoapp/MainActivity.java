package com.weatone.rbademoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/* Notes :

The given data didn't mention any sufficient information that would allow me to synchronize that API data with the Database data and make sure that we could update only the changes.
A key containing the last update of the data, ids for the items are usually what we are looking for in order to do so.
Without it, I'm afraid I couldn't have been sure that "Bob" is still the same "Bob"...

Also, I didn't fully implement all the getters/setters, content values, text view changes to privilege features over details first.
I could have finished the details in 15-30 min.

Best regards.
Celine
 */

public class MainActivity extends AppCompatActivity implements DownloadCallback<String>, AdapterView.OnItemClickListener {

    DatabaseHelper dbHelper;
    ListView peopleListView;
    PeopleListAdapter peopleListAdapter;
    ArrayList<People> peopleList = new ArrayList<People>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

        RESTClient mRESTClient = new RESTClient(this);
        mRESTClient.execute("http://www.meetyourculture.com/rbaapp/data1.json");

        peopleListView = (ListView) findViewById(R.id.peopleListView);
        peopleListAdapter = new PeopleListAdapter(this);
        peopleListView.setOnItemClickListener(this);
        peopleListView.setAdapter(peopleListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_load1) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onData(String result) {
        if (result != null) {
            try {
                JSONObject json = new JSONObject(result);

                // People
                peopleList = new ArrayList<>();
                JSONArray peopleJSON = json.getJSONArray("people");
                for (int i = 0; i<peopleJSON.length(); i +=1) {
                    JSONObject itemJSON = (JSONObject) peopleJSON.get(i);
                    People item = new People();
                    item.setName(itemJSON.getString("firstname"), itemJSON.getString("lastname"));
                    item.setAge(itemJSON.getString("age"));
                    //item.setAddress(itemJSON.getString("age"));
                    peopleList.add(item);
                    peopleListAdapter.notifyDataSetChanged();
                }

                dbHelper.addListOfPeople(peopleList); // TODO: we should try to update, but we have no id or field that we could use to test upon

            } catch (Exception e) {

            }
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        People item = peopleList.get(position);

        Intent intent = new Intent(this, PeopleDetailActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    private class PeopleListAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;

        public PeopleListAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return peopleList.size();
        }

        @Override
        public Object getItem(int position) {
            return peopleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final  PeopleListRowViewHolder holder;

            if ( convertView == null ) {
                convertView = mInflater.inflate(R.layout.item_people, null);
                convertView.setId(position);
                //  convertView.setClickable(true);
                holder = new PeopleListRowViewHolder();
                holder.displayNameTextView = (TextView) convertView.findViewById(R.id.displayNameTextView);
                convertView.setTag(position);
            } else {
                holder = (PeopleListRowViewHolder) convertView.getTag();
            }

            People item = peopleList.get(position);
            holder.displayNameTextView.setText(item.getDisplayName());

            return convertView;
        }

        public class PeopleListRowViewHolder {
            public TextView displayNameTextView;
        }
    }
}
