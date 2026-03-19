package ui;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Dashboard {

    public static DefaultTableModel model;
    public static Map<Integer, Integer> rowMap = new HashMap<>();

    private static int idCounter = 1;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        JFrame frame = new JFrame("💳 Payment Dashboard");
        frame.setSize(900, 550);
        frame.setLayout(new BorderLayout());

        // TABLE
        String[] columns = {"Payment ID", "Amount", "Status"};
        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.BOLD, 14));

        // COLOR RENDERER
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = (String) table.getValueAt(row, 2);

                if ("SUCCESS".equals(status)) {
                    c.setForeground(Color.GREEN);
                } else if ("FAILED".equals(status)) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.ORANGE);
                }

                return c;
            }
        });

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // PANEL
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel label = new JLabel("Enter Amount:");
        JTextField field = new JTextField(10);
        JButton button = new JButton("Create Payment");

        panel.add(label);
        panel.add(field);
        panel.add(button);

        frame.add(panel, BorderLayout.SOUTH);

        // BUTTON ACTION
        button.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(field.getText());
                int id = idCounter++;

                int rowIndex = model.getRowCount();
                model.addRow(new Object[]{id, amount, "PENDING"});
                rowMap.put(id, rowIndex);

                // FAILURE SIMULATION (20%)
                boolean fail = new Random().nextInt(5) == 0;

                if (!fail) {
                    sendToBackend(id, amount);
                } else {
                    model.setValueAt("FAILED", rowIndex, 2);
                }

                field.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input");
            }
        });

        // ✅ FIXED TIMER (NO ERROR)
        new javax.swing.Timer(2000, e -> refreshTable()).start();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // SEND TO BACKEND
    public static void sendToBackend(int id, double amount) {
        try {
            URL url = new URL("http://localhost:8080/payment");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = "{\"id\":" + id + ",\"amount\":" + amount + "}";

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            conn.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // REFRESH TABLE (MULTI ROW TRACKING)
    public static void refreshTable() {
        try {
            URL url = new URL("http://localhost:8080/payment");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = br.readLine();

            if (response == null) return;

            for (Integer id : rowMap.keySet()) {

                if (response.contains("\"id\":" + id)) {

                    int rowIndex = rowMap.get(id);

                    if (response.contains("\"id\":" + id) &&
                        response.contains("\"status\":\"SUCCESS\"")) {

                        model.setValueAt("SUCCESS", rowIndex, 2);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}