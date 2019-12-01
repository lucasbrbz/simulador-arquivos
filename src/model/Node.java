package model;

import java.util.Calendar;

public class Node {
	private static int nINodes = 0;
	private int tamanho,id,ultimaPosicao = 0;
	private int[] referencias = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private String permissao,datacriacao,ultimamodificacao,arquivo;
	
	public Node(int tamanho,String permissao) {
		Calendar c = Calendar.getInstance();
		this.tamanho = tamanho;
		this.permissao = permissao;
		this.datacriacao = c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
		this.ultimamodificacao = this.datacriacao;
		this.id = nINodes;
	}
	
	public int[] getReferencias() {
		return referencias;
	}
	
	public void addReferencia(int posicao) {
		referencias[ultimaPosicao] = posicao;
		ultimaPosicao++;
	}
	
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	public int getID() {
		return id;
	}
	
	public static void addNode() {
		nINodes++;
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
	
	public void setDataModificacao() {
		Calendar c = Calendar.getInstance();
		this.ultimamodificacao = c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);;
	}
	
}
