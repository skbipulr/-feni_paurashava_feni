
package bd.gov.fenipaurashava.departmentUser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DepartmentListResponse {

    @SerializedName("data")
    @Expose
    private List<Department> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Department> getData() {
        return data;
    }

    public void setData(List<Department> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
