package problems;

import datastructures.LinkedIntList;

// IntelliJ will complain that this is an unused import, but you should use `ListNode` variables
// in your solution, and then this error should go away.

import datastructures.LinkedIntList.ListNode;

/**
 * Parts b.vi, b.vii, and b.viii should go here.
 *
 * (Implement `reverse3`, `firstLast`, and `shift` as described by the spec.
 *  See the spec on the website for picture examples and more explanation!)
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not construct new `ListNode` objects (though you may have as many `ListNode` variables as
 *      you like).
 * - do not call any `LinkedIntList` methods
 * - do not construct any external data structures like arrays, queues, lists, etc.
 * - do not mutate the `data` field of any nodes; instead, change the list only by modifying links
 *      between nodes.
 * - your solution should run in linear time with respect to the number of elements in the list.
 */

public class LinkedIntListProblems {

    // Reverses the 3 elements in the `LinkedIntList` (assume there are only 3 elements).
    public static void reverse3(LinkedIntList list) {
        ListNode temp = list.front.next.next;
        list.front.next.next = null;
        temp.next = list.front.next;
        list.front.next = null;
        temp.next.next = list.front;
        list.front = temp;
    }

    public static void shift(LinkedIntList list) {
        if (list.front != null && list.front.next != null) {
            ListNode even = list.front.next;
            list.front.next = list.front.next.next;
            ListNode current1 = list.front;
            ListNode current2 = even;
            while (current1.next != null) {
                current1 = current1.next;
                if (current1.next != null) {
                    current2.next = current1.next;
                    current1.next = current1.next.next;
                    current2 = current2.next;
                }
            }
            current2.next = null;
            current1.next = even;
        }
    }

    public static void firstLast(LinkedIntList list) {
        if (list.front != null && list.front.next != null) {
            ListNode current = list.front;
            ListNode temp = list.front;
            list.front = list.front.next;
            while (current.next != null) {
                current = current.next;
            }
            current.next = temp;
            temp.next = null;
        }

    }
}
