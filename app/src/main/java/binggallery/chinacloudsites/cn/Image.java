package binggallery.chinacloudsites.cn;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxue on 2015/6/10.
 *
 * <p>
 * http://binggallery.chinacloudsites.cn/api/image/list
 * <br/>
 * 397926db-ff87-4bb7-7713-c13d0e97ed94~s~w~�����������������ҹ�԰��һȺ�ڰ��� (? Jonathan & Angela Scott/Getty Images)~�˾����󰲣������ܺ󶨣������ܺ�ۣ��۶��ܺ�������ܺ�á�����ת������
 * </p>
 * s:320x180
 * w:1920x1200
 * l:958x512
 */
public class Image implements Serializable {

    private static final long serialVersionUID = 0L;

    public static final String RESOLUTION_CEDE_S = "s";
    public static final String RESOLUTION_CEDE_L = "l";
    public static final String RESOLUTION_CEDE_W = "w";

    public static final String RESOLUTION_VALUE_S = "320x180";
    public static final String RESOLUTION_VALUE_L = "958x512";
    public static final String RESOLUTION_VALUE_W = "1920x1200";

    private String raw;

    private String uid;
    private String minpix;
    private String maxpix;
    private String copyright;
    private String desc;

    public Image(String raw) {
//        setRaw(raw);

        this.raw = raw;
        String[] splitRaw = raw.split("~");

        this.uid = arrayIndex(splitRaw, 0);
        this.minpix = arrayIndex(splitRaw, 1);
        this.maxpix = arrayIndex(splitRaw, 2);
        this.copyright = arrayIndex(splitRaw, 3);
        this.desc = arrayIndex(splitRaw, 4);
    }

    public String getUid() {
        return uid;
    }

    public String getMinpix() {
        return minpix;
    }

    public String getMaxpix() {
        return maxpix;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Image{" +
                "raw='" + raw + '\'' +
                ", uid='" + uid + '\'' +
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
        Log.d("LX", "parse()");
        Image[] parsedImages = null;
        Pattern pattern = Pattern.compile("(\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12})~(s)~([lw])~([^~]*)(~([^~]*))?((?=\\r\\n)|$)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(parsed);

        List<Image> images = new ArrayList<>();

        while (matcher.find()) {

            Log.d("LX", "parse() matcher.group():" + matcher.group());
            images.add(new Image(matcher.group(0)));

            int groupCount = matcher.groupCount();
            Log.d("LX", "parse() groupCount:" + groupCount);
            for (int i = 0; i < groupCount; i++) {
                Log.d("LX", "parse() matcher.group(" + i + "):" + matcher.group(i));
            }
        }

        parsedImages = images.toArray(new Image[]{});

        return parsedImages;
    }
}