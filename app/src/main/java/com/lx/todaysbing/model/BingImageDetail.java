package com.lx.todaysbing.model;

import bing.com.Image;

/**
 * Created by liuxue on 2015/9/26.
 */
public class BingImageDetail extends ImageDetail<bing.com.Image> {

    // http://cn.bing.com/coverstory?date=20150926
    public static final String URL_SHARE = "http://cn.bing.com/coverstory?date=";

    private String mkt;

    public BingImageDetail(Image object, String[] resolutions, String mkt) {
        super(object, resolutions);
        this.mkt = mkt;

        this.title = object.copyright;
        this.description = object.copyright;
        this.copyRight = object.copyright;

        String[] copyrightParts = object.getSplitCopyright();
        this.copyRightLeft = copyrightParts[0];
        this.copyRightRight = copyrightParts[1];
    }

    @Override
    public String getImageUrl(String resolution) {
        String url = Image.rebuildImageUrl(getData(), resolution);
        return url;
    }

    @Override
    public String getShareUrl(String resolution) {
        if (!Image.MKT_ZH_CN.equals(this.mkt)) {
            return this.getImageUrl(resolution);
        }
        return URL_SHARE + getData().enddate;
    }

    @Override
    public String getShareImageUrl() {
        return this.getImageUrl(this.resolutions[0]);
    }
}
