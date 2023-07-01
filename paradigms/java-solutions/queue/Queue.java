package queue;

// Model: q[0]..q[size-1]
// Inv: size ≥ 0 && ∀i ∈ [0;size-1]: q[i] != null
// Let: immutable(l,k): ∀i ∈ [l;k]: q'[i] = q[i], l≤k
// Let: no_changes: size' = size && immutable(0, size-1)

public interface Queue {
    // pred: element!=null
    // post: q'[size] = element && immutable(0,size-1) && size' = size + 1
    void enqueue(Object element);


    // pred: element != null
    // post: size' = size+1 && ∀i ∈ [1;size] q'[i] = q[i-1] && q'[0] = element
    void push(Object element);


    // pred: size > 0
    // post: R = q[0] && no_changes
    Object element();


    // pred: size > 0
    // post: R = q[size - 1] && no_changes
    Object peek();


    // pred: size > 0
    // post: R=q[0] && size' = size - 1 && ∀i ∈ [0;size'- 1] q'[i] = q[i + 1]
    Object dequeue();


    // pred: size > 0
    // post: R = q[size-1] && size'= size-1 && q[size-1] = null && immutable(0,size'-1)
    Object remove();


    // pred: true
    // post: R = size && no_changes
    int size();


    // pred: true
    // post: R = (size == 0) && no_changes
    boolean isEmpty();


    // pred: true
    // post: size' = 0
    void clear();


    // pred: true
    // post: R = arr[n]: ∀i ∈ [0; size - 1] arr[i] = q[i] && no_changes
    Object[] toArray();


    // pred: true
    // post: R = arr[n].toString, arr: ∀i ∈ [0; size - 1] arr[i] = q[i] && no_changes
    String toStr();


    //pred: true
    //post: R=(amount of equal elements to obj) && no_changes
    int count(Object obj);


    //pred: true
    //post: R=(first index of element equal to obj) && no_changes
    int indexOf(Object obj);


    //pred: true
    //post: R=(last index of element equal to obj) && no_changes
    int lastIndexOf(Object obj);

}
