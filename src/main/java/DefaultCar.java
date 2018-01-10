import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(of = "carId")
public class DefaultCar implements Car {
    private final static int WAITING_TIMEOUT = 10000;
    private final static int PARKING_TIME = 7000;

    @Getter
    private int carId;
    @Getter
    private boolean isWaiting;
    private Parking favoriteParking;
    
    @Override
    public void askParkingForPlace() {
        if (favoriteParking.findPlaceForCar(this)) {
            stayAtParkingPlace();
        } else {
            waitParkingPlace();
        }
    }

    @Override
    public void waitParkingPlace() {
        try {
            isWaiting = true;
            Thread.sleep(WAITING_TIMEOUT);
            isWaiting = false;
            favoriteParking.removeFromWaitingQueue(this);
        } catch (final InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void stayAtParkingPlace() {
        try {
            Thread.sleep(PARKING_TIME);
            favoriteParking.releasePlace(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        askParkingForPlace();
    }

    @Override
    public int getId() {
        return carId;
    }

}
