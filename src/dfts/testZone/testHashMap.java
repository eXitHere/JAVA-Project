/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfts.testZone;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Thana
 */
public class testHashMap {
    public static void main(String[] args) {
        List<Integer> primes = new ArrayList<Integer>();
       
        primes.add(2);
        primes.add(3);
        primes.add(5);
        primes.add(7);  //duplicate
        primes.add(7);
        primes.add(11);
       
        System.out.println("list of prime numbers : " + primes);

        Set<Integer> primesWithoutDuplicates = new LinkedHashSet<Integer>(primes);
       
        primes.clear();
       
        primes.addAll(primesWithoutDuplicates);
       
        System.out.println("list of primes without duplicates : " + primes);
    }
}
