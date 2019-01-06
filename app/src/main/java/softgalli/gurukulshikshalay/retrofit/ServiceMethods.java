package softgalli.gurukulshikshalay.retrofit;


import android.app.Activity;
import android.content.Context;

import softgalli.gurukulshikshalay.model.AboutUsModel;
import softgalli.gurukulshikshalay.model.CategoryModel;
import softgalli.gurukulshikshalay.model.CommonResponse;
import softgalli.gurukulshikshalay.model.EventsAndNoticeLisrModel;
import softgalli.gurukulshikshalay.model.FeeDetailsModel;
import softgalli.gurukulshikshalay.model.FeedBackModel;
import softgalli.gurukulshikshalay.model.GalleryModel;
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

public interface ServiceMethods {

    void userLogin(Activity mActivity, String loginAs, String mobile, String message, DownlodableCallback<UserDetailsModel> callback);

    void getAboutUsDetails(Activity mActivity, String getDetailsFor, DownlodableCallback<AboutUsModel> callback);

    void addteacher(Context mActivity, String teacher_id, String name, String qualification, String mobile_number, String alternate_number, String email_id,
                    String classteacher_for, String joining_date, String address, String operation, String mStrImageUrl, String facebookId, String whatTeach, DownlodableCallback<StuTeaModel> callback);

    void addstudent(Context mActivity, String user_id, String roll_no, String name, String email, String mobile, String clas, String sec,
                    String admission_date, String residential_address, String fName, String fMobile, String mName, String mMobile, DownlodableCallback<StuTeaModel> callback);

    void teacherList(Context mActivity, DownlodableCallback<TeacherListModel> callback);

    void galleryList(Context mActivity, DownlodableCallback<GalleryModel> callback);

    void topperlist(Context mActivity, DownlodableCallback<TopperLisrModel> callback);

    void feedback(Context mActivity, String name, String mobile, String message, String rating, String date, DownlodableCallback<FeedBackModel> callback);

    void requestLeave(Context mActivity, String user_id, String from_date, String to_date, String teacher_id, String description, DownlodableCallback<CommonResponse> callback);

    void requestedLeaveList(Context mActivity, String login_as, String user_id, DownlodableCallback<RequestedLeaveModel> callback);

    void updateRequestedLeave(Context mActivity, String status, String user_id, String teacherId, String teacherComment, DownlodableCallback<CommonResponse> callback);

    void getStudentsListByClassWise(Context mActivity, String clas, String sec, DownlodableCallback<StudentListByClassModel> callback);

    void getClassWiseFeeDetailsList(Context mActivity, String clas, String session, String operation, DownlodableCallback<FeeDetailsModel> callback);

    void getStudentsAttendance(Context mActivity, String className, String sec, String studentId, String date, DownlodableCallback<StudentListByClassModel> callback);

    void attendance(Context mActivity, InsertAttendanceModel insertAttendanceModel, DownlodableCallback<CommonResponse> callback);

    void deleteStudent(Context mActivity, String studentRegId, DownlodableCallback<CommonResponse> callback);

    void getEventsOrNoticeList(Context mActivity, boolean isToGetEventsList, DownlodableCallback<EventsAndNoticeLisrModel> callback);

    void quizSubCat(String catId, String class_id, DownlodableCallback<SubCategoryModel> callback);

    void quizQuestion(String subCatId, String class_id, DownlodableCallback<QuesAnsModel> callback);

    void quizCat(String class_id, DownlodableCallback<CategoryModel> callback);
}
