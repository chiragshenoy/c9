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
import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class NoticeBoard extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private static final int ITEMS_COUNT = 5;
    private List<String> mItems;

    private RecyclerView mRecentRecyclerView;
    private RecyclerView mOldRecyclerView;
    private RecyclerView.LayoutManager mRecentLayoutManager;
    private RecyclerView.LayoutManager mOldLayoutManager;
    private RecyclerView.Adapter<CustomViewHolder> mAdapter;
    private SearchView mSearchView;
    private Menu mMenu;
    private RelativeLayout emptyNotice;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticeboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.c9_toolbar); //Appcompat support for a sexier action bar
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.noticeboard_title);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.noticeboard_title);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);

        emptyNotice = (RelativeLayout) findViewById(R.id.rl_empty_notice);
        emptyNotice.setVisibility(View.GONE);


        initData();
        initRecyclerView();
        handleIntent(getIntent());
    }

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
            }

            @Override
            public int getItemCount() {
                return mItems.size();
            }

        };
        mRecentRecyclerView.setAdapter(mAdapter);
//        mOldRecyclerView.setAdapter(mAdapter);

        if(mAdapter.getItemCount()==0){
            emptyNotice.setVisibility(View.VISIBLE);
            mRecentRecyclerView.setVisibility(View.GONE);

        }
        else{
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

                                if(mItems.size()==0){
                                    mRecentRecyclerView.setVisibility(View.GONE);
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
                    }
                }));
    }


    private class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView noticeSubject;

        public CustomViewHolder(View itemView) {
            super(itemView);

            noticeSubject = (TextView) itemView.findViewById(R.id.notice_subject);
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
        SearchView.SearchAutoComplete theTextArea = (SearchView.SearchAutoComplete)searchView.findViewById(R.id.search_src_text);
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
            if(mi.isActionViewExpanded()){
                mi.collapseActionView();
            } else{
                mi.expandActionView();
            }
        } else{
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