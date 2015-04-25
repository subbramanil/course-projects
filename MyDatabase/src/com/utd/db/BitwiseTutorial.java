package com.utd.db;
/**
 *  "Tutorial For Java Bitwise Operators"
 *  Course: CS-6360 - Database Design
 *  Date: 16 April 2015
 *
 *  Author: Chris Irwin Davis
 *  Email: chrisirwindavis@utdallas.edu
 *  University of Texas at Dallas
 *
 */


/*
 *  Note that the Bitwise operators all return 4-byte int
 *  reguardless if the operands are int, short int, or byte.
 *
 *  Therefore it is necessary to cast the output of bitwise operators
 *  to (byte) in order to assign to a byte data type
 *
 *  Bitwise operators are:
 *      Bitwise AND (&), Bitwise OR (|), Bitwise XOR (^), and Bitwise Negation (~)
 *
 *  For Example:
 *      byte a = 0x09;          // binary 0000 1001 , i.e. Decimal  9 
 *      byte b = 0x0C;          // binary 0000 1100 , i.e. Decimal 12
 *      byte c = (byte)(a | b); // binary 0000 1000 , i.e. Decimal  8
 *      byte d = (byte)~b       // binary 1111 0011
 */

public class BitwiseTutorial {

	/*
	 *  Create a bit mask for each of the four boolean fields
	 *
	 *  Note that all primative integer types: byte, short, int, and long 
	 *  can be assigned using decimal, hexadecimal, or octal.
	 *  You may use any one of the following groups to initialize the set of four bytes
	 */

	// Assign byte variables using Decimal notation
	final static byte double_blind_mask      = 8;    // binary 0000 1000
	final static byte controlled_study_mask  = 4;    // binary 0000 0100
	final static byte govt_funded_mask       = 2;    // binary 0000 0010
	final static byte fda_approved_mask      = 1;    // binary 0000 0001

	/*
	// Assign byte variables using Hexadecimal notation
	final static byte double_blind_mask      = 0x08; // binary 0000 1000
	final static byte controlled_study_mask  = 0x04; // binary 0000 0100
	final static byte govt_funded_mask       = 0x02; // binary 0000 0010
	final static byte fda_approved_mask      = 0x01; // binary 0000 0001
	*/
	
	/*
	// Assign byte variables using Octal notation
	final static byte double_blind_mask      = 010;  // binary 0000 1000
	final static byte controlled_study_mask  = 04;   // binary 0000 0100
	final static byte govt_funded_mask       = 02;   // binary 0000 0010
	final static byte fda_approved_mask      = 01;   // binary 0000 0001
	*/


	/**
	 *  Begin main() method
	 */
    public static void main(String[] args) {
        System.out.println("This is a Java bitwise tutorial");
		System.out.println("CS-6360 Data Design");
		System.out.println("©2015 Chris Irwin Davis, Univeristy of Texas at Dallas");
		drawTextLine('=', 80);
		System.out.println("\n\n");

		/*
		 *  Notice that the bitwise negation operator (~) will flip each bit
		 *  The output is a 4-byte int, so it must be cast back to byte.
		 */
		byte posByte12 = (byte)12;
		byte negByte12 = (byte)~posByte12;
		System.out.println("posByte12:\t" + posByte12 + "\t" + byte2bits(posByte12));
		System.out.println("negByte12:\t" + negByte12 + "\t" + byte2bits(negByte12));
		System.out.println(~posByte12);
		// System.exit(42);
		
		drawTextLine('-', 60);

		/* 
		 *  This is the common byte that will encode all four boolean bits
		 *  This declaration initializes all eight bits in the byte to "false" 
		 */
		byte commonByte = 0x00;               //  binary 0000 0000

		/* Use the locat static method byte2bit to view the individual bit values */
		System.out.print("commonByte is initialized to all zeros: ");
		System.out.println(byte2bits(commonByte));

		drawTextLine('-', 60);

		/*
		 *  Comment out any one of the following four to 
		 *  to prevent the bit from being set to "true"
		 */
		System.out.println("Using Bitwise OR with bit masks to set all four LSB bits to true");

		commonByte = (byte)(commonByte | double_blind_mask);
		commonByte = (byte)(commonByte | controlled_study_mask);
		commonByte = (byte)(commonByte | govt_funded_mask);
		commonByte = (byte)(commonByte | fda_approved_mask);


		/* Use the locat static method byte2bit to view the individual bit values */
		System.out.print("commonByte is now: ");
		System.out.println(byte2bits(commonByte));

		drawTextLine('-', 60);

		System.out.println("Reading and displaying boolean literal values for each of the four LSB bits");
		/*
		 *  Display each of the four bit values
		 */
		/* Use bitwise AND to get 2^3 bit */
		System.out.print("double_blind bit is:     ");
		System.out.println(double_blind_mask == (byte)(commonByte & double_blind_mask));

		/* Use bitwise AND to get 2^2 bit */
		System.out.print("controlled_study bit is: ");
		System.out.println(controlled_study_mask == (byte)(commonByte & controlled_study_mask));

		/* Use bitwise AND to get 2^1 bit */
		System.out.print("govt_funded bit is:      ");
		System.out.println(govt_funded_mask == (byte)(commonByte & govt_funded_mask));

		/* Use bitwise AND to get 2^0 bit */
		System.out.print("fda_approved bit is:     ");
		System.out.println(fda_approved_mask == (byte)(commonByte & fda_approved_mask));

		drawTextLine('-', 60);

		System.out.println("Using Bitwise AND (&) with Bitwise NEG (~) bit mask to set controlled_study bit to false");
		commonByte = (byte)(commonByte & ~controlled_study_mask);
		System.out.print("commonByte is now: ");
		System.out.println(byte2bits(commonByte));

		System.out.println();

		System.out.println("Using Bitwise AND (&) with Bitwise NEG (~) bit mask to set fda_approved bit to false");
		commonByte = (byte)(commonByte & ~fda_approved_mask);
		System.out.print("commonByte is now: ");
		System.out.println(byte2bits(commonByte));

		System.out.println();

		System.out.println("Using Bitwise OR (|) with bit mask to set controlled_study bit to true again");
		commonByte = (byte)(commonByte | controlled_study_mask);
		System.out.print("commonByte is now: ");
		System.out.println(byte2bits(commonByte));
		

    }
	
	/**
	 *  byte2bits(byte) method returns a String, NOT as a true binary byte!!
	 *
	 *  It is used ONLY for debugging purposes. You cannot use arithmetic
	 *  operators on the output.
	 */
	static String byte2bits(byte b) {
		return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');		
	}
	/*
	 *  
	 */
	static void drawTextLine(char c, int i) {
		for(int n=0;n<i;n++) { System.out.print(c); }
		System.out.println();
	}
	
	
}