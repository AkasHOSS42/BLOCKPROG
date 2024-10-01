import javax.swing.*;
import java.io.IOException;

/**La classe a executer.*/
public class DEAP {
	public static void main(String[] args) throws IOException{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				try{
					Fenetre fenetre = new Fenetre();
				}catch(IOException e){}
			}
		});
	}
}
