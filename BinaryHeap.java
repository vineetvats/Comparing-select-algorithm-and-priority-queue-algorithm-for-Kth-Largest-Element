// Starter code for bounded-size Binary Heap implementation

/* Team Members :
 * Punit Bhalla
 * Vineet Vats
 *
 * Purpose:
 * Implementation of Bounded-Size Binary Heap
 */

package pxb173830;

import java.util.Comparator;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
    T[] pq;
    Comparator<T> comp;

    // size variable keep tracks of the current size of the heap
    // maxSize denotes the maximum size of a heap
    int size,maxSize;

    // Constructor for building an empty priority queue using natural ordering
    // of T
    public BinaryHeap(T[] q) {
        // Use a lambda expression to create comparator from compareTo
        this(q, (T a, T b) -> a.compareTo(b));
    }

    // Constructor for building an empty priority queue with custom comparator
    public BinaryHeap(T[] q, Comparator<T> c) {
        pq = q;
        comp = c;
        size = pq.length;
        maxSize = pq.length;

        /* Building a heap from the given array "q"
         * i.e. maintaining the structure constraint and order constraint
         */
        for (int i = parent(size - 1); i >= 0; i--) {
            percolateDown(i);
        }
    }

    /* Adds an element of type T in the heap and maintains order constraint at the same time.
     * This operation throws an exception when we try to add an element in already full heap.
     */
    public void add(T x) { /* throw exception if pq is full */
        if (size == maxSize)
            throw new IllegalStateException("Heap is full!");
        move(size, x);
        percolateUp(size);
        size++;
    }

    /* Adds an element of type T in the heap and maintains order constraint at the same time.
     * This operation returns false when we try to add an element in already full heap.
     */

    public boolean offer(T x) { /* return false if pq is full */
        if (size == maxSize)
            return false;
        move(size, x);
        percolateUp(size);
        size++;
        return true;
    }

    /* Removes and returns the top element of the heap and maintains order constraint at the same time.
     * This operation throws an exception when we try to remove top element from an empty heap.
     */
    public T remove() { /* throw exception if pq is empty */
        if (size == 0)
            throw new IllegalStateException("Heap is empty!");
        T min = pq[0];
        move(0, pq[size - 1]);
        size--;
        percolateDown(0);
        return min;
    }

    /* Removes and returns the top element of the heap and maintains order constraint at the same time.
     * This operation return null when we try to remove top element from an empty heap.
     */
    public T poll() { /* return null if pq is empty */
        if (size == 0)
            return null;
        T min = pq[0];
        move(0, pq[size - 1]);
        size--;
        percolateDown(0);
        return min;
    }

    /* Returns the top element of the heap.
     * This operation returns null when the heap is empty.
     */
    public T peek() { /* return null if pq is empty */
        if (size == 0)
            return null;
        return pq[0];
    }

    /** pq[i] may violate heap order with parent */
    void percolateUp(int i) { /* to be implemented */
        T x = pq[i];
        while (i > 0 && (pq[parent(i)].compareTo(x) > 0)) {
            move(i, pq[parent(i)]);
            i = parent(i);
        }
        move(i, x);
    }

    /** pq[i] may violate heap order with children */
    void percolateDown(int i) { /* to be implemented */
        T x = pq[i];
        int c = leftChild(i);
        while (c <= size - 1) {
            if (c < size - 1 && (pq[c].compareTo(pq[c + 1]) > 0)) {
                c++;
            }
            if (x.compareTo(pq[c]) <= 0) {
                break;
            }
            move(i, pq[c]);
            i = c;
            c = leftChild(i);
        }
        move(i, x);
    }

    // Assign x to pq[i]. Indexed heap will override this method
    void move(int i, T x) {
        pq[i] = x;
    }

    // Returns the index of parent node from the priority queue
    int parent(int i) {
        return (i - 1) / 2;
    }

    // Returns the index of left child of a node from the priority queue
    int leftChild(int i) {
        return 2 * i + 1;
    }

    // prints the elements of heap
    public void printHeap() {
        System.out.print("Current Size : "+this.size + ", List : ");
        for (int i=0;i<size;i++) {
            System.out.print(pq[i] + " ");
        }
        System.out.println();
    }
}
