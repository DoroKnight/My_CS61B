import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;



public class TestRedBlackTree {

    /*
     * Test class for RedBlackTree.java
     *
     * We've provided LLRB Tree representations after every operations in this file as comments to help you debug.
     *
     *
     * Black Nodes are represented with () and red nodes are represented with ()*
     * Left children are listed before right children.
     */

    /*
    Tests for a very basic case of rotating right. This does not check for color flips, but only if the nodes are in the proper
    place after rotating right. Note that we have not provided any basic tests for rotate left, but implementation details for
    rotate right and rotate left should be symmetrical.
     */
    @Test
    public void testBasicRotateRight() {
        // Insert 10, 9, 8
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        assertThat(rbtree.root).isNull();

        RedBlackTree.RBTreeNode<Integer> node1 = new RedBlackTree.RBTreeNode<>(true, 10, null, null);
        RedBlackTree.RBTreeNode<Integer> node2 = new RedBlackTree.RBTreeNode<>(false, 9, null, null);
        RedBlackTree.RBTreeNode<Integer> node3 = new RedBlackTree.RBTreeNode<>(false, 8, null, null);
        node1.left = node2;
        node2.left = node3;

        RedBlackTree.RBTreeNode<Integer> newRoot = rbtree.rotateRight(node1);
        assertThat(newRoot.item).isEqualTo(9);
        assertThat(newRoot.right.item).isEqualTo(10);
        assertThat(newRoot.left.item).isEqualTo(8);
    }

    @Test
    public void testInsertSimple() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();

        /*
        LLRB Tree representation:

         */
        assertThat(rbtree.root).isNull();


        rbtree.insert(10);
        
        /*
        LLRB Tree representation:
           (10)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(10);

        // left
        assertThat(rbtree.root.left).isNull();

        // right
        assertThat(rbtree.root.right).isNull();

        rbtree.insert(5);

        /*
        LLRB Tree representation:
            (10)
            └── (5)*

         */


        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(10);

        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isFalse();
        assertThat(rbtree.root.left.item).isEqualTo(5);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();

