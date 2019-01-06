package softgalli.gurukulshikshalay.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.quize_mgmt_activity.*
import softgalli.gurukulshikshalay.R
import softgalli.gurukulshikshalay.adapter.CategoryAdapter
import softgalli.gurukulshikshalay.common.Utilz
import softgalli.gurukulshikshalay.intrface.ClickPosition
import softgalli.gurukulshikshalay.model.CategoryModel
import softgalli.gurukulshikshalay.retrofit.DownlodableCallback
import softgalli.gurukulshikshalay.retrofit.RetrofitDataProvider

public class QuizQuesCategoryActivity : AppCompatActivity() {
    var retrofitDataProvider: RetrofitDataProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quize_mgmt_activity)
        retrofitDataProvider = RetrofitDataProvider(this)

        if (Utilz.isInternetConnected(this@QuizQuesCategoryActivity)) {
            callCatApi()
        } else {
        }


    }

    private fun callCatApi() {
        retrofitDataProvider!!.quizCat("10", object : DownlodableCallback<CategoryModel> {
            override fun onSuccess(result: CategoryModel?) {
                if (result != null) {
                    if (result.data!!.size > 0) {
                        rv_category.adapter =  CategoryAdapter(this@QuizQuesCategoryActivity, result.data!!, object : ClickPosition {
                            override fun pos(position: Int) {
                                startActivity(Intent(this@QuizQuesCategoryActivity, QuizQuesSubCategory::class.java).putExtra("id", result.data!![position].cat_id).putExtra("name", result.data!![position].catName))
                            }

                            override fun pos(position: Int, name: String?, count: Int) {
                            }
                        })
                    }
                }
            }

            override fun onFailure(error: String?) {
                Toast.makeText(this@QuizQuesCategoryActivity, R.string.something_went_wrong_error_message, Toast.LENGTH_LONG).show()
            }

            override fun onUnauthorized(errorNumber: Int) {
                Toast.makeText(this@QuizQuesCategoryActivity, R.string.something_went_wrong_error_message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
