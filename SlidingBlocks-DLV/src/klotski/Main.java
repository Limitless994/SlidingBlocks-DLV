package klotski;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import klotski.model.Board;
import klotski.view.KlotskiApp;

public class Main {
	private static String encodingResource="encodings/SlidingBlocks-Rules";
	private static String instanceResource="encodings/SlidingBlocks-instance";
	private static Handler handler;
	public static void main(String[] args) {
		handler = new DesktopHandler(new DLVDesktopService("lib/dlv.mingw.exe"));
		InputProgram  program = new ASPInputProgram();
		program.addFilesPath(encodingResource);
		program.addFilesPath(instanceResource);
		handler.addProgram(program);

		Board b = new Board();
		KlotskiApp app = new KlotskiApp(b);
		// register the class for reflection
		try {
			ASPMapper.getInstance().registerClass(Col.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Output o =  handler.startSync();
		
		AnswerSets answers = (AnswerSets) o;
		
		int n=0;
		for(AnswerSet a:answers.getAnswersets()){
			 System.out.println("AS n.: " + ++n + ": " + a);
			try {

				for(Object obj:a.getAtoms()){
					if(obj instanceof Col)  {
						Col col = (Col) obj;
						System.out.print(col + " ");
					}
				}
				System.out.println();
			} catch (Exception e) {
				e.printStackTrace();
			} 			
		}
		
		
		app.setVisible(true);
	}
}
