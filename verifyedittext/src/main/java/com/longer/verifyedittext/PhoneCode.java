package com.longer.verifyedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PhoneCode extends RelativeLayout implements IPhoneCode {
//TODO   验证码输入完成后失去焦点
//TODO : 键盘的弹出隐藏

    //暴露给外面的接口
    private IPhoneCode.OnVCodeInputListener onVCodeInputListener;

    //输入的长度
    private int codeLength = 5;
    //输入的内容
    private String inputData = "";
    private EditText editText;

    //TextView的list
    private List<TextView> tvList = new ArrayList<>();
    //输入框默认背景
    private Drawable tvBgNormal = getResources().getDrawable(R.drawable.verify_rectangel_bg_normal);
    //输入框焦点背景
    private Drawable tvBgFocus = getResources().getDrawable(R.drawable.verify_rectangle_bg_focus);
    //是否设置了自定义默认背景
    private Boolean isSetTvBgNormal = false;
    //是否设置了自定义焦点背景
    private Boolean isSetTvBgFocus = false;
    //输入框的间距
    private int tvMarginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
    //TextView宽
    private int tvWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
    //TextView高
    private int tvHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
    //TextView字体颜色
    private int tvTextColor = Color.BLACK;
    //TextView字体大小
    private float tvTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 33, getResources().getDisplayMetrics());

    //输入框样式  1000：方框（默认）  1001：圆圈  1002：下划线
    private int tvBgStyle = 1000;
    //正常时边框颜色
    private int tvNormalColorStroke = Color.parseColor("#bbbbbb");
    //正常时内容颜色
    private int tvNormalColorContent = Color.TRANSPARENT;
    //选中时边框颜色
    private int tvFocusColorStroke = Color.parseColor("#108ee9");
    //选中时内容颜色
    private int tvFocusColorContent = Color.TRANSPARENT;
    //TextView是否加粗
    private Boolean isBold = true;
    //边框的宽度
    private int tvStrokeSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
    //是否输入类型为数字
    private Boolean isNumber = true;
    //是否输入隐藏为密码符号
    private Boolean isShowPwd = false;

    public PhoneCode(Context context) {
        this(context, null);
    }

    public PhoneCode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义样式的属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PhoneCode, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.PhoneCode_codeLength) {
                //验证码长度
                codeLength = typedArray.getInteger(attr, 6);
            } else if (attr == R.styleable.PhoneCode_codeTextColor) {
                //验证码字体颜色
                tvTextColor = typedArray.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.PhoneCode_codeTextSize) {
                //验证码字体大小
                tvTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 33, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_tvWidth) {
                //方框宽度
                tvWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_tvHeight) {
                //方框宽度
                tvHeight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_codeMargin) {
                //方框间隔
                tvMarginRight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_bgNormal) {
                //默认背景
                int mtvBgNormal = typedArray.getResourceId(attr, R.drawable.verify_rectangel_bg_normal);
                tvBgNormal = getResources().getDrawable(mtvBgNormal);
                isSetTvBgNormal = true;
            } else if (attr == R.styleable.PhoneCode_bgFocus) {
                //焦点背景
                int mtvBgFocus = typedArray.getResourceId(attr, R.drawable.verify_rectangle_bg_focus);
                tvBgFocus = getResources().getDrawable(mtvBgFocus);
                isSetTvBgFocus = true;
            } else if (attr == R.styleable.PhoneCode_codeStyle) {
                //样式
                tvBgStyle = typedArray.getInteger(attr, 1000);
            } else if (attr == R.styleable.PhoneCode_normalStrokeColor) {
                //正常时边框颜色
                tvNormalColorStroke = typedArray.getColor(attr, Color.GRAY);
            } else if (attr == R.styleable.PhoneCode_normalContentColor) {
                //正常时内容背景颜色
                tvNormalColorContent = typedArray.getColor(attr, Color.TRANSPARENT);
            } else if (attr == R.styleable.PhoneCode_focusStrokeColor) {
                //选中时边框颜色
                tvFocusColorStroke = typedArray.getColor(attr, Color.BLUE);
            } else if (attr == R.styleable.PhoneCode_focusContentColor) {
                //选中时内容颜色
                tvFocusColorContent = typedArray.getColor(attr, Color.TRANSPARENT);
            } else if (attr == R.styleable.PhoneCode_isBold) {
                //是否加粗
                isBold = typedArray.getBoolean(attr, true);
            } else if (attr == R.styleable.PhoneCode_strokeSize) {
                //边框的宽度
                tvStrokeSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_isNumber) {
                //是否输入类型为数字
                isNumber = typedArray.getBoolean(attr, true);
            } else if (attr == R.styleable.PhoneCode_isShowPwd) {
                //是否隐藏输入为密码符号
                isShowPwd = typedArray.getBoolean(attr, false);
            }
        }
        //用完回收
        typedArray.recycle();
        init();
    }

    private void init() {
        removeAllViews();
        initTvBg();
        initTextView();
        initEditText();
        tvSetFocus(0);
    }

    //初始化输入框默认背景和焦点背景
    private void initTvBg() {
        //如果没有设置，那么我们设置默认的样式，如果设置了，那我们不能设置颜色值 以及 不在关心 tvBtyle
        //不是只带的样式，我们可以设置背景颜色的值
        if (!isSetTvBgNormal) {
            int mtvBgNormal;
            if (tvBgStyle == 1001)//圆圈
                mtvBgNormal = R.drawable.verify_oval_bg_normal;
            else if (tvBgStyle == 1002) //下划线
                mtvBgNormal = R.drawable.verify_line_bg_normal;
            else //方框
                mtvBgNormal = R.drawable.verify_rectangel_bg_normal;

            tvBgNormal = getResources().getDrawable(mtvBgNormal);
        }

        if (!isSetTvBgFocus) {
            int mtvBgFocus;
            if (tvBgStyle == 1001) //圆圈
                mtvBgFocus = R.drawable.verify_oval_bg_focus;
            else if (tvBgStyle == 1002)//下划线
                mtvBgFocus = R.drawable.verify_line_bg_focus;
            else//方框
                mtvBgFocus = R.drawable.verify_rectangle_bg_focus;

            tvBgFocus = getResources().getDrawable(mtvBgFocus);
        }


    }

    /**
     * 设置TextView
     */
    private void initTextView() {
        tvList.clear();
        LinearLayout linearLayout = new LinearLayout(getContext());
        addView(linearLayout);
        LayoutParams llLayoutParams = (LayoutParams) linearLayout.getLayoutParams();
        llLayoutParams.width = LayoutParams.MATCH_PARENT;
        llLayoutParams.height = LayoutParams.WRAP_CONTENT;
        //linearLayout.setLayoutParams(llLayoutParams);
        //水平排列
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        //内容居中
        linearLayout.setGravity(Gravity.CENTER);
        for (int i = 0; i < codeLength; i++) {
            TextView textView = new TextView(getContext());
            linearLayout.addView(textView);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.width = tvWidth;
            layoutParams.height = tvHeight;
            //只需将中间隔开，所以最后一个textView不需要margin
            if (i == codeLength - 1) {
                layoutParams.rightMargin = 0;
            } else {
                layoutParams.rightMargin = tvMarginRight;
            }

            textView.setLayoutParams(layoutParams);
            textView.setBackground(tvBgNormal);
            textView.setGravity(Gravity.CENTER);
            //注意单位
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTextSize);
            //是否加粗
            if (isBold)
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setTextColor(tvTextColor);
            tvList.add(textView);
        }
        setTvBg();
    }

    //如果用户没有设置自定义默认背景
    private void setTvBg() {
        //通过代码动态修改样式 - normal  未选中状态
        if (!isSetTvBgNormal) {
            Drawable draw_normal = tvBgNormal;
            if (draw_normal instanceof GradientDrawable) {
                //方框和圆圈样式
                GradientDrawable gradientDrawable = (GradientDrawable) draw_normal;
                gradientDrawable.setColor(tvNormalColorContent);
                gradientDrawable.setStroke(tvStrokeSize, tvNormalColorStroke);
                tvBgNormal = gradientDrawable;
//                textView.setBackground(gradientDrawable);
            } else if (draw_normal instanceof LayerDrawable) {
                //下划线样式
                LayerDrawable layerDrawable = (LayerDrawable) draw_normal;
                GradientDrawable drawable1 = (GradientDrawable) layerDrawable.getDrawable(0);
                //底部颜色（下划线颜色）
                drawable1.setColor(tvNormalColorStroke);
                //内容颜色
                GradientDrawable drawable2 = (GradientDrawable) layerDrawable.getDrawable(1);
                drawable2.setColor(tvNormalColorContent == Color.TRANSPARENT ? Color.WHITE : tvNormalColorContent);
                tvBgNormal = layerDrawable;
//                textView.setBackground(layerDrawable);
            }
        }

        //通过代码动态修改样式 - focus  选中状态
        if (!isSetTvBgFocus) {
            Drawable draw_focus = tvBgFocus;
            if (draw_focus instanceof GradientDrawable) {
                //方框和圆圈样式
                GradientDrawable gradientDrawable = (GradientDrawable) draw_focus;
                gradientDrawable.setColor(tvFocusColorContent);
                gradientDrawable.setStroke(tvStrokeSize, tvFocusColorStroke);
                tvBgFocus = gradientDrawable;
                //选中的不用设置  因为要通过焦点设置 如果设置会覆盖掉上面的
//                textView.setBackground(gradientDrawable);
            } else if (draw_focus instanceof LayerDrawable) {
                //下划线样式
                LayerDrawable layerDrawable = (LayerDrawable) draw_focus;
                GradientDrawable drawable1 = (GradientDrawable) layerDrawable.getDrawable(0);
                //底部颜色（下划线颜色）
                drawable1.setColor(tvFocusColorStroke);
                //内容颜色
                GradientDrawable drawable2 = (GradientDrawable) layerDrawable.getDrawable(1);
                drawable2.setColor(tvFocusColorContent == Color.TRANSPARENT ? Color.WHITE : tvFocusColorContent);
                tvBgFocus = layerDrawable;
                //选中的不用设置  因为要通过焦点设置 如果设置会覆盖掉上面的
//                textView.setBackground(layerDrawable);
            }
        }
    }

    /**
     * 输入框和父布局一样大，但字体大小0，看不见的
     */
    private void initEditText() {
        editText = new EditText(getContext());
        addView(editText);
        LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = tvHeight;
        editText.setLayoutParams(layoutParams);

        //防止横盘小键盘全屏显示
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_FULLSCREEN);
        //隐藏光标
        editText.setCursorVisible(false);
        //最大输入长度
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(codeLength)});
        //输入类型为数字
        if (isNumber)
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextSize(0);
        editText.setBackgroundResource(0);
        //屏蔽长按
        editText.setLongClickable(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !TextUtils.isEmpty(s.toString())) {
                    //有验证码的情况
                    inputData = s.toString();
                    tvSetFocus();

                    //给textView设置数据
                    for (int i = 0; i < inputData.length(); i++) {
                        //密码隐藏
                        if (isShowPwd)
                            tvList.get(i).setText("•");
                        else
                            tvList.get(i).setText(inputData.substring(i, i + 1));
                    }
                    //其他的没有就设为空
                    for (int i = inputData.length(); i < codeLength; i++) {
                        tvList.get(i).setText("");
                    }
                } else {
                    //一位验证码都没有的情况
                    tvSetFocus(0);
                    for (int i = 0; i < codeLength; i++) {
                        tvList.get(i).setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != onVCodeInputListener) {
                    if (s.length() == codeLength) {
                        onVCodeInputListener.vCodeComplete(s.toString());
                    } else {
                        onVCodeInputListener.vCodeIncomplete(s.toString());
                    }
                }
            }
        });
    }

    /**
     * 设置正常背景以及焦点背景
     */
    private void tvSetFocus() {
        //如果是最后一位验证码，焦点在最后一个，否者在下一位
        if (inputData.length() == codeLength) {
            tvSetFocus(codeLength - 1);
        } else {
            tvSetFocus(inputData.length());
        }
    }

    /**
     * 假装获取焦点
     */
    private void tvSetFocus(int index) {
        tvSetFocus(tvList.get(index));
    }

    private void tvSetFocus(TextView textView) {
        for (int i = 0; i < codeLength; i++) {
            tvList.get(i).setBackground(tvBgNormal);
            //用来刷新背景
            tvList.get(i).invalidateDrawable(tvBgNormal);
        }
        //重新获取焦点
        textView.setBackground(tvBgFocus);
        textView.invalidateDrawable(tvBgFocus);
    }

    public int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int px2dp(final float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public int sp2px(final float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int px2sp(final float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    //---------------- set begin -------------

    /**
     * 直接设置验证码
     */
    public void setText(String inputData) {
        this.inputData = inputData;
        editText.setText(inputData);
    }

    /**
     * 隐藏软键盘(有输入框)
     *
     * @param context
     * @param mEditText
     */
    public static void hideSoftKeyboard(@NonNull Context context, @NonNull EditText mEditText) {
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    @Override
    public void setOnVCodeCompleteListener(IPhoneCode.OnVCodeInputListener OnVCodeInputListener) {
        this.onVCodeInputListener = OnVCodeInputListener;
    }

    @Override
    public void setCodeLength(int size) {
        if (size < 1) return;
        codeLength = size;
        init();
    }

    @Override
    public void setBgNormal(Drawable tvBgNormal) {
        if (tvBgNormal != null) {
            this.tvBgNormal = tvBgNormal;
            isSetTvBgNormal = true;
        } else {
            isSetTvBgNormal = false;
            this.tvBgNormal = getResources().getDrawable(R.drawable.verify_rectangel_bg_normal);
        }
        tvSetFocus();
    }

    @Override
    public void setBgNormal(int tvBgNormal) {
        this.tvBgNormal = getResources().getDrawable(tvBgNormal);
        isSetTvBgNormal = true;
        tvSetFocus();
    }

    @Override
    public void setBgFocus(Drawable tvBgFocus) {
        if (tvBgFocus != null) {
            this.tvBgFocus = tvBgFocus;
            isSetTvBgFocus = true;
        } else {
            isSetTvBgFocus = false;
        }
        tvSetFocus();
    }

    @Override
    public void setBgFocus(int tvBgFocus) {
        this.tvBgFocus = getResources().getDrawable(tvBgFocus);
        isSetTvBgFocus = true;
        tvSetFocus();
    }

    @Override
    public void setCodeMargin(int tvMargin) {
        this.tvMarginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tvMargin, getResources().getDisplayMetrics());
        for (int i = 0; i < codeLength; i++) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvList.get(i).getLayoutParams();
            //只需将中间隔开，所以最后一个textView不需要margin
            if (i == codeLength - 1) {
                layoutParams.rightMargin = 0;
            } else {
                layoutParams.rightMargin = tvMarginRight;
            }
            tvList.get(i).setLayoutParams(layoutParams);
        }
    }

    @Override
    public void setTvWidth(int tvWidth) {
        this.tvWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tvWidth, getResources().getDisplayMetrics());
        for (int i = 0; i < codeLength; i++) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvList.get(i).getLayoutParams();
            layoutParams.width = this.tvWidth;
            tvList.get(i).setLayoutParams(layoutParams);
        }
    }

    @Override
    public void setTvHeight(int tvHeight) {
        this.tvHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tvHeight, getResources().getDisplayMetrics());
        for (int i = 0; i < codeLength; i++) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvList.get(i).getLayoutParams();
            layoutParams.height = this.tvHeight;
            tvList.get(i).setLayoutParams(layoutParams);
        }
    }

    @Override
    public void setCodeTextColor(int tvTextColor) {
        this.tvTextColor = tvTextColor;
        for (int i = 0; i < codeLength; i++) {
            tvList.get(i).setTextColor(this.tvTextColor);
        }
    }

    @Override
    public void setCodeTextSize(float tvTextSize) {
        this.tvTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tvTextSize, getResources().getDisplayMetrics());
        for (int i = 0; i < codeLength; i++) {
            tvList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, this.tvTextSize);
        }
    }

    @Override
    public void setCodeStyle(int tvBgStyle) {
        this.tvBgStyle = tvBgStyle;
        init();
    }

    @Override
    public void setNormalStrokeColor(int tvNormalColorStroke) {
        this.tvNormalColorStroke = tvNormalColorStroke;
        setTvBg();
        tvSetFocus();
    }

    @Override
    public void setNormalContentColor(int tvNormalColorContent) {
        this.tvNormalColorContent = tvNormalColorContent;
        setTvBg();
        tvSetFocus();
    }

    @Override
    public void setFocusStrokeColor(int tvFocusColorStroke) {
        this.tvFocusColorStroke = tvFocusColorStroke;
        setTvBg();
        tvSetFocus();
    }

    @Override
    public void setFocusContentColor(int tvFocusColorContent) {
        this.tvFocusColorContent = tvFocusColorContent;
        setTvBg();
        tvSetFocus();
    }

    @Override
    public void setBold(Boolean bold) {
        this.isBold = bold;
        for (int i = 0; i < codeLength; i++) {
            tvList.get(i).setTypeface(isBold ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }

    @Override
    public void setStrokeSize(int tvStrokeSize) {
        this.tvStrokeSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tvStrokeSize, getResources().getDisplayMetrics());
        setTvBg();
        tvSetFocus();
    }

    @Override
    public void setNumber(Boolean number) {
        this.isNumber = number;
        editText.setInputType(number ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        editText.setText("");
        inputData = "";
    }

    @Override
    public void setShowPwd(Boolean showPwd) {
        this.isShowPwd = showPwd;
        editText.setText(inputData);
    }
    //---------------- set end -------------

}