import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
// ==========================================================
    // Add Tests
    // ==========================================================

    @Test
    public void add_first_from_empty() {
        // Initialize an empty deque
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(10);
        // Verify that the element is correctly placed at the front
        assertThat(ad.toList()).containsExactly(10).inOrder();
    }

    @Test
    public void add_last_from_empty() {
        // Initialize an empty deque
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10);
        // Verify that the element is correctly placed at the back
        assertThat(ad.toList()).containsExactly(10).inOrder();
    }

    @Test
    public void add_first_nonempty() {
        // Set up a non-empty deque
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10);
        ad.addFirst(5);
        // Check structural integrity after adding to front
        assertThat(ad.toList()).containsExactly(5, 10).inOrder();
    }

    @Test
    public void add_last_nonempty() {
        // Set up a non-empty deque
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(10);
        ad.addLast(15);
        // Check structural integrity after adding to back
        assertThat(ad.toList()).containsExactly(10, 15).inOrder();
    }

    @Test
    public void add_first_trigger_resize() {
        // Initialize deque and trigger a resize operation via addFirst
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 10; i++) {
            ad.addFirst(i);
        }
        // Verify size and order post-resizing
        assertThat(ad.size()).isEqualTo(10);
        assertThat(ad.toList()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).inOrder();
    }

    @Test
    public void add_last_trigger_resize() {
        // Initialize deque and trigger a resize operation via addLast
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }
        // Verify size and order post-resizing
        assertThat(ad.size()).isEqualTo(10);
        assertThat(ad.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    // ==========================================================
    // Add After Remove Tests
    // ==========================================================

    @Test
    public void add_first_after_remove_to_empty() {
        // Populate and completely empty the deque
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.removeFirst();
        ad.addFirst(2);
        // Ensure adding to front works correctly after the clear
        assertThat(ad.toList()).containsExactly(2).inOrder();
    }

    @Test
    public void add_last_after_remove_to_empty() {
        // Populate and completely empty the deque
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.removeFirst();
        ad.addLast(2);
        // Ensure adding to back works correctly after the clear
        assertThat(ad.toList()).containsExactly(2).inOrder();
    }

    // ==========================================================
    // Remove Tests
    // ==========================================================

    @Test
    public void remove_first() {
        // Test basic removal from the front
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        int removed = ad.removeFirst();
        // Assert returned value and remaining structure
        assertThat(removed).isEqualTo(1);
        assertThat(ad.toList()).containsExactly(2).inOrder();
    }

    @Test
    public void remove_last() {
        // Test basic removal from the back
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        int removed = ad.removeLast();
        // Assert returned value and remaining structure
        assertThat(removed).isEqualTo(2);
        assertThat(ad.toList()).containsExactly(1).inOrder();
    }

    @Test
    public void remove_first_to_empty() {
        // Continuously remove from front until deque is empty
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.removeFirst();
        ad.removeFirst();
        // Assert empty state
        assertThat(ad.isEmpty()).isTrue();
        assertThat(ad.size()).isEqualTo(0);
    }

    @Test
    public void remove_last_to_empty() {
        // Continuously remove from back until deque is empty
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.removeLast();
        ad.removeLast();
        // Assert empty state
        assertThat(ad.isEmpty()).isTrue();
        assertThat(ad.size()).isEqualTo(0);
    }

    @Test
    public void remove_first_to_one() {
        // Remove from front until a single element remains
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.removeFirst();
        ad.removeFirst();
        // Assert single element remains
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.get(0)).isEqualTo(3);
    }

    @Test
    public void remove_last_to_one() {
        // Remove from back until a single element remains
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.removeLast();
        ad.removeLast();
        // Assert single element remains
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.get(0)).isEqualTo(1);
    }

    @Test
    public void remove_first_trigger_resize() {
        // Trigger array scale-down via removeFirst when usage drops below 25%
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 16; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < 13; i++) {
            ad.removeFirst();
        }
        // Assert state after downsizing
        assertThat(ad.size()).isEqualTo(3);
        assertThat(ad.toList()).containsExactly(13, 14, 15).inOrder();
    }

    @Test
    public void remove_last_trigger_resize() {
        // Trigger array scale-down via removeLast when usage drops below 25%
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 16; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < 13; i++) {
            ad.removeLast();
        }
        // Assert state after downsizing
        assertThat(ad.size()).isEqualTo(3);
        assertThat(ad.toList()).containsExactly(0, 1, 2).inOrder();
    }

    // ==========================================================
    // Get Tests
    // ==========================================================

    @Test
    public void get_valid() {
        // Request an element using a valid index
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10);
        ad.addLast(20);
        // Assert correct element returned
        assertThat(ad.get(1)).isEqualTo(20);
    }

    @Test
    public void get_oob_large() {
        // Request an element using an out-of-bounds large index
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10);
        // Assert null is returned safely
        assertThat(ad.get(5)).isNull();
    }

    @Test
    public void get_oob_neg() {
        // Request an element using a negative index
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10);
        // Assert null is returned safely
        assertThat(ad.get(-1)).isNull();
    }

    // ==========================================================
    // Size Tests
    // ==========================================================

    @Test
    public void size() {
        // Track the size variable during modification
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.size()).isEqualTo(0);
        ad.addLast(1);
        assertThat(ad.size()).isEqualTo(1);
        ad.addLast(2);
        assertThat(ad.size()).isEqualTo(2);
    }

    @Test
    public void size_after_remove_to_empty() {
        // Assert size handles complete removal gracefully
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.removeFirst();
        assertThat(ad.size()).isEqualTo(0);
    }

    @Test
    public void size_after_remove_from_empty() {
        // Assert size does not become negative when removing from zero elements
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.removeFirst();
        assertThat(ad.size()).isEqualTo(0);
    }

    // ==========================================================
    // isEmpty Tests
    // ==========================================================

    @Test
    public void is_empty_true() {
        // Evaluate condition on zero elements
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.isEmpty()).isTrue();
    }

    @Test
    public void is_empty_false() {
        // Evaluate condition on non-zero elements
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        assertThat(ad.isEmpty()).isFalse();
    }

    // ==========================================================
    // toList Tests
    // ==========================================================

    @Test
    public void to_list_empty() {
        // Convert empty deque to list
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.toList()).isEmpty();
    }

    @Test
    public void to_list_nonempty() {
        // Convert populated deque to list maintaining order
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        assertThat(ad.toList()).containsExactly(1, 2).inOrder();
    }

    // ==========================================================
    // Advanced Resize Tests
    // ==========================================================

    @Test
    public void resize_up_and_resize_down() {
        // Execute a stress test to trigger scaling up and then scaling down
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // Scale up phase
        for (int i = 0; i < 32; i++) {
            ad.addLast(i);
        }
        assertThat(ad.size()).isEqualTo(32);

        // Scale down phase
        for (int i = 0; i < 30; i++) {
            ad.removeFirst();
        }
        assertThat(ad.size()).isEqualTo(2);
        assertThat(ad.toList()).containsExactly(30, 31).inOrder();
    }

    /**
     * Tests for Task 10: iterator()
     */
    @Test
    public void testIterator() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        // Test empty iterator behavior
        Iterator<String> iter = ad.iterator();
        assertThat(iter.hasNext()).isFalse();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        // Verify enhanced for-loop compilation and Truth library integration
        assertThat(ad).containsExactly("front", "middle", "back").inOrder();

        // Test multiple independent iterators simultaneously
        Iterator<String> iter1 = ad.iterator();
        Iterator<String> iter2 = ad.iterator();

        assertThat(iter1.next()).isEqualTo("front");
        assertThat(iter2.next()).isEqualTo("front");
        assertThat(iter1.next()).isEqualTo("middle");
        assertThat(iter2.hasNext()).isTrue();
    }

    /**
     * Tests for Task 11: equals(Object obj)
     */
    @Test
    public void testEquals() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        // Test equality of two empty deques
        assertThat(ad1).isEqualTo(ad2);

        // Test reflexivity property
        assertThat(ad1).isEqualTo(ad1);

        // Test negative cases against null and disparate class types
        assertThat(ad1).isNotEqualTo(null);
        assertThat(ad1).isNotEqualTo("This is a String, not a Deque");

        ad1.addLast("a");
        ad1.addLast("b");
        ad2.addLast("a");
        ad2.addLast("b");

        // Test equality with identical elements and ordering
        assertThat(ad1).isEqualTo(ad2);

        // Test inequality due to size mismatch
        ad2.addLast("c");
        assertThat(ad1).isNotEqualTo(ad2);

        // Test inequality due to sequence order variation
        Deque61B<String> ad3 = new ArrayDeque61B<>();
        ad3.addLast("b");
        ad3.addLast("a");
        assertThat(ad1).isNotEqualTo(ad3);
    }

    /**
     * Tests for Task 12: toString()
     */
    @Test
    public void testToString() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        // Test string representation of an empty deque
        assertThat(ad.toString()).isEqualTo("[]");

        ad.addLast("front");

        // Test string representation with a single element
        assertThat(ad.toString()).isEqualTo("[front]");

        ad.addLast("middle");
        ad.addLast("back");

        // Test string representation with multiple elements and correct delimiters
        assertThat(ad.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        assertThat(ad).isEqualTo(ad2);
    }
}
