import lombok.val;

public class Launcher {
    public static void main(String[] args) {
        val parking = new Parking();
        parking.init();
        for (int i = 0;i < 7; i++) {
            new Thread(new DefaultCar(i, false, parking)).start();
        }
    }
}
