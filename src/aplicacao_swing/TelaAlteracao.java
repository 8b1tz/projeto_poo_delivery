package aplicacao_swing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import fachada.Fachada;

public class TelaAlteracao {

	private JFrame frmAlterao;
	private JTextField idpedido;
	private JTextField idproduto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaAlteracao window = new TelaAlteracao();
					window.frmAlterao.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaAlteracao() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAlterao = new JFrame();
		frmAlterao.setResizable(false);
		frmAlterao.setIconImage(
				Toolkit.getDefaultToolkit().getImage(TelaAlteracao.class.getResource("/imagem/icon.png")));
		frmAlterao.setTitle("Alteracao");
		frmAlterao.setBounds(100, 100, 450, 300);
		frmAlterao.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Alteracao");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(135, 0, 197, 49);
		frmAlterao.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("id do pedido:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(40, 78, 111, 28);
		frmAlterao.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("id do produto:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(40, 116, 143, 28);
		frmAlterao.getContentPane().add(lblNewLabel_1_1);

		idpedido = new JTextField();
		idpedido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idpedido.setBounds(169, 84, 163, 19);
		frmAlterao.getContentPane().add(idpedido);
		idpedido.setColumns(10);

		idproduto = new JTextField();
		idproduto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idproduto.setColumns(10);
		idproduto.setBounds(169, 122, 163, 19);
		frmAlterao.getContentPane().add(idproduto);
		JLabel resultLb = new JLabel("");
		resultLb.setFont(new Font("Tahoma", Font.PLAIN, 16));
		resultLb.setBounds(40, 209, 362, 28);
		frmAlterao.getContentPane().add(resultLb);
		JButton btnNewButton = new JButton("Inserir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Fachada.adicionarProdutoPedido(Integer.parseInt(idpedido.getText()),
							Integer.parseInt(idproduto.getText()));
					resultLb.setText("Sucesso! o produto " + idproduto.getText() +" no pedido "+ idpedido.getText() + " foi adicionado!");
				} catch (Exception e1) {
					resultLb.setText(e1.getMessage());
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(40, 159, 111, 19);
		frmAlterao.getContentPane().add(btnNewButton);

		JButton btnCancelar = new JButton("Remover");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Fachada.removerProdutoPedido(Integer.parseInt(idpedido.getText()),
							Integer.parseInt(idproduto.getText()));
					resultLb.setText("Sucesso! o produto " + idproduto.getText() +" no pedido "+ idpedido.getText() + " foi removido!");
				} catch (Exception e1) {
					resultLb.setText(e1.getMessage());
				}
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCancelar.setBounds(291, 159, 111, 19);
		frmAlterao.getContentPane().add(btnCancelar);
		resultLb.setFont(new Font("Tahoma", Font.PLAIN, 16));
		resultLb.setBounds(40, 209, 362, 28);
		frmAlterao.getContentPane().add(resultLb);
	}

	public JFrame getFrame() {
		return frmAlterao;
	}

	public void setFrame(JFrame frame) {
		this.frmAlterao = frame;
	}
}
