[![](https://jitpack.io/v/XuQK/KDTabLayout.svg)](https://jitpack.io/#XuQK/KDTabLayout)

一直感觉[MagicIndicator](https://github.com/hackware1993/MagicIndicator)这个库想当牛逼，在自己的工作过程中也帮我解决了很多问题，无奈作者志向高远，已经转行不写代码了，所以我参考了一下该库，自己撸了一个TabLayout。

本TabLayout直接继承自ViewGroup，默认实现了一些简单的效果，也留出了接口供用户自定义单个Tab，Indicator，Badge等控件，可以和ViewPager2和ViewPager搭配使用。

效果图：

![](demo.gif)

# 使用方式：

## 将JitPack存储库添加到构建文件(项目根目录下build.gradle文件)

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## 添加依赖项

```gradle
// 版本号参看Release
implementation 'com.github.XuQK:KDTabLayout:versionCode'

// 项目依赖于以下库，如果没有需要在主工程中添加
implementation 'androidx.appcompat:appcompat:versionCode'
// 如果要使用ViewPager2，请添加ViewPager2库
implementation 'androidx.viewpager2:viewpager2:versionCode'
```
