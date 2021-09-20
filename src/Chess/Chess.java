
package Chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import objects.*;
import Board.Board;

/*
 * @author Tianyu Chen Fengge Chen
 */


public class Chess {
	
	static boolean is_draw = false; 
	
	
	static boolean isGameEnd = false;
	
	public static int[] convert(String s) {
		int[] a = new int[4];
		
		char c = (char) (s.charAt(0)-49);
		a[1] = Character.getNumericValue(c);
		a[0] = 8 - Character.getNumericValue(s.charAt(1));
		char b = (char) (s.charAt(3)-49);
		a[3] = Character.getNumericValue(b);
		a[2] = 8 - Character.getNumericValue(s.charAt(4));
		
		return a;
	}
	
	
	public static King KingsCollision(Piece[][] board, boolean color) {	
		if(board==null) return null;
		King target = null;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(board[i][j]!=null&&board[i][j]!=null) {
					
					if (!Board.isEmpty(board, i, j) && board[i][j].CurrPlayer() == color 
							&& board[i][j] instanceof King) {
					
						target = (King) board[i][j];
					
					//System.out.println(target.getx());
					//System.out.println(target.gety());
					}
				}else {
					continue;
				}
			}
		
	}
		return target;
	}
	public static void main (String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Piece[][] board = new Piece[8][8];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = null;
				//board[i][j].file = i;
				//board[i][j].rank = j;
				//board[i][j].occupier = null;
			}
		}	
		//System.out.println("Hello");
		
		board[1][0] = new Pawn(1, 0, false);
		board[1][1] = new Pawn(1, 1, false);
		board[1][2] = new Pawn(1, 2, false);
		board[1][3] = new Pawn(1, 3, false);
		board[1][4] = new Pawn(1, 4, false);
		board[1][5] = new Pawn(1, 5, false);
		board[1][6] = new Pawn(1, 6, false);
		board[1][7] = new Pawn(1, 7, false);
		board[0][0] = new Rook(0, 0, false);
		board[0][1] = new Knight(0, 1, false);
		board[0][2] = new Bishop(0, 2, false);
		board[0][3] = new Queen(0, 3, false);
		board[0][4] = new King(0, 4, false);
		board[0][5] = new Bishop(0, 5, false);
		board[0][6] = new Knight(0, 6, false);
		board[0][7] = new Rook(0, 7, false);
		board[6][0] = new Pawn(6, 0, true);
		board[6][1] = new Pawn(6, 1, true);
		board[6][2] = new Pawn(6, 2, true);
		board[6][3] = new Pawn(6, 3, true);
		board[6][4] = new Pawn(6, 4, true);
		board[6][5] = new Pawn(6, 5, true);
		board[6][6] = new Pawn(6, 6, true);
		board[6][7] = new Pawn(6, 7, true);
		board[7][0] = new Rook(7, 0, true);
		board[7][1] = new Knight(7, 1, true);
		board[7][2] = new Bishop(7, 2, true);
		board[7][3] = new Queen(7, 3, true);
		board[7][4] = new King(7, 4, true);
		board[7][5] = new Bishop(7, 5, true);
		board[7][6] = new Knight(7, 6, true);
		board[7][7] = new Rook(7, 7, true);
		
		Board.show_Board(board);
		
		boolean color = true; // white = true, black = false
		
		while (!kingTaken(board, color) && !checkMate(board, true) && !checkMate(board, false)) { // ** condition needs to be whether or not the King is dead or game is over			
			/*
			King king = null;
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j<= 7; j++) {
					if (!Board.isEmpty(board, i, j) && board[i][j].getTeam() == color && board[i][j] instanceof King) {
						king = (King) board[i][j];
					}
				}
			}
			*/
			King king = KingsCollision(board,color);
			
			Pawn.resetEnPassant(board, color);
			
			if (color) { System.out.print("White's move: ");
			} else { System.out.print("Black's move: "); }
			
			String s = br.readLine().trim().toLowerCase();
			
			System.out.println(); // formatting space
			
			processInput(s);
			
			if (is_draw) {
				if (s.equals("draw")) {
					close(color, true);
				}
				is_draw = false;
			}
			
			if (checkValidInput(s, color, board)) {
				int[] trans = convert(s);
				
				// if start state is NULL
				if (board[trans[0]][trans[1]] == null) {
					//System.out.println("1");
					System.out.println("Illegal move, try again");
					System.out.println();
					continue;
				}
				
				// start state is wrong team
				if (board[trans[0]][trans[1]].CurrPlayer() == !color) {
					//System.out.println("2");
					System.out.println("Illegal move, try again");
					System.out.println();
					continue;
				}
				
				Piece occupier = board[trans[2]][trans[3]];
				// if move is not valid
				if (board[trans[0]][trans[1]].move(board, trans[2], trans[3])) {
					// do not allow moves that cause check
					if (Piece.isChecked(board, king.get_rank(), king.get_file(), color) && king.CurrPlayer() == color) {
						
						int p = trans[0];
						int q = trans[1];
						board[p][q] = board[trans[2]][trans[3]];
				    	board[p][q].enpassant = board[trans[2]][trans[3]].enpassant;
				    	board[trans[2]][trans[3]].firstmove = false;
				        board[trans[2]][trans[3]] = null;
				        board[p][q].x = p;
				        board[p][q].y = q;
						board[trans[2]][trans[3]] = occupier;
						System.out.println("Illegal move, try again");
						System.out.println();
						continue;
					}
					
					
					
					King temp = KingsCollision(board,color);
					
					Board.show_Board(board);
					if (temp != null) {
						if (Piece.isChecked(board, temp.get_rank(), temp.get_file(), !color)) {
							System.out.println("Check");
						}
					}
					
					color = !color;
				} else {
					System.out.println("Illegal move, try again");
					System.out.println();
					continue;
				}
			} else {
				System.out.println("Illegal move, try again");
				System.out.println();
				continue;
			}
		}
		
		close(!color, false);
		
	}
	
	
	private static void printProposal(boolean turn, String s) {
		// TODO Auto-generated method stub
		if (is_draw) {
			if (s.equals("draw")) {
				close(turn, true);
			}
			is_draw = false;
		}
	}

	
	
	private static void processInput(String s) {
		// TODO Auto-generated method stub
		if (s.toLowerCase().contains("resign")) {
			//end of game
			//Controller.switchCurPlayer();
			System.out.println("resign");
			return;
			
		}else if (s.toLowerCase().contains("draw?")) {
			is_draw = true;
			//Controller.switchCurPlayer();
		}else if (s.toLowerCase().compareTo("draw") == 0 && is_draw) {
			System.out.println("draw");
			Chess.isGameEnd = true;
			//System.exit(0);
		}else {
			return;
		}
		
	}
	
	// checks if your move kills the King
	
	public static boolean kingTaken(Piece[][] board, boolean color) {
		
		King king = KingsCollision(board,color);
		if (king == null) {
			close(!color, false);
			return true;
		}
		return false;
	}
	
	
	/**
	 * A check to see if the king has any possible moves that might result in the king being captured
	 * @param board The board in which chess is being played on
	 * @param turn True if it is the user's turn is white, false is black
	 * @return
	 */
	/*
	
	public static boolean checkMate(Piece[][] board, boolean color) {
		
		King king = null;
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (!Board.isEmpty(board, i, j) && board[i][j].getTeam() == turn && board[i][j] instanceof King) {
					king = (King) board[i][j];
				}
			}
		}
		
		King king = KingsCollision(board,color);
		if (Piece.isChecked(board, king.getx(), king.gety(), king.getTeam())) {
			//System.out.println("check!!!!");
			Piece piece = null;
			Piece Occupier = null;
			
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (!Board.isEmpty(board, i, j) && board[i][j].getTeam() == color) {						
						piece = board[i][j];
						for (int x = 0; x < 8; x++) {
							for (int y = 0; y < 8; y++) {
								int prevr = piece.getx();
								int prevc = piece.gety();
							    Occupier = board[x][y];
							    boolean firstmove = board[prevr][prevc].getFirstMove();
							    
								if (piece.move(board, x, y)) {
									int p = piece.getx();
									int q = piece.gety();
									if (!Piece.isChecked(board, king.getx(), king.gety(), king.getTeam())) {
										board[piece.getx()][piece.gety()].undo(board, prevr, prevc, firstmove);
										//int p = piece.getx();
										//int q = piece.gety();
										
										board[prevr][prevc] = board[p][q];
										board[prevr][prevc].firstmove = firstmove;
										board[prevr][prevc].enpassant = board[p][q].enpassant;
							    		board[p][q] = null;
							    		board[prevr][prevc].x = x;
							    		board[prevr][prevc].y = y;
							    		
										
							    		board[prevr][prevc] = Occupier;
							    		
										return false;
									}
									board[piece.getx()][piece.gety()].undo(board, prevr, prevc, firstmove);
									
									board[prevr][prevc] = board[p][q];
									board[prevr][prevc].firstmove = firstmove;
									board[prevr][prevc].enpassant = board[p][q].enpassant;
						    		board[p][q] = null;
						    		board[prevr][prevc].x = x;
						    		board[prevr][prevc].y = y;
						    		board[prevr][prevc] = Occupier;
						    		
									board[prevr][prevc] = Occupier;
									
								}
							}
						}
					}
				}
			}	
			

			System.out.println("Checkmate");
			return true;
		} 

		return false;
	}

*/
	
	public static boolean checkMate(Piece[][] board, boolean turn) {
		King king = KingsCollision(board,turn);
		
		if (Piece.isChecked(board, king.get_rank(), king.get_file(), king.CurrPlayer())) {
			//System.out.println("check!!!!");
			Piece piece = null;
			Piece Occupier = null;
			
			
			
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (!Board.isEmpty(board, i, j) && board[i][j].CurrPlayer() == turn) {
						// do every move and check if check works or not
						piece = board[i][j];
						for (int x = 0; x < 8; x++) {
							for (int y = 0; y < 8; y++) {
								int prevr = piece.get_rank();
								int prevc = piece.get_file();
							    Occupier = board[x][y];
							    boolean firstmove = board[prevr][prevc].getFirstMove();
								if (piece.move(board, x, y)) {
									int p = piece.get_rank();
									int q = piece.get_file();
									if (!Piece.isChecked(board, king.get_rank(), king.get_file(), king.CurrPlayer())) {
										board[p][q].undo(board, prevr, prevc, firstmove);
										/*
										board[prevr][prevc] = board[p][q];
										board[prevr][prevc].firstmove = firstmove;
										board[prevr][prevc].enpassant = board[p][q].enpassant;
							    		board[p][q] = null;
							    		board[prevr][prevc].x = x;
							    		board[prevr][prevc].y = y;
										*/
										board[x][y] = Occupier;
										// must make this move
										return false;
									}
									board[piece.get_rank()][piece.get_file()].undo(board, prevr, prevc, firstmove);
									board[x][y] = Occupier;
								}
							}
						}
					}
				}
			}	

			System.out.println("Checkmate");
			return true;
		} 

		return false;
	}

	
	public static boolean checkValidInput(String s, boolean turn, Piece[][] board) {
		if (s.length() != 5 || s.charAt(2) != ' ') {
			if (s.length() >= 6) {
				// resign
				if (s.equals("resign")) {
					close(!turn, false);
				}
				// draw
				if (checkValidInput(s.substring(0, 5), turn, board)) {
					if (s.substring(6).equals("draw?")) {
						is_draw = true;
						return true;
					}
				}
				
				int[] trans = convert(s);
				if (board[trans[0]][trans[1]] instanceof Pawn) {
					if ((board[trans[0]][trans[1]].CurrPlayer() && trans[2] == 0) 
							|| (!board[trans[0]][trans[1]].CurrPlayer() && trans[2] == 7)) {
						// white or black
						if ((s.length() == 7) && checkValidInput(s.substring(0, 5), turn, board) 
								&& s.charAt(5) == ' ' && Character.isLetter(s.charAt(6))) {
							Pawn.promotion = s.charAt(6);
							return true;
						} 						
					}
				}	
			}
			return false;
		}
		
		
		
		
		return true;
	}
	/*
	public static boolean switch(Piece[][] board, int[] arr, boolean turn) {
		boolean team = board[arr[0]][arr[1]].CurrPlayer();
		
		if (turn == team) { // correct team
			turn = !turn;
			return true;
		}
		
		return false;
	}
	*/
	
	
	
	public static void close(boolean winning, boolean draw) { // input is winner, draw is true if draw
		if (draw) System.out.println("draw");
		
		else if (!winning) {
			System.out.println("Black wins");
		}else {
			System.out.println("White wins");
		}
		
		System.exit(0);
	}
}