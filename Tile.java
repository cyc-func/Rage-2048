class Tile
{
	private int value;					// number on the tile
	private int lazyIndex;			// how long has the tile been sitting here


	public Tile(final int value)
	{
		this.value = value;
		lazyIndex = 0;
	}

	public int getLazyIndex()
	{
		return lazyIndex;
	}

	public void resetLazyIndex()
	{
		lazyIndex = -1;
	}

	public void incrementLazyIndex()
	{
		lazyIndex++;
	}

	public int getValue()
	{
		return value;
	}

	public void combineTile()
	{
		value *= 2;
	}
}
