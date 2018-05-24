package com.lshy.engine;

import java.io.Serializable;

public class BitBoard implements Serializable{
  	int Low, Mid, Hi;// int:32
	public BitBoard(int Arg1) {
		Low = Arg1;
		Mid = 0;
		Hi = 0;
	}
	public BitBoard(int Arg1, int Arg2) {
		Low = Arg1;
		Mid = Arg2;
		Hi = 0;
	}
	public BitBoard(int Arg1, int Arg2, int Arg3) {
		Low = Arg1;
		Mid = Arg2;
		Hi = Arg3;
		
	}
	public boolean notZero(){
		return (Low|Mid|Hi)!=0;
	}
	public boolean equals(final BitBoard Arg){
		return ((Low==Arg.Low) && (Mid==Arg.Mid) && (Hi==Arg.Hi));
	}
	public boolean notequals(final BitBoard Arg){
		return Low != Arg.Low || Mid != Arg.Mid || Hi!=Arg.Hi;
	}
	public BitBoard opNot(){
		return new BitBoard(~Low, ~Mid, ~Hi);
	}
	public BitBoard opAnd(final BitBoard Arg){
		return new BitBoard(Low & Arg.Low, Mid & Arg.Mid, Hi & Arg.Hi);
	}
	public BitBoard opOr(final BitBoard Arg){
		return new BitBoard(Low | Arg.Low, Mid | Arg.Mid, Hi | Arg.Hi);
	}
	public BitBoard opXor(final BitBoard Arg){
		return new BitBoard(Low ^ Arg.Low, Mid ^ Arg.Mid, Hi ^ Arg.Hi);
	}
	public void assignAnd(final BitBoard Arg) {
		Low &= Arg.Low;
		Mid &= Arg.Mid;
		Hi &= Arg.Hi;
	}
	public void assignOr(final BitBoard Arg) {
		Low |= Arg.Low;
		Mid |= Arg.Mid;
		Hi |= Arg.Hi;
	}
	public void assignXor(final BitBoard Arg) {
		Low ^= Arg.Low;
		Mid ^= Arg.Mid;
		Hi ^= Arg.Hi;
	}
	public void setBitBoard(int Arg1, int Arg2, int Arg3) {
		Low = Arg1;
		Mid = Arg2;
		Hi = Arg3;		
	}
	public BitBoard getLeftShift(int Arg){
		if (Arg < 0) {
			return getRightShift(-Arg);
		} else if (Arg == 0) {
			return new BitBoard(Low, Mid, Hi);
		} else if (Arg < 32) {
			return new BitBoard(Low << Arg, Mid << Arg | Low >>> (32 - Arg), Hi << Arg | Mid >>> (32 - Arg));
		} else if (Arg == 32) {
			return new BitBoard(0, Low, Mid);
		} else if (Arg < 64) {
			return new BitBoard(0, Low << (Arg - 32), Mid << (Arg - 32) | Low >>> (64 - Arg));
		} else if (Arg == 64) {
			return new BitBoard(0, 0, Low);
		} else if (Arg < 96) {
			return new BitBoard(0, 0, Low << (Arg - 64));
		} else {
			return new BitBoard(0, 0, 0);
		}
	}

	public BitBoard getRightShift(int Arg){
		if (Arg < 0) {
			return getLeftShift(-Arg);
		} else if (Arg == 0) {
			return new BitBoard(Low,Mid,Hi);
		} else if (Arg < 32) {
			return new BitBoard(Low >> Arg | Mid << (32 - Arg), Mid >> Arg | Hi << (32 - Arg), Hi >> Arg);
		} else if (Arg == 32) {
			return new BitBoard(Mid, Hi, 0);
		} else if (Arg < 64) {
			return new BitBoard(Mid >> (Arg - 32) | Hi << (64 - Arg), Hi >> (Arg - 32), 0);
		} else if (Arg == 64) {
			return new BitBoard(Hi, 0, 0);
		} else if (Arg < 96) {
			return new BitBoard(Hi >> (Arg - 64), 0, 0);
		} else {
			return new BitBoard(0, 0, 0);
		}
	}

	public void leftShift(int Arg) {
		if (Arg < 0) {
			rightShift(-Arg);
		} else if (Arg == 0) {
		} else if (Arg < 32) {
			Hi <<= Arg;
			Hi |= Mid >>> (32 - Arg);
			Mid <<= Arg;
			Mid |= Low >>> (32 - Arg);
			Low <<= Arg;
		} else if (Arg == 32) {
			Hi = Mid;
			Mid = Low;
			Low = 0;
		} else if (Arg < 64) {
			Hi = Mid << (Arg - 32);
			Hi |= Low >>> (64 - Arg);
			Mid = Low << (Arg - 32);
			Low = 0;
		} else if (Arg == 64) {
			Hi = Low;
			Mid = 0;
			Low = 0;
		} else if (Arg < 96) {
			Hi = Low << (Arg - 64);
			Mid = 0;
			Low = 0;
		} else {
			Low = 0;
			Mid = 0;
			Hi = 0;
		}
	}

	public void rightShift(int Arg) {
		if (Arg < 0) {
			leftShift(-Arg);
		} else if (Arg == 0) {
		} else if (Arg < 32) {
			Low >>>= Arg;
			Low |= Mid << (32 - Arg);
			Mid >>>= Arg;
			Mid |= Hi << (32 - Arg);
			Hi >>>= Arg;
		} else if (Arg == 32) {
			Low = Mid;
			Mid = Hi;
			Hi = 0;
		} else if (Arg < 64) {
			Low = Mid >>> (Arg - 32);
			Low |= Hi << (64 - Arg);
			Mid = Hi >>> (Arg - 32);
			Hi = 0;
		} else if (Arg == 64) {
			Low = Hi;
			Mid = 0;
			Hi = 0;
		} else if (Arg < 96) {
			Low = Hi >>> (Arg - 64);
			Mid = 0;
			Hi = 0;
		} else {
			Low = 0;
			Mid = 0;
			Hi = 0;
		}
	}

