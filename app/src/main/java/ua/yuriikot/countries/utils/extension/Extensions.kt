package ua.yuriikot.countries.utils.extension

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer


fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, android.arch.lifecycle.Observer {
        it?.let(observer)
    })
}

fun <T> LiveData<T>.observeOneTimeNonNull(lifecycleOwner: LifecycleOwner, observer: (t: T) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            t?.let {
                observer.invoke(it)
                removeObserver(this)
            }
        }
    })
}