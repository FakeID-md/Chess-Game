package objects;
import Board.Board;
/*
 * @author Tianyu Chen Fengge Chen
 */
public abstract class Piece {
		
        public int x;
        
        public int y;
       
        public boolean firstmove = true; 
        
        boolean color; 
        
		public boolean enpassant; 

        
        public void set(boolean color) {
                this.color = color;
        }
       
        public boolean CurrPlayer() {
                return color;
        }
        
        public boolean getFirstMove() {
        		return firstmove;
        }
       
        public boolean isOppositeTeam(Piece x) {
        		boolean color = this.color;
        		boolean team = x.color;
        		if (team == color) {
        			return false;
        		}
        		return true;
        }
        
    	public int get_rank() {
    		return this.x;
    	}
    	
    	public int get_file() {
    		return this.y;
    	}



    	
	    public void update(Piece[][] board, int x, int y) {
	    	board[x][y] = board[this.x][this.y];
	    	board[x][y].enpassant = board[this.x][this.y].enpassant;
	        board[this.x][this.y] = null;
	        board[x][y].x = x;
	        board[x][y].y = y;
	        this.firstmove = false;
	    }
    
   
	   
	    public void undo(Piece[][] board, int x, int y, boolean firstmove) {
	    		board[x][y] = board[this.x][this.y];
	    		board[x][y].firstmove = firstmove;
	    		board[x][y].enpassant = board[this.x][this.y].enpassant;
	        board[this.x][this.y] = null;
	        board[x][y].x = x;
	        board[x][y].y = y;
	    }
    
    
	    /*
	    public void kill(Piece[][] board, int thisx, int thisy, int x, int y) {
	    	board[x][y] = board[thisx][thisy];
			board[thisx][thisy] = null;
			board[x][y].x = x;
			board[x][y].y = y;
			board[x][y].firstmove = false;
	    }
    */
	    
    
    public boolean move(Piece[][] board, int x, int y) {
		// TODO Auto-generated method stub
		int oldx = this.x;
		int oldy = this.y;
		
		if (isPathClear(board, x, y)) {
			// Move 
			if (Board.isEmpty(board, x, y)) {
				board[oldx][oldy].update(board, x, y);
				return true;
			}
			
			// Kill
			if (board[oldx][oldy].isOppositeTeam(board[x][y])) {
				//board[oldx][oldy].kill(board, this.x, this.y, x, y);
				board[x][y] = board[this.x][this.y];
				board[this.x][this.y] = null;
				board[x][y].x = x;
				board[x][y].y = y;
				board[x][y].firstmove = false;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
    
    
    
    /*
    
    public static boolean isChecked(Piece[][] board, int rank, int file, boolean color) {
    	
    		for (int i = rank+1; i <= 7; i++) { // south (Rook + Queen)
    			if (!Board.isEmpty(board, i, file)) {
    	    			if (board[i][file] instanceof Rook || board[i][file] instanceof Queen) {
    	    				if(board[i][file].team != color) {
    	    					return true;
    	    				}
    	    			} else {
    	    				break;
    	    			}
    			}	
    		}
    		
    		for (int i = rank-1; i >= 0; i--) { // north (Rook + Queen)
    			if (!Board.isEmpty(board, i, file)) {
    	    			if ((board[i][file] instanceof Rook || board[i][file] instanceof Queen))
    	    				if( board[i][file].team != color) {
    	    				return true;
    	    			} else {
    	    				break;
    	    			}
    			}	
    		}
    		
    		for (int i = file+1; i <= 7; i++) { // east (Rook + Queen)
    			if (!Board.isEmpty(board, rank, i)) {
    	    			if (board[rank][i] instanceof Rook || board[rank][i] instanceof Queen)
    	    				if(board[rank][i].team != color) {
    	    					return true;	
    	    			} else {
    	    				break;
    	    			}
    			}	
    		}
    		
    		for (int i = file-1; i >= 0; i--) { // west (Rook + Queen)
    			if (!Board.isEmpty(board, rank, i)) {
    	    			if (board[rank][i] instanceof Rook || board[rank][i] instanceof Queen) 
    	    				if(board[rank][i].team != color) {
    	    					return true;
    	    			} else {
    	    				break;
    	    			}
    			}	
    		}
    		
    		for (int i = rank+1, j = file-1; i <= 7 && j >= 0; i++, j--) { 
    			// northeast (Bishop + Queen)
    			if (!Board.isEmpty(board, i, j)) { 
    	    			if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].team != color) {
    	    				return true;
    	    			} else {
    	    				break;
    	    			}
    			}	
    		}
    		
    		for (int i = rank-1, j = file-1; i >= 0 && rank >= 0; i--, j--) { // northwest (Bishop + Queen)
    			if (!Board.isEmpty(board, i, j)) {
    	    			if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].team != color) {
    	    				return true;
    	    			} else {
    	    				break;
    	    			}
    			}	
    		}
    		
    		for (int i = rank+1, j = rank+1; i <= 7 && j <= 7; i++, j++) { 
    			// southeast (Bishop + Queen)
    			if (!Board.isEmpty(board, i, j)) { 
    	    			if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) 
    	    					&& board[i][j].team != color) {
    	    				return true;
    	    			} else {
    	    				break;
    	    			}
    			}	
    		}
    		
    		for (int i = rank-1, j = file+1; i >= 0 && j <= 7; i--, j++) { // southwest (Bishop + Queen)
    			if (!Board.isEmpty(board, i, j)) {
    	    			if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) 
    	    					&& board[i][j].team != color) {
    	    				return true;
    	    			} else {
    	    				break;
    	    			}
    			}	
    		}
    		
    		if (color) { // white piece, checking for black Pawn
    			if ((!Board.isEmpty(board, rank-1, file-1) 
    					&& board[rank-1][file-1] instanceof Pawn && board[rank-1][file-1].team != color) 
    					|| (!Board.isEmpty(board, rank-1, file+1) && board[rank-1][file+1] instanceof Pawn 
    							&& board[rank-1][file+1].team != color)) { 
    				return true;
    			}
    		} else { // black piece, checking for white Pawn
    			if ((!Board.isEmpty(board, rank+1, file-1) 
    					&& board[rank+1][file-1] instanceof Pawn 
    					&& board[rank+1][file-1].team != color) || 
    					(!Board.isEmpty(board, rank+1, file+1) 
    							&& board[rank+1][file+1] instanceof Pawn && board[rank+1][file+1].team != color)) { 
    				return true;
    			}
    		}
    		
    		//return specialCase(board, rank, file,color);
    		
    		// Knight
    		if (!Board.isEmpty(board, rank+2, file+1) && board[rank+2][file+1] instanceof Knight && board[rank+2][file+1].team != color) 
    			return true;
    		if (!Board.isEmpty(board, x+2, y-1) && board[x+2][y-1] instanceof Knight && board[x+2][y-1].team != color) return true;
    		if (!Board.isEmpty(board, x-2, y+1) && board[x-2][y+1] instanceof Knight && board[x-2][y+1].team != color) return true;
    		if (!Board.isEmpty(board, x-2, y-1) && board[x-2][y-1] instanceof Knight && board[x-2][y-1].team != color) return true;
    		
    		if (!Board.isEmpty(board, x+1, y+2) && board[x+1][y+2] instanceof Knight && board[x+1][y+2].team != color) return true;
    		if (!Board.isEmpty(board, x+1, y-2) && board[x+1][y-2] instanceof Knight && board[x+1][y-2].team != color) return true;
    		if (!Board.isEmpty(board, x-1, y+2) && board[x-1][y+2] instanceof Knight && board[x-1][y+2].team != turn) return true;
    		if (!Board.isEmpty(board, x-1, y-2) && board[x-1][y-2] instanceof Knight && board[x-1][y-2].team != turn) return true;
    		// King
    		if (!Board.isEmpty(board, x+1, y) && board[x+1][y] instanceof King && board[x+1][y].team != turn) return true;
    		if (!Board.isEmpty(board, x-1, y) && board[x-1][y] instanceof King && board[x-1][y].team != turn) return true;
    		if (!Board.isEmpty(board, x, y+1) && board[x][y+1] instanceof King && board[x][y+1].team != turn) return true;
    		if (!Board.isEmpty(board, x, y-1) && board[x][y-1] instanceof King && board[x][y-1].team != turn) return true;
    		
    		if (!Board.isEmpty(board, x+1, y+1) && board[x+1][y+1] instanceof King && board[x+1][y+1].team != turn) return true;
    		if (!Board.isEmpty(board, x-1, y+1) && board[x-1][y+1] instanceof King && board[x-1][y+1].team != turn) return true;
    		if (!Board.isEmpty(board, x+1, y-1) && board[x+1][y-1] instanceof King && board[x+1][y-1].team != turn) return true;
    		if (!Board.isEmpty(board, x-1, y-1) && board[x-1][y-1] instanceof King && board[x-1][y-1].team != turn) return true;
    	
    		return false;
    		
    }
    */
    
    /*
    
    public static boolean isChecked(Piece[][] board, int x, int y, boolean turn) {
		for (int i = x+1; i <= 7; i++) { // south (Rook + Queen)
			if (!Board.isEmpty(board, i, y)) {
	    			if ((board[i][y] instanceof Rook || board[i][y] instanceof Queen) && board[i][y].team != turn) {
	    				return true;
	    			} else {
	    				break;
	    			}
			}	
		}
		
		for (int i = x-1; i >= 0; i--) { // north (Rook + Queen)
			if (!Board.isEmpty(board, i, y)) {
	    			if ((board[i][y] instanceof Rook || board[i][y] instanceof Queen) && board[i][y].team != turn) {
	    				return true;
	    			} else {
	    				break;
	    			}
			}	
		}
		
		for (int i = y+1; i <= 7; i++) { // east (Rook + Queen)
			if (!Board.isEmpty(board, x, i)) {
	    			if ((board[x][i] instanceof Rook || board[x][i] instanceof Queen) && board[x][i].team != turn) {
	    				return true;
	    			} else {
	    				break;
	    			}
			}	
		}
		
		for (int i = y-1; i >= 0; i--) { // west (Rook + Queen)
			if (!Board.isEmpty(board, x, i)) {
	    			if ((board[x][i] instanceof Rook || board[x][i] instanceof Queen) && board[x][i].team != turn) {
	    				return true;
	    			} else {
	    				break;
	    			}
			}	
		}
		
		for (int i = x+1, j = y-1; i <= 7 && j >= 0; i++, j--) { // northeast (Bishop + Queen)
			if (!Board.isEmpty(board, i, j)) { 
	    			if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].team != turn) {
	    				return true;
	    			} else {
	    				break;
	    			}
			}	
		}
		
		for (int i = x-1, j = y-1; i >= 0 && j >= 0; i--, j--) { // northwest (Bishop + Queen)
			if (!Board.isEmpty(board, i, j)) {
	    			if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].team != turn) {
	    				return true;
	    			} else {
	    				break;
	    			}
			}	
		}
		
		for (int i = x+1, j = y+1; i <= 7 && j <= 7; i++, j++) { // southeast (Bishop + Queen)
			if (!Board.isEmpty(board, i, j)) { 
	    			if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].team != turn) {
	    				return true;
	    			} else {
	    				break;
	    			}
			}	
		}
		
		for (int i = x-1, j = y+1; i >= 0 && j <= 7; i--, j++) { // southwest (Bishop + Queen)
			if (!Board.isEmpty(board, i, j)) {
	    			if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].team != turn) {
	    				return true;
	    			} else {
	    				break;
	    			}
			}	
		}
		
		if (turn) { // white piece, checking for black Pawn
			if ((!Board.isEmpty(board, x-1, y-1) && board[x-1][y-1] instanceof Pawn && board[x-1][y-1].team != turn) || (!Board.isEmpty(board, x-1, y+1) && board[x-1][y+1] instanceof Pawn && board[x-1][y+1].team != turn)) { 
				return true;
			}
		} else { // black piece, checking for white Pawn
			if ((!Board.isEmpty(board, x+1, y-1) && board[x+1][y-1] instanceof Pawn && board[x+1][y-1].team != turn) || (!Board.isEmpty(board, x+1, y+1) && board[x+1][y+1] instanceof Pawn && board[x+1][y+1].team != turn)) { 
				return true;
			}
		}
		
		// Knight
		if (!Board.isEmpty(board, x+2, y+1) && board[x+2][y+1] instanceof Knight && board[x+2][y+1].team != turn) return true;
		if (!Board.isEmpty(board, x+2, y-1) && board[x+2][y-1] instanceof Knight && board[x+2][y-1].team != turn) return true;
		if (!Board.isEmpty(board, x-2, y+1) && board[x-2][y+1] instanceof Knight && board[x-2][y+1].team != turn) return true;
		if (!Board.isEmpty(board, x-2, y-1) && board[x-2][y-1] instanceof Knight && board[x-2][y-1].team != turn) return true;
		
		if (!Board.isEmpty(board, x+1, y+2) && board[x+1][y+2] instanceof Knight && board[x+1][y+2].team != turn) return true;
		if (!Board.isEmpty(board, x+1, y-2) && board[x+1][y-2] instanceof Knight && board[x+1][y-2].team != turn) return true;
		if (!Board.isEmpty(board, x-1, y+2) && board[x-1][y+2] instanceof Knight && board[x-1][y+2].team != turn) return true;
		if (!Board.isEmpty(board, x-1, y-2) && board[x-1][y-2] instanceof Knight && board[x-1][y-2].team != turn) return true;
		
		// King
		if (!Board.isEmpty(board, x+1, y) && board[x+1][y] instanceof King && board[x+1][y].team != turn) return true;
		if (!Board.isEmpty(board, x-1, y) && board[x-1][y] instanceof King && board[x-1][y].team != turn) return true;
		if (!Board.isEmpty(board, x, y+1) && board[x][y+1] instanceof King && board[x][y+1].team != turn) return true;
		if (!Board.isEmpty(board, x, y-1) && board[x][y-1] instanceof King && board[x][y-1].team != turn) return true;
		
		if (!Board.isEmpty(board, x+1, y+1) && board[x+1][y+1] instanceof King && board[x+1][y+1].team != turn) return true;
		if (!Board.isEmpty(board, x-1, y+1) && board[x-1][y+1] instanceof King && board[x-1][y+1].team != turn) return true;
		if (!Board.isEmpty(board, x+1, y-1) && board[x+1][y-1] instanceof King && board[x+1][y-1].team != turn) return true;
		if (!Board.isEmpty(board, x-1, y-1) && board[x-1][y-1] instanceof King && board[x-1][y-1].team != turn) return true;
		
		return false;
}
    
    private static boolean specialCase(Piece[][] board, int x, int y, boolean color) {
		// TODO Auto-generated method stub
    	if (!Board.isEmpty(board, x+2, y+1) && board[x+2][y+1] instanceof Knight && board[x+2][y+1].team != color) return true;
		if (!Board.isEmpty(board, x+2, y-1) && board[x+2][y-1] instanceof Knight && board[x+2][y-1].team != color) return true;
		if (!Board.isEmpty(board, x-2, y+1) && board[x-2][y+1] instanceof Knight && board[x-2][y+1].team != color) return true;
		if (!Board.isEmpty(board, x-2, y-1) && board[x-2][y-1] instanceof Knight && board[x-2][y-1].team != color) return true;
		
		if (!Board.isEmpty(board, x+1, y+2) && board[x+1][y+2] instanceof Knight && board[x+1][y+2].team != color) return true;
		if (!Board.isEmpty(board, x+1, y-2) && board[x+1][y-2] instanceof Knight && board[x+1][y-2].team != color) return true;
		if (!Board.isEmpty(board, x-1, y+2) && board[x-1][y+2] instanceof Knight && board[x-1][y+2].team != color) return true;
		if (!Board.isEmpty(board, x-1, y-2) && board[x-1][y-2] instanceof Knight && board[x-1][y-2].team != color) return true;
		// King
		if (!Board.isEmpty(board, x+1, y) && board[x+1][y] instanceof King && board[x+1][y].team != color) return true;
		if (!Board.isEmpty(board, x-1, y) && board[x-1][y] instanceof King && board[x-1][y].team != color) return true;
		if (!Board.isEmpty(board, x, y+1) && board[x][y+1] instanceof King && board[x][y+1].team != color) return true;
		if (!Board.isEmpty(board, x, y-1) && board[x][y-1] instanceof King && board[x][y-1].team != color) return true;
		
		if (!Board.isEmpty(board, x+1, y+1) && board[x+1][y+1] instanceof King && board[x+1][y+1].team != color) return true;
		if (!Board.isEmpty(board, x-1, y+1) && board[x-1][y+1] instanceof King && board[x-1][y+1].team != color) return true;
		if (!Board.isEmpty(board, x+1, y-1) && board[x+1][y-1] instanceof King && board[x+1][y-1].team != color) return true;
		if (!Board.isEmpty(board, x-1, y-1) && board[x-1][y-1] instanceof King && board[x-1][y-1].team != color) return true;
	
		return false;
	}
	*/
    
	public static boolean isChecked(Piece[][] board, int a, int b, boolean team) {
        for (int i = a+1; i <= 7; i++) { // south (Rook + Queen)
            if (!Board.isEmpty(board, i, b)) {
                    if ((board[i][b] instanceof Rook || board[i][b] instanceof Queen) && board[i][b].color!= team) {
                        return true;
                    } else {
                        break;
                    }
            }   
        }
        
        for (int i = a-1; i >= 0; i--) { // north (Rook + Queen)
            if (!Board.isEmpty(board, i, b)) {
                    if ((board[i][b] instanceof Rook || board[i][b] instanceof Queen) && board[i][b].color != team) {
                        return true;
                    } else {
                        break;
                    }
            }   
        }
        
        for (int i = b+1; i <= 7; i++) { // east (Rook + Queen)
            if (!Board.isEmpty(board, a, i)) {
                    if ((board[a][i] instanceof Rook || board[a][i] instanceof Queen) && board[a][i].color != team) {
                        return true;
                    } else {
                        break;
                    }
            }   
        }
        
        for (int i = b-1; i >= 0; i--) { // west (Rook + Queen)
            if (!Board.isEmpty(board, a, i)) {
                    if ((board[a][i] instanceof Rook || board[a][i] instanceof Queen) && board[a][i].color != team) {
                        return true;
                    } else {
                        break;
                    }
            }   
        }
        
        for (int i = a+1, j = b-1; i <= 7 && j >= 0; i++, j--) { // northeast (Bishop + Queen)
            if (!Board.isEmpty(board, i, j)) { 
                    if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].color != team) {
                        return true;
                    } else {
                        break;
                    }
            }   
        }
        
        for (int i = a-1, j = b-1; i >= 0 && j >= 0; i--, j--) { // northwest (Bishop + Queen)
            if (!Board.isEmpty(board, i, j)) {
                    if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].color != team) {
                        return true;
                    } else {
                        break;
                    }
            }   
        }
        
