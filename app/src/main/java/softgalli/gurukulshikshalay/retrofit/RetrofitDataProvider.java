package softgalli.gurukulshikshalay.retrofit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softgalli.gurukulshikshalay.R;
import softgalli.gurukulshikshalay.common.AppConstants;
import softgalli.gurukulshikshalay.common.ClsGeneral;
import softgalli.gurukulshikshalay.common.Utilz;
import softgalli.gurukulshikshalay.intrface.DialogClickListener;
import softgalli.gurukulshikshalay.model.AboutUsModel;
import softgalli.gurukulshikshalay.model.CategoryModel;
import softgalli.gurukulshikshalay.model.CommonResponse;
import softgalli.gurukulshikshalay.model.EventsAndNoticeLisrModel;
import softgalli.gurukulshikshalay.model.FeeDetailsModel;
import softgalli.gurukulshikshalay.model.FeedBackModel;
import softgalli.gurukulshikshalay.model.GalleryModel;
import softgalli.gurukulshikshalay.model.GeneralParamsModel;
import softgalli.gurukulshikshalay.model.InsertAttendanceModel;
import softgalli.gurukulshikshalay.model.QuesAnsModel;
import softgalli.gurukulshikshalay.model.RequestedLeaveModel;
import softgalli.gurukulshikshalay.model.StuTeaModel;
import softgalli.gurukulshikshalay.model.StudentListByClassModel;
import softgalli.gurukulshikshalay.model.SubCategoryModel;
import softgalli.gurukulshikshalay.model.TeacherListModel;
import softgalli.gurukulshikshalay.model.TopperLisrModel;
import softgalli.gurukulshikshalay.model.UserDetailsModel;

/**
 * Created by Shankar on 1/27/2018.
 */

public class RetrofitDataProvider extends AppCompatActivity implements ServiceMethods {
    private static final String TAG = RetrofitDataProvider.class.getSimpleName();
    Context context;

    public RetrofitDataProvider(Context context) {
        this.context = context;
    }

