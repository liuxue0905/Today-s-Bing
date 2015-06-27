package com.lx.todaysbing.event;

import binggallery.chinacloudsites.cn.Image;

/**
 * Created by liuxue on 2015/6/26.
 */
public class OnBingGalleryListOnResponseEvent {
    public Image[] images;

    public OnBingGalleryListOnResponseEvent(Image[] images) {
        this.images = images;
    }
}
