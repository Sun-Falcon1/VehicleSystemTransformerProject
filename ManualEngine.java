package f1;

public class ManualEngine extends Engine {

    private final double fuelBurnRate = 0.1; // liters per km

    public ManualEngine(double fuelLevel, int horsepower) {
        super(fuelLevel, 0, horsepower);
    }

    @Override
    public String getEngineType() {
        return "Manual (Combustion)";
    }

    @Override
    public void consumeResource(double distance) throws InsufficientResourceException {
        double needed = distance * fuelBurnRate;
        if (needed > getFuelLevel()) {
            throw new InsufficientResourceException(
                    "Not enough fuel to travel " + distance + " km. Fuel remaining: "
                            + String.format("%.2f", getFuelLevel()) + " L");
        }
        setFuelLevel(getFuelLevel() - needed);
    }
}