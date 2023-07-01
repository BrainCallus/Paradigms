package queue;

// Model: q[0]..q[size-1]
// Inv: size ≥ 0 && ∀i ∈ [0;size-1]: q[i] != null
// Let: immutable(l,k): ∀i ∈ [l;k]: a'[i] = q[i], l≤k
// Let: no_changes: size' = size && immutable(0, size-1)

public class ArrayQueue extends AbstractQueue {
    private Object[] queue = new Object[4];
    private int tail = -1;
    private int head;


    // pred: element!=null
    // post: q'[(size+1 + (int)toHead)) % size'] = element &&
    // ∀i ∈ [(int)toHead;size-1+(int)toHead] q'[i] = q[i-(int)toHead]
    @Override
    protected void abstractAdd(Object element, boolean toHead) {
        this.ensureCapacity();
        if (toHead) {
            checkHead();
        }
        addElement(element, toHead ? --head : ++tail);
    }


    // pred: size > 0
    // post: R=q[(size-(int)fromTale) % size] &&
    // ∀i ∈ [0;size - 1 - (int)remove] q'[i] = q[i + (int)(!fromTale & remove)]
    @Override
    protected Object abstractGet(boolean fromTale, boolean remove) {
        return fromTale ? getElement(remove ? tail-- : tail) : getElement(remove ? head++ : head);
    }


    // pred: true
    // post: size' = 0
    @Override
    protected void clearImpl() {
        this.queue = new Object[4];
        head = 0;
        tail = -1;
    }


    // pred: true
    // post: R = arr[n]: ∀i ∈ [0; size - 1] arr[i] = q[i] && no_changes
    @Override
    protected void abstractFill(Object[] target) {
        for (int i = 0; i < size(); i++) {
            target[i] = queue[(head + i) % queue.length];
        }
    }


    // pred: element!=null
    // post: q'[[ptr] % (size+1)] = element
    private void addElement(Object element, int ptr) {
        this.queue[ptr % queue.length] = element;
    }


    // pred: true
    // post: head' > 0 && tail' > 0
    private void checkHead() {
        if (head <= 0) {
            head += queue.length;
            tail += queue.length;
        }
    }


    // pred: true
    // post: queue.len > size && no_changes
    private void ensureCapacity() {
        if (size() >= this.queue.length) {
            Object[] queueDup = new Object[2 * size()];
            abstractFill(queueDup);
            this.tail = this.queue.length - 1;
            this.head = 0;
            this.queue = queueDup;
        }

    }


    // pred: size > 0
    // post: R=q[ptr]
    private Object getElement(int ptr) {
        return this.queue[ptr % this.queue.length];
    }

}
