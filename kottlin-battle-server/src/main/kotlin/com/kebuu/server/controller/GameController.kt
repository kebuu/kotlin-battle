package com.kebuu.server.controller

import com.kebuu.server.dto.GameDto
import com.kebuu.server.exception.UnknownUserException
import com.kebuu.server.manager.GameManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/games")
class GameController @Autowired constructor(val gameManager: GameManager) {

    @GetMapping("/active")
    fun getActiveGame(): GameDto {
        return GameDto(gameManager.getActiveGame())
    }

    @GetMapping("/start")
    fun getAciveGame() {
        Thread(Runnable {
            gameManager.start()
        }).start()
    }

    @GetMapping("/register")
    fun register(@RequestParam port: Int, principal: Principal, request: HttpServletRequest): ResponseEntity<Void> {
        return try {
            gameManager.register(principal.name, request.remoteAddr, port)
            ResponseEntity.ok().build()
        } catch (e: UnknownUserException) {
            ResponseEntity.notFound().build()
        } catch (e: UnknownUserException) {
            ResponseEntity.status(428).build()
        }
    }

    @GetMapping("/unregister")
    fun unregister(principal: Principal): ResponseEntity<Void> {
        return try {
            gameManager.unregister(principal.name)
            ResponseEntity.ok().build()
        } catch (e: UnknownUserException) {
            ResponseEntity.status(428).build()
        }
    }
}

