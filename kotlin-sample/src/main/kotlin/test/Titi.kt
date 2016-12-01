package test

interface Truc {
    val id : String
}

class Titi(override val id: String) : Truc
