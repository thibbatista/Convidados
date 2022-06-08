package com.thiagosantos.convidados.service.constants

class DataBaseConstants private constructor(){

    /*
    Tabelas disponiveis no banco de dados com suas colunas
     */

    object GUEST{
        const val  TABLE_NAME = "guest"

        object COLUMNS{
            const val ID = "id"
            const val  NAME = "name"
            const val PRESENCE = "presence"
        }
    }
}