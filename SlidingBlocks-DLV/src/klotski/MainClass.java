package klotski;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class MainClass {

	public MainClass() {
	}
	
//	private static String encodingResource="encodings/3col";
//	private static String instanceResource="encodings/3col-instance";
//	private static Handler handler;
//	
////	private static String encoding = "col(X,red) | col(X,blue) | col(X,green) :- node(X).\r\n" 
////	                               + ":- col(X,C), col(Y,C), arc(X,Y).";
////	private static String instance = " node(1..5).\r\n" + 
////									  "arc(1,2).\r\n" + 
////			                          "arc(1,3).\r\n" + 
////								      "arc(2,3).\r\n" + 
////								      "arc(4,5).\r\n" + 
////								      "arc(1,4).";
//								
//	public static void main(String[] args) {
//		
//		handler = new DesktopHandler(new DLVDesktopService("lib/dlv.mingw.exe"));
//		
//		InputProgram  program = new ASPInputProgram();
////		program.addProgram(encoding);
////		program.addProgram(instance);
//		program.addFilesPath(encodingResource);
//		program.addFilesPath(instanceResource);
//		handler.addProgram(program);
//		
//		// register the class for reflection
//		try {
//			ASPMapper.getInstance().registerClass(Col.class);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Output o =  handler.startSync();
//		
//		AnswerSets answers = (AnswerSets) o;
//		
//		int n=0;
//		for(AnswerSet a:answers.getAnswersets()){
//			// System.out.println("AS n.: " + ++n + ": " + a);
//			try {
//
//				for(Object obj:a.getAtoms()){
//					if(obj instanceof Col)  {
//						Col col = (Col) obj;
//						System.out.print(col + " ");
//					}
//				}
//				System.out.println();
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 			
//		}
//	}
}

	
	