public interface Car extends Runnable {
    void askParkingForPlace();
    void waitParkingPlace();
    void stayAtParkingPlace();
    int getId();
}
