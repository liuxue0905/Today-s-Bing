package com.lx.todaysbing.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    }

    @Override
    public String getImageUrl(String resolution) {
        String url = binggallery.chinacloudsites.cn.Image.getImageUrl(getData(), resolution);
        return url;
    }
}
