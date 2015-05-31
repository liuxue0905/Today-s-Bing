package com.lx.todaysbing.umeng;

/**
 * Created by liuxue on 2015/5/31.
 */
public class MobclickAgentHelper {

    public static class BingImageMain {
        public static final String EVENT_ID_BINGIMAGEMAIN = "BingImageMain";
        public static final String EVENT_ID_BINGIMAGEMAIN_ONITEMCLICK_MARKET = EVENT_ID_BINGIMAGEMAIN + '_' + "onItemClick_Market";
        public static final String EVENT_ID_BINGIMAGEMAIN_SETTINGS = EVENT_ID_BINGIMAGEMAIN + '_' + "settings";
        public static final String EVENT_ID_BINGIMAGEMAIN_UPDATE = EVENT_ID_BINGIMAGEMAIN + '_' + "update";
        public static final String EVENT_ID_BINGIMAGEMAIN_SHARE = EVENT_ID_BINGIMAGEMAIN + '_' + "share";;
    }

    public static class BingImageToday {
        public static final String EVENT_ID_BINGIMAGETODAY = "BingImageToday";
        public static final String EVENT_ID_BINGIMAGETODAY_MKT = EVENT_ID_BINGIMAGETODAY + '_' + "mkt";
        public static final String EVENT_ID_BINGIMAGETODAY_DETAIL = EVENT_ID_BINGIMAGETODAY + '_' + "detail";
    }

    public static class BingImageNDay {
        public static final String EVENT_ID_BINGIMAGENDAY = "BingImageNDay";
        public static final String EVENT_ID_BINGIMAGENDAY_ONITEMCLICK = EVENT_ID_BINGIMAGENDAY + '_' + "onItemClick";
    }

    public static class BingImageDetail {
        public static final String EVENT_ID_BINGIMAGEDETAIL = "BingImageDetail";
        public static final String EVENT_ID_BINGIMAGENDAY_RESOLUTION = EVENT_ID_BINGIMAGEDETAIL + '_' + "Resolution";
        public static final String EVENT_ID_BINGIMAGENDAY_SAVE = EVENT_ID_BINGIMAGEDETAIL + '_' + "Save";
        public static final String EVENT_ID_BINGIMAGENDAY_LOCATION = EVENT_ID_BINGIMAGEDETAIL + '_' + "Location";
        public static final String EVENT_ID_BINGIMAGENDAY_ONITEMCLICK_RESOLUTION = EVENT_ID_BINGIMAGEDETAIL + '_' + "onItemClick_Resolution";
    }

}
