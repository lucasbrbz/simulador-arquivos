package view;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Main;
import model.Node;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;

public class PrincipalView extends JFrame {

	private JPanel contentPane;
	private JTable table,tableBlocos;
	private DefaultTableModel model;
	private ArrayList<String> itensFrameTabela = new ArrayList<>();
	private JTextField textFieldTamanhoDisco,textFieldTamanhoBloco;

	public PrincipalView() {
		setTitle("Simulador de Sistema de Arquivos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 437, 454);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 431, 21);
		contentPane.add(menuBar);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(10, 26, 411, 89);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblDiscoRigidohd = new JLabel("Disco r\u00EDgido (HD)");
		lblDiscoRigidohd.setFont(new Font("Impact", Font.PLAIN, 22));
		lblDiscoRigidohd.setBounds(10, 0, 149, 37);
		panel.add(lblDiscoRigidohd);
		
		textFieldTamanhoDisco = new JTextField();
		textFieldTamanhoDisco.setEnabled(false);
		textFieldTamanhoDisco.setBounds(20, 51, 86, 20);
		panel.add(textFieldTamanhoDisco);
		textFieldTamanhoDisco.setColumns(10);
		
		JLabel lblTamanhoDoDisco = new JLabel("Tamanho do disco");
		lblTamanhoDoDisco.setBounds(10, 34, 109, 14);
		panel.add(lblTamanhoDoDisco);
		
		textFieldTamanhoBloco = new JTextField();
		textFieldTamanhoBloco.setEnabled(false);
		textFieldTamanhoBloco.setBounds(137, 51, 86, 20);
		panel.add(textFieldTamanhoBloco);
		textFieldTamanhoBloco.setColumns(10);
		
		JLabel lblTamanhoDoBloco = new JLabel("Tamanho do bloco");
		lblTamanhoDoBloco.setBounds(127, 34, 109, 14);
		panel.add(lblTamanhoDoBloco);

		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem mntmCriarArquivo = new JMenuItem("Criar arquivo");
		mntmCriarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int count = 0;
					for(int i=0;i<Main.getListaBlocosLivres().length;i++) {
						if(Main.getListaBlocosLivres()[i] == -1) count++;
					}
					if(count == Main.getDisco().getNumBlocos()) throw new Exception();
					String arquivo = JOptionPane.showInputDialog(contentPane,"Nome do arquivo:","Criar arquivo",JOptionPane.INFORMATION_MESSAGE) + ".txt";
					for(String file : Main.getTabela().keySet()) if(file.equals(arquivo)) throw new Exception();
					if(arquivo.equals("") || arquivo.equals(null)) throw new Exception();
					String[] opcoes = {"Leitura","Escrita","Leitura/Escrita"};
					String permissao = (String) JOptionPane.showInputDialog(contentPane,"Escolha o tipo de permissão:","Criar arquivo",JOptionPane.INFORMATION_MESSAGE,null, opcoes,opcoes[0]);
					if(permissao.equals(null)) throw new Exception();
					Node inode = new Node(0,permissao);
					Node.addNode();
					Main.getTabela().put(arquivo, inode);
					JOptionPane.showMessageDialog(null, "Arquivo criado com sucesso!", "Criar arquivo", JOptionPane.INFORMATION_MESSAGE);
					if(JOptionPane.showConfirmDialog(null,"Deseja editar o arquivo?","Criar arquivo",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
						Main.callEditarFrame(arquivo);
						if(Main.getEditarFrame().getFlagSalvar()) {
							String texto = Main.getEditarFrame().getTextPane().getText();
							int tamanhoTexto = Main.getEditarFrame().getTextPane().getText().length(); //se não tiver nada length = 0
							count = 0;
							for(int i=0,j=0,k=0;i<Main.getDisco().getTamanho();i++,k++) {
								if(count == tamanhoTexto) break;
								if(Main.getListaBlocosLivres()[j] != -1) {
									Main.getDisco().getVetorDisco()[i] = texto.charAt(k);
									count++;
									if((i+1)%Main.getDisco().getTamanhoBloco() == 0) {
										Main.getListaBlocosLivres()[j] = -1;
										if(Main.getListaBlocosLivres()[j+1] == -1) i+=Main.getDisco().getTamanhoBloco();
										j++;
									}
								}
								else j++;
							}
						}
					}
					else adicionaFrameTabela();
					for(int i=0;i<Main.getDisco().getVetorDisco().length;i++) {
						System.out.println(Main.getDisco().getVetorDisco()[i]);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Impossível criar o arquivo/arquivo já existente!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnArquivo.add(mntmCriarArquivo);
		
		JMenuItem mntmEditarArquivo = new JMenuItem("Editar arquivo");
		mntmEditarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0,j = 0,k = 0;
				String[] opcoes = carregaTabela();
				String arquivo = (String) JOptionPane.showInputDialog(contentPane,null,"Escolha o arquivo",JOptionPane.INFORMATION_MESSAGE,null, opcoes,opcoes[0]);
				Main.callEditarFrame(arquivo);
				if(Main.getEditarFrame().getFlagSalvar()) {
					String texto = Main.getEditarFrame().getTextPane().getText();
					int tamanhoTexto = Main.getEditarFrame().getTextPane().getText().length(); //se não tiver nada length = 0
					int count = 0;
					for(i=0,j=0,k=0;i<Main.getDisco().getTamanho();i++,k++) {
						if(count == tamanhoTexto) break;
						if(Main.getListaBlocosLivres()[j] != -1) {
							Main.getDisco().getVetorDisco()[i] = texto.charAt(k);
							count++;
							if((i+1)%Main.getDisco().getTamanhoBloco() == 0) {
								Main.getListaBlocosLivres()[j] = -1;
								if(Main.getListaBlocosLivres()[j+1] == -1) i+=Main.getDisco().getTamanhoBloco();
								j++;
							}
						}
						else j++;
					}
				}
			}
		});
		mnArquivo.add(mntmEditarArquivo);
		
		JMenuItem mntmRemoverArquivo = new JMenuItem("Remover arquivo");
		mntmRemoverArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] opcoes = carregaTabela();
					String arquivo = (String) JOptionPane.showInputDialog(contentPane,"Escolha o arquivo a ser removido:","Remover arquivo",JOptionPane.INFORMATION_MESSAGE,null, opcoes,opcoes[0]);
					if(arquivo.equals(null)) throw new Exception();
					Main.getTabela().remove(arquivo);
					JOptionPane.showMessageDialog(null, "Arquivo removido com sucesso!", "Remover arquivo", JOptionPane.INFORMATION_MESSAGE);
					atualizaFrameTabela();
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Não há arquivos no sistema!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnArquivo.add(mntmRemoverArquivo);
		
