import java.util.*;
import java.text.NumberFormat;

import util.DListInt;

public class Sort {
    public static void main (String [] args) {
        int arraySize = 50;
        int numbers[] = new int[arraySize];

        Random rand = new Random();

        int num;
        int min = 0;
        int max = 0;
        for (int i = 0; i < arraySize; i++) {
            num = rand.nextInt(100);
            if (num < 0) {
                num *= -1;
            }

            numbers[i] = num;

            if (i == 0) {
                min = max = num;
            } else {
                if (num < min) {
                    min = num;
                }
                if (num > max) {
                    max = num;
                }
            }
        }

        long start = System.nanoTime();

        // execBubbleSort(numbers);
        // execQuickSort(numbers, 0, arraySize - 1);
        // execBucketSort(numbers, min, max);
        execHeapSort(numbers);

        long stop = System.nanoTime();

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(10);

        int div = 1000;
        String elapsed = nf.format((stop - start) / div);

        // print output
        System.out.println(Arrays.toString(numbers));

         System.out.print("total elapsed time is " + elapsed + " micro seconds.");
    }

    public static void execBubbleSort(int numbers[]) {
        int arraySize = numbers.length;

        for (int i = arraySize - 1; i >= 1; i--) {
            for (int j = arraySize - 2; j >= 0; j--) {
                if (numbers[j] > numbers[j+1]) {
                    int tmp = numbers[j+1];
                    numbers[j+1] = numbers[j];
                    numbers[j] = tmp;
                }
            }
        }

        return;
    }

    public static void execQuickSort(int numbers[], int left, int right) {
        if (left >= right) {
            return;
        }

        int arraySize = numbers.length;

        int pivotPointer = (left + right) / 2;
        int pivot = numbers[pivotPointer];

        int leftPointer  = left;
        int rightPointer = right;

        while (leftPointer <= rightPointer) {
            while (numbers[leftPointer] < pivot) {
                leftPointer++;
            }
            while (numbers[rightPointer] > pivot) {
                rightPointer--;
            }

            if (leftPointer <= rightPointer) {
                int tmp = numbers[leftPointer];
                numbers[leftPointer] = numbers[rightPointer];
                numbers[rightPointer] = tmp;

                leftPointer++;
                rightPointer--;
            }
        }

        execQuickSort(numbers, left, rightPointer);
        execQuickSort(numbers, leftPointer, right);

        return;
    }

    public static void execBucketSort(int numbers[], int min, int max) {
        int arraySize = numbers.length;

        DListInt[] bucket = new DListInt[max - min + 1];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new DListInt();
        }

        for (int i = 0; i < arraySize; i++) {
            bucket[numbers[i] - min].add(numbers[i]);
        }

        for (int i = 0, j = 0; i < bucket.length; i++) {
            while (bucket[i].num > 0) {
                numbers[j] = bucket[i].shift();
                j++;
            }
        }

        return;
    }

    public static void execHeapSort(int numbers[])
    {
        int arraySize = numbers.length;
        int heap[] = new int[arraySize];

        // heap node num
        int num = 0;

        // build heap structure
        for (int target = 0; target < arraySize; target++) {
            heap[num] = numbers[target];
            num++;

            int i = num;
            int j = i / 2;

            int tmp;
            while (i > 1 && heap[i - 1] < heap[j - 1]) {
                tmp = heap[i - 1];
                heap[i - 1] = heap[j - 1];
                heap[j - 1] = tmp;

                i = j;
                j = i / 2;
            }
        }

        // build sorted numbers
        int root;
        for (int target = 0; target < arraySize; target++) {
            root = heap[0];
            num--;
            heap[0] = heap[num];

            int i = 1, j = i * 2;
            while (j <= num) {
                if(j + 1 <= num && heap[j - 1] > heap[j]) j++;

                if (heap[i - 1] > heap[j - 1]) {
                    int temp = heap[i - 1];
                    heap[i - 1] = heap[j - 1];
                    heap[j - 1] = temp;

                }

                i = j;
                j = i * 2;
            }

            numbers[target] = root;
        }

        return;
    }
}
