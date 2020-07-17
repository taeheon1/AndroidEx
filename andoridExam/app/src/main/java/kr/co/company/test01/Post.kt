package kr.co.company.test01

import java.io.Serializable

class Post(
    val owner: String? = null,
    val contnent: String? = null,
    val image: String? = null
) : Serializable