        for (int i = a+1, j = b+1; i <= 7 && j <= 7; i++, j++) { // southeast (Bishop + Queen)
            if (!Board.isEmpty(board, i, j)) { 
                    if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].color != team) {
                        return true;
                    } else {
                        break;
                    }
            }   
        }
        
        for (int i = a-1, j = b+1; i >= 0 && j <= 7; i--, j++) { // southwest (Bishop + Queen)
            if (!Board.isEmpty(board, i, j)) {
                    if ((board[i][j] instanceof Bishop || board[i][j] instanceof Queen) && board[i][j].color != team) {
                        return true;
                    } else {
                        break;
                    }
            }   
        }
        
        if (team) { // white piece, checking for black Pawn
            if ((!Board.isEmpty(board, a-1, b-1) && board[a-1][b-1] instanceof Pawn && board[a-1][b-1].color != team) ||
            	(!Board.isEmpty(board, a-1, b+1) && board[a-1][b+1] instanceof Pawn && board[a-1][b+1].color != team)) { 
                return true;
            }
        } else { // black piece, checking for white Pawn
            if ((!Board.isEmpty(board, a+1, b-1) && board[a+1][b-1] instanceof Pawn && board[a+1][b-1].color != team) 
            		|| (!Board.isEmpty(board, a+1, b+1) && board[a+1][b+1] instanceof Pawn && board[a+1][b+1].color != team)) { 
                return true;
            }
        }
        
        // Knight
        if (!Board.isEmpty(board, a+2, b+1) && board[a+2][b+1] instanceof Knight && board[a+2][b+1].color != team) return true;
        if (!Board.isEmpty(board, a+2, b-1) && board[a+2][b-1] instanceof Knight && board[a+2][b-1].color != team) return true;
        if (!Board.isEmpty(board, a-2, b+1) && board[a-2][b+1] instanceof Knight && board[a-2][b+1].color != team) return true;
        if (!Board.isEmpty(board, a-2, b-1) && board[a-2][b-1] instanceof Knight && board[a-2][b-1].color != team) return true;
        
        if (!Board.isEmpty(board, a+1, b+2) && board[a+1][b+2] instanceof Knight && board[a+1][b+2].color != team) return true;
        if (!Board.isEmpty(board, a+1, b-2) && board[a+1][b-2] instanceof Knight && board[a+1][b-2].color != team) return true;
        if (!Board.isEmpty(board, a-1, b+2) && board[a-1][b+2] instanceof Knight && board[a-1][b+2].color != team) return true;
        if (!Board.isEmpty(board, a-1, b-2) && board[a-1][b-2] instanceof Knight && board[a-1][b-2].color != team) return true;
        
        // King
        if (!Board.isEmpty(board, a+1, b) && board[a+1][b] instanceof King && board[a+1][b].color != team) return true;
        if (!Board.isEmpty(board, a-1, b) && board[a-1][b] instanceof King && board[a-1][b].color != team) return true;
        if (!Board.isEmpty(board, a, b+1) && board[a][b+1] instanceof King && board[a][b+1].color != team) return true;
        if (!Board.isEmpty(board, a, b-1) && board[a][b-1] instanceof King && board[a][b-1].color != team) return true;
        
        if (!Board.isEmpty(board, a+1, b+1) && board[a+1][b+1] instanceof King && board[a+1][b+1].color != team) return true;
        if (!Board.isEmpty(board, a-1, b+1) && board[a-1][b+1] instanceof King && board[a-1][b+1].color != team) return true;
        if (!Board.isEmpty(board, a+1, b-1) && board[a+1][b-1] instanceof King && board[a+1][b-1].color != team) return true;
        if (!Board.isEmpty(board, a-1, b-1) && board[a-1][b-1] instanceof King && board[a-1][b-1].color != team) return true;
        
        return false;
    }
    
    
	/**
     * A check to determine if a path is clear
     * @param board The board that the piece is on
     * @param x X coordinate of the Piece
     * @param y Y coordinate of the Piece
     * @return True if path is clear
     */
   public abstract boolean isPathClear(Piece[][] board, int x, int y);

}