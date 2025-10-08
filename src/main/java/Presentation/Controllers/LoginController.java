package Presentation.Controllers;

import Domain.Dtos.auth.UserResponseDto;
import Presentation.Observable;
import Presentation.Views.CarsView;
import Presentation.Views.LoginView;
import Presentation.Views.MainView;
import Services.AuthService;
import Services.CarService;
import Utilities.EventType;

import javax.swing.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class LoginController {

    private final LoginView loginView;
    private final AuthService authService;

    public LoginController(LoginView loginView, AuthService authService) {
        this.loginView = loginView;
        this.authService = authService;

        this.loginView.addLoginListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

        // Run login in a background thread
        SwingWorker<UserResponseDto, Void> worker = new SwingWorker<>() {
            @Override
            protected UserResponseDto doInBackground() throws Exception {
                loginView.showLoading();
                return authService.login(username, password);
            }

            @Override
            protected void done() {
                loginView.hideLoading();
                try {
                    UserResponseDto user = get();
                    if (user != null) {
                        openMainView();
                    } else {
                        JOptionPane.showMessageDialog(loginView, "Error when logging in...", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                }
            }
        };
        worker.execute();
    }

    private void openMainView() {
        MainView mainView = new MainView();

        CarsView carsView = new CarsView();
        CarService carService = new CarService("localhost", 7000);
        CarsController carsController = new CarsController(carsView, carService);
        carsController.addObserver(carsView);

        Dictionary<String, JPanel> tabs = new Hashtable<>();
        tabs.put("Cars", carsView.getContentPanel());

        mainView.AddTabs(tabs);
        mainView.setVisible(true);
    }
}