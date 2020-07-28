package kr.co.company.test01

import java.io.Serializable

class User(
    var username : String ? = null,
    var token : String? = null
) : Serializable