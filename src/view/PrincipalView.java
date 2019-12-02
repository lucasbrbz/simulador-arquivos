package view;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Main;
import model.Bloco;
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
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;

import javax.swing.JTextField;
import java.awt.Color;

public class PrincipalView extends JFrame {

	private JPanel contentPane;
	private JList<Character> list;
	private DefaultListModel<Character> modelList = new DefaultListModel<Character>();
	private JTable table,tableBlocos;
	private DefaultTableModel model,modelTableBlocos;
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
		panel.setBounds(10, 26, 411, 81);
		panel.setBackground(Color.GRAY);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblDiscoRigidohd = new JLabel("Disco r\u00EDgido (HD)");
		lblDiscoRigidohd.setFont(new Font("Impact", Font.PLAIN, 22));
		lblDiscoRigidohd.setBounds(10, 0, 149, 37);
		panel.add(lblDiscoRigidohd);
		
		textFieldTamanhoDisco = new JTextField();
		textFieldTamanhoDisco.setDisabledTextColor(new Color(0, 0, 0));
		textFieldTamanhoDisco.setEnabled(false);
		textFieldTamanhoDisco.setHorizontalAlignment(JTextField.CENTER);
		textFieldTamanhoDisco.setBounds(20, 51, 86, 20);
		panel.add(textFieldTamanhoDisco);
		textFieldTamanhoDisco.setColumns(10);
		
		JLabel lblTamanhoDoDisco = new JLabel("Tamanho do disco");
		lblTamanhoDoDisco.setBounds(10, 34, 109, 14);
		panel.add(lblTamanhoDoDisco);
		
