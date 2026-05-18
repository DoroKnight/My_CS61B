import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

     // Below, you'll write your own tests for LinkedListDeque61B.
     @Test
     public void addFirstFromEmptyTest() {
         // Initialize an empty deque (初始化一个空的双端队列)
         Deque61B<Integer> deque = new LinkedListDeque61B<>();
         // Add element to the front of the empty deque (向空队列头部添加元素)
         deque.addFirst(10);
         // Assert the deque contains exactly the added element (断言队列仅包含该添加的元素)
         assertThat(deque.toList()).containsExactly(10).inOrder();
     }

    @Test
    public void addLastFromEmptyTest() {
        // Initialize an empty deque (初始化一个空的双端队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        // Add element to the back of the empty deque (向空队列尾部添加元素)
        deque.addLast(20);
        // Assert the deque contains exactly the added element (断言队列仅包含该添加的元素)
        assertThat(deque.toList()).containsExactly(20).inOrder();
    }

    @Test
    public void addFirstNonemptyTest() {
        // Initialize and populate a deque (初始化并填充双端队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(10);
        // Add element to the front of the non-empty deque (向非空队列头部添加元素)
        deque.addFirst(5);
        // Verify the order of elements (验证元素顺序)
        assertThat(deque.toList()).containsExactly(5, 10).inOrder();
    }

    @Test
    public void addLastNonemptyTest() {
        // Initialize and populate a deque (初始化并填充双端队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addFirst(10);
        // Add element to the back of the non-empty deque (向非空队列尾部添加元素)
        deque.addLast(15);
        // Verify the order of elements (验证元素顺序)
        assertThat(deque.toList()).containsExactly(10, 15).inOrder();
    }

    // --- Flags for add after remove tests ---

    @Test
    public void addFirstAfterRemoveToEmptyTest() {
        // Create a deque and add elements (创建双端队列并添加元素)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addFirst(10);
        deque.addLast(20);
        // Remove all elements to empty the deque (移除所有元素使队列为空)
        deque.removeFirst();
        deque.removeLast();
        // Add an element to the front after emptying (清空后在头部添加元素)
        deque.addFirst(30);
        // Verify the state (验证状态)
        assertThat(deque.toList()).containsExactly(30).inOrder();
    }

    @Test
    public void addLastAfterRemoveToEmptyTest() {
        // Create a deque and add elements (创建双端队列并添加元素)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addFirst(10);
        // Remove the only element (移除唯一元素)
        deque.removeFirst();
        // Add an element to the back after emptying (清空后在尾部添加元素)
        deque.addLast(40);
        // Verify the state (验证状态)
        assertThat(deque.toList()).containsExactly(40).inOrder();
    }

    // --- Flags for remove tests ---

    @Test
    public void removeFirstTest() {
        // Populate the deque with multiple elements (使用多个元素填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        // Remove the first element and store the return value (移除头部元素并存储返回值)
        Integer removed = deque.removeFirst();
        // Verify the return value and the remaining elements (验证返回值及剩余元素)
        assertThat(removed).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(2).inOrder();
    }

    @Test
    public void removeLastTest() {
        // Populate the deque with multiple elements (使用多个元素填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        // Remove the last element and store the return value (移除尾部元素并存储返回值)
        Integer removed = deque.removeLast();
        // Verify the return value and the remaining elements (验证返回值及剩余元素)
        assertThat(removed).isEqualTo(2);
        assertThat(deque.toList()).containsExactly(1).inOrder();
    }

    @Test
    public void removeFirstToEmptyTest() {
        // Add elements to the deque (向队列添加元素)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        // Remove elements until one remains (移除元素直到仅剩一个)
        deque.removeFirst();
        // Remove the final element to trigger empty state (移除最后的元素触发空状态)
        Integer removed = deque.removeFirst();
        // Verify the state is completely empty (验证状态完全为空)
        assertThat(removed).isEqualTo(2);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void removeLastToEmptyTest() {
        // Add elements to the deque (向队列添加元素)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        // Remove elements until one remains (移除元素直到仅剩一个)
        deque.removeLast();
        // Remove the final element to trigger empty state (移除最后的元素触发空状态)
        Integer removed = deque.removeLast();
        // Verify the state is completely empty (验证状态完全为空)
        assertThat(removed).isEqualTo(1);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void removeFirstToOneTest() {
        // Initialize deque with three elements (初始化包含三个元素的队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        // Remove the first element so two remain (移除第一个元素使得剩余两个)
        deque.removeFirst();
        // Remove another element so exactly one remains (再次移除使得正好剩余一个)
        Integer removed = deque.removeFirst();
        // Verify return value and final state (验证返回值和最终状态)
        assertThat(removed).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(3).inOrder();
    }

    @Test
    public void removeLastToOneTest() {
        // Initialize deque with three elements (初始化包含三个元素的队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        // Remove the last element so two remain (移除最后一个元素使得剩余两个)
        deque.removeLast();
        // Remove another element so exactly one remains (再次移除使得正好剩余一个)
        Integer removed = deque.removeLast();
        // Verify return value and final state (验证返回值和最终状态)
        assertThat(removed).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(1).inOrder();
    }

    // --- Flags for get tests ---

    @Test
    public void getValidTest() {
        // Initialize and populate deque (初始化并填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);
        // Retrieve valid element at index 1 (在索引1处检索有效元素)
        Integer item = deque.get(1);
        // Verify the retrieved item (验证检索到的元素)
        assertThat(item).isEqualTo(20);
    }

    @Test
    public void getOobLargeTest() {
        // Initialize and populate deque (初始化并填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(10);
        // Retrieve element with an Out of Bounds index (使用越界索引检索元素)
        Integer item = deque.get(5);
        // Verify the result is null (验证结果为 null)
        assertThat(item).isNull();
    }

    @Test
    public void getOobNegTest() {
        // Initialize and populate deque (初始化并填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(10);
        // Retrieve element with a negative index (使用负索引检索元素)
        Integer item = deque.get(-1);
        // Verify the result is null (验证结果为 null)
        assertThat(item).isNull();
    }

    @Test
    public void getRecursiveValidTest() {
        // Initialize and populate deque (初始化并填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);
        // Retrieve valid element recursively at index 2 (在索引2处递归检索有效元素)
        Integer item = deque.getRecursive(2);
        // Verify the retrieved item (验证检索到的元素)
        assertThat(item).isEqualTo(30);
    }

    @Test
    public void getRecursiveOobLargeTest() {
        // Initialize and populate deque (初始化并填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(10);
        // Retrieve element recursively with an Out of Bounds index (使用越界索引递归检索元素)
        Integer item = deque.getRecursive(5);
        // Verify the result is null (验证结果为 null)
        assertThat(item).isNull();
    }

    @Test
    public void getRecursiveOobNegTest() {
        // Initialize and populate deque (初始化并填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(10);
        // Retrieve element recursively with a negative index (使用负索引递归检索元素)
        Integer item = deque.getRecursive(-1);
        // Verify the result is null (验证结果为 null)
        assertThat(item).isNull();
    }

    // --- Flags for size tests ---

    @Test
    public void sizeTest() {
        // Initialize an empty deque (初始化空队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        // Add multiple elements (添加多个元素)
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        // Assert the size matches the number of elements added (断言大小与添加的元素数量匹配)
        assertThat(deque.size()).isEqualTo(3);
    }

    @Test
    public void sizeAfterRemoveToEmptyTest() {
        // Add elements to the deque (向队列添加元素)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        // Remove all elements (移除所有元素)
        deque.removeFirst();
        deque.removeLast();
        // Verify the size is reset to 0 (验证大小已重置为 0)
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void sizeAfterRemoveFromEmptyTest() {
        // Initialize an empty deque (初始化空队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        // Attempt to remove an element from empty deque (尝试从空队列移除元素)
        deque.removeFirst();
        // Verify the size remains 0 (验证大小保持为 0)
        assertThat(deque.size()).isEqualTo(0);
    }

    // --- Flags for isEmpty tests ---

    @Test
    public void isEmptyTrueTest() {
        // Initialize an empty deque (初始化空队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        // Assert that the deque is empty (断言队列为空)
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void isEmptyFalseTest() {
        // Initialize and populate deque (初始化并填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        // Assert that the deque is not empty (断言队列非空)
        assertThat(deque.isEmpty()).isFalse();
    }

    // --- Flags for toList tests ---

    @Test
    public void toListEmptyTest() {
        // Initialize an empty deque (初始化空队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        // Verify toList yields an empty Java List (验证 toList 生成一个空的 Java 列表)
        assertThat(deque.toList()).isEmpty();
    }

    @Test
    public void toListNonemptyTest() {
        // Initialize and populate deque (初始化并填充队列)
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(10);
        deque.addLast(20);
        // Verify toList yields an accurate Java List (验证 toList 生成精确的 Java 列表)
        assertThat(deque.toList()).containsExactly(10, 20).inOrder();
    }
}