        // right
        assertThat(rbtree.root.right).isNull();

        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 5) in order").that(callsToFlipColors).isEqualTo(0);
        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 5) in order").that(callsToRotateLeft).isEqualTo(0);
        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 5) in order").that(callsToRotateRight).isEqualTo(0);

    }

    @Test
    public void testInsertFlipColor() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        rbtree.insert(10);
        rbtree.insert(5);
        rbtree.insert(15);

        /*
        LLRB Tree Representation:
            (10)
            ├── (5)
            └── (15)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(10);


        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(5);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();

        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(15);

        // right.left
        assertThat(rbtree.root.right.left).isNull();

        // right.right
        assertThat(rbtree.root.right.right).isNull();

        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 5, 15) in order").that(callsToFlipColors).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Left after inserting (10, 5, 15) in order").that(callsToRotateLeft).isEqualTo(0);
        assertWithMessage("Number of Calls to Rotate Right after inserting (10, 5, 15) in order").that(callsToRotateRight).isEqualTo(0);
    }


    @Test
    public void testInsertRotateLeft() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        rbtree.insert(10);
        rbtree.insert(15);

        /*
        LLRB Tree Representation:
            (15)
            └── (10)*

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(15);


        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isFalse();
        assertThat(rbtree.root.left.item).isEqualTo(10);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();

        // right
        assertThat(rbtree.root.right).isNull();

        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 15) in order").that(callsToFlipColors).isEqualTo(0);
        assertWithMessage("Number of Calls to Rotate Left after inserting (10, 15) in order").that(callsToRotateLeft).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Right after inserting (10, 15) in order").that(callsToRotateRight).isEqualTo(0);
    }


    @Test
    public void testInsertRotateRight() {

        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        rbtree.insert(10);
        rbtree.insert(5);
        rbtree.insert(3);

        /*
        LLRB Tree Representation:
            (5)
            └── (3)
            └── (10)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(5);


        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(3);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();


        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(10);

        // right.left
        assertThat(rbtree.root.right.left).isNull();

        // right.right
        assertThat(rbtree.root.right.right).isNull();

        // Not possible to test rotate right without calling color flip in the same insert, if implemented correctly
        assertWithMessage("Number of Calls to Flip Colors after inserting (5, 3, 10) in order").that(callsToFlipColors).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Left after inserting (5, 3, 10) in order").that(callsToRotateLeft).isEqualTo(0);
        assertWithMessage("Number of Calls to Rotate Right after inserting (5, 3, 10) in order").that(callsToRotateRight).isEqualTo(1);
    }


    @Test
    public void testInsertAllFixes() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();

        rbtree.insert(10);
        rbtree.insert(5);
        rbtree.insert(7);

        /*
        LLRB Tree Representation:
            (7)
            ├── (5)
            └── (10)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(7);

        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(5);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();

        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(10);

        // right.left
        assertThat(rbtree.root.right.left).isNull();

        // right.right
        assertThat(rbtree.root.right.right).isNull();

        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 7, 5) in order").that(callsToFlipColors).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Left after inserting (10, 7, 5) in order").that(callsToRotateLeft).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Right after inserting (10, 7, 5) in order").that(callsToRotateRight).isEqualTo(1);
    }


    @Test
    public void testInsertUpwardPropagation() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();

        rbtree.insert(5);
        rbtree.insert(11);
        rbtree.insert(3);
        rbtree.insert(9);
        rbtree.insert(7);
        rbtree.insert(1);
        rbtree.insert(2);

        /*
        LLRB Tree Representation:
            (5)
            ├── (2)
            │   ├── (1)
            │   └── (3)
            └── (9)
                ├── (7)
                └── (11)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(5);

        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(2);

        // left.left
        assertThat(rbtree.root.left.left).isNotNull();
        assertThat(rbtree.root.left.left.isBlack).isTrue();
        assertThat(rbtree.root.left.left.item).isEqualTo(1);

        // left.right
        assertThat(rbtree.root.left.right).isNotNull();
        assertThat(rbtree.root.left.right.isBlack).isTrue();
        assertThat(rbtree.root.left.right.item).isEqualTo(3);

        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(9);

        // right.left
        assertThat(rbtree.root.right.left).isNotNull();
        assertThat(rbtree.root.right.left.isBlack).isTrue();
        assertThat(rbtree.root.right.left.item).isEqualTo(7);

        // right.right
        assertThat(rbtree.root.right.right).isNotNull();
        assertThat(rbtree.root.right.right.isBlack).isTrue();
        assertThat(rbtree.root.right.right.item).isEqualTo(11);

        assertWithMessage("Number of Calls to Flip Colors after inserting (5, 11, 3, 9, 7, 1, 2) in order").that(callsToFlipColors).isEqualTo(4);
        assertWithMessage("Number of Calls to Rotate Left after inserting (5, 11, 3, 9, 7, 1, 2) in order").that(callsToRotateLeft).isEqualTo(3);
        assertWithMessage("Number of Calls to Rotate Right after inserting (5, 11, 3, 9, 7, 1, 2) in order").that(callsToRotateRight).isEqualTo(4);
        
    }

    @Test
    public void testLeftMostInsertion() {
        // something new
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();

        rbtree.insert(9);
        rbtree.insert(8);
        rbtree.insert(7);
        rbtree.insert(6);
        rbtree.insert(5);
        rbtree.insert(4);
        rbtree.insert(3);
        rbtree.insert(2);
        rbtree.insert(1);

        /*
        LLRB Tree Representation:
            (6)
            ├── (4)
            │   ├── (2)*
            │   │   ├── (1)
            │   │   └── (3)
            │   └── (5)
            └── (8)
                ├── (7)
                └── (9)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(6);

        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(4);

        // left.left
        assertThat(rbtree.root.left.left).isNotNull();
        assertThat(rbtree.root.left.left.isBlack).isFalse();
        assertThat(rbtree.root.left.left.item).isEqualTo(2);

        // left.left.left
        assertThat(rbtree.root.left.left.left).isNotNull();
        assertThat(rbtree.root.left.left.left.isBlack).isTrue();
        assertThat(rbtree.root.left.left.left.item).isEqualTo(1);

        // left.left.right
        assertThat(rbtree.root.left.left.right).isNotNull();
        assertThat(rbtree.root.left.left.right.isBlack).isTrue();
        assertThat(rbtree.root.left.left.right.item).isEqualTo(3);

        // left.right
        assertThat(rbtree.root.left.right).isNotNull();
        assertThat(rbtree.root.left.right.isBlack).isTrue();
        assertThat(rbtree.root.left.right.item).isEqualTo(5);

        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(8);

        // right.left
        assertThat(rbtree.root.right.left).isNotNull();
        assertThat(rbtree.root.right.left.isBlack).isTrue();
        assertThat(rbtree.root.right.left.item).isEqualTo(7);

        // right.right
        assertThat(rbtree.root.right.right).isNotNull();
        assertThat(rbtree.root.right.right.isBlack).isTrue();
        assertThat(rbtree.root.right.right.item).isEqualTo(9);

        assertWithMessage("Number of Calls to Flip Colors after inserting (9, 8, 7, 6, 5, 4, 3, 2, 1) in order").that(callsToFlipColors).isEqualTo(5);
        assertWithMessage("Number of Calls to Rotate Left after inserting (9, 8, 7, 6, 5, 4, 3, 2, 1) in order").that(callsToRotateLeft).isEqualTo(0);
        assertWithMessage("Number of Calls to Rotate Right after inserting (9, 8, 7, 6, 5, 4, 3, 2, 1) in order").that(callsToRotateRight).isEqualTo(5);
    }

    @Test
    public void testFlipColorsActuallyFlipsAllThreeNodes() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        RedBlackTree.RBTreeNode<Integer> left = new RedBlackTree.RBTreeNode<>(false, 5);
        RedBlackTree.RBTreeNode<Integer> right = new RedBlackTree.RBTreeNode<>(false, 15);
        RedBlackTree.RBTreeNode<Integer> root = new RedBlackTree.RBTreeNode<>(true, 10, left, right);

        rbtree.flipColors(root);

        assertThat(root.isBlack).isFalse();
        assertThat(left.isBlack).isTrue();
        assertThat(right.isBlack).isTrue();
        assertThat(root.left).isSameInstanceAs(left);
        assertThat(root.right).isSameInstanceAs(right);

        rbtree.flipColors(root);

        assertThat(root.isBlack).isTrue();
        assertThat(left.isBlack).isFalse();
        assertThat(right.isBlack).isFalse();
    }

    @Test
    public void testRotateLeftPreservesSubtreesReferencesAndSwapsColors() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        RedBlackTree.RBTreeNode<Integer> node5 = new RedBlackTree.RBTreeNode<>(true, 5);
        RedBlackTree.RBTreeNode<Integer> node12 = new RedBlackTree.RBTreeNode<>(true, 12);
        RedBlackTree.RBTreeNode<Integer> node20 = new RedBlackTree.RBTreeNode<>(true, 20);
        RedBlackTree.RBTreeNode<Integer> node15 =
                new RedBlackTree.RBTreeNode<>(false, 15, node12, node20);
        RedBlackTree.RBTreeNode<Integer> node10 =
                new RedBlackTree.RBTreeNode<>(true, 10, node5, node15);

        RedBlackTree.RBTreeNode<Integer> newRoot = rbtree.rotateLeft(node10);

        assertThat(newRoot).isSameInstanceAs(node15);
        assertThat(newRoot.left).isSameInstanceAs(node10);
        assertThat(newRoot.right).isSameInstanceAs(node20);
        assertThat(node10.left).isSameInstanceAs(node5);
        assertThat(node10.right).isSameInstanceAs(node12);
        assertThat(newRoot.isBlack).isTrue();
        assertThat(node10.isBlack).isFalse();
        assertBSTOrder(newRoot);
    }

    @Test
    public void testRotateRightPreservesSubtreesReferencesAndSwapsColors() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        RedBlackTree.RBTreeNode<Integer> node5 = new RedBlackTree.RBTreeNode<>(true, 5);
        RedBlackTree.RBTreeNode<Integer> node12 = new RedBlackTree.RBTreeNode<>(true, 12);
        RedBlackTree.RBTreeNode<Integer> node20 = new RedBlackTree.RBTreeNode<>(true, 20);
        RedBlackTree.RBTreeNode<Integer> node10 =
                new RedBlackTree.RBTreeNode<>(false, 10, node5, node12);
        RedBlackTree.RBTreeNode<Integer> node15 =
                new RedBlackTree.RBTreeNode<>(true, 15, node10, node20);

        RedBlackTree.RBTreeNode<Integer> newRoot = rbtree.rotateRight(node15);

        assertThat(newRoot).isSameInstanceAs(node10);
        assertThat(newRoot.left).isSameInstanceAs(node5);
        assertThat(newRoot.right).isSameInstanceAs(node15);
        assertThat(node15.left).isSameInstanceAs(node12);
        assertThat(node15.right).isSameInstanceAs(node20);
        assertThat(newRoot.isBlack).isTrue();
        assertThat(node15.isBlack).isFalse();
        assertBSTOrder(newRoot);
    }

    @Test
    public void testDuplicateInsertDoesNotChangeTreeOrCallFixes() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();

        rbtree.insert(10);
        RedBlackTree.RBTreeNode<Integer> originalRoot = rbtree.root;
        callsToFlipColors = 0;
        callsToRotateLeft = 0;
        callsToRotateRight = 0;

        rbtree.insert(10);

        assertThat(rbtree.root).isSameInstanceAs(originalRoot);
        assertThat(rbtree.root.item).isEqualTo(10);
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.left).isNull();
        assertThat(rbtree.root.right).isNull();
        assertThat(countNodes(rbtree.root)).isEqualTo(1);
        assertThat(callsToFlipColors).isEqualTo(0);
        assertThat(callsToRotateLeft).isEqualTo(0);
        assertThat(callsToRotateRight).isEqualTo(0);
    }

    @Test
    public void testMixedInsertionsMaintainAllLLRBInvariants() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        int[] values = {30, 10, 50, 5, 20, 40, 60, 1, 7, 15, 25, 35, 45, 55, 70};

        for (int value : values) {
            rbtree.insert(value);
            assertValidLLRB(rbtree);
        }

        assertThat(countNodes(rbtree.root)).isEqualTo(values.length);
        assertContainsExactly(rbtree.root, values);
    }

    @Test
    public void testZigZagInsertionsMaintainAllLLRBInvariants() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        int[] values = {8, 16, 12, 14, 10, 4, 6, 2, 1, 3, 5, 7, 9, 11, 13, 15};

        for (int value : values) {
            rbtree.insert(value);
            assertValidLLRB(rbtree);
        }

        assertThat(countNodes(rbtree.root)).isEqualTo(values.length);
        assertContainsExactly(rbtree.root, values);
    }

    /*
     * Just super neat class to test the number of times your LLRB Tree implementation makes calls to it's
     * "fixing" operations.
     */
    class TestableRedBlackTree extends RedBlackTree<Integer> {

        @Override
        void flipColors(RBTreeNode<Integer> node) {
            callsToFlipColors++;
            super.flipColors(node);
        }

        @Override
        RBTreeNode<Integer> rotateRight(RBTreeNode<Integer> node) {
            callsToRotateRight++;
            return super.rotateRight(node);
        }

        @Override
        RBTreeNode<Integer> rotateLeft(RBTreeNode<Integer> node) {
            callsToRotateLeft++;
            return super.rotateLeft(node);
        }
        
    }

    private int callsToFlipColors = 0;
    private int callsToRotateRight = 0;
    private int callsToRotateLeft = 0;

    private void assertValidLLRB(RedBlackTree<Integer> tree) {
        assertThat(tree.root).isNotNull();
        assertWithMessage("The root of an LLRB tree must always be black.")
                .that(tree.root.isBlack).isTrue();
        assertBSTOrder(tree.root);
        assertNoRightLeaningRedLinks(tree.root);
        assertNoNodeHasTwoRedChildren(tree.root);
        assertNoConsecutiveRedNodes(tree.root);
        blackHeight(tree.root);
    }

    private void assertBSTOrder(RedBlackTree.RBTreeNode<Integer> node) {
        assertBSTOrder(node, null, null);
    }

    private void assertBSTOrder(RedBlackTree.RBTreeNode<Integer> node,
                                Integer lowerBound, Integer upperBound) {
        if (node == null) {
            return;
        }
        if (lowerBound != null) {
            assertWithMessage("BST order violation: node must be greater than its lower bound.")
                    .that(node.item).isGreaterThan(lowerBound);
        }
        if (upperBound != null) {
            assertWithMessage("BST order violation: node must be less than its upper bound.")
                    .that(node.item).isLessThan(upperBound);
        }
        assertBSTOrder(node.left, lowerBound, node.item);
        assertBSTOrder(node.right, node.item, upperBound);
    }

    private void assertNoRightLeaningRedLinks(RedBlackTree.RBTreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        assertWithMessage("LLRB violation: red links must lean left, not right.")
                .that(isRed(node.right)).isFalse();
        assertNoRightLeaningRedLinks(node.left);
        assertNoRightLeaningRedLinks(node.right);
    }

    private void assertNoNodeHasTwoRedChildren(RedBlackTree.RBTreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        assertWithMessage("LLRB violation: no node may have two red children.")
                .that(isRed(node.left) && isRed(node.right)).isFalse();
        assertNoNodeHasTwoRedChildren(node.left);
        assertNoNodeHasTwoRedChildren(node.right);
    }

    private void assertNoConsecutiveRedNodes(RedBlackTree.RBTreeNode<Integer> node) {
        if (node == null) {
            return;
        }
        if (isRed(node)) {
            assertWithMessage("LLRB violation: a red node may not have a red left child.")
                    .that(isRed(node.left)).isFalse();
            assertWithMessage("LLRB violation: a red node may not have a red right child.")
                    .that(isRed(node.right)).isFalse();
        }
        assertNoConsecutiveRedNodes(node.left);
        assertNoConsecutiveRedNodes(node.right);
    }

    private int blackHeight(RedBlackTree.RBTreeNode<Integer> node) {
        if (node == null) {
            return 1;
        }
        int leftBlackHeight = blackHeight(node.left);
        int rightBlackHeight = blackHeight(node.right);
        assertWithMessage("LLRB violation: every root-to-null path must have the same black height.")
                .that(leftBlackHeight).isEqualTo(rightBlackHeight);
        if (node.isBlack) {
            return leftBlackHeight + 1;
        }
        return leftBlackHeight;
    }

    private boolean isRed(RedBlackTree.RBTreeNode<Integer> node) {
        return node != null && !node.isBlack;
    }

    private int countNodes(RedBlackTree.RBTreeNode<Integer> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    private void assertContainsExactly(RedBlackTree.RBTreeNode<Integer> node, int[] expectedValues) {
        Set<Integer> actual = new HashSet<>();
        addItems(node, actual);
        Set<Integer> expected = new HashSet<>();
        for (int value : expectedValues) {
            expected.add(value);
        }
        assertThat(actual).containsExactlyElementsIn(expected);
    }

    private void addItems(RedBlackTree.RBTreeNode<Integer> node, Set<Integer> items) {
        if (node == null) {
            return;
        }
        items.add(node.item);
        addItems(node.left, items);
        addItems(node.right, items);
    }
}
