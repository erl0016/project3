import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ManageCustomerUI {
    public JFrame view;

    public JButton btnLoad = new JButton("Load Customer");
    public JButton btnSave = new JButton("Save Customer");

    private JTextField txtCustomerID = new JTextField(20);
    private JTextField txtCustomerName = new JTextField(20);
    private JTextField txtCustomerAddress = new JTextField(20);
    private JTextField txtCustomerPhone = new JTextField(20);
    private JTextField txtCustomerPaymentInfo = new JTextField(20);

    public ManageCustomerUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Customer Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Customer ID "));
        line1.add(txtCustomerID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name "));
        line2.add(txtCustomerName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address "));
        line3.add(txtCustomerAddress);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone Number "));
        line4.add(txtCustomerPhone);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        line5.add(new JLabel("Payment Info "));
        line5.add(txtCustomerPaymentInfo);
        view.getContentPane().add(line5);

        btnLoad.addActionListener(new LoadButtonListener());
        btnSave.addActionListener(new SaveButtonListener());
    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListener implements ActionListener {



        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //StoreManager.checkServer();
            CustomerModel customerModel = new CustomerModel();
            Gson gson = new Gson();
            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer ID cannot be null!");
                return;
            }

            try {
                customerModel.setId(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer ID is invalid!");
                return;
            }

            // do client/server

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_CUSTOMER;
                msg.data = String.valueOf(customerModel.getId());
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Customer does NOT exists!");
                }
                else {
                    customerModel = gson.fromJson(msg.data, CustomerModel.class);
                    txtCustomerName.setText(customerModel.getName());
                    txtCustomerAddress.setText(customerModel.getAddress());
                    txtCustomerPhone.setText(Long.toString(customerModel.getPhoneNumber()));
                    txtCustomerPaymentInfo.setText(customerModel.getPaymentInfo());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //StoreManager.checkServer();
            CustomerModel customerModel = new CustomerModel();
            Gson gson = new Gson();
            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer ID cannot be null!");
                return;
            }

            try {
                customerModel.setId(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer ID is invalid!");
                return;
            }

            String name = txtCustomerName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
                return;
            }
            customerModel.setName(name);

            String addressText = txtCustomerAddress.getText();
            try {
                customerModel.setAddress(addressText);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Address is invalid!");
                return;
            }

            String phoneNumber = txtCustomerPhone.getText();
            try {
                customerModel.setPhoneNumber(Long.parseLong(phoneNumber));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Phone number is invalid!");
                return;
            }

            String paymentInfo = txtCustomerPaymentInfo.getText();
            try {
                customerModel.setPaymentInfo(paymentInfo);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Payment info is invalid!");
                return;
            }

            // all product infor is ready! Send to Server!


            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_CUSTOMER;
                msg.data = gson.toJson(customerModel);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class); // receive from Server

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Customer is NOT saved successfully!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Customer is SAVED successfully!");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
