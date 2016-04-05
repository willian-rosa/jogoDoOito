package jogoDoOito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Button;

public class ArvoreTabuleiros {

	private Tabuleiro raiz;
	private Tabuleiro ponteiro;
	
	private Tabuleiro  objetivo = new Tabuleiro(); 
	
	public ArvoreTabuleiros(ArrayList<Button> botoes) {
		
		this.gerarObjetivo();  
		
		this.gerarTabuleiroRaiz();
		
		System.out.println("Inicio");
		System.out.println(this.raiz);
		
		this.gerarTabuleirosPossivel(this.ponteiro);
		
		int i = 0;
		
		while (this.isJogoFinalizado() == false) {
//			try {
//				Thread.sleep(1000);
//			} catch (Exception e) {
//			}
			
			if(this.ponteiro.getTabuleiroFilhos().size() == 0){
				this.ponteiro = this.heuristicaGetParenteMelhor(this.ponteiro.getTabuleiroPai());
			}else{
				this.ponteiro = heuristicaGetTabuleiroMelhor(this.ponteiro);
//				this.atualizaView(this.ponteiro, botoes);
			}
			
			this.gerarTabuleirosPossivel(this.ponteiro);
			
			i++;
			
			System.out.println(i);
			
		}
		
		System.out.println("\n\n Pontos: "+this.ponteiro.getQuantidadePontos()+": Jogadas: "+i);
		System.out.println(this.ponteiro);
		
		
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
			
			try {
				pecas[posicaoEspacoBranco[0]][posicaoEspacoBranco[1]] = pecas[posicao[0]][posicao[1]].clone();
			} catch (Exception e) {
				System.out.println("Tabuleiro atual sem peças");
				
				System.exit(0);
			}
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
		
		ordenaTabuleiroFilhosBubbleSortMelhorPontuacao(tabuleiroAtual);
		
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
		
//		numeroPecas.add(6);
//		numeroPecas.add(3);
//		numeroPecas.add(5);
//		numeroPecas.add(0);
//		numeroPecas.add(2);
//		numeroPecas.add(8);
//		numeroPecas.add(1);
//		numeroPecas.add(7);
//		numeroPecas.add(4);
		
		return numeroPecas;
		
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
	
	private void atualizaView(Tabuleiro tabuleiro, ArrayList<Button> botoes) {

		Peca[][] pecasTabuleiro = tabuleiro.getPecas();
		
		int i = 0;
		
		for (int x = 0; x < pecasTabuleiro.length; x++) {
			for (int y = 0; y < pecasTabuleiro[x].length; y++) {
				botoes.get(i).setText(""+((pecasTabuleiro[x][y]==null)?0:pecasTabuleiro[x][y].getNumeroPeca()));
				i++;
				
			}
		}
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
	
	private void ordenaTabuleiroFilhosBubbleSortMelhorPontuacao(Tabuleiro tabuleiro){
		
		int qtdFilhos = tabuleiro.getTabuleiroFilhos().size();
		ArrayList<Tabuleiro> tabuleirosFilhos = tabuleiro.getTabuleiroFilhos();
		
		for (int i = 0; i < qtdFilhos-1; i++) {
			for (int j = 1; j < qtdFilhos-i; j++) {
				if(tabuleirosFilhos.get(j-1).getQuantidadePontos() < tabuleirosFilhos.get(j).getQuantidadePontos()){
					Tabuleiro tabuleiroTemp = tabuleirosFilhos.get(j-1);

					tabuleirosFilhos.set(j-1, tabuleirosFilhos.get(j));
					tabuleirosFilhos.set(j, tabuleiroTemp);
					
				}
			}
		}
		
	}

	private Tabuleiro heuristicaGetTabuleiroMelhor(Tabuleiro tabuleiro){
		try {
			return tabuleiro.getTabuleiroFilhos().get(0);
		} catch (Exception e) {
			System.out.println("Erro:\n" + tabuleiro);
			System.out.println("filhos: " + tabuleiro.getTabuleiroFilhos().size());
			System.exit(0);
		}
		return null;
		
	}
	private Tabuleiro heuristicaGetParenteMelhor(Tabuleiro tabuleiroPai){
		
		ArrayList<Tabuleiro> tabuleiros = tabuleiroPai.getTabuleiroFilhos();
		
		tabuleiroPai.incIndexFilhoAtual();
		
		int indexAtual = tabuleiroPai.getIndexFilhoAtual();

		if(tabuleiros.size()<=indexAtual){
			return heuristicaGetParenteMelhor(tabuleiroPai.getTabuleiroPai());
		}
		
		return tabuleiros.get(tabuleiroPai.getIndexFilhoAtual());
		
	}
	
	
	
}
