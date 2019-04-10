package ua.yuriikot.countries.sdk.model


class Answers(
    val answers: List<Answers> = ArrayList()
)

class Answer(
    val answerType: String,
    val widgetUniqueName: String,
    val screenUniqueName: String,
    val answer: Unit
)