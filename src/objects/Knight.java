package objects;

/**
 * 
 * @author Tianyu Chen	
 * @author Fengge Chen
 */

public class Knight extends Piece {
	/**
	 * 2 Arguments constructor for Knight Class
	 * @param x is X coordinate
	 * @param y is Y coordinate
	 * @param white is true and black is false
	 */
	public Knight(int x, int y, boolean team) {
		this.x = x;
		this.y = y;
		
		this.color = team;
	}
	public int math(int x, int y) {
		int result = Math.abs(x-y);
		return result;
	}

	@Override
	public boolean isPathClear(Piece[][] board, int x, int y) {
		int odx = this.x; //original of the pieces x location 
		int ody = this.y; //original of the pieces y location 
		
		int changex = math(odx , x);
		int changey = math(ody , y);
		
		if ((changex == 2 && changey == 1) || (changex == 1 && changey == 2)) {
			return true; //check if the knight can move or not
		}
		
		return false;
	}
}
