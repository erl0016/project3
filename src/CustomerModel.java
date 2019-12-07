public class CustomerModel {

    private int Id;
    private long PhoneNumber;
    private String Name, Address, PaymentInfo;

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

    public long getPhoneNumber() {
        return PhoneNumber;
    }
    public void setPhoneNumber(long phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }

    public String getPaymentInfo() {
        return PaymentInfo;
    }
    public void setPaymentInfo(String paymentInfo) {
        PaymentInfo = paymentInfo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(Id).append(",");
        sb.append("\"").append(Name).append("\"").append(",");
        sb.append("\"").append(Address).append("\"").append(",");
        sb.append(PhoneNumber).append(",");
        sb.append("\"").append(PaymentInfo).append("\"").append(")");
        return sb.toString();
    }

}
