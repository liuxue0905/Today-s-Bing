package bing.com;

import java.io.Serializable;

/**
 * Created by liuxue on 2015/5/7.
 */
public class Tooltips implements Serializable {

    private static final long serialVersionUID = 0L;

    public String loading;

    public String previous;

    public String next;

    public String walle;

    public String walls;

    @Override
    public String toString() {
        return "Tooltips{" +
                "loading='" + loading + '\'' +
                ", previous='" + previous + '\'' +
                ", next='" + next + '\'' +
                ", walle='" + walle + '\'' +
                ", walls='" + walls + '\'' +
                '}';
    }
}
