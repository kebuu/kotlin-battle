package com.kebuu.server.bean

import com.kebuu.core.enums.ClientStatus

data class ClientInfo(val host:String, val port: Int, val status: ClientStatus) {

}

