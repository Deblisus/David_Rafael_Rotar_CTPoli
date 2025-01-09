package Panels;

import Models.User;
import Repository.UserRepository;
import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

import javax.swing.*;

public class Login {
    private JPanel loginPanel;
    public JButton loginButton;
    public JButton registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    UserRepository userRepository;

    BcryptFunction bcrypt;

    public Login() {
        userRepository = new UserRepository();
        bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);
    }

    public boolean tryLogin() {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();

        if(username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and Password are Required");
            return false;
        }
        User tryUser = userRepository.findUser(username);

        if(tryUser == null) {
            errorLabel.setText("User not found");
            clearFields();
            return false;
        } else {
            boolean verified = Password.check(password, tryUser.getPassword()).addPepper("peppered").with(bcrypt);
            if (verified) {
                clearFields();
                errorLabel.setText("");
                return true;
            } else {
                errorLabel.setText("Wrong Password");
                return false;
            }
        }
    }

    public boolean tryRegister() {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        Hash hash = Password.hash(password).addPepper("peppered").with(bcrypt);

        if(username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and Password are Required");
            return false;
        }
        User newUser = new User(username, hash.getResult());
        User tryUser = userRepository.createUser(newUser);
        if(tryUser == null) {
            errorLabel.setText("User creation Failed");
            return false;
        } else {
            clearFields();
            errorLabel.setText("");
            return true;
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public JPanel getPanel() {
        return loginPanel;
    }
}


