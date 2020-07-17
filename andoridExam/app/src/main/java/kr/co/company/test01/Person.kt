package kr.co.company.test01

import java.io.Serializable

class PersonFromServer
    (
    var id: Int? = null,
    var name: String? = null,
    var age: Int? =null,
    var intro: String? = null
) : Serializable // 인터페이스 상속
