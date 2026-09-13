package f1;

public abstract class Engine {

    private double fuelLevel;
    private double batteryLevel;
    private int horsepower;
    private boolean isRunning;

    public Engine(double fuelLevel, double batteryLevel, int horsepower) {
        this.fuelLevel = fuelLevel;
        this.batteryLevel = batteryLevel;
        this.horsepower = horsepower;
        this.isRunning = false;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    // Abstract methods -- every subclass must implement these differently
    public abstract String getEngineType();

    public abstract void consumeResource(double distance) throws InsufficientResourceException;
}