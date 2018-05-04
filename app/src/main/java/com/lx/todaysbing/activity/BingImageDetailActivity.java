package com.lx.todaysbing.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.lx.todaysbing.R;
import com.lx.todaysbing.fragment.BingImageDetailFragment;
import com.lx.todaysbing.model.ImageDetail;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;


public class BingImageDetailActivity extends AppCompatActivity implements BingImageDetailFragment.OnBingImageDetailFragmentInteractionListener {

    private static final String TAG = "BingImageDetailActivity";

    private static final String EXTRA_COLOR = "color";
    private static final String EXTRA_IMAGE_DETAIL = "Image";
    private static final String EXTRA_RESOLUTION = "Resolution";

    private String mColor;
    private String mResolution;
    private ImageDetail mImageDetail;

//    @InjectView(R.id.fakeStatusBar)
//    View fakeStatusBar;

    public static void action(Context context, String color, ImageDetail imageDetail, String resolution) {
        Intent intent = new Intent(context, BingImageDetailActivity.class);
        intent.putExtra(EXTRA_COLOR, color);
        intent.putExtra(EXTRA_IMAGE_DETAIL, imageDetail);
        intent.putExtra(EXTRA_RESOLUTION, resolution);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() savedInstanceState:" + savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bing_image_detail);

//        Utils.setupFakeStatusBarHeightOnGlobalLayout(this, fakeStatusBar);

        mColor = getIntent().getStringExtra(EXTRA_COLOR);
        mImageDetail = (ImageDetail) getIntent().getSerializableExtra(EXTRA_IMAGE_DETAIL);
        mResolution = getIntent().getStringExtra(EXTRA_RESOLUTION);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BingImageDetailFragment.newInstance(mColor, mImageDetail, mResolution, null))
                    .commit();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bing_image_detail, menu);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ResolutionActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String resolution = data.getStringExtra("resolution");

                BingImageDetailFragment fragment = (BingImageDetailFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                fragment.onResolutionChanged(resolution);

                Map<String, String> map = new HashMap<>();
                map.put("resolution", resolution);
                MobclickAgent.onEvent(this, MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_ONITEMCLICK_RESOLUTION, map);

                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBingImageDetailFragmentInteraction(Uri uri) {

    }
}
