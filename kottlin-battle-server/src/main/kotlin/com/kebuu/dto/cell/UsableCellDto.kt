package com.kebuu.dto.cell

import com.kebuu.dto.UserDto

open class UsableCellDto(val users: List<UserDto> = emptyList()): CellDto() {

}

