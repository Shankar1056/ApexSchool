package softgalli.gurukulshikshalay.model;

public class AdmissionListModel {
    String name;
    String phoneNo;
    String emailId;
    String className;
    String comment;

    public AdmissionListModel(String name, String phoneNo, String emailId, String className, String comment) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.className = className;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getClassName() {
        return className;
    }

    public String getComment() {
        return comment;
    }
}
