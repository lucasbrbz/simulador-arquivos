package view;

import java.awt.EventQueue;

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
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;

public class PrincipalView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private ArrayList<File> itensFrameTabela = new ArrayList<>();
	private JTextField textFieldTamanhoDisco;
	private JTextField textFieldTamanhoBloco;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalView frame = new PrincipalView();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PrincipalView() {
		setTitle("Simulador de Sistema de Arquivos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 437, 430);
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
		panel.setBounds(10, 26, 411, 172);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblDiscoRigidohd = new JLabel("Disco r\u00EDgido (HD)");
		lblDiscoRigidohd.setFont(new Font("Impact", Font.PLAIN, 22));
		lblDiscoRigidohd.setBounds(10, 0, 149, 37);
		panel.add(lblDiscoRigidohd);
		
		textFieldTamanhoDisco = new JTextField();
		textFieldTamanhoDisco.setEnabled(false);
		textFieldTamanhoDisco.setBounds(20, 62, 86, 20);
//		textFieldTamanhoDisco.setText(Integer.toString(Main.getDisco().getTamanho()));
		panel.add(textFieldTamanhoDisco);
		textFieldTamanhoDisco.setColumns(10);
		
		JLabel lblTamanhoDoDisco = new JLabel("Tamanho do disco");
		lblTamanhoDoDisco.setBounds(10, 45, 109, 14);
		panel.add(lblTamanhoDoDisco);
		
		textFieldTamanhoBloco = new JTextField();
		textFieldTamanhoBloco.setEnabled(false);
		textFieldTamanhoBloco.setBounds(20, 110, 86, 20);
//		textFieldTamanhoBloco.setText(Integer.toString(Main.getDisco().getTamanhoBloco()));
		panel.add(textFieldTamanhoBloco);
		textFieldTamanhoBloco.setColumns(10);
		
		JLabel lblTamanhoDoBloco = new JLabel("Tamanho do bloco");
		lblTamanhoDoBloco.setBounds(10, 93, 109, 14);
		panel.add(lblTamanhoDoBloco);

		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem mntmCriarArquivo = new JMenuItem("Criar arquivo");
		mntmCriarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String nome = JOptionPane.showInputDialog(contentPane,"Nome do arquivo:","Criar arquivo",JOptionPane.INFORMATION_MESSAGE);
					if((new File("./" + nome + ".txt")).exists()) {
						throw new Exception();
					}
					if(nome.equals("") || nome.equals(null)) throw new Exception();
					String[] opcoes = {"Leitura","Escrita","Leitura/Escrita"};
					String permissao = (String) JOptionPane.showInputDialog(contentPane,"Escolha o tipo de permiss�o:","Criar arquivo",JOptionPane.INFORMATION_MESSAGE,null, opcoes,opcoes[0]);
					if(permissao.equals(null)) throw new Exception();
					Node inode = new Node(0,permissao);
					Node.addNode();
					File arquivo = new File("./" + nome + ".txt");
					switch(permissao) {
						case "Leitura":
							arquivo.setReadOnly();
							break;
						case "Escrita":
							arquivo.setWritable(true);
							break;
						case "Leitura/Escrita":
							arquivo.setReadable(true);
							arquivo.setWritable(true);
							break;
					}
					FileWriter arq = new FileWriter(arquivo);
					arq.close();
					Main.getTabela().put(arquivo, inode);
					JOptionPane.showMessageDialog(null, "Arquivo criado com sucesso!", "Criar arquivo", JOptionPane.INFORMATION_MESSAGE);
					for(File f : Main.getTabela().keySet()) {
						System.out.println(f.getName());
					}
					adicionaFrameTabela();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Imposs�vel criar o arquivo/arquivo j� existente!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnArquivo.add(mntmCriarArquivo);
		
		JMenuItem mntmEditarArquivo = new JMenuItem("Editar arquivo");
		mntmEditarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
					File arq = new File(arquivo);
					Main.getTabela().remove(arq);
					arq.delete();
					JOptionPane.showMessageDialog(null, "Arquivo removido com sucesso!", "Remover arquivo", JOptionPane.INFORMATION_MESSAGE);
					for(File f : Main.getTabela().keySet()) {
						System.out.println(f.getName());
					}
					atualizaFrameTabela();
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
						Main.getDisco().setTamanho(tDisco);
						Main.getDisco().setTamanhoBloco(tBloco);
						JOptionPane.showMessageDialog(null, "Disco formatado com sucesso!", "Formatar disco", JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "Imposs�vel formatar disco!", "Erro", JOptionPane.ERROR_MESSAGE);
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
					try(FileInputStream f = new FileInputStream(chooser.getSelectedFile()+".dat")) {
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
		model = (DefaultTableModel) table.getModel();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 209, 411, 181);
		contentPane.add(scrollPane);
	}
	
	public String[] carregaTabela() {
		String[] opcoes = new String[itensFrameTabela.size()];	
        int i = 0;
        for(File arquivo : Main.getTabela().keySet()){
        	opcoes[i] = arquivo.getName();
        	i++;
        }
		return opcoes;
	}
	
	public void adicionaFrameTabela() {
		boolean b = true;
		for(File arquivo : Main.getTabela().keySet()){	
			for(File item : itensFrameTabela){
				if(item.getName().equals(arquivo.getName())) {
					b = false;
				}
			}		
			if(b) {
				model.addRow(new Object[]{
						arquivo.getName(),
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
		for(File item : itensFrameTabela) {
			model.addRow(new Object[]{
					item.getName(),
					Integer.toString(Main.getTabela().get(item).getTamanho()),
					Integer.toString(Main.getTabela().get(item).getID()),
					Main.getTabela().get(item).getDataCriacao(),
					Main.getTabela().get(item).getDataModificacao()});
		}
		table.revalidate();
	}
}
