package com.lx.todaysbing.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuxue on 2015/5/12.
 */
public class Vid implements Serializable {

    private static final long serialVersionUID = 0L;

    public List<String[]> sources;

    public boolean loop;

    public String image;

    public String caption;

    public String captionlink;

    public String dark;
}
