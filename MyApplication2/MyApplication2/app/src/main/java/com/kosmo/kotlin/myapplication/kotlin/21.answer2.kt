package com.kosmo.kotlin.myapplication.kotlin

import java.time.temporal.TemporalAmount

fun main(array: Array<String>) {
    val account = Account("이태헌", "1548/12/04", balance = 1000)
    println(account.checkBalance())
    println(account.save(1000))
    println(account.withdraw(2000))
    println(account.checkBalance())

    println()
    val account2 = Account("이태헌", "1548/12/04", balance = -1000)
    println(account2.checkBalance())

    val account3 = Account2("홍길동","1987/1/1")
    println(account3.checkBalance())

}

class Account {
    val name: String
    val birth: String
    var balance: Int

    constructor(name: String, birth: String, balance: Int) {
        this.name = name
        this.birth = birth
        if (balance >= 0) {
            this.balance = balance
        } else {
            this.balance = 0
        }
    }

    fun checkBalance(): Int {
        return balance
    }

    fun withdraw(amount: Int): Boolean {
        if (balance >= amount) {
            balance -= amount
            return true
        } else {
            return false
        }
    }

    fun save(amount: Int) {
        balance += amount
    }

}

class Account2(val name: String, val birth: String, var balance: Int = 1000) {

    fun checkBalance(): Int {
        return balance
    }

    fun withdraw(amount: Int): Boolean {
        if (balance >= amount) {
            balance -= amount
            return true
        } else {
            return false
        }
    }

    fun save(amount: Int) {
        balance += amount
    }

}

//var 이나 val이 없으면 fun에서 사용 불가능. 멤버변수에는 사용 가능
class Account3(initialBalance : Int) {

    val balance: Int = if(initialBalance > 0) initialBalance else 0

//    fun checkBalance():Int{
//        return initialBalance
//    }
}

