package controller;

import java.io.File;
import java.util.LinkedHashMap;

import model.HD;
import model.Node;
import view.PrincipalView;

public class Main {
	
	private static PrincipalView principalView = new PrincipalView();
	private static LinkedHashMap<File,Node> tabelaNodes = new LinkedHashMap<File,Node>();
	private static HD disco = new HD(100,20);

	public static void main(String[] args) {
		principalView.setVisible(true);
	}
	
	public static HD getDisco() {
		return disco;
	}
	
	public static LinkedHashMap<File,Node> getTabela() {
		return tabelaNodes;
	}
	
	public static void configuraDisco(Object discoArquivo) {
		 disco = (HD) discoArquivo;
	}

}
