package com.example.salary_app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

public class SalaryCalculator extends Application {

    private TextField employeeNameField;
    private TextField baseSalaryField;
    private ComboBox<String> monthComboBox;
    private TextField calculatedSalaryField;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/salary_db";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Salary Calculator");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        employeeNameField = new TextField();
        grid.add(new Label("Employee Name:"), 0, 0);
        grid.add(employeeNameField, 1, 0);

        baseSalaryField = new TextField();
        grid.add(new Label("Base Salary:"), 0, 1);
        grid.add(baseSalaryField, 1, 1);

        monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        );
        grid.add(new Label("Month:"), 0, 2);
        grid.add(monthComboBox, 1, 2);

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> calculateSalary());
        grid.add(calculateButton, 1, 3);

        calculatedSalaryField = new TextField();
        calculatedSalaryField.setEditable(false);
        grid.add(new Label("Calculated Salary:"), 0, 4);
        grid.add(calculatedSalaryField, 1, 4);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateSalary() {
        String name = employeeNameField.getText();
        double baseSalary = Double.parseDouble(baseSalaryField.getText());
        String month = monthComboBox.getValue();

        // Simple calculation (you might want to add more complex logic)
        double calculatedSalary = baseSalary;

        // Save to database
        saveToDatabase(name, month, calculatedSalary);

        calculatedSalaryField.setText(String.format("%.2f", calculatedSalary));
    }

    private void saveToDatabase(String name, String month, double salary) {
        String sql = "INSERT INTO salaries (employee_name, month, salary) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, month);
            pstmt.setDouble(3, salary);

            pstmt.executeUpdate();
            System.out.println("Salary data saved to database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
