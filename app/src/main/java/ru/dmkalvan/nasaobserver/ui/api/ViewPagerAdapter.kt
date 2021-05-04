package ru.dmkalvan.nasaobserver.ui.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

private const val YESTERDAY_FRAGMENT = 0
private const val TODAY_FRAGMENT = 1
private const val RANDOM_FRAGMENT = 2

class ViewPagerAdapter(
    fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = arrayOf(YesterdayFragment(), TodayFragment(), RandomFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> fragments[YESTERDAY_FRAGMENT]
            1 -> fragments[TODAY_FRAGMENT]
            2 -> fragments[RANDOM_FRAGMENT]
            else -> fragments[TODAY_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }
}