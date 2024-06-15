package com.pay.chikun.utils;

import java.sql.Timestamp; //timestamp available in two packages one is security package and another is sql.  here we will use sql
import java.util.Random;

public class Utils {
    //generate 11 digit account number
    public static String generateAccNum() {
        //UUID randomUUID= UUID.randomUUID();     // to get unique id
        Random rnd= new Random();
        int part1= rnd.nextInt(123789); //random nos are from these 6 digits
        int part2= rnd.nextInt(83410);  //another random numbers from these 5 digits total 6+5= 11 digit number
        return String.valueOf(part1+ ""+ part2);
    }
    //get Timestamp method
    public static String getTimestamp() {
        Timestamp timestamp= new Timestamp(System.currentTimeMillis());// you can use currentTime or nanoTime
        return String.valueOf(timestamp);
    }
}
