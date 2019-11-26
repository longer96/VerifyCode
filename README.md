一个自定义的安卓验证码输入框控件、银行卡归属类型查询。
==========

Dependency
----
- #### Gradle

```groovy
dependencies {
    implementation 'com.longer:verifycode:1.0.3'
}
```

Introduction
----
![image](https://img.shields.io/badge/api-%2B14-blue.svg)
![image](https://img.shields.io/badge/license-Apache2.0-blue.svg)
[![image](https://img.shields.io/badge/author-longer-orange.svg)](https://github.com/longer)
- [x] 自带了方框、下划线、圆圈输入框的样式
- [x] 支持自定义验证框长度、字体大小、颜色、验证框宽高度、间距
- [x] 支持选中与非选中边框颜色、内容背景颜色
- [x] 支持自定义选中或非选中背景
- [x] 支持输入类型的设置（是否为数字）
- [x] 支持是否将验证码已密码的方式显示
- [x] 支持银行卡\信用卡 卡号的验证以及卡种，归属银行的查询
- [x] 输入键盘的隐藏


ScreenCapture
----

<br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/VerifyCode/blob/master/images/p1.png"/>

<br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/VerifyCode/blob/master/images/p2.png"/>

* 密码输入 app:isShowPwd <br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/VerifyCode/blob/master/images/p4.png"/>

* 可以通过自定义背景，实现很多样式
  * app:bgFocus="@drawable/bg_line_center_normal" 中划线  [bg_line_center_normal.xml](/app/src/main/res/drawable/bg_line_center_normal.xml)
  * app:bgFocus="@drawable/bg_blue_radiu5_solid"  圆角矩形  [bg_blue_radiu5_solid.xml](/app/src/main/res/drawable/bg_blue_radiu5_solid.xml)
<br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/VerifyCode/blob/master/images/p3.png"/>

* 文字输入，字体颜色修改
<br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/VerifyCode/blob/master/images/p5.png"/>


Function instruction
----
attribute|function name|instruction
---|---|---
codeLength|setCodeLength(5)|验证码长度 默认5
codeTextSize|setCodeTextSize(29)|验证码字体大小 8
codeTextColor|setCodeTextColor(Color.RED)|验证码字体颜色
tvWidth|setTvWidth(40)|验证码框的宽度
tvHeight|setTvHeight(40)|验证码框的高度
codeMargin|setCodeMargin(10)|验证码框间距
bgNormal|setBgNormal(R.drawable.bg_line_normal)|验证码默认背景（使用用户自定义drawable，将无法使用只带方法修改边框颜色）
bgFocus|setBgFocus(R.drawable.bg_line_focus)|验证码焦点背景（同上）
codeStyle|setCodeStyle(1001)|输入框样式  1000：方框（默认）  1001：圆圈  1002：下划线
normalStrokeColor|setNormalStrokeColor(Color.RED)|正常时边框颜色
normalContentColor|setNormalContentColor(Color.RED)|正常时内容背景颜色
focusStrokeColor|setFocusStrokeColor(Color.BLUE)|焦点时边框颜色
focusContentColor|setFocusContentColor(Color.BLUE)|焦点时内容背景颜色
isBold|setBold(true)|是否加粗 true false
strokeSize|setStrokeSize(2)|边框的宽度
isNumber|setNumber(false)|是否输入类型为数字  true(默认) false
isShowPwd|setShowPwd(true)|是否隐藏输入为密码符号  true  false(默认)


Example
=======

- #### xml 简单使用
```xml
<com.longer.verifyedittext.PhoneCode
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

- #### xml 详细
```xml
<com.longer.verifyedittext.PhoneCode
    android:id="@+id/phonecode2"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="20dp"
    android:paddingTop="20dp"
    android:paddingBottom="20dp"
    app:codeLength="5"
    app:codeMargin="10dp"
    app:codeStyle="1002"
    app:codeTextColor="@color/colorPrimary"
    app:codeTextSize="24sp"
    app:focusContentColor="#fff"
    app:focusStrokeColor="#eb6951"
    app:isNumber="false"
    app:isShowPwd="false"
    app:normalContentColor="#fff"
    app:normalStrokeColor="#eee"
    app:strokeSize="5dp"
    app:tvHeight="45dp"
    app:tvWidth="45dp" />
```

- #### 验证码java
```java
PhoneCode phonecode = findViewById(R.id.phonecode);
//隐藏键盘
phonecode.hideKeyboard();
//设置内容
phonecode.setText("123");
//清除背景样式
phonecode.setBgNormal(null);
phonecode.setBgFocus(null);

//设置监听
phonecode.setOnVCodeCompleteListener(new PhoneCode.OnVCodeInputListener() {
        @Override
        public void vCodeComplete(String verificationCode) {
            vcode = verificationCode;
            Toast.makeText(MainActivity.this, "验证码: " + verificationCode, Toast.LENGTH_SHORT).show();
        }
    
        @Override
        public void vCodeIncomplete(String verificationCode) {
        }
});
```

- #### 银行卡号验证java
```java
BankInfoBean bankinfobean = new BankInfoBean(cardnum);
//或者
BankInfoBean bankinfobean = new BankInfoBean();
bankinfobean.setTotalBankcode(cardnum);

//验证银行卡是否有效
bankinfobean.checkBankCard(cardnum);

if (checkBankCard(cardnum)) {
    String name = bankinfobean.getBankName();
    String name = bankinfobean.getCardType();
} else {
    Toast.makeText(MainActivity.this, "卡号 " + cardnum + " 不合法,请重新输入", Toast.LENGTH_LONG).show();
}
```

Tip
----
- 当输入完成后，将自动隐藏软键盘。