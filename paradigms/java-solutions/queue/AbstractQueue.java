package queue;

import java.util.Objects;

// Model: q[0]..q[size-1]
// Inv: size ≥ 0 && ∀i ∈ [0;size-1]: q[i] != null
// Let: immutable(l,k): ∀i ∈ [l;k]: q'[i] = q[i], l≤k
// Let: no_changes: size' = size && immutable(0, size-1)
public abstract class AbstractQueue implements Queue {
    private int size;


    // pred: element!=null
    // post: size' = size + 1 && q'[size] = element && immutable(0,size-1)
    @Override
    public void enqueue(Object element) {
        addElement(element, false);
    }


    // pred: element != null
    // post: size' = size+1 && ∀i ∈ [1;size] q'[i] = q[i-1] && q'[0] = element
    @Override
    public void push(Object element) {
        addElement(element, true);
    }


    // pred: size > 0
    // post: R = q[0] && no_changes
    @Override
    public Object element() {
        return getElement(false, false);
    }


    // pred: size > 0
    // post: R = q[size - 1] && no_changes
    @Override
    public Object peek() {
        return getElement(true, false);

    }


    // pred: size > 0
    // post: R=q[0] && size' = size - 1 && ∀i ∈ [0;size'- 1] q'[i] = q[i + 1]
    @Override
    public Object dequeue() {
        return getElement(false, true);
    }


    // pred: size > 0
    // post: R = q[size-1] && size'= size-1 && q[size-1] = null && immutable(0,size'-1)
    @Override
    public Object remove() {
        return getElement(true, true);
    }


    // pred: true
    // post: R = size && no_changes
    @Override
    public int size() {
        return size;
    }


    // pred: true
    // post: R = (size == 0) && no_changes
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    // pred: true
    // post: size' = 0
    @Override
    public void clear() {
        size = 0;
        clearImpl();
    }


    // pred: true
    // post: R = arr[n]: ∀i ∈ [0; size - 1] arr[i] = q[i] && no_changes
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        abstractFill(arr);
        return arr;
    }


    // pred: true
    // post: R = arr[n].toString, arr: ∀i ∈ [0; size - 1] arr[i] = q[i] && no_changes
    @Override
    public String toStr() {
        Object[] elements = toArray();
        StringBuilder sbuilder = new StringBuilder("[");
        for (int i = 0; i < elements.length; i++) {
            if (i != elements.length - 1) {
                sbuilder.append(elements[i].toString()).append(", ");
            } else {
                sbuilder.append(elements[i].toString());
            }
        }
        sbuilder.append("]");
        return sbuilder.toString();
    }


    //pred: true
    //post: R=(amount of equal elements to obj) && no_changes
    @Override
    public int count(Object obj) {
        Object[] elements = toArray();
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], obj)) {
                ++count;
            }
        }
        return count;
    }

    // pred: true
    // post: size' = 0
    protected abstract void clearImpl();


    //pred: true
    //post: R=(first index of element equal to obj) && no_changes
    @Override
    public int indexOf(Object obj) {
        return getIndexOf(obj, false);
    }


    //pred: true
    //post: R=(last index of element equal to obj) && no_changes
    @Override
    public int lastIndexOf(Object obj) {
        return getIndexOf(obj, true);
    }


    // pred: element!=null
    // post: q'[(size + (int)toHead)) % (size+1)] = element &&
    // ∀i ∈ [(int)toHead;size-1+(int)toHead] q'[i] = q[i-(int)toHead]
    protected abstract void abstractAdd(Object element, boolean toHead);


    // pred: size > 0
    // post: R=q[(size-(int)fromTale) % size] &&
    // ∀i ∈ [0;size - 1 - (int)remove] q'[i] = q[i + (int)(!fromTale & remove)]
    protected abstract Object abstractGet(boolean fromTale, boolean remove);


    // pred: true
    // post: R = arr[n]: ∀i ∈ [0; size - 1] arr[i] = q[i] && no_changes
    protected abstract void abstractFill(Object[] arr);


    // pred: element!=null
    // post: size' = size + 1 && q'[(size' - 1 + (int)toHead)) % size'] = element &&
    // ∀i ∈ [(int)toHead;size-1+(int)toHead] q'[i] = q[i-(int)toHead]
    private void addElement(Object element, boolean toHead) {
        Objects.requireNonNull(element);
        abstractAdd(element, toHead);
        size++;
    }


    // pred: size > 0
    // post: R=q[(size-(int)fromTale) % size] && size' = size - (int)remove
    // && ∀i ∈ [0;size'- 1] q'[i] = q[i + (int)(!fromTale & remove)]
    private Object getElement(boolean fromTale, boolean remove) {
        assert size() > 0;
        Object element = abstractGet(fromTale, remove);
        if (remove) {
            --size;
        }
        return element;
    }


    //pred: true
    //post: R=(last or first index of element equal to obj | -1 if no such element)
    // && no_changes
    private int getIndexOf(Object obj, boolean fromDesc) {
        Object[] elements = toArray();
        for (int i = 0; i < size; i++) {
            if (Objects.equals(obj, elements[index(i, fromDesc)])) {
                return index(i, fromDesc);
            }
        }
        return -1;
    }


    // pred: 0 ≤ ind < size
    // post: R=(fromDesc ? size - ind - 1 : ind) && 0 ≤ R <size
    private int index(int ind, boolean fromDesc) {
        return fromDesc ? size - ind - 1 : ind;
    }

}
