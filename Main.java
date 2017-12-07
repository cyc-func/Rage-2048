import java.io.IOException;


/* BIG QUESTION

	if after a move, tile x replaces tile y. then should
	tile y's lazy index be assigned to tile x? 
 */


class Main
{
	public static void main(String[] args) throws IOException
	{
		TileHandler tileHandler = new TileHandler(4, 4);
		tileHandler.printGrid();

		do
		{
			char input = (char) System.in.read();
			switch(input)
			{
				case 'w':
					if(tileHandler.isGridCompressibleUp())
					{
						tileHandler.up();
						tileHandler.incrementLazyIndex();
						tileHandler.spawnTile();
					}
					tileHandler.printGrid();
					break;
				case 'a':
					if(tileHandler.isGridCompressibleLeft())
					{
						tileHandler.left();
						tileHandler.incrementLazyIndex();
						tileHandler.spawnTile();
					}
					tileHandler.printGrid();
					break;
				case 's':
					if(tileHandler.isGridCompressibleDown())
					{
						tileHandler.down();
						tileHandler.incrementLazyIndex();
						tileHandler.spawnTile();
					}
					tileHandler.printGrid();
					break;
				case 'd':
					if(tileHandler.isGridCompressibleRight())
					{
						tileHandler.right();
						tileHandler.incrementLazyIndex();
						tileHandler.spawnTile();
					}
					tileHandler.printGrid();
					break;
			}
		}while(!tileHandler.isGameOver());
	}
}
