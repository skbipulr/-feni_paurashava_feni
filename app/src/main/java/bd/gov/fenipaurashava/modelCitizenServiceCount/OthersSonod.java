
package bd.gov.fenipaurashava.modelCitizenServiceCount;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OthersSonod {

    @SerializedName("current_amount")
    @Expose
    private CurrentAmount__2 currentAmount;
    @SerializedName("certificate")
    @Expose
    private Certificate__1 certificate;

    public CurrentAmount__2 getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(CurrentAmount__2 currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Certificate__1 getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate__1 certificate) {
        this.certificate = certificate;
    }

}
