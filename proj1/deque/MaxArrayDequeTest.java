package deque;

import org.junit.Test;
import java.util.Comparator;

import static org.junit.Assert.*;
public class MaxArrayDequeTest {
    @Test
    public void TestString() {
        Comparator<String> s = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        };
        MaxArrayDeque<String> stringMaxArrayDeque = new MaxArrayDeque<>(s);
        stringMaxArrayDeque.addLast("1");
        stringMaxArrayDeque.addLast("12");
        stringMaxArrayDeque.addLast("123");
        stringMaxArrayDeque.addLast("1234");
        String c = stringMaxArrayDeque.max();
        assertEquals("1234", c);
    }
    @Test
    public void TestDog() {
        Dog d1 = new Dog(100, "BiggerDog");
        Dog d2 = new Dog(50, "SmallerDog");
        Dog d3 = new Dog(10, "TheBiggerDogInName");
        Dog d4 = new Dog(10, "SmallerDog");
        MaxArrayDeque<Dog> dogs = new MaxArrayDeque<>(new Dog.DogNameComparator());
        Dog nullDog = dogs.max();
        assertNull(nullDog);
        dogs.addLast(d1);
        dogs.addLast(d2);
        dogs.addLast(d3);
        dogs.addLast(d4);
        Dog NameBiggerDog = dogs.max();
        assertEquals(d3.name, NameBiggerDog.name);
        Dog SizeBiggerDog = dogs.max(new Dog.DogSizeComparator());
        assertEquals("BiggerDog", SizeBiggerDog.name);
    }
    //内嵌类，所以可以是public的
    public class Dog {
        private int size;
        private String name;
        public Dog(int s, String Name) {
            size = s;
            name = Name;
        }
        private static class DogNameComparator implements Comparator<Dog> {
            @Override
          public int compare(Dog o1 , Dog o2) {
                return o1.name.length() - o2.name.length();
            }
        }
        private static class DogSizeComparator implements Comparator<Dog> {
            @Override
            public int compare(Dog d1 , Dog d2) {
                return d1.size - d2.size;
            }
        }
    }
}
