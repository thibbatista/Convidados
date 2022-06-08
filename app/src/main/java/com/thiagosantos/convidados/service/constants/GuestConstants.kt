package com.thiagosantos.convidados.service.constants

class GuestConstants private constructor() {
    companion object {
        const val GUESTID = "guestID"

    }


    object FILTER {
        const val Empty = 0
        const val PRESENT = 1
        const val ABSENT = 2
    }


}