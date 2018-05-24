package com.lshy.engine;

import java.io.Serializable;
import java.util.ArrayList;

public class ActiveBoard implements Serializable{
	//Rank[x],File[x],Bottom[x] �� x % 10,x / 10,x * 10�����
	public static final int[] RANK = {// File[19]=1,Rank[19]=9;
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9
	};

	public static final int FILE[] = {// File[12]=1,Rank[12]=2;
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
		4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		8, 8, 8, 8, 8, 8, 8, 8, 8, 8
	};

	public static final int[] BOTTOM = {
		0, 10, 20, 30, 40, 50, 60, 70, 80
	};

	public static final int[] HORSE_LEG_TABLE = {//int 
		-10,  0,-10,  0,  0,  0,  0,  0,  0, -1,  0,  
		  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,
		  0,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0,
		  1,  0,  0,  0,  0,  0,  0,	10,  0, 10
		//Move.Dst - Move.Src={-21,-19,-12,-8,8,12,19,21}
		//HorseLeg[Dst-Src+21]={-10,-10,-1,1,-1,,1,10,10}:�����ȵ�����
		//Legal Move: return Squares[Move.Src + HorseLegTab[Move.Dst - Move.Src + 21]]==0
	};

	public static final int[] PIECE_TYPES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 6, 6, 6,
		7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 13, 13, 13
	};
	public final static int MAX_MOVE_NUM = 256;
	public final static int LOOP_HASH_MASK = 0x3ff;
	public final static int MAX_CONSECUTIVE_MOVES = 200;

	private int player; // 0 = Red(White) and 1 = Black
	private int[] evalue;//int[2]Total Value of Red(0)/Black(1) Pieces
	private int[] squares;//int[90]Piece Indexes of each square, Squares[i] = 0:Unoccupied, 16-31:Red, 32-47 = Black
	private int[] pieces;//int[48]Square Indexes of each piece, -1 = Captured//������
	// Square[x]=y(y: index of PieceTypes in Square[x]),
	// Pieces[y]=x(y is the pieceType, x represent pieceType Y in Square 5),
	// x:index of location in board(0~89)
	private int[] bitFiles;//[9]���㰴��λ���߲�ѯBitFiles[1]��ʾ��1��(b����)�ϵ�����
	private int[] bitRanks;//[10]���㰴��λ���߲�ѯ
	private BitBoard[] pieceBits;//[14]�ֱ��ּ���ڵ�����λ����
	private BitBoard allPieces;//��������

	// Zobrist Key and Lock
	private long zobristKey, zobristLock;
	
	// History MoveNodes,������Ϊѭ���ظ����
	private int moveNum;
	MoveNode[] moveList;//[ChessStruct.MaxMoveNum];
	char[] loopHash;//[LoopHashMask + 1];
	
	public ActiveBoard() {
		int i;
		player = 0;
		evalue = new int[2];
		evalue[0] = evalue[1] = 0;
		squares = new int[90];
		for (i = 0; i < 90; i ++) {
			squares[i] = 0;
		}
		pieces = new int[48];
		for (i = 16; i < 48; i ++) {
			pieces[i] = -1;
		}
		bitFiles = new int[9];
		for (i = 0; i < 9; i ++) {
			bitFiles[i] = 0;
		}
		bitRanks = new int[10];
		for (i = 0; i < 10; i ++) {
			bitRanks[i] = 0;
		}
		pieceBits = new BitBoard[14];
		for (i = 0; i < 14; i ++) {
			pieceBits[i] = new BitBoard(0);
		}
		allPieces = new BitBoard(0);
		zobristKey = zobristLock = 0;
		moveNum = 1;
		moveList = new MoveNode[MAX_MOVE_NUM];
		for (i = 0;i< MAX_MOVE_NUM;i++){
			moveList[i]=new MoveNode();
		}
		loopHash=new char[LOOP_HASH_MASK+1];
		for (i = 0; i < LOOP_HASH_MASK+1; i ++) {
			loopHash[i] = 0;
		}
	}
	private void changeSide() {
		player = 1 - player;
		zobristKey ^= PreMoveNodesGen.ZobristKeyPlayer;
		zobristLock ^= PreMoveNodesGen.ZobristLockPlayer;
	}
	private void clearSquare(int Square) {
		int Piece;
		Piece = squares[Square];
		squares[Square] = 0;
		pieces[Piece] = -1;
		changePiece(Square, Piece);
	}
	private void clearPiece(int Piece) {
		int Square;
		Square = pieces[Piece];
		squares[Square] = 0;
		pieces[Piece] = -1;
		changePiece(Square, Piece);
	}
	private void setPiece(int Square, int Piece) {
		squares[Square] = Piece;
		pieces[Piece] = Square;
		changePiece(Square, Piece, true);
	}
	public void nullMove() {
		MoveNode ThisMove=new MoveNode();
		changeSide();
		ThisMove.src = ThisMove.dst = ThisMove.cap = -1;
		ThisMove.chk = false;
		moveList[moveNum] = ThisMove;
		moveNum ++;
	}
	public void undoNull() {
		moveNum --;
		changeSide();
	}
	// Move Detection Procedures
	public boolean narrowCap(MoveNode Move){
		return narrowCap(Move,false);
	}
	public boolean narrowCap(MoveNode Move, boolean AdvisorBishop){//�Ƿ����
		int Captured;
		//Move.Dst=00010001 or 00010010:red����
		//Move.Dst=00100010 or 00100010:blackʿ��
		//>00010010 or >00100010:��������
		Captured = squares[Move.dst] & 0xf;
		if (Captured > 10) {
			Captured = RANK[Move.dst];
			return (player!=0) ? (Captured >= 5) : (Captured <= 4);
		} else {
			return AdvisorBishop || Captured > 4;
		}
	}
	public MoveNode lastMove(){
		return moveList[moveNum - 1];
	}
	public int evaluation() {
		return evalue[player] - evalue[1 - player];
	}
	private void changePiece(int Square, int Piece){
		changePiece(Square,Piece,false);
	}
	private void changePiece(int Square, int Piece, boolean IsAdd) {
		int x, y, PieceType, Side, Value;
		allPieces.assignXor(PreMoveNodesGen.BitMask[Square]);
		x = FILE[Square];
		y = RANK[Square];
		bitFiles[x] ^= 1 << y;
		bitRanks[y] ^= 1 << x;
		PieceType = PIECE_TYPES[Piece];
		pieceBits[PieceType].assignXor(PreMoveNodesGen.BitMask[Square]);
		zobristKey ^= PreMoveNodesGen.ZobristKeyTable[PieceType][Square];
		zobristLock ^= PreMoveNodesGen.ZobristLockTable[PieceType][Square];
		if (PieceType < 7) {
			Side = 0;
			Value = CCEvalue.BasicValues[PieceType] + CCEvalue.PosValues[PieceType][Square];
		} else {
			Side = 1;
			Value = CCEvalue.BasicValues[PieceType - 7] + CCEvalue.PosValues[PieceType - 7][89 - Square];
		}
		if (IsAdd) {
			evalue[Side] += Value;
		} else {
			evalue[Side] -= Value;
		}
	}
	public boolean movePiece(MoveNode Move){
		int Moved, Captured;
		MoveNode ThisMove;
		long OldZobristKey;
		if (Move.src<0 || Move.dst<0) return false;//add for search function
		OldZobristKey = zobristKey;
		Moved = squares[Move.src];
		Captured = squares[Move.dst];
		if (Captured!=0) {
			clearSquare(Move.dst);
		}
		clearSquare(Move.src);
		setPiece(Move.dst, Moved);
		if (checked(player)) {
			Moved = squares[Move.dst];
			clearSquare(Move.dst);
			setPiece(Move.src, Moved);
			if (Captured!=0) {
				setPiece(Move.dst, Captured);
			}
			return false;
		} else {
			if (loopHash[(int) (OldZobristKey & LOOP_HASH_MASK)]==0) {
				loopHash[(int) (OldZobristKey & LOOP_HASH_MASK)] = (char) moveNum;
			}
			changeSide();
			ThisMove = Move;
			ThisMove.cap = Captured;
			ThisMove.chk = checked(player);
			moveList[moveNum] = ThisMove;
			moveNum ++;
			return true;
		}
	}
	public void undoMove() {
		int Moved;
		MoveNode ThisMove;
		moveNum --;
		ThisMove = moveList[moveNum];
		Moved = squares[ThisMove.dst];
		clearSquare(ThisMove.dst);
		setPiece(ThisMove.src, Moved);
		if (ThisMove.cap!=0) {
			setPiece(ThisMove.dst, ThisMove.cap);
		}
		changeSide();
		if (loopHash[(int) (zobristKey & LOOP_HASH_MASK)] == moveNum) {
			loopHash[(int) (zobristKey & LOOP_HASH_MASK)] = 0;
		}
	}

	//	Leagal Move Detection Procedures
	public boolean leagalMove(MoveNode Move){
		int Piece, Attack, x, y, BitWord;
		Piece = squares[Move.src];
		if ((Piece & (player!=0 ? 32 : 16))==0) {
			return false;//��ѡ�������Ƿ��ǵ�ǰPlayer��
		}
		Attack = squares[Move.dst];
		if ((Attack & (player!=0 ? 32 : 16))!=0) {
			return false;//���Ե������Ƿ��ǶԷ���
		}
		switch (PIECE_TYPES[Piece] - (player!=0 ? 7 : 0)) {
		case 5://��,����ʱ�м�Ҫ���ڼ�
			x = FILE[Move.src];
			y = RANK[Move.src];
			if (x == FILE[Move.dst]) {//����
				BitWord = bitFiles[x];
				if (Move.src < Move.dst) {//��
					if ((Attack & (player!=0 ? 16 : 32))!=0) {//����
						return Move.dst == PreMoveNodesGen.FileCannonCapMax[y][BitWord] + BOTTOM[x];
					} else {//������
						return Move.dst <= PreMoveNodesGen.FileNonCapMax[y][BitWord] + BOTTOM[x];
					}
				} else {//Move.Src > Move.Dst,��
					if ((Attack & (player!=0 ? 16 : 32))!=0) {
						return Move.dst == PreMoveNodesGen.FileCannonCapMin[y][BitWord] + BOTTOM[x];
					} else {
						return Move.dst >= PreMoveNodesGen.FileNonCapMin[y][BitWord] + BOTTOM[x];
					}
				}
			} else {//ƽ
				BitWord = bitRanks[y];
				if (Move.src < Move.dst) {
					if ((Attack & (player!=0 ? 16 : 32))!=0) {
						return Move.dst == PreMoveNodesGen.RankCannonCapMax[x][BitWord] + y;
					} else {
						return Move.dst <= PreMoveNodesGen.RankNonCapMax[x][BitWord] + y;
					}
				} else {
					if ((Attack & (player!=0 ? 16 : 32))!=0) {
						return Move.dst == PreMoveNodesGen.RankCannonCapMin[x][BitWord] + y;
					} else {
						return Move.dst >= PreMoveNodesGen.RankNonCapMin[x][BitWord] + y;
					}
				}
			}
		case 4://��,����ʱ���м䲻���м��
			x = FILE[Move.src];
			y = RANK[Move.src];
			if (x == FILE[Move.dst]) {
				BitWord = bitFiles[x];
				if (Move.src < Move.dst) {
					if ((Attack & (player!=0 ? 16 : 32))!=0) {
						return Move.dst == PreMoveNodesGen.FileRookCapMax[y][BitWord] + BOTTOM[x];
					} else {
						return Move.dst <= PreMoveNodesGen.FileNonCapMax[y][BitWord] + BOTTOM[x];
					}
				} else {
					if ((Attack & (player!=0 ? 16 : 32))!=0) {
						return Move.dst == PreMoveNodesGen.FileRookCapMin[y][BitWord] + BOTTOM[x];
					} else {
						return Move.dst >= PreMoveNodesGen.FileNonCapMin[y][BitWord] + BOTTOM[x];
					}
				}
			} else {
				BitWord = bitRanks[y];
				if (Move.src < Move.dst) {
					if ((Attack & (player!=0 ? 16 : 32))!=0) {
						return Move.dst == PreMoveNodesGen.RankRookCapMax[x][BitWord] + y;
					} else {
						return Move.dst <= PreMoveNodesGen.RankNonCapMax[x][BitWord] + y;
					}
				} else {
					if ((Attack & (player!=0 ? 16 : 32))!=0) {
						return Move.dst == PreMoveNodesGen.RankRookCapMin[x][BitWord] + y;
					} else {
						return Move.dst >= PreMoveNodesGen.RankNonCapMin[x][BitWord] + y;
					}
				}
			}
		case 3://��,����
			return squares[Move.src + HORSE_LEG_TABLE[Move.dst - Move.src + 21]]==0;
		case 2://��,������
			return squares[(Move.src + Move.dst) >> 1]==0;
		default://�˽���,���ر����
			return true;
		}
	}
	//��״̬���
	public boolean checked(int Side) {
		int SrcSq, Side7, BitFileWord, BitRankWord;
		SrcSq = pieces[(Side + 1) << 4];
		if (SrcSq == -1) {
			return true; // ����
		}
		BitFileWord = bitFiles[FILE[SrcSq]];
		BitRankWord = bitRanks[RANK[SrcSq]];
		SrcSq = PreMoveNodesGen.KingIndex[SrcSq];
		if (SrcSq == -1) {
			return true; // �����Ź���
		}
		Side7 = Side!=0 ? 7 : 0;
		if (((PreMoveNodesGen.FileRookCheck[SrcSq][BitFileWord].opXor(PreMoveNodesGen.RankRookCheck[SrcSq][BitRankWord])).opAnd(pieceBits[7 - Side7].opXor(pieceBits[11 - Side7]))).notZero()) {
			return true; // �Ͻ�����򱻳���
		}
		if (((PreMoveNodesGen.FileCannonCheck[SrcSq][BitFileWord].opXor(PreMoveNodesGen.RankCannonCheck[SrcSq][BitRankWord])).opAnd(pieceBits[12 - Side7])).notZero()){
			return true; // ��
		}
		
		BitBoard tmpBit00 = PreMoveNodesGen.CheckLegs[SrcSq];//�����ȵ�λ��
		BitBoard tmpBit0 = tmpBit00.opAnd(allPieces);//�����ȵ�λ�����ӵ�λ��
		int tmpI = BitBoard.CheckSum(PreMoveNodesGen.CheckLegs[SrcSq].opAnd(allPieces));
		int tmpI2 = BitBoard.CheckSum(tmpBit0);//tmpI==tmpI2?
		BitBoard tmpBit1 = PreMoveNodesGen.KnightPinCheck[SrcSq][tmpI];//���������ȵ�λ��(ʵ��)
		BitBoard tmpBit2 = pieceBits[10 - Side7];
		BitBoard tmpBit3 = tmpBit1.opAnd(tmpBit2);
		if ((PreMoveNodesGen.KnightPinCheck[SrcSq][BitBoard.CheckSum(PreMoveNodesGen.CheckLegs[SrcSq].opAnd(allPieces))].opAnd(pieceBits[10 - Side7])).notZero()) {
			return true; // ��
		}
		if ((PreMoveNodesGen.PawnCheck[SrcSq].opAnd(pieceBits[13 - Side7])).notZero()) {
			return true; // ����
		}
		return false;
	}

	//	�ظ����
	private final int loopMove = 1;
	private final int perpCheckMove = 2;
	private final int oppPerpCheckMove = 4;
	
	public int loopValue(int Arg, int MoveNum) {
		return ((Arg & perpCheckMove)!=0 ? MoveNum + 1 - CCEvalue.MaxValue : 0) + ((Arg & oppPerpCheckMove)!=0 ? CCEvalue.MaxValue - 1 - MoveNum : 0);
	}

	public int isLoop(int Recur){
		int Index, i, j, MovedPieces, ThisRecur;
		boolean OppSide,PerpCheck, OppPerpCheck;
		int[] DstLst=new int[MAX_MOVE_NUM], SrcLst=new int[MAX_MOVE_NUM];
		MoveNode ThisMove;
		if (loopHash[(int) (zobristKey & LOOP_HASH_MASK)]==0) {
			return 0;
		}
		ThisRecur = 0;
		OppSide = true;
		MovedPieces = 0;
		PerpCheck = OppPerpCheck = true;
		Index = moveNum;
		while (true) {
			Index --;
			ThisMove = moveList[Index];
			if (ThisMove.cap!=0) {
				break;
			}
			if (OppSide) {
				if (OppPerpCheck && ThisMove.chk) {
					OppPerpCheck = false;
				}
			} else {
				if (PerpCheck && ThisMove.chk) {
					PerpCheck = false;
				}
			}
			for (i = 0; i < MovedPieces; i ++) {
				if (SrcLst[i] == ThisMove.dst) {
					SrcLst[i] = ThisMove.src;
					if (!OppSide) {
						for (j = 0; j < MovedPieces; j ++) {
							if (SrcLst[j] != DstLst[j]) {
								break;
							}
						}
						if (j == MovedPieces) {
							ThisRecur ++;
							if (ThisRecur == Recur) {
								return loopMove + (PerpCheck ? perpCheckMove : 0) + (OppPerpCheck ? oppPerpCheckMove : 0);
							}
						}
					}
					break;
				}
			}
			if (i == MovedPieces) {
				DstLst[MovedPieces] = ThisMove.dst;
				SrcLst[MovedPieces] = ThisMove.src;
				MovedPieces ++;
			}
			OppSide = !OppSide;
		}
		return 0;
	}
	//Piece Types and Values
	public static int fenPiece(char Arg) {
		switch (Arg) {
			case 'K'://����˧
				return 0;
			case 'A'://��
				return 1;
			case 'B'://��
			case 'E'://��
				return 2;
			case 'N'://knight
			case 'H'://horse
				return 3;
			case 'R'://rook
				return 4;
			case 'C'://cannon
				return 5;
			default://�䣬��
				return 6;
		}
	}
	//------------------------------------------------------------
	public void loadFen(final String FenStr) {
		int i, j, k;
		int[] RedPiece=new int[7];
		int[] BlackPiece=new int[7];
		final char[] CharPtr;
		int index;
		// Init:
		RedPiece[0] = 16;
		for (i = 1; i < 7; i ++) {
			RedPiece[i] = (i << 1) + 15;
		}
		for (i = 0; i < 7; i ++) {
			BlackPiece[i] = RedPiece[i] + 16;
		}
		for (i = 16; i < 48; i ++) {
			if (pieces[i] != -1) {
				clearPiece(i);
			}
		}
		moveNum = 1;
		if (FenStr.length()==0) {
			return;
		}else{
			CharPtr = FenStr.toCharArray();
		}
		// Read Board:
		i = 0;
		j = 9;
		index=0;
		while (CharPtr[index] != ' ') {
			if (CharPtr[index] == '/') {
				i = 0;
				j --;
				if (j < 0) {
					break;
				}
			} else if (CharPtr[index] >= '1' && CharPtr[index] <= '9') {
				for (k = 0; k < (CharPtr[index] - '0'); k ++) {
					if (i >= 9) {
						break;
					}
					i ++;
				}
			} else if (CharPtr[index] >= 'A' && CharPtr[index] <= 'Z') {
				k = fenPiece(CharPtr[index]);
				if (i < 9) {
					if (RedPiece[k] < 32) {
						setPiece(BOTTOM[i] + j, RedPiece[k]);
						RedPiece[k] ++;
					}
				}
				i ++;
			} else if (CharPtr[index] >= 'a' && CharPtr[index] <= 'z') {
				k = fenPiece((char) (CharPtr[index] + 'A' - 'a'));
				if (i < 9) {
					if (BlackPiece[k] < 48) {
						setPiece(BOTTOM[i] + j, BlackPiece[k]);
						BlackPiece[k] ++;
					}
				}
				i ++;
			}
			index++;
			if (CharPtr[index] == '\0') {
				return;
			}
		}
		index ++;
		// Read Player:
		if (player == (CharPtr[index] == 'b' ? 0 : 1)) {
			changeSide();
		}
		// Set Check Status
		moveList[moveNum - 1].chk = checked(player);
		//catch (ArrayIndexOutOfBoundsException e){
		//	e.printStackTrace();
		//	System.out.println(this.AllPieces);
		//}
	}
	public String getFenStr(){//current state FenStr
		ArrayList pieceList = new ArrayList();
		int i,j,k,pieceType;
		int FilePieces;
		int low,high;
		int bitMask = 1<<8;
		String FileStr="";
		for (i=9;i>=0;i--){
			FilePieces=bitRanks[i];
			for (j=8;j>=0;j--){
				if ((FilePieces & (bitMask>>j))==0){
					FileStr=FileStr+"1";
				}else{
					int tmpPiece = squares[(8-j)*10+i];
					FileStr=FileStr+getFenName(PIECE_TYPES[tmpPiece]);
				}	
			}
			if (i!=0)
				FileStr=FileStr+"/";
		}
		if(player==0)
			FileStr=FileStr + " w - - 0 " + (moveNum+1)/2;
		else
			FileStr=FileStr + " b - - 0 " + (moveNum+1)/2;
		i=0;
		String retVal="";
		if (FileStr.charAt(i)!='1'){
			retVal = retVal+FileStr.charAt(i);
			i++;
		}
		int maxId = FileStr.indexOf('-');
		while(i<maxId){
			int m = 0;
			while(FileStr.charAt(i)=='1'){
				m++;i++;
			}
			if(m!=0){
				retVal=retVal+m;
			}
			if(i<maxId){
				retVal=retVal+FileStr.charAt(i);
				i++;
			}
		}
		retVal = retVal + FileStr.substring(FileStr.indexOf('-'));
		return retVal;
	}
	public char getFenName(int pieceTypeNum){
	//rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR w - - 0 1
		switch (pieceTypeNum%7){
			case 0 ://����˧
				return (pieceTypeNum < 7) ? 'K':'k';
			case 1://��
				return (pieceTypeNum < 7) ? 'A':'a';
			case 2://��
				return (pieceTypeNum < 7) ? 'B':'b';
			case 3://knight
				return (pieceTypeNum < 7) ? 'N':'n';
			case 4://rook
				return (pieceTypeNum < 7) ? 'R':'r';
			case 5://cannon
				return (pieceTypeNum < 7) ? 'C':'c';
			default://�䣬��
				return (pieceTypeNum < 7) ? 'P':'p';
		}
	}
	public static void main(String[] args){
		System.out.println("new ChessPosition");
		ActiveBoard cp = new ActiveBoard();
		String tmpStr = "nbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR w - - 0 1";
		cp.loadFen(tmpStr);
		System.out.println(cp.allPieces);
		
		ActiveBoard cp1 = new ActiveBoard();
		cp1.loadFen(tmpStr);
		System.out.println(cp1.allPieces);
		
		ActiveBoard cp2 = new ActiveBoard();
		cp2.loadFen(tmpStr);
		System.out.println(cp2.allPieces);
	}
	public int getPlayer() {
		return player;
	}
	public int getOppPlayer(){
		return 1-player;
	}
	public int getSquares(int dstSq) {
		return squares[dstSq];
	}
	public long getZobristKey() {
		return zobristKey;
	}
	public long getZobristLock() {
		return zobristLock;
	}
	public int getMoveNum() {
		return moveNum;
	}
	public int getEvalue(int redOrBlack){return evalue[redOrBlack];}
	public int getPieces(int i) {
		return pieces[i];
	}
	public int getBitFiles(int x) {
		return bitFiles[x];
	}
	public int getBitRanks(int y) {
		return bitRanks[y];
	}
	public BitBoard getPieceBits(int piece) {
		return pieceBits[piece];
	}
}
