package com.kosmo.kotlin.myapplication.kotlin

//18.Class

// OOP -> Object Oriented Programing (객체 지향 프로그래밍)

// 객체란 뭘까?
// -이름이 있는 모든 것

// 절차지향 프로그래밍
// - 코드를 위에서 부터 아래로 실행을 하면 문제가 해결된다!

// 객체지향 프로그래밍
// - 객체를 만들어서, 객체에게 일을 시켜서 문제를 해결한다
// - 선수, 심판, 경기장, 관중 -> 축구 게임

// 객체를 만드는 방법
// - 설명서가 있어야 한다.


fun main(array: Array<String>) {

    // 클래스(설명서)를 통해서 실체를 만드는 방법
    // ->인스턴스화 -> 인스턴스(객체)
    Car("v8 engine", "big")
    val number: Int = 10
    val bigCar = Car("v8 engine", "big")

    // 우리가 만든 클래스는 자료형이 된다.
    val bigCar1: Car = Car("v8 engine", "big")
    val superCar: SuperCar = SuperCar("good engine", "big", "white")

    // 인스턴스가 가지고있는 기능을 사용하는 방법
    val runableCar: RunableCar = RunableCar("simple engine", "body")
    // RunableCar.ride() -> 불가능
    runableCar.ride()
    runableCar.drive()
    runableCar.navi("부산")


    // 인스턴스의 멤버 변수에 접근하는 방법
    val runableCar2: Runable2 = Runable2("nice engine", "long body")
    println(runableCar2.body)
    println(runableCar2.engine)

    println()
    val testClass = TestClass()
    testClass.test(2)
    testClass.test(3,4)
}

// 클래스(설명서) 만드는 방법(1)
class Car(var engine: String, var body: String) {

}

// 클래스(설명서) 만드는 방법(2)
class SuperCar {
    var engine: String
    var body: String
    var door: String

    constructor(engine: String, body: String, door: String) {

        this.engine = engine
        this.body = body
        this.door = door

    }
}

// 클래스(설명서) 만드는 방법(3) -> 1번 방법의 확장
class Car1 constructor(engine: String, body: String) {
    var door: String = ""

    // 생성자
    constructor(engine: String, body: String, door: String) : this(engine, body) {
        this.door = door
    }

}

// 클래스(설명서) 만드는 방법(4) -> 2번 방법의 확장
class Car2 {
    var engine: String = ""
    var body: String = ""
    var door: String = ""

    constructor(engine: String, body: String) {
        this.engine = engine
        this.body = body
    }

    constructor(engine: String, body: String, door: String) {
        this.door = door
        this.engine = engine
        this.body = body
    }

}

class RunableCar constructor(engine: String, body: String) {

    fun ride() {
        println("탑승 하였습니다")
    }

    fun drive() {
        println("달립니다 !")
    }

    fun navi(destination: String) {
        println("$destination 으로 목적지가 설정되었습니다.")
    }

}

class Runable2 {
    var engine: String
    var body: String

    constructor(engine: String, body: String) {
        this.engine = engine
        this.body = body
    }

    // 객체 만들때 가장 먼저 호출되는 부분
    init {
        // 초기 셋팅을 할때 유용
        println("RunableCar2가 만들어 졌습니다.")
    }

    fun ride() {
        println("탑승 하였습니다")
    }

    fun drive() {
        println("달립니다 !")
    }

    fun navi(destination: String) {
        println("$destination 으로 목적지가 설정되었습니다.")
    }
}

//오버로딩
// -> 이름이 같지만 받는 파라미터가 다른 함수
class TestClass() {

    fun test(a: Int) {
        println("Up")
    }

    fun test(a: Int, b: Int) {
        println("down")
    }

}
