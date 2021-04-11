package com.adedoyin.backend.question1;

import java.util.Arrays;

public class ArrayAlgorithm {

	public int getMinInteger(int[] A) {

		A = removeRepeatingItems(A);

		int length = getLength(A);

		if (length <= 0) {
			return 1;
		} else if (length == 1) {
			int max = A[0];
			return isPositiveInteger(max) ? A[0] + 1 : 1;
		}

		if (!isPositiveInteger(A[length - 1])) {
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

	private boolean isPositiveInteger(int number) {
		return number >= 0;
	}

	private int getMin(int[] A) {
		return Arrays.stream(A).min().orElse(0);
	}

	private int[] removeRepeatingItems(int[] A) {
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
		int[] A = { 1, 2, 3, 3, 4, 6, 8 };
		int minInteger = algorithm.getMinInteger(A);
		System.out.println(minInteger);
	}

}
