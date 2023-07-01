package queue;

public class ArrayQueueTst {
    public static void fill(ArrayQueue queue, String pref) {
        for (int i = 0; i < 7; i++) {
            queue.enqueue(pref + i);
        }
    }

    public static void dump(ArrayQueue queue) {

        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.dequeue());
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        fill(queue1, "q1_");
        System.out.println("q1 = " + queue1.toStr());
        fill(queue2, "q2_");
        System.out.println("q2 = " + queue2.toStr());
        System.out.println(queue2.element());
        System.out.println(queue2.dequeue());
        dump(queue1);
        dump(queue2);
        System.out.println("q1 = " + queue1.toStr());
        System.out.println("q2 = " + queue2.toStr());
    }
}
