package com.longer.verifyedittext;

import android.graphics.drawable.Drawable;

interface IPhoneCode {

    void setOnVCodeCompleteListener(OnVCodeInputListener OnVCodeInputListener);

    /**
     * 输入完成回调接口
     */
    interface OnVCodeInputListener {
        //完成输入
        void vCodeComplete(String verificationCode);

        //当验证码变化的时候 未完成输入
        void vCodeIncomplete(String verificationCode);
    }

    /**
     * 设置长度
     */
    void setCodeLength(int size);

    //输入框默认背景
    void setTvBgNormal(Drawable tvBgNormal);
    void setTvBgNormal(int tvBgNormal);

    //输入框焦点背景
    void setTvBgFocus(Drawable tvBgFocus);
    void setTvBgFocus(int tvBgFocus);

    //输入框的间距
    void setTvMargin(int tvMargin);

    //TextView宽
    void setTvWidth(int tvWidth);

    //TextView高
    void setTvHeight(int tvHeight);

    //TextView字体颜色
    void setTvTextColor(int tvTextColor);

    //TextView字体大小
    void setTvTextSize(float tvTextSize);

    //输入框样式  1000：方框（默认）  1001：圆圈  1002：下划线
    void setTvBgStyle(int tvBgStyle);

    //正常时边框颜色
    void setTvNormalColorStroke(int tvNormalColorStroke);

    //正常时内容颜色
    void setTvNormalColorContent(int tvNormalColorContent);

    //选中时边框颜色
    void setTvFocusColorStroke(int tvFocusColorStroke);

    //选中时内容颜色
    void setTvFocusColorContent(int tvFocusColorContent);

    //TextView是否加粗
    void setBold(Boolean bold);

    //边框的宽度
    void setTvStrokeSize(int tvStrokeSize);

    //是否输入类型为数字
    void setNumber(Boolean number);

    //是否输入隐藏为密码符号
    void setShowPwd(Boolean showPwd);
}
