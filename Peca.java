package jogoDoOito;

public class Peca {
	
	private int numeroPeca;

	public int getNumeroPeca() {
		return numeroPeca;
	}

	public void setNumeroPeca(int numeroPeca) {
		this.numeroPeca = numeroPeca;
	}
	
	@Override
	public String toString() {
		return this.numeroPeca+"";
	}
	
	public Peca clone() {
		Peca novaPeca = new Peca();
		novaPeca.setNumeroPeca(this.numeroPeca);
		return novaPeca;
	}

}
