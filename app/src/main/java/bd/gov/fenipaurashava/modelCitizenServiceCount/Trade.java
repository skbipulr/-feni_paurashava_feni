
package bd.gov.fenipaurashava.modelCitizenServiceCount;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Trade {

    @SerializedName("current_amount")
    @Expose
    private CurrentAmount currentAmount;
    @SerializedName("due_amount")
    @Expose
    private DueAmount dueAmount;
    @SerializedName("certificate")
    @Expose
    private Certificate certificate;

    public CurrentAmount getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(CurrentAmount currentAmount) {
        this.currentAmount = currentAmount;
    }

    public DueAmount getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(DueAmount dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

}
