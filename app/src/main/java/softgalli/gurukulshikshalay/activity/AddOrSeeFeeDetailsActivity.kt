package softgalli.gurukulshikshalay.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import softgalli.gurukulshikshalay.R
import softgalli.gurukulshikshalay.common.AppConstants
import softgalli.gurukulshikshalay.common.KotlinUtils
import softgalli.gurukulshikshalay.common.Utilz
import softgalli.gurukulshikshalay.model.FeeDetailsModel
import softgalli.gurukulshikshalay.model.FeeTypeModel
import softgalli.gurukulshikshalay.retrofit.DownlodableCallback
import softgalli.gurukulshikshalay.retrofit.RetrofitDataProvider
import java.lang.Integer.parseInt
import java.util.*

class AddOrSeeFeeDetailsActivity : AppCompatActivity() {
   /* @BindView(R.id.updateFeeDetailsTV)
    internal var updateFeeDetailsTV: TextView? = null
    @BindView(R.id.deleteFeeDetailsTV)
    internal var deleteFeeDetailsTV: TextView? = null
    @BindView(R.id.classAndSessionTV)
    internal var classAndSessionTV: TextView? = null
    @BindView(R.id.totalFeeTV)
    internal var totalFeeTV: TextView? = null
    @BindView(R.id.feeTypeAndAmountLl)
    internal var feeTypeAndAmountLl: LinearLayout? = null
    @BindView(R.id.classNameSpinner)
    internal var classNameSpinner: Spinner? = null
    @BindView(R.id.sectionNameSpinner)
    internal var sectionNameSpinner: Spinner? = null
    @BindView(R.id.noRecordFoundTv)
    internal var noRecordFoundTv: TextView? = null
    @BindView(R.id.mainCardView)
    internal var mainCardView: CardView? = null
    internal var updateDeleteLV: LinearLayout? = null
    @BindView(R.id.noRecordFoundCardView)
    internal var noRecordFoundCardView: CardView? = null
    private var retrofitDataProvider: RetrofitDataProvider? = null
    private var mStrClass = AppConstants.STR_10
    private var mStrSession = AppConstants.STR_SESSION*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_students_activity)
       /* ButterKnife.bind(this)

        initToolbar()
        initView()
        manageClicks()
        manageSelectingClassAndSession()*/
    }
/*
    private fun manageClicks() {
        val addFeeFab = findViewById<FloatingActionButton>(R.id.addFeeFab)
        addFeeFab.setOnClickListener {
            // Click action
            showAddUpdateFeeDetails(AppConstants.ADD)
        }
        updateFeeDetailsTV?.setOnClickListener {
            callFeeDetailsApi(AppConstants.UPDATE)
        }
        deleteFeeDetailsTV?.setOnClickListener {
            callFeeDetailsApi(AppConstants.DELETE)
        }
    }

    fun showAddUpdateFeeDetails(operation: String) {
        if (operation?.isNullOrEmpty() || operation?.equals(AppConstants.ADD)) {

        } else if (operation?.isNullOrEmpty() || operation?.equals(AppConstants.UPDATE)) {

        }
        //callFeeDetailsApi(operation)
    }

    private fun initView() {
        updateDeleteLV = findViewById(R.id.updateDeleteLV)
        noRecordFoundTv?.setText(getString(R.string.please_add_fee_structure))
        retrofitDataProvider = RetrofitDataProvider(this)
    }

    override fun onResume() {
        super.onResume()
        callFeeDetailsApi(AppConstants.GET)
    }

    private fun getClassWiseFeeDetailsList(mStrOperation: String) {
        Utilz.showDailog(this, resources.getString(R.string.pleasewait))
        retrofitDataProvider?.getClassWiseFeeDetailsList(this, mStrClass, mStrSession, mStrOperation, object : DownlodableCallback<FeeDetailsModel> {
            override fun onSuccess(result: FeeDetailsModel?) {
                Utilz.closeDialog()
                if (result != null && result.status?.contains(AppConstants.TRUE) ?: false) {
                    noRecordFoundCardView?.setVisibility(View.GONE)
                    mainCardView?.setVisibility(View.VISIBLE)
                    //Updating UI.
                    updateUI(result)
                } else {
                    noRecordFoundCardView?.setVisibility(View.VISIBLE)
                    mainCardView?.setVisibility(View.GONE)
                }
            }

            override fun onFailure(error: String) {
                Utilz.closeDialog()
                KotlinUtils.showToast(applicationContext, getString(R.string.something_went_wrong_error_message))
            }

            override fun onUnauthorized(errorNumber: Int) {
                Utilz.closeDialog()
                KotlinUtils.showToast(applicationContext, getString(R.string.something_went_wrong_error_message))
            }
        })
    }

    private fun updateUI(feeDetailsModel: FeeDetailsModel?) {
        KotlinUtils.showToast(applicationContext, feeDetailsModel?.msg + "")
        if (feeDetailsModel != null && feeDetailsModel.data != null && !feeDetailsModel.data!!.isEmpty()) {
            classAndSessionTV?.setText(getString(R.string.class_name) + " " + mStrClass + " (" + mStrSession + ")")
            var feeTypeArrayList = feeDetailsModel.data;
            var totalFee = 0
            for (each: FeeTypeModel in feeTypeArrayList!!) {
                // creating TextView programmatically
                val feeTypeTv = TextView(this)
                feeTypeTv.textSize = 14f
                feeTypeTv.setTextColor(ContextCompat.getColor(applicationContext, R.color.textColor))
                feeTypeTv.text = each.feeType + " : " + getString(R.string.rupee_symbol) + each.feeAmount
                totalFee = totalFee + parseInt(each.feeAmount!!)
                // add TextView to LinearLayout
                feeTypeAndAmountLl?.addView(feeTypeTv)
            }
            totalFeeTV?.setText(getString(R.string.total_fee) + " : " + getString(R.string.rupee_symbol) + totalFee)
        } else {
            noRecordFoundCardView?.setVisibility(View.VISIBLE)
            mainCardView?.setVisibility(View.GONE)
        }
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(getString(R.string.class_wise_fee_details))
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun manageSelectingClassAndSession() {
        val classList = ArrayList<String>()
        val sectionList = ArrayList<String>()

        classList.addAll(Utilz.getClassList())

        sectionList.addAll(Utilz.getSessionList())
        val classAdapter = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, classList)
        val sectionAdapter = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, sectionList)
        classAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        classNameSpinner?.setAdapter(classAdapter)
        sectionNameSpinner?.setAdapter(sectionAdapter)
        classNameSpinner?.setSelection(1)
        sectionNameSpinner?.setSelection(1)
        classNameSpinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                mStrClass = Utilz.getSelectedClass(position)
                callFeeDetailsApi(AppConstants.GET)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        })
        sectionNameSpinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                mStrSession = Utilz.getSelectedSection(position)
                callFeeDetailsApi(AppConstants.GET)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        })
    }

    private fun callFeeDetailsApi(operation: String) {
        if (Utilz.isOnline(this)) {
            if (mStrClass?.isEmpty()) {
                KotlinUtils.showToast(applicationContext, getString(R.string.please_select_class))
            } else if (mStrSession?.isEmpty()) {
                KotlinUtils.showToast(applicationContext, getString(R.string.please_select_session))
            } else {
                getClassWiseFeeDetailsList(operation)
            }
        } else {
            Utilz.showNoInternetConnectionDialog(this)
        }
    }*/
}
