package github.xuqk.kdtablayout

import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.KDBaseTab

/**
 * Created By：XuQK
 * Created Date：2/16/20 2:18 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
abstract class KDTabAdapter {

    /**
     * 创建对应位置的Tab，如果不需要Tab展示，返回null即可
     */
    open fun createTab(position: Int): KDBaseTab? {
        return null
    }

    /**
     * 创建Indicator，如果不需要Indicator展示，返回null即可
     */
    open fun createIndicator(): KDTabIndicator? {
        return null
    }

    /**
     * Tab数量
     */
    open fun getTabCount(): Int {
        return 0
    }
}