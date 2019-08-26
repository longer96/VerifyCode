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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhoneCode extends RelativeLayout {

//TODO   验证码输入完成后失去焦点
//TODO : 键盘的弹出隐藏

    private OnVerificationCodeCompleteListener onVerificationCodeCompleteListener;

    //输入的长度
    private int vCodeLength = 5;
    //输入的内容
    private String inputData;
    private EditText editText;

    //TextView的list
    private List<TextView> tvList = new ArrayList<>();
    //输入框默认背景
//    private int tvBgNormal = R.drawable.verify_rectangel_bg_normal;
    private Drawable tvBgNormal = getResources().getDrawable(R.drawable.verify_rectangel_bg_normal);
    //输入框焦点背景
//    private int tvBgFocus = R.drawable.verify_rectangle_bg_focus;
    private Drawable tvBgFocus = getResources().getDrawable(R.drawable.verify_rectangle_bg_focus);
    //输入框的间距
    private int tvMarginRight = 10;
    //TextView宽
    private int tvWidth = 45;
    //TextView高
    private int tvHeight = 45;
    //TextView字体颜色
    private int tvTextColor;
    //TextView字体大小
    private float tvTextSize = 8;

    //TODO 输入框样式  0：圆圈（默认）  1：方框  2：下划线
    private int tvBgStyle = 0;
    //TODO 正常时边框颜色
    private int tvNormalColorStroke = Color.GRAY;
    //TODO 正常时内容颜色
    private int tvNormalColorContent = Color.TRANSPARENT;
    //TODO 选中时边框颜色
    private int tvFocusColorStroke = Color.BLUE;
    //TODO 选中时内容颜色
    private int tvFocusColorContent = Color.TRANSPARENT;
    //TODO TextView是否加粗
    private Boolean isBold = false;
    //TODO 边框的宽度
    private int tvStrokeSize = 2;

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
                vCodeLength = typedArray.getInteger(attr, 6);
            } else if (attr == R.styleable.PhoneCode_codeTextColor) {
                //验证码字体颜色
                tvTextColor = typedArray.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.PhoneCode_codeTextSize) {
                //验证码字体大小
                tvTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_codeWidth) {
                //方框宽度
                tvWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_codeHeight) {
                //方框宽度
                tvHeight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_codeMargin) {
                //方框间隔
                tvMarginRight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.PhoneCode_codeBgNormal) {
                //默认背景
                int mtvBgNormal = typedArray.getResourceId(attr, R.drawable.verify_rectangel_bg_normal);
                tvBgNormal = getResources().getDrawable(mtvBgNormal);
            } else if (attr == R.styleable.PhoneCode_codeBgFocus) {
                //焦点背景
                int mtvBgFocus = typedArray.getResourceId(attr, R.drawable.verify_rectangle_bg_focus);
                tvBgFocus = getResources().getDrawable(mtvBgFocus);
            } else if (attr == R.styleable.PhoneCode_codeStyle) {
                //样式
                tvBgStyle = typedArray.getInteger(attr, 0);
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
                isBold = typedArray.getBoolean(attr, false);
            } else if (attr == R.styleable.PhoneCode_strokeSize) {
                //边框的宽度
                tvStrokeSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
            }
        }
        //用完回收
        typedArray.recycle();
        init();
    }


    private void init() {
        initTextView();
        initEditText();
        tvSetFocus(0);
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
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(vCodeLength)});
        //输入类型为数字
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextSize(0);
        editText.setBackgroundResource(0);
        //屏蔽长按
        editText.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !TextUtils.isEmpty(s.toString())) {
                    //有验证码的情况
                    inputData = s.toString();

                    //如果是最后一位验证码，焦点在最后一个，否者在下一位
                    if (inputData.length() == vCodeLength) {
                        tvSetFocus(vCodeLength - 1);
                    } else {
                        tvSetFocus(inputData.length());
                    }

                    //给textView设置数据
                    for (int i = 0; i < inputData.length(); i++) {
                        tvList.get(i).setText(inputData.substring(i, i + 1));
                    }
                    for (int i = inputData.length(); i < vCodeLength; i++) {
                        tvList.get(i).setText("");
                    }
                } else {
                    //一位验证码都没有的情况
                    tvSetFocus(0);
                    for (int i = 0; i < vCodeLength; i++) {
                        tvList.get(i).setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != onVerificationCodeCompleteListener) {
                    if (s.length() == vCodeLength) {
                        onVerificationCodeCompleteListener.verificationCodeComplete(s.toString());
                    } else {
                        onVerificationCodeCompleteListener.verificationCodeIncomplete(s.toString());
                    }
                }
            }
        });
    }

    /**
     * 设置TextView
     */
    private void initTextView() {
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
        for (int i = 0; i < vCodeLength; i++) {
            TextView textView = new TextView(getContext());
            linearLayout.addView(textView);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.width = tvWidth;
            layoutParams.height = tvHeight;
            //只需将中间隔开，所以最后一个textView不需要margin
            if (i == vCodeLength - 1) {
                layoutParams.rightMargin = 0;
            } else {
                layoutParams.rightMargin = tvMarginRight;
            }

            textView.setLayoutParams(layoutParams);
            textView.setBackground(tvBgNormal);
            textView.setGravity(Gravity.CENTER);
            //注意单位
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTextSize);
            //TODO 是否加粗
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setTextColor(tvTextColor);

            //TODO 如果用户是自己上传的点击样式图  ， 那么我们不同执行
            //通过代码动态修改样式 - normal
            Drawable draw_normal = tvBgNormal;
            if (draw_normal instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) draw_normal;
                gradientDrawable.setColor(Color.TRANSPARENT);
                gradientDrawable.setStroke(dp2px(1), Color.GRAY);
                textView.setBackground(gradientDrawable);

            } else if (draw_normal instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) draw_normal;
                GradientDrawable drawable1 = (GradientDrawable) layerDrawable.getDrawable(0);
                //底部颜色
                drawable1.setColor(Color.RED);
                //内容颜色
                GradientDrawable drawable2 = (GradientDrawable) layerDrawable.getDrawable(1);
                drawable2.setColor(Color.BLACK);
                textView.setBackground(drawable1);
            }

            //通过代码动态修改样式 - focus
            Drawable draw_focus = tvBgFocus;
            if (draw_focus instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) draw_focus;
                //内容颜色
                gradientDrawable.setColor(Color.TRANSPARENT);
                //边框颜色
                gradientDrawable.setStroke(dp2px(1), Color.BLUE);
                textView.setBackground(gradientDrawable);

            } else if (draw_focus instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) draw_focus;
                GradientDrawable drawable1 = (GradientDrawable) layerDrawable.getDrawable(0);
                //底部颜色
                drawable1.setColor(Color.BLUE);
                //内容颜色
                GradientDrawable drawable2 = (GradientDrawable) layerDrawable.getDrawable(1);
                drawable2.setColor(Color.YELLOW);
                textView.setBackground(drawable1);
            }

            tvList.add(textView);
        }
    }

    /**
     * 假装获取焦点
     */
    private void tvSetFocus(int index) {
        tvSetFocus(tvList.get(index));
    }

    private void tvSetFocus(TextView textView) {
        for (int i = 0; i < vCodeLength; i++) {
            tvList.get(i).setBackground(tvBgNormal);
        }
        //重新获取焦点
        textView.setBackground(tvBgFocus);
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

    /**
     * 输入完成回调接口
     */
    public interface OnVerificationCodeCompleteListener {
        //完成输入
        void verificationCodeComplete(String verificationCode);

        //未完成输入
        void verificationCodeIncomplete(String verificationCode);
    }

    public void setOnVerificationCodeCompleteListener(OnVerificationCodeCompleteListener onVerificationCodeCompleteListener) {
        this.onVerificationCodeCompleteListener = onVerificationCodeCompleteListener;
    }


    /**
     * 直接设置验证码
     */
    public void setInputData(String inputData) {
        this.inputData = inputData;
        editText.setText(inputData);
    }
}