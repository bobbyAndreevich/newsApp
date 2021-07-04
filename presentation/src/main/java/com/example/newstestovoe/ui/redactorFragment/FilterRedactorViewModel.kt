package com.example.newstestovoe.ui.redactorFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entities.Filter
import com.example.domain.useCases.AddFilterUseCase
import com.example.domain.useCases.UpdateFilterUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FilterRedactorViewModel(private val addFilterUseCase: AddFilterUseCase,
                              private val updateFilterUseCase: UpdateFilterUseCase) : ViewModel(),
    CoroutineScope {

    var isEnteredName = MutableLiveData<Boolean>().apply { value = true }
    var isAllFieldsFilled = MutableLiveData<Boolean>().apply { value = true }
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler{ _, e -> throw e}

    var name = MutableLiveData<String>().apply {   value = ""  }
    var description = MutableLiveData<String>().apply {    value = "" }

    fun updateFilterData(filter : Filter){
        name.value = filter.name
        description.value = filter.description
    }

    fun saveNewFilter() = launch {
        withContext(Dispatchers.IO){
            val filter = collectFilter()
            addFilterUseCase.addFilter(filter)
        }
    }

    private fun collectFilter() : Filter{
        return Filter(name.value!!, description.value!!)
    }

    fun saveChangedFilter(filter: Filter) = launch {
        withContext(Dispatchers.IO){
            val newFilter = collectFilter()
            newFilter.id = filter.id
            updateFilterUseCase.updateFilter(newFilter)
        }
    }

    fun validation() : Boolean {
        if (name.value!!.isEmpty()){
            isEnteredName.value = false
            isAllFieldsFilled.value = false
            return false
        }
        else{
            isEnteredName.value = true
            isAllFieldsFilled.value = true
            return true
        }
    }

}