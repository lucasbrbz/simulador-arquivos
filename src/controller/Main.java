package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Bloco;
import model.HD;
import view.PrincipalView;

/**
 * Classe principal que gerencia/controla os frames e funcionalidades gerais
 * @author Lucas
 * @author Pedro
 *
 */
public class Main {
	
	private static PrincipalView principalFrame = new PrincipalView();
	private static ArrayList<Bloco> listaBlocos = new ArrayList<Bloco>();
	private static HD disco;

	/**
	 * Realiza a chamada do frame principal e cria/carrega o arquivo disco rígido
	 * @param args
	 */
	public static void main(String[] args) {
		principalFrame.setVisible(true);
		if(!(new File("./disco.dat")).exists()) {
			int tamanho = 0,tBloco = 0;
			try {
				tamanho = Integer.parseInt(JOptionPane.showInputDialog(null,"Tamanho do disco:","Criar disco rígido",JOptionPane.INFORMATION_MESSAGE));
				tBloco = Integer.parseInt(JOptionPane.showInputDialog(null,"Tamanho dos blocos:","Criar disco rígido",JOptionPane.INFORMATION_MESSAGE));
				disco = new HD(tamanho,tBloco);
				preencheListaBlocos();
				montaTabelaBlocos(false);
				JFileChooser chooser = new JFileChooser("./");
				chooser.setSelectedFile(new File("./disco.dat"));
			    if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			    	FileOutputStream f = new FileOutputStream(chooser.getSelectedFile());
			    	ObjectOutputStream o = new ObjectOutputStream(f);
			    	disco.setTabelaNodes(disco.getTabelaNodes());
			    	o.writeObject(disco);
			    	o.flush();
			    	o.close();
			    	montaJList(false);
					atualizaTextField();
			    	JOptionPane.showMessageDialog(null,"Disco criado com sucesso!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
			    } else throw new Exception();
			} catch(Exception ex) {
	    		JOptionPane.showMessageDialog(null,"Impossível criar o disco!","Erro",JOptionPane.ERROR_MESSAGE);
	        }
		}
		else {
			try(FileInputStream f = new FileInputStream("./disco.dat")) {
	    		ObjectInputStream o = new ObjectInputStream(f);
	    	    Main.configuraDisco(o.readObject());
	    	    o.close();
	    	    preencheListaBlocos();
	    	    montaTabelaBlocos(false);
	    	    montaJList(false);
				disco.getTabelaNodes().forEach((k,v) -> {
					principalFrame.adicionaFrameTabela(k);
					int[] referencias = v.getReferencias();
					int i = 0;
					while(referencias[i] != -1) {
						int j = referencias[i];
						for(Bloco b : listaBlocos) {
							if(b.getPosicao() == referencias[i]) {
								listaBlocos.remove(b);
								break;
							}
						}
						do {
							if(disco.getVetorDisco()[j] == '-') {
								principalFrame.getListModel().setElementAt('-',j);
							} else {
								principalFrame.getListModel().setElementAt(disco.getVetorDisco()[j],j);
							}
							j++;
						} while(j%disco.getTamanhoBloco() != 0);
						i++;
					}
				});
				atualizaTextField();
	    	    JOptionPane.showMessageDialog(null, "Disco carregado com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
	    	} catch(Exception ex) {
	    		JOptionPane.showMessageDialog(null, "Impossível carregar o disco!", "Erro", JOptionPane.ERROR_MESSAGE);
	        }
		}
		
	}
	
	public static HD getDisco() {
		return disco;
	}
	
	public static ArrayList<Bloco> getListaBlocos() {
		return listaBlocos;
	}
	
	/**
	 * Método responsável por passar um objeto HD lido do arquivo para um objeto HD de tempo de execução
	 * @param discoArquivo Objeto desserializado lido do arquivo que necessitará de um cast
	 */
	public static void configuraDisco(Object discoArquivo) {
		 disco = (HD) discoArquivo;
	}
	
	/**
	 * Método responsável por atualizar o texto exibido nos campos tamanho do HD e tamanho do bloco,
	 * que será chamado apenas quando houver formatação de disco ou carregamento a partir de arquivo
	 */
	public static void atualizaTextField() {
		principalFrame.getTextFieldTamanho().setText(Integer.toString(disco.getTamanho()));
		principalFrame.getTextFieldTamanhoBloco().setText(Integer.toString(disco.getTamanhoBloco()));
	}
	
	/**
	 * Método responsável por popular a lista de blocos, configurando sua posição de maneira dinâmica
	 */
	public static void preencheListaBlocos() {
		for(int i=0;i<disco.getNumBlocos();i++) {
			Bloco b = new Bloco(i*disco.getTamanhoBloco());
			listaBlocos.add(b);
		}
	}
	
	/**
	 * Montador da tabela de blocos livres
	 * @param flag Variável para saber se deverá limpar e repopular a tabela
	 */
	public static void montaTabelaBlocos(boolean flag) {
		if(flag) {
			int tam = principalFrame.getTabelaBlocos().getRowCount();
			for(int i=0;i<tam;i++) {
				principalFrame.getTabelaBlocosModel().removeRow(0);
			}
		}
		principalFrame.getTabelaBlocos().revalidate();
		for(int i=0;i<listaBlocos.size();i++) {
			int count = 0;
			for(int j=i+1;j<listaBlocos.size();j++) {
				count++;
			}
			principalFrame.getTabelaBlocosModel().addRow(new Object[] {
					i,
					listaBlocos.get(i).getPosicao(),
					count
			});
		}
	}
	
	/**
	 * Montador da lista de visualização do HD
	 * @param flag Variável para saber se deverá limpar e repopular a lista
	 */
	public static void montaJList(boolean flag) {
		principalFrame.getJList().setSize(disco.getTamanho(),1);
		if(flag) principalFrame.getListModel().removeAllElements();;
		for(int i=0;i<disco.getTamanho();i++) {
			principalFrame.getListModel().add(i,'-');
		}
	}
}
