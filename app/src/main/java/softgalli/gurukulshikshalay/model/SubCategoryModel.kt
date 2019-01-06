package softgalli.gurukulshikshalay.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

class SubCategoryModel {
    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<QuizSubCategory>? = null


}

@Parcelize
class QuizSubCategory : Parcelable {
    @SerializedName("id")
    var subcat_id: String? = null
    @SerializedName("subCatNme")
    var subCatNme: String? = null
    @SerializedName("catId")
    var catId: String? = null
    @SerializedName("school_id")
    var school_id: String? = null
}
