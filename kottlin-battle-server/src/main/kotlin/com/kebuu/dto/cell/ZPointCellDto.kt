package com.kebuu.dto.cell

import com.kebuu.dto.UserDto

class ZPointCellDto(val points: Int, users: List<UserDto> = emptyList()): UsableCellDto(users) {

}

