package binggallery.chinacloudsites.cn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxue on 2015/6/10.
 *
 * <p>
 * http://binggallery.chinacloudsites.cn/api/image/list
 * <br/>
 * 397926db-ff87-4bb7-7713-c13d0e97ed94~s~w~肯尼亚马赛马拉国家公园，一群黑斑羚 (? Jonathan & Angela Scott/Getty Images)~人静而后安，安而能后定，定而能后慧，慧而能后悟，悟而能后得。——转自网络
 * </p>
 * s:320x180
 * w:1920x1200
 * l:958x512
 */
public class Image implements Serializable {

    private static final long serialVersionUID = 0L;

    public static final String API_LIST = "http://binggallery.chinacloudsites.cn/api/image/list";

    public static final String RESOLUTION_CEDE_S = "s";
    public static final String RESOLUTION_CEDE_L = "l";
    public static final String RESOLUTION_CEDE_W = "w";

    public static final String RESOLUTION_VALUE_S_SMALL = "320x180";
    public static final String RESOLUTION_VALUE_S_LARGE = "800x480";
    public static final String RESOLUTION_VALUE_L = "958x512";
    public static final String RESOLUTION_VALUE_W = "1920x1200";

    private String raw;

    private String uid;
    private String minpix;
    private String maxpix;
    private String copyright;
    private String desc;

    public Image(String raw) {
        this.raw = raw;

        String[] splitRaw = raw.split("~");

        this.uid = arrayIndex(splitRaw, 0);
        this.minpix = arrayIndex(splitRaw, 1);
        this.maxpix = arrayIndex(splitRaw, 2);
        this.copyright = arrayIndex(splitRaw, 3);
        this.desc = arrayIndex(splitRaw, 4);
    }

    public Image(String uid, String minpix, String maxpix, String copyright, String desc) {
        this.uid = uid;
        this.minpix = minpix;
        this.maxpix = maxpix;
        this.copyright = copyright;
        this.desc = desc;
    }

    /** Not-null value. */
    public String getUid() {
        return uid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMinpix() {
        return minpix;
    }

    public void setMinpix(String minpix) {
        this.minpix = minpix;
    }

    public String getMaxpix() {
        return maxpix;
    }

    public void setMaxpix(String maxpix) {
        this.maxpix = maxpix;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Image{" +
                "uid='" + uid + '\'' +
                ", minpix='" + minpix + '\'' +
                ", maxpix='" + maxpix + '\'' +
                ", copyright='" + copyright + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static <T> T arrayIndex(T[] array, int idx) {
        if (array != null && array.length >= idx + 1) {
            return array[idx];
        }
        return null;
    }

    public String[] getSplitCopyright() {
        if (this.copyright == null) {
            return null;
        }

        String[] a1 = splitCopyRight(this.copyright, "(.*)\\s*\\((.+)\\)");
        if (a1 == null) {
            a1 = splitCopyRight(this.copyright, "(.*)\\s*--\\s*(.*)");
        }
        if (a1 == null) {
            a1 = new String[2];
            a1[0] = this.copyright;
        }
        return a1;
    }

    /**
     *
     * @param copyright
     * @return
     */
    private static String[] splitCopyRight(String copyright, String pattern) {
        if (copyright != null && copyright.length() != 0) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(copyright);
            if (m.find()) {
                String[] ret = new String[2];

                ret[0] = m.group(1);
                ret[1] = m.group(2);

                return ret;
            }
        }

        return null;
    }

    // http://portalvhdsdhkv82t6k91cd.blob.core.chinacloudapi.cn/binggallery/6b2218ab-91b6-4683-91b9-15699da160a6_s.jpg

    /**
     * s
     * @return
     */
    public String getMinpixUrl() {
        return getImageUrl(minpix);
    }

    /**
     * w|l
     * @return
     */
    public String getMaxpixUrl() {
        return getImageUrl(maxpix);
    }

    public String getImageUrl(String slw) {
        return "http://portalvhdsdhkv82t6k91cd.blob.core.chinacloudapi.cn/binggallery/" + uid + "_" + slw + ".jpg";
    }

    public static Image[] parse(String parsed) {
//        Log.d("LX", "parse()");

        if (parsed == null) {
            return null;
        }

        Image[] parsedImages = null;
        Pattern pattern = Pattern.compile("(\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12})~(s)~([lw])~([^~]*)(~([^~]*))?((?=\\r\\n)|$)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(parsed);

        List<Image> images = new ArrayList<>();

        while (matcher.find()) {

//            Log.d("LX", "parse() matcher.group():" + matcher.group());
            images.add(new Image(matcher.group(0)));

//            int groupCount = matcher.groupCount();
//            Log.d("LX", "parse() groupCount:" + groupCount);
//            for (int i = 0; i < groupCount; i++) {
//                Log.d("LX", "parse() matcher.group(" + i + "):" + matcher.group(i));
//            }
        }

        parsedImages = images.toArray(new Image[]{});

        return parsedImages;
    }

    public static String getImageUrl(binggallery.chinacloudsites.cn.Image image, String imageResolution) {
        String url = image.getMaxpixUrl();
        if (binggallery.chinacloudsites.cn.Image.RESOLUTION_VALUE_L.equalsIgnoreCase(imageResolution)) {
            url = image.getImageUrl(binggallery.chinacloudsites.cn.Image.RESOLUTION_CEDE_L);
        } else if (binggallery.chinacloudsites.cn.Image.RESOLUTION_VALUE_W.equalsIgnoreCase(imageResolution)) {
            url = image.getImageUrl(binggallery.chinacloudsites.cn.Image.RESOLUTION_CEDE_W);
        }
        return url;
    }
}
