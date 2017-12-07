class TileHandler
{
	private final int ROW_COUNT;
	private final int COL_COUNT;

	private final Tile grid[][];
	private final int resetTracker[][];			// (index, r, c)
	private int resetTrackerLength = 0;

	public TileHandler(int rowCount, int colCount)
	{
		ROW_COUNT = rowCount;
		COL_COUNT = colCount;

		grid = new Tile[ROW_COUNT][COL_COUNT];
		resetTracker = new int[ROW_COUNT * COL_COUNT][3];

		initTileSetup();
	}

	private void initTileSetup()			// randomize this?
	{
		grid[0][0] = new Tile(2);
		grid[ROW_COUNT - 1][COL_COUNT - 1] = new Tile(2);
	}

	public void spawnTile()			// modify this
	{
		int maxima = -1;
		int row = -1;
		int col = -1;

		for(int i = 0; i < resetTrackerLength; i++)
		{
			if(resetTracker[i][0] > maxima && grid[resetTracker[i][1]][resetTracker[i][2]] == null)
			{
				maxima = resetTracker[i][0];
				row = resetTracker[i][1];
				col = resetTracker[i][2];
			}
		}

		resetTrackerLength = 0;

		if(maxima != -1)
		{
			grid[row][col] = new Tile(2);	// or 4? also tie breaking?
			return;
		}

		for(int i = 0; i < ROW_COUNT; i++)
		{
			for(int j = 0; j < COL_COUNT; j++)
			{
				if(grid[i][j] == null)
				{
					grid[i][j] = new Tile(2);
					return;
				}
			}
		}
	}

	public void incrementLazyIndex()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			for(int c = 0; c < COL_COUNT; c++)
			{
				if(grid[r][c] != null)
				{
					grid[r][c].incrementLazyIndex();
				}
			}
		}
	}

	public void left()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			leftShiftGroup(r, 0);
		}
	}

	public void right()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			rightShiftGroup(r, COL_COUNT - 1);
		}
	}

	public void up()
	{
		for(int c = 0; c < COL_COUNT; c++)
		{
			upShiftGroup(c, 0);
		}
	}

	public void down()
	{
		for(int c = 0; c < COL_COUNT; c++)
		{
			downShiftGroup(c, ROW_COUNT - 1);
		}
	}

	private void leftShiftGroup(int r, int baseCol)
	{
		int c = baseCol;
		while(c < COL_COUNT)
		{
			if(grid[r][c] != null)
			{
				if(c != baseCol)
				{
					resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
					resetTracker[resetTrackerLength][1] = r;
					resetTracker[resetTrackerLength][2] = c;
					resetTrackerLength++;

					grid[r][c].resetLazyIndex();
					grid[r][baseCol] = grid[r][c];
					grid[r][c] = null;
				}

				break;
			}

			c++;
		}

		c++;
		while(c < COL_COUNT)
		{
			if(grid[r][c] != null)
			{
				if(grid[r][c].getValue() == grid[r][baseCol].getValue())
				{
					resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
					resetTracker[resetTrackerLength][1] = r;
					resetTracker[resetTrackerLength][2] = c;
					resetTrackerLength++;

					grid[r][baseCol].combineTile();
					grid[r][c] = null;
				}
				else
				{
					if(c != baseCol + 1)
					{
						resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
						resetTracker[resetTrackerLength][1] = r;
						resetTracker[resetTrackerLength][2] = c;
						resetTrackerLength++;

						grid[r][c].resetLazyIndex();
						grid[r][baseCol + 1] = grid[r][c];
						grid[r][c] = null;
					}
				}

				c = baseCol + 1;
				if(c < COL_COUNT)
				{
					leftShiftGroup(r, c);
				}

				break;
			}

			c++;
		}
	}

	private void rightShiftGroup(int r, int baseCol)
	{
		int c = baseCol;
		while(c >= 0)
		{
			if(grid[r][c] != null)
			{
				if(c != baseCol)
				{
					resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
					resetTracker[resetTrackerLength][1] = r;
					resetTracker[resetTrackerLength][2] = c;
					resetTrackerLength++;

					grid[r][c].resetLazyIndex();
					grid[r][baseCol] = grid[r][c];
					grid[r][c] = null;
				}

				break;
			}

			c--;
		}

		c--;
		while(c >= 0)
		{
			if(grid[r][c] != null)
			{
				if(grid[r][c].getValue() == grid[r][baseCol].getValue())
				{
					resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
					resetTracker[resetTrackerLength][1] = r;
					resetTracker[resetTrackerLength][2] = c;
					resetTrackerLength++;

					grid[r][baseCol].combineTile();
					grid[r][c] = null;
				}
				else
				{
					if(c != baseCol - 1)
					{
						resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
						resetTracker[resetTrackerLength][1] = r;
						resetTracker[resetTrackerLength][2] = c;
						resetTrackerLength++;

						grid[r][c].resetLazyIndex();
						grid[r][baseCol - 1] = grid[r][c];
						grid[r][c] = null;
					}
				}

				c = baseCol - 1;
				if(c >= 0)
				{
					rightShiftGroup(r, c);
				}

				break;
			}

			c--;
		}
	}

	private void upShiftGroup(int c, int baseRow)
	{
		int r = baseRow;
		while(r < ROW_COUNT)
		{
			if(grid[r][c] != null)
			{
				if(r != baseRow)
				{
					resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
					resetTracker[resetTrackerLength][1] = r;
					resetTracker[resetTrackerLength][2] = c;
					resetTrackerLength++;

					grid[r][c].resetLazyIndex();
					grid[baseRow][c] = grid[r][c];
					grid[r][c] = null;
				}

				break;
			}

			r++;
		}

		r++;
		while(r < ROW_COUNT)
		{
			if(grid[r][c] != null)
			{
				if(grid[r][c].getValue() == grid[baseRow][c].getValue())
				{
					resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
					resetTracker[resetTrackerLength][1] = r;
					resetTracker[resetTrackerLength][2] = c;
					resetTrackerLength++;

					grid[baseRow][c].combineTile();
					grid[r][c] = null;
				}
				else
				{
					if(r != baseRow + 1)
					{
						resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
						resetTracker[resetTrackerLength][1] = r;
						resetTracker[resetTrackerLength][2] = c;
						resetTrackerLength++;

						grid[r][c].resetLazyIndex();
						grid[baseRow + 1][c] = grid[r][c];
						grid[r][c] = null;
					}
				}

				r = baseRow + 1;
				if(r < ROW_COUNT)
				{
					upShiftGroup(c, r);
				}

				break;
			}

			r++;
		}
	}

	private void downShiftGroup(int c, int baseRow)
	{
		int r = baseRow;
		while(r >= 0)
		{
			if(grid[r][c] != null)
			{
				if(r != baseRow)
				{
					resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
					resetTracker[resetTrackerLength][1] = r;
					resetTracker[resetTrackerLength][2] = c;
					resetTrackerLength++;

					grid[r][c].resetLazyIndex();
					grid[baseRow][c] = grid[r][c];
					grid[r][c] = null;
				}

				break;
			}

			r--;
		}

		r--;
		while(r >= 0)
		{
			if(grid[r][c] != null)
			{
				if(grid[r][c].getValue() == grid[baseRow][c].getValue())
				{
					resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
					resetTracker[resetTrackerLength][1] = r;
					resetTracker[resetTrackerLength][2] = c;
					resetTrackerLength++;

					grid[baseRow][c].combineTile();
					grid[r][c] = null;
				}
				else
				{
					if(r != baseRow - 1)
					{
						resetTracker[resetTrackerLength][0] = grid[r][c].getLazyIndex();
						resetTracker[resetTrackerLength][1] = r;
						resetTracker[resetTrackerLength][2] = c;
						resetTrackerLength++;

						grid[r][c].resetLazyIndex();
						grid[baseRow - 1][c] = grid[r][c];
						grid[r][c] = null;
					}
				}

				r = baseRow - 1;
				if(r >= 0)
				{
					downShiftGroup(c, r);
				}

				break;
			}

			r--;
		}
	}

	public boolean isGridCompressibleLeft()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			for(int c = 0; c < COL_COUNT; c++)
			{
				if(grid[r][c] != null && canTileMoveLeft(r, c))
				{
					return true;
				}
			}
		}

		return false;
	}

	public boolean isGridCompressibleRight()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			for(int c = 0; c < COL_COUNT; c++)
			{
				if(grid[r][c] != null && canTileMoveRight(r, c))
				{
					return true;
				}
			}
		}

		return false;
	}

	public boolean isGridCompressibleUp()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			for(int c = 0; c < COL_COUNT; c++)
			{
				if(grid[r][c] != null && canTileMoveUp(r, c))
				{
					return true;
				}
			}
		}

		return false;
	}

	public boolean isGridCompressibleDown()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			for(int c = 0; c < COL_COUNT; c++)
			{
				if(grid[r][c] != null && canTileMoveDown(r, c))
				{
					return true;
				}
			}
		}

		return false;
	}

	private boolean canTileMoveLeft(final int r, final int c)			// tile's row, col
	{
		if(c - 1 >= 0)
		{
			if(grid[r][c - 1] == null || grid[r][c - 1].getValue() == grid[r][c].getValue())
			{
				return true;
			}
		}

		return false;
	}

	private boolean canTileMoveRight(final int r, final int c)		// tile's row, col
	{
		if(c + 1 < COL_COUNT)
		{
			if(grid[r][c + 1] == null || grid[r][c + 1].getValue() == grid[r][c].getValue())
			{
				return true;
			}
		}

		return false;
	}

	private boolean canTileMoveUp(final int r, final int c)				// tile's row, col
	{
		if(r - 1 >= 0)
		{
			if(grid[r - 1][c] == null || grid[r - 1][c].getValue() == grid[r][c].getValue())
			{
				return true;
			}
		}

		return false;
	}

	private boolean canTileMoveDown(final int r, final int c)			// tile's row, col
	{
		if(r + 1 < ROW_COUNT)
		{
			if(grid[r + 1][c] == null || grid[r + 1][c].getValue() == grid[r][c].getValue())
			{
				return true;
			}
		}

		return false;
	}

	private boolean isGridFull()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			for(int c = 0; c < COL_COUNT; c++)
			{
				if(grid[r][c] == null)
				{
					return false;
				}
			}
		}

		return true;
	}

	public boolean isGameOver()
	{
		if(!isGridFull())
		{
			return false;
		}

		return !(isGridCompressibleLeft() 	||
						 isGridCompressibleRight() 	||
						 isGridCompressibleUp() 		||
						 isGridCompressibleDown());
	}


	final private int SCORE_PADDING = 8;
	/* Padding is the maximum length of number + 2 
	 * Set padding wisely
	 * With r rows and c columns, maximum padding should be
	 * floor(log10(2**(rc+3))) + 1? Check this and modify
	 */

	public void printGrid()
	{
		for(int r = 0; r < ROW_COUNT; r++)
		{
			for(int c = 0; c < COL_COUNT; c++)
			{
				if(grid[r][c] == null)
				{
					System.out.printf("%" + SCORE_PADDING + "s", "_");
				}
				else
				{
					System.out.printf("%" + SCORE_PADDING + "d", grid[r][c].getValue());
				}
			}
			System.out.println();
		}
		System.out.println();

		/*for(int r = 0; r < ROW_COUNT; r++)
		{
			for(int c = 0; c < COL_COUNT; c++)
			{
				if(grid[r][c] == null)
				{
					System.out.printf("%" + SCORE_PADDING + "s", "_");
				}
				else
				{
					System.out.printf("%" + SCORE_PADDING + "d", grid[r][c].getLazyIndex());
				}
			}
			System.out.println();
		}
		System.out.println();*/
	}
}
