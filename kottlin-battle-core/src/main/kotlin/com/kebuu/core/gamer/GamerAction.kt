package com.kebuu.core.gamer

import com.kebuu.core.action.StepAction
import com.kebuu.core.action.StepActionValidator

class GamerAction(val gamer: Gamer, val action: StepAction){

    fun isValid(validator: StepActionValidator) = action.isValid(validator)
}

