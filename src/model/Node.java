package model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Classe que implementa um i-node e seus atributos
 * @author Lucas
 * @author Pedro
 *
 */
public class Node implements Serializable {
	private int tamanho,ultimaPosicao = 0;
	private int[] referencias = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private String permissao,datacriacao,ultimamodificacao;
	
	/**
	 * Construtor da classe, que define o tamanho e a permiss�o do arquivo atrelado
	 * bem como sua data de cria��o e modifica��o, que no momento da cria��o s�o iguais
	 * @param tamanho Tamanho do arquivo
	 * @param permissao Permiss�o do arquivo
	 */
	public Node(int tamanho,String permissao) {
		Calendar c = Calendar.getInstance();
		this.tamanho = tamanho;
		this.permissao = permissao;
		this.datacriacao = c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
		this.ultimamodificacao = this.datacriacao;
	}
	
	public int[] getReferencias() {
		return referencias;
	}
	
	/**
	 * M�todo que adiciona uma posi��o ao vetor de refer�ncias para localiza��o do arquivo no HD
	 * @param posicao Posi��o a ser adicionada
	 */
	public void addReferencia(int posicao) {
		referencias[ultimaPosicao] = posicao;
		ultimaPosicao++;
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	public String getPermissao() {
		return permissao;
	}
	
	public String getDataCriacao() {
		return datacriacao;
	}
	
	public String getDataModificacao() {
		return ultimamodificacao;
	}
	
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	
	/**
	 * M�todo respons�vel por definir a data da �ltima modifica��o do arquivo quando houver edi��o
	 */
	public void setDataModificacao() {
		Calendar c = Calendar.getInstance();
		this.ultimamodificacao = c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
	}
	
}
