package queue;

import java.util.Objects;

// Model: q[0]..q[size-1]
// Inv: size ≥ 0 && ∀i ∈ [0;size-1]: q[i] != null
// Let: immutable(l,k): ∀i ∈ [l;k]: q'[i] = q[i], l≤k
// Let: no_changes: size' = size && immutable(0, size-1)
public class LinkedQueue extends AbstractQueue {
    private Node tail;
    private Node head;


    // pred: element!=null
    // post: q'[(size + (int)toHead)) % (size+1)] = element &&
    // ∀i ∈ [(int)toHead;size-1+(int)toHead] q'[i] = q[i-(int)toHead]
    @Override
    protected void abstractAdd(Object element, boolean toHead) {
        if (addToEmpty(element)) {
            if (toHead) {
                Node headCpy = head;
                head = new Node(element, null, headCpy);
                headCpy.prev = tail;
            } else {
                Node tailCpy = tail;
                tail = new Node(element, tailCpy, null);
                tailCpy.next = tail;
            }
        }
    }


    // pred: size > 0
    // post: R=q[(size-(int)fromTale) % size] &&
    // ∀i ∈ [0;size - 1 - (int)remove] q'[i] = q[i + (int)(!fromTale & remove)]
    @Override
    protected Object abstractGet(boolean fromTale, boolean remove) {
        Object ret;
        if (fromTale) {
            ret = tail.element;
            tail = remove ? shiftQueue(tail.prev, head) : tail;

        } else {
            ret = head.element;
            head = remove ? shiftQueue(head.next, tail) : head;
        }
        return ret;
    }


    // pred: true
    // post: size' = 0
    @Override
    protected void clearImpl() {
        tail = null;
        head = null;
    }


    // pred: true
    // post: R = arr[n]: ∀i ∈ [0; size - 1] arr[i] = q[i] && no_changes
    @Override
    protected void abstractFill(Object[] arr) {
        Node ptr = head;
        int index = 0;
        while (index < size()) {
            arr[index++] = ptr.element;
            ptr = ptr.next;
        }
    }


    // pred: element != null
    // post: size > 0
    private boolean addToEmpty(Object element) {
        if (size() == 0) {
            head = new Node(element, null, null);
            tail = head;
            tail.prev = head;
            head.next = tail;
            return false;
        }
        return true;
    }


    // pred: size > 0
    // post: (Node) R != null
    private Node shiftQueue(Node ptr, Node alter) {
        return Objects.requireNonNullElse(ptr, alter);
    }

    private static class Node {
        private Node next;
        private Node prev;
        private final Object element;

        public Node(Object element, Node prev, Node next) {
            this.prev = prev;
            this.next = next;
            this.element = element;
        }
    }
}