		textFieldTamanhoBloco = new JTextField();
		textFieldTamanhoBloco.setDisabledTextColor(Color.BLACK);
		textFieldTamanhoBloco.setEnabled(false);
		textFieldTamanhoBloco.setHorizontalAlignment(JTextField.CENTER);
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
					for(Bloco b : Main.getListaBlocos()) {
						if(b.estaOcupado()) count++;
					}
					if(count == Main.getDisco().getNumBlocos()) throw new Exception();
					String arquivo = JOptionPane.showInputDialog(contentPane,"Nome do arquivo:","Criar arquivo",JOptionPane.INFORMATION_MESSAGE) + ".txt";
					for(String file : Main.getTabela().keySet()) if(file.equals(arquivo)) throw new Exception();
					if(arquivo.equals("") || arquivo.equals(null)) throw new Exception();
					String[] opcoes = {"Leitura","Escrita","Leitura/Escrita"};
					String permissao = null;
					switch((String) JOptionPane.showInputDialog(contentPane,"Escolha o tipo de permiss�o:","Criar arquivo",JOptionPane.INFORMATION_MESSAGE,null, opcoes,opcoes[0])) {
						case "Leitura":
							permissao = "r";
							break;
						case "Escrita":
							permissao = "w";
							break;
						case "Leitura/Escrita":
							permissao = "rw";
							break;
					}
					if(permissao.equals(null)) throw new Exception();
					int editar = JOptionPane.showConfirmDialog(null,"Deseja editar o arquivo?","Criar arquivo",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(editar == 0) {
						String texto = JOptionPane.showInputDialog(contentPane,null,"Editar arquivo - " + arquivo,JOptionPane.INFORMATION_MESSAGE);
						int posicaoTexto = 0;
						boolean terminou = false;
						Node inode = new Node(texto.length(),permissao);
						for(Bloco b : Main.getListaBlocos()) {
							if(b.estaOcupado() == false) {
								b.ocupar();
								inode.addReferencia(b.getPosicao());
								for(int i=posicaoTexto,j=b.getPosicao();i<texto.length();i++,j++) {
									Main.getDisco().getVetorDisco()[j] = texto.charAt(i);
									modelList.setElementAt(texto.charAt(i),j);
									if(i == texto.length() - 1) terminou = true;
									if((i+1)%Main.getDisco().getTamanhoBloco() == 0) {
										posicaoTexto = i+1;
										break;
									}
								}
								if(terminou) break;
							}
						}
						Node.addNode();
						Main.getTabela().put(arquivo, inode);
						JOptionPane.showMessageDialog(contentPane, "Arquivo criado com sucesso!", "Criar arquivo", JOptionPane.INFORMATION_MESSAGE);
						adicionaFrameTabela(arquivo);
					}
					else if(editar == 1) {
						Node inode = new Node(0,permissao);
						Node.addNode();
						Main.getTabela().put(arquivo, inode);
						JOptionPane.showMessageDialog(contentPane, "Arquivo criado com sucesso!", "Criar arquivo", JOptionPane.INFORMATION_MESSAGE);
						adicionaFrameTabela(arquivo);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Imposs�vel criar o arquivo/arquivo j� existente!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnArquivo.add(mntmCriarArquivo);
		
		JMenuItem mntmEditarArquivo = new JMenuItem("Editar arquivo");
		mntmEditarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Object[] dados = null;
					String[] opcoes = carregaTabela();
					String arquivo = (String) JOptionPane.showInputDialog(contentPane,null,"Escolha o arquivo",JOptionPane.INFORMATION_MESSAGE,null, opcoes,opcoes[0]);
					String texto = JOptionPane.showInputDialog(contentPane,null,"Editar arquivo - " + arquivo,JOptionPane.INFORMATION_MESSAGE);
					int posicaoTexto = 0;
					boolean terminou = false;
					for(Bloco b : Main.getListaBlocos()) {
						if(b.estaOcupado() == false) {
							b.ocupar();
							for(int i=posicaoTexto,j=b.getPosicao();i<texto.length();i++,j++) {
								Main.getDisco().getVetorDisco()[j] = texto.charAt(i);
								modelList.setElementAt(texto.charAt(i),j);
								if(i == texto.length() - 1) terminou = true;
								if((i+1)%Main.getDisco().getTamanhoBloco() == 0) {
									posicaoTexto = i+1;
									break;
								}
							}
							if(terminou) break;
						}
					}
					Main.getTabela().get(arquivo).setTamanho(texto.length());
					for(int i=0;i<table.getRowCount();i++) {
						if(model.getValueAt(i,0).equals(arquivo)) {
							model.setValueAt(arquivo, i, 0);
							model.setValueAt(Main.getTabela().get(arquivo).getTamanho(), i, 1);
							model.setValueAt(Main.getTabela().get(arquivo).getID(), i, 2);
							model.setValueAt(Main.getTabela().get(arquivo).getDataCriacao(), i, 3);
							model.setValueAt(Main.getTabela().get(arquivo).getDataModificacao(), i, 4);
							break;
						}
					}
					table.revalidate();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "N�o h� arquivos no sistema!", "Erro", JOptionPane.ERROR_MESSAGE);
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
					for(int i=0;i<table.getRowCount();i++) {
						if(model.getValueAt(i,0).equals(arquivo)) {
							model.removeRow(i);	
							break;
						}
					}
					table.revalidate();
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "N�o h� arquivos no sistema!", "Erro", JOptionPane.ERROR_MESSAGE);
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
						for(String key : Main.getTabela().keySet()) Main.getTabela().remove(key);
						for(int i=0;i<Main.getDisco().getTamanho();i++) {
							System.out.println(1);
							modelList.setElementAt('-',i);;
							model.removeRow(i);
							Main.getDisco().getVetorDisco()[i] = ' ';
						}
						Main.getDisco().setTamanho(tDisco);
						Main.getDisco().setTamanhoBloco(tBloco);
						JOptionPane.showMessageDialog(null, "Disco formatado com sucesso!", "Formatar disco", JOptionPane.INFORMATION_MESSAGE);
						Main.atualizaTextField();
						Main.preencheListaBlocos();
						table.revalidate();
					}
					else JOptionPane.showMessageDialog(null, "Imposs�vel formatar disco!", "Erro", JOptionPane.ERROR_MESSAGE);
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
		
		table = new JTable(new DefaultTableModel(new Object[][] {},new String[] {"Arquivo","Tamanho","i-Node","Data cria��o","�ltima modifica��o"}));
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoscrolls(false);
		table.setEnabled(false);
		model = (DefaultTableModel) table.getModel();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 294, 411, 118);
		contentPane.add(scrollPane);
		
		tableBlocos = new JTable(new DefaultTableModel(new Object[][] {},new String[] {"Elemento","Endere�o","Qtd de blocos livres"}));
		tableBlocos.getTableHeader().setResizingAllowed(false);
		tableBlocos.getTableHeader().setReorderingAllowed(false);
		tableBlocos.setRowSelectionAllowed(false);
		tableBlocos.setAutoscrolls(false);
		tableBlocos.setEnabled(false);
		modelTableBlocos = (DefaultTableModel) tableBlocos.getModel();
		JScrollPane scrollPaneBlocos = new JScrollPane(tableBlocos);
		scrollPaneBlocos.setBounds(10, 165, 411, 118);
		contentPane.add(scrollPaneBlocos);
		
		list = new JList<Character>();
		list.setForeground(Color.BLACK);
		list.setVisibleRowCount(-1);
		list.setAutoscrolls(false);
		list.setEnabled(false);
		list.setModel(modelList);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		JScrollPane scrollPaneList = new JScrollPane(list);
		scrollPaneList.setForeground(Color.BLACK);
		scrollPaneList.setBounds(10, 114, 411, 40);
		contentPane.add(scrollPaneList);
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
	
	public void adicionaFrameTabela(String arquivo) {
		model.addRow(new Object[]{
			arquivo,
			Main.getTabela().get(arquivo).getTamanho(),
			Main.getTabela().get(arquivo).getID(),
			Main.getTabela().get(arquivo).getDataCriacao(),
			Main.getTabela().get(arquivo).getDataModificacao()
			});
	}
	
	public JTextField getTextFieldTamanho() {
		return textFieldTamanhoDisco;	
	}
	
	public JTextField getTextFieldTamanhoBloco() {
		return textFieldTamanhoBloco;
	}
	
	public JList<Character> getJList(){
		return list;
	}
	
	public DefaultListModel<Character> getListModel(){
		return modelList;
	}
}
