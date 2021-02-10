package problems;

import datastructures.IntTree;
// IntelliJ will complain that this is an unused import, but you should use `IntTreeNode` variables
// in your solution, and then this error should go away.

/**
 * Parts b.ix, through b.xi should go here.
 *
 * (Implement `depthSum`, `removeLeaves`, and `trim` as described by the spec.
 * See the spec on the website for picture examples and more explanation!)
 *
 * Also note: you may want to use private helper methods to help you solve these problems.
 * YOU MUST MAKE THE PRIVATE HELPER METHODS STATIC, or else your code will not compile.
 * This happens for reasons that aren't the focus of this assignment and are mostly skimmed over in
 * 142 and 143. If you want to know more, you can ask on the discussion board or at office hours.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not construct new `IntTreeNode` objects (though you may have as many `IntTreeNode` variables
 *      as you like).
 * - do not call any `IntTree` methods
 * - do not construct any external data structures like arrays, queues, lists, etc.
 * - do not mutate the `data` field of any nodes (the `data` field of `IntTreeNode` is final, so you
 *      cannot if you try)
 */

public class IntTreeProblems {

    public static int depthSum(IntTree tree) {
        int level = 1;
        int sum = 0;
        return depth(tree.overallRoot, level, sum);
    }

    private static int depth(IntTree.IntTreeNode current, int level, int sum) {
        if (current != null) {
            if (current.left == null && current.right == null) {
                sum += current.data * level;
            } else {
                sum += depth(current.left, level + 1, sum) +
                        depth(current.right, level + 1, sum) + current.data * level;
            }
        }
        return sum;

    }

    public static void removeLeaves(IntTree tree) {
        tree.overallRoot = remove(tree.overallRoot);
    }

    private static IntTree.IntTreeNode remove(IntTree.IntTreeNode current) {
        if (current != null) {
            if (current.left == null && current.right == null) {
                return null;
            } if (current.left != null) {
                current.left = remove(current.left);
            } if (current.right != null) {
                current.right = remove(current.right);

            }
        }
        return current;

    }

    public static void trim(IntTree tree, int min, int max) {
        tree.overallRoot = trim(tree.overallRoot, min, max);
    }

    private static IntTree.IntTreeNode trim(IntTree.IntTreeNode current, int min, int max) {
        if (current == null) {
            return null;
        } else if (current.data < min) {
            return trim(current.right, min, max);
        } else if (current.data > max) {
            return trim(current.left, min, max);
        } else {
            current.left = trim(current.left, min, max);
            current.right = trim(current.right, min, max);
            return current;
        }
    }
}
