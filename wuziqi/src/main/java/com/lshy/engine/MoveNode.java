package com.lshy.engine;

public class MoveNode {
	public int src, cap, dst;//λ��a0=0,b0=10 
	public boolean chk;
	//	"cap" & "chk" are only used in history moves
	public MoveNode(){
		src = dst = cap = -1;
		chk = false;
	}
	//public MoveNode(int s, int d)
	public MoveNode(int s,int d){
		src = s; dst = d;
		chk=false;
	}
	public MoveNode(String moveStr){
		move(moveStr);chk=false;
	}
	public char[] location(){//����
		char[] loc=new char[4];
		loc[0] = (char) (ActiveBoard.FILE[src] + 'a');
		loc[1] = (char) (ActiveBoard.RANK[src] + '0');
		loc[2] = (char) (ActiveBoard.FILE[dst] + 'a');
		loc[3] = (char) (ActiveBoard.RANK[dst] + '0');
		return loc;
	}
	public void move(String moveStr) {
		src = (char) (ActiveBoard.BOTTOM[moveStr.charAt(0) - 'a'] + moveStr.charAt(1) - '0');
		dst = (char) (ActiveBoard.BOTTOM[moveStr.charAt(2) - 'a'] + moveStr.charAt(3) - '0');
		if (src < 0 || src >= 90 || dst < 0 || dst >= 90) {//invalid move
			src = dst = -1;
		}
	}
	//for test
	public String toString(){
		return (src>=0 && dst>=0)? String.copyValueOf(location()):src+"-->"+dst;
	}
}
