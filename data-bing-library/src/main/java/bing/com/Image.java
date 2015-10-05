package bing.com;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxue on 2015/5/7.
 */
public class Image implements Serializable {

    private static final long serialVersionUID = 0L;

    public static final String MKT_ZH_CN = "zh-CN";

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

    //China
    // url:http://s.cn.bing.net/az/hprichbg/rb/FudanAni_ZH-CN13023015076_1920x1080.jpg
    // urlbase:/az/hprichbg/rb/FudanAni_ZH-CN13023015076
    // Other
    // url:/az/hprichbg/rb/GoldenGateFogVideo_EN-US10020580773_1920x1080.jpg
    // urlbase:/az/hprichbg/rb/GoldenGateFogVideo_EN-US10020580773
    // China->Other
    // http://global.bing.com/az/hprichbg/rb/PalaisDuPharo_ZH-CN6551548558_1920x1080.jpg

    //[a-zA-z]+://[^\s]*_(\d+x\d+).jpg$
//    public static String rebuildImageUrl(String url, String suggestResolutionString) {
//
////        Pattern p = Pattern.compile("(\\d+x\\d+)");
////        Matcher m = p.matcher(url);
////        if (m.find()) {
////            return m.replaceAll(suggestResolutionString);
////        }
////        return url;
//
//        return "http://global.bing.com" + url + "_" + suggestResolutionString + ".jpg";
//    }

    public static String rebuildImageUrl(Image image, String resolution) {
        if (image == null)
            return null;
        return "http://global.bing.com" + image.urlbase + "_" + resolution + ".jpg";
    }

    public String[] getSplitCopyright() {
        return splitCopyRight(this.copyright, "(.*)\\s*\\((.+)\\)");
    }

    private static String[] splitCopyRight(String copyright, String pattern) {
        String[] ret = new String[2];

        if (copyright != null && copyright.length() != 0) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(copyright);
            if (m.find()) {
                ret[0] = m.group(1);
                ret[1] = m.group(2);
            }
        }

        return ret;
    }
}
