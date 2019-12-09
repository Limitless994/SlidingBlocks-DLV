package klotski.solver;


public class Block {
	String name;
	int id;
	int area;
	int riga;
	int colonna;
	Type type;
	
	public enum Type{
		SINGLE,DOUBLE_WIDE,DOUBLE_TALL,QUAD
	}
	
	public Block(int i, int h, int w, String n){
		this.name = n;
		this.riga = h;
		this.colonna = w;
		this.area = h * w;
		this.id = i;
	}
	
	@Override public String toString(){
		return "{ name: " + this.name + " Size:(" + this.colonna + "," + this.riga + ") area: " + this.area + " }";
	}
}
