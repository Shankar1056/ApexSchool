package softgalli.gurukulshikshalay.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_callist.*
import kotlinx.android.synthetic.main.toolbar.*
import softgalli.gurukulshikshalay.R
import softgalli.gurukulshikshalay.adapter.CateoryListAdapter
import softgalli.gurukulshikshalay.common.Utilz
import softgalli.gurukulshikshalay.intrface.ClickPosition
import softgalli.gurukulshikshalay.model.QuesAnsModel
import softgalli.gurukulshikshalay.model.SubCategoryModel
import softgalli.gurukulshikshalay.retrofit.DownlodableCallback
import softgalli.gurukulshikshalay.retrofit.RetrofitDataProvider

class QuizQuesSubCategory : AppCompatActivity() {

    var retrofitDataProvider: RetrofitDataProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_callist)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)

        if (Utilz.isInternetConnected(this@QuizQuesSubCategory)) {
            callCatApi()
        } else {
            Toast.makeText(this@QuizQuesSubCategory, "" + resources.getString(R.string.no_internet_connection_msg), Toast.LENGTH_SHORT).show()
        }

    }

    private fun callCatApi() {
        retrofitDataProvider!!.quizSubCat("1", "10", object : DownlodableCallback<SubCategoryModel> {
            override fun onSuccess(result: SubCategoryModel?) {
                if (result != null) {
                    if (result.data!!.size > 0) {
                        rv_list.adapter = CateoryListAdapter(this@QuizQuesSubCategory, result.data!!, object : ClickPosition {
                            override fun pos(position: Int) {
                                callQuesApi(result.data!![position].subcat_id, result.data!![position].subCatNme)
                            }

                            override fun pos(position: Int, name: String?, count: Int) {
                            }
                        })
                    }
                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }

    private fun callQuesApi(subcat_id: String?, subCatNme: String?) {
        retrofitDataProvider!!.quizQuestion(subcat_id, "10",  object : DownlodableCallback<QuesAnsModel> {
            override fun onSuccess(result: QuesAnsModel?) {
                if (result != null) {
                    if (result.data!!.size > 0) {
                        startActivity(Intent(this@QuizQuesSubCategory, QuesAnsActivity::class.java).putParcelableArrayListExtra("list", result.data).putExtra("name", subCatNme))
                    }
                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }
}