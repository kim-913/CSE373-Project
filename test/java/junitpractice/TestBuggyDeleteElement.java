package junitpractice;

import misc.BaseTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestBuggyDeleteElement extends BaseTest {
    /**
     * In this test, you should create a new `BuggyIntList` and choose an element to delete from the
     * middle of the linked list. Your tests should check whether  the deletion is actually
     * successful by examining the linked list after attempting to delete.
     *
     * This is an example of verifying behavior for normal/"happy" cases. Our buggy implementation
     * does handle this case correctly.
     */
    @Test
    void testDeleteFromMiddle() {
        BuggyIntList list = new BuggyIntList(new int[]{1, 2, 3});
        list.deleteElement(2);
        assertThat(list.toString(), is("[<1>, <3>]"));
    }

    /**
     * In this test, you should use `deleteElement` to attempt to delete the first element from a
     * linked list. Your tests should check whether the deletion is actually successful by examining
     * the linked list after attempting to delete.
     *
     * This is an example of verifying behavior for edge cases. Particularly for linked lists, front
     * cases are usually easy to miss.
     */
    @Test
    void testDeleteFromFront() {
        BuggyIntList list = new BuggyIntList(new int[]{1, 2, 3});
        list.deleteElement(1);
        assertThat(list.toString(), is("[<2>, <3>]"));
    }

    /**
     * The method comment of `deleteElement` claims that if you try to delete an element that is not
     * in the list, it will throw an `IllegalArgumentException`. The goal of this test is to check
     * this behavior.
     */
    @Test
    void testThrowsIllegalArgumentException() {
        BuggyIntList list = new BuggyIntList(new int[]{1, 2, 3});
        assertThrows(IllegalMonitorStateException.class, () -> list.deleteElement(5));
    }
}
