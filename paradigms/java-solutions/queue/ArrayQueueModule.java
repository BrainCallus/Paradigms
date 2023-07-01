package queue;

import java.util.Objects;

// Model: q[0]..q[size-1]
// Inv: size >= 0 && head >=0 && tail >= head && ∀i ∈ [0;size-1]: q[i] != null
// Let: immutable(l,k): ∀i ∈ [l;k]: a'[i] = q[i], l<=k
// Let: size = tail - head
public class ArrayQueueModule {
    private static Object[] queue = new Object[4];
    private static int tail; //в хвост суем новые
    private static int head; //головной эл-т - первый в очереди

    // pred: element!=null
    // post: a'[size] = element && immutable(0,size-1) && size'= size + 1 &&
    // tail' = tail + 1 && head' = head
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size());
        queue[tail % queue.length] = element;
        tail++;
    }

    // pred: true
    // post: n' = n && immutable(0,size - 1) && head' = 0 && size' = size
    private static void ensureCapacity(int capacity) {
        if (capacity >= queue.length) {
            Object[] queueDup = new Object[2 * capacity];
            streamlineElements(queueDup);
            tail = queue.length;
            head = 0;
            queue = queueDup;
        }
    }

    // pred: size > 0
    // post: R = q[0] && size'= size && immutable(0,size - 1)
    public static Object element() {
        assert size() > 0;
        return queue[head % queue.length];
    }

    // pred: size > 0
    // post: size' = size - 1 && head' = head +1 && tail'= tail &&
    // ∀i ∈ [0;size'- 1] q'[i] = q[i + 1]
    public static Object dequeue() {
        assert size() > 0;
        head++;
        return queue[(head - 1) % queue.length];
    }

    // pred: size > 0
    // post: R = q[size - 1] && size'= size && immutable(0,size - 1) && head >= 0 && size > 0
    public static Object peek() {
        assert size() > 0;
        return queue[(tail - 1) % queue.length];
    }

    //pred: true
    //post: head' > 0 && size' = size && immutable(0,size-1)
    private static void checkHead() {
        while (head <= 0) {
            head += queue.length;
            tail += queue.length;
        }
    }

    // pred: element != null
    // post: size' = size+1 && ∀i ∈ [1;size] q'[i] = q[i-1] && q'[0] = element && head' >= 0
    public static void push(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size());
        checkHead();
        queue[(--head) % queue.length] = element;
    }

    // pred: size > 0
    // post: R = q[size-1] && size'= size-1 && head' = head && tail' = tail - 1 && immutable(0,size'-1)
    public static Object remove() {
        assert size() > 0;
        tail--;
        return queue[tail % queue.length];
    }

    // pred: pred: target.len = size
    // post: ∀i ∈ [0; size - 1] target[i] = q[i] && immutable(0, size - 1) && size' = size
    private static void streamlineElements(Object[] target) {
        for (int i = 0; i < size(); i++) {
            target[i] = queue[(head + i) % queue.length];
        }
    }

    // pred: true
    // post: R = arr[n]: ∀i ∈ [0; size - 1] arr[i] = q[i] && immutable(0, size - 1) && size' = size
    public static Object[] toArray() {
        Object[] arr = new Object[size()];
        streamlineElements(arr);
        return arr;
    }

    // pred: true
    // post: R = arr[n].toString, arr: ∀i ∈ [0; size - 1] arr[i] = q[i] && immutable(0, size - 1)
    // && size' = size
    public static String toStr() {
        StringBuilder sbuilder = new StringBuilder("[");
        Object[] asArray = toArray();
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
    public static int size() {
        return tail - head;
    }

    // pred: true
    // post: R = (size == 0) && immutable(0, size - 1) && tail' = tail && head' = head
    public static boolean isEmpty() {
        return tail - head == 0;
    }

    // pred: true
    // post: size' = 0 && head' = 0 && tail' = 0
    public static void clear() {
        queue = new Object[4];
        head = 0;
        tail = 0;
    }
}
