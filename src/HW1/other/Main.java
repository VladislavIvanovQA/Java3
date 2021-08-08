package HW1.other;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Box<Apple> boxApple = new Box<>();
        boxApple.addFruits(Arrays.asList(new Apple(), new Apple(), new Apple()));

        Box<Orange> boxOrange = new Box<>();
        boxOrange.addFruits(Arrays.asList(new Orange(), new Orange()));

        System.out.println("Apple box wight: " + boxApple.getWeightBox());
        System.out.println("Orange box wight: " + boxOrange.getWeightBox());
        System.out.println("Compare box Apple and Orange: " + boxApple.compare(boxOrange));

        boxOrange.addFruit(new Orange());

        System.out.println("Apple box wight: " + boxApple.getWeightBox());
        System.out.println("Orange box wight: " + boxOrange.getWeightBox());
        System.out.println("Compare box Apple and Orange: " + boxApple.compare(boxOrange));

        Box<Apple> boxAppleOther = boxApple.insertOtherBox();
        System.out.println("Before apple box wight: " + boxApple.getWeightBox());
        System.out.println("New apple box wight: " + boxAppleOther.getWeightBox());

    }
}
