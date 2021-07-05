package com.example.newstestovoe.ui.newsFragment

import android.widget.Filterable
import androidx.lifecycle.*
import com.example.domain.entities.Filter
import com.example.domain.entities.News
import com.example.domain.useCases.GetFiltersUseCase
import com.example.domain.useCases.GetNewsUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NewsViewModel(private val getFiltersUseCase: GetFiltersUseCase,
                    private val getNewsUseCase: GetNewsUseCase) : ViewModel(),
    CoroutineScope, Filterable {


    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler{ _, e -> throw e}

    private lateinit var newsObserver: Observer<List<News>>
    private lateinit var filtersObserver : Observer<List<Filter>>

    private val mutableNewsData = MutableLiveData<List<News>>()
    val newsData: LiveData<List<News>> = mutableNewsData

    private val mutableFiltersData = MutableLiveData<List<Filter>>()
    val filtersData: LiveData<List<Filter>> = mutableFiltersData
    private var notFilteredList = listOf<News>()

    val filterSelected = MutableLiveData<Int>().apply { value = 0 }




    init {
        onCreate()
    }

    fun getNews() = mutableNewsData.value

    override fun onCleared() {
        super.onCleared()
        getNewsUseCase.getNews().asLiveData().removeObserver(newsObserver)
        getFiltersUseCase.getFilters().asLiveData().removeObserver(filtersObserver)
        coroutineContext.cancelChildren()
    }

    private fun onCreate(){
        newsObserver = Observer {
            if (filterSelected.value == 0) {
                mutableNewsData.value = it.sortedBy { it.publishedAt }
                notFilteredList = mutableNewsData.value!!
            }
            else{
                notFilteredList = it.sortedBy { it.publishedAt }
                mutableNewsData.value = notFilteredList.filter {
                    it.stringFilter == filtersData.value!![filterSelected.value!! - 1].name
                }
            }
        }
        getNewsUseCase.getNews().asLiveData().observeForever(newsObserver)


        filtersObserver = Observer {
            mutableFiltersData.value = it
        }
        getFiltersUseCase.getFilters().asLiveData().observeForever(filtersObserver)
    }




    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val stringFilter = constraint.toString()
                val results = FilterResults()
                if (stringFilter ==  "Все")
                    results.values = notFilteredList
                else
                    results.values = notFilteredList.filter { it.stringFilter == stringFilter }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mutableNewsData.value = results?.values as? List<News>
            }
        }
    }
}