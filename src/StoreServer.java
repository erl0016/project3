import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class StoreServer {
    static String dbfile = StoreManager.DB_FILE;

    public static void main(String[] args) {

        int port = 1000;

        if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            dbfile = args[1];
        }

        try {
            SQLiteDataAdapter adapter = new SQLiteDataAdapter();
            Gson gson = new Gson();
            adapter.connect(dbfile);

            ServerSocket server = new ServerSocket(port);

            System.out.println("Server is listening at port = " + port);

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                MessageModel msg = gson.fromJson(in.nextLine(), MessageModel.class);

                switch (msg.code) {
                    case MessageModel.GET_PRODUCT: {
                        System.out.println("GET product with id = " + msg.data);
                        ProductModel p = adapter.loadProduct(Integer.parseInt(msg.data));
                        if (p == null) {
                            msg.code = MessageModel.OPERATION_FAILED;
                        }
                        else {
                            msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                            msg.data = gson.toJson(p);
                        }
                        out.println(gson.toJson(msg));
                        break;
                    }
                    case MessageModel.PUT_PRODUCT: {
                        ProductModel p = gson.fromJson(msg.data, ProductModel.class);
                        System.out.println("PUT command with Product = " + p);
                        int res = adapter.saveProduct(p);
                        if (res == IDataAdapter.PRODUCT_SAVED_OK) {
                            msg.code = MessageModel.OPERATION_OK;
                        }
                        else {
                            msg.code = MessageModel.OPERATION_FAILED;
                        }
                        out.println(gson.toJson(msg));
                        break;
                    }
                    case MessageModel.GET_CUSTOMER: {
                        System.out.println("GET customer with id = " + msg.data);
                        CustomerModel customerModel = adapter.loadCustomer(Integer.parseInt(msg.data));
                        if (customerModel == null) {
                            msg.code = MessageModel.OPERATION_FAILED;
                        }
                        else {
                            msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                            msg.data = gson.toJson(customerModel);
                        }
                        out.println(gson.toJson(msg));
                        break;
                    }
                    case MessageModel.PUT_CUSTOMER: {
                        CustomerModel customerModel = gson.fromJson(msg.data, CustomerModel.class);
                        System.out.println("PUT command with Customer = " + customerModel);
                        int res = adapter.saveCustomer(customerModel);
                        if (res == IDataAdapter.CUSTOMER_SAVED_OK) {
                            msg.code = MessageModel.OPERATION_OK;
                        }
                        else {
                            msg.code = MessageModel.OPERATION_FAILED;
                        }
                        out.println(gson.toJson(msg));
                        break;
                    }
                    case MessageModel.GET_PURCHASE: {
                        System.out.println("GET purchase with id = " + msg.data);
                        PurchaseModel purchaseModel = adapter.loadPurchase(Integer.parseInt(msg.data));
                        if (purchaseModel == null) {
                            msg.code = MessageModel.OPERATION_FAILED;
                        }
                        else {
                            msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                            msg.data = gson.toJson(purchaseModel);
                        }
                        out.println(gson.toJson(msg));
                        break;
                    }
                    case MessageModel.PUT_PURCHASE: {
                        PurchaseModel purchaseModel = gson.fromJson(msg.data, PurchaseModel.class);
                        System.out.println("PUT command with Purchase = " + purchaseModel);
                        int res = adapter.savePurchase(purchaseModel);
                        if (res == IDataAdapter.PURCHASE_SAVED_OK) {
                            msg.code = MessageModel.OPERATION_OK;
                        }
                        else {
                            msg.code = MessageModel.OPERATION_FAILED;
                        }
                        out.println(gson.toJson(msg));
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}