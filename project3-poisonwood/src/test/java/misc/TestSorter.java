package misc;

import datastructures.lists.DoubleLinkedList;
import datastructures.lists.IList;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static datastructures.lists.IListMatcher.listContaining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * See spec for details on what kinds of tests this class should include.
 */
@Tag("project3")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TestSorter extends BaseTest {
    @Test
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Sorter.topKSort(5, list);
        assertThat(top, is(listContaining(15, 16, 17, 18, 19)));
    }

    // this tests if the exceptions are correctly thrown
    @Test
    public void checkException() {
        IList<Integer> list = new DoubleLinkedList<>();
        assertThrows(IllegalArgumentException.class, () -> { Sorter.topKSort(-1, list); });
        assertThrows(IllegalArgumentException.class, () -> { Sorter.topKSort(1, null); });
        assertThrows(IllegalArgumentException.class, () -> { Sorter.topKSort(-1, null); });
    }


    // this test tests the case when k = 0. Check that the original list is not mutated.
    @Test
    public void kZero() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(10);
        list.add(7);
        list.add(9);
        list.add(20);
        list.add(2);
        IList<Integer> top = Sorter.topKSort(0, list);
        assertThat(top, is(listContaining()));
        assertThat(list, is(listContaining(10, 7, 9, 20, 2)));
    }

    // this tests the case where k is less than size or equal to size or greater than size
    // Check that the original list is not mutated.
    @Test
    public void testBasic() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(10);
        list.add(7);
        list.add(9);
        list.add(20);
        list.add(2);
        IList<Integer> top = Sorter.topKSort(3, list);
        assertThat(top, is(listContaining(9, 10, 20)));
        top = Sorter.topKSort(5, list);
        assertThat(top, is(listContaining(2, 7, 9, 10, 20)));
        top = Sorter.topKSort(10, list);
        assertThat(top, is(listContaining(2, 7, 9, 10, 20)));
        assertThat(list, is(listContaining(10, 7, 9, 20, 2)));
    }

    // this tests an emptyList
    // Check that the original list is not mutated.
    @Test
    public void emptyList() {
        IList<Integer> list = new DoubleLinkedList<>();
        IList<Integer> top = Sorter.topKSort(0, list);
        assertThat(top, is(listContaining()));
        top = Sorter.topKSort(5, list);
        assertThat(top, is(listContaining()));
        assertThat(list, is(listContaining()));
    }
}
