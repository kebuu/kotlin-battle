package com.kebuu.core.comparator

import com.kebuu.core.comparator.ActionComparator.compare
import com.kebuu.core.gamer.GamerAction
import java.util.*

object GamerActionComparator: Comparator<GamerAction> {

    override fun compare(gamerAction1: GamerAction, gamerAction2: GamerAction): Int =
        compare(gamerAction1.action, gamerAction2.action)
}