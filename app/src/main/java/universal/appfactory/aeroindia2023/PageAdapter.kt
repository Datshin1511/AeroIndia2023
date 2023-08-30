@file:Suppress("DEPRECATION")

package universal.appfactory.aeroindia2023

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fm: FragmentManager, pageCount: Int): FragmentPagerAdapter(fm) {
    val pages: Int = pageCount
    override fun getCount(): Int{
        return pages
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> GridLayoutFragment1()
            1 -> GridLayoutFragment2()
            3 -> GridLayoutFragment3()
            else -> GridLayoutFragment1()
        }
    }

}