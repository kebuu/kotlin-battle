package test.test

import test.Test2

interface Truc {
    val id : String
}

class Titi(override val id: String) : Truc


class Tata : Test2() {

    @Throws(NullPointerException::class)
    override fun tutu() = "tata"
}

class Tata2 {

    fun tutu() = {
        Test2().tutu()
    }
}