//package com.lx.todaysbing.wxapi;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.lx.todaysbing.R;
//import com.tencent.mm.sdk.openapi.BaseReq;
//import com.tencent.mm.sdk.openapi.BaseResp;
//import com.tencent.mm.sdk.openapi.ConstantsAPI;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
//import net.sourceforge.simcpux.Constants;
//
///**
// * Created by liuxue on 2015/9/30.
// */
//public class WXEntryActivityBAK extends Activity implements IWXAPIEventHandler {
//    private static final String TAG = WXEntryActivityBAK.class.getCanonicalName();
//
//    // IWXAPI 是第三方app和微信通信的openapi接口
//    private IWXAPI api;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
//
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    public void onReq(BaseReq req) {
//        Log.d(TAG, "onReq() req:" + req);
//        Log.d(TAG, "onReq() req.getType():" + req.getType());
//        switch (req.getType()) {
//            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
////                goToGetMsg();
//                break;
//            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
////                goToShowMsg((ShowMessageFromWX.Req) req);
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        Log.d(TAG, "onResp() resp:" + resp);
//        Log.d(TAG, "onResp() resp.getType():" + resp.getType());
//        Log.d(TAG, "onResp() resp.errCode:" + resp.errCode);
//        Log.d(TAG, "onResp() resp.errStr:" + resp.errStr);
//
//        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
//            int result = 0;
//
//            switch (resp.errCode) {
//                case BaseResp.ErrCode.ERR_OK:
//                    result = R.string.errcode_success;
//                    break;
//                case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    result = R.string.errcode_cancel;
//                    break;
//                case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                    result = R.string.errcode_deny;
//                    break;
//                default:
//                    result = R.string.errcode_unknown;
//                    break;
//            }
//
//            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//        }
//
//        finish();
//    }
//}