	public static int CheckSum(final BitBoard Arg) {
		int temp1,temp2;
		temp1 = Arg.Low ^ Arg.Mid ^ Arg.Hi;
		temp2 = (temp1&0xFFFF) ^ (temp1>>>16);
		return (temp2&0xFF) ^ (temp2>>>8);
	}

	public static BitBoard Duplicate(int Arg) {
		int temp1,temp2,temp16;
		int temp32;
		temp1 = (Arg&0xFF);
		temp2 = temp1|(temp1<<8);
		temp32 = temp2|(temp2<<16);
		return new BitBoard(temp32, temp32, temp32);
	}

	//MSB = Most Significant Bit
	//������߷���λ�ǵڼ�λ(�����λ=0����)
	public static int Msb32(int Arg) {
		int RetVal;
		int TempArg;
		RetVal = 0;
		TempArg = Arg;
		if ((TempArg & 0xffff0000)!=0) {
			RetVal += 16;
			TempArg &= 0xffff0000;
		}
		if ((TempArg & 0xff00ff00)!=0) {
			RetVal += 8;
			TempArg &= 0xff00ff00;
		}
		if ((TempArg & 0xf0f0f0f0)!=0) {
			RetVal += 4;
			TempArg &= 0xf0f0f0f0;
		}
		if ((TempArg & 0xcccccccc)!=0) {
			RetVal += 2;
			TempArg &= 0xcccccccc;
		}
		if ((TempArg & 0xaaaaaaaa)!=0) {
			RetVal += 1;
		}
		return RetVal;
	}

	public static int MSB(BitBoard Arg) {
		if (Arg.Hi!=0) {
			return Msb32(Arg.Hi) + 64;
		} else if (Arg.Mid!=0) {
			return Msb32(Arg.Mid) + 32;
		} else if (Arg.Low!=0) {
			return Msb32(Arg.Low);
		} else {
			return -1;
		}
	}

	//LSB = Least Significant Bit
	//������ͷ���λ�ǵڼ�λ(�����λ=0����)
	public static int Lsb32(int Arg) {
		int RetVal;
		int TempArg;
		RetVal = 31;
		TempArg = Arg;
		if ((TempArg & 0x0000ffff)!=0) {
			RetVal -= 16;
			TempArg &= 0x0000ffff;
		}
		if ((TempArg & 0x00ff00ff)!=0) {
			RetVal -= 8;
			TempArg &= 0x00ff00ff;
		}
		if ((TempArg & 0x0f0f0f0f)!=0) {
			RetVal -= 4;
			TempArg &= 0x0f0f0f0f;
		}
		if ((TempArg & 0x33333333)!=0) {
			RetVal -= 2;
			TempArg &= 0x33333333;
		}
		if ((TempArg & 0x55555555)!=0) {
			RetVal -= 1;
		}
		return RetVal;
	}

	public static int LSB(final BitBoard Arg) {
		if (Arg.Low!=0) {
			return Lsb32(Arg.Low);
		} else if (Arg.Mid!=0) {
			return Lsb32(Arg.Mid) + 32;
		} else if (Arg.Hi!=0) {
			return Lsb32(Arg.Hi) + 64;
		} else {
			return -1;
		}
	}

	//�������λ����
	public static int Count32(int Arg) {
		int t;
		t = ((Arg >>> 1) & 0x55555555) + (Arg & 0x55555555);
		t = ((t >>> 2) & 0x33333333) + (t & 0x33333333);
		t = ((t >>> 4) & 0x0f0f0f0f) + (t & 0x0f0f0f0f);
		t = ((t >>> 8) & 0x00ff00ff) + (t & 0x00ff00ff);
		return (t >>> 16) + (t & 0x0000ffff);
	}

	public static int Count(final BitBoard Arg) {
		return Count32(Arg.Low) + Count32(Arg.Mid) + Count32(Arg.Hi);
	}
	
	//for test
	public String toString(){
		String tmpStr="";
		int index=0;
		for (;index<32;index++){
			if(index%10==0)
				tmpStr="\n"+tmpStr;
			tmpStr=(((Low&(1<<index))!=0)? "x":"0")+tmpStr;
		}
		for (;index<64;index++){
			if(index%10==0)
				tmpStr="\n"+tmpStr;
			tmpStr=(((Mid&(1<<(index-32)))!=0)? "x":"0")+tmpStr;			
		}
		for (;index<90;index++){
			if(index%10==0)
				tmpStr="\n"+tmpStr;
			tmpStr=(((Hi&(1<<(index-64)))!=0) ? "x":"0")+tmpStr;			
		}
		return tmpStr;
	}
	//test
	public static void main(String[] args){
		BitBoard b1 = new BitBoard(3,3);
		System.out.println(b1);
		System.out.println(BitBoard.MSB(b1));
		//System.out.println(CheckSum(b1));
		//b1.leftShift(56);
		//System.out.println(b1);
		//b1.rightShift(50);
		//System.out.println(b1);
		//System.out.println(Count(b1));
		BitBoard b2 = new BitBoard(41,42,65);
		System.out.println(b2);
		
		//System.out.println(Count(b2));
		//System.out.println(b1.opAnd(b2));
		//System.out.println(b1.opOr(b2));
		//System.out.println(b1.opXor(b2));
		//System.out.println(b1.opNot());
		
	}
}
