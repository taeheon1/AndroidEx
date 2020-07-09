package com.kosmo.kotlin.myapplication.kotlin

//1) 사칙 연산을 수행할 수 있는 클래스

// 2) 은행 계좌만들기
// - 계좌 생성 기능 (이름, 생년 월일)
// - 잔고를 확인하는 기능
// - 출금 기능
// - 예금 기능

// 3) TV 클래스
//   - on/off 기능
//   - c채널을 돌리는 기능
//   - 초기 채널은 (S사 M사 K사 3개)
fun main(array: Array<String>) {
    val cal: Calculate = Calculate()
    cal.plus(2, 3)
    cal.minus(5, 2)
    cal.multi(3, 6)
    cal.divi(5, 2)

    val account: BankAccount = BankAccount("임대근", "930811")
    account.check()
    account.push(50000)
    account.out(60000)
    account.out(30000)

    val tv: TV = TV()

    tv.change(2)
    tv.start()
    tv.change(3)
    tv.change(5)
    tv.off()
    tv.change(1)


}

class Calculate {

    fun plus(a: Int, b: Int) {
        println("$a + $b = ${a + b}")
    }

    fun minus(a: Int, b: Int) {
        println("$a - $b = ${a - b}")
    }

    fun multi(a: Int, b: Int) {
        println("$a * $b = ${a * b}")
    }

    fun divi(a: Int, b: Int) {
        println("$a / $b = ${a / b}")
    }
}

class BankAccount(var name: String, var birth: String) {

    var change: Int = 0

    fun check() {
        println("통장 잔고 : ${change}원")
        println()
    }

    fun push(money: Int) {
        change = change + money
        println("${money}원이 입금되었습니다.")
        check()
    }

    fun out(money: Int) {
        if (money > change) {
            println("잔액이 부족합니다.")
            println()
        } else {
            println("${money}원이 출금되었습니다.")
            change = change - money
            check()
        }
    }

}

class TV {
    var state: Boolean
    val broad = arrayListOf("SBS", "MBC", "KBS")
    var channel: String

    init {
        state = false
        channel = broad[0]
        println("Tv off")
        println()
    }

    fun start() {
        state = true
        println("Tv on")
        println("현재 채널 : $channel")
        println()
    }

    fun off() {
        state = false
        println("Tv off")
        println()
    }

    fun change(num: Int) {
        if (state) {

            if (num > broad.size) {
                channel = broad[0]

            } else {
                channel = broad[num - 1]

            }
            println("현재 채널 : $channel")

        } else {
            println("tv 켜주세요")
        }
        println()
    }

}
