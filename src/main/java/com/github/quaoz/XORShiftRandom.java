package com.github.quaoz;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Generates a pseudo-random number using an XORShift method
 */
public class XORShiftRandom {
	private final int max;
	private long last;
	private long increment;

	/**
	 * Constructs a new pseudo-random number generator.
	 *
	 * @param max The maximum value
	 * @param die The die to role
	 */
	public XORShiftRandom(int max, @NotNull Die die) {
		this.max = max;

		int seed = die.roll();
		last = seed | 1;
		increment = seed;
	}

	/**
	 * Returns the next random number in the sequence.
	 *
	 * @return The next random number in the sequence
	 */
	public int next() {
		// Values from https://www.javamex.com/tutorials/random_numbers/xorshift.shtml
		last ^= (last << 21);
		last ^= (last >>> 35);
		last ^= (last << 4);
		increment += 123456789123456789L;

		return (int) Math.abs((last + increment) % max);
	}

	/**
	 * Returns the first {@code count} terms from the sequence.
	 *
	 * @param max   The maximum value
	 * @param die   The die to role
	 * @param count The number of values to generate
	 *
	 * @return An array of pseudo-random numbers
	 */
	public static int @NotNull [] random(int max, @NotNull Die die, int count) {
		XORShiftRandom simulateDie = new XORShiftRandom(max, die);
		int[] results = new int[count];

		for (int i = 0; i < count; i++) {
			results[i] = simulateDie.next();
		}

		return results;
	}

	public static void test(int max, Die die, int count, boolean quiet) {
		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			Arrays.stream(XORShiftRandom.random(max, die, count / 100)).forEach(list::add);
		}

		if (!quiet) {
			for (int i = 0; i < max; i++) {
				System.out.println(i + " appeared " + Collections.frequency(list, i) + " times ");
			}
		}
	}
}
