
package bd.gov.fenipaurashava.modelCitizenServiceCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CurrentAmount__1 {

    @SerializedName("today")
    @Expose
    private Integer today;
    @SerializedName("total")
    @Expose
    private String total;

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
