import lombok.val;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Parking {
    private final static int AMOUNT_OF_PLACES = 4;

    private List<ParkingPlace> parkingPlaces = new CopyOnWriteArrayList<>();
    private Queue<Car> waitingCars = new ConcurrentLinkedQueue<>();

    public void init() {
        for(int i = 0; i < AMOUNT_OF_PLACES; i++) {
            val parkingPlace = new ParkingPlace(i, null);
            parkingPlaces.add(parkingPlace);
        }
    }

    public synchronized void releasePlace(final Car car) {
        val place = parkingPlaces.stream()
            .filter(p -> car.equals(p.getCurrentCar()))
            .findFirst();
        place.ifPresent(parkingPlace -> {
            parkingPlace.releasePlace();
            val logMessage = String.format(
                "Car with id:%d leaved place with number:%d",
                car.getId(),
                parkingPlace.getNumber());
            System.out.println(logMessage);
            processWaitingQueue();
        });
    }

    public synchronized boolean findPlaceForCar(final Car car) {
        return tryToFindFreePlaceForCar(car);
    }

    public synchronized void removeFromWaitingQueue(final Car car) {
        waitingCars.remove(car);
    }

    private void processWaitingQueue() {
        val waitingCar = waitingCars.poll();
        if (waitingCar != null) {
            findPlaceForCar(waitingCar);
        }
    }

    private boolean tryToFindFreePlaceForCar(final Car car) {
        for (ParkingPlace parkingPlace : parkingPlaces) {
            if (parkingPlace.isFree()) {
                parkingPlace.takePlace(car);
                val logMessage = String.format(
                    "Car with id:%d is on place with number:%d",
                    car.getId(),
                    parkingPlace.getNumber());
                System.out.println(logMessage);
                return true;
            }
        }
        System.out.println(String.format("Car with id:%d is waiting for place", car.getId()));
        waitingCars.add(car);
        return false;
    }

}
