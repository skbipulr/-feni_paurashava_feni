
package bd.gov.fenipaurashava.modelForEmployeeGET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {


    private int image;

    @SerializedName("username")
    @Expose
    private Object username;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("employee_id")
    @Expose
    private Object employeeId;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("bcs_batch")
    @Expose
    private String bcsBatch;
    @SerializedName("joining_date")
    @Expose
    private Object joiningDate;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("fb_id")
    @Expose
    private Object fbId;
    @SerializedName("tweeter_id")
    @Expose
    private Object tweeterId;
    @SerializedName("order_no")
    @Expose
    private Integer orderNo;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("created_by_ip")
    @Expose
    private String createdByIp;
    @SerializedName("updated_by_ip")
    @Expose
    private Object updatedByIp;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Object getUsername() {
        return username;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Datum(String name, String designation, String mobileNo, String email, int image) {
        this.name = name;
        this.designation = designation;
        this.mobileNo = mobileNo;
        this.email = email;
        this.image = image;
    }

    public Datum(String name) {
        this.name = name;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Object employeeId) {
        this.employeeId = employeeId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBcsBatch() {
        return bcsBatch;
    }

    public void setBcsBatch(String bcsBatch) {
        this.bcsBatch = bcsBatch;
    }

    public Object getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Object joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Object getFbId() {
        return fbId;
    }

    public void setFbId(Object fbId) {
        this.fbId = fbId;
    }

    public Object getTweeterId() {
        return tweeterId;
    }

    public void setTweeterId(Object tweeterId) {
        this.tweeterId = tweeterId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedByIp() {
        return createdByIp;
    }

    public void setCreatedByIp(String createdByIp) {
        this.createdByIp = createdByIp;
    }

    public Object getUpdatedByIp() {
        return updatedByIp;
    }

    public void setUpdatedByIp(Object updatedByIp) {
        this.updatedByIp = updatedByIp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }
}
