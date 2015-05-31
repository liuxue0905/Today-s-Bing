package com.lx.todaysbing;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.lx.todaysbing.util.ResolutionUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

//    @SmallTest
//    public void testAPI() {
//        APIImpl api = new APIImpl();
////        HPImageArchive data = api.getHPImageArchive(2, "zh-CN");
//        HPImageArchive data = api.getHPImageArchive("js", 1, 1, "zh-CN", 1);
//        System.out.println("data:" + new Gson().toJson(data));
//    }

    @SmallTest
    public void testResolution() {
//        String suggestResolutionString = ResolutionUtils.getSuggestResolutionStr(getContext(), "960x540");
//        String suggestResolutionString = ResolutionUtils.getSuggestResolution(getContext(), "540x960");
        String suggestResolutionString = ResolutionUtils.getSuggestResolution(getContext(), "720x1185");
//        String suggestResolutionString = ResolutionUtils.getSuggestResolutionStr(getContext(), "1920x1080");
//        String suggestResolutionString = ResolutionUtils.getSuggestResolutionStr(getContext(), "1280x720");
//        String suggestResolutionString = Utils.getSuggestResolutionStr(getContext());
        System.out.println("suggestResolutionString:" + suggestResolutionString);
    }
}