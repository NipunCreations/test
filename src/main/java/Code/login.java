import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {
    
    public Login() {
        initComponents();
    }

    // Database connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/";
    private final String DB_USERNAME = "dedunu_festival";
    private final String DB_PASSWORD = "nipun123";

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String username = username.getText();
        String password = jTextField2.getText();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
        } else {
            try (Connection conn = connect()) {
                String sql = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    switch (role) {
                        case "Admin":
                            JOptionPane.showMessageDialog(this, "Welcome Admin");
                            // Redirect to admin page
                            break;
                        case "Accountant":
                            JOptionPane.showMessageDialog(this, "Welcome Accountant");
                            // Redirect to accounts page
                            break;
                        case "HR Manager":
                            JOptionPane.showMessageDialog(this, "Welcome HR Manager");
                            // Redirect to HR management page
                            break;
                        case "Employee":
                            JOptionPane.showMessageDialog(this, "Welcome Employee");
                            // Redirect to stock management page
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Unknown role");
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }                                        

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}
