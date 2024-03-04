package oy.tol.tra;

public class Algorithms {
    public static <T>void swap(T[] array,int index1,int index2){
        T tmp=array[index1];
        array[index1]=array[index2];
        array[index2]=tmp;
    }
    public static <T extends Comparable<T>> void sort(T [] array){
        for (int i = 0; i < array.length-1; i++) {
            for (int j = 0; j < array.length-i-1; j++) {
                if(array[j].compareTo(array[j+1])>0){
                    swap(array, j, j+1);
                }
            }
        }
    }
    public static <T> void reverse(T [] array){
        int left=0;
        int right=array.length-1;
        while(left<right){
            swap(array,left,right);
            left++;
            right--;
        }
    }
}
