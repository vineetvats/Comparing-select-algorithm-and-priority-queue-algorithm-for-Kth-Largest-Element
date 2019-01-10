/**
 * @author Punit Bhalla and Vineet Vats
 * <p>
 * find K largest elements from an array using following methods:
 * Choice 1. Select Algorithm
 * Choice 2. Priority Queue
 * <p>
 * Compares the performance for both the methods.
 */

package pxb173830;

import java.util.Random;

public class SP11 {

    /**
     * threshold: if n (no. of elements in an array) is less than this value, insertion sort is used.
     * index: this value is used to print K largest elements using select algorithm
     * numTrials: this value is used for evaluating the performance of both the algorithms
     * pq: stores the k largest numbers using priority queues.
     */
    private static int threshold = 5;
    private static int index;
    public static int numTrials = 10;
    private static BinaryHeap pq;

    /**
     * This function finds the k largest numbers in an array using priority queue algorithm
     *
     * @param arr array of integers
     * @param k   number of largest elements to be retrieved
     */
    public static void selectKLargestNumbers(int[] arr, int k) {
        Integer[] temp = new Integer[k];
        for (int i = 0; i < k; i++) {
            temp[i] = Integer.valueOf(arr[i]);
        }
        pq = new BinaryHeap(temp);
        for (int i = k; i < arr.length; i++) {
            Integer x = arr[i];
            if (x.compareTo((Integer) pq.peek()) > 0) {
                pq.move(0, x);
                pq.percolateDown(0);
            }
        }
    }

    /**
     * This function finds the k largest numbers in an array using select algorithm
     *
     * @param arr array of integers
     * @param k   number of largest elements to be retrieved
     *            result is stored in arr[arr.length-k... arr.length-1]
     */
    public static void select(int[] arr, int k) {
        index = select(arr, 0, arr.length, k);
    }

    /**
     * Helper function to find k largest numbers from an array
     *
     * @param arr array of integers
     * @param p   start position of array
     * @param n   number of elements in an array
     * @param k   : number of largest elements to be retrieved
     * @return the index which then can be used to retrieve k largest elements from arr
     */
    private static int select(int[] arr, int p, int n, int k) {
        if (n < threshold) {
            insertionSort(arr, p, p + n - 1);
            return p + n - k;
        } else {
            int q = randomizedPartition(arr, p, p + n - 1);
            int left = q - p;
            int right = n - left - 1;
            if (right >= k) {
                return select(arr, q + 1, right, k);
            } else if (right + 1 == k) return q;
            else {
                return select(arr, p, left, k - right - 1);
            }
        }
    }

    /**
     * This function finds the random partition within an array
     *
     * @param arr array of integers
     * @param p   start position of array
     * @param r   end position of array
     * @return partition's index
     */
    private static int randomizedPartition(int[] arr, int p, int r) {
        Random random = new Random();
        int i = random.nextInt(r - p) + p;
        swap(arr, i, r);
        return partition(arr, p, r);
    }

    /**
     * This function finds the partition's index
     *
     * @param arr array of integers
     * @param p   start position of the array
     * @param r   end position of the array
     * @return partition's index
     */
    private static int partition(int[] arr, int p, int r) {
        int i = p - 1;
        int x = arr[r];
        for (int j = p; j < r; j++) {
            if (arr[j] <= x) {
                i = i + 1;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, r);
        return i + 1;
    }

    /**
     * This function swaps the elements within an array
     *
     * @param arr array of integers
     * @param i   index to be swapped
     * @param j   index with which arr[i] needs to be swapped
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * This function sorts the array using insertion sort algorithm
     *
     * @param arr array of integers
     * @param p   start index of the array
     * @param r   end index of the array
     */
    private static void insertionSort(int[] arr, int p, int r) {
        int temp;
        int j;
        for (int i = p + 1; i <= r; i++) {
            temp = arr[i];
            j = i - 1;
            while (j >= p && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * Helper method to shuffle the array
     *
     * @param arr  array of integers
     * @param from start index of array
     * @param to   end index of array
     */
    private static void shuffle(int[] arr, int from, int to) {
        int n = to - from + 1;
        for (int i = 1; i < n; i++) {
            int j = new Random().nextInt(i);
            swap(arr, i + from, j + from);
        }
    }


    /**
     * This function shuffles the array
     *
     * @param arr array of integers
     */
    private static void shuffle(int[] arr) {
        shuffle(arr, 0, arr.length - 1);
    }

    /**
     * This functions prints the elements of an array
     *
     * @param arr     array of integers
     * @param message message to be printed
     */
    public static <T> void printArray(int[] arr, String message) {
        printArray(arr, 0, arr.length - 1, message);
    }

    /**
     * This functions prints the elements of an array
     *
     * @param arr     array of integers
     * @param from    from index of an array
     * @param to      to index of an array
     * @param message message to be printed
     */
    public static <T> void printArray(int[] arr, int from, int to, String message) {
        System.out.print(message);
        for (int i = from; i <= to; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 20;
        int k = (int) Math.ceil(n / 2.0);
        int choice = 1 + new Random().nextInt(2);
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            choice = Integer.parseInt(args[1]);
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        Timer timer;
        switch (choice) {
            case 1:// Select Algorithm
                timer = new Timer();
                for (int i = 0; i < numTrials; i++) {
                    shuffle(arr);
                    select(arr, k);
                }
                printArray(arr, index, n - 1, "K largest Numbers using Select Algorithm:\n");
                timer.end();
                timer.scale(numTrials);
                System.out.println("Choice: " + choice + "\n" + timer);
                break;
            case 2://Priority Queue Method
                timer = new Timer();
                for (int i = 0; i < numTrials; i++) {
                    shuffle(arr);
                    selectKLargestNumbers(arr, k);
                }
                timer.end();
                timer.scale(numTrials);
                System.out.println("K Largest Numbers using Priority Queue Algorithm:");
                while (pq.size != 0) {
                    System.out.print(pq.poll() + " ");
                }
                System.out.println("\nChoice: " + choice + "\n" + timer);
                break;
        }

    }

}
