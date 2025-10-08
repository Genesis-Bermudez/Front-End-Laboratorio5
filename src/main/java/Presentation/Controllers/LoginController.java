package Presentation.Controllers;

import Domain.Dtos.auth.UserResponseDto;
import Presentation.IObserver;
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

public class LoginController extends Observable {

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
                loginView.showLoading(true);
                return authService.login(username, password);
            }

            @Override
            protected void done() {
                loginView.showLoading(false);
                try {
                    UserResponseDto user = get();
                    if (user != null) {
                        loginView.setVisible(false);
                        openMainView();
                        notifyObservers(EventType.UPDATED, user);
                    } else {
                        JOptionPane.showMessageDialog(loginView, "Error when logging in...", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(loginView, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void openMainView() {
        MainView mainView = new MainView();

        CarsView carsView = new CarsView(mainView);
        CarService carService = new CarService("localhost", 7000);
        CarsController carsController = new CarsController(carsView, carService);
        carsController.addObserver(carsView);

        Dictionary<String, JPanel> tabs = new Hashtable<>();
        tabs.put("Cars", carsView.getContentPanel());

        mainView.AddTabs(tabs);
        mainView.setVisible(true);
    }
}