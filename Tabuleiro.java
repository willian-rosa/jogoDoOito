package jogoDoOito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

public class Tabuleiro {

	private final int margem = 10;
	private final int tamanhoBotao = 25;
	
	private ArrayList<Peca> estadoAtual = new ArrayList<Peca>();
	
	private int[] objetivo = {1,2,3,4,5,6,7,8,0}; 
	
	public Tabuleiro(Shell shell) {
		this.gerarTabuleiro(shell);
		
	}
	
	private void gerarTabuleiro(Shell shell) {
		
		List<Integer> numerosPecas = this.gerarNumeroPecasRandomicos();
		
		for (int i = 0; i < numerosPecas.size(); i++) {
			Peca peca = this.gerarPecas(shell, numerosPecas.get(i), i);
			
			estadoAtual.add(peca);
			
		}
		
	}
	
	private List<Integer> gerarNumeroPecasRandomicos() {
		List<Integer> numeroPecas = new ArrayList<Integer>();
		
		for (int i = 0; i < 9; i++){
			numeroPecas.add(i);
		}
		Collections.shuffle(numeroPecas);
		
		return numeroPecas;
		
	}
	
	private int gerarWidthPeca(int index){
		
		int mod = index % 3;
		
		int width = (mod*this.tamanhoBotao)+(this.margem*mod)+this.margem;
		
		return width;
		
	}
	
	private int gerarHeightPeca(int index){

		int coord = (int)index/3; 

		int height = (coord*this.tamanhoBotao)+(this.margem*coord)+this.margem;
		
		return height;
		
	}
	
	private Peca gerarPecas(Shell shell, Integer labelButton, int index) {
		
		Button btnPeca = new Button(shell, SWT.NONE);
		btnPeca.setBounds(gerarWidthPeca(index), gerarHeightPeca(index), tamanhoBotao, tamanhoBotao);
		btnPeca.setText(labelButton+"");
		
		Peca peca = new Peca();
		peca.setBotao(btnPeca);
		
		return peca;
		
	}
	
	private int heuristicaQuantidadePecaLugarCerto(ArrayList<Peca> pecas){
		
		int qtdLugarCeto = 0;
		
		for (int i = 0; i < this.objetivo.length; i++) {
			if(pecas.get(i).getNumeroPeca() == objetivo[i]){
				qtdLugarCeto++;
			}
		}
		
		return qtdLugarCeto;
		
	}
	
	
}
