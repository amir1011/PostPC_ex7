package huji.postpc2021.amir1011.ex7

import java.util.*

enum class Status{
    Waiting,
    InProgress,
    Ready /*,
    Done*/
}

class SandwichOrder(_name: String?, _pickles: Int, _tahini: Boolean, _hummus: Boolean,
                    _comment: String, _id: String?) {
    constructor() : this(null, 0, false, false, "", null) {
    }
    private var id: String = _id ?: UUID.randomUUID().toString()
    private var name: String? = _name
    var pickles: Int = _pickles
    var tahini: Boolean = _tahini
    var hummus: Boolean = _hummus
    var comment: String = _comment
    var status: String = "waiting"

    fun getSandwichId(): String {
        return id
    }
    fun getSandwichHummus(): Boolean {
        return hummus
    }
    fun getSandwichName(): String? {
        return name
    }
    fun getSandwichPickles(): Int {
        return pickles
    }
    fun getSandwichTahini(): Boolean {
        return tahini
    }
    fun getSandwichComment(): String {
        return comment
    }
    fun getSandwichStatus(): String {
        return status
    }

    fun setSandwichId(_id: String){
        id = _id
    }
    fun setSandwichPickles(_pickles: Int){
        pickles = _pickles
    }
    fun setSandwichTahini(_tahini: Boolean){
        tahini = _tahini
    }
    fun setSandwichHummus(_hummus: Boolean){
        hummus = _hummus
    }
    fun setSandwichComment(_comment: String){
        comment = _comment
    }
    fun setSandwichStatus(_status: String){
        status = _status
    }
    fun setSandwichName(_name: String){
        name = _name
    }

}