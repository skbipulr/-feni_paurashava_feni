
package bd.gov.fenipaurashava.modelCitizenServiceCount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Market {

    @SerializedName("current_amount")
    @Expose
    private CurrentAmount__1 currentAmount;
    @SerializedName("due_amount")
    @Expose
    private DueAmount__1 dueAmount;

    public CurrentAmount__1 getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(CurrentAmount__1 currentAmount) {
        this.currentAmount = currentAmount;
    }

    public DueAmount__1 getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(DueAmount__1 dueAmount) {
        this.dueAmount = dueAmount;
    }

}
