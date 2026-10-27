package tech.mohamedashraf.presentation.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<STATE, INTENT> : ViewModel() {

    var state = MutableLiveData<STATE>()

    var intent = MutableLiveData<INTENT>()

    private var observer: Observer<INTENT> = Observer { }

    fun observeIntents(observer: Observer<INTENT>) {
        this@BaseViewModel.observer = observer
        intent.observeForever(observer)
    }

    fun clear() {
        super.onCleared()
        intent.removeObserver(observer)
        state = MutableLiveData()
        intent = MutableLiveData()
    }
}