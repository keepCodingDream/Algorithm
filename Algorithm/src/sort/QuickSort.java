package sort;

import java.util.Random;

public class QuickSort {
  public static void main(String[] args) {
    int a[] = new int[10];
    Random random = new Random();
    random.setSeed(System.currentTimeMillis());
    for (int i = 0; i < a.length; i++) {
      a[i] = random.nextInt(100);
    }
    System.out.println("before data:");
    print(a);
    quickSort(a, 0, a.length);
    System.out.println("after data:");
    print(a);
  }

  public static void quickSort(int a[], int start, int end) {
    int length = end - start;
    if (length <= 1) {
      return;
    }
    int mid = (start + end) / 2;
    int left[] = new int[length];
    int right[] = new int[length];
    int leftIndex = 0;
    int rightIndex = 0;
    int midValue = a[mid];
    for (int i = start; i < end; i++) {
      if (i != mid) {
        if (a[i] < midValue) {
          left[leftIndex++] = a[i];
        } else {
          right[rightIndex++] = a[i];
        }
      }

    }
    int indexA = start;
    for (int i = 0; i < leftIndex; i++) {
      a[indexA++] = left[i];
    }
    a[indexA++] = midValue;
    for (int i = 0; i < rightIndex; i++) {
      a[indexA++] = right[i];
    }
    quickSort(a, start, start + leftIndex);
    quickSort(a, start + leftIndex + 1, end);
  }

  public static void print(int a[]) {
    for (int i = 0; i < a.length; i++) {
      System.out.println(a[i]);
    }
  }
}
