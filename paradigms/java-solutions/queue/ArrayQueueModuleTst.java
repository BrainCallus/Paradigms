package queue;

public class ArrayQueueModuleTst {
    public static void fill(String pref) {
        for (int i = 0; i < 7; i++) {
            ArrayQueueModule.enqueue(pref + i);
        }
    }

    public static void dumpHead() {

        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " "
                    + ArrayQueueModule.element() + " "
                    + ArrayQueueModule.dequeue()
            );
        }
    }

    public static void dumpTale() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " "
                    + ArrayQueueModule.peek() + " " + ArrayQueueModule.remove());
        }
    }

    public static void main(String[] args) {
        fill("qH_");
        System.out.println("q = " + ArrayQueueModule.toStr());
        dumpHead();
        System.out.println("q = " + ArrayQueueModule.toStr());
        fill("qT_");
        System.out.println("q = " + ArrayQueueModule.toStr());
        dumpTale();
        System.out.println("q = " + ArrayQueueModule.toStr());
    }
}
