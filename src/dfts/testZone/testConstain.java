/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfts.testZone;

import java.util.Scanner;

public class testConstain {
    public static void main(String[] args) {
        String phone = "0848578664";
        Scanner n = new Scanner(System.in);
        while(true){
            String input = n.nextLine();
            System.out.println(input.toUpperCase().contains(phone));
        }
    }
}
