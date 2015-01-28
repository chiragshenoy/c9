package app.cloud9.com.cloud9;
/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticeBoard extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private static final int ITEMS_COUNT = 1;
    private List<String> mItems;

    final static String URL = "https://api.myjson.com/bins/4txdj";
    JSONArray json_array;
    HttpClient client;
    String name;
    ArrayList<NoticeJson> arraylist;

    private RecyclerView mRecentRecyclerView;
    private RecyclerView mOldRecyclerView;
    private RecyclerView.LayoutManager mRecentLayoutManager;
    private RecyclerView.LayoutManager mOldLayoutManager;
    private RecyclerView.Adapter<CustomViewHolder> mAdapter;
    private SearchView mSearchView;
    private Menu mMenu;
    private RelativeLayout emptyNotice;
    TextView test;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticeboard);


        client = new DefaultHttpClient();
        new Read().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.c9_toolbar); //Appcompat support for a sexier action bar
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.noticeboard_title);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.noticeboard_title);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);


        emptyNotice = (RelativeLayout) findViewById(R.id.rl_empty_notice);
        emptyNotice.setVisibility(View.GONE);

        test = (TextView) findViewById(R.id.test);


        initData();
        initRecyclerView();
        handleIntent(getIntent());

    }

    //drama begings
    //Json Part
    public JSONArray get_entire_json() throws IOException, JSONException {

        StringBuilder url = new StringBuilder(URL);
        HttpGet get = new HttpGet(url.toString());
        HttpResponse r = client.execute(get);

        int status = r.getStatusLine().getStatusCode();

        if (status == 200) {
            HttpEntity e = r.getEntity();
            String data = EntityUtils.toString(e);
//            JSONObject full_json = new JSONObject(data);
            JSONArray full_json = new JSONArray(data);

            return full_json;
        } else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            return null;

        }
    }

    public class Read extends AsyncTask<String, Integer, String> {
        /**
         * progress dialog to show user that the backup is processing.
         */


        int i = 0;

        //Only getting the list of the subjects and getting basic info
        @Override
        protected String doInBackground(String... params) {
            try {
                //Getting the Json Array
                json_array = get_entire_json();

                //Array of NoticeJson ( Setters were not required )
//                NoticeJson[] noticeJsons = new NoticeJson[json_array.length()];
                arraylist = new ArrayList<>();


                for (int i = 0; i < json_array.length(); i++) {
                    try {
                        JSONObject jsonObject = json_array.getJSONObject(i);

                        arraylist.add(i, new NoticeJson());
                        arraylist.get(i).setId(jsonObject.getString("id"));
                        arraylist.get(i).setPosted_by(jsonObject.getString("posted_by"));
                        arraylist.get(i).setSubject(jsonObject.getString("subject"));
                        arraylist.get(i).setText(jsonObject.getString("text"));
                        arraylist.get(i).setTarget_group(jsonObject.getString("target_group"));
                        arraylist.get(i).setPosted_at(jsonObject.getString("posted_at"));
                        arraylist.get(i).setPath(jsonObject.getString("path"));
                    } catch (Exception e) {

                    }
//                    noticeJsons[i].setId(jsonObject.getString("id"));
//                    noticeJsons[i].posted_by = jsonObject.getString("posted_by");
//                    noticeJsons[i].subject = jsonObject.getString("subject");
//                    noticeJsons[i].text = jsonObject.getString("text");
//                    noticeJsons[i].target_group = jsonObject.getString("target_group");
//                    noticeJsons[i].posted_at = jsonObject.getString("posted_at");
//                    noticeJsons[i].path = jsonObject.getString("path");
                }


                return null;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Testing one case
            try {
                test.setText(json_array.getJSONObject(3).getString("text"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
// Adding subject of notification
            for (int i = 0; i < arraylist.size(); i++) {
                mItems.add(i, arraylist.get(i).subject);
            }

            mAdapter.notifyDataSetChanged();


            //   test.setText("Welcome " + name + "!");
            //loader.clearAnimation();
            //loader.setVisibility(View.GONE);
//            loader.setAnimation(loaderPop);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    //drama end

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    private void initData() {
        mItems = new ArrayList<String>();
        for (int i = 0; i < ITEMS_COUNT; i++) {
            mItems.add("Item " + (i + 1));
        }
    }

    private void initRecyclerView() {
        mRecentRecyclerView = (RecyclerView) findViewById(R.id.recentrecyclerView);
        mRecentRecyclerView.setHasFixedSize(true);
//        mOldRecyclerView = (RecyclerView) findViewById(R.id.oldrecyclerView);

        mRecentLayoutManager = new LinearLayoutManager(this);

//        mOldLayoutManager = new LinearLayoutManager(this);


        mRecentRecyclerView.setLayoutManager(mRecentLayoutManager);
//        mOldRecyclerView.setLayoutManager(mOldLayoutManager);


        mAdapter = new RecyclerView.Adapter<CustomViewHolder>() {
            @Override
            public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_snippet
                        , viewGroup, false);
                return new CustomViewHolder(view);
            }

            @Override
            public void onBindViewHolder(CustomViewHolder viewHolder, int i) {
                viewHolder.noticeSubject.setText(mItems.get(i));
               // viewHolder.noticeBody.setText(arraylist.get(i).text);
            }

            @Override
            public int getItemCount() {
                return mItems.size();
            }


        };
        mRecentRecyclerView.setAdapter(mAdapter);
//        mOldRecyclerView.setAdapter(mAdapter);

        if (mAdapter.getItemCount() == 0) {
            emptyNotice.setVisibility(View.VISIBLE);
            mRecentRecyclerView.setVisibility(View.GONE);

        } else {
            emptyNotice.setVisibility(View.GONE);
            mRecentRecyclerView.setVisibility(View.VISIBLE);

        }


        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(
                        mRecentRecyclerView,
                        new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    mLayoutManager.removeView(mLayoutManager.getChildAt(position));
                                    mItems.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(NoticeBoard.this, "Notice Removed", Toast.LENGTH_SHORT).show();

                                //If no notice available,

                                if (mItems.size() == 0) {
                                    //mRecentRecyclerView.setVisibility(View.GONE);
                                    emptyNotice.setVisibility(View.VISIBLE);
                                }

                            }
                        });
        mRecentRecyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        mRecentRecyclerView.setOnScrollListener(touchListener.makeScrollListener());
        mRecentRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(NoticeBoard.this, "Clicked " + mItems.get(position), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getBaseContext(), NoticeViewer.class);

                        View noticeSubj = view.findViewById(R.id.notice_subject);
                        View noticeIcon = view.findViewById(R.id.group_icon);
                        View noticeBody = view.findViewById(R.id.notice_body);


                        String subjectTransitionName = getString(R.string.transition_notice);
                        String groupIconTransitionName = getString(R.string.transition_group_icon);
                        String bodyTransitionName = getString(R.string.transition_notice_body);
                        String cardTransitionName = getString(R.string.transition_notice_card);


                        ActivityOptionsCompat options =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(NoticeBoard.this,
                                        Pair.create(noticeSubj, subjectTransitionName),
                                        Pair.create(noticeIcon, groupIconTransitionName),
                                        Pair.create(noticeBody, bodyTransitionName)//,
                                        //Pair.create(view, cardTransitionName)
                                );

                        ActivityCompat.startActivity(NoticeBoard.this, i, options.toBundle());
                    }
                }));

    }


    private class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView noticeSubject;
        private TextView noticeBody;

        public CustomViewHolder(View itemView) {
            super(itemView);

            noticeSubject = (TextView) itemView.findViewById(R.id.notice_subject);
            noticeBody = (TextView) itemView.findViewById(R.id.notice_body);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar

        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search_notice);
        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.search_notice).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setMaxWidth(3800);
        SearchView.SearchAutoComplete theTextArea = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        theTextArea.setTextColor(Color.WHITE);//or any color that you want

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setSubmitButtonEnabled(true);
        //searchView.setOnQueryTextListener(this);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_notice:
                //onSearchRequested();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onSearchRequested() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            MenuItem mi = mMenu.findItem(R.id.search_notice);
            if (mi.isActionViewExpanded()) {
                mi.collapseActionView();
            } else {
                mi.expandActionView();
            }
        } else {
            //onOptionsItemSelected(mMenu.findItem(R.id.search));
        }
        return super.onSearchRequested();
    }


    public boolean onQueryTextChange(String newText) {
        Toast.makeText(NoticeBoard.this, "Query = " + newText, Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(NoticeBoard.this, "Query = " + query + " : submitted", Toast.LENGTH_SHORT).show();
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }
}