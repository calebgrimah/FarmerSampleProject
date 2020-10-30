package com.tellirium.farmer.api.model

import com.tellirium.farmer.db.model.Farmer

data class Content(
    val farmers: List<Farmer>,
    val imageBaseUrl: String,
    val totalRec: Int
)