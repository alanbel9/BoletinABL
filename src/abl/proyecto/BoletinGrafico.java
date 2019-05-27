package abl.proyecto;
import abl.libreria.Cadenas;
import abl.proyecto.Principal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoletinGrafico {

	private JFrame frame;
	private JTextField txtFecha;
	private JTextField txtNumBoletin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoletinGrafico window = new BoletinGrafico();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BoletinGrafico() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alan\\Downloads\\abel.png"));
		frame.setBounds(100, 100, 450, 264);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIntroduceFechaDdmmyyyy = new JLabel("Introduce fecha:");
		lblIntroduceFechaDdmmyyyy.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblIntroduceFechaDdmmyyyy.setBounds(31, 33, 132, 14);
		frame.getContentPane().add(lblIntroduceFechaDdmmyyyy);
		
		txtFecha = new JTextField();
		txtFecha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Al clicar el texto desaparece
				txtFecha.setText("");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				//si se va y deja la casilla vacía, vuelve a ponerse el patrón de fecha.
				if(txtFecha.getText() == "") txtFecha.setText("dd/mm/yyyy");
			}
		});
		txtFecha.setText("dd/mm/yyyy");
		txtFecha.setBounds(173, 30, 86, 20);
		frame.getContentPane().add(txtFecha);
		txtFecha.setColumns(10);
		
		JButton btnDescargar = new JButton("Descargar");
		btnDescargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( txtFecha.getText().trim().length() == 0  )   {
					JOptionPane.showMessageDialog(null, "Fecha no valida. ");
				}
				else{
					
					Principal p = new Principal();
					p.fechaBoletin = txtFecha.getText().trim();
					
					// Escribir automáticamente el texto del número boletín.
					String numBoletin = p.buscarNumeroBoletin(p.obtenerBoletinNotificaciones());
					txtNumBoletin.setText(numBoletin);
					p.iniciar();
				}
			}
		});
		btnDescargar.setBounds(294, 29, 119, 23);
		frame.getContentPane().add(btnDescargar);
		
		JLabel lblNmeroDeBoletn = new JLabel("N\u00BA de Bolet\u00EDn:");
		lblNmeroDeBoletn.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNmeroDeBoletn.setBounds(31, 75, 132, 14);
		frame.getContentPane().add(lblNmeroDeBoletn);
		
		txtNumBoletin = new JTextField();
		txtNumBoletin.setEnabled(false);
		txtNumBoletin.setColumns(10);
		txtNumBoletin.setBounds(173, 72, 86, 20);
		frame.getContentPane().add(txtNumBoletin);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//salir
				System.exit(0);
			}
		});
		btnSalir.setBounds(294, 71, 119, 23);
		frame.getContentPane().add(btnSalir);
		
		JLabel lblSss = new JLabel("");
		lblSss.setIcon(new ImageIcon("C:\\Users\\Alan\\Downloads\\logo-AB.png"));
		lblSss.setBounds(127, 115, 172, 110);
		frame.getContentPane().add(lblSss);
	}
}
