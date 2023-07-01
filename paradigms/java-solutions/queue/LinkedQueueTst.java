package queue;

public class LinkedQueueTst {
    public static void enqueueq(Queue queue, String pref, int st, int fin) {
        for (int i = st; i <= fin; i++) {
            queue.enqueue(pref + i);
        }
    }

    public static void pushq(Queue queue, String pref, int st, int fin) {
        for (int i = fin; i >= st; i--) {
            queue.push(pref + i);
        }
    }

    public static void dumpFromHead(Queue queue) {
        int i = 0;
        while (!queue.isEmpty()) {
            System.out.println("q[" + i + "] = " + queue.dequeue());
            i++;
        }
    }

    public static void main(String[] args) {
        Queue queue = new LinkedQueue();
        enqueueq(queue, "qL_", 5, 8);
        pushq(queue, "qL_", 1, 4);
        System.out.println(queue.toStr());
        System.out.println("\n By elements:");
        dumpFromHead(queue);
    }
}
