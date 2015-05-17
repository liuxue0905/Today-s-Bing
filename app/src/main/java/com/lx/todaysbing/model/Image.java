package com.lx.todaysbing.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuxue on 2015/5/7.
 */
public class Image implements Serializable {

    private static final long serialVersionUID = 0L;

    public String startdate;

    public String fullstartdate;

    public String enddate;

    public String url;

    public String urlbase;

    public String copyright;

    public String copyrightlink;

    public boolean wp;

    public String hsh;

    public int drk;

    public int top;

    public int bot;

    public List<Hs> hs;

    public List<Msg> msg;

    public Pano pano;

    public Vid vid;

    @Override
    public String toString() {
        return "Image{" +
                "startdate='" + startdate + '\'' +
                ", fullstartdate='" + fullstartdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", url='" + url + '\'' +
                ", urlbase='" + urlbase + '\'' +
                ", copyright='" + copyright + '\'' +
                ", copyrightlink='" + copyrightlink + '\'' +
                ", wp=" + wp +
                ", hsh='" + hsh + '\'' +
                ", drk=" + drk +
                ", top=" + top +
                ", bot=" + bot +
                ", hs=" + hs +
                ", msg=" + msg +
                ", pano=" + pano +
                ", vid=" + vid +
                '}';
    }
}
