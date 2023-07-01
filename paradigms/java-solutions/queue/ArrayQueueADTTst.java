package queue;

public class ArrayQueueADTTst {
    public static void fill(ArrayQueueADT queue, String pref) {
        for (int i = 0; i < 7; i++) {
            ArrayQueueADT.push(queue, pref + i);
        }
    }

    public static void dumpHead(ArrayQueueADT queue) {

        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.size(queue) + " "
                    + ArrayQueueADT.element(queue) + " "
                    + ArrayQueueADT.dequeue(queue)
            );
        }
    }

    public static void dumpTale(ArrayQueueADT queue) {

        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.size(queue) + " "
                    + ArrayQueueADT.peek(queue) + " "
                    + ArrayQueueADT.remove(queue)
            );
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = ArrayQueueADT.create();
        ArrayQueueADT queue2 = ArrayQueueADT.create();
        fill(queue1, "q1_H_");
        System.out.println("q1 = " + ArrayQueueADT.toStr(queue1));
        fill(queue2, "q2_H_");
        System.out.println("q2 = " + ArrayQueueADT.toStr(queue2));
        System.out.println();
        dumpHead(queue1);
        dumpHead(queue2);
        System.out.println("q1 = " + ArrayQueueADT.toStr(queue1));
        System.out.println("q2 = " + ArrayQueueADT.toStr(queue2));
        System.out.println();
        fill(queue1, "q1_T_");
        fill(queue2, "q2_T_");
        System.out.println("q1 = " + ArrayQueueADT.toStr(queue1));
        System.out.println("q2 = " + ArrayQueueADT.toStr(queue2));
        dumpTale(queue1);
        dumpTale(queue2);
        System.out.println("q1 = " + ArrayQueueADT.toStr(queue1));
        System.out.println("q2 = " + ArrayQueueADT.toStr(queue2));
    }
}
