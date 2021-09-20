package objects;

import Board.Board;

/**
 * 
 * @author Tianyu Chen
 * @author Fengge Chen
 */

public class King extends Piece {
	
	/**
	 * white determines if the white castle is taken
	 */
	static boolean white = false;
	/**
	 * black determines if the black castle is taken
	 */
	static boolean black = false;
	
	/**
	 * 
	 * @param x Current X coordinate
	 * @param y Current Y coordinate
	 * @param team True is White, False is Black
	 */
	public King(int x, int y, boolean team) {
		this.x = x;
		this.y = y;
		
		this.color = team;
	}
	/**
	 * 
	 */
	
	public int math(int x, int y) {
		int result = Math.abs(x-y);
		return result;
	}

	public boolean isPathClear(Piece[][] board, int x, int y) {
		int odx = this.x; 		//original of the pieces x location 
		int ody = this.y;		//original of the pieces y location 
		/*
		board[7][5] = board[7][7];
    	board[7][5].enpassant = board[7][7].enpassant;
        board[7][5] = null;
        board[7][5].x = 7;
        board[7][5].y = 5;
        this.firstmove = false;
        */
		int changex = math(x , odx); 
		int changey = math(y , ody);
		/*
		board[7][5] = board[7][7];
    	board[7][5].enpassant = board[7][7].enpassant;
        board[7][5] = null;
        board[7][5].x = 7;
        board[7][5].y = 5;
        this.firstmove = false;
        */
		if (((changex == changey) && changex == 1 && changey == 1) 
			|| (changex == 1 && changey == 0) 
			|| (changex == 0 && changey == 1)) //to check the path when king move diagonal,up,down, left and right
		{	return true;
		}
		
		Piece king = board[odx][ody];
		// Castling
		
		// Have to put extra check conditions in here
		if ((king.CurrPlayer() == true && odx == 7 && ody == 4 && x == 7 && y == 6 && king.firstmove 
				&& board[x][y+1].firstmove) 
			||(king.CurrPlayer()== false && odx == 0 && ody == 4 && x == 0 && y == 6 
			&& king.firstmove && board[x][y+1].firstmove)) 
			
			// this is to check the king and rook change their position or not
			{
			if (Board.isEmpty(board, odx, ody+1) && Board.isEmpty(board, odx, ody+2) //to check the board is empty
					&& !Piece.isChecked(board, odx, ody, board[odx][ody].CurrPlayer()) //to check the board is empty
					&& !Piece.isChecked(board, odx, ody+1, board[odx][ody].CurrPlayer()) //to check the board is empty
					&& !Piece.isChecked(board, odx, ody+2, board[odx][ody].CurrPlayer())) //to check the board is empty
			{ // if the board is empty, to check which team should move
				if (board[odx][ody].CurrPlayer())
					/*
					board[7][5] = board[7][7];
			    	board[7][5].enpassant = board[7][7].enpassant;
			        board[7][5] = null;
			        board[7][5].x = 7;
			        board[7][5].y = 5;
			        this.firstmove = false;
			        */
					white = true; //white true white can move 
				else black = true;  // black true black can move 
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean move(Piece[][] board, int x, int y) {
		// TODO Auto-generated method stub
		int odx = this.x; //original of the pieces x location 
		int ody = this.y; //original of the pieces y location 
		
		if (isPathClear(board, x, y)) {
			// Move 
			if (Board.isEmpty(board, x, y)) { // to check the move is empty
				board[odx][ody].update(board, x, y);
				if (white) {
					if (board[x][y].CurrPlayer()) board[7][7].update(board, 7, 5);
					/*
					board[7][5] = board[7][7];
			    	board[7][5].enpassant = board[7][7].enpassant;
			        board[7][5] = null;
			        board[7][5].x = 7;
			        board[7][5].y = 5;
			        this.firstmove = false;
			        */
					white = false; // change king and rook
				}
				if (black) {
					if (!board[x][y].CurrPlayer()) board[0][7].update(board, 0, 5);
					black = false; //change king and rook
				}
				return true;
			}
			
			// Kill
			if (board[odx][ody].isOppositeTeam(board[x][y])) {
				//board[odx][ody].kill(board, this.x, this.y, x, y);
				board[x][y] = board[this.x][this.y];
				board[this.x][this.y] = null;
				board[x][y].x = x;
				board[x][y].y = y;
				board[x][y].firstmove = false;
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
		} else {
			return false;
		}
	}
	

}