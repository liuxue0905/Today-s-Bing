package com.lx.todaysbing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.lx.todaysbing.R;
import com.lx.todaysbing.fragment.BingImageDetailFragment;
import com.lx.todaysbing.model.Image;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.lx.todaysbing.util.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class BingImageDetailActivity extends AppCompatActivity {

    private static final String TAG = "BingImageDetailActivity";

    private static final String EXTRA_IMAGE = "Image";
    private static final String EXTRA_RESOLUTION = "Resolution";

    private Image mImage;
    private String mResolution;

//    @InjectView(R.id.fakeStatusBar)
//    View fakeStatusBar;

    public static void action(Context context, Image image, String resolution) {
        Intent intent = new Intent(context, BingImageDetailActivity.class);
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

        mImage = (Image) getIntent().getSerializableExtra(EXTRA_IMAGE);
        mResolution = getIntent().getStringExtra(EXTRA_RESOLUTION);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BingImageDetailFragment.newInstance(mImage, mResolution))
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
                mResolution = resolution;

                BingImageDetailFragment fragment = (BingImageDetailFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                fragment.bind(mImage, mResolution);

                Map<String, String> map = new HashMap<>();
                map.put("resolution", resolution);
                MobclickAgent.onEvent(this, MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_ONITEMCLICK_RESOLUTION, map);

                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
