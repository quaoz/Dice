package com.github.quaoz;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Generates a pseudo-random number using a linear congruential generator, this is typically used
 * with much larger numbers. For example {@link java.util.Random} uses {@code 2^48} as the modulus,
 * {@code 25,214,903,917} as the multiplier and {@code 11} as the increment.
 * <p>
 * Due to this implementation using {@code 5} as the multiplier and {@code 3} as the increment
 * using a max which is a multiple of {@code 5} or {@code 3} such as {@code 60} will generate a
 * sequence with a very short period.
 */
public class LCG {
	private int last;
	private final int modulus;
	private final int multiplier = 5;
	private final int increment = 3;

	/**
	 * Constructs a new pseudo-random number generator.
	 *
	 * @param max The maximum value must be greater than {@code 32}
	 * @param die The die to role
	 */
	public LCG(int max, @NotNull Die die) {
		last = die.roll();
		modulus = Math.max(max, (multiplier * increment) + 2);
	}

	/**
	 * Returns the next random number in the sequence.
	 *
	 * @return The next random number in the sequence
	 */
	public int next() {
		last = (multiplier * last + increment) % modulus;
		return last;
	}

	/**
	 * Returns the first {@code count} terms from the sequence.
	 *
	 * @param max   The maximum value must be greater than {@code 32}
	 * @param die   The die to role
	 * @param count The number of values to generate
	 *
	 * @return An array of pseudo-random numbers
	 */
	public static int @NotNull [] random(int max, @NotNull Die die, int count) {
		LCG simulateDie = new LCG(max, die);
		int[] results = new int[count];

		for (int i = 0; i < count; i++) {
			results[i] = simulateDie.next();
		}

		return results;
	}

	public static void test(int max, Die die, int count, boolean quiet) {
		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			Arrays.stream(LCG.random(max, die, count / 100)).forEach(list::add);
		}

		if (!quiet) {
			for (int i = 0; i < max; i++) {
				System.out.println(i + " appeared " + Collections.frequency(list, i) + " times ");
			}
		}
	}
}
