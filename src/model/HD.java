package model;

import java.io.Serializable;

public class HD implements Serializable {
	private char[] disco;
	private int tamanho,tBloco,nBlocos;
	
	public HD(int tamanho,int tBloco) {
		this.disco = new char[tamanho];
		this.tamanho = tamanho;
		this.tBloco = tBloco;
		this.nBlocos = (tamanho/tBloco);
	}
	
	public char[] getDisco() {
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
	
}
