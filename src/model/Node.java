package model;

import java.io.File;
import java.util.Calendar;

public class Node {
	private static int nINodes = 0;
	private int tamanho,id;
	private int[] referencias = new int[12];
	private String permissao,datacriacao,ultimamodificacao;
	private File arquivo;
	
	public Node(int tamanho,String permissao) {
		Calendar c = Calendar.getInstance();
		this.tamanho = tamanho;
		this.permissao = permissao;
		this.datacriacao = c.get(Calendar.DATE) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
		this.ultimamodificacao = this.datacriacao;
	}
	
	public void setReferencias(int[] referencias) {
		
	}
	
	public void setArquivo(File arquivo) {
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
	
}
