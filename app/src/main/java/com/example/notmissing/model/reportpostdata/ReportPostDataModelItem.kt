package com.example.notmissing.model.reportpostdata

import java.io.Serializable

data class ReportPostDataModelItem(
    val content: String,
    val created_at: String,
    val post_id: Int,
    val title: String,
    val writer: String
) : Serializable