package objects;

import Board.Board;
/**
 * 
 * @author Tianyu Chen	
 * @author Fengge Chen
 */

public class Queen extends Piece {
	
	/**
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate 
	 * @param team True is White, False is Black
	 */
	public Queen(int x, int y, boolean team) {
		this.x = x;
		this.y = y;
		
		this.color = team;
		firstmove = true;
	}
	
	public int math(int x, int y) {
		int result = Math.abs(x-y);
		return result;
	}
	
	/**
	 * 
	 * @param board the piece location
	 * @param odx Old x coordinate 
	 * @param ody Old y coordinate 
	 * @param x New x coordinate 
	 * @param y New y coordinate value
	 * @param direction piece wants to move
	 * @return true for moving
	 */
	private boolean lineCheck(Piece[][] board, int odx, int ody, int x, int y, int direction) {
		// 1 (northeast) 2 (southeast) 3 (southwest) 4 (northwest) 5 (right) 6 (left) 7 (up) 8 (down)
				if (direction == 1) {
					while (odx != (x+1) && ody != (y-1)) {
						odx--; 
						ody++;
						if (!Board.isEmpty(board, odx, ody)) {
							return false;
						} // moving to northeast
					}
				} else if (direction == 2) {
					while (odx != (x-1) && ody != (y-1)) {
						odx++; 
						ody++;
						if (!Board.isEmpty(board, odx, ody)) {
							return false;
						} // moving to south east
					}
				} else if (direction == 3) {
					while (odx != (x-1) && ody != (y+1)) {
						odx++; 
						ody--;
						if (!Board.isEmpty(board, odx, ody)) {
							return false;
						}
					} // moving to southwest
				} else if (direction == 4) {
					while (odx != (x+1) && ody != (y+1)) {
						odx--; 
						ody--;
						if (!Board.isEmpty(board, odx, ody)) {
							return false;
						}
					} // moving to north west 
				} else if (direction == 5) {
					while (ody != (y-1)) {
						ody++;
						if (!Board.isEmpty(board, odx, ody)) {
							return false;
						} // moving right
					}
				} else if (direction == 6) {
					while (ody != (y+1)) {
						ody--;
						if (!Board.isEmpty(board, odx, ody)) {
							return false;
						} // moving left
					}
				} else if (direction == 7) {
					while (odx != (x+1)) {
						odx--;
						if (!Board.isEmpty(board, odx, ody)) {
							return false;
						}
					} //moving up
				} else if (direction == 8) {
					while (odx != (x-1)) {
						odx++;
						if (!Board.isEmpty(board, odx, ody)) {
							return false;
						}
					} // moving down
				} else {
					return false;
				}
				
				return true;
	}

	@Override
	public boolean isPathClear(Piece[][] board, int x, int y) {
		int odx = this.x; //original of the pieces x location 
		int ody = this.y; //original of the pieces y location 
		
		int changex = math(x , odx);
		int changey = math(y , ody);
		
		int dx = odx - x; // pieces x location  after changed
		int dy = ody - y; // pieces x location  after changed

		
		
		if (changex == changey) { // to check the Queen can move diagonal
			if (dx > 0 && dy < 0) {
				return lineCheck(board, odx, ody, x, y, 1); // moving northeast
			} else if (dx < 0 && dy < 0) { 
				return lineCheck(board, odx, ody, x, y, 2); // moving southeast
			} else if (dx < 0 && dy > 0) {
				return lineCheck(board, odx, ody, x, y, 3); // moving southwest
			} else if (dx > 0 && dy > 0) {
				return lineCheck(board, odx, ody, x, y, 4); // moving northwest
			}
		} else if (changex == 0 && changey != 0) { // to check the Queen can move vertical
			if (dy < 0) { // moving right
				return lineCheck(board, odx, ody, x, y, 5);
			} else { // moving left
				return lineCheck(board, odx, ody, x, y, 6);
			}
		} else if (changex != 0 && changey == 0) { // vertical
			if (dx > 0) { // moving up
				return lineCheck(board, odx, ody, x, y, 7);
			} else { // moving down
				return lineCheck(board, odx, ody, x, y, 8);
			}
			
		}
		
		return false;
	}

}