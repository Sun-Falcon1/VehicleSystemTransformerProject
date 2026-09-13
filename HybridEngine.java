package f1;

public class HybridEngine extends Engine implements Rechargeable {

    private String mode; // "MANUAL" or "ELECTRIC"
    private final double fuelBurnRate = 0.08;
    private final double powerDrawRate = 1.5;

    public HybridEngine(double fuelLevel, double batteryLevel, int horsepower) {
        super(fuelLevel, batteryLevel, horsepower);
        this.mode = "MANUAL";
    }

    public void switchMode(String newMode) {
        if (newMode.equalsIgnoreCase("MANUAL") || newMode.equalsIgnoreCase("ELECTRIC")) {
            this.mode = newMode.toUpperCase();
        }
    }

    public String getMode() {
        return mode;
    }

    @Override
    public String getEngineType() {
        return "Hybrid (currently in " + mode + " mode)";
    }

    @Override
    public void consumeResource(double distance) throws InsufficientResourceException {
        if (mode.equals("MANUAL")) {
            double needed = distance * fuelBurnRate;
            if (needed > getFuelLevel()) {
                throw new InsufficientResourceException(
                        "Hybrid (manual mode): not enough fuel. Fuel remaining: "
                                + String.format("%.2f", getFuelLevel()) + " L");
            }
            setFuelLevel(getFuelLevel() - needed);
        } else {
            double needed = distance * powerDrawRate;
            if (needed > getBatteryLevel()) {
                throw new InsufficientResourceException(
                        "Hybrid (electric mode): not enough battery. Battery remaining: "
                                + String.format("%.2f", getBatteryLevel()) + "%");
            }
            setBatteryLevel(getBatteryLevel() - needed);
        }
    }

    @Override
    public void charge(double amount) {
        setBatteryLevel(Math.min(getBatteryLevel() + amount, 100));
    }

    @Override
    public double getChargePercentage() {
        return getBatteryLevel();
    }
}