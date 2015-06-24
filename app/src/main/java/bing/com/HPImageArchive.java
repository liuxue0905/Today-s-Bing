package bing.com;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuxue on 2015/5/7.
 */
public class HPImageArchive implements Serializable {

    private static final long serialVersionUID = 0L;

    public List<Image> images;

    public Tooltips tooltips;

    @Override
    public String toString() {
        return "HPImageArchive{" +
                "images=" + images +
                ", tooltips=" + tooltips +
                '}';
    }


}
