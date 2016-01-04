package transmitter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * 
 * @author Ruchira
 *
 */
/*
The input interface subsystem shall map the input into internal logical-bit format.
Input pre-processor gives the input to the input-processor module.
Output format of input pre-processor varies according to it�s input format. 
TS: constant length packets of 188x8 bits
GSE: variable or constant length packets.
GCS: a continuous bit stream
These are signaled by the BBHEADER TS/GS field
Implementation is done for single PLP, TS format. 

Here the bits and the fields are filled using a random number generator
Next step is to do that using a file
 */
public class InputInterface {
	FEC_Frame fec = new FEC_Frame();
	
	final int frameLength = fec.frameLength; //64800 OR 16200	
	final int Kbch = fec.Kbch;
	final int bbheaderlength = fec.BBHeaderLength;
	// LDPC + BCH = 64800 - 48600
	// Kbch = DFL + Padding + 80
	
	final int DFL = fec.DFL; // need padding of 48600-44600-80 bits	
	final int pad = fec.pad;
	
	public ArrayList<FEC_Frame> fill_DataField(String inputbits) throws IOException{
		ArrayList<FEC_Frame> frames = new ArrayList<FEC_Frame>(); 
		int total_frames = inputbits.length()/DFL;
//		System.out.println("total_frames: "+total_frames+"  input bits: "+inputbits.length()+"     frame length: "+frameLength);
		System.out.println("/-------------------------- Data Field creation---------------------------/");
		
//		File file = new File("E:\\codes\\inputstream.txt");
//		BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));		
//		StringTokenizer st = new StringTokenizer(reader.readLine(), " ");
		
		int input_index = 0;
		System.out.println("DFL = "+DFL);
		System.out.println("Kbch = "+Kbch);
		for (int i = 0; i < total_frames; i++) {
			boolean[] data_field = new boolean[Kbch];
			FEC_Frame newframe = new FEC_Frame(data_field);
//			int input_index = i * DFL;
			int frame_index = bbheaderlength;
			for (int j = 0; j < DFL; j++) {
				if (inputbits.charAt(input_index) == '1') {
					newframe.FEC_frame[frame_index] = true;
				}else {
					newframe.FEC_frame[frame_index] = false;
				}
				frame_index++;		
				input_index++;
			}
//			printDataField(newframe);
			frames.add(newframe);	
		}
//		System.out.println("------------------- Input Interface --------------------");
//		printDataField(frames);
		return frames;
	}
	
	private static void printDataField(FEC_Frame frame) {
			boolean[] data = frame.FEC_frame;
			System.out.println("\n-------------- Frame-----------------------");
			for (int i = 0; i < data.length; i++) {
				
				if (data[i] == true) {
					System.out.print("1 ");
				}else {
					System.out.print("0 ");
				}
			}
		
	}
	
	private static void printDataField(ArrayList<FEC_Frame> frames) {
		for (int k = 0; k < frames.size(); k++) {
			FEC_Frame tempframe = frames.get(k);
			boolean[] data = tempframe.FEC_frame;
			System.out.println("\n-------------- Frame "+(k+1)+"-----------------------");
			for (int i = 0; i < data.length; i++) {
				
				if (data[i] == true) {
					System.out.print("1 ");
				}else {
					System.out.print("0 ");
				}
			}
		}
		
	}
	
/*	private static void printDataField() {
		for (int i = 0; i < 100; i++) {
			if (i % 2000 == 0) {
				System.out.println();
			}
			if (FEC_frame[i] == true) {
				System.out.print("1 ");
			}else {
				System.out.print("0 ");
			}
		}
	}*/

}