    private ApiRetrofitService createRetrofitService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiRetrofitService.class);
    }

    @Override
    public void addteacher(final Context mActivity, String teacher_id, String name, String qualification, String mobile_number, String alternate_number,
                           String email_id, String classteacher_for, String joining_date, String address, String mStrOperation, String mStrImageUrl, String facebookId, String whatTeach, final DownlodableCallback<StuTeaModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().addTeacher(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                teacher_id, name, qualification, mobile_number, alternate_number, email_id, classteacher_for, joining_date, address, mStrOperation, mStrImageUrl, facebookId, whatTeach).enqueue(
                new Callback<StuTeaModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StuTeaModel> call, @NonNull final Response<StuTeaModel> response) {
                        if (response.isSuccessful()) {
                            StuTeaModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StuTeaModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    @Override
    public void addstudent(final Context mActivity, String user_id, String rollNumber, String name, String email, String mobile, String clas, String sec,
                           String admission_date, String residential_address, String fName, String fMobile, String mName, String mMobile, final DownlodableCallback<StuTeaModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().addStudent(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                user_id, rollNumber, name, email, mobile, clas, sec, admission_date, residential_address, fName, fMobile, mName, mMobile).enqueue(
                new Callback<StuTeaModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StuTeaModel> call, @NonNull final Response<StuTeaModel> response) {
                        if (response.isSuccessful()) {
                            StuTeaModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StuTeaModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void galleryList(final Context mActivity, final DownlodableCallback<GalleryModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().listGallery(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId()).enqueue(
                new Callback<GalleryModel>() {
                    @Override
                    public void onResponse(@NonNull Call<GalleryModel> call, @NonNull final Response<GalleryModel> response) {
                        if (response.isSuccessful()) {
                            GalleryModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GalleryModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    @Override
    public void topperlist(final Context mActivity, final DownlodableCallback<TopperLisrModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().topperList(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId()).enqueue(
                new Callback<TopperLisrModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TopperLisrModel> call, @NonNull final Response<TopperLisrModel> response) {
                        if (response.isSuccessful()) {
                            TopperLisrModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TopperLisrModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    private GeneralParamsModel getGeneralParameters() {
        String apiKeyStr = ApiUrl.API_KEY;
        String tokenStr = ClsGeneral.getStrPreferences(AppConstants.TOKEN);
        String loginAsStr = ClsGeneral.getStrPreferences(AppConstants.LOGIN_AS);
        String schoolIdStr = ClsGeneral.getStrPreferences(AppConstants.SCHOOL_ID);

        GeneralParamsModel generalParamsModel = new GeneralParamsModel(apiKeyStr, tokenStr, loginAsStr, schoolIdStr);
        Utilz.showLog(TAG, "apiKeyStr : " + apiKeyStr + ",\n" +
                "tokenStr : " + tokenStr + ",\n" +
                "loginAsStr : " + loginAsStr + ",\n" +
                "schoolIdStr : " + schoolIdStr);
        return generalParamsModel;
    }

    @Override
    public void teacherList(final Context mActivity, final DownlodableCallback<TeacherListModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().teacherList(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId()).enqueue(
                new Callback<TeacherListModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TeacherListModel> call, @NonNull final Response<TeacherListModel> response) {
                        if (response.isSuccessful()) {
                            TeacherListModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TeacherListModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    @Override
    public void feedback(final Context mActivity, String name, String mobile, String message, String rating, String date, final DownlodableCallback<FeedBackModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().sendFeedback(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                name, mobile, message, rating, date).enqueue(
                new Callback<FeedBackModel>() {
                    @Override
                    public void onResponse(@NonNull Call<FeedBackModel> call, @NonNull final Response<FeedBackModel> response) {
                        if (response.isSuccessful()) {
                            FeedBackModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<FeedBackModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void userLogin(final Activity mActivity, final String userIdStr, final String passwordStr, final String loginAs, final DownlodableCallback<UserDetailsModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().userLogin(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                loginAs,
                generalParamsModel.getSchoolId(),
                userIdStr, passwordStr).enqueue(
                new Callback<UserDetailsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UserDetailsModel> call, @NonNull final Response<UserDetailsModel> response) {
                        Utilz.closeDialog();
                        if (response.code() == AppConstants.RESPONSE_CODE_200) {
                            UserDetailsModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else if (response.code() == AppConstants.RESPONSE_CODE_204) {
                            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.failed_to_login),
                                    mActivity.getResources().getString(R.string.user_id_or_password_incorrect), getChoiceDialogClickListener(mActivity, response.code()));
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserDetailsModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void getAboutUsDetails(final Activity mActivity, String getDetailsFor, final DownlodableCallback<AboutUsModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getAboutUsDetails(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                getDetailsFor).enqueue(
                new Callback<AboutUsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<AboutUsModel> call, @NonNull final Response<AboutUsModel> response) {
                        Utilz.closeDialog();
                        if (response.code() == AppConstants.RESPONSE_CODE_200) {
                            AboutUsModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AboutUsModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    private void handleResponseCode(final Context mActivity, int code) {
        Utilz.closeDialog();
        /*if (code == AppConstants.RESPONSE_CODE_201) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.send_message), getChoiceDialogClickListener(mActivity, code));
        } else*/
        if (code == AppConstants.RESPONSE_CODE_204) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.no_data_found), mActivity.getResources().getString(R.string.no_data_found_message), getChoiceDialogClickListener(mActivity, code));
        } else if (code == AppConstants.RESPONSE_CODE_400) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.invalid_request), mActivity.getResources().getString(R.string.invalid_request_message), getChoiceDialogClickListener(mActivity, code));
        } else if (code == AppConstants.RESPONSE_CODE_405) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.invalid_request), mActivity.getResources().getString(R.string.invalid_request_message), getChoiceDialogClickListener(mActivity, code));
        } else if (code == AppConstants.RESPONSE_CODE_401) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.session_expired), mActivity.getResources().getString(R.string.invalid_session_login_again), getChoiceDialogClickListener(mActivity, code));
        } else {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.something_went_wrong), mActivity.getResources().getString(R.string.something_went_wrong_error_message), getChoiceDialogClickListener(mActivity, code));
        }
    }

    private DialogClickListener getChoiceDialogClickListener(final Context context, final int code) {
        return new DialogClickListener() {
            @Override
            public void onClickOfPositive() {
                if (code == AppConstants.RESPONSE_CODE_201) {
                    Utilz.showLog(TAG, "RESPONSE_CODE_201");
                } else if (code == AppConstants.RESPONSE_CODE_204) {
                    Utilz.showLog(TAG, "RESPONSE_CODE_204");
                } else if (code == AppConstants.RESPONSE_CODE_400) {
                    Utilz.showLog(TAG, "RESPONSE_CODE_400");
                } else if (code == AppConstants.RESPONSE_CODE_401) {
                    Utilz.showLog(TAG, "RESPONSE_CODE_401");
                    //logout and go to login screen
                    Utilz.logout(context);
                } else if (code == AppConstants.RESPONSE_CODE_405) {
                    Utilz.showLog(TAG, "RESPONSE_CODE_405");
                } else {
                    Utilz.showLog(TAG, "Something went wrong");
                }
            }
        };
    }

    @Override
    public void requestLeave(final Context mActivity, String user_id, String from_date, String to_date, String teacher_id, String description, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().requestLeave(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                user_id, from_date, to_date, teacher_id, description).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void requestedLeaveList(final Context mActivity, String loginedAs, String userId, final DownlodableCallback<RequestedLeaveModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().requestedLeaveList(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                userId).enqueue(
                new Callback<RequestedLeaveModel>() {
                    @Override
                    public void onResponse(@NonNull Call<RequestedLeaveModel> call, @NonNull final Response<RequestedLeaveModel> response) {
                        if (response.isSuccessful()) {
                            RequestedLeaveModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RequestedLeaveModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void updateRequestedLeave(final Context mActivity, String status, String user_id, String teacherId, String teacherComment, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().updateLeave(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                status, user_id, teacherId, teacherComment).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void getStudentsListByClassWise(final Context mActivity, String className, String sec, final DownlodableCallback<StudentListByClassModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getStudentsListByClassWise(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                className, sec).enqueue(
                new Callback<StudentListByClassModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StudentListByClassModel> call, @NonNull final Response<StudentListByClassModel> response) {
                        if (response.isSuccessful()) {
                            StudentListByClassModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StudentListByClassModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void getClassWiseFeeDetailsList(final Context mActivity, String className, String session, String operation, final DownlodableCallback<FeeDetailsModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getClassWiseFeeDetailsList(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                className, session, operation).enqueue(
                new Callback<FeeDetailsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<FeeDetailsModel> call, @NonNull final Response<FeeDetailsModel> response) {
                        if (response.isSuccessful()) {
                            FeeDetailsModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<FeeDetailsModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void getStudentsAttendance(final Context mActivity, String className, String sec, String studentId, String date, final DownlodableCallback<StudentListByClassModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getStudentsAttendance(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                className, sec, studentId, date).enqueue(
                new Callback<StudentListByClassModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StudentListByClassModel> call, @NonNull final Response<StudentListByClassModel> response) {
                        if (response.isSuccessful()) {
                            StudentListByClassModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StudentListByClassModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void attendance(final Context mActivity, InsertAttendanceModel insertAttendanceModel, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().insertAttendance(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                insertAttendanceModel).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void deleteStudent(final Context mActivity, String studentUserIdStr, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().deleteStudent(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                studentUserIdStr).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    public void changePassword(final Context mActivity, String userId, String loginType, String oldPassword, String newPassword, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().changePassword(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                userId, oldPassword, newPassword).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    public void addEvent(final Context mActivity, String title, String message, String date, String posted_by, String status, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().addEvent(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                title, message, date, posted_by, status).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    public void publishNotice(final Context mActivity, String title, String message, String date, String posted_by, String status, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().publishNotice(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                title, message, date, posted_by, status).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    public void callUpdateDeleteNoticeAndEvents(final Context mActivity, String title, String message, String date, String posted_by, String status, String mApiUrl, String mNoticeEventId, boolean isToUpdate, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        String isToUpdateStr;
        if (isToUpdate)
            isToUpdateStr = AppConstants.UPDATE;
        else
            isToUpdateStr = AppConstants.DELETE;
        Callback callback1 = new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    CommonResponse teacherListDataModelPojo = response.body();
                    callback.onSuccess(teacherListDataModelPojo);
                } else {
                    handleResponseCode(mActivity, response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                Utilz.showLog("Result", "t" + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        };
        if (!TextUtils.isEmpty(mApiUrl) && mApiUrl.equalsIgnoreCase(ApiUrl.EDIT_DELETE_NOTICE))
            createRetrofitService().callUpdateDeleteNoticeBoard(generalParamsModel.getApiKey(),
                    generalParamsModel.getToken(),
                    generalParamsModel.getLoginAs(),
                    generalParamsModel.getSchoolId(),
                    title, message, date, posted_by, status, mNoticeEventId, isToUpdateStr).enqueue(callback1);
        else
            createRetrofitService().callUpdateDeleteEvents(generalParamsModel.getApiKey(),
                    generalParamsModel.getToken(),
                    generalParamsModel.getLoginAs(),
                    generalParamsModel.getSchoolId(),
                    title, message, date, posted_by, status, mNoticeEventId, isToUpdateStr).enqueue(callback1);
    }

    @Override
    public void getEventsOrNoticeList(final Context mActivity, final boolean isToGetEventsList, final DownlodableCallback<EventsAndNoticeLisrModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        Callback callback1 = new Callback<EventsAndNoticeLisrModel>() {
            @Override
            public void onResponse(@NonNull Call<EventsAndNoticeLisrModel> call, @NonNull final Response<EventsAndNoticeLisrModel> response) {
                if (response.isSuccessful()) {
                    EventsAndNoticeLisrModel mobileRegisterPojo = response.body();
                    callback.onSuccess(mobileRegisterPojo);
                } else {
                    handleResponseCode(mActivity, response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventsAndNoticeLisrModel> call, @NonNull Throwable t) {
                Utilz.showLog("Result", "t" + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        };

        if (isToGetEventsList) {
            createRetrofitService().getEventsList(generalParamsModel.getApiKey(),
                    generalParamsModel.getToken(),
                    generalParamsModel.getLoginAs(),
                    generalParamsModel.getSchoolId()).enqueue(callback1);
        } else {
            createRetrofitService().getNoticeBoardList(generalParamsModel.getApiKey(),
                    generalParamsModel.getToken(),
                    generalParamsModel.getLoginAs(),
                    generalParamsModel.getSchoolId()).enqueue(callback1);
        }
    }


    @Override
    public void quizCat(String class_id, final DownlodableCallback<CategoryModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getQuizCat(generalParamsModel.getApiKey(),
                generalParamsModel.getSchoolId(), class_id).enqueue(
                new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CategoryModel> call, @NonNull final Response<CategoryModel> response) {
                        if (response.isSuccessful()) {
                            CategoryModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CategoryModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void quizSubCat(String catId, String class_id, final DownlodableCallback<SubCategoryModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getQuizSubCat(generalParamsModel.getApiKey(),
                generalParamsModel.getSchoolId(), catId, class_id).enqueue(
                new Callback<SubCategoryModel>() {
                    @Override
                    public void onResponse(@NonNull Call<SubCategoryModel> call, @NonNull final Response<SubCategoryModel> response) {
                        if (response.isSuccessful()) {
                            SubCategoryModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SubCategoryModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void quizQuestion(String subCatId, String class_id, final DownlodableCallback<QuesAnsModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getQuizQues(generalParamsModel.getApiKey(),
                generalParamsModel.getSchoolId(), subCatId, class_id).enqueue(
                new Callback<QuesAnsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<QuesAnsModel> call, @NonNull final Response<QuesAnsModel> response) {
                        if (response.isSuccessful()) {
                            QuesAnsModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<QuesAnsModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }
}