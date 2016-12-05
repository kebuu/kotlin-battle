package test


interface Truc {
    val id : String
}

class Titi(override val id: String) : Truc


class Tata : Test2() {

    override fun tutu() = "tata"
}

class Tata2 {

    fun tutu() = {
        Test2().tutu()
    }
}