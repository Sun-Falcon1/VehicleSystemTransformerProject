package f1;


public class Vehicle {

    private String brand;
    private String model;
    private double totalDistanceTraveled;
    private Engine engine;

    public Vehicle(String brand, String model, Engine engine) {
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.totalDistanceTraveled = 0;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getTotalDistanceTraveled() {
        return totalDistanceTraveled;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    // Polymorphism: doesn't know or care which Engine subclass this is
    public void drive(double distance) throws InsufficientResourceException {
        engine.consumeResource(distance);
        totalDistanceTraveled += distance;
    }

    public String displayVehicleInfo() {
        return brand + " " + model
                + " | Engine: " + engine.getEngineType()
                + " | Fuel: " + String.format("%.2f", engine.getFuelLevel()) + " L"
                + " | Battery: " + String.format("%.2f", engine.getBatteryLevel()) + "%"
                + " | Total Distance: " + String.format("%.2f", totalDistanceTraveled) + " km";
    }
}