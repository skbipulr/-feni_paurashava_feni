
package bd.gov.fenipaurashava.modelForEmployeeGET;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EmployeeResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("employee_data")
    @Expose
    private List<Datum> employeeData = null;
    @SerializedName("designations")
    @Expose
    private List<Designation> designations = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(List<Datum> employeeData) {
        this.employeeData = employeeData;
    }

    public List<Designation> getDesignations() {
        return designations;
    }

    public void setDesignations(List<Designation> designations) {
        this.designations = designations;
    }

}
