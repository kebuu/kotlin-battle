package com.kebuu.dto

data class ClientInfo(val host:String, val port: Int, val status: ClientStatus) {

}

enum class ClientStatus {
    OK, KO
}
