package jogoDoOito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

public class ArvoreTabuleiros {

	private Tabuleiro raiz;
	private Tabuleiro ponteiro;
	
	
	private final int margem = 10;

	private final int tamanhoBotao = 25;
	
	private ArrayList<Peca> estadoAtual = new ArrayList<Peca>();
	
	private int[] objetivo = {1,2,3,4,5,6,7,8,0}; 
	
	public ArvoreTabuleiros(Shell shell) {
		
		this.gerarTabuleiroRaiz();
		System.out.println(this.raiz);
		
		this.gerarTabuleirosPossivel();
		
		
	}
	
	private void gerarTabuleiroRaiz() {
		
		List<Integer> numerosPecas = this.gerarNumeroPecasRandomicos();
		
		Tabuleiro tabuleiro =  new Tabuleiro();
		
		tabuleiro.populatePecasByListInteger(numerosPecas);
		
		this.raiz = tabuleiro;
		this.ponteiro = tabuleiro;
		
		
	}
			
	private Tabuleiro gerarTabuleiroPossivel(int[] posicaoEspacoBranco, int[] posicao) {
		
		if(posicao[0] >= 0 && posicao[0] <= 2 && posicao[1] >= 0 && posicao[1] <= 2){
			
			Tabuleiro tabuleiroAtual = this.ponteiro;
			
			Tabuleiro novoTabuleiro = new Tabuleiro();
			novoTabuleiro.setPecas(tabuleiroAtual.clonePecas());
			novoTabuleiro.setTabuleiroPai(tabuleiroAtual);
			
			Peca[][] pecas = novoTabuleiro.getPecas();
			
			pecas[posicaoEspacoBranco[0]][posicaoEspacoBranco[1]] = pecas[posicao[0]][posicao[1]].clone();
			pecas[posicao[0]][posicao[1]] = null;
			
			
			return novoTabuleiro;
		}
		
		return null;
		
	}
	
	private void gerarTabuleirosPossivel() {
		
		int[] posicaoEspacoBranco = this.raiz.getPosicaoEspacoBranco();
		
		int[][] novasPosicoes = new int[4][2];
		
		
		//Movimentos
		novasPosicoes[0] = this.gerarOperadorMovimentoParaCima(posicaoEspacoBranco);
		novasPosicoes[1] = this.gerarOperadorMovimentoParaBaixo(posicaoEspacoBranco);
		novasPosicoes[2] = this.gerarOperadorMovimentoParaEsquerda(posicaoEspacoBranco);
		novasPosicoes[3] = this.gerarOperadorMovimentoParaDireita(posicaoEspacoBranco);
		
		
		
		for (int i = 0; i < novasPosicoes.length; i++) {
			
			System.out.println("novo "+novasPosicoes[i][0]+" "+novasPosicoes[i][1]);
			Tabuleiro novoTabuleiro = this.gerarTabuleiroPossivel(posicaoEspacoBranco, novasPosicoes[i]);
			if(novoTabuleiro != null){
					System.out.println(novoTabuleiro);
			}
			
		}
		
		
		
	}
	
	private int[] gerarOperadorMovimentoParaCima(int[] posicao){
		int[] novaPosicao = posicao.clone();
		novaPosicao[0]--;
		return novaPosicao;
	}
	
	private int[] gerarOperadorMovimentoParaBaixo(int[] posicao){
		int[] novaPosicao = posicao.clone();
		novaPosicao[0]++;
		return novaPosicao;
	}
	
	private int[] gerarOperadorMovimentoParaEsquerda(int[] posicao){
		int[] novaPosicao = posicao.clone();
		novaPosicao[1]--;
		return novaPosicao;
	}
	
	private int[] gerarOperadorMovimentoParaDireita(int[] posicao){
		int[] novaPosicao = posicao.clone();
		novaPosicao[1]++;
		return novaPosicao;
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
	
	public Tabuleiro getRaiz() {
		return raiz;
	}

	public void setRaiz(Tabuleiro raiz) {
		this.raiz = raiz;
	}

	public Tabuleiro getPonteiro() {
		return ponteiro;
	}

	public void setPonteiro(Tabuleiro ponteiro) {
		this.ponteiro = ponteiro;
	}
	
}
