package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.green
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.item_view.view.*

class RecyclerViewActivity : AppCompatActivity() { // 리사이클러뷰 라이브러리 gradle 추가 시켜줘야 한다. 어댑터를 사용하므로 어댑터를 만들어주어야 한다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val carList = ArrayList<CarForList>()
        for (i in 0 until 50) {
            carList.add(CarForList("" + i + " 번째 자동차", "" + i + "순위 엔진"))
        }
        val adapter = RecyclerViewAdapter(carList, LayoutInflater.from(this@RecyclerViewActivity))
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this@RecyclerViewActivity)   //수직으로 만들꺼기 때문에 리니어레이아웃을 만든다
//        recycler_view.layoutManager = GridLayoutManager(this@RecyclerViewActivity,2) //2 -> 몇줄로 나올껀지 컬럼수
    }
}

class RecyclerViewAdapter(
    val itemList: ArrayList<CarForList>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    // inner 를 붙혀주면 바깥에 있는 클래스의 멤버변수에도 접근이 가능하다.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // ViewHolder를 무조건 만들어 주어한다.
        val carName: TextView
        val carEngine: TextView

        init {
            carName = itemView.findViewById(R.id.car_name)
            carEngine = itemView.findViewById(R.id.car_engine)
            itemView.setOnClickListener{
               val position: Int = adapterPosition
                val engine = itemList.get(position).engine
                Log.d("engine", engine)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // 태그를 달아서 재활용을 한다.
        holder.carName.setText(itemList.get(position).name)
        holder.carEngine.setText(itemList.get(position).engine)
    }
}