package math;

import java.util.Arrays;

/**
 * Created by Liuh on 2016/11/17.
 */
public class SelectSort {
    public static void main(String[] args) {
        Select();
        BubbleSort();
        InsertSort();
        CountSort();
    }

    /**
     * 选择排序
     */
    static void Select(){
        int a[] = {1,2,3,4,5,6,3,1,2,3 };
        for (int i = 0; i < a.length-1; i++) {
            int min = i;
            for ( int j= i+1; j < a.length; j++) {
                if(a[j]<a[min]&&a[j]!=a[min]){
                    min = j;
                }
            }
            int temp = a[i];
            a[i] = a[min];
            a[min] = temp;
        }
        System.out.println(Arrays.toString(a));
    }

    /**
     * 冒泡排序
     */
    static void BubbleSort(){
        int a[] = {1,4,2,5,7,3 };
        for (int i = 0; i < a.length-1; i++) {
            for (int j = 0; j < a.length-i-1; j++) {
                if(a[j]>a[j+1]){
                    int temp = a[j+1];
                    a[j+1] = a[j];
                    a[j]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }
    /**
     * 插入排序
     */
    static void InsertSort() {
        int[] a = {1,3,2,1,4,2,5,7,3};
        int mark,compare;
        for(mark = 1;mark < a.length;mark++ ){
            int temp = a[mark];
            compare = mark;
            while(compare>0&&a[compare-1]>temp){
                a[compare] = a[compare-1];
                compare--;
            }
            a[compare] =temp;
        }
        System.out.println(Arrays.toString(a));
    }

    /**
     * 计数排序
     */
    static void CountSort(){
        int a[] = {1,2,3,4,4,4,3,1,2,3 };
        int c[] = new int[5];
        for(int i=0;i<c.length;i++){
            for(int j=0;j<a.length;j++){
                if(a[j] == i){
                    c[i]++;
                }
            }
        }
        int index = 0;
        for(int j=0;j<c.length;j++){
            for(int i=index;i<index + c[j];i++){
                a[i] = j;
            }
            index +=c[j];
        }
        System.out.println(Arrays.toString(a));
    }

}
