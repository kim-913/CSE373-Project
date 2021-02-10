package datastructures.lists;

import datastructures.lists.DoubleLinkedList.Node;
import misc.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static datastructures.lists.TestDoubleLinkedList.makeBasicList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class should contain all the tests you implement to verify that your `delete` method behaves
 * as specified. Each test should run quickly; if your tests take longer than 1 second to run, they
 * may get timed out on the runners and during grading.
 */
@Tag("project1")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TestDoubleLinkedListDelete extends BaseTest {

    @Test
    void testDeleteBasic() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(i);
        }
        Node<Integer> front = ((DoubleLinkedList<Integer>) list).front;
        Node<Integer> back = ((DoubleLinkedList<Integer>) list).back;

        int nine = list.delete(9);
        assertThat(list.size(), is(10));
        assertThat(nine, is(9));
        assertThat(back.prev.data, is(8));
        assertThat(back.prev.next.data, is(10));


        int seven = list.delete(7);
        assertThat(list.size(), is(9));
        assertThat(seven, is(7));
        assertThat(back.prev.prev.data, is(6));
        assertThat(back.prev.prev.next.data, is(8));
        assertThat(list.toString(), is("[0, 1, 2, 3, 4, 5, 6, 8, 10]"));
    }

    @Test
    void testDeleteWithMutators() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(i);
        }

        int five = list.delete(5);
        assertThat(list.toString(), is("[0, 1, 2, 3, 4, 6, 7, 8, 9, 10]"));
        list.insert(5, 50);
        assertThat(list.toString(), is("[0, 1, 2, 3, 4, 50, 6, 7, 8, 9, 10]"));
        int fifty = list.delete(5);
        assertThat(list.size(), is(10));
        assertThat(list.toString(), is("[0, 1, 2, 3, 4, 6, 7, 8, 9, 10]"));
        assertThat(fifty, is(50));

        list.set(4, 40);
        assertThat(list.toString(), is("[0, 1, 2, 3, 40, 6, 7, 8, 9, 10]"));
        int fourty = list.delete(4);
        assertThat(list.size(), is(9));
        assertThat(fourty, is(40));
        assertThat(list.toString(), is("[0, 1, 2, 3, 6, 7, 8, 9, 10]"));
    }

    @Test
    void testDeleteSameIndex() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(i);
        }
        Node<Integer> front = ((DoubleLinkedList<Integer>) list).front;
        int two = list.delete(2);
        assertThat(list.size(), is(10));
        assertThat(two, is(2));
        int three = list.delete(2);
        assertThat(list.size(), is(9));
        assertThat(three, is(3));
        int four = list.delete(2);
        assertThat(list.size(), is(8));
        assertThat(four, is(4));
        int five = list.delete(2);
        assertThat(list.size(), is(7));
        assertThat(five, is(5));
        assertThat(list.toString(), is("[0, 1, 6, 7, 8, 9, 10]"));
        assertThat(front.next.next.data, is(6));
        assertThat(front.next.next.prev.data, is(1));
    }

    @Test
    void edgeCase() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(i);
        }
        int ten = list.delete(10);
        Node<Integer> back = ((DoubleLinkedList<Integer>) list).back;
        assertThat(ten, is(10));
        assertThat(back.data, is(9));
        assertThat(back.prev.data, is(8));
        assertThat(back.prev.next.data, is(9));
        assertThat(back.next, is(nullValue()));

        int zero = list.delete(0);
        Node<Integer> front = ((DoubleLinkedList<Integer>) list).front;
        assertThat(zero, is(0));
        assertThat(front.data, is(1));
        assertThat(front.next.data, is(2));
        assertThat(front.next.prev.data, is(1));
        assertThat(front.prev, is(nullValue()));
    }

    @Test
    void emptyCase() {
        IList<Integer> list = new DoubleLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> { list.delete(0); });
        list.add(0);
        list.delete(0);
        assertThrows(IndexOutOfBoundsException.class, () -> {list.delete(0); });
    }

    @Test
    void invalidInput() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(i);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> {list.delete(11); });
        assertThrows(IndexOutOfBoundsException.class, () -> {list.delete(-1); });
    }
    @Test
    void testExample() {
        /*
        Feel free to modify or delete this dummy test.

        Below is an example of using casting, so that Java lets us access
        the specific fields of DoubleLinkedList. If you wish, you may
        use this syntax to access the internal pointers within
        DoubleLinkedList objects. Being able to check the internal pointers
        will more easily let you be thorough in your tests. For reference, our
        secret tests will be checking that the internal pointers are correct to more
        easily check your solution.
         */
        IList<String> list = makeBasicList();

        Node<String> front = ((DoubleLinkedList<String>) list).front;
        Node<String> back = ((DoubleLinkedList<String>) list).back;
        assertThat(front.next, is(back.prev));
        assertThat(front.prev, is(nullValue()));
        assertThat(back.next, is(nullValue()));
    }
}
