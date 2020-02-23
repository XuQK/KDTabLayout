package github.xuqk.kdtablayout

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.util.TypedValue


/**
 * Created By：XuQK
 * Created Date：2/15/20 9:11 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */

internal fun log(any: Any?) {
    Log.d("KDTagLayout", any.toString())
}

internal fun dpToPx(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
}

/**
 * 获取baseline离中线的距离
 */
fun Paint.FontMetrics.getBaselineToCenter(): Float {
    return (descent - ascent) / 2 - descent
}

class HsvEvaluator : TypeEvaluator<Int> {
    var startHsv = FloatArray(3)
    var endHsv = FloatArray(3)
    var outHsv = FloatArray(3)
    override fun evaluate(
        fraction: Float,
        startValue: Int,
        endValue: Int
    ): Int { // 把 ARGB 转换成 HSV
        Color.colorToHSV(startValue, startHsv)
        Color.colorToHSV(endValue, endHsv)
        // 计算当前动画完成度（fraction）所对应的颜色值
        if (endHsv[0] - startHsv[0] > 180) {
            endHsv[0] = endHsv[0] - 360
        } else if (endHsv[0] - startHsv[0] < -180) {
            endHsv[0] = endHsv[0] + 360
        }
        outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction
        if (outHsv[0] > 360) {
            outHsv[0] = outHsv[0] - 360
        } else if (outHsv[0] < 0) {
            outHsv[0] = outHsv[0] + 360
        }
        outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction
        outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction
        // 计算当前动画完成度（fraction）所对应的透明度
        val alpha = (startValue.ushr(24) +
                (endValue.ushr(24) - startValue.ushr(24)) * fraction).toInt()
        // 把 HSV 转换回 ARGB 返回
        return Color.HSVToColor(alpha, outHsv)
    }
}