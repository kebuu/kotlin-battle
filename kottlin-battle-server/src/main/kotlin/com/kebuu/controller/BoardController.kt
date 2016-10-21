package com.kebuu.controller

import com.kebuu.dto.BoardDto
import com.kebuu.dto.cell.EmptyCellDto
import com.kebuu.dto.cell.MoutainCellDto
import com.kebuu.dto.cell.UsableCellDto
import com.kebuu.dto.cell.ZPointCellDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/boards")
internal class BoardController {

    val restTemplate : RestTemplate = RestTemplate()

    @GetMapping("/{id}")
    fun getBoard(): BoardDto {
        val cells = listOf(
            listOf(EmptyCellDto(), MoutainCellDto()),
            listOf(UsableCellDto(), ZPointCellDto(10))
        )

        return BoardDto(cells)
    }
}

