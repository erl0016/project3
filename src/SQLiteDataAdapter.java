
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAdapter {

    Connection conn = null;

    public int connect(String dbfile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }

    @Override
    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = null;

        try {
            String sql = "SELECT product_id, product_name, product_price, product_quantity FROM tbl_product WHERE product_id = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                product = new ProductModel();
                product.setProdId(rs.getInt("product_id"));
                product.setProdName(rs.getString("product_name"));
                product.setProdPrice(rs.getDouble("product_price"));
                product.setProdQuantity(rs.getInt("product_quantity"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public int saveProduct(ProductModel product) {
        try {
            Statement stmt = conn.createStatement();
            ProductModel p = loadProduct(product.getProdId()); // check if this product exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM tbl_product WHERE product_id = " + product.getProdId());
            }
            String sql = "INSERT INTO tbl_product(product_id, product_name, product_price, product_quantity) VALUES " + product;
            System.out.println(sql);
            //Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_DUPLICATE_ERROR;
        }
        return PRODUCT_SAVED_OK;
    }

    public CustomerModel loadCustomer(int id) {
        CustomerModel customerModel = null;

        try {
            String sql = "select * from tbl_customer where customer_id = " + id;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                customerModel = new CustomerModel();
                customerModel.setId(resultSet.getInt("customer_id"));
                customerModel.setName(resultSet.getString("customer_name"));
                customerModel.setAddress(resultSet.getString("customer_address"));
                customerModel.setPhoneNumber(resultSet.getInt("customer_phone"));
                customerModel.setPaymentInfo(resultSet.getString("customer_payment_info"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customerModel;
    }

    @Override
    public int saveCustomer(CustomerModel customerModel) {
        try {
            String sql = "insert into tbl_customer(customer_id, customer_name, customer_address, customer_phone, customer_payment_info) values "
                    + customerModel;
            System.out.println(sql);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }
        return PURCHASE_SAVED_OK;
    }

    @Override
    public PurchaseModel loadPurchase(int id) {
        PurchaseModel purchaseModel = null;

        try {
            String sql = "select * from tbl_purchase where purchase_id = " + id;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                purchaseModel = new PurchaseModel();
                purchaseModel.mPurchaseID = Integer.parseInt(resultSet.getString("purchase_id"));
                purchaseModel.mCustomerID = Integer.parseInt(resultSet.getString("customer_id"));
                purchaseModel.mProductID = Integer.parseInt(resultSet.getString("product_id"));
                purchaseModel.mPrice = Double.parseDouble(resultSet.getString("purchase_product_price"));
                purchaseModel.mQuantity = Double.parseDouble(resultSet.getString("purchase_quantity"));
                purchaseModel.mCost = Double.parseDouble(resultSet.getString("purchase_cost"));
                purchaseModel.mTax = Double.parseDouble(resultSet.getString("purchase_tax"));
                purchaseModel.mTotal = Double.parseDouble(resultSet.getString("purchase_total"));
                purchaseModel.mDate = resultSet.getString("purchase_date");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return purchaseModel;
    }

    @Override
    public int savePurchase(PurchaseModel purchaseModel) {
        try {
            String sql = "insert into tbl_purchase(" +
                    "purchase_id, " +
                    "customer_id, " +
                    "product_id, " +
                    "purchase_product_price, " +
                    "purchase_quantity, " +
                    "purchase_cost, " +
                    "purchase_tax, " +
                    "purchase_total, " +
                    "purchase_date) " +
                    "values "
                    + purchaseModel;
            System.out.println(sql);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }
        return PURCHASE_SAVED_OK;
    }
}
