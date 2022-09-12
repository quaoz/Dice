package com.github.quaoz;

import java.util.Random;

/**
 * Represents a die.
 */
public class Die {
	private final int sides;
	private final Random random;

	/**
	 * Constructs a new die.
	 *
	 * @param sides The number of sides
	 */
	public Die(int sides) {
		this.sides = sides;
		this.random = new Random();
	}

	/**
	 * Returns the number of sides.
	 *
	 * @return The number of sides
	 */
	public int getSides() {
		return sides;
	}

	/**
	 * Rolls a random number
	 *
	 * @return A random number
	 */
	public int roll() {
		return random.nextInt(sides);
	}
}
