package com.example.newstestovoe.ui.filterFragment

import androidx.lifecycle.*
import com.example.domain.entities.Filter
import com.example.domain.useCases.DeleteFilterUseCase
import com.example.domain.useCases.GetFiltersUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FiltersViewModel(private val getFiltersUseCase: GetFiltersUseCase,
                       private val deleteFilterUseCase: DeleteFilterUseCase) : ViewModel(), CoroutineScope {


    private val mutableFiltersData = MutableLiveData<List<Filter>>()
    val filtersData: LiveData<List<Filter>> = mutableFiltersData
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler{ _, e -> throw e}


    init {
        onCreate()
    }

    fun getFilters() = filtersData.value
    fun filterDeleted(filter: Filter) = launch {
        withContext(Dispatchers.IO){
            deleteFilterUseCase.deleteFilter(filter)
        }
    }

    private lateinit var observer : Observer<List<Filter>>
    private fun onCreate(){
        observer = Observer {
            mutableFiltersData.value = it
        }
        getFiltersUseCase.getFilters().asLiveData().observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        getFiltersUseCase.getFilters().asLiveData().removeObserver(observer)
        coroutineContext.cancelChildren()
    }


}