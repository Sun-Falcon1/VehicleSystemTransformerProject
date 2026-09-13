package f1;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VehicleSimulationApp extends Application {

    private Vehicle vehicle;
    private TripLogger logger = new TripLogger();

    private TextArea statusArea = new TextArea();
    private TextField distanceField = new TextField();
    private Label fuelLabel = new Label();
    private Label batteryLabel = new Label();
    private ProgressBar fuelBar = new ProgressBar(0);
    private ProgressBar batteryBar = new ProgressBar(0);

    @Override
    public void start(Stage stage) {

        HybridEngine engine = new HybridEngine(50, 100, 200); // 50 L fuel, 100% battery
        vehicle = new Vehicle("Tesla", "Hybrid-X", engine);

        Label title = new Label("Vehicle Transformation Simulation");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        distanceField.setPromptText("Enter distance in km");

        Button driveBtn = new Button("Drive");
        Button switchBtn = new Button("Switch Manual/Electric");
        Button chargeBtn = new Button("Charge Battery (+20%)");
        Button logBtn = new Button("View Trip Log");
        Button clearLogBtn = new Button("Clear Log");

        driveBtn.setOnAction(e -> handleDrive());
        switchBtn.setOnAction(e -> handleSwitchMode());
        chargeBtn.setOnAction(e -> handleCharge());
        logBtn.setOnAction(e -> showLog());
        clearLogBtn.setOnAction(e -> handleClearLog());

        HBox buttonRow1 = new HBox(10, driveBtn, switchBtn, chargeBtn);
        HBox buttonRow2 = new HBox(10, logBtn, clearLogBtn);

        fuelBar.setPrefWidth(250);
        batteryBar.setPrefWidth(250);

        VBox statusBox = new VBox(5,
                new HBox(10, new Label("Fuel:"), fuelBar, fuelLabel),
                new HBox(10, new Label("Battery:"), batteryBar, batteryLabel)
        );

        statusArea.setEditable(false);
        statusArea.setPrefHeight(220);

        VBox root = new VBox(12,
                title,
                distanceField,
                buttonRow1,
                buttonRow2,
                statusBox,
                statusArea
        );
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);

        stage.setScene(new Scene(root, 480, 520));
        stage.setTitle("Vehicle Transformation Simulator");
        stage.show();

        refreshStatus();
    }

    private void handleDrive() {
        try {
            double distance = Double.parseDouble(distanceField.getText());
            vehicle.drive(distance);
            logger.logEvent("Drove " + distance + " km. " + vehicle.displayVehicleInfo());
            statusArea.appendText("Drove " + distance + " km successfully.\n");
        } catch (NumberFormatException ex) {
            statusArea.appendText("Please enter a valid number for distance.\n");
        } catch (InsufficientResourceException ex) {
            statusArea.appendText("ERROR: " + ex.getMessage() + "\n");
            logger.logEvent("FAILED drive attempt: " + ex.getMessage());
        }
        refreshStatus();
    }

    private void handleSwitchMode() {
        Engine engine = vehicle.getEngine();
        if (engine instanceof HybridEngine hybrid) {
            String newMode = hybrid.getMode().equals("MANUAL") ? "ELECTRIC" : "MANUAL";
            hybrid.switchMode(newMode);
            logger.logEvent("Engine transformed to " + newMode + " mode.");
            statusArea.appendText("Switched engine mode to " + newMode + "\n");
        } else {
            statusArea.appendText("This engine does not support mode switching.\n");
        }
        refreshStatus();
    }

    private void handleCharge() {
        Engine engine = vehicle.getEngine();
        if (engine instanceof Rechargeable rechargeable) {
            rechargeable.charge(20);
            logger.logEvent("Charged battery by 20%.");
            statusArea.appendText("Charged battery by 20%.\n");
        } else {
            statusArea.appendText("This engine cannot be charged.\n");
        }
        refreshStatus();
    }

    private void showLog() {
        statusArea.appendText("--- TRIP LOG ---\n");
        for (String line : logger.readLog()) {
            statusArea.appendText(line + "\n");
        }
    }

    private void handleClearLog() {
        logger.clearLog();
        statusArea.appendText("Trip log cleared.\n");
    }

    private void refreshStatus() {
        Engine engine = vehicle.getEngine();
        double fuel = engine.getFuelLevel();
        double battery = engine.getBatteryLevel();

        fuelLabel.setText(String.format("%.1f L", fuel));
        batteryLabel.setText(String.format("%.1f %%", battery));

        fuelBar.setProgress(Math.max(0, Math.min(fuel / 50.0, 1.0)));
        batteryBar.setProgress(Math.max(0, Math.min(battery / 100.0, 1.0)));

        statusArea.appendText(vehicle.displayVehicleInfo() + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}