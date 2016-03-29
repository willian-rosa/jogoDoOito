package jogoDoOito;

import org.eclipse.swt.widgets.Button;

public class Peca {
	
	private Button botao;
	private int numeroPeca;

	public int getNumeroPeca() {
		return numeroPeca;
	}

	public void setNumeroPeca(int numeroPeca) {
		this.numeroPeca = numeroPeca;
	}

	public Button getBotao() {
		return botao;
	}

	public void setBotao(Button botao) {
		this.botao = botao;
	}
	
	@Override
	public String toString() {
		return this.numeroPeca+"";
	}
	
	public Peca clone() {
		Peca novaPeca = new Peca();
		novaPeca.setBotao(this.botao);
		novaPeca.setNumeroPeca(this.numeroPeca);
		return novaPeca;
	}

}
