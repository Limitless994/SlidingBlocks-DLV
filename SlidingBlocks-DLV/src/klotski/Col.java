package klotski;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("col")
public class Col {

	@Param(0)
	private int x;
	@Param(1)
	private String col;
	
	public Col(int x,String c){
		this.x=x;
		this.col=c;
	}
	
	public Col() {
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	
	public String getCol() {
		return col;
	}

	public void setCol(String c) {
		this.col = c;
	}
	
	public String toString() {
		String s = "";
		s += "col(" + x + "," + col + ")";
		return s;
	}
	

}
