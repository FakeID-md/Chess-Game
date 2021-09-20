package objects;

import Board.Board;
/**
 * 
 * @author Tianyu Chen
 * @author Fengge Chen 
 */

public class Rook extends Piece {
	/**
	 * 
	 * @param x Current X coordinate
	 * @param y Current Y coordinate
	 * @param team True is White, False is Black
	 */
	public Rook(int x, int y, boolean team) {
		this.x = x;
		this.y = y;
		
		this.color = team;
	}
	
	/**
	 *
	 *@param board the piece location
	 * @param odx Old x coordinate 
	 * @param ody Old y coordinate 
	 * @param x New x coordinate 
	 * @param y New y coordinate value
	 * @param direction piece wants to move
	 * @return true for moving
	 */
	private boolean lineCheck(Piece[][] board, int odx, int ody, int x, int y, int direction) {
		// 1 (east) 2 (west) 3 (north) 4 (south)
		if (direction == 1) {
			while (ody != (y-1)) {
				ody++;
				if (!Board.isEmpty(board, odx, ody)) {
					return false;
				}// to check the Rook movement east 
			}
		} else if (direction == 2) {
			while (ody != (y+1)) {
				ody--;
				if (!Board.isEmpty(board, odx, ody)) {
					return false;
				}
			}// to check the Rook movement west 
		} else if (direction == 3) {
			while (odx != (x+1)) {
				odx--;
				if (!Board.isEmpty(board, odx, ody)) {
					return false;
				}
			}// to check the Rook movement north 
		} else if (direction == 4) {
			while (odx != (x-1)) {
				odx++;
				if (!Board.isEmpty(board, odx, ody)) {
					return false;
				}
			}// to check the Rook movement south 
		} else {
			return false;
		}
		
		return true;
	}
	
	public int math(int x, int y) {
		int result = Math.abs(x-y);
		return result;
	}
	
	@Override
	public boolean isPathClear(Piece[][] board, int x, int y) {
		
		int odx = this.x;	//original of the pieces x location 
		int ody = this.y;	//original of the pieces y location 
		
		int changex = math(x , odx);
		int changey = math(y , ody);
		
		int dx = odx - x;	 	// pieces x location  after changed
		int dy = ody - y;		//pieces y location  after changed
		
		if (changex == 0 && changey != 0) { // horizontal
			if (dy < 0) { // moving right
				return lineCheck(board, odx, ody, x, y, 1);
			} else { // moving left
				return lineCheck(board, odx, ody, x, y, 2);
			}
		} else if (changex != 0 && changey == 0) { // vertical
			if (dx > 0) { // moving up
				return lineCheck(board, odx, ody, x, y, 3);
			} else { // moving down
				return lineCheck(board, odx, ody, x, y, 4);
			}
		}
		
		return false;
	}
}