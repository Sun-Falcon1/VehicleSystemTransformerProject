package f1;

public class ElectricEngine extends Engine implements Rechargeable {

    private final double powerDrawRate = 2.0; // % battery per km

    public ElectricEngine(double batteryLevel, int horsepower) {
        super(0, batteryLevel, horsepower);
    }

    @Override
    public String getEngineType() {
        return "Electric";
    }

    @Override
    public void consumeResource(double distance) throws InsufficientResourceException {
        double needed = distance * powerDrawRate;
        if (needed > getBatteryLevel()) {
            throw new InsufficientResourceException(
                    "Not enough battery to travel " + distance + " km. Battery remaining: "
                            + String.format("%.2f", getBatteryLevel()) + "%");
        }
        setBatteryLevel(getBatteryLevel() - needed);
    }

    @Override
    public void charge(double amount) {
        double newLevel = getBatteryLevel() + amount;
        setBatteryLevel(Math.min(newLevel, 100));
    }

    @Override
    public double getChargePercentage() {
        return getBatteryLevel();
    }
}