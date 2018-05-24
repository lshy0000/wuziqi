package com.lshy.engine;
public class SortedMoveNodes {
	public int MoveNum;
	public MoveNode[] MoveList;
	public int[] ValueList;
	public SortedMoveNodes(MoveNode[] MList,int[] VList){
		MoveList=MList;
		ValueList=VList;
	}
	public SortedMoveNodes(){
		MoveList=new MoveNode[ActiveBoard.MAX_MOVE_NUM];
		ValueList=new int[ActiveBoard.MAX_MOVE_NUM];
		for (int i=0;i<ActiveBoard.MAX_MOVE_NUM;i++){
			MoveList[i]=new MoveNode();
			ValueList[i]=0;
		}
	}
	public void GenMoves(final ActiveBoard Position, final int HistTab[][]) {
		MoveNum=0;
		GenKingMoves(Position, HistTab);
		GenAdvisorMoves(Position, HistTab);
		GenBishopMoves(Position, HistTab);
		GenKnightMoves(Position, HistTab);
		GenRookMoves(Position, HistTab);
		GenCannonMoves(Position, HistTab);
		GenPawnMoves(Position, HistTab);
	}

	//Sort Procedures

	//"1, 4, 13, 40 ..." is better than "1, 2, 4, 8, ..."
	public static final int[] ShellStep = {0, 1, 4, 13, 40, 121, 364, 1093};
	public final int[] MvvValues = {//int 48
		//�������ߵ���ʧ:˧�������ڱ�
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		0, 4, 4, 4, 4, 8, 8, 12, 12, 8, 8, 4, 4, 4, 4, 4,
		0, 4, 4, 4, 4, 8, 8, 12, 12, 8, 8, 4, 4, 4, 4, 4
	};

	public void ShellSort() {
		int Step, StepIndex;
		int i, j, BestValue;
		MoveNode BestMove;
		StepIndex = 1;
		do {
			StepIndex ++;
		} while (ShellStep[StepIndex] < MoveNum);
		StepIndex --;
		while (StepIndex!=0) {
			Step = ShellStep[StepIndex];
			for (i = Step; i < MoveNum; i ++) {
				BestMove = MoveList[i];
				BestValue = ValueList[i];
				j = i - Step;
				while (j >= 0 && BestValue > ValueList[j]) {
					MoveList[j + Step] = MoveList[j];
					ValueList[j + Step] = ValueList[j];
					j -= Step;
				}
				MoveList[j + Step] = BestMove;
				ValueList[j + Step] = BestValue;
			}
			StepIndex --;
		}
	} 

	public void BubbleSortMax(int Index) {
		int i, TempValue;
		MoveNode TempMove;
		for (i = MoveNum - 1; i > Index; i --) {
			if (ValueList[i - 1] < ValueList[i]) {
				TempMove = MoveList[i - 1];
				MoveList[i - 1] = MoveList[i];
				MoveList[i] = TempMove;
				TempValue = ValueList[i - 1];
				ValueList[i - 1] = ValueList[i];
				ValueList[i] = TempValue;
			}
		}
	}

	//Move Generation Procedures, including MVV/LVA (for Caps) and History (for All) Heuristic
	
	public void GenKingMoves(final ActiveBoard Position, final int HistTab[][]) {
		int SrcSq, DstSq, Attack, AttTag;
		int[] DstArr=new int[8];
		int indexDst=0;
		int listIndex=MoveNum;
		AttTag = Position.getPlayer()!=0 ? 16 : 32;
		SrcSq = Position.getPieces(48 - AttTag);
		if (SrcSq != -1) {
			copyArray(DstArr, PreMoveNodesGen.KingMoves[SrcSq], 8);
			indexDst=0;
			DstSq = DstArr[indexDst]; 
			while (DstSq != -1) {
				Attack = Position.getSquares(DstSq);
				if ((Attack & AttTag)!=0) {//����
					MoveList[listIndex].src =SrcSq;
					MoveList[listIndex].dst =DstSq;
					if (HistTab == null) {
						ValueList[listIndex] = MvvValues[Attack]; // King = 3
					} else {
						ValueList[listIndex] = HistTab[SrcSq][DstSq];
					}
					listIndex++;
				} else if (HistTab != null && Attack==0) {
					MoveList[listIndex].src =SrcSq;
					MoveList[listIndex].dst =DstSq;
					ValueList[listIndex] = HistTab[SrcSq][DstSq];
					listIndex++;
				}
				indexDst++;
				DstSq = DstArr[indexDst];
			}
		}
		MoveNum = listIndex;//MovePtr - MoveList;
	}

