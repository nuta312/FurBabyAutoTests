package tests;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;


public class Main {

    public static void main(String[] args) throws JsonProcessingException {

//                    0 1 2 3 4
        int[] nums = {1,2,3,4,5};
//                    1 2 3 4 5
        System.out.println(Arrays.toString(nums));
        System.out.println(nums[4]);
        System.out.println(nums[2]); // 5-1=4
        System.out.println(nums[nums.length-1]);

        System.out.println(nums[nums.length-3]);

    }

    public static boolean firstLast6(int[] nums) {
        return nums[0] == 6 || nums[nums.length - 1] == 0;
    }

}
