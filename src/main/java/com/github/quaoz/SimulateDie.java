package com.github.quaoz;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Generates a random number.
 */
public class SimulateDie {
	public int numRolls;
	public int blockSize;
	public int max;
	public Die die;

	/**
	 * Constructs a new die simulator.
	 *
	 * @param max The maximum value
	 * @param die The die to role
	 */
	public SimulateDie(int max, @NotNull Die die) {
		this.die = die;
		this.max = max;

		int sides = die.getSides();

		// For n rolls there are sides^n combinations, this calculates n such that there are more
		// possible combinations than the given max value.
		//
		// It is equivalent to:
		// 		while (Math.pow(sides, numRolls) < max) { numRolls++; }
		// however runs slightly faster.
		numRolls = (int) Math.ceil(Math.log(max) / Math.log(sides));

		// Calculates the range of values which correspond to each number.
		//
		// e.g. using a 3 sided dice to generate a number up to 4:
		//    numRolls       = 2
		//    possibilities  = 3^2 = 9
		//    blockSize        = floor(9 / 4) = 2
		//    possible values = [1, 2, 3, 4, 5, 6, 7, 8, 9]
		//    value mapping   = [1, 2] -> 1
		//                      [3, 4] -> 2
		//                      [5, 6] -> 3
		//                      [7, 8] -> 4
		//                      [9]    -> re-roll
		//    each block in the value mapping contains blockSize (2) elements
		blockSize = Math.floorDiv((int) Math.pow(sides, numRolls), max);
	}

	/**
	 * Returns the next random number.
	 *
	 * @return The next random number
	 */
	public int next() {
		// Generates the required rolls
		int[] rolls = new int[numRolls];
		for (int i = 0; i < numRolls; i++) {
			rolls[i] = die.roll();
		}

		// Coverts the random co-ordinates in rolls[] to a linear index.
		//
		// e.g. when a grid like:
		//       [[a, b, c],
		//        [d, e, f],
		//        [g, h, i]]
		//    is converted into a list like:
		//       [a, b, c, d, e, f, g, h, i]
		//    the item at f which was at the co-ordinates (1, 2) is now at the 5th index
		int index = 0;
		for (int i = 0; i < numRolls; i++) {
			index += Math.pow(die.getSides(), i) * rolls[i];
		}

		// Maps the random index to a value.
		int value = Math.floorDiv(index, blockSize);

		// If the value is too large (like 9 in the first example) re-roll, otherwise return the value.
		return value >= max
				? next()
				: value;
	}

	/**
	 * Returns {@code count} random numbers.
	 *
	 * @param max   The maximum value
	 * @param die   The die to role
	 * @param count The number of values to generate
	 *
	 * @return An array of random numbers
	 */
	public static int @NotNull [] random(int max, Die die, int count) {
		SimulateDie simulateDie = new SimulateDie(max, die);
		int[] results = new int[count];

		for (int i = 0; i < count; i++) {
			results[i] = simulateDie.next();
		}

		return results;
	}

	public static void test(int max, Die die, int count, boolean quiet) {
		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			Arrays.stream(SimulateDie.random(max, die, count / 100)).forEach(list::add);
		}

		if (!quiet) {
			for (int i = 0; i < max; i++) {
				System.out.println(i + " appeared " + Collections.frequency(list, i) + " times ");
			}
		}
	}
}
