package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.Bloco;
import model.HD;
import model.Node;
import view.EditarView;
import view.PrincipalView;

public class Main {
	
	private static PrincipalView principalFrame = new PrincipalView();
	private static EditarView editarFrame;
	private static LinkedHashMap<String,Node> tabelaNodes = new LinkedHashMap<String,Node>();
	private static ArrayList<Bloco> listaBlocos = new ArrayList<Bloco>();
	private static HD disco;

	public static void main(String[] args) {
		principalFrame.setVisible(true);
		if(!(new File("./disco.dat")).exists()) {
			int tamanho = Integer.parseInt(JOptionPane.showInputDialog(null,"Tamanho do disco:","Criar disco rígido",JOptionPane.INFORMATION_MESSAGE));
			int tBloco = Integer.parseInt(JOptionPane.showInputDialog(null,"Tamanho dos blocos:","Criar disco rígido",JOptionPane.INFORMATION_MESSAGE));
			disco = new HD(tamanho,tBloco);
			preencheListaBlocos();
			JFileChooser chooser = new JFileChooser();
			File file = new File("./disco.dat");
			chooser.setCurrentDirectory(new File("./"));
		    if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		    	chooser.setSelectedFile(file);
		    	try(FileOutputStream f = new FileOutputStream(chooser.getSelectedFile())) {
		    		ObjectOutputStream o = new ObjectOutputStream(f);
		    	    o.writeObject(Main.getDisco());
		    	    o.flush();
		    	    o.close();
		    	    JOptionPane.showMessageDialog(null,"Disco criado com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
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
	    	    preencheListaBlocos();
	    	    JOptionPane.showMessageDialog(null, "Disco carregado com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
	    	} catch(Exception ex) {
	            ex.printStackTrace();
	        }
		}
		montaJList();
		atualizaTextField();
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
	
	public static ArrayList<Bloco> getListaBlocos() {
		return listaBlocos;
	}
	
	public static void configuraDisco(Object discoArquivo) {
		 disco = (HD) discoArquivo;
	}
	
	public static void atualizaTextField() {
		principalFrame.getTextFieldTamanho().setText(Integer.toString(disco.getTamanho()));
		principalFrame.getTextFieldTamanhoBloco().setText(Integer.toString(disco.getTamanhoBloco()));
	}
	
	private static void preencheListaBlocos() {
		listaBlocos = new ArrayList<Bloco>();
		for(int i=0;i<disco.getNumBlocos();i++) {
			Bloco b = new Bloco(i*Main.getDisco().getTamanhoBloco());
			listaBlocos.add(b);
		}
	}
	
	public static void montaJList() {
		principalFrame.getJList().setSize(disco.getTamanho(),1);
		for(int i=0;i<disco.getTamanho();i++) {
			principalFrame.getListModel().add(i,' ');
		}
	}
	
	public static void atualizaJList() {
		
	}
}
