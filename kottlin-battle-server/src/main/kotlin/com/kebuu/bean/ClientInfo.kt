package com.kebuu.bean

import com.kebuu.enums.ClientStatus

data class ClientInfo(val host:String, val port: Int, val status: ClientStatus) {

}

