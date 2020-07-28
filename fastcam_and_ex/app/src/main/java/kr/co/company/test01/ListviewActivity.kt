package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_listview.*

class ListviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)

        val carList = ArrayList<CarForList>()
        for (i in 0 until 50) {
            carList.add(CarForList("" + i + " 번째 자동차", "" + i + "순위 엔진"))
        }
        val adapter = ListViewAdapter(carList, LayoutInflater.from(this@ListviewActivity))
        listView.adapter = adapter
        listView.setOnItemClickListener{ parent, view, position, id ->
            var carName = (adapter.getItem(position) as CarForList).name
            var carEngine = (adapter.getItem(position) as CarForList).engine

            Toast.makeText(this@ListviewActivity, carName + ""+ carEngine, Toast.LENGTH_SHORT).show()
        }
    }
}

class ListViewAdapter( val carForList: ArrayList<CarForList>,
                       val layoutInflater: LayoutInflater
) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view : View
            val holder : ViewHolder

            if (convertView == null) {
                Log.d("converter", "1")
                view = layoutInflater.inflate(R.layout.item_view,null)
                holder = ViewHolder()

                holder.carName = view.findViewById(R.id.car_name)
                holder.carEngine = view.findViewById(R.id.car_engine)

                view.tag = holder
            }else {
                //findviewById 가 없어서 더 가볍게 돌아간다.
                Log.d("converter", "2")
                holder = convertView.tag as ViewHolder
                view = convertView
            }
            holder.carName?.setText(carForList.get(position).name)
            holder.carEngine?.setText(carForList.get(position).engine)

            return view
        }

        override fun getItem(position: Int): Any {
            //그리고자 하는 아이템 리스트의 하나(포지션에 해당하는)
            return carForList.get(position)
        }

        override fun getItemId(position: Int): Long {
            // 해당 포지션에 위치해 있는 아이템뷰의 아이디 설정
            return position.toLong()
        }

        override fun getCount(): Int {
            //그리고자 하는 아이템 리스트의 전체 갯수
            return carForList.size
        }
    }

// 리스트 뷰를 그릴때 생성을 해줘야한다 안드로이드에서 권장하는 방법이다.
class ViewHolder {
    var carName: TextView?=null
    var carEngine: TextView?=null
}
