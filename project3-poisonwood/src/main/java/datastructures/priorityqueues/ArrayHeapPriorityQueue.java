package datastructures.priorityqueues;

import datastructures.EmptyContainerException;
import datastructures.dictionaries.ArrayDictionary;
import datastructures.dictionaries.IDictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see IPriorityQueue for details on what each method must do.
 */
public class ArrayHeapPriorityQueue<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    /*
    You MUST use this field to store the contents of your heap.
    You may NOT rename this field or change its type: we will be inspecting it in our secret tests.
    */
    T[] heap;
    IDictionary<T, Integer> indices;
    private int size;

    // Feel free to add more fields and constants.

    public ArrayHeapPriorityQueue() {
        this.size = 0;
        this.heap = makeArrayOfT(1);
        this.indices = new ArrayDictionary<>();
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type `T`.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int arraySize) {
        /*
        This helper method is basically the same one we gave you in `ArrayDictionary` and
        `ChainedHashDictionary`.

        As before, you do not need to understand how this method works, and should not modify it in
         any way.
        */
        return (T[]) (new Comparable[arraySize]);
    }

    @Override
    public T removeMin() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        T min = heap[0];
        swap(0, size - 1);
        indices.remove(min);
        size--;
        percolate(0);
        return min;
    }

    @Override
    public T peekMin() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        return heap[0];
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (contains(item)) {
            throw new InvalidElementException();
        }
        if (size == heap.length) {
            T[] temp = heap;
            heap = makeArrayOfT(heap.length * 2);
            for (int i = 0; i < temp.length; i++) {
                heap[i] = temp[i];
            }
        }
        indices.put(item, size);
        heap[size] = item;
        percolate(size);
        size++;
    }

    @Override
    public boolean contains(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        return indices.containsKey(item);
    }

    @Override
    public void remove(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (!contains(item)) {
            throw new InvalidElementException();
        }
        int index = indices.remove(item);
        if (index == size - 1) {
            heap[size - 1] = null;
        } else {
            swap(index, size - 1);
            heap[size - 1] = null;
            percolate(index);
        }
        size--;
    }

    @Override
    public void replace(T oldItem, T newItem) {
        if (oldItem == null || newItem == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0 || !contains(oldItem) || contains(newItem)) {
            throw new InvalidElementException();
        }
        int index = indices.remove(oldItem);
        heap[index] = newItem;
        indices.put(newItem, index);
        percolate(index);
    }

    @Override
    public int size() {
        return size;
    }

    private int findSmallestChild(int index) {
        int ind = index * NUM_CHILDREN + 1;
        for (int i = 2; i <= NUM_CHILDREN; i++) {
            if (index * NUM_CHILDREN + i < size
                    && heap[ind].compareTo(heap[index * NUM_CHILDREN + i]) > 0) {
                ind = index * NUM_CHILDREN + i;
            }
        }
        return ind;

    }
    private int getParentIndex(int index) {
        if (index > 0){
            return (index - 1) / NUM_CHILDREN;
        }
        return index;
    }

    /**
     * A method stub that you may replace with a helper method for percolating
     * upwards from a given index, if necessary.
     */
    private void percolateUp(int index) {
        if (index > 0) {
            int parent = getParentIndex(index);
            if (heap[parent].compareTo(heap[index]) > 0) {
                swap(index, parent);
                percolate(parent);
            }
        }
    }

    /**
     * A method stub that you may replace with a helper method for percolating
     * downwards from a given index, if necessary.
     */
    private void percolateDown(int index) {
        if (index * NUM_CHILDREN + 1 < size) {
            int child = findSmallestChild(index);
            if (heap[index].compareTo(heap[child]) > 0) {
                swap(child, index);
                percolate(child);
            }
        }
    }

    /**
     * A method stub that you may replace with a helper method for determining
     * which direction an index needs to percolate and percolating accordingly.
     */
    private void percolate(int index) {
        if (index == 0) {
            percolateDown(index);
        } else {
            int parent = getParentIndex(index);
            if (heap[parent].compareTo(heap[index]) > 0) {
                percolateUp(index);
            } else if (heap[index].compareTo(heap[parent]) > 0) {
                percolateDown(index);
            }
        }
    }

    /**
     * A method stub that you may replace with a helper method for swapping
     * the elements at two indices in the `heap` array.
     */
    private void swap(int a, int b) {
        T temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
        indices.put(heap[a], a);
        indices.put(temp, b);
    }

    @Override
    public String toString() {
        return IPriorityQueue.toString(this);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayHeapIterator<>(this.heap, this.size());
    }

    private static class ArrayHeapIterator<T extends Comparable<T>> implements Iterator<T> {
        private final T[] heap;
        private final int size;
        private int index;

        ArrayHeapIterator(T[] heap, int size) {
            this.heap = heap;
            this.size = size;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.size;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            T output = heap[this.index];
            this.index++;
            return output;
        }
    }
}