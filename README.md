# TranslateDrawable
参考RotateDrawable和ScaleDrawable以及AnimationDrawable和LayerDrawable实现的一个Drawable，从名字不难知道这个一个可平移的drawable。

TranslateDrawable与AnimationDrawable效果类似。但AnimationDrawable由图片序列构成，而TranslateDrawable对单张图片进行平移实现动画效果，对比效果如下：

![translate-drawable](screenshot/translate-drawable.gif)

TranslateDrawable仅使用一张图片

![single-image](https://github.com/410063005/TranslateDrawable/blob/master/app/src/main/res/drawable-xxhdpi/ic_demo.png)

类似上面的动画效果，TranslateDrawable对比AnimationDrawable有以下优势：

+ 更少的图片资源，TranslateDrawable只需要一张图片，AnimationDrawable需要一张以上
+ 更少的内存消耗
