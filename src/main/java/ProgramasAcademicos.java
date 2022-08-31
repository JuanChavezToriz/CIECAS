
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
public class ProgramasAcademicos extends javax.swing.JFrame {

    /**
     * Creates new form Administracion
     */
    conexionSQL cc = new conexionSQL();
    Connection con = cc.conexion();

    public ProgramasAcademicos() {
        initComponents();
        this.setLocationRelativeTo(null);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdFechaCreacion.getDateEditor();
        editor.setEditable(false);
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) jdFechaActualizacion.getDateEditor();
        editor2.setEditable(false);
        JTextFieldDateEditor editor3 = (JTextFieldDateEditor) jdFechaInicioPeriodo.getDateEditor();
        editor3.setEditable(false);
        JTextFieldDateEditor editor4 = (JTextFieldDateEditor) jdFechaFinPeriodo.getDateEditor();
        editor4.setEditable(false);
        mostrarDatosPA();
        mostrarDatosPeriodo();

    }

    public void guardarProgramaAcademico(String[] datos) {
        String sql = "CALL `sp_guardar_programaA`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, datos[0]);
            ps.setString(2, datos[1]);
            ps.setString(3, datos[2]);
            ps.setString(4, datos[3]);
            ps.setString(5, datos[4]);
            ps.setString(6, datos[5]);
            ps.setString(7, datos[6]);
            ps.setString(8, datos[7]);
            ps.setString(9, datos[8]);
            ps.setString(10, datos[9]);
            int res = ps.executeUpdate();

            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Evento Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
        }
        mostrarDatosPA();
    }

    public void guardarPeriodos(String[] datos) {
        String sql = "Update periodos set A_Peri = 0 where A_Peri = 1";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            int res = ps.executeUpdate();
            sql = "Insert into  periodos (Nom_peri, FCI_Peri, FHT_Peri, A_Peri) values (?, ?, ?, 1)";
            ps = con.prepareStatement(sql);
            ps.setString(1, datos[0]);
            ps.setString(2, datos[1]);
            ps.setString(3, datos[2]);

            res = ps.executeUpdate();

            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Periodo Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar periodo");
            }
            limpiar2();
            mostrarDatosPeriodo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
        }

    }

    public void mostrarDatosPA() {

        String[] titulos = {"Clave del Programa", "Nombre del Programa", "Modalidad"};
        String[] registros = new String[3];
        //Se especifica que la información de la tabla no puede ser editada.
        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        //Asignación de la consulta a una String.
        String SQL = "select Clv_Prog, Nom_Prog, Mod_prog  from programa_academico";

        try {
            //Se ejecuta la consulta y se almacenan los datos provenientes de la DB en un arreglo
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                registros[0] = rs.getString("Clv_Prog");
                registros[1] = rs.getString("Nom_Prog");
                registros[2] = rs.getString("Mod_Prog");
                modelo.addRow(registros);

            }
            //Se llena la tabla
            tablePE.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos" + ex.getMessage());
        }

    }

    public void filtrarDatosPA(String valor) {

        String[] titulos = {"Clave del Programa", "Nombre del Programa", "Modalidad"};
        String[] registros = new String[3];
        //Se especifica que la información de la tabla no puede ser editada.
        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        //Asignación de la consulta a una String.
        String SQL = "select Clv_Prog, Nom_Prog, Mod_prog  from programa_academico where concat(Clv_Prog, ' ',Nom_Prog,' ', Mod_prog) like '%" + valor + "%'";

        try {
            //Se ejecuta la consulta y se almacenan los datos provenientes de la DB en un arreglo
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                registros[0] = rs.getString("Clv_Prog");
                registros[1] = rs.getString("Nom_Prog");
                registros[2] = rs.getString("Mod_Prog");
                modelo.addRow(registros);

            }
            //Se llena la tabla
            tablePE.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos" + ex.getMessage());
        }

    }

    public void eliminarPE(String Clv_prog) {
        try {
            System.out.println("Clv_prog = " + Clv_prog);
            String SQL = "delete from programa_academico where Clv_Prog = ? ";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, Clv_prog);
            pst.execute();
            JOptionPane.showMessageDialog(null, "El programa academico ha sido eliminado de la base de datos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
        }
    }

    public void mostrarDatosPeriodo() {

        String[] titulos = {"ID del Periodo","Nombre del periodo", "Fecha de inicio", "Fecha de termino"};
        String[] registros = new String[4];
        //Se especifica que la información de la tabla no puede ser editada.
        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        //Asignación de la consulta a una String.
        String SQL = "select ID_Peri,Nom_Peri,FCI_Peri , FHT_Peri  from periodos";

        try {
            //Se ejecuta la consulta y se almacenan los datos provenientes de la DB en un arreglo
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                registros[0] = rs.getString("ID_Peri");
                registros[1] = rs.getString("Nom_Peri");
                registros[2] = rs.getString("FCI_Peri");
                registros[3]  = rs.getString("FHT_Peri");
                modelo.addRow(registros);

            }
            //Se llena la tabla
            tablePeriodos.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos" + ex.getMessage());
        }

    }

    public void filtrarDatosPeriodo(String valor) {


       String[] titulos = {"ID del Periodo","Nombre del periodo", "Fecha de inicio", "Fecha de termino"};
        String[] registros = new String[4];
        //Se especifica que la información de la tabla no puede ser editada.
        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        //Asignación de la consulta a una String.
        String SQL = "select ID_Peri,Nom_Peri,FCI_Peri , FHT_Peri  from periodos where concat(Nom_Peri, ' ',FCI_Peri,' ', FHT_Peri) like '%" + valor + "%'";

        try {
            //Se ejecuta la consulta y se almacenan los datos provenientes de la DB en un arreglo
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                registros[0] = rs.getString("ID_Peri");
                registros[1] = rs.getString("Nom_Peri");
                registros[2] = rs.getString("FCI_Peri");
                registros[3]  = rs.getString("FHT_Peri");
                modelo.addRow(registros);

            }
            //Se llena la tabla
            tablePeriodos.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos" + ex.getMessage());
        }

    }
    
     public void eliminarPerido(String ID_Peri) {
        try {
            
            String SQL = "delete from periodos where ID_Peri = ? ";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, ID_Peri);
            pst.execute();
            JOptionPane.showMessageDialog(null, "El periodo ha sido eliminado de la base de datos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
        }
    }

    public void limpiar2() {

        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdFechaInicioPeriodo.getDateEditor();
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) jdFechaFinPeriodo.getDateEditor();

        txtNomPeri.setText(null);
        editor.setText(null);
        editor2.setText(null);

    }

    public void limpiar() {

        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdFechaCreacion.getDateEditor();
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) jdFechaActualizacion.getDateEditor();

        txtCAC.setText(null);
        txtCVP.setText(null);
        txtCantidad.setText(null);
        txtNomPE.setText(null);
        editor.setText(null);
        editor2.setText(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCVP = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNomPE = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbxRdP = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jdFechaCreacion = new com.toedter.calendar.JDateChooser();
        cbxModalidad = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jdFechaActualizacion = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        cbxTiempo = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCAC = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbxPeriAdmi = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePE = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        btnAgregarPrograma = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtBuscar3 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePeriodos = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNomPeri = new javax.swing.JTextField();
        jdFechaInicioPeriodo = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jdFechaFinPeriodo = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        jButton4.setText("jButton4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(111, 27, 70));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane2.setBackground(new java.awt.Color(111, 27, 70));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(111, 27, 70)));
        jTabbedPane2.setForeground(new java.awt.Color(111, 27, 70));
        jTabbedPane2.setPreferredSize(new java.awt.Dimension(750, 607));

        jPanel1.setBackground(new java.awt.Color(111, 27, 70));

        jPanel16.setAutoscrolls(true);

        jLabel5.setText("Clave del Programa:");

        txtCVP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCVPActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre del Programa de Estudios:");

        jLabel7.setText("Modalidad:");

        cbxRdP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Licenciatura", "Maestría", "Especialidades médicas", " " }));

        jLabel8.setText("Requisito del Programa:");

        jLabel9.setText("Fecha de creación: ");

        jdFechaCreacion.setToolTipText("Introduce la fecha a llevarse a cabo el evento.");
        jdFechaCreacion.setMinSelectableDate(new java.util.Date(-62135744288000L));

        cbxModalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Escolarizada", "Mixta", "Abierta", "Virtual a distancia" }));

        jLabel12.setText("Fecha de actualización: ");

        jdFechaActualizacion.setToolTipText("Introduce la fecha a llevarse a cabo el evento.");
        jdFechaActualizacion.setMinSelectableDate(new java.util.Date(-62135744288000L));

        jLabel13.setText("Duración:");

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        cbxTiempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Años", "Semestres", "Cuatrimestres", "Trimestres", "Asignatura o créditos", "Módulos" }));

        jLabel14.setText("Créditos a cubrir:");

        txtCAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCACActionPerformed(evt);
            }
        });

        jLabel16.setText("Periodo de admisión: ");

        cbxPeriAdmi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", " " }));

        tablePE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablePE.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablePE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePEMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablePEMousePressed(evt);
            }
        });
        tablePE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablePEKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tablePE);

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel17.setText("Programas de Estudio");

        btnAgregarPrograma.setText("Agregar Programa Académico");
        btnAgregarPrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProgramaActionPerformed(evt);
            }
        });

        jButton5.setText("Limpiar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Borrar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel18.setText("Búsqueda:");

        txtBuscar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscar3ActionPerformed(evt);
            }
        });
        txtBuscar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscar3KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxModalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCVP, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(80, 80, 80)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNomPE, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxRdP, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jdFechaCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCAC, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jdFechaActualizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxPeriAdmi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(234, 234, 234)
                                .addComponent(jButton5)
                                .addGap(18, 18, 18)
                                .addComponent(btnAgregarPrograma)
                                .addGap(74, 74, 74))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton6)
                                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addGroup(jPanel16Layout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCVP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtNomPE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(cbxModalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(cbxRdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jdFechaCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jdFechaActualizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtCAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cbxPeriAdmi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPrograma)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(281, 281, 281))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Añadir Programa Académico", jPanel1);

        jPanel3.setBackground(new java.awt.Color(111, 27, 70));

        jPanel18.setAutoscrolls(true);

        tablePeriodos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablePeriodos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablePeriodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePeriodosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablePeriodosMousePressed(evt);
            }
        });
        tablePeriodos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablePeriodosKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tablePeriodos);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel11.setText("Búsqueda:");

        txtBuscar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscar2ActionPerformed(evt);
            }
        });
        txtBuscar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscar2KeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel1.setText("Historial de Periodos Escolares");

        jButton2.setText("Nuevo Periodo Académico");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre del Periodo:");

        jdFechaInicioPeriodo.setToolTipText("Introduce la fecha a llevarse a cabo el evento.");
        jdFechaInicioPeriodo.setMinSelectableDate(new java.util.Date(-62135744288000L));

        jLabel3.setText("Fecha de Inicio del Periodo Académico");

        jLabel4.setText("Fecha de Filanización del Periodo Académico");

        jdFechaFinPeriodo.setToolTipText("Introduce la fecha a llevarse a cabo el evento.");
        jdFechaFinPeriodo.setMinSelectableDate(new java.util.Date(-62135744288000L));

        jButton3.setText("Eliminar Periodo Académico");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.setText("Limpiar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNomPeri, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton7))
                                    .addComponent(jdFechaFinPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jdFechaInicioPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNomPeri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jdFechaInicioPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdFechaFinPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton7)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Periodos Académicos", jPanel3);

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
                .addContainerGap(736, Short.MAX_VALUE)
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

    private void tablePeriodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePeriodosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePeriodosMouseClicked

    private void tablePeriodosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePeriodosMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePeriodosMousePressed

    private void tablePeriodosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablePeriodosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePeriodosKeyReleased

    private void txtBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar2ActionPerformed

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyReleased
        // TODO add your handling code here:
        filtrarDatosPeriodo(txtBuscar2.getText());
    }//GEN-LAST:event_txtBuscar2KeyReleased

    private void txtCVPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCVPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCVPActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtCACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCACActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCACActionPerformed

    private void tablePEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePEMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePEMouseClicked

    private void tablePEMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePEMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePEMousePressed

    private void tablePEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablePEKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePEKeyReleased

    private void btnAgregarProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProgramaActionPerformed
        // TODO add your handling code here:
        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdFechaCreacion.getDateEditor();
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) jdFechaActualizacion.getDateEditor();
        if (txtNomPE.getText().trim().isEmpty() || txtCVP.getText().trim().isEmpty() || txtCantidad.getText().trim().isEmpty() || editor.getText().isEmpty()
                || txtCAC.getText().trim().isEmpty() || editor2.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Introduce todos los datos");
        } else {

            String dia = Integer.toString(jdFechaCreacion.getCalendar().get(Calendar.DAY_OF_MONTH));
            String mes = Integer.toString(jdFechaCreacion.getCalendar().get(Calendar.MONTH) + 1);
            String year = Integer.toString(jdFechaCreacion.getCalendar().get(Calendar.YEAR));
            String fecha = (year + "-" + mes + "-" + dia);
            dia = Integer.toString(jdFechaActualizacion.getCalendar().get(Calendar.DAY_OF_MONTH));
            mes = Integer.toString(jdFechaActualizacion.getCalendar().get(Calendar.MONTH) + 1);
            year = Integer.toString(jdFechaCreacion.getCalendar().get(Calendar.YEAR));
            String fecha2 = (year + "-" + mes + "-" + dia);

            String[] datos = new String[10];
            datos[0] = txtCVP.getText();
            datos[1] = txtNomPE.getText();
            datos[2] = (String) cbxModalidad.getSelectedItem();
            datos[3] = (String) cbxRdP.getSelectedItem();
            datos[4] = fecha;
            datos[5] = fecha2;
            datos[6] = txtCantidad.getText();
            datos[7] = (String) cbxTiempo.getSelectedItem();
            datos[8] = txtCAC.getText();
            datos[9] = (String) cbxPeriAdmi.getSelectedItem();
            guardarProgramaAcademico(datos);
        }

    }//GEN-LAST:event_btnAgregarProgramaActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablePE.getSelectedRow();
        String valorID = (String) tablePE.getValueAt(filaSeleccionada, 0);
        eliminarPE(valorID);
        mostrarDatosPA();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JTextFieldDateEditor editor3 = (JTextFieldDateEditor) jdFechaInicioPeriodo.getDateEditor();
        JTextFieldDateEditor editor4 = (JTextFieldDateEditor) jdFechaFinPeriodo.getDateEditor();
        if (txtNomPeri.getText().trim().isEmpty() || editor3.getText().trim().isEmpty() || editor4.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Introduce todos los datos");
        } else {
            String dia = Integer.toString(jdFechaInicioPeriodo.getCalendar().get(Calendar.DAY_OF_MONTH));
            String mes = Integer.toString(jdFechaInicioPeriodo.getCalendar().get(Calendar.MONTH) + 1);
            String year = Integer.toString(jdFechaInicioPeriodo.getCalendar().get(Calendar.YEAR));
            String fecha = (year + "-" + mes + "-" + dia);
            dia = Integer.toString(jdFechaFinPeriodo.getCalendar().get(Calendar.DAY_OF_MONTH));
            mes = Integer.toString(jdFechaFinPeriodo.getCalendar().get(Calendar.MONTH) + 1);
            year = Integer.toString(jdFechaFinPeriodo.getCalendar().get(Calendar.YEAR));
            String fecha2 = (year + "-" + mes + "-" + dia);

            String[] datos = new String[3];

            datos[0] = txtNomPeri.getText();
            datos[1] = fecha;
            datos[2] = fecha2;
            guardarPeriodos(datos);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        limpiar2();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtBuscar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar3ActionPerformed

    private void txtBuscar3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar3KeyReleased
        // TODO add your handling code here:
        filtrarDatosPA(txtBuscar3.getText());
    }//GEN-LAST:event_txtBuscar3KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablePeriodos.getSelectedRow();
        String valorID = (String) tablePeriodos.getValueAt(filaSeleccionada, 0);
        eliminarPerido(valorID);
        mostrarDatosPeriodo();
    }//GEN-LAST:event_jButton3ActionPerformed

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
                new ProgramasAcademicos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPrograma;
    private javax.swing.JComboBox<String> cbxModalidad;
    private javax.swing.JComboBox<String> cbxPeriAdmi;
    private javax.swing.JComboBox<String> cbxRdP;
    private javax.swing.JComboBox<String> cbxTiempo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private com.toedter.calendar.JDateChooser jdFechaActualizacion;
    private com.toedter.calendar.JDateChooser jdFechaCreacion;
    private com.toedter.calendar.JDateChooser jdFechaFinPeriodo;
    private com.toedter.calendar.JDateChooser jdFechaInicioPeriodo;
    private javax.swing.JTable tablePE;
    private javax.swing.JTable tablePeriodos;
    private javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtBuscar3;
    private javax.swing.JTextField txtCAC;
    private javax.swing.JTextField txtCVP;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtNomPE;
    private javax.swing.JTextField txtNomPeri;
    // End of variables declaration//GEN-END:variables
}
