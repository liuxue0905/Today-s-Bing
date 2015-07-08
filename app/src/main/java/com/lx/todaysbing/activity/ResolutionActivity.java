package com.lx.todaysbing.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;

import com.lx.todaysbing.R;
import com.lx.todaysbing.adapter.MarketAdapter;
import com.lx.todaysbing.adapter.ResolutionAdapter;
import com.lx.todaysbing.util.Utils;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ResolutionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String EXTRA_RESOLUTIONS = "resolutions";
    public static final String EXTRA_RESOLUTION = "resolution";
    public static final int REQUEST_CODE = 1;

    @InjectView(R.id.fakeStatusBar)
    View fakeStatusBar;
    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ResolutionAdapter mAdapter;

    private String[] mResolutions;

    public static void action(Activity activity, int requestCode, String[] resolutions, String resolution) {
        Intent intent = new Intent(activity, ResolutionActivity.class);
        intent.putExtra(EXTRA_RESOLUTIONS, resolutions);
        intent.putExtra(EXTRA_RESOLUTION, resolution);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolution);
        ButterKnife.inject(this);

        mResolutions = getIntent().getStringArrayExtra(EXTRA_RESOLUTIONS);

        Utils.setupFakeStatusBarHeightOnGlobalLayout(this, fakeStatusBar);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ResolutionAdapter(this, mResolutions);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_resolution, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String resolution = mAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("resolution", resolution);
        setResult(RESULT_OK, intent);
        finish();
    }
}
