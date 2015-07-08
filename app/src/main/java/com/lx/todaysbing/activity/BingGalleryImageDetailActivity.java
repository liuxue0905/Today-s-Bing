package com.lx.todaysbing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.lx.todaysbing.R;
import com.lx.todaysbing.fragment.BingGalleryImageDetailFragment;
import com.lx.todaysbing.fragment.BingImageDetailFragment;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import binggallery.chinacloudsites.cn.Image;
import butterknife.ButterKnife;

/**
 * Created by liuxue on 2015/6/24.
 */
public class BingGalleryImageDetailActivity extends AppCompatActivity {

    private static final String TAG = "BingImageDetailActivity";

    private static final String EXTRA_COLOR = "color";
    private static final String EXTRA_IMAGE = "Image";
    private static final String EXTRA_RESOLUTION = "Resolution";

    private String mColor;
    private Image mImage;
    private String mResolution;

//    @InjectView(R.id.fakeStatusBar)
//    View fakeStatusBar;

    public static void action(Context context, String color, Image image, String resolution) {
        Intent intent = new Intent(context, BingGalleryImageDetailActivity.class);
        intent.putExtra(EXTRA_COLOR, color);
        intent.putExtra(EXTRA_IMAGE, image);
        intent.putExtra(EXTRA_RESOLUTION, resolution);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() savedInstanceState:" + savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bing_image_detail);
        ButterKnife.inject(this);

//        Utils.setupFakeStatusBarHeightOnGlobalLayout(this, fakeStatusBar);

        mColor = getIntent().getStringExtra(EXTRA_COLOR);
        mImage = (Image) getIntent().getSerializableExtra(EXTRA_IMAGE);
        mResolution = getIntent().getStringExtra(EXTRA_RESOLUTION);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BingGalleryImageDetailFragment.newInstance(mColor, mImage, mResolution, null))
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ResolutionActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String resolution = data.getStringExtra("resolution");
                Log.d(TAG, "onActivityResult() resolution:" + resolution);

                BingGalleryImageDetailFragment fragment = (BingGalleryImageDetailFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                fragment.bind(mImage, mResolution, resolution);

                Map<String, String> map = new HashMap<>();
                map.put("resolution", resolution);
                MobclickAgent.onEvent(this, MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_ONITEMCLICK_RESOLUTION, map);

                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}