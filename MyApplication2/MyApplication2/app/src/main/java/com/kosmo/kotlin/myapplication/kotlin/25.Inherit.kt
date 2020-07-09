package com.kosmo.kotlin.myapplication.kotlin


// 두번 이상이 넘어가면 리펙토링 해라
//                      -> 정리정돈

// 25. 상속
// 부모로부터 설명서를 물려받는다!

fun main(array: Array<String>){
    val superCar : SuperCar100 = SuperCar100()
    superCar.drive()
    superCar.stop()

    val bus100 = Bus100()
    bus100.drive()

}

//class선언에서 default는 private
//fun 도 default는 private
//  부모 : Car100
//
open class Car100(){

    open fun drive(){
        println("달린다")
    }

    fun stop(){

    }
}

class SuperCar100():Car100() {
    override fun drive() {
//        println("빨리달린다")
        super.drive()
    }
}

class Bus100() :Car100() {

}