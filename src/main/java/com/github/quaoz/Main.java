package com.github.quaoz;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Run test? (y/n): ");

		// Time taken in nanoseconds, XOR is the best method, running the fastest and being usable with any values
		// unlike LCG which is limited to values above 32 and produces poor results with multiples of 3 or 5.
		//
		// Sim: 23582431750
		// LCG:   911695542
		// XOR:   789994708

		if (scanner.next().equalsIgnoreCase("y")) {
			Die die = new Die(5);
			int count = 100000000;
			int max = 32;
			boolean quiet = true;

			System.out.println("\nTest Simulation:");
			long start = System.nanoTime();
			SimulateDie.test(max, die, count, quiet);
			System.out.println("Took: " + (System.nanoTime() - start));

			System.out.println("\nTest Linear Congruential Generator:");
			start = System.nanoTime();
			LCG.test(max, die, count, quiet);
			System.out.println("Took: " + (System.nanoTime() - start));

			System.out.println("\nTest XOR Shift Random:");
			start = System.nanoTime();
			XORShiftRandom.test(max, die, count, quiet);
			System.out.println("Took: " + (System.nanoTime() - start));
		} else {
			System.out.println("Method 1 (Simulation):");
			System.out.print("Die size: ");
			Die die = new Die(scanner.nextInt());

			System.out.print("Simulated die size: ");
			int size = scanner.nextInt();

			System.out.print("How many values: ");
			int count = scanner.nextInt();

			System.out.println("Result: " + Arrays.toString(SimulateDie.random(size, die, count)));

			System.out.println("\nMethod 2 (LCG):");
			System.out.print("Die size: ");
			die = new Die(scanner.nextInt());

			System.out.print("Maximum value (minimum 32): ");
			size = scanner.nextInt();

			System.out.print("How many values: ");
			count = scanner.nextInt();

			System.out.println("Result: " + Arrays.toString(LCG.random(size, die, count)));

			System.out.println("\nMethod 3 (XOR):");
			System.out.print("Die size: ");
			die = new Die(scanner.nextInt());

			System.out.print("Maximum value: ");
			size = scanner.nextInt();

			System.out.print("How many values: ");
			count = scanner.nextInt();

			System.out.println("Result: " + Arrays.toString(XORShiftRandom.random(size, die, count)));
		}
	}
}
