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
	
	private Tabuleiro  objetivo = new Tabuleiro(); 
	
	public ArvoreTabuleiros(Shell shell) {
		
		this.gerarObjetivo();
		
		this.gerarTabuleiroRaiz();
		
		this.gerarTabuleirosPossivel(this.ponteiro);
		
		int i = 0;
		
		while (i < 100 && this.isJogoFinalizado() == false) {
			
			System.out.println("\n\n Pontos: "+this.ponteiro.getQuantidadePontos());
			System.out.println(this.ponteiro);
			
			Tabuleiro tabuleiroMaior = heuristicaGetTabuleiroMelhor(this.ponteiro);
			
			this.ponteiro = tabuleiroMaior;
			
			this.gerarTabuleirosPossivel(this.ponteiro);
			
			i++;
		}
		
		
	}
	
	private void gerarTabuleiroRaiz() {
		
		List<Integer> numerosPecas = this.gerarNumeroPecasRandomicos();
		
		Tabuleiro tabuleiro =  new Tabuleiro();
		
		tabuleiro.populatePecasByListInteger(numerosPecas);
		
		this.raiz = tabuleiro;
		this.ponteiro = tabuleiro;
		
		
	}
	
			
	private void gerarObjetivo() {
		
		//TODO o populatePecasByListInteger esta errado
		
		List<Integer> numerosPecas = new ArrayList<Integer>();
		
		for (int i = 1; i < 9; i++){
			numerosPecas.add(i);
		}
		
		numerosPecas.add(0);
		
		Tabuleiro tabuleiro =  new Tabuleiro();
		
		tabuleiro.populatePecasByListInteger(numerosPecas);
		
		this.objetivo = tabuleiro;
		
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
	
	private void gerarTabuleirosPossivel(Tabuleiro tabuleiroAtual) {
		
		int[] posicaoEspacoBranco = tabuleiroAtual.getPosicaoEspacoBranco();
		
		int[][] novasPosicoes = new int[4][2];
		
		//Movimentos do jogo
		novasPosicoes[0] = this.gerarOperadorMovimentoParaCima(posicaoEspacoBranco);
		novasPosicoes[1] = this.gerarOperadorMovimentoParaBaixo(posicaoEspacoBranco);
		novasPosicoes[2] = this.gerarOperadorMovimentoParaEsquerda(posicaoEspacoBranco);
		novasPosicoes[3] = this.gerarOperadorMovimentoParaDireita(posicaoEspacoBranco);
		
		
		//Gera jogas possiveis do objeto atual, com os movimentos permitidos
		for (int i = 0; i < novasPosicoes.length; i++) {
			
			Tabuleiro novoTabuleiro = this.gerarTabuleiroPossivel(posicaoEspacoBranco, novasPosicoes[i]);
						
			if(novoTabuleiro != null){
				
				novoTabuleiro.setTabuleiroPai(tabuleiroAtual);
				
				if(!this.heuristicaComparaTabuleirosAnteriores(novoTabuleiro)){
					
					this.heuristicaQuantidadePecaLugarCerto(novoTabuleiro);
					
					tabuleiroAtual.addTabuleiroFilho(novoTabuleiro);
					
				}
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
	
	
	private boolean isJogoFinalizado(){
		
		return this.ponteiro.getQuantidadePontos() == 9;
		
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
	
	//IA
	
	private boolean heuristicaComparaTabuleirosAnteriores(Tabuleiro tabuleiro){
		
		boolean iguais = false;
		
		Tabuleiro tabuleiroPai = tabuleiro.getTabuleiroPai();
		
		while(tabuleiroPai != null && iguais == false){
			
			tabuleiroPai = tabuleiroPai.getTabuleiroPai();
			
			iguais = heuristicaComparaTabuleirosSaoIguais(tabuleiro, tabuleiroPai);
			
		}
		
		
		return iguais;
		
	}
	private boolean heuristicaComparaTabuleirosSaoIguais(Tabuleiro tabuleiro1, Tabuleiro tabuleiro2){
		
		int pecasNaMesmaPosicao = 0;
		
		if(tabuleiro1 != null && tabuleiro2 != null){
			
			Peca[][] pecasTabuleiro1 =	tabuleiro1.getPecas();
			Peca[][] pecasTabuleiro2= 	tabuleiro2.getPecas();
			
			for (int x = 0; x < pecasTabuleiro1.length; x++) {
				for (int y = 0; y < pecasTabuleiro1[x].length; y++) {
					
					Peca peca1 = pecasTabuleiro1[x][y];
					Peca peca2 = pecasTabuleiro2[x][y];
					if(peca1 == null && peca2 == null){
						pecasNaMesmaPosicao++;
					}else if(peca1 != null && peca2 != null && peca1.getNumeroPeca() == peca2.getNumeroPeca()){
						pecasNaMesmaPosicao++;
					}
				}
			}
		}

		return pecasNaMesmaPosicao == 9;
		
	}
	
	private void heuristicaQuantidadePecaLugarCerto(Tabuleiro tabuleiro){
		
		int quantidadePontos = 0;
		
		Peca[][] pecasTabuleiro = tabuleiro.getPecas();
		Peca[][] pecasObjetivo = this.objetivo.getPecas();
		
		for (int x = 0; x < pecasTabuleiro.length; x++) {
			for (int y = 0; y < pecasTabuleiro[x].length; y++) {
				
				Peca pecaTabuleiro = pecasTabuleiro[x][y];
				Peca pecaObjetivo = pecasObjetivo[x][y];
				if(pecaTabuleiro == null && pecaObjetivo == null){
					quantidadePontos++;
				}else if(pecaTabuleiro != null && pecaObjetivo != null && pecaTabuleiro.getNumeroPeca() == pecaObjetivo.getNumeroPeca()){
					quantidadePontos++;
				}
			}
		}

		tabuleiro.setQuantidadePontos(quantidadePontos);
		
	}
	
	private Tabuleiro heuristicaGetTabuleiroMelhor(Tabuleiro tabuleiro){
		
		Tabuleiro tabuleiroMelhor = new Tabuleiro();
		
		for (Tabuleiro tabuleiroFilho : tabuleiro.getTabuleiroFilhos()) {
			if(tabuleiroFilho.getQuantidadePontos() > tabuleiroMelhor.getQuantidadePontos()){
				tabuleiroMelhor = tabuleiroFilho;
			}
		}
		
		return tabuleiroMelhor;
		
	}
	
}
