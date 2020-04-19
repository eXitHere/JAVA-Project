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
        List<String> primes = new ArrayList<String>();
       
        primes.add("Hello");
        primes.add("Hello");
        primes.add("Hello");
        primes.add("Hello");  //duplicate
        primes.add("Hello");
        primes.add("Hello");
       
        System.out.println("list of prime numbers : " + primes);

        Set<String> primesWithoutDuplicates = new LinkedHashSet<String>(primes);
       
        primes.clear();
       
        primes.addAll(primesWithoutDuplicates);
       
        System.out.println("list of primes without duplicates : " + primes);
    }
}
