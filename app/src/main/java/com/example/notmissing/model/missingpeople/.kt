package com.example.notmissing.model.missingpeople

import java.io.Serializable

data class People(
    val age: Int,
    val ageNow: String,
    val alldressingDscd: String,
    val bdwgh: Int,
    val faceshpeDscd: String,
    val frmDscd: String,
    val haircolrDscd: String,
    val hairshpeDscd: String,
    val height: Int,
    val msspsnIdntfccd: Int,
    val nltyDscd: String,
    val nm: String,
    val occrAdres: String,
    val occrde: String,
    val rnum: Int,
    val sexdstnDscd: String,
    val tknphotoFile: String,
    val tknphotolength: Int,
    val writngTrgetDscd: String
) : Serializable