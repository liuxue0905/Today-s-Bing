package com.lx.todaysbing.model;

import java.io.Serializable;

/**
 * Created by liuxue on 2015/5/7.
 */
public class Msg implements Serializable {

    private static final long serialVersionUID = 0L;

    public String title;

    public String link;

    public String text;

    @Override
    public String toString() {
        return "Msg{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
