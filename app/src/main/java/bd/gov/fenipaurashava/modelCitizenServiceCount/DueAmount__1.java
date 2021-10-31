
package bd.gov.fenipaurashava.modelCitizenServiceCount;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DueAmount__1 {

    @SerializedName("today")
    @Expose
    private Integer today;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
