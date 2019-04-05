package ua.yuriikot.countries.utils.extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


fun FragmentManager.addFragment(containerID: Int, fragment: Fragment, backStack: String? = null) {
    val transaction = beginTransaction()
        .add(containerID, fragment)
    backStack?.let(transaction::addToBackStack)
    transaction.commit()
}
