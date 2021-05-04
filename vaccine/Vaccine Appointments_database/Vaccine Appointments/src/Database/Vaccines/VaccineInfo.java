package Database.Vaccines;

import java.math.BigDecimal;

public class VaccineInfo {

    private String type;
    private BigDecimal price;

    public VaccineInfo(String type, BigDecimal price){
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
