package queue;

import java.util.Objects;

// Model: q[0]..q[size-1]
// Inv: size >= 0 && head >=0 && tail >= head && ∀i ∈ [0;size-1]: q[i] != null
// Let: immutable(l,k): ∀i ∈ [l;k]: a'[i] = q[i], l<=k
// Let: size = tail - head
public class ArrayQueueADT {
    private Object[] queue;
    private int tail;
    private int head;

    public ArrayQueueADT() {
        this.queue = new Object[4];
        this.tail = 0;
        this.head = 0;
    }

    // pred: true
    // post: q not null && tail' = head' = 0 => size = 0
    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }

    // pred: element!=null
    // post: a'[size] = element && immutable(0,size-1) && size'= size + 1 &&
    // tail' = tail + 1 && head' = head
    public static void enqueue(ArrayQueueADT queueADT, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queueADT, size(queueADT));
        queueADT.queue[(queueADT.tail) % queueADT.queue.length] = element;
        queueADT.tail++;
    }

    // pred: true
    // post: n' = n && immutable(0,size - 1) && head' = 0 && size' = size
    private static void ensureCapacity(ArrayQueueADT queueADT, int capacity) {
        if (capacity >= queueADT.queue.length) {
            Object[] queueDup = new Object[2 * capacity];
            streamlineElements(queueADT, queueDup);
            queueADT.tail = queueADT.queue.length;
            queueADT.head = 0;
            queueADT.queue = queueDup;
        }
    }

    // pred: size > 0
    // post: R = q[0] && size'= size && immutable(0,size - 1)
    public static Object element(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        return queueADT.queue[queueADT.head % queueADT.queue.length];
    }

    // pred: size > 0
    // post: size' = size - 1 && head' = head +1 && tail'= tail &&
    // ∀i ∈ [0;size'- 1] q'[i] = q[i + 1]
    public static Object dequeue(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        queueADT.head++;
        return queueADT.queue[(queueADT.head - 1) % queueADT.queue.length];
    }

    // pred: size > 0
    // post: R = q[size - 1] && size'= size && immutable(0,size - 1) && head >= 0 && size > 0
    public static Object peek(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        return queueADT.queue[(queueADT.tail - 1) % queueADT.queue.length];
    }

    //pred: true
    //post: head' > 0 && size' = size && immutable(0,size-1)
    private static void checkHead(ArrayQueueADT queueADT) {
        while (queueADT.head <= 0) {
            queueADT.head += queueADT.queue.length;
            queueADT.tail += queueADT.queue.length;
        }
    }

    // pred: element != null
    // post: size' = size+1 && ∀i ∈ [1;size] q'[i] = q[i-1] && q'[0] = element && head' >= 0
    public static void push(ArrayQueueADT queueADT, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queueADT, size(queueADT));
        checkHead(queueADT);
        queueADT.queue[(--queueADT.head) % queueADT.queue.length] = element;
    }

    // pred: size > 0
    // post: R = q[size-1] && size'= size-1 && head' = head && tail' = tail - 1 && immutable(0,size'-1)
    public static Object remove(ArrayQueueADT queueADT) {
        assert queueADT.size(queueADT) > 0;
        queueADT.tail--;
        return queueADT.queue[queueADT.tail % queueADT.queue.length];
    }

    // pred: pred: target.len = size
    // post: ∀i ∈ [0; size - 1] target[i] = q[i] && immutable(0, size - 1) && size' = size
    private static void streamlineElements(ArrayQueueADT queueADT, Object[] target) {
        for (int i = 0; i < size(queueADT);) {
            target[i] = queueADT.queue[(queueADT.head + i++) % queueADT.queue.length];
        }
    }

    // pred: true
    // post: R = arr[n]: ∀i ∈ [0; size - 1] arr[i] = q[i] && immutable(0, size - 1) && size' = size
    public static Object[] toArray(ArrayQueueADT queueADT) {
        Object[] arr = new Object[size(queueADT)];
        streamlineElements(queueADT, arr);
        return arr;
    }

    // pred: true
    // post: R = arr[n].toString, arr: ∀i ∈ [0; size - 1] arr[i] = q[i] && immutable(0, size - 1)
    // && size' = size
    public static String toStr(ArrayQueueADT queueADT) {
        StringBuilder sbuilder = new StringBuilder("[");
        Object[] asArray = toArray(queueADT);
        for (int i = 0; i < asArray.length; i++) {
            if (i != asArray.length - 1) {
                sbuilder.append(asArray[i].toString()).append(", ");
            } else {
                sbuilder.append(asArray[i].toString());
            }
        }
        sbuilder.append("]");
        return sbuilder.toString();
    }


    // pred: true
    // post: R = size && immutable(0, size - 1) && tail' = tail && head' = head
    public static int size(ArrayQueueADT queueADT) {
        return queueADT.tail - queueADT.head;
    }

    // pred: true
    // post: R = (size == 0) && immutable(0, size - 1) && tail' = tail && head' = head
    public static boolean isEmpty(ArrayQueueADT queueADT) {
        return queueADT.tail - queueADT.head == 0;
    }

    // pred: true
    // post: size' = 0 && head' = 0 && tail' = 0
    public static void clear(ArrayQueueADT queueADT) {
        queueADT.tail = 0;
        queueADT.head = 0;
        queueADT.queue = new Object[4];
    }
}