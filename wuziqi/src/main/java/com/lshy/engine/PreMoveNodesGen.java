package com.lshy.engine;

import java.io.IOException;
import java.util.Random;

public class PreMoveNodesGen {
	private static Random rand = new Random();
	public static long ZobristKeyPlayer;
	public static long ZobristLockPlayer;
	public static long[][] ZobristKeyTable = new long[14][90];
	public static long[][] ZobristLockTable = new long[14][90];

	//	PreMoveNodesGen Procedures
	public static final int[] KingIndex = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 0,  1,  2, -1, -1, -1, -1,  3,  4,  5,
		 6,  7,  8, -1, -1, -1, -1,  9, 10, 11,
		12, 13, 14, -1, -1, -1, -1, 15, 16, 17,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1
	};

	//���ڽ���״̬�ĸ���λ����!
	public static final BitBoard[] BitMask=new BitBoard[90];
	public static final BitBoard[] CheckLegs=new BitBoard[18];//˧��ÿ��λ�ñ����ȵ�λ����
	public static final BitBoard[][] KnightPinCheck=new BitBoard[18][256];
	//��ȥ����λ�����ӵĽ���λ��
	public static final BitBoard[][] FileRookCheck=new BitBoard[18][1024];
	//FileRookCheck[indexOfKing][bitBoardOfRook]=checkBitBoardOfRook
	public static final BitBoard[][] FileCannonCheck=new BitBoard[18][1024];
	public static final BitBoard[][] RankRookCheck=new BitBoard[18][512];
	public static final BitBoard[][] RankCannonCheck=new BitBoard[18][512];
	public static final BitBoard[] PawnCheck=new BitBoard[18];

	//Arrays for Move Generations
	public static final int[][] KingMoves=new int[90][8];
	//KingMoves[oldLoc][index]=newLoc(-1=invalid)
	public static final int[][] AdvisorMoves=new int[90][8];
	public static final int[][] BishopMoves=new int[90][8];
	public static final int[][] ElephantEyes=new int[90][4];
	public static final int[][] KnightMoves=new int[90][12];
	public static final int[][] HorseLegs=new int[90][8];
	public static final int[][][] FileNonCapMoves=new int[10][1024][12];
	//��ʮ������,FileNonCapMoves[y][bitWordY][index]=newY,�� 
	public static final int[][][] FileRookCapMoves=new int[10][1024][4];
	public static final int[][][] FileCannonCapMoves=new int[10][1024][4];
	public static final int[][][] RankNonCapMoves=new int[9][512][12];
	//RankNonCapMoves[x][bitWordX][index]=newX,ƽ
	public static final int[][][] RankRookCapMoves=new int[9][512][4];
	public static final int[][][] RankCannonCapMoves=new int[9][512][4];
	public static final int[][][] PawnMoves=new int[90][2][4];
	//PawnMoves[oldLoc][redOrblack][index]=newLoc
	//Arrays for Move Validitiy Detections
	public static final int[][] FileNonCapMax=new int[10][1024];
	//FileNonCapMax[y][bitwordY]=MaxY//����
	public static final int[][] FileNonCapMin=new int[10][1024];
	//FileNonCapMax[y][bitwordY]=MinY
	public static final int[][] FileRookCapMax=new int[10][1024];
	public static final int[][] FileRookCapMin=new int[10][1024];
	public static final int[][] FileCannonCapMax=new int[10][1024];
	public static final int[][] FileCannonCapMin=new int[10][1024];
	public static final int[][] RankNonCapMax=new int[9][512];//ƽ
	public static final int[][] RankNonCapMin=new int[9][512];
	public static final int[][] RankRookCapMax=new int[9][512];
	public static final int[][] RankRookCapMin=new int[9][512];
	public static final int[][] RankCannonCapMax=new int[9][512];
	public static final int[][] RankCannonCapMin=new int[9][512];

	static{
		zobristGen();
		preMoveGen();
	}

//--------------------------------------------------------------------------
	private PreMoveNodesGen(){
	}
//---------------------------------------------------------------------------
	public static void zobristGen() {
		int i, j;
		long RandSeed;
		RandSeed = 1;
		rand.setSeed(RandSeed);
		ZobristKeyPlayer = rand.nextLong();
		for (i = 0; i < 14; i ++) {
			for (j = 0; j < 90; j ++) {
				ZobristKeyTable[i][j] = rand.nextLong();
			}
		}
		ZobristLockPlayer = rand.nextLong();
		for (i = 0; i < 14; i ++) {
			for (j = 0; j < 90; j ++) {
				ZobristLockTable[i][j] = rand.nextLong();
			}
		}
	}
