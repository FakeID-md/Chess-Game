package objects;

import Board.Board;

/**
 * 
 * @author Tianyu Chen	
 * @author Fengge Chen
 *
 */
public class Pawn extends Piece {
	// int x
	// int y
	// boolean firstmove = false;
	// boolean team;
	// boolean alive = true;
	
	/**
	 * Returns True if vulnerable to en passant
	 */
	boolean enpassant; 
	
	public static char promotion = 'x'; // x is no promotion
	static boolean enPassantKill = false;
	
	/*
	 * 
	 * @param 
	 * 		x
	 * @param 
	 * 		y
	 * @param 
	 * 		team
	 */
	public Pawn(int x, int y, boolean color) {
		this.x = x;
		this.y = y;
		
		this.color = color;
	}
	/*
	 * 
	 * @param c first character of pieces character 
	 * @param board shows piece location 
	 * @param x Current X coordinate
	 * @param y Current Y coordinate
	 * @return True if promotion is possible
	 */
	// change a pawn to another type
	public static boolean promotion(char c, Piece[][] board, int x, int y) {
		if (c == 'p') {
			board[x][y] = new Pawn(x, y, board[x][y].CurrPlayer()); //promotion to Pawn 
		} else if (c == 'r') {
			board[x][y] = new Rook(x, y, board[x][y].CurrPlayer());	//promotion to Rook
		} else if (c == 'n') {
			board[x][y] = new Knight(x, y, board[x][y].CurrPlayer());	//promotion to Knight 
		} else if (c == 'b') {
			board[x][y] = new Bishop(x, y, board[x][y].CurrPlayer());	//promotion to Bishop 
		} else if (c == 'q') {
			board[x][y] = new Queen(x, y, board[x][y].CurrPlayer());	//promotion to Queen 
		} else {
			return false;
		}
		 
		board[x][y].firstmove = false;
		return true;
	}
	/**
	 * Resets Enpassant move
	 * @param board The board where	the piece is on
	 * @param turn True is White's turn, False is Black's turn
	 */
	// resets all enpassants to false after each move
	public static void resetEnPassant(Piece[][] board, boolean turn) {
		for (int x = 0; x < 8; x++) {
			/*
			board[7][5] = board[7][7];
	    	board[7][5].enpassant = board[7][7].enpassant;
	        board[7][5] = null;
	        board[7][5].x = 7;
	        board[7][5].y = 5;
	        this.firstmove = false;
	        */
			for (int y = 0; y < 8; y++) {
				if (board[x][y] instanceof Pawn && board[x][y].color == turn) {
					/*
					board[7][5] = board[7][7];
			    	board[7][5].enpassant = board[7][7].enpassant;
			        board[7][5] = null;
			        board[7][5].x = 7;
			        board[7][5].y = 5;
			        this.firstmove = false;
			        */
					board[x][y].enpassant = false; //to check the en passant from array x 0 to 7 and array x[y] 0 to 7 
				}
			}
		}
	}

	
	@Override
	public boolean isPathClear(Piece[][] board, int x, int y) {
		// Black Pawn 
		if (this.color == false) {
			// to check the diagonal kill 
			if (!(Board.isEmpty(board, x, y)) && board[x][y].color == true && (x - this.x == 1) 
					&& (math(y , this.y) == 1)) return true;
			// check the status when moving 1 space forward
			if (this.firstmove && Board.isEmpty(board, x, y) && Board.isEmpty(board, x-1, y) 
					&& (x - this.x == 1 || x - this.x == 2) && (y - this.y == 0)) {
				board[this.x][this.y].enpassant = true;
				/*
				board[7][5] = board[7][7];
		    	board[7][5].enpassant = board[7][7].enpassant;
		        board[7][5] = null;
		        board[7][5].x = 7;
		        board[7][5].y = 5;
		        this.firstmove = false;
		        */
				return true;
			}
			// check the status when moving 1 space forward 
			if ((Board.isEmpty(board, x, y)) && (x - this.x == 1) && (y - this.y == 0)) return true;
			// check if it is enPassant Kill
			if ((x - this.x == 1) && (math(y , this.y) == 1)) {
				// (y - this.y == -1) means it is false and others means it is true
				/*
				board[7][5] = board[7][7];
		    	board[7][5].enpassant = board[7][7].enpassant;
		        board[7][5] = null;
		        board[7][5].x = 7;
		        board[7][5].y = 5;
		        this.firstmove = false;
		        */
				if (board[x-1][y] == null) {
					return false;
				}
				
				if (board[x-1][y] instanceof Pawn && board[this.x][this.y].isOppositeTeam(board[x-1][y]) && board[x-1][y].enpassant) {
					board[x-1][y] = null;
					/*
					board[7][5] = board[7][7];
			    	board[7][5].enpassant = board[7][7].enpassant;
			        board[7][5] = null;
			        board[7][5].x = 7;
			        board[7][5].y = 5;
			        this.firstmove = false;
			        */
					return true;
				} else {
					return false;
				}
			}
		} 
		// White Pawn
		else {
			// diagonal kill
			if (!(Board.isEmpty(board, x, y)) && board[x][y].color == false && (this.x - x == 1) && (math(y , this.y) == 1)) return true;
			// move 2 spaces forward
			if (this.firstmove && Board.isEmpty(board, x, y) && Board.isEmpty(board, x+1, y) && (this.x - x == 1 || this.x - x == 2) && (y - this.y == 0)) {
				board[this.x][this.y].enpassant = true;
				/*
				board[7][5] = board[7][7];
		    	board[7][5].enpassant = board[7][7].enpassant;
		        board[7][5] = null;
		        board[7][5].x = 7;
		        board[7][5].y = 5;
		        this.firstmove = false;
		        */
				return true;
			}
			// check the status when moving 1 space forward
			if ((Board.isEmpty(board, x, y)) && (this.x - x == 1) && (this.y - y == 0)) return true;
			// enPassantKill
			if ((x - this.x == -1) && (math(this.y , y) == 1)) {
				// (y - this.y == -1) means it is false and others means it is true
				
				if (board[x+1][y] == null) {
					/*
					board[7][5] = board[7][7];
			    	board[7][5].enpassant = board[7][7].enpassant;
			        board[7][5] = null;
			        board[7][5].x = 7;
			        board[7][5].y = 5;
			        this.firstmove = false;
			        */
					return false;
				}
				
				if (board[x+1][y] instanceof Pawn && board[this.x][this.y].isOppositeTeam(board[x+1][y]) && board[x+1][y].enpassant) {
					board[x+1][y] = null;
					/*
					board[7][5] = board[7][7];
			    	board[7][5].enpassant = board[7][7].enpassant;
			        board[7][5] = null;
			        board[7][5].x = 7;
			        board[7][5].y = 5;
			        this.firstmove = false;
			        */
					return true;
				} else {
					/*
					board[7][5] = board[7][7];
			    	board[7][5].enpassant = board[7][7].enpassant;
			        board[7][5] = null;
			        board[7][5].x = 7;
			        board[7][5].y = 5;
			        this.firstmove = false;
			        */
					return false;
				}
			}
		}
		
		return false;
	}

	
	@Override
	public boolean move(Piece[][] board, int x, int y) {
		// TODO Auto-generated method stub
		int oldx = this.x;
		int oldy = this.y;
		if (isPathClear(board, x, y)) {
			if (enPassantKill) {
				board[oldx][oldy].update(board, x, y);//check the conditions of empassant 
				if (board[x][y].color) {
					board[x+1][y] = null;
				} else {
					board[x-1][y] = null;
				}
				enPassantKill = false;
			}
			
			// pawn promotion
			if (promotion != 'x') {
				if (Board.isEmpty(board, x, y)) {
					board[oldx][oldy].update(board, x, y); // move
				} else if (board[oldx][oldy].isOppositeTeam(board[x][y])) { // kill
					//board[oldx][oldy].kill(board, this.x, this.y, x, y);
					board[x][y] = board[this.x][this.y];
					board[this.x][this.y] = null;
					board[x][y].x = x;
					board[x][y].y = y;
					board[x][y].firstmove = false;
				} else {
					promotion = 'x';
					return false;
				}
				
				if (promotion(promotion, board, x, y)) {
					promotion = 'x';
					return true;
				} else {
					promotion = 'x';
					return false;
				}
			}
			
			// Move 
			if (Board.isEmpty(board, x, y)) {
				board[oldx][oldy].update(board, x, y);
				return true;
			}
			
			// Kill
			if (board[oldx][oldy].isOppositeTeam(board[x][y])) {
				board[x][y] = board[this.x][this.y];
				board[this.x][this.y] = null;
				board[x][y].x = x;
				board[x][y].y = y;
				board[x][y].firstmove = false;
			//	board[oldx][oldy].kill(board, this.x, this.y, x, y);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int math(int x, int y) {
		int result = Math.abs(x-y);
		return result;
	}
}