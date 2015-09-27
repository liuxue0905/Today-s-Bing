package com.lx.todaysbing.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by liuxue on 2015/9/26.
 */
public abstract class ImageDetail<T> implements Serializable {
    private static final long serialVersionUID = 0L;

    private T object;

    public String title;
    public String description;
    public String copyRight;
    public String copyRightLeft;
    public String copyRightRight;

    public String[] resolutions;
    public Map<String, String> imageUrls;

    public ImageDetail(T object, String[] resolutions) {
        this.object = object;
        this.resolutions = resolutions;
    }

    public T getData() {
        return object;
    }

    public abstract String getImageUrl(String resolution);
}
