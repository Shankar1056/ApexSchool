package softgalli.gurukulshikshalay.model

import com.google.gson.annotations.SerializedName

class CategoryModel {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<QuizCategory>? = null

}

class QuizCategory {
    @SerializedName("id")
    var cat_id: String? = null
    @SerializedName("catName")
    var catName: String? = null
    @SerializedName("catIcon")
    var catIcon: String? = null
    @SerializedName("school_id")
    var school_id: String? = null
}
