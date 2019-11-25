package controller;

import java.io.File;
import java.util.Hashtable;

import model.HD;
import model.Node;
import view.PrincipalView;

public class Main {
	
	private static PrincipalView principalView = new PrincipalView();
	private static Hashtable<File,Node> tabelaNodes = new Hashtable<File,Node>();
	private static HD disco = new HD(100,20);

	public static void main(String[] args) {
		principalView.setVisible(true);
	}
	
	public static HD getDisco() {
		return disco;
	}
	
	public static Hashtable<File,Node> getTabela() {
		return tabelaNodes;
	}
	
//	public void configuraDisco(HD discoArquivo) {
//		 disco = discoArquivo;
//	}

}
