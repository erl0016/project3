/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerUI {

    private JFrame view;
    private JTextField txtCustID = new JTextField(20);
    private JTextField txtCustName = new JTextField(20);
    private JTextField txtCustAddress = new JTextField(20);
    private JTextField txtCustPhone = new JTextField(20);
    private JTextField txtCustPaymentInfo = new JTextField(20);

    public AddCustomerUI() {
        this.view = new JFrame();

        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Id "));
        line1.add(txtCustID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name "));
        line2.add(txtCustName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address "));
        line3.add(txtCustAddress);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone Number "));
        line4.add(txtCustPhone);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        line5.add(new JLabel("Payment Info "));
        line5.add(txtCustPaymentInfo);
        view.getContentPane().add(line5);

        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add");
        panelButtons.add(btnAdd);
        JButton btnCancel = new JButton("Cancel");
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddCustomerUI.AddButtonListener());
        btnCancel.addActionListener(actionEvent -> view.dispose());
    }

    void run() {
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customerModel = new CustomerModel();

            Object source = actionEvent.getSource();
            String sourceClass = source.getClass().toString();
            if (sourceClass.contains("JButton")) {
                JButton jButton = (JButton)source;
                switch (jButton.getText()) {
                    case "Cancel": {
                        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?",
                                "Confirm Cancel", 0, 1);
                        if (response == 0) {
                            //confirm cancellation request
                            break;
                        }
                        break;
                    }
                    case "Add": {

                        String id = txtCustID.getText();
                        if (id.length() == 0) {
                            JOptionPane.showMessageDialog(null, "Id cannot be null!");
                            return;
                        }

                        try {
                            customerModel.setId(Integer.parseInt(id));
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Id is invalid!");
                            return;
                        }

                        String name = txtCustName.getText();
                        if (name.length() == 0) {
                            JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
                            return;
                        }
                        customerModel.setName(name);

                        String addressText = txtCustAddress.getText();
                        try {
                            customerModel.setAddress(addressText);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Address is invalid!");
                            return;
                        }

                        String phone = txtCustPhone.getText();
                        try {
                            customerModel.setPhoneNumber(Long.parseLong(phone));
                        }
                        catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Phone Number is invalid!");
                            return;
                        }

                        String paymentInfo = txtCustPaymentInfo.getText();
                        try {
                            customerModel.setPaymentInfo(paymentInfo);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Payment Info is invalid!");
                            return;
                        }

                        switch (StoreManager.getInstance().getDataAdapter().saveCustomer(customerModel)) {
                            case SQLiteDataAdapter.ACTION_SAVED_OK:
                                JOptionPane.showMessageDialog(null, "Customer added successfully!" + customerModel);
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Customer NOT added successfully! Duplicate customer ID!");
                                break;
                        }
                    }
                    default: {
                        System.out.println("Something occurred during panel sub-operation.");
                        break;
                    }
                }
            }


        }
    }
}
*/