	public void GenAdvisorMoves(final ActiveBoard Position, final int HistTab[][]) {
		int i, SrcSq, DstSq, Attack, AttTag;
		int DstArr[]=new int[8];
		int indexDst=0;
		int listIndex=MoveNum;
		AttTag = Position.getPlayer()!=0 ? 16 : 32;
		for (i = 1; i <= 2; i ++) {
			SrcSq = Position.getPieces(48 - AttTag + i);
			if (SrcSq != -1) {
				copyArray(DstArr, PreMoveNodesGen.AdvisorMoves[SrcSq], 8);
				indexDst=0;
				DstSq = DstArr[indexDst];
				while (DstSq != -1) {
					Attack = Position.getSquares(DstSq);
					if ((Attack & AttTag)!=0) {
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						if (HistTab == null) {
							ValueList[listIndex] = MvvValues[Attack] + 3; // Advisor = 0;
						} else {
							ValueList[listIndex] = HistTab[SrcSq][DstSq];
						}
						listIndex ++;
					} else if (HistTab != null && Attack==0) {
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;						
						ValueList[listIndex] = HistTab[SrcSq][DstSq];
						listIndex++;
					}
					indexDst++;
					DstSq = DstArr[indexDst];
				}
			}
		}
		MoveNum = listIndex;
	}

	public void GenBishopMoves(final ActiveBoard Position, final int HistTab[][]) {
		int i, SrcSq, DstSq, Attack, AttTag;
		int DstArr[]=new int[8], EyeArr[]=new int[4];
		int indexDst=0;
		int indexEye=0;
		int listIndex=MoveNum;
		AttTag = Position.getPlayer()!=0 ? 16 : 32;
		for (i = 3; i <= 4; i ++) {
			SrcSq = Position.getPieces(48 - AttTag + i);
			if (SrcSq != -1) {
				copyArray(DstArr, PreMoveNodesGen.BishopMoves[SrcSq], 8);
				copyArray(EyeArr, PreMoveNodesGen.ElephantEyes[SrcSq], 4);
				indexDst=0;
				indexEye=0;
				DstSq = DstArr[indexDst];
				while (DstSq != -1) {
					Attack = Position.getSquares(DstSq);
					if ((Attack & AttTag)!=0) {
						if (Position.getSquares(EyeArr[indexEye])==0) {
							MoveList[listIndex].src =SrcSq;
							MoveList[listIndex].dst =DstSq;
							if (HistTab == null) {
								ValueList[listIndex] = MvvValues[Attack] + 3; // Bishop = 0;
							} else {
								ValueList[listIndex] = HistTab[SrcSq][DstSq];
							}
							listIndex++;
						}
					} else if (HistTab != null && Attack==0) {
						if (Position.getSquares(EyeArr[indexEye])==0) {
							MoveList[listIndex].src =SrcSq;
							MoveList[listIndex].dst =DstSq;
							ValueList[listIndex] = HistTab[SrcSq][DstSq];
							listIndex++;
						}
					}
					indexDst++;
					DstSq = DstArr[indexDst];
					indexEye++;
				}
			}
		}
		MoveNum=listIndex;
	}

	public void GenKnightMoves(final ActiveBoard Position, final int HistTab[][]) {
		int i, SrcSq, DstSq, Attack, AttTag;
		int[] DstArr=new int[12], LegArr=new int[8];
		int indexDst=0;
		int indexLeg=0;
		int listIndex=MoveNum;
		AttTag = Position.getPlayer()!=0 ? 16 : 32;
		for (i = 5; i <= 6; i ++) {
			SrcSq = Position.getPieces(48 - AttTag + i);			
			if (SrcSq != -1) {
				copyArray(DstArr, PreMoveNodesGen.KnightMoves[SrcSq], 12);
				copyArray(LegArr, PreMoveNodesGen.HorseLegs[SrcSq], 8);
				indexLeg = 0;
				indexDst = 0;
				DstSq = DstArr[indexDst];
				while (DstSq != -1) {
					Attack = Position.getSquares(DstSq);
					if ((Attack & AttTag)!=0) {
						if (Position.getSquares(LegArr[indexLeg])==0) {
							MoveList[listIndex].src =SrcSq;
							MoveList[listIndex].dst =DstSq;
							if (HistTab == null) {
								ValueList[listIndex] = MvvValues[Attack] + 2; // Knight = 1;
							} else {
								ValueList[listIndex] = HistTab[SrcSq][DstSq];
							}
							listIndex++;
						}
					} else if (HistTab != null && Attack==0) {
						if (Position.getSquares(LegArr[indexLeg])==0) {
							MoveList[listIndex].src =SrcSq;
							MoveList[listIndex].dst =DstSq;
							ValueList[listIndex] = HistTab[SrcSq][DstSq];
							listIndex++;
						}
					}
					indexDst ++;
					DstSq = DstArr[indexDst];
					indexLeg++;
				}
			}
		}
		MoveNum = listIndex;
	}

