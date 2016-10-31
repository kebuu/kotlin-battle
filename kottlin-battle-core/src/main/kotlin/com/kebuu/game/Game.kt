package com.kebuu.game

import com.kebuu.Dimension
import com.kebuu.Position
import com.kebuu.action.MoveAction
import com.kebuu.board.Board
import com.kebuu.board.Hole
import com.kebuu.board.Mountain
import com.kebuu.board.ZPoint
import com.kebuu.board.spawn.Spawn
import com.kebuu.dto.GameInfo
import com.kebuu.enums.GameMode
import com.kebuu.enums.GameStatus
import com.kebuu.gamer.Gamer
import com.kebuu.gamer.GamerAction

open class Game(val id: Int,
           val mode: GameMode = GameMode.LEVEL_0) {

    var currentStep = 0
    val gamers: MutableList<Gamer> = mutableListOf()
    var status: GameStatus = GameStatus.CREATED
    val board: Board = Board()

    fun addGamers(vararg gamers: Gamer) {
        this.gamers.addAll(gamers)
    }

    fun init() {
        status = GameStatus.STARTED
        board.setDimension(Dimension(5, 5))

        for (gamer in gamers) {
            val spawnAttributes = gamer.getSpawnAttributes(0)
            val spawn = Spawn(gamer, spawnAttributes, Position.ORIGIN)
            board.addItem(spawn)
        }

        board.addItem(Hole(board.randomEmptyPosition()))
        board.addItem(Mountain(board.randomEmptyPosition()))
        board.addItem(ZPoint(position = board.randomEmptyPosition()))
    }

    fun end() {
        status = GameStatus.STOPPED
    }

    fun runStep(step: Int) {
        currentStep = step

        val actions = gamers.map {
            val nextAction = it.getNextAction(GameInfo(board.gamerSpawn(it), this))
            GamerAction(it, nextAction)
        }

        processActions(actions)
    }

    private fun processActions(gamerActions: List<GamerAction>) {
        for (gamerAction in gamerActions) {
            val action = gamerAction.action
            if (action is MoveAction) {
                board.gamerSpawn(gamerAction.gamer).moveTo(action.goTo)
            }
        }
    }
}

