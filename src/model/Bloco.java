package model;

public class Bloco {
	private int posicao;
	private boolean ocupado;
	
	public Bloco(int posicao) {
		this.posicao = posicao;
		this.ocupado = false;
	}
	
	public int getPosicao() {
		return posicao;
	}

	public boolean estaOcupado() {
		return (ocupado) ? true : false;
	}
	
	public void ocupar() {
		ocupado = true;
	}
}
