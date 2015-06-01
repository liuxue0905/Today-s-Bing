package com.lx.todaysbing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lx.todaysbing.activity.MarketActivity;
import com.lx.todaysbing.fragment.BingImagesFragment;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.lx.todaysbing.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements BingImagesFragment.OnBingImagesFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    private Context mContext;

    @InjectView(R.id.fakeStatusBar)
    View fakeStatusBar;

    private String mColor;
    private String mMkt;
//    private String mResolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.setupFakeStatusBarHeightOnGlobalLayout(this, fakeStatusBar);

        mColor = "#006AC1";
        mMkt = "zh-CN";
//        mResolution = Utils.getSuggestResolution(this);
//        Log.d(TAG, "onCreate() mResolution:" + mResolution);
//        Log.d(TAG, "onCreate() (savedInstanceState == null):" + (savedInstanceState == null));

        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BingImagesFragment.newInstance(mColor, mMkt/*, mResolution*/))
                    .commit();

            UmengUpdateAgent.setUpdateOnlyWifi(false);
            UmengUpdateAgent.update(this);
        }
//        else {
//
//        }
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
        if (id == R.id.action_settings) {
            MobclickAgent.onEvent(this, MobclickAgentHelper.BingImageMain.EVENT_ID_BINGIMAGEMAIN_SETTINGS);
            Toast.makeText(mContext, R.string.tips_im_coming_next_version, Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_update) {
            MobclickAgent.onEvent(this, MobclickAgentHelper.BingImageMain.EVENT_ID_BINGIMAGEMAIN_UPDATE);
            onActinUpdate();
            return true;
        }
        else if (id == R.id.action_share) {
            MobclickAgent.onEvent(this, MobclickAgentHelper.BingImageMain.EVENT_ID_BINGIMAGEMAIN_SHARE);
            Toast.makeText(mContext, R.string.tips_im_coming_next_version, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onActinUpdate() {
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setSlotId("65102");
        UmengUpdateAgent.forceUpdate(this);
        UmengUpdateAgent.setUpdateListener(null);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MarketActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//                String market = data.getStringExtra("market");
                mMkt = data.getStringExtra("mkt");

                BingImagesFragment fragment = (BingImagesFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                fragment.bind(mColor, mMkt);

                Map<String, String> map = new HashMap<>();
                map.put("mkt", mMkt);
//                map.put("market", market);
                MobclickAgent.onEvent(this, MobclickAgentHelper.BingImageMain.EVENT_ID_BINGIMAGEMAIN_ONITEMCLICK_MARKET, map);
                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
