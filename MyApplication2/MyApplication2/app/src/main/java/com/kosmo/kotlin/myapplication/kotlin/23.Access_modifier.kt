package com.kosmo.kotlin.myapplication.kotlin

fun main(array: Array<String>){
    val testAccess=TestAccess("아무개")
//    private 키워드 때문에 외부에서 사용 불가
//    testAccess.test()
//    println(testAccess.name)
//    testAccess.name = "아무개 투"
//    println(testAccess.name)

    val reward : Reward = Reward()
    reward.rewardAmount = 2000

    val runningCar = Running()
    runningCar.runFast()
//    runningCar.run()

}

class Reward() {
    var rewardAmount : Int = 1000

}

class TestAccess{

    private var name : String = "홍길동"

    constructor( name: String){
        this.name = name
    }



    private fun test(){
        println("테스트")
    }

    fun changeName(newName:String){
        this.name = newName


    }
}

class Running(){

    fun runFast() {
        run()
    }

    private fun run() {

    }
}