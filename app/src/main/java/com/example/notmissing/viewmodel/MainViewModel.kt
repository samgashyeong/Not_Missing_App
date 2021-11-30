package com.example.notmissing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notmissing.model.missingpeople.People
import com.example.notmissing.model.reportpostdata.ReportPostDataModel
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem
import com.example.notmissing.model.safespot.Spot

class MainViewModel : ViewModel() {
    val missingPeopleList : MutableLiveData<ArrayList<People>> = MutableLiveData()
    val mapList : MutableLiveData<ArrayList<Spot>> = MutableLiveData()
    val postList : MutableLiveData<ArrayList<ReportPostDataModelItem>> = MutableLiveData()

}