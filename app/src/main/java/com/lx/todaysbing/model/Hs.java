package com.lx.todaysbing.model;

import java.io.Serializable;

/**
 * Created by liuxue on 2015/5/7.
 */
public class Hs implements Serializable {

    private static final long serialVersionUID = 0L;

    public String desc;

    public String link;

    public String query;

    public int locx;

    public int locy;

    @Override
    public String toString() {
        return "Hs{" +
                "desc='" + desc + '\'' +
                ", link='" + link + '\'' +
                ", query='" + query + '\'' +
                ", locx=" + locx +
                ", locy=" + locy +
                '}';
    }
}
