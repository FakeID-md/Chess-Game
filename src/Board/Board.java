package Board;

import objects.Piece;
import objects.Bishop;
import objects.King;
import objects.Knight;
import objects.Pawn;
import objects.Queen;
import objects.Rook;

public class Board {
	
	private static void printName(Piece spot, char piece) {
		boolean team = spot.CurrPlayer();
		if (team == true) { // white
			System.out.print("w" + piece + " ");
		} else { // black
			System.out.print("b" + piece + " ");
		}
	}
	
	 
	private static void printChecker(int x, int y) {
		if (x % 2 != 0) {
			if (y % 2 == 0) {
				System.out.print("## ");
			} 
			else {
				System.out.print("   ");
			}
		} else {
			if (y % 2 == 0) {
				System.out.print("   ");
			}
			else {
				System.out.print("## ");
			}
		}
		
	}
	
	/**
	 * Prints the complete board with checkers and pieces in their respective location.
	 * @param board A plain 2D piece array object
	 */
	public static void show_Board(Piece[][] board) {
		// PRINT BOARD
		int n = 8;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				
				if (board[x][y] instanceof Pawn) {
					printName(board[x][y], 'P');
				} 
				else if (board[x][y] instanceof Rook) {
					printName(board[x][y], 'R');
				}
				else if (board[x][y] instanceof Knight) {
					printName(board[x][y], 'N');
				}
				else if (board[x][y] instanceof Bishop) {
					printName(board[x][y], 'B');
				}
				else if (board[x][y] instanceof Queen) {
					printName(board[x][y], 'Q');
				}
				else if (board[x][y] instanceof King) {
					printName(board[x][y], 'K');
				}
				else {
					printChecker(x, y);
				}

				// BORDER: RIGHT (Numbers)
				if (y == 7) {
					System.out.print(n);
					n--;
				}
				// BORDER: RIGHT (Numbers)
			}
			System.out.println();
			
			// BORDER: BOTTOM (Letters)
			if (x == 7) {
				char l = 'a';
				for (int bord = 0; bord < 8; bord++) {
					System.out.print(" " + l + " ");
					l = (char) (l + 1);
				}
				System.out.println();
			}
			// BORDER: BOTTOM (Letters)
		}
		System.out.println();
	}
	
	/**
	 * Checks if the spot on the board is empty
	 * @param board Uses the 2D piece array object
	 * @param x The x coordinate of the piece
	 * @param y The y coordinate of the piece
	 * @return true if the coordinate on the board is empty
	 */
	public static boolean isEmpty(Piece[][] board, int x, int y) {
		if (x > 7 || x < 0 || y > 7 || y < 0) { // out of range
			return true;
		}
		
		if (board[x][y] == null) {
			return true;
		}
		
		return false;
	}
	
	
	
	
}