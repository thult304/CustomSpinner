package vn.propzy.customspinner

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

interface SpinnerGroupAdapterInfo {
    fun getChildItemViewType(group: Int, child: Int): Int
    fun getGroupItemViewType(group: Int): Int
    fun getGroupCount(): Int
    fun getItemCountInGroup(group: Int): Int
    fun getDropDownChildItemView(group: Int, child: Int, convertView: View?, parent: ViewGroup?): View
    fun getDropDownGroupItemView(group: Int, convertView: View?, parent: ViewGroup?): View
    fun getGroupItem(group: Int): Any
    fun getChildItem(group: Int, child: Int): Any
}

abstract class SpinnerGroupAdapter : BaseAdapter(),
    SpinnerGroupAdapterInfo {

    private data class GroupPositionInfo(val group: Int, val child: Int)

    override fun getItem(position: Int): Any {
        var positionInfo = calculateGroupPosition(position)

        return if (positionInfo.child < 0) {
            getGroupItem(positionInfo.group)
        } else {
            getChildItem(positionInfo.group, positionInfo.child)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * don't implement/override this in the sub class.
     * use getChild ItemViewType or getGroupItemViewType
     */
    override fun getItemViewType(position: Int): Int {
        var positionInfo = calculateGroupPosition(position)

        return if (positionInfo.child < 0) {
            getGroupItemViewType(positionInfo.group)
        } else {
            getChildItemViewType(positionInfo.group, positionInfo.child)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var positionInfo = calculateGroupPosition(position)

        return if (positionInfo.child < 0) {
            getDropDownGroupItemView(positionInfo.group, convertView, parent)
        } else {
            getDropDownChildItemView(positionInfo.group, positionInfo.child, convertView, parent)
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var positionInfo = calculateGroupPosition(position)

        return if (positionInfo.child < 0) {
            getDropDownGroupItemView(positionInfo.group, convertView, parent)
        } else {
            getDropDownChildItemView(positionInfo.group, positionInfo.child, convertView, parent)
        }
    }

    private fun calculateGroupPosition(position: Int): GroupPositionInfo {
        val groupCount = getGroupCount()

        var count = 0
        for (group in 0 until groupCount) {

            val childTotal = getItemCountInGroup(group)

            count += 1 + childTotal

            if (count >= (position + 1)) {
                val extra = count - position

                val child = childTotal - extra

                return GroupPositionInfo(group, child)
            }
        }

        return GroupPositionInfo(0, -1)
    }

    /**
     * don't implement/override this in the sub-class
     * use getGroupCount or getItemCountInGroup
     */
    override fun getCount(): Int {
        return calculateItemCount()
    }

    private fun calculateItemCount(): Int {

        val groupCount = getGroupCount()

        var totalItem = 0

        for (group in 0 until groupCount) {

            totalItem += 1 + getItemCountInGroup(group)
        }

        return totalItem
    }
}