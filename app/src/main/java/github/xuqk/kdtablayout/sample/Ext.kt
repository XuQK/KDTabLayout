package github.xuqk.kdtablayout.sample

import android.content.Context
import android.util.TypedValue

/**
 * Created By：XuQK
 * Created Date：2/23/20 4:11 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */

fun dpToPx(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
}

val ZH = arrayOf(
    "纸杯蛋糕",
    "甜甜圈",
    "闪电泡芙",
    "优格冰淇淋",
    "姜饼",
    "蜂巢",
    "冰淇淋三明治",
    "雷根糖",
    "奇巧巧克力",
    "棒棒糖",
    "棉花糖",
    "牛轧糖",
    "奥利奥",
    "派")

val EN = arrayOf(
    "Cupcake",
    "Donut",
    "Eclair",
    "Froyo",
    "Gingerbread",
    "Honeycomb",
    "Ice Cream Sandwich",
    "Jelly Bean",
    "KitKat",
    "Lollipop",
    "Marshmallow",
    "Nougat",
    "Oreo",
    "Pie")