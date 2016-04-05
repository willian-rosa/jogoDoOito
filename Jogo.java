package jogoDoOito;

import java.util.ArrayList;

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
		shell.setSize(190, 210);
		shell.setText("Jogo do Oito");

		ArrayList<Button> botoes = gerarBotoes(shell);
		
		shell.open();
		shell.layout();
		
		ArvoreTabuleiros tabuleiro = new ArvoreTabuleiros(botoes);
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
	public static ArrayList<Button> gerarBotoes(Shell shell) {
		
		ArrayList<Button> botoes = new ArrayList<Button>();
		
		Button button = new Button(shell, SWT.NONE);
		button.setBounds(10, 10, 50, 50);
		botoes.add(button);
		
		button = new Button(shell, SWT.NONE);
		button.setBounds(10, 60, 50, 50);
		botoes.add(button);

		button = new Button(shell, SWT.NONE);
		button.setBounds(10, 110, 50, 50);
		botoes.add(button);
		
		button = new Button(shell, SWT.NONE);
		button.setBounds(60, 10, 50, 50);
		botoes.add(button);
		
		button = new Button(shell, SWT.NONE);
		button.setBounds(60, 60, 50, 50);
		botoes.add(button);

		button = new Button(shell, SWT.NONE);
		button.setBounds(60, 110, 50, 50);
		botoes.add(button);
		
		button = new Button(shell, SWT.NONE);
		button.setBounds(110, 10, 50, 50);
		botoes.add(button);
		
		button = new Button(shell, SWT.NONE);
		button.setBounds(110, 60, 50, 50);
		botoes.add(button);
		
		button = new Button(shell, SWT.NONE);
		button.setBounds(110, 110, 50, 50);
		botoes.add(button);
		
		return botoes;
	}
}
