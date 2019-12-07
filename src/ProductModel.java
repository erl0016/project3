public class ProductModel {
    private int prodId, prodQuantity;
    private String prodName, prodVendor, prodDescription;
    private double prodPrice, prodTax, prodTotal;

    int getProdId() {
        return prodId;
    }

    String getProdName() {
        return prodName;
    }

    double getProdPrice() {
        return prodPrice;
    }

    int getProdQuantity() {return prodQuantity;}

    void setProdId(int id) {
        prodId = id;
    }

    void setProdName(String name) {
        prodName = name;
    }

    void setProdPrice(double price) {
        prodPrice = price;
    }

    void setProdQuantity(int quantity) {
        prodQuantity = quantity;
    }

    void setProdTax(double tax) {prodTax = tax;}

    void setProdTotal(double total) {prodTotal = total;}

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(prodId).append(",");
        sb.append("\"").append(prodName).append("\"").append(",");
        sb.append(prodPrice).append(",");
        sb.append(prodQuantity).append(")");
        return sb.toString();
    }
}