package com.kosmo.kotlin.myapplication.kotlin


// 인터페이스도 구현이 있는 함수를 만들 수 있다
// 인터페이스에 구현이 있는 함수는 그 인터페이스를 구현 하는 클래스에서 그 함수를 구현할 필요가 없다
// 인터페이스에 구현이 없는 함수는 그 인터페이스를 구현하는 클래스에서 그 함수를 반드시 구현해야 한다.

fun main(array: Array<String>) {
    val student_ = Student__()

    student_.sleep()

}

interface Person__ {
    fun eat() {
        println("먹는다")
    }

    fun sleep() {
    }

    abstract fun study()
}

class Student__ : Person__ {
    override fun sleep() {
        TODO("Not yet implemented")
    }


    override fun study() {
        TODO("Not yet implemented")
    }


}

class SoccerPlay__ : Person__ {

    override fun study() {
        TODO("Not yet implemented")
    }
}



