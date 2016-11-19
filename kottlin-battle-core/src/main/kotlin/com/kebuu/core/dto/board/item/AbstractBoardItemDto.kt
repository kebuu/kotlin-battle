package com.kebuu.core.dto.board.item

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
        JsonSubTypes.Type(value = HoleBoardItemDto::class, name = "hole"),
        JsonSubTypes.Type(value = MountainBoardItemDto::class, name = "mountain"),
        JsonSubTypes.Type(value = SpawnBoardItemDto::class, name = "spawn"),
        JsonSubTypes.Type(value = TreasureBoardItemDto::class, name = "treasure")
)
abstract class AbstractBoardItemDto