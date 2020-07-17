package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_realm.*

class RealmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realm)

        Realm.init(this@RealmActivity)
        val config: RealmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded() // 데이터베이스의 틀에 변경이 생기면 데이터를 싹 지운다. ( 컬럼 등 )
            .build()
        Realm.setDefaultConfiguration(config)
        val realm = Realm.getDefaultInstance()

        button_save.setOnClickListener {
            realm.executeTransaction {
                with(it.createObject(School::class.java)){
                    this.name = "어떤 대학교"
                    this.location = "서울"
                }
            }
        }
        button_load.setOnClickListener {
            realm.executeTransaction{
                var data = it.where(School::class.java).findFirst() // 테이블에 가서 첫번째 레코드 가져오기
                Log.d("dataa", "data: " + data)
            }
        }
        button_delete.setOnClickListener {
            realm.executeTransaction{
                it.where(School::class.java).findAll().deleteAllFromRealm() // 전체 데이터 삭제
//                it.where(School::class.java).findFirst().deleteFromRealm() // 첫번째 레코드 데이터 삭제
            }
        }
    }
}
