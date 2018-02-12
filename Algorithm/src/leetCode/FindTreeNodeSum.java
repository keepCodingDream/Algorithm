package leetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 给定一个数n，查找树中是否有一个路径之后等于n。
 * 例如:
 * <p>
 * 1
 * 2  3
 * 4  5  6  7
 * <p>
 * 这颗树，求连续路径上和等于10的所有路径
 * <p>
 * input:
 * 1 2 3 4 5 6 7 10
 * output:
 * 1 3 6
 * 3 7
 *
 * @author tracy.
 * @create 2018-02-11 10:01
 **/
public class FindTreeNodeSum {
    static class TreeNode {
        private TreeNode left;
        private TreeNode right;
        private int value;

        public TreeNode(int value) {
            this.value = value;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /**
     * @param head  当前遍历的树节点
     * @param value 目标和值
     * @param road  累计的路径
     */
    static void printRoads(TreeNode head, int value, Stack<Integer> road) {
        int currentVal = head.getValue();
        //whatever enter the value
        road.push(currentVal);
        //这里和普通的递归查找不一样，即使找到了要求中的路径，我们也要继续找下去,因为这条路可能有多个解
        //递归结束的唯一原因只能是遍历完了整颗树，不存在剪枝的情况
        Integer[] newStack = new Integer[road.size()];
        newStack = road.toArray(newStack);
        int sum = 0;
        for (int i = newStack.length - 1; i >= 0; i--) {
            sum += newStack[i];
            if (sum == value) {
                printResult(newStack, i, newStack.length - 1);
            }
        }
        TreeNode left = head.getLeft();
        TreeNode right = head.getRight();
        if (left != null) {
            printRoads(left, value, road);
            //when back just reload
            road.pop();
        }
        if (right != null) {
            printRoads(right, value, road);
            //when back just reload
            road.pop();
        }
    }

    static void printResult(Integer[] newStack, int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.print(newStack[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            String[] input = line.split(" ");
            Queue<String> inputQueue = new LinkedBlockingDeque<>(Arrays.asList(input));
            TreeNode headNode = new TreeNode(Integer.valueOf(inputQueue.poll()));
            Queue<TreeNode> treeNodeQueue = new LinkedBlockingDeque<>();
            treeNodeQueue.offer(headNode);
            TreeNode currentNode = headNode;
            int size = input.length - 1;
            int index = 0;
            int sum = 0;
            do {
                TreeNode left = currentNode.getLeft();
                TreeNode right = currentNode.getRight();
                if (left == null) {
                    String inputItem = inputQueue.poll();
                    if (++index == size) {
                        sum = Integer.valueOf(inputItem);
                        break;
                    }
                    if (inputItem == null) {
                        break;
                    } else {
                        TreeNode treeNode = new TreeNode(Integer.valueOf(inputItem));
                        currentNode.setLeft(treeNode);
                        treeNodeQueue.offer(treeNode);
                    }
                }
                if (right == null) {
                    String inputItem = inputQueue.poll();
                    if (++index == size) {
                        sum = Integer.valueOf(inputItem);
                        break;
                    }
                    if (inputItem == null) {
                        break;
                    } else {
                        TreeNode treeNode = new TreeNode(Integer.valueOf(inputItem));
                        currentNode.setRight(treeNode);
                        treeNodeQueue.offer(treeNode);
                    }
                }
                currentNode = treeNodeQueue.poll();
            } while (currentNode != null);
            printRoads(headNode, sum, new Stack<>());
        }
    }
}