	public void GenRookMoves(final ActiveBoard Position, final int HistTab[][]) {
		int i, SrcSq, DstSq, Attack, AttTag;
		int x, y, Shift, BitWord;
		int[] DstArr=new int[12];
		int indexDst=0;
		int listIndex=MoveNum;
		AttTag = Position.getPlayer()!=0 ? 16 : 32;
		for (i = 7; i <= 8; i ++) {
			SrcSq = Position.getPieces(48 - AttTag + i);
			if (SrcSq != -1) {
				x = ActiveBoard.FILE[SrcSq];
				y = ActiveBoard.RANK[SrcSq];
				Shift = ActiveBoard.BOTTOM[x];
				BitWord = Position.getBitFiles(x);
				copyArray(DstArr, PreMoveNodesGen.FileRookCapMoves[y][BitWord], 4);
				indexDst = 0;
				DstSq = DstArr[indexDst];
				while (DstSq != -1) {
					DstSq += Shift;
					Attack = Position.getSquares(DstSq);
					if ((Attack & AttTag)!=0) {
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						if (HistTab == null) {
							ValueList[listIndex] = MvvValues[Attack] + 1; // Rook = 2
						} else {
							ValueList[listIndex] = HistTab[SrcSq][DstSq];
						}
						listIndex++;
					}
					indexDst++;
					DstSq = DstArr[indexDst];
				}
				if (HistTab != null) {
					copyArray(DstArr, PreMoveNodesGen.FileNonCapMoves[y][BitWord], 12);
					indexDst=0;
					DstSq = DstArr[indexDst];
					while (DstSq != -1) {
						DstSq += Shift;
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						ValueList[listIndex] = HistTab[SrcSq][DstSq];
						listIndex++;
						indexDst++;
						DstSq = DstArr[indexDst];
					}
				}
				Shift = y;
				BitWord = Position.getBitRanks(y);
				copyArray(DstArr, PreMoveNodesGen.RankRookCapMoves[x][BitWord], 4);
				indexDst=0;
				DstSq = DstArr[indexDst];
				
				while (DstSq != -1) {
					DstSq += Shift;
					Attack = Position.getSquares(DstSq);
					if ((Attack & AttTag)!=0) {
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						if (HistTab == null) {
							ValueList[listIndex] = MvvValues[Attack] + 1; // Rook = 2;
						} else {
							ValueList[listIndex] = HistTab[SrcSq][DstSq];
						}
						listIndex ++;
					}
					indexDst ++;
					DstSq = DstArr[indexDst];
				}
				if (HistTab != null) {
					copyArray(DstArr, PreMoveNodesGen.RankNonCapMoves[x][BitWord], 12);
					indexDst = 0;
					DstSq = DstArr[indexDst];
					while (DstSq != -1) {
						DstSq += Shift;
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						ValueList[listIndex] = HistTab[SrcSq][DstSq];
						listIndex++;
						indexDst ++;
						DstSq = DstArr[indexDst];
					}
				}
			}
		}
		MoveNum = listIndex;
	}

