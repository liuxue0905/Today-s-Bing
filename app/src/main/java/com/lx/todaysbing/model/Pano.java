package com.lx.todaysbing.model;

import java.io.Serializable;

/**
 * Created by liuxue on 2015/5/7.
 */
public class Pano implements Serializable {

    private static final long serialVersionUID = 0L;

    public String url;

    public String image;

    public boolean hs;

    public boolean an;

    public String cap;

    public String caplink;

    public double pos;

    public double maxfov;

    @Override
    public String toString() {
        return "Pano{" +
                "url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", hs=" + hs +
                ", an=" + an +
                ", cap='" + cap + '\'' +
                ", caplink='" + caplink + '\'' +
                ", pos=" + pos +
                ", maxfov=" + maxfov +
                '}';
    }
}
