
public class PairInt {
	private int x;
	private int y;

	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean equals(Object p) {
		if (this == p)
			return true;
		if (p == null || getClass() != p.getClass())
			return false;
		PairInt pair = (PairInt) p;
		return x == pair.x && y == pair.y;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	public PairInt copy() {
		return new PairInt(this.x, this.y);
	}
}
