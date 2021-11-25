package com.example.notmissing.model.missingpeople

data class MissingPeopleModel(
    val list: List<People>,
    val msg: String,
    val result: String,
    val totalCount: Int
)