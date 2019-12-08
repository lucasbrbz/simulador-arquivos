package model;

/**
 * Classe que implementa um bloco e sua posi��o apenas para serem utilizados na lista de blocos
 * @author Lucas
 * @author Pedro
 *
 */
public class Bloco {
	private int posicao;
	
	/**
	 * Construtor da classe, define a posi��o somente no momento de cria��o
	 * @param posicao
	 */
	public Bloco(int posicao) {
		this.posicao = posicao;
	}
	
	public int getPosicao() {
		return posicao;
	}
}
