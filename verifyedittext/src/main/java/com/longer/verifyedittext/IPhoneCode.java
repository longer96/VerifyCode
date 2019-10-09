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
    void setBgNormal(Drawable tvBgNormal);

    void setBgNormal(int tvBgNormal);

    //输入框焦点背景
    void setBgFocus(Drawable tvBgFocus);

    void setBgFocus(int tvBgFocus);

    //输入框的间距
    void setCodeMargin(int tvMargin);

    //TextView宽
    void setTvWidth(int tvWidth);

    //TextView高
    void setTvHeight(int tvHeight);

    //TextView字体颜色
    void setCodeTextColor(int tvTextColor);

    //TextView字体大小

    /**
     * 单位px
     */
    void setCodeTextSize(float tvTextSize);

    //输入框样式  1000：方框（默认）  1001：圆圈  1002：下划线
    void setCodeStyle(int tvBgStyle);

    //正常时边框颜色
    void setNormalStrokeColor(int tvNormalColorStroke);

    //正常时内容颜色
    void setNormalContentColor(int tvNormalColorContent);

    //选中时边框颜色
    void setFocusStrokeColor(int tvFocusColorStroke);

    //选中时内容颜色
    void setFocusContentColor(int tvFocusColorContent);

    //TextView是否加粗
    void setBold(Boolean bold);

    //边框的宽度
    void setStrokeSize(int tvStrokeSize);

    //是否输入类型为数字
    void setNumber(Boolean number);

    //是否输入隐藏为密码符号
    void setShowPwd(Boolean showPwd);
}
