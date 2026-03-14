import java.util.HashMap;

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

public class UseCase4RoomInitialization {

    public static void main(String[] args) {

        HashMap<String, Integer> inventory = new HashMap<>();

        inventory.put("Single", 5);
        inventory.put("Double", 0);
        inventory.put("Suite", 2);

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        System.out.println("Available Rooms:\n");

        if (inventory.get("Single") > 0) {
            r1.display();
            System.out.println("Available: " + inventory.get("Single"));
            System.out.println();
        }

        if (inventory.get("Double") > 0) {
            r2.display();
            System.out.println("Available: " + inventory.get("Double"));
            System.out.println();
        }

        if (inventory.get("Suite") > 0) {
            r3.display();
            System.out.println("Available: " + inventory.get("Suite"));
            System.out.println();
        }
    }
}