		JMenu mnDisco = new JMenu("Disco");
		menuBar.add(mnDisco);
		
		JMenuItem mntmFormatarDisco = new JMenuItem("Formatar disco");
		mntmFormatarDisco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int tDisco = Integer.parseInt(JOptionPane.showInputDialog(contentPane,"Tamanho do disco:","Formatar disco",JOptionPane.INFORMATION_MESSAGE));
					int tBloco = Integer.parseInt(JOptionPane.showInputDialog(contentPane,"Tamanho do bloco:","Formatar disco",JOptionPane.INFORMATION_MESSAGE));
					if(tDisco%tBloco == 0) {
						Main.getDisco().setTamanho(tDisco);
						Main.getDisco().setTamanhoBloco(tBloco);
						JOptionPane.showMessageDialog(null, "Disco formatado com sucesso!", "Formatar disco", JOptionPane.INFORMATION_MESSAGE);
						Main.atualizaTextField();
					}
					else
						JOptionPane.showMessageDialog(null, "Impossível formatar disco!", "Erro", JOptionPane.ERROR_MESSAGE);
				} catch(Exception e1) {
					
				}
				
			}
		});
		mnDisco.add(mntmFormatarDisco);
		
		JMenu mnSimulador = new JMenu("Simulador");
		menuBar.add(mnSimulador);
		
		JMenuItem mntmSalvarEstadoAtual = new JMenuItem("Salvar estado atual");
		mntmSalvarEstadoAtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./"));
			    if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			    	try(FileOutputStream f = new FileOutputStream(chooser.getSelectedFile()+".dat")) {
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
		});
		mnSimulador.add(mntmSalvarEstadoAtual);
		
		JMenuItem mntmCarregarDeUm = new JMenuItem("Carregar de um arquivo");
		mntmCarregarDeUm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./"));
				if(chooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					try(FileInputStream f = new FileInputStream(chooser.getSelectedFile())) {
			    		ObjectInputStream o = new ObjectInputStream(f);
			    	    Main.configuraDisco(o.readObject());
			    	    o.close();
			    	    JOptionPane.showMessageDialog(null, "Arquivo carregado com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
			    	} catch(Exception ex) {
			            ex.printStackTrace();
			        }
				}
			}
		});
		mnSimulador.add(mntmCarregarDeUm);
		
		table = new JTable(new DefaultTableModel(new Object[][] {},new String[] {"Arquivo","Tamanho","i-Node","Data criação","Última modificação"}));
		table.setEnabled(false);
		model = (DefaultTableModel) table.getModel();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 294, 411, 118);
		contentPane.add(scrollPane);
		
		tableBlocos = new JTable(new DefaultTableModel(new Object[][] {},new String[] {"Elemento","Endereço","Qtd de blocos livres"}));
		tableBlocos.setEnabled(false);
		model = (DefaultTableModel) tableBlocos.getModel();
		JScrollPane scrollPaneBlocos = new JScrollPane(tableBlocos);
		scrollPaneBlocos.setBounds(10, 165, 411, 118);
		contentPane.add(scrollPaneBlocos);
	}
	
	public String[] carregaTabela() {
		String[] opcoes = new String[Main.getTabela().size()];	
        int i = 0;
        for(String arquivo : Main.getTabela().keySet()){
        	opcoes[i] = arquivo;
        	i++;
        }
		return opcoes;
	}
	
	public void adicionaFrameTabela() {
		boolean b = true;
		for(String arquivo : Main.getTabela().keySet()){	
			for(String item : itensFrameTabela){
				if(item.equals(arquivo)) {
					b = false;
				}
			}		
			if(b) {
				model.addRow(new Object[]{
						arquivo,
						Integer.toString(Main.getTabela().get(arquivo).getTamanho()),
						Integer.toString(Main.getTabela().get(arquivo).getID()),
						Main.getTabela().get(arquivo).getDataCriacao(),
						Main.getTabela().get(arquivo).getDataModificacao()});
				itensFrameTabela.add(arquivo);
			}
		}
	}
	
	public void atualizaFrameTabela() {
		for(int i=0;i<itensFrameTabela.size();i++) {
			model.removeRow(i);
		}
		for(String item : itensFrameTabela) {
			model.addRow(new Object[]{
					item,
					Integer.toString(Main.getTabela().get(item).getTamanho()),
					Integer.toString(Main.getTabela().get(item).getID()),
					Main.getTabela().get(item).getDataCriacao(),
					Main.getTabela().get(item).getDataModificacao()});
		}
		table.revalidate();
	}
	
	public JTextField getTextFieldTamanho() {
		return textFieldTamanhoDisco;	
	}
	
	public JTextField getTextFieldTamanhoBloco() {
		return textFieldTamanhoBloco;
	}
}
