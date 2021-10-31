
package bd.gov.fenipaurashava.modelCitizenServiceCount;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DueAmount {

    @SerializedName("today")
    @Expose
    private String today;
    @SerializedName("total")
    @Expose
    private String total;

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
