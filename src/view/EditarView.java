package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditarView extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;
	private boolean salvar = false;

	public EditarView() {
		setVisible(false);
		setTitle("Editar arquivo");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 302, 209);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(10, 11, 276, 128);
		contentPane.add(textPane);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
				dispose();
			}
		});
		btnCancelar.setBounds(197, 150, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar = true;
				textPane.setText("");
				dispose();
			}
		});
		btnSalvar.setBounds(98, 150, 89, 23);
		contentPane.add(btnSalvar);
		
	}
	
	public EditarView(String nomearq) {
		setVisible(false);
		setTitle("Editar arquivo - " + nomearq);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 302, 209);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(10, 11, 276, 128);
		contentPane.add(textPane);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
				dispose();
			}
		});
		btnCancelar.setBounds(197, 150, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar = true;
				textPane.setText("");
				dispose();
			}
		});
		btnSalvar.setBounds(98, 150, 89, 23);
		contentPane.add(btnSalvar);
		
	}
	
	public JTextPane getTextPane() {
		return textPane;
	}
	
	public boolean getFlagSalvar() {
		return salvar;
	}
	
}
