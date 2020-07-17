package kr.co.company.test01

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class Fragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("life_cycle", "F onCreateView")

        //프라그먼트가 인터페이스를 처음으로 그릴 때 호출된다.
        // inflater - > 뷰를 그려주는 역학
        // container -> 부모 뷰
        return inflater.inflate(R.layout.fragment_three, container, false)
    }
}