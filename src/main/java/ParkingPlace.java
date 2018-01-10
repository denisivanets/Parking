import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ParkingPlace {
    @Getter
    private int number;
    private Car currentCar;

    public boolean isFree() {
        return currentCar == null;
    }

    public synchronized Car getCurrentCar() {
        return currentCar;
    }

    public synchronized void takePlace(final Car car) {
        currentCar = car;
    }

    public synchronized void releasePlace() {
        currentCar = null;
    }

}
