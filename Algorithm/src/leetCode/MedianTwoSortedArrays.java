package leetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * <p>
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * @author tracy.
 * @create 2017-10-30 17:14
 **/
class Solution3 {
    double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //归并排序
        int lengthOne = nums1.length;
        int lengthTwo = nums2.length;
        int oneIndex = 0;
        int twoIndex = 0;
        int totalIndex = 0;
        int finalArray[] = new int[lengthOne + lengthTwo];
        while (oneIndex < lengthOne && twoIndex < lengthTwo) {
            if (nums1[oneIndex] < nums2[twoIndex]) {
                finalArray[totalIndex++] = nums1[oneIndex++];
            } else {
                finalArray[totalIndex++] = nums2[twoIndex++];
            }
        }
        while (oneIndex < lengthOne) {
            finalArray[totalIndex++] = nums1[oneIndex++];
        }
        while (twoIndex < lengthTwo) {
            finalArray[totalIndex++] = nums2[twoIndex++];
        }
        if (totalIndex % 2 == 0) {
            return (finalArray[totalIndex / 2 - 1] + finalArray[totalIndex / 2]) / 2d;
        } else {
            return finalArray[totalIndex / 2] * 1.0;
        }
    }
}

public class MedianTwoSortedArrays {
    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for (int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            int[] nums1 = stringToIntegerArray(line);
            line = in.readLine();
            int[] nums2 = stringToIntegerArray(line);

            double ret = new Solution3().findMedianSortedArrays(nums1, nums2);

            String out = String.valueOf(ret);

            System.out.print(out);
        }
    }
}
