package sort;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 堆排序
 *
 * @author tracy.
 * @create 2018-02-07 10:46
 **/
public class HeapSort<V> {
    final class TreeNode<T> {
        private TreeNode<T> left = null;
        private TreeNode<T> right = null;
        private TreeNode<T> father = null;
        private T value;

        TreeNode(TreeNode<T> father) {
            this.father = father;
        }

        T getNodeValue() {
            return value;
        }

        void setNodeValue(T value) {
            this.value = value;
        }

        TreeNode<T> getLeftChild() {
            return left;
        }

        void setLeftChild(TreeNode<T> left) {
            this.left = left;
        }

        TreeNode<T> getRightChild() {
            return right;
        }

        void setRightChild(TreeNode<T> right) {
            this.right = right;
        }

    }

    private TreeNode<V> headNode;

    public void createTree(V values[]) {
        TreeNode<V> head = new TreeNode<>(null);
        Queue<TreeNode<V>> queue = new LinkedBlockingDeque<>();
        queue.offer(head);
        TreeNode<V> prev = null;
        for (V item : values) {
            //本身为空(首次)或者右节点不为空(孩子已满)
            if (prev == null || prev.getRightChild() != null) {
                prev = queue.poll();
            }
            TreeNode<V> currentNode = new TreeNode<>(prev);
            currentNode.setNodeValue(item);
            if (prev.getLeftChild() == null) {
                prev.setLeftChild(currentNode);
            } else if (prev.getRightChild() == null) {
                prev.setRightChild(currentNode);
            }
            queue.offer(currentNode);
        }
        headNode = head;
    }

    public void printTree() {
        StringBuilder builder = new StringBuilder("Current values: ");
        Queue<TreeNode<V>> queue = new LinkedBlockingDeque<>();
        TreeNode<V> currentNode = headNode;
        do {
            if (currentNode.getNodeValue() != null) {
                builder.append(currentNode.getNodeValue());
                builder.append(" ");
            }
            if (currentNode.getLeftChild() != null && currentNode.getLeftChild().getNodeValue() != null) {
                queue.offer(currentNode.getLeftChild());
            }
            if (currentNode.getRightChild() != null && currentNode.getRightChild().getNodeValue() != null) {
                queue.offer(currentNode.getRightChild());
            }
            currentNode = queue.poll();
        } while (currentNode != null);
        System.out.print(builder.toString());
    }

    public static void main(String[] args) {
        HeapSort<Integer> heapSort = new HeapSort<>();
        heapSort.createTree(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        heapSort.printTree();
    }
}
