package jogoDoOito;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;

public class Jogo {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Jogo do Oito");
		
		ArvoreTabuleiros tabuleiro = new ArvoreTabuleiros(shell);
		
		
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
