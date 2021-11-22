package com.sharif.tech_challenge.data.model

//Card
data class CardModel (
        override val cards: List<CardResult>
): BaseResponseListModel<CardResult>

