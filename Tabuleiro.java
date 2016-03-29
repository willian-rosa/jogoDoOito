package jogoDoOito;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

	private Tabuleiro tabuleiroPai;
	private ArrayList<Tabuleiro> tabuleiroFilhos = new ArrayList<Tabuleiro>();
	
	private Peca[][] pecas = new Peca[3][3];
	
	

	public Peca[][] getPecas() {
		return pecas;
	}
	
	public Peca[][] clonePecas() {
		
		Peca[][] novaPecas = new Peca[3][3];
		
		for (int x = 0; x < pecas.length; x++) {
			for (int y = 0; y < pecas[x].length; y++) {
				novaPecas[x][y] = (this.pecas[x][y] == null)?null:this.pecas[x][y].clone();  
			}
		}
		
		return novaPecas;
		
	}

	public void setPecas(Peca[][] pecas) {
		this.pecas = pecas;
	}
	
	public void populatePecasByListInteger(List<Integer> numerosPecas){
		
		int k = 0;
		for (int i = 0; i < pecas.length; i++) {
			for (int j = 0; j < pecas[i].length; j++) {
				if(numerosPecas.get(k) != 0){
					Peca peca = new Peca();
					peca.setNumeroPeca(numerosPecas.get(k));
					this.pecas[j][i] = peca;
				}
				k++;
			}
		}
	}
	
	public int[] getPosicaoEspacoBranco(){
		
		int[] posicao = {0,0};
		
		for (int x = 0; x < pecas.length; x++) {
			for (int y = 0; y < pecas[x].length; y++) {
				if(this.pecas[x][y] == null){
					posicao[0] = x;
					posicao[1] = y;
					
					return posicao;
				}
			}
		}
		return posicao;
	}
	
	public Tabuleiro getTabuleiroPai() {
		return tabuleiroPai;
	}

	public void setTabuleiroPai(Tabuleiro tabuleiroPai) {
		this.tabuleiroPai = tabuleiroPai;
	}

	public ArrayList<Tabuleiro> getTabuleiroFilhos() {
		return tabuleiroFilhos;
	}

	public void setTabuleiroFilhos(ArrayList<Tabuleiro> tabuleiroFilhos) {
		this.tabuleiroFilhos = tabuleiroFilhos;
	}
	
	@Override
	public String toString() {
		
		String tabuleiro = "";
		
		for (int x = 0; x < pecas.length; x++) {
			for (int y = 0; y < pecas[x].length; y++) {
				tabuleiro+= this.pecas[x][y]+" ";
			}
			
			tabuleiro+="\n";
			
		}
		
		return tabuleiro;
	}
	
}
