package HW1.other;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> implements Comparable<Box<T>> {
    private List<T> fruitsInBox = new ArrayList<>();

    public void addFruit(T fruit) {
        fruitsInBox.add(fruit);
    }

    public void addFruits(List<T> fruits) {
        fruitsInBox.addAll(fruits);
    }

    public void removeFruit(T fruit) {
        this.fruitsInBox.remove(fruit);
    }

    public void removeFruits() {
        this.fruitsInBox.clear();
    }

    public Float getWeightBox() {
        float result = 0f;
        for (T inBox : fruitsInBox) {
            result = result + inBox.getWeight();
        }
        return result;
    }

    public Box<T> insertOtherBox() {
        Box<T> box = new Box<>();
        box.addFruits(this.fruitsInBox);
        this.removeFruits();
        return box;
    }

    public boolean compare(Box obj) {
        return compareTo(obj) == 0;
    }

    @Override
    public int compareTo(Box o) {
        return o.getWeightBox().compareTo(this.getWeightBox());
    }
}
