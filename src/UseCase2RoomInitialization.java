abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    abstract void display();
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 2000);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

public class UseCase2RoomInitialization {

    static int singleAvailable = 5;
    static int doubleAvailable = 3;
    static int suiteAvailable = 2;

    public static void main(String[] args) {

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        r1.display();
        System.out.println("Available: " + singleAvailable);

        r2.display();
        System.out.println("Available: " + doubleAvailable);

        r3.display();
        System.out.println("Available: " + suiteAvailable);
    }
}