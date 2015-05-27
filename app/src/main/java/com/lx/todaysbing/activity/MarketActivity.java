package com.lx.todaysbing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.lx.todaysbing.R;
import com.lx.todaysbing.adapter.MarketAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MarketActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String EXTRA_MARKET = "market";
    public static final int REQUEST_CODE = 1;

    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MarketAdapter mAdapter;

    public static void action(Activity activity, int requestCode, String market) {
        Intent intent = new Intent(activity, MarketActivity.class);
        intent.putExtra(EXTRA_MARKET, market);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        ButterKnife.inject(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MarketAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_market, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] mktArray = getResources().getStringArray(R.array.mkt);
        String mkt = mktArray[position];

        String markte = mAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("market", markte);
        intent.putExtra("mkt", mkt);
        setResult(RESULT_OK, intent);
        finish();
    }
}
