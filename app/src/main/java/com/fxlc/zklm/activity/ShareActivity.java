package com.fxlc.zklm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.Constant;
import com.fxlc.zklm.R;
import com.fxlc.zklm.util.BitmapUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


public class ShareActivity extends BaseActivity implements View.OnClickListener {
    Tencent mTencent;
    String appId = "1106195082";
    BaseUiListener listener;
    View shareQQ, shareWeChat;
    IWXAPI wxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mTencent = Tencent.createInstance(appId, getApplicationContext());
        wxApi = WXAPIFactory.createWXAPI(this, Constant.wxAppId, false);
        shareQQ = findViewById(R.id.share_qq);
        shareWeChat = findViewById(R.id.share_wx);
        shareQQ.setOnClickListener(this);
        shareWeChat.setOnClickListener(this);

    }

    public void shareQQ() {
        Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, Constant.share_url);
//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "我在测试");
//分享的图片URL
//        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
//                "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
//分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "zklm");

        mTencent.shareToQQ(this, bundle, listener);
    }

    public void shareWX() {

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = Constant.share_url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "网页标题";
        msg.description = "网页描述";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        msg.thumbData = BitmapUtil.bitmapToByte(thumb, Bitmap.CompressFormat.PNG, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        wxApi.sendReq(req);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_qq:
                shareQQ();
                break;
            case R.id.share_wx:
                shareWX();
                break;
            case R.id.share_timeline:
                shareWX();
                break;

        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    }
}
