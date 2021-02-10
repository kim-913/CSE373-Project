package datastructures.priorityqueues;

import datastructures.EmptyContainerException;
import misc.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * See spec for details on what kinds of tests this class should include.
 */
@Tag("project3")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TestArrayHeapPriorityQueue extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeapPriorityQueue<>();
    }

    /**
     * A helper method for accessing the private array inside an `ArrayHeapPriorityQueue`.
     */
    protected static <T extends Comparable<T>> Comparable<T>[] getArray(IPriorityQueue<T> heap) {
        return ((ArrayHeapPriorityQueue<T>) heap).heap;
    }

    private boolean isHeap(Comparable<Integer>[] array, int i, int size) {
        if (i >= size) {
            return true;
        } else if (4 * i + 1 < size && (int) array[i] > (int) array[4 * i + 1]) {
            return false;
        } else if (4 * i + 2 < size && (int) array[i] > (int) array[4 * i + 2]) {
            return false;
        } else if (4 * i + 3 < size && (int) array[i] > (int) array[4 * i + 3]) {
            return false;
        } else if (4 * i + 4 < size && (int) array[i] > (int) array[4 * i + 4]) {
            return false;
        } else {
            return isHeap(array, 4 * i + 1, size) && isHeap(array, 4 * i + 2, size) && isHeap(array, 4 * i + 3, size)
                    && isHeap(array, 4 * i + 4, size);
        }
    }

    // test some basic operations with add method
    @Test
    void testAddBasic() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertThat(heap.size(), is(0));
        heap.add(1);
        heap.add(0);
        heap.add(-1);
        heap.add(3);
        assertThat(heap.size(), is(4));
        Comparable<Integer>[] array = getArray(heap);
        assertThat(isHeap(array, 0, heap.size()), is(true));
        assertThat(array[0], is(-1));
    }


    // this test tests adding keys in decreasing order.
    // check heap invariant after each step
    // remove consecutively.
    // check if the first node is the smallest value
    // check contains method
    @Test
    void testAddRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        Comparable<Integer>[] array = getArray(heap);
        for (int i = 1000; i >= 1; i--) {
            heap.add(i);
            array = getArray(heap);
            assertThat(heap.size(), is(1001 - i));
            assertThat(heap.contains(i), is(true));
            assertThat(array[0], is(i));
            assertThat(isHeap(array, 0, heap.size()), is(true));
        }
        for (int i = 1; i <= 1000; i++) {
            assertThat(heap.removeMin(), is(i));
            assertThat(heap.size(), is(1000 - i));
            assertThat(heap.contains(i), is(false));
            assertThat(isHeap(array, 0, heap.size()), is(true));
        }
    }

    // this method tests whether add method can throw exception correctly
    @Test
    void testException() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertThrows(IllegalArgumentException.class, () -> { heap.add(null); });
        heap.add(0);
        assertThrows(InvalidElementException.class, () -> { heap.add(0); });
    }

    // tests whether removeMin throws exception correctly
    // tests whether removeMin gives the correct value
    // tests whether removeMin updates size correctly
    @Test
    void testRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertThrows(EmptyContainerException.class, () -> { heap.removeMin(); });
        for (int i = 0; i <= 10; i++) {
            heap.add(i);
        }
        assertThat(heap.removeMin(), is(0));
        assertThat(heap.removeMin(), is(1));
        assertThat(heap.size(), is(9));
        assertThat(heap.removeMin(), is(2));
        assertThat(heap.size(), is(8));
        Comparable<Integer>[] array = getArray(heap);
        assertThat(array[0], is(3));
        assertThat(isHeap(array, 0, heap.size()), is(true));
    }

    // check if peekMin throws exception correctly
    // check if peekMin removes the smallest value
    @Test
    void testPeek() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertThrows(EmptyContainerException.class, () -> { heap.peekMin(); });
        for (int i = 0; i <= 10; i++) {
            heap.add(i);
        }
        assertThat(heap.peekMin(), is(0));
        assertThat(heap.peekMin(), is(0));
        Comparable<Integer>[] array = getArray(heap);
        assertThat(array[0], is(0));
    }

    // check if exception is thrown correctly
    // test if the method returns the correct result
    @Test
    void testContains() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertThrows(IllegalArgumentException.class, () -> { heap.contains(null); });
        assertThat(heap.contains(10), is(false));
        for (int i = 0; i <= 10; i++) {
            heap.add(i);
        }
        assertThat(heap.contains(5), is(true));
        assertThat(heap.contains(11), is(false));
    }

    // check if exception is thrown
    // check if last element is removed correctly
    // check if a middle element is removed correctly
    // check if the first element is removed correctly
    @Test
    void testRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertThrows(IllegalArgumentException.class, () -> { heap.remove(null); });
        for (int i = 0; i <= 10; i++) {
            heap.add(i);
        }
        assertThrows(InvalidElementException.class, () -> { heap.remove(11); });
        heap.remove(10);
        Comparable<Integer>[] array = getArray(heap);
        assertThat(isHeap(array, 0, heap.size()), is(true));
        heap.remove(1);
        heap.remove(0);
        assertThat(isHeap(array, 0, heap.size()), is(true));
    }

    // check exceptions
    // replace values and check the internal state after the replacement.
    // check if size is updated correctly
    @Test
    void testReplace() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertThrows(InvalidElementException.class, () -> {heap.replace(4321, 75829); });
        for (int i = 0; i <= 10; i++) {
            heap.add(i);
        }
        assertThrows(IllegalArgumentException.class, () -> { heap.replace(null, 1); });
        assertThrows(IllegalArgumentException.class, () -> { heap.replace(1, null); });
        assertThrows(InvalidElementException.class, () -> { heap.replace(11, 20); });
        assertThrows(InvalidElementException.class, () -> { heap.replace(1, 2); });
        heap.replace(2, 11);
        assertThat(heap.size(), is(11));
        heap.replace(4, 100);
        heap.replace(1, 4);
        Comparable<Integer>[] array = getArray(heap);
        assertThat(array[0], is(0));
        assertThat(isHeap(array, 0, heap.size()), is(true));
        assertThat(heap.size(), is(11));
        assertThat(heap.removeMin(), is(0));
        assertThat(heap.removeMin(), is(3));
        heap.replace(10, 3);
        assertThat(isHeap(array, 0, heap.size()), is(true));
    }

    @Test
    void testAddEmptyInternalArray() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.add(3);
        Comparable<Integer>[] array = getArray(heap);
        assertThat(array[0], is(3));
    }

    @Test
    void testUpdateDecrease() {
        IPriorityQueue<IntPair> heap = this.makeInstance();
        for (int i = 1; i <= 5; i++) {
            heap.add(new IntPair(i, i));
        }

        heap.replace(new IntPair(2, 2), new IntPair(0, 0));

        assertThat(heap.removeMin(), is(new IntPair(0, 0)));
        assertThat(heap.removeMin(), is(new IntPair(1, 1)));
    }

    @Test
    void testUpdateIncrease() {
        IntPair[] values = IntPair.createArray(new int[][]{{0, 0}, {2, 2}, {4, 4}, {6, 6}, {8, 8}});
        IPriorityQueue<IntPair> heap = this.makeInstance();

        for (IntPair value : values) {
            heap.add(value);
        }

        IntPair newValue = new IntPair(5, 5);
        heap.replace(values[0], newValue);

        assertThat(heap.removeMin(), is(values[1]));
        assertThat(heap.removeMin(), is(values[2]));
        assertThat(heap.removeMin(), is(newValue));
    }

}