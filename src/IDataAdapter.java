
public interface IDataAdapter {

    int CONNECTION_OPEN_OK = 100;
    int CONNECTION_OPEN_FAILED = 101;

    int CONNECTION_CLOSE_OK = 200;
    int CONNECTION_CLOSE_FAILED = 201;

    int PRODUCT_SAVED_OK = 0;
    int PRODUCT_DUPLICATE_ERROR = 1;

    int CUSTOMER_SAVED_OK = 300;
    int CUSTOMER_DUPLICATE_ERROR = 3001;

    int PURCHASE_SAVED_OK = 500;
    int PURCHASE_DUPLICATE_ERROR = 501;

    int connect(String dbfile);
    int disconnect();

    ProductModel loadProduct(int id);
    int saveProduct(ProductModel model);

    CustomerModel loadCustomer(int id);
    int saveCustomer(CustomerModel customerModel);

    PurchaseModel loadPurchase(int id);
    int savePurchase(PurchaseModel model);
}
