package com.lx.todaysbing.model;

import binggallery.chinacloudsites.cn.Image;

/**
 * Created by liuxue on 2015/9/26.
 */
public class BingGalleryImageDetail extends ImageDetail<binggallery.chinacloudsites.cn.Image> {

    public BingGalleryImageDetail(Image object, String[] resolutions) {
        super(object, resolutions);

        this.title = object.getCopyright();
        this.description = object.getCopyright();
        this.copyRight = object.getCopyright();

        String[] strings = object.getSplitCopyright();
        this.copyRightLeft = (strings == null && strings.length>=1) ? null : strings[0];
        this.copyRightRight = (strings == null && strings.length>=2) ? null : strings[1];
    }

    @Override
    public String getImageUrl(String resolution) {
        String url = binggallery.chinacloudsites.cn.Image.getImageUrl(getData(), resolution);
        return url;
    }

    @Override
    public String getShareUrl(String resolution) {
        return getImageUrl(resolution);
    }
}
