import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.*;

public class Test {
    private static Random rand = new Random( 47 );
    private static int size = 10;
    public static void main(String[] args) {
        int arr[] = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt( 1000 );
        }

        for (int i = 0; i < size; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        int index = (size - 1) / 2;
        for (int i = index; i >= 0; i--) {
            heapSort( arr, i, size-1 );
        }

        int tmp;
        for(int i = size - 1; i > 0; i--){
            tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            heapSort( arr, 0, i - 1 );
        }


        System.out.println();

      /*  insertingSort( arr ); */
        for (int i = 0; i < size; i++) {
            System.out.println(arr[i] + " ");
        }
    }

    //从小到大排序
    //交换排序
    private static void exchangingSort(int[] arr){
        int tmp;
        for (int i = 0; i < size - 1; i++) {
            tmp = arr[i];
            for (int j = i + 1; j < size; j++) {
                if(arr[i] > arr[j]){
                    arr[i] = arr[j];
                    arr[j] = tmp;
                    tmp = arr[i];
                }
            }
        }
    }

    //从小到大排序
    //冒泡排序
    private static void bubblingSort(int[] arr){
        int tmp, position;
        for (int i = 0; i < size - 1; i++) {
            tmp = arr[0];
            position = 0;
            int j = 1;
            for (j = 1; j < size - (i + 1); j++) {
                if(arr[j] > tmp){
                    tmp = arr[j];
                    position = j;
                }
            }
            arr[position] = arr[j];
            arr[j] = tmp;
        }
    }

    //从小到大排序
    //插入排序
    public static void insertingSort(int[] arr){
        int tmp;
        for (int i = 1; i < size; i++) {
            tmp = arr[i];
            int j;
            for (j = i - 1; j > 0 ; j--) {
                if(arr[j] > tmp){
                    arr[j+1] = arr[j];
                }else{
                    break;
                }
            }
            arr[j+1] = tmp;
        }
    }


    //从小到大排序
    //堆排序
    public static void heapSort(int[] arr, int index, int end){
        int tmp, position,child;
        tmp = arr[index];
        for ( position = index; position * 2 + 1 <= end; position = child) {
            child = position * 2 + 1;
            if(child > end)
                break;
            if(child+1 <= end && arr[child+1] > arr[child]){
                child++;
            }
            if(arr[child] > tmp){
                arr[position] = arr[child];
            }else{
                break;
            }
        }
        arr[position] = tmp;
    }
}