	public void GenCannonMoves(final ActiveBoard Position, final int HistTab[][]) {
		int i, SrcSq, DstSq, Attack, AttTag;
		int x, y, Shift, BitWord;
		int DstArr[]=new int[12];
		int indexDst=0;
		int listIndex=MoveNum;
		AttTag = Position.getPlayer()!=0 ? 16 : 32;
		for (i = 9; i <= 10; i ++) {
			SrcSq = Position.getPieces(48 - AttTag + i);
			if (SrcSq != -1) {
				x = ActiveBoard.FILE[SrcSq];
				y = ActiveBoard.RANK[SrcSq];
				Shift = ActiveBoard.BOTTOM[x];
				BitWord = Position.getBitFiles(x);
				copyArray(DstArr, PreMoveNodesGen.FileCannonCapMoves[y][BitWord], 4);
				indexDst = 0;
				DstSq = DstArr[indexDst];
				while (DstSq != -1) {
					DstSq += Shift;
					Attack = Position.getSquares(DstSq);
					if ((Attack & AttTag)!=0) {
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						if (HistTab == null) {
							ValueList[listIndex] = MvvValues[Attack] + 2; // Cannon = 1
						} else {
							ValueList[listIndex] = HistTab[SrcSq][DstSq];
						}
						listIndex++;
					}
					indexDst ++;
					DstSq = DstArr[indexDst];
				}
				if (HistTab != null) {
					copyArray(DstArr, PreMoveNodesGen.FileNonCapMoves[y][BitWord], 12);
					indexDst = 0;
					DstSq = DstArr[indexDst];
					while (DstSq != -1) {
						DstSq += Shift;
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						ValueList[listIndex] = HistTab[SrcSq][DstSq];
						listIndex ++;
						indexDst ++;
						DstSq = DstArr[indexDst];
					}
				}
				Shift = y;
				BitWord = Position.getBitRanks(y);
				copyArray(DstArr, PreMoveNodesGen.RankCannonCapMoves[x][BitWord], 4);
				indexDst = 0;
				DstSq = DstArr[indexDst];
				while (DstSq != -1) {
					DstSq += Shift;
					Attack = Position.getSquares(DstSq);
					if ((Attack & AttTag)!=0) {
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						if (HistTab == null) {
							ValueList[listIndex] = MvvValues[Attack] + 2; // Cannon = 1
						} else {
							ValueList[listIndex] = HistTab[SrcSq][DstSq];
						}
						listIndex ++;
					}
					indexDst ++;
					DstSq = DstArr[indexDst];
				}
				if (HistTab != null) {
					copyArray(DstArr, PreMoveNodesGen.RankNonCapMoves[x][BitWord], 12);
					indexDst = 0;
					DstSq = DstArr[indexDst];
					while (DstSq != -1) {
						DstSq += Shift;
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						ValueList[listIndex] = HistTab[SrcSq][DstSq];
						listIndex ++;
						indexDst ++;
						DstSq = DstArr[indexDst];
					}
				}
			}
		}
		MoveNum = listIndex;
	}

	public void GenPawnMoves(final ActiveBoard Position, final int HistTab[][]) {
		int i, SrcSq, DstSq, Attack, AttTag;
		int DstArr[]=new int[4];
		int indexDst=0;
		int listIndex=MoveNum;
		AttTag = Position.getPlayer()!=0 ? 16 : 32;

		for (i = 11; i <= 15; i ++) {
			SrcSq = Position.getPieces(48 - AttTag + i);
			if (SrcSq != -1) {
				copyArray(DstArr, PreMoveNodesGen.PawnMoves[SrcSq][Position.getPlayer()], 4);
				indexDst = 0;
				DstSq = DstArr[indexDst];
				while (DstSq != -1) {
					Attack = Position.getSquares(DstSq);
					if ((Attack & AttTag)!=0) {
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						if (HistTab == null) {
							ValueList[listIndex] = MvvValues[Attack] + 3; // Pawn = 0
						} else {
							ValueList[listIndex] = HistTab[SrcSq][DstSq];
						}
						listIndex ++;
					} else if (HistTab != null && Attack==0) {
						MoveList[listIndex].src =SrcSq;
						MoveList[listIndex].dst =DstSq;
						ValueList[listIndex] = HistTab[SrcSq][DstSq];
						listIndex++;
					}
					indexDst ++;
					DstSq = DstArr[indexDst];
				}
			}
		}
		MoveNum = listIndex;
	}
	private void copyArray(int[] DstArr,final int[] SrcArr,int len){
		for (int i=0;i<len;i++){
			DstArr[i]=SrcArr[i];
		}
		for (int i=len;i<DstArr.length;i++){
			DstArr[i]= -1;
		}
	}
	//for test
	public String toString(){
		String tmpStr="";
		for (int i=0;i<MoveNum;i++){
			tmpStr = tmpStr + "["+ MoveList[i]+"]";
			if ((i+1)%8==0) tmpStr+="\n";
		}
		return tmpStr;
	}
}
