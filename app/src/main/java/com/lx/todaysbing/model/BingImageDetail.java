package com.lx.todaysbing.model;

import bing.com.Image;

/**
 * Created by liuxue on 2015/9/26.
 */
public class BingImageDetail extends ImageDetail<bing.com.Image> {

    public BingImageDetail(Image object, String[] resolutions) {
        super(object, resolutions);

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
}
