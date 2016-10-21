package com.kebuu.dto.cell

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
        JsonSubTypes.Type(value = EmptyCellDto::class, name = "empty"),
        JsonSubTypes.Type(value = MoutainCellDto::class, name = "moutain"),
        JsonSubTypes.Type(value = ZPointCellDto::class, name = "zpoint"),
        JsonSubTypes.Type(value = UsableCellDto::class, name = "usable")
)
abstract class CellDto {

}