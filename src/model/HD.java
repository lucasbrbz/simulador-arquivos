package model;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Classe que implementa o disco r�gido e seus atributos e permite manipul�-los
 * @author Lucas
 * @author Pedro
 *
 */
public class HD implements Serializable {
	
	private char[] disco;
	private int tamanho,tBloco,nBlocos;
	private LinkedHashMap<String,Node> tabelaNodes = new LinkedHashMap<String,Node>();

	/**
	 * Construtor da classe, que cria o vetor disco e o preenche com tra�os para indicar vazio
	 * @param tamanho Tamanho do vetor disco r�gido
	 * @param tBloco Tamanho do bloco pelo qual ser� dividido o disco r�gido
	 */
	public HD(int tamanho,int tBloco) {
		this.disco = new char[tamanho];
		for(int i=0;i<tamanho;i++) {
			disco[i] = '-';
		}
		this.tamanho = tamanho;
		this.tBloco = tBloco;
		this.nBlocos = (tamanho/tBloco);
	}
	
	public LinkedHashMap<String,Node> getTabelaNodes() {
		return tabelaNodes;
	}
	
	/**
	 * M�todo que cria um novo vetor disco para quando a formata��o � executada
	 * @param tamanho Tamanho do novo vetor
	 */
	public void novoVetorDisco(int tamanho) {
		this.disco = new char[tamanho];
	}
	
	public char[] getVetorDisco() {
		return disco;
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	
	public int getTamanhoBloco() {
		return tBloco;
	}
	
	public void setTamanhoBloco(int tBloco) {
		this.tBloco = tBloco;
	}
	
	public int getNumBlocos() {
		return nBlocos;
	}
	
	public void setTabelaNodes(LinkedHashMap<String,Node> tabela) {
		tabelaNodes = tabela;
	}
	
}