//--------------------------------------------------------------------------
	private static boolean InCity(int x, int y) {
		return (x >= 3 && x <= 5 && (y >= 0 && y <= 2 || y >= 7 && y <= 9));
	}

	private static boolean InBoard(int x, int y) {
		return (x >= 0 && x <= 8 && y >= 0 && y <= 9);
	}

	private static void preMoveGen() {
		int i, j, k, l, m, Index, IndexKing;
		BitBoard TempBoard;
		for (i = 0; i < 90; i ++) {
			BitMask[i] = new BitBoard(1, 0, 0);
			BitMask[i].leftShift(i);
		}
		for (i = 0; i < 9; i ++) {
			for (j = 0; j < 10; j ++) {	
				IndexKing = KingIndex[i * 10 + j];
				if (IndexKing != -1) {		
					// ����Nodes
					CheckLegs[IndexKing] = new BitBoard(0);
					for (k = -1; k <= 1; k += 2) {
						for (l = -1; l <= 1; l += 2) {
							CheckLegs[IndexKing].assignOr(BitMask[(i + k) * 10 + j + l]);
						}
					}
					for (k = 0; k < 256; k ++) {
						KnightPinCheck[IndexKing][k] = new BitBoard(0);
						TempBoard = CheckLegs[IndexKing].opAnd(BitBoard.Duplicate(k));
						for (l = -1; l <= 1; l += 2) {
							for (m = -1; m <= 1; m += 2) {
								if (InBoard(i + l, j + m * 2)) {
									if (!((TempBoard.opAnd(BitMask[(i + l) * 10 + j + m])).notZero())) {
										KnightPinCheck[IndexKing][k].assignOr(BitMask[(i + l) * 10 + j + m * 2]);
									}
								}
								if (InBoard(i + l * 2, j + m)) {
									if (!((TempBoard.opAnd(BitMask[(i + l) * 10 + j + m])).notZero())) {
										KnightPinCheck[IndexKing][k].assignOr(BitMask[(i + l * 2) * 10 + j + m]);
									}
								}
							}
						}
					}

					// ���ڽ�����File��Nodes
					for (k = 0; k < 1024; k ++) {
						FileRookCheck[IndexKing][k] = new BitBoard(0);
						FileCannonCheck[IndexKing][k] = new BitBoard(0);
						for (l = j + 1; l <= 9; l ++) {
							if ((k & (1 << l))!=0) {
								FileRookCheck[IndexKing][k].assignOr(BitMask[i * 10 + l]);
								l ++;
								break;
							}
						}
						for (; l <= 9; l ++) {//ͨ�������ѭ���Ѹ�һ��
							if ((k & (1 << l))!=0) {
								FileCannonCheck[IndexKing][k].assignOr(BitMask[i * 10 + l]);
								break;
							}
						}
						for (l = j - 1; l >= 0; l --) {
							if ((k & (1 << l))!=0) {
								FileRookCheck[IndexKing][k].assignOr(BitMask[i * 10 + l]);
								l --;
								break;
							}
						}
						for (; l >= 0; l --) {
							if ((k & (1 << l))!=0) {
								FileCannonCheck[IndexKing][k].assignOr(BitMask[i * 10 + l]);
								break;
							}
						}
					}

					// ���ڽ�����Rank��Nodes
					for (k = 0; k < 512; k ++) {
						RankRookCheck[IndexKing][k] = new BitBoard(0);
						RankCannonCheck[IndexKing][k] = new BitBoard(0);
						for (l = i + 1; l <= 8; l ++) {
							if ((k & (1 << l))!=0) {
								RankRookCheck[IndexKing][k].assignOr(BitMask[l * 10 + j]);
								l ++;
								break;
							}
						}
						for (; l <= 8; l ++) {
							if ((k & (1 << l))!=0) {
								RankCannonCheck[IndexKing][k].assignOr(BitMask[l * 10 + j]);
								break;
							}
						}
						for (l = i - 1; l >= 0; l --) {
							if ((k & (1 << l))!=0) {
								RankRookCheck[IndexKing][k].assignOr(BitMask[l * 10 + j]);
								l --;
								break;
							}
						}
						for (; l >= 0; l --) {
							if ((k & (1 << l))!=0) {
								RankCannonCheck[IndexKing][k].assignOr(BitMask[l * 10 + j]);
								break;
							}
						}
					}

					// ������Nodes
					PawnCheck[IndexKing] = new BitBoard(0);
					PawnCheck[IndexKing].assignOr(BitMask[j >= 5 ? i * 10 + j - 1 : i * 10 + j + 1]);//����ǰ��
					for (l = -1; l <= 1; l += 2) {//��������
						PawnCheck[IndexKing].assignOr(BitMask[(i + l) * 10 + j]);
					}
				}

				// ���߷�Nodes
				Index = 0;
				for (k = -1; k <= 1; k += 2) {
					if (InCity(i + k, j)) {//ƽ
						KingMoves[i * 10 + j][Index] =  (i + k) * 10 + j;
						Index ++;
					}
					if (InCity(i, j + k)) {
						KingMoves[i * 10 + j][Index] =  i * 10 + j + k;
						Index ++;
					}
				}
				KingMoves[i * 10 + j][Index] =  -1;

				// ʿNodes
				Index = 0;
				for (k = -1; k <= 1; k += 2) {
					for (l = -1; l <= 1; l += 2) {
						if (InCity(i + k, j + l)) {
							AdvisorMoves[i * 10 + j][Index] = (i + k) * 10 + j + l;
							Index ++;
						}
					}
				}
				AdvisorMoves[i * 10 + j][Index] =  -1;

				// ��Nodes
				Index = 0;
				for (k = -2; k <= 2; k += 4) {
					for (l = -2; l <= 2; l += 4) {
						if (InBoard(i + k, j + l)) {
							if (InBoard(i + k, j + l) && (j >= 5 && j + l >= 5 || j <= 4 && j + l <= 4)) {
								BishopMoves[i * 10 + j][Index] =  (i + k) * 10 + j + l;
								ElephantEyes[i * 10 + j][Index] =  (i + k / 2) * 10 + j + l / 2;
								Index ++;
							}
						}
					}
				}
				BishopMoves[i * 10 + j][Index] =  -1;

				// ��Nodes
				Index = 0;
				for (k = -1; k <= 1; k += 2) {
					for (l = -2; l <= 2; l += 4) {
						if (InBoard(i + k, j + l)) {
							KnightMoves[i * 10 + j][Index] = (i + k) * 10 + j + l;
							HorseLegs[i * 10 + j][Index] = i * 10 + j + l / 2;
							Index ++;
						}
						if (InBoard(i + l, j + k)) {
							KnightMoves[i * 10 + j][Index] = (i + l) * 10 + j + k;
							HorseLegs[i * 10 + j][Index] = (i + l / 2) * 10 + j;
							Index ++;
						}
					}
				}
				KnightMoves[i * 10 + j][Index] = -1;

				// ��Nodes
				for (k = 0; k <= 1; k ++) {
					Index = 0;
					if (InBoard(i, k==1 ? j - 1 : j + 1)) {
						PawnMoves[i * 10 + j][k][Index] = (k==1 ? i * 10 + j - 1 : i * 10 + j + 1);
						Index ++;
					}
					if (k==1 ? j <= 4 : j >= 5) {
						for (l = -1; l <= 1; l += 2) {
							if (InBoard(i + l, j)) {
								PawnMoves[i * 10 + j][k][Index] = (i + l) * 10 + j;
								Index ++;
							}
						}
					}
					PawnMoves[i * 10 + j][k][Index] =  -1;
				}
			}//end of j loop
		}//end of i loop

		// Generate FilePreMoveNodes for Rooks and Cannons
		for (i = 0; i < 10; i ++) {
			for (j = 0; j < 1024; j ++) {
				Index = 0;
				FileNonCapMax[i][j] = i;
				for (k = i + 1; k <= 9; k ++) {
					if ((j & (1 << k))!=0) {
						break;
					}
					FileNonCapMoves[i][j][Index] =  k;
					Index ++;
					FileNonCapMax[i][j] =  k;
				}
				FileNonCapMin[i][j] =  i;
				for (k = i - 1; k >= 0; k --) {
					if ((j & (1 << k))!=0) {
						break;
					}
					FileNonCapMoves[i][j][Index] =  k;
					Index ++;
					FileNonCapMin[i][j] =  k;
				}
				FileNonCapMoves[i][j][Index] =  -1;
				Index = 0;
				FileRookCapMax[i][j] =  i;
				for (k = i + 1; k <= 9; k ++) {
					if ((j & (1 << k))!=0) {
						FileRookCapMoves[i][j][Index] =  k;
						Index ++;
						FileRookCapMax[i][j] =  k;
						break;
					}
				}
				FileRookCapMin[i][j] =  i;
				for (k = i - 1; k >= 0; k --) {
					if ((j & (1 << k))!=0) {
						FileRookCapMoves[i][j][Index] =  k;
						Index ++;
						FileRookCapMin[i][j] =  k;
						break;
					}
				}
				FileRookCapMoves[i][j][Index] =  -1;
				Index = 0;
				FileCannonCapMax[i][j] =  i;
				for (k = i + 1; k <= 9; k ++) {
					if ((j & (1 << k))!=0) {
						k ++;
						break;
					}
				}
				for (; k <= 9; k ++) {
					if ((j & (1 << k))!=0) {
						FileCannonCapMoves[i][j][Index] =  k;
						Index ++;
						FileCannonCapMax[i][j] =  k;
						break;
					}
				}
				FileCannonCapMin[i][j] =  i;
				for (k = i - 1; k >= 0; k --) {
					if ((j & (1 << k))!=0) {
						k --;
						break;
					}
				}
				for (; k >= 0; k --) {
					if ((j & (1 << k))!=0) {
						FileCannonCapMoves[i][j][Index] =  k;
						Index ++;
						FileCannonCapMin[i][j] =  k;
						break;
					}
				}
				FileCannonCapMoves[i][j][Index] =  -1;
			}
		}

		// Generate RankPreMoveNodes for Rooks and Cannons
		for (i = 0; i < 9; i ++) {
			for (j = 0; j < 512; j ++) {
				Index = 0;
				RankNonCapMax[i][j] =  (i * 10);
				for (k = i + 1; k <= 8; k ++) {
					if ((j & (1 << k))!=0) {
						break;
					}
					RankNonCapMoves[i][j][Index] =  (k * 10);
					Index ++;
					RankNonCapMax[i][j] =  (k * 10);
				}
				RankNonCapMin[i][j] =  (i * 10);
				for (k = i - 1; k >= 0; k --) {
					if ((j & (1 << k))!=0) {
						break;
					}
					RankNonCapMoves[i][j][Index] =  (k * 10);
					Index ++;
					RankNonCapMin[i][j] =  (k * 10);
				}
				RankNonCapMoves[i][j][Index] = -1;
				Index = 0;
				RankRookCapMax[i][j] =  (i * 10);
				for (k = i + 1; k <= 8; k ++) {
					if ((j & (1 << k))!=0) {
						RankRookCapMoves[i][j][Index] =  (k * 10);
						Index ++;
						RankRookCapMax[i][j] =  (k * 10);
						break;
					}
				}
				RankRookCapMin[i][j] =  (i * 10);
				for (k = i - 1; k >= 0; k --) {
					if ((j & (1 << k))!=0) {
						RankRookCapMoves[i][j][Index] =  (k * 10);
						Index ++;
						RankRookCapMin[i][j] =  (k * 10);
						break;
					}
				}
				RankRookCapMoves[i][j][Index] =  -1;
				Index = 0;
				RankCannonCapMax[i][j] =  (i * 10);
				for (k = i + 1; k <= 8; k ++) {
					if ((j & (1 << k))!=0) {
						k ++;
						break;
					}
				}
				for (; k <= 8; k ++) {
					if ((j & (1 << k))!=0) {
						RankCannonCapMoves[i][j][Index] =  (k * 10);
						Index ++;
						RankCannonCapMax[i][j] =  (k * 10);
						break;
					}
				}
				RankCannonCapMin[i][j] =  (i * 10);
				for (k = i - 1; k >= 0; k --) {
					if ((j & (1 << k))!=0) {
						k --;
						break;
					}
				}
				for (; k >= 0; k --) {
					if ((j & (1 << k))!=0) {
						RankCannonCapMoves[i][j][Index] =  (k * 10);
						Index ++;
						RankCannonCapMin[i][j] =  (k * 10);
						break;
					}
				}
				RankCannonCapMoves[i][j][Index] =  -1;
			}
		}
		return;
	}
	//for test
	public static void main(String[] args) throws IOException{
		System.out.println("-------------------------------------");
		//System.out.println("KnightMoves[44][i]:");
		//for(int i=0;i<12;i++)
		//	System.out.print((int)KnightMoves[44][i]+"   ");
		
		//System.out.println("\nKnightMoves[44][i](Knight Leg):");
		//for(int i=0;i<8;i++)
		//	System.out.print((int)HorseLegs[44][i]+"   ");	
		
		//System.out.println("\nFileNonCapMoves[2][0][i]:");
		//for(int i=0;i<12;i++)
		//	System.out.print((int)FileNonCapMoves[2][0][i]+"   ");
		
		//System.out.println("\nFileNonCapMoves[2][129][i]:");
		//for(int i=0;i<12;i++)
		//	System.out.print((int)FileNonCapMoves[2][129][i]+"   ");
			
		//System.out.println("\nFileCannonCapMoves[2][129][i]:");
		//for(int i=0;i<4;i++)
		//	System.out.print((int)FileCannonCapMoves[2][224][i]+"   ");
		//for(int i=0;i<18;i++){
		//System.out.println("\nPawnCheck["+i+"]");
		//System.out.println(PawnCheck[i]);	
		//}
		//for(int i=50;i<80;i++){
			//System.out.println("\nKnightPinCheck[8]["+i+"]");
			//System.out.println(KnightPinCheck[8][i]);
			//System.out.println(CheckLegs[i]);
			//System.out.println(BitBoard.CheckSum(CheckLegs[i]));
			//System.out.println(BitMask[i]);
		//}
		//System.in.read();
		for (int i=0;i<18;i++){
			System.out.println(i);
			System.out.println(CheckLegs[i]);		
		}
	}
}
