package com.example.notmissing.model.safespot

import java.io.Serializable

data class Spot(
    val adres: String,
    val bsshNm: String,
    val cl: String,
    val clNm: String,
    val etcAdres: Any,
    val hmpg: Any,
    val lcSn: Int,
    val lcinfoLa: Double,
    val lcinfoLo: Double,
    val rn: Int,
    val scope: Any,
    val scopeCd: Any,
    val telno: String,
    val zip: String
) : Serializable