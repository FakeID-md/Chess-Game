package objects;

import Board.Board;
/**
 * 
 * @author Tianyu Chen 
 * @author Fengge Chen
 *
 */

public class Bishop extends Piece {
	/**
	 * 
	 * @param x Current X coordinate
	 * @param y Current Y coordinate
	 * @param team True is White, False is Black
	 */
	public Bishop(int x, int y, boolean team) {
		this.x = x;
		this.y = y;
		this.color = team;
	}
	
	public int math(int x, int y) {
		int result = Math.abs(x-y);
		return result;
	}
	
	/**
	 * A check to see if a move is possible
	 * @param board shows piece location
	 * @param odx Old x coordinate integer
	 * @param ody Old y coordinate integer
	 * @param x New x coordinate integer
	 * @param y New y coordinate integer
	 * @param direction which piece wants to move
	 * @return True if move is possible
	 */
	private boolean lineCheck(Piece[][] board, int odx, int ody, int x, int y, int direction) {
		if (direction == 1) {
			while (odx != (x+1) && ody != (y-1)) {
				odx--; 
				ody++;
				if (!Board.isEmpty(board, odx, ody)) {
					return false;
				}
			}// 1 is to check the piece move to northeast
		} else if (direction == 2) {
			while (odx != (x-1) && ody != (y-1)) {
				odx++; 
				ody++;
				if (!Board.isEmpty(board, odx, ody)) {
					return false;
				}
			}// 2 is to check the piece move to southeast
		} else if (direction == 3) {
			while (odx != (x-1) && ody != (y+1)) {
				odx++; 
				ody--;
				if (!Board.isEmpty(board, odx, ody)) {
					return false;
				}
			} // 3 is to check the piece move to southwest
		} else if (direction == 4) {
			while (odx != (x+1) && ody != (y+1)) {
				odx--; 
				ody--;
				if (!Board.isEmpty(board, odx, ody)) {
					return false;
				}
			}// 4 is to check the piece move to northwest
		} else {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean isPathClear(Piece[][] board, int x, int y) {
		
		int odx = this.x; //the old location of x
		int ody = this.y; //the old location of y
		
		int resultx = math(x , odx); //the result for new x location minus old x
		int resulty = math(y , ody); //the result for new y location minus old y
		
		
		if (resultx == resulty) { // moving diagonally
			int dx = odx - x; 
			int dy = ody - y;
			if (dy < 0 && dx > 0) { //moving top right
				return lineCheck(board, odx, ody, x, y, 1);
			} else if (dy < 0 && dx < 0 ) { // moving down right
				return lineCheck(board, odx, ody, x, y, 2);
			} else if (dy > 0 && dx < 0 ) { // moving down left
				return lineCheck(board, odx, ody, x, y, 3);
			} else if (dy > 0 && dx > 0) { // moving top left
				return lineCheck(board, odx, ody, x, y, 4);
			}
		} 
		return false;
	}
}
