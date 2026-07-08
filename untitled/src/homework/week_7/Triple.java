package homework.week_7;

import java.util.function.Function;

public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }
    public C getThird() { return third; }

    public static <X, Y, Z> Triple<X, Y, Z> create(X first, Y second, Z third) {
        return new Triple<>(first, second, third);
    }

    public <X, Y, Z> Triple<X, Y, Z> transform(
            Function<A, X> firstFunc,
            Function<B, Y> secondFunc,
            Function<C, Z> thirdFunc) {
        return new Triple<>(
                firstFunc.apply(this.first),
                secondFunc.apply(this.second),
                thirdFunc.apply(this.third)
        );
    }
}

class TripleDemo {
    public static void main(String[] args) {
        Triple<String, Integer, Double> t1 = Triple.create("Текст", 42, 3.14);
        System.out.println("Triple 1: " + t1.getFirst() + ", " + t1.getSecond() + ", " + t1.getThird());

        Triple<Integer, String, Boolean> t2 = t1.transform(
                String::length,
                String::valueOf,
                val -> val > 3.0
        );

        System.out.println("Triple 2 (Transformed): " + t2.getFirst() + ", " + t2.getSecond() + ", " + t2.getThird());
    }
}