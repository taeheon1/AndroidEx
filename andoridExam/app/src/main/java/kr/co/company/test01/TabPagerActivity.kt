package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tab_pager.*

class TabPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_pager)

        tab_layaout.addTab(tab_layaout.newTab().setText("ONE")) // 텝을 원하는 숫자 만큼 더해준다.
        tab_layaout.addTab(tab_layaout.newTab().setText("TWO"))
        tab_layaout.addTab(tab_layaout.newTab().setText("THREE"))

        val pagerAdapter = FragmentPagerAdapter(supportFragmentManager, 3)
        view_pager.adapter = pagerAdapter

        tab_layaout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
//                view_pager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {  // 해당 탭이 선택 되었을때 위치를 바꾼다.
                view_pager.setCurrentItem(tab!!.position)
            }

        })
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layaout)) //-> 페이져가 이동했을때 탭을 이동시키는 코드
    }
}

class FragmentPagerAdapter(
    fragmentManager: FragmentManager, // fragment를 사용함으로 fragmentManager를 받는다.
    val tabCount: Int
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return Fragment1()
            }
            1 -> {
                return Fragment2()
            }
            2 -> {
                return Fragment3()
            }
            else -> return Fragment1()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}
