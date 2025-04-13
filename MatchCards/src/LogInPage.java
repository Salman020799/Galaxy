import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

class LoginSignupFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // In-memory user database
    static HashMap<String, String> userDatabase = new HashMap<>();

    public LoginSignupFrame() {
        setTitle("Welcome");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Pre-fill one user
        userDatabase.put("admin", "admin123");

        LoginPanel loginPanel = new LoginPanel(this);
        SignupPanel signupPanel = new SignupPanel(this);

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(signupPanel, "Signup");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}


class LoginPanel extends JPanel {
    public LoginPanel(LoginSignupFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(44, 62, 80));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        RoundedButton loginButton = new RoundedButton("Login");
        JButton switchButton = new JButton("Create an account");

        styleTextField(usernameField);
        styleTextField(passwordField);

        // Handle login event
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
        
                if (LoginSignupFrame.userDatabase.containsKey(username) &&
                    LoginSignupFrame.userDatabase.get(username).equals(password)) {
        
                    JOptionPane.showMessageDialog(LoginPanel.this, "Login Successful!");
                    frame.dispose(); // Close login window
                    new MainMenu();  // Open your game menu
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this,
                        "Invalid username or password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        

        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showPanel("Signup");
            }
        });
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 10, 10, 10);
        add(title, gbc);
        gbc.gridy++;
        add(usernameField, gbc);
        gbc.gridy++;
        add(passwordField, gbc);
        gbc.gridy++;
        add(loginButton, gbc);
        gbc.gridy++;
        add(switchButton, gbc);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(200, 35));
    }
}


class SignupPanel extends JPanel {
    public SignupPanel(LoginSignupFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(52, 73, 94));
        
        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        RoundedButton signupButton = new RoundedButton("Sign Up");
        JButton switchButton = new JButton("Already have an account?");
        
        styleTextField(usernameField);
        styleTextField(passwordField);
        

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
        
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignupPanel.this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (LoginSignupFrame.userDatabase.containsKey(username)) {
                    JOptionPane.showMessageDialog(SignupPanel.this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    LoginSignupFrame.userDatabase.put(username, password);
                    JOptionPane.showMessageDialog(SignupPanel.this, "Sign Up Successful! You can now log in.");
                    frame.showPanel("Login");
                }
            }
        });
        
        
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showPanel("Login");
            }
        });
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 10, 10, 10);
        add(title, gbc);
        gbc.gridy++;
        add(usernameField, gbc);
        gbc.gridy++;
        add(passwordField, gbc);
        gbc.gridy++;
        add(signupButton, gbc);
        gbc.gridy++;
        add(switchButton, gbc);
    }
    
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(200, 35));
    }
}

class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setBackground(new Color(41, 128, 185));
        setBorderPainted(false);
        setPreferredSize(new Dimension(200, 40));
        setOpaque(true);
    }
}
