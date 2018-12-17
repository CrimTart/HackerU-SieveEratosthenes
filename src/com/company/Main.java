package com.company;

import java.util.Scanner;

//need to determine if a long number n is prime or not using Eratosthenes sieve
//skip all numbers divisible by 2 or 3, thus going from n to n/2 to (n/2)*(2/3)=n/3 space req.
//to check if k is prime, need only see if k is divisible by numbers up to sqrt(k)
//sqrt(Long.MAX_VALUE)/3 << Integer.MAX_VALUE, thus boolean[sqrt(Long.MAX_VALUE)/3] can fit in heap space (barely)

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Input a positive integer up to " + Long.MAX_VALUE);
            long upperLong = scanner.nextLong();
            if (upperLong < 1) throw new IllegalArgumentException();
            int upperInt = (int)Math.ceil(Math.sqrt(upperLong)/3);
            boolean[] mySieve = constructSieve(upperInt);
            System.out.println(upperLong + (isPrime(upperLong, mySieve)?" is prime!":" is not prime!"));
        }
        catch (IllegalArgumentException e) {
            System.out.println("Integer must be positive");
        }
        catch (Exception e) {
            System.out.println("Incorrect input parameters");
        }
        finally {
            scanner.close();
        }
    }

    public static boolean isPrime(long n, boolean[] sieve) {
        if (n == 2 || n == 3) return true;
        if (n == 1 || n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 0; i < sieve.length; i++) {
            if (sieve[i] && n % (2 * i + 3) == 0) return false;
        }
        return true;
    }

    //Skipping all numbers divisible by 2 or 3, construct a list of all primes up to an upper limit
    public static boolean[] constructSieve(int upper) {
        boolean[] sieve = new boolean[upper];
        for (int i = 0; i < sieve.length; i++) sieve[i] = true;
        /*for (int i = 0; i <= Math.sqrt(sieve.length - 1); i++) {
            if (sieve[i]) {
                int shift = 2 * i + 3; // actual number represented by the sieve cell
                for (int j = i + shift; j < sieve.length; j += shift)
                    sieve[j] = false;
            }
        }*/
        for (int i = 0; i <= Math.sqrt(sieve.length - 1); i++)
            if (sieve[i]) {
                int shift = sieveValue(i);
                for (int j = i + shift; j < sieve.length; j += shift)
                    sieve[j] = false;
            }
        return sieve;
    }

    public static int sieveValue(int i) {
        if (i == 0) return 5;
        return sieveValue(i-1) + 2 * (1 + i%2);
    }
}
