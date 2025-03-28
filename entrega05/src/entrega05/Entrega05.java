/*ARIEL SEMPERTEGUI SOLIZ*/
package entrega05;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Entrega05 {

	private static final String EXP_REG_UDS = "^[0-9][0-9]?$";

	
	private JFrame ventana;
	private JLabel lblId; 
	private JTextField txtDescripcion;
	private JTextField txtUdsVenta;
	private JTextField txtUdsAlmacen;
	private JTable tablaProducto;
	private DefaultTableModel tableModelProducto;
	private JTextField txtFechaCaducidad;
	
	/**
	 * Create the application.
	 */
	public Entrega05() {
		initialize();
	}
	
	private void mostrarDatosTablaProducto() throws SQLException {
		
		tableModelProducto.setRowCount(0);
		Connection con = ConnectionSingleton.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from producto");
		
		while(rs.next()) {
			Object[] row = new Object[6];
			row[0] = rs.getInt("id");
			row[1] = rs.getString("descripcion");
			row[2] = rs.getInt("udsventa");
			row[3] = rs.getString("udsalmacen");
			row[4] = rs.getInt("udstotal");
			row[5] = rs.getObject("caducidad", LocalDate.class);
			tableModelProducto.addRow(row);
		}
		
		rs.close();
		stmt.close();
		con.close();
	}
	
	private void borrarInputs() {
		lblId.setText("");
		txtDescripcion.setText("");
		txtUdsVenta.setText("");
		txtUdsAlmacen.setText("");
		txtFechaCaducidad.setText("");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ventana = new JFrame();
		ventana.setTitle("GESTION TIENDA LOGISTICA");
		ventana.setBounds(100, 100, 655, 613);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.getContentPane().setLayout(null);
		
		JScrollPane scrollPaneProducto = new JScrollPane();
		scrollPaneProducto.setBounds(61, 34, 512, 180);
		ventana.getContentPane().add(scrollPaneProducto);
		
		tableModelProducto = new DefaultTableModel();
		tableModelProducto.addColumn("Código");
		tableModelProducto.addColumn("Descripción");
		tableModelProducto.addColumn("Uds. Venta");
		tableModelProducto.addColumn("Uds. Almacén");
		tableModelProducto.addColumn("Uds. Total");
		tableModelProducto.addColumn("Fecha");
		
		tablaProducto = new JTable(tableModelProducto);
		tablaProducto.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPaneProducto.setViewportView(tablaProducto);
		
		JButton btnAnyadirProducto = new JButton("Añadir");
		btnAnyadirProducto.setBounds(61, 410, 117, 25);
		ventana.getContentPane().add(btnAnyadirProducto);
		
		JButton btnActualizarProducto = new JButton("Actualizar");
		btnActualizarProducto.setEnabled(false);
		btnActualizarProducto.setBounds(190, 410, 117, 25);
		ventana.getContentPane().add(btnActualizarProducto);
		
		JButton btnBorrarProducto = new JButton("Borrar");
		btnBorrarProducto.setEnabled(false);
		btnBorrarProducto.setBounds(319, 410, 117, 25);
		ventana.getContentPane().add(btnBorrarProducto);
		
		JLabel lblAnyadirProducto = new JLabel("Añadir Producto:");
		lblAnyadirProducto.setBounds(61, 243, 152, 15);
		ventana.getContentPane().add(lblAnyadirProducto);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblDescripcion.setBounds(84, 270, 117, 15);
		ventana.getContentPane().add(lblDescripcion);
		
		JLabel lblUdsVenta = new JLabel("Uds. Venta:");
		lblUdsVenta.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUdsVenta.setBounds(84, 307, 117, 15);
		ventana.getContentPane().add(lblUdsVenta);
		
		JLabel lblUdsAlmacen = new JLabel("Uds. Almacen:");
		lblUdsAlmacen.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUdsAlmacen.setBounds(84, 342, 117, 15);
		ventana.getContentPane().add(lblUdsAlmacen);
		
		JLabel lblCaducidad = new JLabel("Fecha caducidad:");
		lblCaducidad.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblCaducidad.setBounds(84, 371, 117, 15);
		ventana.getContentPane().add(lblCaducidad);
		
		lblId = new JLabel("");
		lblId.setVisible(false);
		lblId.setBounds(352, 272, 70, 15);
		ventana.getContentPane().add(lblId);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(201, 270, 142, 19);
		ventana.getContentPane().add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		txtUdsVenta = new JTextField();
		txtUdsVenta.setColumns(10);
		txtUdsVenta.setBounds(201, 307, 96, 19);
		ventana.getContentPane().add(txtUdsVenta);
		
		txtUdsAlmacen = new JTextField();
		txtUdsAlmacen.setColumns(10);
		txtUdsAlmacen.setBounds(201, 340, 96, 19);
		ventana.getContentPane().add(txtUdsAlmacen);
		
		txtFechaCaducidad = new JTextField();
		txtFechaCaducidad.setColumns(10);
		txtFechaCaducidad.setBounds(201, 371, 96, 19);
		ventana.getContentPane().add(txtFechaCaducidad);
		
		JLabel lblFormatFecha = new JLabel("yyyy-MM-dd");
		lblFormatFecha.setFont(new Font("Dialog", Font.ITALIC, 11));
		lblFormatFecha.setBounds(314, 373, 87, 15);
		ventana.getContentPane().add(lblFormatFecha);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		btnCancelar.setBounds(190, 453, 117, 25);
		ventana.getContentPane().add(btnCancelar);
		
		
		btnAnyadirProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtDescripcion.getText().isBlank()) {
					JOptionPane.showMessageDialog(ventana, "Introduce una descripcion", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else if(txtUdsVenta.getText().isBlank() || !Pattern.matches(EXP_REG_UDS, txtUdsVenta.getText())) {
					JOptionPane.showMessageDialog(ventana, "Introduce uds de venta. 2 Dígitos como máximo", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else if(txtUdsAlmacen.getText().isBlank() || !Pattern.matches(EXP_REG_UDS, txtUdsAlmacen.getText())) {
					JOptionPane.showMessageDialog(ventana, "Introduce uds en almacen 2. Dígitos como máximo", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else if(txtFechaCaducidad.getText().isBlank()){
					JOptionPane.showMessageDialog(ventana, "Introduce fecha de caducidad", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else {
					
					int udsVenta = Integer.parseInt(txtUdsVenta.getText());
					int udsAlmacen = Integer.parseInt(txtUdsAlmacen.getText());
					int udsTotal = udsVenta + udsAlmacen;
					
					try {
						LocalDate fechaCaducidad = LocalDate.parse(txtFechaCaducidad.getText());
						
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement pstmt = con.prepareStatement("insert into producto(descripcion, udsventa, udsalmacen, udstotal, caducidad) values(?,?,?,?,?)");
						pstmt.setString(1, txtDescripcion.getText());
						pstmt.setInt(2, udsVenta);
						pstmt.setInt(3, udsAlmacen);
						pstmt.setInt(4, udsTotal);
						pstmt.setObject(5, fechaCaducidad);
						pstmt.executeUpdate();
						pstmt.close();						
						con.close();
						
						mostrarDatosTablaProducto();
						
						borrarInputs();

						
					}catch(SQLException ex1) {
						JOptionPane.showMessageDialog(ventana, ex1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}catch(DateTimeParseException ex2) {
						JOptionPane.showMessageDialog(ventana, "Introduce una fecha como la del formato", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnActualizarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtDescripcion.getText().isBlank()) {
					JOptionPane.showMessageDialog(ventana, "Introduce una descripcion", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else if(txtUdsVenta.getText().isBlank() || !Pattern.matches(EXP_REG_UDS, txtUdsVenta.getText())) {
					JOptionPane.showMessageDialog(ventana, "Introduce uds de venta. 2 Dígitos como máximo", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else if(txtUdsAlmacen.getText().isBlank() || !Pattern.matches(EXP_REG_UDS, txtUdsAlmacen.getText())) {
					JOptionPane.showMessageDialog(ventana, "Introduce uds en almacen. 2 Dígitos como máximo", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else if(txtFechaCaducidad.getText().isBlank()){
					JOptionPane.showMessageDialog(ventana, "Introduce fecha de caducidad", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else {
					
					int udsVenta = Integer.parseInt(txtUdsVenta.getText());
					int udsAlmacen = Integer.parseInt(txtUdsAlmacen.getText());
					int udsTotal = udsVenta + udsAlmacen;
					
					try {
						LocalDate fechaCaducidad = LocalDate.parse(txtFechaCaducidad.getText());
						
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement pstmt = con.prepareStatement("update producto set descripcion = ?, udsventa = ?, udsalmacen = ?, udstotal = ?, caducidad = ? where id = ?");
						pstmt.setString(1, txtDescripcion.getText());
						pstmt.setInt(2, udsVenta);
						pstmt.setInt(3, udsAlmacen);
						pstmt.setInt(4, udsTotal);
						pstmt.setObject(5, fechaCaducidad);
						pstmt.setInt(6, Integer.parseInt(lblId.getText()));
						pstmt.executeUpdate();
						pstmt.close();						
						con.close();
						
						mostrarDatosTablaProducto();
						
						borrarInputs();
						
					}catch(SQLException ex1) {
						JOptionPane.showMessageDialog(ventana, ex1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}catch(DateTimeParseException ex2) {
						JOptionPane.showMessageDialog(ventana, "Introduce una fecha como la del formato", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnBorrarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement pstmt = con.prepareStatement("delete from producto where id = ?");
					pstmt.setInt(1, Integer.parseInt(lblId.getText()));
					pstmt.executeUpdate();
					pstmt.close();						
					con.close();
					
					mostrarDatosTablaProducto();
					
					borrarInputs();
					
				}catch(SQLException ex1) {
					JOptionPane.showMessageDialog(ventana, ex1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}catch(DateTimeParseException ex2) {
					JOptionPane.showMessageDialog(ventana, "Introduce una fecha como la del formato", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		tablaProducto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				btnAnyadirProducto.setEnabled(false);
				btnActualizarProducto.setEnabled(true);
				btnBorrarProducto.setEnabled(true);
				btnCancelar.setEnabled(true);
				
				int index = tablaProducto.getSelectedRow();
				TableModel model = tablaProducto.getModel();
				
				lblId.setText(model.getValueAt(index, 0).toString());
				txtDescripcion.setText(model.getValueAt(index, 1).toString());
				txtUdsVenta.setText(model.getValueAt(index, 2).toString());
				txtUdsAlmacen.setText(model.getValueAt(index, 3).toString());
				txtFechaCaducidad.setText(model.getValueAt(index, 5).toString());
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnAnyadirProducto.setEnabled(true);
				btnActualizarProducto.setEnabled(false);
				btnBorrarProducto.setEnabled(false);
				btnCancelar.setEnabled(false);
				
				borrarInputs();
				
			}
		});
	}

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
					Entrega05 window = new Entrega05();
					window.ventana.setVisible(true);
				try {
					window.mostrarDatosTablaProducto();
				} catch(SQLException ex1) {
					JOptionPane.showMessageDialog(window.ventana, ex1.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
				} catch(Exception ex2) {
					ex2.printStackTrace();
				}
			}
		});
	}
}
