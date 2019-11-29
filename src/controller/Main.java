package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.HD;
import model.Node;
import view.EditarView;
import view.PrincipalView;

public class Main {
	
	private static PrincipalView principalFrame = new PrincipalView();
	private static EditarView editarFrame;
	private static LinkedHashMap<String,Node> tabelaNodes = new LinkedHashMap<String,Node>();
	private static int[] listaBlocos,listaBlocosLivres;
	private static HD disco;

	public static void main(String[] args) {
		principalFrame.setVisible(true);
		if(!(new File("./disco.dat")).exists()) {
			int tamanho = Integer.parseInt(JOptionPane.showInputDialog(null,"Tamanho do disco:","Criar disco rígido",JOptionPane.INFORMATION_MESSAGE));
			int tBloco = Integer.parseInt(JOptionPane.showInputDialog(null,"Tamanho dos blocos:","Criar disco rígido",JOptionPane.INFORMATION_MESSAGE));
			disco = new HD(tamanho,tBloco);
			listaBlocos = new int[disco.getNumBlocos()];
			listaBlocosLivres = new int[disco.getNumBlocos()];
			for(int i=1;i<listaBlocos.length;i++) {
				listaBlocos[i] = i*disco.getTamanhoBloco();
			}
			JFileChooser chooser = new JFileChooser();
			File file = new File("./disco.dat");
			chooser.setCurrentDirectory(file);
		    if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		    	chooser.setSelectedFile(file);
		    	try(FileOutputStream f = new FileOutputStream(chooser.getSelectedFile())) {
		    		ObjectOutputStream o = new ObjectOutputStream(f);
		    	    o.writeObject(Main.getDisco());
		    	    o.flush();
		    	    o.close();
		    	    JOptionPane.showMessageDialog(null, "Salvo com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
		    	} catch(Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		}
		else {
			try(FileInputStream f = new FileInputStream("./disco.dat")) {
	    		ObjectInputStream o = new ObjectInputStream(f);
	    	    Main.configuraDisco(o.readObject());
	    	    o.close();
	    	    listaBlocosLivres = new int[disco.getNumBlocos()];
				for(int i=1;i<listaBlocosLivres.length;i++) {
					listaBlocosLivres[i] = i*disco.getTamanhoBloco();
				}
	    	    JOptionPane.showMessageDialog(null, "Disco carregado com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
	    	} catch(Exception ex) {
	            ex.printStackTrace();
	        }
		}
		atualizaTextField();
	}
	
	public static void callEditarFrame() {
		editarFrame = new EditarView();
		editarFrame.setVisible(true);
	}
	
	public static void callEditarFrame(String nomearq) {
		editarFrame = new EditarView(nomearq);
		editarFrame.setVisible(true);
	}
	
	public static EditarView getEditarFrame() {
		return editarFrame;
	}
	
	public static HD getDisco() {
		return disco;
	}
	
	public static LinkedHashMap<String,Node> getTabela() {
		return tabelaNodes;
	}
	
	public static int[] getListaBlocos() {
		return listaBlocos;
	}
	
	public static int[] getListaBlocosLivres() {
		return listaBlocosLivres;
	}
	
	public static void configuraDisco(Object discoArquivo) {
		 disco = (HD) discoArquivo;
	}
	
	public static void atualizaTextField() {
		principalFrame.getTextFieldTamanho().setText(Integer.toString(disco.getTamanho()));
		principalFrame.getTextFieldTamanhoBloco().setText(Integer.toString(disco.getTamanhoBloco()));
	}
}
