/*
 * �������� 2005-3-18
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.lshy.engine;

//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Random;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class TestNumShift {

	public static void main(String[] args) {
		/*
		int i = -1;		
		int j = (i<<16);
		int k = (i>>>16);
		//printBinaryInt(j);
		//printBinaryInt(k);
		//printBinaryInt(j&k);
		
		int t=23465;
		printBinaryInt(t);

		printBinaryInt(t>>>1);
		printBinaryInt(0x55555555);
		printBinaryInt((t >>> 1) & 0x55555555);
		printBinaryInt(t & 0x55555555);
		t = ((t >>> 1) & 0x55555555) + (t & 0x55555555);
		printBinaryInt(t);
		System.out.println("================================");

		printBinaryInt(t>>>2);
		printBinaryInt(0x33333333);
		printBinaryInt((t >>> 2) & 0x33333333);
		printBinaryInt(t);
		printBinaryInt(0x33333333);
		printBinaryInt(t & 0x33333333);
		t = ((t >>> 2) & 0x33333333) + (t & 0x33333333);
		printBinaryInt(t);
		System.out.println("================================");
		
		printBinaryInt(t >>> 4);
		printBinaryInt(0x0f0f0f0f);
		printBinaryInt((t >>> 4) & 0x0f0f0f0f);
		printBinaryInt(t & 0x0f0f0f0f);
		t = ((t >>> 4) & 0x0f0f0f0f) + (t & 0x0f0f0f0f);
		printBinaryInt(t);
		System.out.println("================================");
		
		printBinaryInt(t >>> 8);
		printBinaryInt(0x00ff00ff);
		printBinaryInt((t >>> 8) & 0x00ff00ff);
		
		printBinaryInt(t);
		printBinaryInt(0x00ff00ff);
		t = ((t >>> 8) & 0x00ff00ff) + (t & 0x00ff00ff);
		printBinaryInt(t);
		
		printBinaryInt((t >>> 16) + (t & 0x0000ffff));
		*/
		/*
		char x = (char)(-1);
		System.out.println((int)(char)((int)x+1));
		Random rand = new Random();
		for (int i=1;i<100;i++){
			System.out.println(rand.nextLong());
		}
		ArrayList list = new ArrayList();
		BitBoard bb = new BitBoard(1);
		list.add(bb);
		list.add(bb);
		list.add(bb);
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));
		((BitBoard)list.get(0)).leftShift(1);
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));*/
		//short k = 2,j = 3;
		//j = (short) (k<<3);
		/*
		try {
			BufferedReader inFile = new BufferedReader(new FileReader("tmpinfo.log"));
			for (int i=0;i<1;i++){
				inFile.readLine();
			}
			for (int i=0;i<1000;i++){
				System.out.println(inFile.readLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		char[] str=new char[10];
		str[0]='a';
		str[1]='b';
		str[2]='c';
		System.out.println(str);//.toString());
		
		
	}
	public static void printBinaryInt(int i){
		System.out.print(i+"=");
		for (int j=31;j>=0;j--){
			if(((1<<j) & i)!=0)
				System.out.print("1");
			else
				System.out.print("0");
		}
		System.out.println();
	}
}
