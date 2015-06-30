package com.lx.todaysbing.event;

import bing.com.HPImageArchive;

/**
 * Created by liuxue on 2015/6/29.
 */
public class OnHPImageArchiveSuccessEvent {

    private final HPImageArchive hpImageArchive;

    public OnHPImageArchiveSuccessEvent(HPImageArchive hpImageArchive) {
        this.hpImageArchive = hpImageArchive;
    }


    public HPImageArchive getHPImageArchive() {
        return hpImageArchive;
    }
}
