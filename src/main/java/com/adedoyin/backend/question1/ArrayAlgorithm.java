package com.adedoyin.backend.question1;

import java.util.Arrays;

public class ArrayAlgorithm {
	
	public int solution(int[] A) {
        // write your code in Java SE 8

        A = removeDuplicates(A);

        int length = getLength(A);

        if (length <= 0) {
            return 1;
        } else if (length == 1) {
            int max = A[0];
            return isPositive(max) ? A[0] + 1 : 1;
        }

        //Arrays.sort(A);

        if (!isPositive(A[length - 1])) {
            return 1;
        }

        int max = A[length - 1];

        int j = 1;
        int newArrayIndex = 0;
        int[] newArray = new int[j];

        for (int i = max; i > 0; i--) {

            if (!contains(A, i)) {
                newArray[newArrayIndex] = i;
            }
        }

        int newLength = getLength(newArray);
        if (newLength <= 0 || getMin(newArray) == 0) {
            return max + 1;
        } else {
            return getMin(newArray);
        }


    }

    private int getLength(int[] A) {
        return A.length;
    }

    private boolean isPositive(int number) {
        return number >= 0;
    }

    private int getMin(int[] A) {
        return Arrays.stream(A)
                .min().orElse(0);
    }

    private int[] removeDuplicates(int[] A) {
        return Arrays.stream(A).distinct().toArray();
    }


    private static boolean contains(final int[] array, final int v) {

        boolean result = false;

        for (int i : array) {
            if (i == v) {
                result = true;
                break;
            }
        }

        return result;

    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayAlgorithm algorithm = new ArrayAlgorithm();
		int[] A = {1,2,3,3,4,6,8};
		int minInteger = algorithm.solution(A);
		System.out.println(minInteger);
	}

}
