
import com.toedter.calendar.JTextFieldDateEditor;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author estme
 */
public class Movilidad extends javax.swing.JFrame {

    /**
     * Creates new form Administracion
     */
    conexionSQL cc = new conexionSQL();
    Connection con = cc.conexion();

    public Movilidad() {
        initComponents();
        this.setLocationRelativeTo(null);
        llenarComboBoxPais();
        llenarComboBoxEstado();
        mostrarDatos();
    }

    public void llenarComboBoxPais() {
        try {
            String sql = "select name from countries";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cbxPais.removeAll();
            while (rs.next()) {
                cbxPais.addItem(rs.getString("name"));

            }
        } catch (Exception ex) {
            JOptionPane.showInputDialog("Error: " + ex);
        }

    }

    public void llenarComboBoxEstado() {
        try {

            String sql = "select estado from estados";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cbxEstado.removeAll();
            while (rs.next()) {
                cbxEstado.addItem(rs.getString("estado"));

            }
        } catch (Exception ex) {
            JOptionPane.showInputDialog("Error: " + ex);
        }
    }

    public void mostrarDatos() {
        String[] titulos = {"Boleta del Alumno", "Nombre", "Programa Academico", "Estado Movilidad"};
        String[] registros = new String[4];
        //Se especifica que la información de la tabla no puede ser editada.
        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        //Asignación de la consulta a una String.
        String SQL = "select s.Bol_Solic, s.Nom_Solic,s.AP_Solic,s.AM_Solic,s.Ins_Solic ,s.Egr_Solic,s.EM_Solic, p.Nom_prog from solicitante s INNER JOIN programa_academico p  where s.ID_Prog = p.ID_Prog";

        try {
            //Se ejecuta la consulta y se almacenan los datos provenientes de la DB en un arreglo
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                if (Integer.parseInt(rs.getString("Ins_Solic")) == 1) {
                    registros[0] = rs.getString("Bol_Solic");
                    registros[1] = rs.getString("Nom_Solic") + " " + rs.getString("AP_Solic") + " " + rs.getString("AM_Solic");
                    registros[2] = rs.getString("Nom_prog");
                    registros[3] = comprobarMovilidad(rs.getString("EM_Solic"));
                    modelo.addRow(registros);

                }

            }
            //Se llena la tabla
            tableMov.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos" + ex.getMessage());
        }
    }

    public void filtrarDatosMostrados(String valor) {
        String[] titulos = {"Boleta del Alumno", "Nombre", "Programa Academico", "Estado Movilidad"};
        String[] registros = new String[4];
        //Se especifica que la información de la tabla no puede ser editada.
        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        //Asignación de la consulta a una String.
        String SQL = "select s.Bol_Solic, s.Nom_Solic,s.AP_Solic,s.AM_Solic,s.Ins_Solic ,s.Egr_Solic,s.EM_Solic, p.Nom_prog from solicitante s INNER JOIN programa_academico p  where s.ID_Prog = p.ID_Prog and where concat(Nom_Solic, ' ',AP_solic, ' ', AM_Solic) like '%" + valor + "%'";

        try {
            //Se ejecuta la consulta y se almacenan los datos provenientes de la DB en un arreglo
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                if (Integer.parseInt(rs.getString("Ins_Solic")) == 1) {
                    registros[0] = rs.getString("Bol_Solic");
                    registros[1] = rs.getString("Nom_Solic") + " " + rs.getString("AP_Solic") + " " + rs.getString("AM_Solic");
                    registros[2] = rs.getString("Nom_prog");
                    registros[3] = comprobarMovilidad(rs.getString("EM_Solic"));
                    modelo.addRow(registros);

                }

            }
            //Se llena la tabla
            tableMov.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos: " + ex.getMessage());
        }
    }

    public void asignarMovilidad(String valorID) {

        String sql = "Insert into movilidad(Matri, ID_Peri, POE_Movi, Fim_Movi,VC_Movi,EOR_Movi) values (?,?,?,?,?,?)";
        String sql2 = "select ID_Peri from periodos where A_Peri = 1";
        String sql3 = "update solicitante set EM_Solic = 1  where Bol_Solic = ?";

        try {
            String ID_periodoActual = "", varFinanciamiento = "0", varValorC = "0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql2);
            while (rs.next()) {
                ID_periodoActual = rs.getString("ID_Peri");
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, valorID);
            ps.setString(2, ID_periodoActual);
            if (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()).equals("Seleccionar Pais")) {
                ps.setString(3, (String) cbxPais.getSelectedItem());
            } else {
                ps.setString(3, (String) cbxEstado.getSelectedItem());
            }
            if (ckbFinanciamiento.isSelected()) {
                varFinanciamiento = "1";
            }
            if (ckbValorC.isSelected()) {
                varValorC = "1";
            }
            ps.setInt(4, Integer.parseInt(varFinanciamiento));
            ps.setInt(5, Integer.parseInt(varValorC));
            ps.setInt(6, (comprobarEOR((String) cbxEOR.getSelectedItem())));
            ps.executeUpdate();

            ps = con.prepareStatement(sql3);
            ps.setInt(1, Integer.parseInt(valorID));
            ps.executeUpdate();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al asignar movilidad: " + ex);
        }
        mostrarDatos();
    }

    public void verificarMovilidad(String valorId) {
        int valor;
        String sql = "select EM_Solic from solicitante where Bol_Solic = ?";
        try {
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, valorId);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                valor = Integer.parseInt(rs.getString("EM_Solic"));
                if (valor == 1) {
                    jTabbedPane1.setEnabled(false);
                    cbxEOR.setEnabled(false);
                    ckbFinanciamiento.setEnabled(false);
                    ckbValorC.setEnabled(false);
                    cbxEstado.setEnabled(false);
                    cbxPais.setEnabled(false);
                } else {
                    jTabbedPane1.setEnabled(true);
                    cbxEOR.setEnabled(true);
                    ckbFinanciamiento.setEnabled(true);
                    ckbValorC.setEnabled(true);
                    cbxEstado.setEnabled(true);
                    cbxPais.setEnabled(true);
                    
                }
            }

        } catch (Exception ex) {

        }

    }

    public String comprobarMovilidad(String string) {
        if (string.equals("1")) {
            return "En movilidad";
        } else {
            return "Sin movilidad";
        }
    }

    public int comprobarEOR(String string) {
        if (string.equals("Envío")) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMov = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        ckbFinanciamiento = new javax.swing.JCheckBox();
        ckbValorC = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        cbxPais = new javax.swing.JComboBox<>();
        cbxEstado = new javax.swing.JComboBox<>();
        cbxEOR = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(111, 27, 70));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane2.setBackground(new java.awt.Color(111, 27, 70));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(111, 27, 70)));
        jTabbedPane2.setForeground(new java.awt.Color(111, 27, 70));

        jPanel1.setBackground(new java.awt.Color(111, 27, 70));

        jPanel16.setAutoscrolls(true);

        tableMov.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableMov.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMovMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableMovMousePressed(evt);
            }
        });
        tableMov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableMovKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableMov);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel9.setText("Búsqueda:");

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        ckbFinanciamiento.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        ckbFinanciamiento.setText("Financiamiento");
        ckbFinanciamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbFinanciamientoActionPerformed(evt);
            }
        });

        ckbValorC.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        ckbValorC.setText("Valor curricular");
        ckbValorC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbValorCActionPerformed(evt);
            }
        });

        jButton2.setText("Asignar Movilidad");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        cbxPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPaisActionPerformed(evt);
            }
        });
        jTabbedPane1.addTab("Seleccionar Pais", cbxPais);

        cbxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoActionPerformed(evt);
            }
        });
        jTabbedPane1.addTab("Seleccionar Entidad", cbxEstado);

        cbxEOR.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Envío", "Recepción" }));
        cbxEOR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEORActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckbValorC, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ckbFinanciamiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cbxEOR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(83, 83, 83))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ckbFinanciamiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckbValorC)
                        .addGap(32, 32, 32)
                        .addComponent(cbxEOR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Movilidad", jPanel1);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 840, 600));

        jPanel4.setBackground(new java.awt.Color(111, 27, 70));

        jButton1.setBackground(new java.awt.Color(111, 27, 70));
        jButton1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 583, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tableMov.getSelectedRow();
        String valorID = (String) tableMov.getValueAt(filaSeleccionada, 0);
        asignarMovilidad(valorID);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ckbValorCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbValorCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckbValorCActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        filtrarDatosMostrados(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtBuscarActionPerformed

    private void cbxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEstadoActionPerformed

    private void cbxPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxPaisActionPerformed

    private void ckbFinanciamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbFinanciamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckbFinanciamientoActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void cbxEORActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEORActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEORActionPerformed

    private void tableMovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMovMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tableMov.getSelectedRow();
        String valorID = (String) tableMov.getValueAt(filaSeleccionada, 0);
        verificarMovilidad(valorID);
    }//GEN-LAST:event_tableMovMouseClicked

    private void tableMovKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableMovKeyReleased
        // TODO add your handling code here:
        int filaSeleccionada = tableMov.getSelectedRow();
        String valorID = (String) tableMov.getValueAt(filaSeleccionada, 0);
        verificarMovilidad(valorID);
    }//GEN-LAST:event_tableMovKeyReleased

    private void tableMovMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMovMousePressed
        // TODO add your handling code here:
        int filaSeleccionada = tableMov.getSelectedRow();
        String valorID = (String) tableMov.getValueAt(filaSeleccionada, 0);
        verificarMovilidad(valorID);
    }//GEN-LAST:event_tableMovMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Movilidad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxEOR;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxPais;
    private javax.swing.JCheckBox ckbFinanciamiento;
    private javax.swing.JCheckBox ckbValorC;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tableMov;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
