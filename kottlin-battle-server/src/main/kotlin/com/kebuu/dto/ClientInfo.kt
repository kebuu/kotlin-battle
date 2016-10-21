package com.kebuu.dto

import com.kebuu.enums.ClientStatus

data class ClientInfo(val host:String, val port: Int, val status: ClientStatus) {

}

