package com.example.notmissing.model.safespot

data class SafeSpotModel(
    val list: List<Spot>,
    val msg: String,
    val result: String,
    val totalCount: Int
)