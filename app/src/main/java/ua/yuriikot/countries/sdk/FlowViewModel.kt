package ua.yuriikot.countries.sdk

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ua.yuriikot.countries.sdk.model.Answers
import ua.yuriikot.countries.sdk.model.Flow
import ua.yuriikot.countries.sdk.model.ListElement
import ua.yuriikot.countries.sdk.model.Screen

class FlowViewModel : ViewModel() {

    lateinit var onToast: (String) -> Unit
    lateinit var onErrorToast: (String) -> Unit
    lateinit var onSnack: (String, () -> Unit) -> Unit

    fun applyListElementAnswer(listElement: ListElement) {
        // Todo store answer
        if (listElement.nextScreenUniqueName == "finish") {
            onToast("finished")
        } else {
            currentScreen.value = flow.screens.find { it.uniqueName == listElement.nextScreenUniqueName }
        }
    }

    fun onNextClick() {
        // Todo store answer
        val screen = currentScreen.value!!
        if (screen.goNextCondition == Screen.Companion.GoNextCondition.FINISH) {
            onToast("finished")
        } else {
            currentScreen.value = flow.screens.find { it.uniqueName == screen.nextScreen }

            isLoading.value = true
            makeSomeRequest()
        }
    }


    fun makeSomeRequest(){
        makeAsyncRequest({
            currentScreen.value = flow.screens.find { it.uniqueName == screen.nextScreen }
            isLoading.value = false
        }, { error ->{
            onSnack(error.message, this::makeSomeRequest)
            isLoading.value = false
        }})
    }

    lateinit var flow: Flow

    fun setFlow(flow: Flow) {
        this.flow = flow
        currentScreen.value = flow.screens[0]
    }


    val currentScreen = MutableLiveData<Screen>()
    val isLoading = MutableLiveData<Boolean>()

    val answers: Answers = Answers()

    fun runStringQuery(query: String){

    }



}
