package model;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class HD implements Serializable {
	
	private char[] disco;
	private int tamanho,tBloco,nBlocos;
	private LinkedHashMap<String,Node> tabelaNodes;

	public HD(int tamanho,int tBloco) {
		this.disco = new char[tamanho];
		this.tamanho = tamanho;
		this.tBloco = tBloco;
		this.nBlocos = (tamanho/tBloco);
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
		this.tabelaNodes = tabela;
	}
	
	public LinkedHashMap<String,Node> getTabelaNodes() {
		return tabelaNodes;
	}
	
}
