
package bd.gov.fenipaurashava.modelCitizenServiceCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("trade")
    @Expose
    private Trade trade;
    @SerializedName("market")
    @Expose
    private Market market;
    @SerializedName("others_sonod")
    @Expose
    private OthersSonod othersSonod;

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public OthersSonod getOthersSonod() {
        return othersSonod;
    }

    public void setOthersSonod(OthersSonod othersSonod) {
        this.othersSonod = othersSonod;
    }

}
