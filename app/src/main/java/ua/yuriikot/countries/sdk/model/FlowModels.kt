package ua.yuriikot.countries.sdk.model

import java.util.*


class Flow(var screens: List<Screen>)

class Screen(
    val uniqueName: String,
    val title: String,
    val backAllowed: Boolean,
    val skipAllowed: Boolean,
    val nextAllowed: Boolean,
    val goNextCondition: GoNextCondition,
    val nextScreen: String,
    val widgetType: WidgetType,
    val widget: Widget
) {
    companion object {
        enum class GoNextCondition { NEXT, FINISH, CUSTOM }
        enum class WidgetType { INPUT_WIDGET, LIST_WIDGET }
    }

}

open class Widget(var uniqueName: String)

class InputWidget(
    uniqueName: String,
    var hint: String,
    var limit: Int
) : Widget(uniqueName = uniqueName)


class ChooseListWidget(
    uniqueName: String,
    val elements: List<ListElement>
) : Widget(uniqueName = uniqueName)

class YesNoWidget(
    uniqueName: String,
    val yesElement: ListElement,
    val noElement: ListElement
) : Widget(uniqueName = uniqueName)

class DatePickerWidget(
    uniqueName: String,
    from :String,
    to: String
) : Widget(uniqueName = uniqueName)


date pickers  screen5 screen6

from = "${screen5.widget.value}"


class ListElement(
    val uniqueId: String,
    val name: String,
    val nextScreenUniqueName: String
)