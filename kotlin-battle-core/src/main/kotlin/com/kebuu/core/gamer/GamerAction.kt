package com.kebuu.core.gamer

import com.kebuu.core.action.StepAction
import com.kebuu.core.action.validation.ActionValidator

class GamerAction(val gamer: Gamer, val action: StepAction){

    fun isValid(validator: ActionValidator) = action.validateBy(validator)
}

