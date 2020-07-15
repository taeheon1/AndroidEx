package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tab_pager.*

class TabPager2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_pager)

        tab_layaout.addTab(tab_layaout.newTab().setText("ONE")) // 텝을 원하는 숫자 만큼 더해준다.
        tab_layaout.addTab(tab_layaout.newTab().setText("TWO"))
        tab_layaout.addTab(tab_layaout.newTab().setText("THREE"))

        val adapter = ThreePageAdapter(LayoutInflater.from(this@TabPager2Activity))
        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layaout)) //view_pager 와 Tab 을 연결해줌.

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
    }
}

class ThreePageAdapter(
    val layoutInflater: LayoutInflater
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        when(position) {
            0-> {
                val view = layoutInflater.inflate(R.layout.fragment_one, container, false)
                container.addView(view)
                return view
            }
            1->{
                val view = layoutInflater.inflate(R.layout.fragment_two, container, false)
                container.addView(view)
                return view
            }
            2->{
                val view = layoutInflater.inflate(R.layout.fragment_three, container, false)
                container.addView(view)
                return view
            }
            else->{
                val view = layoutInflater.inflate(R.layout.fragment_one, container, false)
                container.addView(view)
                return view
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) { // 코틀린은 object 를 변수명을 사용할 수 없다. view 이다/
        container.removeView("object" as View) // Any 를 View 로 캐스팅 해주어야한다. 그래야 container 에 들어간다.
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int {
        return 3
    }
}

