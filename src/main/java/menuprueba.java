
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;
import javax.swing.table.DefaultTableModel;
import static javax.swing.text.html.parser.DTDConstants.NUMBER;

public class menuprueba extends javax.swing.JFrame {

    DefaultTableModel modelo;
    DefaultTableModel modelo2;
    DefaultTableModel modelo3;
    DefaultTableModel modelo4;
    DefaultTableModel modelo5;
    DefaultTableModel modelo6;

    boolean test = false;
    boolean test1 = false;
    boolean test2 = false;
    boolean test3 = false;
    boolean test4 = false;
    boolean test5 = false;
    boolean test6 = false;
    boolean testnum = false;
    boolean testnum1 = false;
    boolean testnum2 = false;
    boolean testnum3 = false;
    boolean testnum4 = false;

    //variables para validar datos en especiaidades
    boolean prueba = true;
    boolean prueba1 = true;
    boolean prueba2 = true;
    boolean prueba3 = true;
    boolean prueba4 = true;
    boolean prueba5 = true;
    boolean prueba6 = true;

    static int cp, noEXT, noINT, telCASA, telCELULAR, confirmado;
    static Date fechavar;
    static String dateInString = "0000-00-00";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public menuprueba() {
        initComponents();
        this.setLocationRelativeTo(this);

        //Modelo de la tabla
        /*  modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido Paterno");
        modelo.addColumn("Apellido Materno");
        modelo.addColumn("Fecha de Nacimiento");
        modelo.addColumn("Sexo");
        this.tabla1.setModel(modelo); //datos del solicitante tabla solicitante */
        modelo2 = new DefaultTableModel();
        modelo2.addColumn("Calle");
        modelo2.addColumn("# Ext");
        modelo2.addColumn("# Int");
        modelo2.addColumn("Colonia");
        modelo2.addColumn("Municipio");
        modelo2.addColumn("CP");
        modelo2.addColumn("Estado");
        modelo2.addColumn("País");
        this.tabla2.setModel(modelo2); //domicilio del solicitante tabla domicilio del soli
        //

        modelo3 = new DefaultTableModel();
        modelo3.addColumn("Tel Casa");
        modelo3.addColumn("Tel Celular");
        modelo3.addColumn("Correo 1");
        modelo3.addColumn("Correo 2");
        this.tabla3.setModel(modelo3); //CONTACTO solicitante tabla CONTACTO del soli
        //
        modelo4 = new DefaultTableModel();
        modelo4.addColumn("Discapacidad");
        this.tabla4.setModel(modelo4); //solicitante tabla discapacidad del soli

        modelo5 = new DefaultTableModel();
        modelo5.addColumn("Lengua");
        this.tabla5.setModel(modelo5); //solicitante tabla discapacidad del soli

        modelo6 = new DefaultTableModel();
        modelo6.addColumn("Prog Académico");
        modelo6.addColumn("Modalidad");
        this.tabla6.setModel(modelo6); //solicitante tabla programa academico

        //Variables de pruebas para agregar datos a la BD van cambiando de valores cuando entran a los metodos
        llenarComboBoxPrograma();
        llenarComboBoxPais();
        llenarComboBoxEstado();

    }
    Connection cn = conexionSQL.conexion();

    public void llenarComboBoxPrograma() {
        try {
            String sql = "select Nom_Prog from programa_academico";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            jcprograma.removeAll();
            while (rs.next()) {
                jcprograma.addItem(rs.getString("Nom_Prog"));

            }
        } catch (Exception ex) {
            // JOptionPane.showInputDialog("Error: " + ex);
            System.out.println("ERROR EN LA LLENAR ESPECIALIDADES");
        }

    }

    public void llenarComboBoxPais() {
        try {
            String sql = "select name from countries";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cbxPais.removeAll();

            while (rs.next()) {
                cbxPais.addItem(rs.getString("name"));

            }
        } catch (Exception ex) {
            //JOptionPane.showInputDialog("Error: " + ex);
            System.out.println("ERROR EN LA LLENAR PAISES");
        }

    }

    public void llenarComboBoxEstado() {
        try {

            String sql = "select estado from estados";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cbxEstado.removeAll();
            while (rs.next()) {
                cbxEstado.addItem(rs.getString("estado"));

            }
        } catch (Exception ex) {
            System.out.println("ERROR EN LA LLENAR ESTADOS");
            //JOptionPane.showInputDialog("Error: " + ex);
        }
    }

    public void mostrarDatos() {

        String[] titulos = {"Nombre", "Apellido Paterno", "Apellido Materno", "Fecha de Nacimiento", "Sexo"};
        String[] registros = new String[2];

        modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        String SQL = "select Nom_Solic, AP_Solic, AM_Solic,Sexo_Solic from solicitante";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                registros[0] = rs.getString("Nom_Solic") + " " + rs.getString("AP_Solic") + " " + rs.getString("AM_Solic");
                registros[1] = rs.getString("Sexo_Solic");
                modelo.addRow(registros);

            }

            tabla1.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos" + ex.getMessage());
        }

    }//cierra un metodo mostrar datos
    
    public void filtrarDatosSolicitud(String nombre, javax.swing.JTable hola){
    //Juan aqui se tiene pensando hacer un switch case con un parametro para validar donde caer y seleccionar la tabla en especifico
       String[] titulos = {"Nombre", "Apellido Paterno", "Apellido Materno", "Fecha de Nacimiento", "Sexo"};
        String[] registros = new String[3];
        
       DefaultTableModel modelo = new DefaultTableModel(null,titulos){
           @Override 
           public boolean isCellEditable(int row, int column){
               return false;
           }
       
       };
       
       String SQL = "select Nom_Solic, AP_Solic, AM_Solic,Sexo_Solic from solicitante s where concat(Nom_Solic, ' ',AP_solic, ' ', AM_Solic) like '%"+ nombre +"%'";
       
       try{
           Statement st = cn.createStatement();
           ResultSet rs = st.executeQuery(SQL);
           while(rs.next()){
               
               if(Integer.parseInt(rs.getString("Ins_Solic")) == 0 && Integer.parseInt(rs.getString("Egr_Solic")) == 0 ){
                   registros[0]= rs.getString("ID_Solic");
                   registros[1]= rs.getString("Nom_Solic")+ " "+ rs.getString("AP_Solic")+ " "+ rs.getString("AM_Solic") ;
                   registros[2]= rs.getString("Nom_prog");
                   modelo.addRow(registros);
               
               
               
               }
               
           }
           
           hola.setModel(modelo);
       }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al Mostrar Datos" + ex.getMessage()); 
        }
       
    }//Cierra metodo filtrar datos
//icono del jframe
// @Overrid/

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        SUBTITULO3 = new javax.swing.JLabel();
        tfnom22 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tfapep = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfapem = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfcalle = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfnoext = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfnoint = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfcolonia = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tfmunicipioalca = new javax.swing.JTextField();
        tfcp = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tftelcasa = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tftelcelular = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        rbmasc = new javax.swing.JRadioButton();
        rbfeme = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        tfcorreo2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        tfcorreo1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btnguardarycontinuar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        tfdiscapacidad = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        tflenguaext = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jdfechanaci = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        SUBTITULO13 = new javax.swing.JLabel();
        jcprograma = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        cbxPais = new javax.swing.JComboBox<>();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jPanel3 = new javax.swing.JPanel();
        SUBTITULO5 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        tfprograma1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        tfinstituto1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        JDfechagraduacion1 = new com.toedter.calendar.JDateChooser();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        rbespecialidad = new javax.swing.JRadioButton();
        jLabel31 = new javax.swing.JLabel();
        tfprograma2 = new javax.swing.JTextField();
        tfinstituto2 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        JDfechagraduacion2 = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        rbmaestria = new javax.swing.JRadioButton();
        tfprograma3 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        tfinstituto3 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        JDfechagraduacion3 = new com.toedter.calendar.JDateChooser();
        btguardarantecedentes = new javax.swing.JButton();
        SUBTITULO12 = new javax.swing.JLabel();
        tfpais1 = new javax.swing.JTextField();
        tfpais2 = new javax.swing.JTextField();
        tfpais3 = new javax.swing.JTextField();
        btguardarantecedentes1 = new javax.swing.JButton();
        jDesktopPane3 = new javax.swing.JDesktopPane();
        jPanel4 = new javax.swing.JPanel();
        SUBTITULO6 = new javax.swing.JLabel();
        txtBuscar1 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        btneditarante1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla2 = new javax.swing.JTable();
        btneditarante2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla1 = new javax.swing.JTable();
        SUBTITULO7 = new javax.swing.JLabel();
        SUBTITULO8 = new javax.swing.JLabel();
        SUBTITULO9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla3 = new javax.swing.JTable();
        SUBTITULO10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla4 = new javax.swing.JTable();
        SUBTITULO15 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabla5 = new javax.swing.JTable();
        SUBTITULO16 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabla6 = new javax.swing.JTable();
        btneditarante3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Solicitud de Inscripción");
        setLocation(new java.awt.Point(60, 0));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(111, 27, 70));

        jButton1.setBackground(new java.awt.Color(111, 27, 70));
        jButton1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTabbedPane2.setBackground(new java.awt.Color(111, 27, 70));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(111, 27, 70)));
        jTabbedPane2.setForeground(new java.awt.Color(111, 27, 70));

        jDesktopPane1.setBackground(new java.awt.Color(111, 27, 70));

        SUBTITULO3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        SUBTITULO3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO3.setText("DATOS DEL SOLICITANTE:");
        SUBTITULO3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tfnom22.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tfnom22.setToolTipText("Introduce el nombre");
        tfnom22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfnom22ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("* Nombre:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tfapep.setToolTipText("Introduce el Apellido Paterno");
        tfapep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfapepActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("* Apellido Paterno");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("* Apellido Materno");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tfapem.setToolTipText("Introduce el Apellido Materno");
        tfapem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfapemActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nombre");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Domicilio");

        tfcalle.setToolTipText("Introduce la Calle");
        tfcalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcalleActionPerformed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("* Calle");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tfnoext.setToolTipText("Introduce el Numero Exterior");
        tfnoext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfnoextActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("* No. Ext");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tfnoint.setToolTipText("Introduce el Numero Interior");
        tfnoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfnointActionPerformed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("No. Int");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tfcolonia.setToolTipText("Introduce la Colonia");
        tfcolonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcoloniaActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("* Colonia");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("* Municipio / Alcaldia");

        tfmunicipioalca.setToolTipText("Introduce la Alcandia");
        tfmunicipioalca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfmunicipioalcaActionPerformed(evt);
            }
        });

        tfcp.setToolTipText("Introduce el CP");
        tfcp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcpActionPerformed(evt);
            }
        });

        jLabel11.setText("* Codigo Postal");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("* Estado");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("* País");

        tftelcasa.setToolTipText("Introduce el telefono");
        tftelcasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tftelcasaActionPerformed(evt);
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText(" Tel. Casa");

        tftelcelular.setToolTipText("Introduce el Telefono celular");
        tftelcelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tftelcelularActionPerformed(evt);
            }
        });

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Tel. Celular");

        buttonGroup1.add(rbmasc);
        rbmasc.setText("Hombre");
        rbmasc.setToolTipText("Selecciona el Sexo");
        rbmasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbmascActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbfeme);
        rbfeme.setText("Mujer");
        rbfeme.setToolTipText("Selecciona el Sexo");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("* Sexo");

        tfcorreo2.setToolTipText("Introduce el correo electrónico opcional");
        tfcorreo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcorreo2ActionPerformed(evt);
            }
        });

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("* Correo electrónico 1");

        tfcorreo1.setToolTipText("Introduce el correo electrónico");
        tfcorreo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcorreo1ActionPerformed(evt);
            }
        });

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Correo electrónico 2");

        btnguardarycontinuar.setText("Guardar y Continuar");
        btnguardarycontinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarycontinuarActionPerformed(evt);
            }
        });

        btneditar.setText("Limpiar campos");
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        tfdiscapacidad.setToolTipText("Introduce la discapacidad");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Discapacidad");

        tflenguaext.setToolTipText("Introduce la lengua extranjera");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("* Lengua Extranjera");

        jLabel21.setText("Nombre de la unidad Académica");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("CIECAS");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("* Nombre del Programa Académico de Posgrado");

        jdfechanaci.setToolTipText("Introduce la fecha de nacimiento");
        jdfechanaci.setDateFormatString("dd MM yyyy");
        jdfechanaci.setMinSelectableDate(new java.util.Date(-62135744288000L));

        jLabel24.setText("* Fecha de Nacimiento");

        SUBTITULO13.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO13.setText("REGISTRO DE LOS DATOS DEL SOLICITANTE");
        SUBTITULO13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jcprograma.setSelectedItem("");
        jcprograma.setAutoscrolls(true);
        jcprograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcprogramaActionPerformed(evt);
            }
        });

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("* Campos Obligatorios");
        jLabel40.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        cbxEstado.setSelectedIndex(-1);
        cbxEstado.setSelectedItem(-1);

        cbxPais.setSelectedIndex(-1);
        cbxPais.setSelectedItem(145);
        cbxPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPaisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfcp, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(47, 47, 47)
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(11, 11, 11)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbxPais, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tftelcasa, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tftelcelular, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(rbmasc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(rbfeme, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(336, 336, 336)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(334, 334, 334)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(72, 72, 72)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(123, 123, 123)
                                                        .addComponent(jLabel2))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(tfnom22, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(tfapep, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(tfapem, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jdfechanaci, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(38, 38, 38)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(73, 73, 73)
                                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(tfcalle, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(111, 111, 111)))
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(tfnoext)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(tfnoint)
                                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(tfcolonia, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(48, 48, 48)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tfmunicipioalca, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(30, 30, 30)
                                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfcorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(71, 71, 71)
                                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(tfcorreo2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(405, 405, 405)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(tfdiscapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tflenguaext, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(SUBTITULO3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(224, 224, 224)
                                .addComponent(SUBTITULO13, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(66, 66, 66))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(btneditar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnguardarycontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel21))
                .addGap(83, 83, 83)
                .addComponent(jcprograma, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(131, 131, 131))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SUBTITULO13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addGap(18, 18, 18)
                .addComponent(SUBTITULO3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfapem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfapep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfnom22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jdfechanaci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel24)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(tfcalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel6))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(tfnoext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfnoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfcolonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(tfmunicipioalca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(tfcp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(tftelcasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(tftelcelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbxPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel12))))
                                .addGap(1, 1, 1))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(rbmasc)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rbfeme))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfcorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfdiscapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfcorreo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(tflenguaext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel19))))
                        .addGap(38, 38, 38)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22))
                            .addComponent(jcprograma, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnguardarycontinuar)
                            .addComponent(btneditar)))
                    .addComponent(jLabel2))
                .addGap(22, 22, 22))
        );

        jDesktopPane1.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Datos del Solicitante", jDesktopPane1);

        jDesktopPane2.setBackground(new java.awt.Color(111, 27, 70));

        SUBTITULO5.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO5.setText("ANTECEDENTES ACADÉMICOS");
        SUBTITULO5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Nivel Licenciatura");

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Programa academico");

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Institución");

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("País / Estado");

        jLabel29.setText("Fecha de graduación");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Nivel Especialidad");

        rbespecialidad.setText("No Aplicable");
        rbespecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbespecialidadActionPerformed(evt);
            }
        });

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Programa academico");

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Institución");

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("País / Estado");

        jLabel34.setText("Fecha de graduación");

        JDfechagraduacion2.setDoubleBuffered(false);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Nivel Maestría");

        rbmaestria.setText("No Aplicable");
        rbmaestria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbmaestriaActionPerformed(evt);
            }
        });

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Programa academico");

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Institución");

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("País / Estado");

        jLabel39.setText("Fecha de graduación");

        btguardarantecedentes.setText("Guardar y Confirmar");
        btguardarantecedentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btguardarantecedentesActionPerformed(evt);
            }
        });

        SUBTITULO12.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO12.setText("SOLICITUD DE INSCRIPCIÓN AL PROGRAMA ACADÉMICO DE POSGRADO");
        SUBTITULO12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btguardarantecedentes1.setText("Limpiar Datos");
        btguardarantecedentes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btguardarantecedentes1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGap(378, 378, 378)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(370, 370, 370))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tfprograma3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(26, 26, 26)
                                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGap(28, 28, 28)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tfinstituto3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(26, 26, 26)
                                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(45, 45, 45)
                                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(73, 73, 73)
                                                    .addComponent(jLabel39))
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(tfpais3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(15, 15, 15)
                                                    .addComponent(JDfechagraduacion3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(352, 352, 352))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(rbmaestria)
                                            .addGap(240, 240, 240)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfprograma2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfinstituto2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addGap(87, 87, 87)
                                                        .addComponent(rbespecialidad))
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addGap(47, 47, 47)
                                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(71, 71, 71)
                                                .addComponent(jLabel34))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(tfpais2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)
                                                .addComponent(JDfechagraduacion2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(tfprograma1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(SUBTITULO5, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(28, 28, 28)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tfinstituto1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(26, 26, 26)
                                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(45, 45, 45)
                                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(73, 73, 73)
                                                    .addComponent(jLabel29))
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(tfpais1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(15, 15, 15)
                                                    .addComponent(JDfechagraduacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(172, 172, 172)
                                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGap(21, 21, 21)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(SUBTITULO12, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btguardarantecedentes1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(btguardarantecedentes, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(SUBTITULO12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDfechagraduacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(SUBTITULO5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfprograma1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel28))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfinstituto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfpais1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(25, 25, 25)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbespecialidad)
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfprograma2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDfechagraduacion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfinstituto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfpais2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbmaestria)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfprograma3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDfechagraduacion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfinstituto3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfpais3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btguardarantecedentes)
                    .addComponent(btguardarantecedentes1))
                .addGap(41, 41, 41))
        );

        jDesktopPane2.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Antecedentes Académicos", jDesktopPane2);

        jDesktopPane3.setBackground(new java.awt.Color(111, 27, 70));

        SUBTITULO6.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO6.setText("DOMICILIO DEL SOLICITANTE");
        SUBTITULO6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscar1ActionPerformed(evt);
            }
        });
        txtBuscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscar1KeyReleased(evt);
            }
        });

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Nombre");

        btneditarante1.setText("Buscar");
        btneditarante1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarante1ActionPerformed(evt);
            }
        });

        tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane1.setViewportView(tabla2);

        btneditarante2.setText("Consultar todos");
        btneditarante2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarante2ActionPerformed(evt);
            }
        });

        tabla1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla1.setMaximumSize(new java.awt.Dimension(0, 0));
        tabla1.setMinimumSize(new java.awt.Dimension(0, 0));
        tabla1.setPreferredSize(new java.awt.Dimension(0, 0));
        jScrollPane2.setViewportView(tabla1);

        SUBTITULO7.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO7.setText("EDITAR DATOS DEL SOLICITANTE");
        SUBTITULO7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        SUBTITULO8.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO8.setText("DATOS DEL SOLICITANTE");
        SUBTITULO8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        SUBTITULO9.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO9.setText("DOMICILIO DEL SOLICITANTE");
        SUBTITULO9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tabla3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tabla3);

        SUBTITULO10.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO10.setText("DISCAPACIDAD");
        SUBTITULO10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tabla4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane4.setViewportView(tabla4);

        SUBTITULO15.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO15.setText("LENGUA");
        SUBTITULO15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tabla5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane5.setViewportView(tabla5);

        SUBTITULO16.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SUBTITULO16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SUBTITULO16.setText("PROG. ACADÉMICO");
        SUBTITULO16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tabla6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane6.setViewportView(tabla6);

        btneditarante3.setText("Editar");
        btneditarante3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarante3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SUBTITULO9, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SUBTITULO10, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(SUBTITULO15, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(SUBTITULO8, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(SUBTITULO6, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btneditarante1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(SUBTITULO16, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 27, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btneditarante3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(285, Short.MAX_VALUE)
                    .addComponent(SUBTITULO7, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(93, 93, 93)
                    .addComponent(btneditarante2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(87, 87, 87)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneditarante1))
                .addGap(23, 23, 23)
                .addComponent(SUBTITULO8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SUBTITULO6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(SUBTITULO9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(SUBTITULO10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(SUBTITULO15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SUBTITULO16, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btneditarante3)))
                .addContainerGap(133, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SUBTITULO7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btneditarante2))
                    .addContainerGap(682, Short.MAX_VALUE)))
        );

        jDesktopPane3.setLayer(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane3Layout = new javax.swing.GroupLayout(jDesktopPane3);
        jDesktopPane3.setLayout(jDesktopPane3Layout);
        jDesktopPane3Layout.setHorizontalGroup(
            jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane3Layout.setVerticalGroup(
            jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("Editar Datos", jDesktopPane3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(824, 824, 824)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 942, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btneditarante3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarante3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btneditarante3ActionPerformed

    private void btneditarante2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarante2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btneditarante2ActionPerformed

    private void btneditarante1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarante1ActionPerformed
        // BOTON PARA COSULTAR Y EDITAR LOS DATOS DEL SOLICIANTE 
        //select * from solicitante WHERE Nom_Solic = 'JUAN' and AP_Solic = "Toriz" and AM_Solic = "CHVEZ";
        //mostrarDatos(txtBuscar1.getText());

    }//GEN-LAST:event_btneditarante1ActionPerformed

    private void btguardarantecedentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btguardarantecedentesActionPerformed

//nivel licenciatura
        //tranformar el tipo date con un formato correcto para la BD
        //nivel licenciatura
        java.sql.Date fechalic = new java.sql.Date(0, 0, 0);
        java.sql.Date fechaespe = new java.sql.Date(0, 0, 0);
        java.sql.Date fechamaes = new java.sql.Date(0, 0, 0);
        //Codigo para la conexion a la BD
        ConsultasMenuSQL menu = new ConsultasMenuSQL();

        //Nivel academico variable para usarse en la BD
        String nivelaca = "";
        int especialidad = 1;

        //Validacion del campo fecha
        try {
            if (!JDfechagraduacion1.getDate().toString().isEmpty() && JDfechagraduacion1.getDate() != null) {
                //Fecha lic
                Date date2 = JDfechagraduacion1.getDate();
                long d2 = date2.getTime();
                java.sql.Date fecha2 = new java.sql.Date(d2);
                fechalic = fecha2;
                System.out.println("fecha de licenciatura: " + fechalic);
                prueba1 = true;
                JDfechagraduacion1.setBackground(Color.white);

            }
        } catch (java.lang.NullPointerException ex) {
            System.out.println("Campo fecha vacio o null");
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FECHA VALIDA - LICENCIATURA");
            JDfechagraduacion1.setBackground(Color.red);
            prueba1 = false;
        }
//NIVEL LICENCIATURA

        if (tfprograma1.getText().isEmpty() || tfprograma1.getText().trim().equalsIgnoreCase("")
                || tfprograma1.getText().trim() == null || tfprograma1.getText().isBlank() || tfinstituto1.getText().isEmpty()
                || tfinstituto1.getText().trim().equalsIgnoreCase("")
                || tfinstituto1.getText().trim() == null || tfinstituto1.getText().isBlank()
                || tfpais1.getText().isEmpty() || tfpais1.getText().trim() == null || tfpais1.getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Por favor llene los datos en color rojo - LICENCIATURA");
            tfprograma1.setBackground(Color.red);
            tfinstituto1.setBackground(Color.red);
            tfpais1.setBackground(Color.red);
            prueba = false;
        } else if (!tfprograma1.getText().isEmpty() && !tfprograma1.getText().trim().equalsIgnoreCase("")
                && tfprograma1.getText().trim() != null && !tfinstituto1.getText().isEmpty()
                && !tfinstituto1.getText().trim().equalsIgnoreCase("")
                && tfinstituto1.getText().trim() != null
                && !tfpais1.getText().isEmpty() && tfpais1.getText().trim() != null && !tfpais1.getText().trim().equalsIgnoreCase("")) {
            tfprograma1.setBackground(Color.white);
            tfinstituto1.setBackground(Color.white);
            tfpais1.setBackground(Color.white);
            prueba = true;
        }

        //Validacion de RB de especialidad y maestria
        if (!rbespecialidad.isSelected()) {

            nivelaca = "Especialidad";
            especialidad = 2;
//Validacion del campo fecha
            try {
                if (!JDfechagraduacion2.getDate().toString().isEmpty() && JDfechagraduacion2.getDate() != null) {
                    //Fecha Espe
                    Date date3 = JDfechagraduacion2.getDate();
                    long d3 = date3.getTime();
                    java.sql.Date fecha3 = new java.sql.Date(d3);
                    fechaespe = fecha3;
                    System.out.println("fecha de licenciatura: " + fechaespe);
                    prueba4 = true;
                    JDfechagraduacion2.setBackground(Color.white);

                }
            } catch (java.lang.NullPointerException ex) {
                System.out.println("Campo fecha en lic vacio o null");
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FECHA VALIDA - ESPECIALIDAD");
                JDfechagraduacion2.setBackground(Color.red);
                prueba4 = false;
            }
//nivel especialidad

            //NIVEL ESPECIALIDAD
            if (tfprograma2.getText().isEmpty() || tfprograma2.getText().trim().equalsIgnoreCase("")
                    || tfprograma2.getText().trim() == null || tfprograma2.getText().isBlank() || tfinstituto2.getText().isEmpty()
                    || tfinstituto2.getText().trim().equalsIgnoreCase("")
                    || tfinstituto2.getText().trim() == null || tfinstituto2.getText().isBlank()
                    || tfpais2.getText().isEmpty() || tfpais2.getText().trim() == null || tfpais2.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Por favor llene los datos en color rojo - ESPECIALIDAD");
                tfprograma2.setBackground(Color.red);
                tfinstituto2.setBackground(Color.red);
                tfpais2.setBackground(Color.red);
                prueba3 = false;
                tfprograma2.setText("Sin Datos");
                tfinstituto2.setText("Sin Datos");
                tfpais2.setText("Sin Datos");

            } else if (!tfprograma2.getText().isEmpty() && !tfprograma2.getText().trim().equalsIgnoreCase("")
                    && tfprograma2.getText().trim() != null && !tfinstituto2.getText().isEmpty()
                    && !tfinstituto2.getText().trim().equalsIgnoreCase("")
                    && tfinstituto2.getText().trim() != null
                    && !tfpais2.getText().isEmpty() && tfpais2.getText().trim() != null && !tfpais2.getText().trim().equalsIgnoreCase("")) {
                {
                    tfprograma2.setBackground(Color.white);
                    tfinstituto2.setBackground(Color.white);
                    tfpais2.setBackground(Color.white);
                    prueba3 = true;
                }

            }
        }
        if (!rbmaestria.isSelected()) {
            //tranformar el tipo date con un formato correcto para la BD
            //nivel maestria
            nivelaca = "Maestria";
            especialidad = 3;
            //Validacion del campo fecha
            try {
                if (!JDfechagraduacion3.getDate().toString().isEmpty() && JDfechagraduacion3.getDate() != null) {
                    //Fecha Maes
                    Date date4 = JDfechagraduacion3.getDate();
                    long d4 = date4.getTime();
                    java.sql.Date fecha4 = new java.sql.Date(d4);
                    fechamaes = fecha4;
                    System.out.println("fecha de maestria: " + fechamaes);
                    prueba6 = true;
                    JDfechagraduacion3.setBackground(Color.white);

                }
            } catch (java.lang.NullPointerException ex) {
                System.out.println("Campo fecha vacio o null");
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FECHA VALIDA - MAESTRIA"); //1899-12-31
                JDfechagraduacion3.setBackground(Color.red);
                prueba6 = false;
            }

            //NIVEL MAESTRIA
            if (tfprograma3.getText().isEmpty() || tfprograma3.getText().trim().equalsIgnoreCase("")
                    || tfprograma3.getText().trim() == null || tfprograma3.getText().isBlank() || tfinstituto3.getText().isEmpty()
                    || tfinstituto3.getText().trim().equalsIgnoreCase("")
                    || tfinstituto3.getText().trim() == null || tfinstituto3.getText().isBlank()
                    || tfpais3.getText().isEmpty() || tfpais3.getText().trim() == null || tfpais3.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Por favor llene los datos en color rojo - MAESTRIA");
                tfprograma3.setBackground(Color.red);
                tfinstituto3.setBackground(Color.red);
                tfpais3.setBackground(Color.red);
                prueba5 = false;
                tfprograma3.setText("Sin Datos");
                tfinstituto3.setText("Sin Datos");
                tfpais3.setText("Sin Datos");

            } else if (!tfprograma3.getText().isEmpty() && !tfprograma3.getText().trim().equalsIgnoreCase("")
                    && tfprograma3.getText().trim() != null && !tfinstituto3.getText().isEmpty()
                    && !tfinstituto3.getText().trim().equalsIgnoreCase("")
                    && tfinstituto3.getText().trim() != null
                    && !tfpais3.getText().isEmpty() && tfpais3.getText().trim() != null && !tfpais3.getText().trim().equalsIgnoreCase("")) {
                tfprograma3.setBackground(Color.white);
                tfinstituto3.setBackground(Color.white);
                tfpais3.setBackground(Color.white);
                prueba5 = true;
            }

        }
        System.out.println("Prueba valor: " + prueba);
        System.out.println("Prueba1 valor: " + prueba1);
        System.out.println("Prueba2 valor: " + prueba2);
        System.out.println("Prueba3 valor: " + prueba3);
        System.out.println("Prueba4 valor: " + prueba4);
        System.out.println("Prueba5 valor: " + prueba5);
        System.out.println("Prueba6 valor: " + prueba6);
        if (prueba && prueba1 && prueba2 && prueba3 && prueba4 && prueba5 && prueba6) {

            confirmado = JOptionPane.showConfirmDialog(rootPane, "CONFIRMACIÓN DE DATOS:" + "\n"
                    + "*************NIVEL LICENCIATURA*************" + "\n"
                    + "Programa Académico:" + tfprograma1.getText() + "\n"
                    + "Instituación: " + tfinstituto1.getText() + "\n"
                    + "País / Estado: " + tfpais1.getText() + "\n"
                    + "Fecha de Graduación: " + fechalic + "\n"
                    + "*************NIVEL ESPECIALIDAD*************" + "\n"
                    + "Programa Académico:" + tfprograma2.getText() + "\n"
                    + "Instituación: " + tfinstituto2.getText() + "\n"
                    + "País / Estado: " + tfpais2.getText() + "\n"
                    + "*************NIVEL MAESTRIA*************" + "\n"
                    + "Programa Académico:" + tfprograma3.getText() + "\n"
                    + "Instituación: " + tfinstituto3.getText() + "\n"
                    + "País / Estado: " + tfpais3.getText() + "\n"
            );

            System.out.println("Entro por datos correctos");

            if (JOptionPane.OK_OPTION == confirmado) {
            }
            /*menu.antecedentes(tfprograma1.getText(), tfinstituto1.getText(), tfpais1.getText(), fechalic,
                    tfprograma2.getText(), tfinstituto2.getText(), tfpais2.getText(), fechaespe,
                    tfprograma3.getText(), tfinstituto3.getText(), tfpais3.getText(), fechamaes, nivelaca, especialidad);
             */

        }

    }//GEN-LAST:event_btguardarantecedentesActionPerformed

    private void rbmaestriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbmaestriaActionPerformed
        if (rbmaestria.isSelected()) {

            tfinstituto3.enable(false);
            tfprograma3.enable(false);
            tfpais3.enable(false);
            JDfechagraduacion3.setEnabled(false);

            tfinstituto3.setText("");
            tfprograma3.setText("");
            tfpais3.setText("");
            JDfechagraduacion3.setDate(null);
            //tfestado2.setText(JDfechagraduacion2.getCalendar());

        } else if (rbmaestria.isSelected() == false) {
            tfinstituto3.enable(true);
            tfprograma3.enable(true);
            tfpais3.enable(true);
            JDfechagraduacion3.setEnabled(true);
        }
    }//GEN-LAST:event_rbmaestriaActionPerformed

    private void rbespecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbespecialidadActionPerformed
        if (rbespecialidad.isSelected()) {

            tfinstituto2.enable(false);
            tfprograma2.enable(false);
            tfpais2.enable(false);
            JDfechagraduacion2.setEnabled(false);

            tfinstituto2.setText("");
            tfprograma2.setText("");
            tfpais2.setText("");
            JDfechagraduacion2.setDate(null);
            //tfestado2.setText(JDfechagraduacion2.getCalendar());

        } else if (rbespecialidad.isSelected() == false) {
            tfinstituto2.enable(true);
            tfprograma2.enable(true);
            tfpais2.enable(true);
            JDfechagraduacion2.setEnabled(true);

        }
    }//GEN-LAST:event_rbespecialidadActionPerformed

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        tfnom22.setText("");
        tfapep.setText("");
        tfapem.setText("");
        tfcalle.setText("");
        tfnoext.setText("");
        tfnoint.setText("");
        tfcolonia.setText("");
        tfmunicipioalca.setText("");
        tfcp.setText("");
        tftelcasa.setText("");
        tftelcelular.setText("");
        rbmasc.setText("");
        rbfeme.setText("");
        tfcorreo1.setText("");
        tfcorreo2.setText("");
        tfdiscapacidad.setText("");
        tflenguaext.setText("");
        jdfechanaci.setDateFormatString("");
        jcprograma.setSelectedIndex(-1);
        cbxPais.setSelectedIndex(-1);
        cbxEstado.setSelectedIndex(-1);
        test = false;
        test1 = false;
        test2 = false; //[255,255,255]
        test3 = false;
        test4 = false;
        testnum = false;
        //Volver a poner los BG de blanco

        tfnom22.setBackground(Color.white);
        tfapep.setBackground(Color.white);
        tfapem.setBackground(Color.white);
        tfcalle.setBackground(Color.white);
        tfnoext.setBackground(Color.white);
        tfnoint.setBackground(Color.white);
        tfcolonia.setBackground(Color.white);
        tfmunicipioalca.setBackground(Color.white);
        tfcp.setBackground(Color.white);
        cbxEstado.setBackground(Color.white);
        cbxPais.setBackground(Color.white);
        tftelcasa.setBackground(Color.white);
        tftelcelular.setBackground(Color.white);
        rbmasc.setBackground(Color.white);
        rbfeme.setBackground(Color.white);;
        tfcorreo1.setBackground(Color.white);
        tfcorreo2.setBackground(Color.white);
        tfdiscapacidad.setBackground(Color.white);
        tflenguaext.setBackground(Color.white);
        jdfechanaci.setBackground(Color.white);
        jcprograma.setBackground(Color.white);
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnguardarycontinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarycontinuarActionPerformed
        //boton de guardar y continuar
        //boton para elegir el sexo
        int sexo = 0;
        String Sexo = null;

        //variables para convertir un string a un entero
        int telcasa = 0;
        int telcelular = 0;
        String numeroEXT = "S/N";
        String numeroINT = "S/N";
        String telecasa = "S/N";
        String telecel = "S/N";

        java.sql.Date fecha = new java.sql.Date(0, 0, 0);

        if (rbmasc.isSelected()) {
            sexo = 1; //hombre
            Sexo = "Hombre";
        }
        if (rbfeme.isSelected()) {
            sexo = 0; //mujer
            Sexo = "Mujer";
        }
//Validacion del campo fecha
        try {
            if (!jdfechanaci.getDate().toString().isEmpty() && jdfechanaci.getDate() != null) {
                Date date = jdfechanaci.getDate();
                long d = date.getTime();
                java.sql.Date fecha2 = new java.sql.Date(d);
                System.out.println("fecha" + fecha2);
                fecha = fecha2;
                test = true;

            }
        } catch (java.lang.NullPointerException ex) {
            System.out.println("Campo fecha vacio o null");
            JOptionPane.showMessageDialog(null, "El campo Fecha de Nacimiento " + "\n" + "esta vacio por favor ingrese una fecha");
            jdfechanaci.setBackground(Color.red);
        }

        //Codigo para la conexion a la BD
        ConsultasMenuSQL menu = new ConsultasMenuSQL();
        //convertir un string a entero
        //cp = Integer.parseInt(tfcp.getText());

        try {
            //Validacion de # interior y exterior before(fechaprueba)
            if (tfnoext.getText().isEmpty() || tfnoext.getText().trim().equalsIgnoreCase("") || tfnoext.getText().trim() == null || tfnoext.getText().isBlank()) {
                numeroEXT = "S/N";

            } else if (!tfnoext.getText().isEmpty() && !tfnoext.getText().trim().equalsIgnoreCase("") && tfnoext.getText().trim() != null) {
                boolean validar = true;
                try {
                    noEXT = Integer.parseInt(tfnoext.getText());
                } catch (java.lang.NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El campo # Ext debe ser numerico");
                    testnum = false;
                    validar = false;
                    tfnoext.setBackground(Color.red);
                }
                if (validar) {
                    numeroEXT = tfnoext.getText();
                    testnum = true;
                    tfnoext.setBackground(Color.white);
                }

            }
            if (tfnoint.getText().isEmpty() || tfnoint.getText().trim().equalsIgnoreCase("") || tfnoint.getText().trim() == null || tfnoint.getText().isBlank()) {
                numeroINT = "S/N";
            } else if (!tfnoint.getText().isEmpty() && !tfnoint.getText().trim().equalsIgnoreCase("") && tfnoint.getText().trim() != null) {
                boolean validar = true;
                try {
                    noINT = Integer.parseInt(tfnoint.getText());
                } catch (java.lang.NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El campo # INT debe ser numerico");
                    testnum1 = false;
                    validar = false;
                    tfnoint.setBackground(Color.red);
                }
                if (validar) {
                    numeroINT = tfnoint.getText();
                    testnum1 = true;
                    tfnoint.setBackground(Color.white);
                }
            }
            if (tfnom22.getText().isEmpty() || tfnom22.getText() == null || tfapep.getText().isEmpty() || tfapep.getText() == null
                    || tfapem.getText().isEmpty() || tfapem.getText() == null) {

                JOptionPane.showMessageDialog(null, "Por favor llene los campos del soliciante correctamente");
                tfnom22.setBackground(Color.red);
                tfapep.setBackground(Color.red);
                tfapem.setBackground(Color.red);
                jdfechanaci.setBackground(Color.red);
                test1 = false;
            } else {
                test1 = true;
            }

            //validamos si va vacio o nula cada uno de los campos
            if (tfcalle.getText().isEmpty() || tfcalle.getText() == null || tfcolonia.getText().isEmpty() || tfcolonia.getText() == null
                    || tfmunicipioalca.getText().isEmpty() || tfmunicipioalca.getText() == null || tfcp.getText().isEmpty() || tfcp.getText() == null
                    || cbxEstado.getSelectedItem().toString().isEmpty() || cbxEstado.getSelectedItem().toString() == null || cbxPais.getSelectedItem().toString().isEmpty() || cbxPais.getSelectedItem().toString() == null) {

                JOptionPane.showMessageDialog(null, "Por favor llene los campos la dirección correctamente");
                tfcalle.setBackground(Color.red);
                tfcolonia.setBackground(Color.red);
                tfmunicipioalca.setBackground(Color.red);
                tfcp.setBackground(Color.red);
                cbxEstado.setBackground(Color.red);
                cbxPais.setBackground(Color.red);
                tfnoext.setBackground(Color.red);
                tfnoint.setBackground(Color.red);
                test2 = false;

            } else {

                //validacion de CP
                boolean validar = true;
                try {
                    cp = Integer.parseInt(tfcp.getText());
                } catch (java.lang.NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El campo CP debe ser numerico");
                    testnum2 = false;
                    validar = false;
                    tfcp.setBackground(Color.red);
                }
                if (validar) {
                    test2 = true;
                    testnum2 = true;
                    tfcp.setBackground(Color.white);
                }

            }
            if (tfcorreo1.getText().isEmpty() || tfcorreo1.getText() == null || tfcp.getText().isEmpty() || tfcp.getText() == null) {

                JOptionPane.showMessageDialog(null, "Por favor llene los campos de contacto correctamente");
                tfcp.setBackground(Color.red);
                tfcorreo1.setBackground(Color.red);
                test3 = false;
            } else {
                //validacion unicamente para numero de casa
                if (tftelcasa.getText().isEmpty() || tftelcasa.getText().trim().equalsIgnoreCase("") || tftelcasa.getText().trim() == null) {
                    telecasa = "S/N";

                } else if (!tftelcasa.getText().isEmpty() && !tftelcasa.getText().trim().equalsIgnoreCase("") && tftelcasa.getText().trim() != null) {
                    boolean validar = true;
                    try {
                        telCASA = Integer.parseInt(tftelcasa.getText().trim());
                    } catch (java.lang.NumberFormatException ex) {
                        testnum3 = false;
                        validar = false;
                        test3 = false;
                        JOptionPane.showMessageDialog(null, "El campo TEL CASA debe ser numerico");
                        System.out.println("el valor fuera del if es CASA : " + validar);
                        tftelcasa.setBackground(Color.red);

                    }
                    if (validar) {
                        telecasa = tftelcasa.getText();
                        System.out.println("el valo dentro del if es : " + validar);
                        System.out.println("el valo de tel casa: " + telecasa);
                        testnum3 = true;
                        test3 = true;
                        tftelcasa.setBackground(Color.white);
                    }

                }
                //validacion unicamente para numero de celular
                if (tftelcelular.getText().isEmpty() || tftelcelular.getText().trim().equalsIgnoreCase("") || tftelcelular.getText().trim() == null) {
                    telecel = "S/N";

                } else if (!tftelcelular.getText().isEmpty() && !tftelcelular.getText().trim().equalsIgnoreCase("") && tftelcelular.getText().trim() != null) {
                    boolean validar = true;
                    try {
                        telCELULAR = Integer.parseInt(tftelcelular.getText().trim());
                    } catch (java.lang.NumberFormatException ex) {
                        testnum4 = false;
                        validar = false;
                        test3 = false;
                        JOptionPane.showMessageDialog(null, "El campo TEL CELULAR debe ser numerico");
                        tftelcelular.setBackground(Color.red);

                        System.out.println("el valor fuera del if es CELU : " + validar);

                    }
                    if (validar) {
                        tftelcelular.setBackground(Color.white);
                        telecel = tftelcelular.getText();
                        System.out.println("el valo de tel cel: " + telecel);
                        System.out.println("el valo dentro del if es : " + validar);
                        testnum4 = true;
                        test3 = true;
                    }

                }

            }
            if (tflenguaext.getText().isEmpty() || tflenguaext.getText() == null) {
                tflenguaext.setBackground(Color.red);
                test4 = false;
            } else {
                test4 = true;
            }
            if (jcprograma.getSelectedIndex() == -1) {
                System.out.println("El index es igual a -1");
                JOptionPane.showMessageDialog(null, "Por favor llene el programa academico");
                jcprograma.setBackground(Color.red);
                test5 = false;
            } else {
                test5 = true;
            }
            //menu.especialidadJC(jcprograma.getSelectedItem().toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Por favor llene los campos de color rojo, correctamente");
        }

        //Validamos que todas nuestros datos vayan con datos
        if (test && test1 && test2 && test3 && test4 && test5 && testnum && testnum1 && testnum2 && testnum3 && testnum4) {

            int confirmado = JOptionPane.showConfirmDialog(rootPane, "CONFIRMACIÓN DE DATOS:" + "\n"
                    + "*************DATOS DEL SOLICITANTE*************" + "\n"
                    + "Nombre:" + tfnom22.getText() + "\n"
                    + "Apellido Paterno: " + tfapep.getText() + "\n"
                    + "Apellido Materno: " + tfapem.getText() + "\n"
                    + "Fecha de Nacimiento: " + fecha + "\n"
                    + "Sexo: " + Sexo + "\n"
                    + "*******************DOMICILIO*******************" + "\n"
                    + "Calle: " + tfcalle.getText() + "\n"
                    + "# Ext: " + numeroEXT + "\n"
                    + "# Int: " + numeroINT + "\n"
                    + "Colonia: " + tfcolonia.getText() + "\n"
                    + "Municipio / Alcaldía: " + tfmunicipioalca.getText() + "\n"
                    + "C.P: " + tfcp.getText() + "\n"
                    + "Estado: " + cbxEstado.getSelectedItem().toString() + "\n"
                    + "País: " + cbxPais.getSelectedItem().toString() + "\n"
                    + "Tel. Casa: " + telecasa + "\n"
                    + "Tel. Celular: " + telecel + "\n"
                    + "******************DISCAPACIDAD*****************" + "\n"
                    + "Discapacidad: " + tfdiscapacidad.getText() + "\n"
                    + "***************LENGUAJE / IDIOMA***************" + "\n"
                    + "Lengua: " + tflenguaext.getText() + "\n"
                    + "***************PROGRAMA ACADÉMICO**************" + "\n"
                    + "Programa: " + jcprograma.getSelectedItem().toString());

            System.out.println("Entro por datos correctos");

            if (JOptionPane.OK_OPTION == confirmado) {
                System.out.println("Se le dio confirmado");
                //inyectamos los datos a las tablas - Pasamos los parametros de nuestras variables al metodo de la clase ConsultasMenuSQL
                menu.solicitante(tfnom22.getText(), tfapep.getText(), tfapem.getText(), tfcalle.getText(), numeroEXT, numeroINT, tfcolonia.getText(),
                        tfmunicipioalca.getText(), tfcp.getText(), cbxEstado.getSelectedItem().toString(), cbxPais.getSelectedItem().toString(), telecasa, telecel,
                        sexo, tfcorreo1.getText(), tfcorreo2.getText(), tfdiscapacidad.getText(), tflenguaext.getText(), jcprograma.getSelectedItem().toString(), fecha, jcprograma.getSelectedItem().toString());

            } else {
                System.out.println("Le dio clic en No o en cancelar");
            }
        }
        //Pasamos los parametros de nuestras variables al metodo de la clase ConsultasMenuSQL
        /* 
        //jcprograma FALTA MODIFICAR */
 /*
        //Codigo para desabilitar los campos despues de guardar
        tfcolonia.enable(false);
        tfnom22.enable(false);
        tfapep.enable(false);
        tfapem.enable(false);
        tfcalle.enable(false);
        tfnoext.enable(false);
        tfnoint.enable(false);
        tfmunicipioalca.enable(false);
        tfcp.enable(false);
        tfestado.enable(false);
        tfpais.enable(false);
        tftelcasa.enable(false);
        tftelcelular.enable(false);
        rbmasc.setEnabled(false);
        rbfeme.setEnabled(false);
        tfcorreo1.enable(false);
        tfcorreo2.enable(false);
        tfdiscapacidad.enable(false);
        tflenguaext.enable(false);
        jcprograma.enable(false);
        jdfechanaci.setEnabled(false);
         */
    }//GEN-LAST:event_btnguardarycontinuarActionPerformed

    private void tfcorreo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcorreo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcorreo1ActionPerformed

    private void tfcorreo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcorreo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcorreo2ActionPerformed

    private void rbmascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbmascActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbmascActionPerformed

    private void tftelcelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tftelcelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tftelcelularActionPerformed

    private void tftelcasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tftelcasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tftelcasaActionPerformed

    private void tfcpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcpActionPerformed

    private void tfmunicipioalcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfmunicipioalcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfmunicipioalcaActionPerformed

    private void tfcoloniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcoloniaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcoloniaActionPerformed

    private void tfnointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnointActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfnointActionPerformed

    private void tfnoextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnoextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfnoextActionPerformed

    private void tfcalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcalleActionPerformed

    private void tfapemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfapemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfapemActionPerformed

    private void tfapepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfapepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfapepActionPerformed

    private void tfnom22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnom22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfnom22ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jcprogramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcprogramaActionPerformed

    }//GEN-LAST:event_jcprogramaActionPerformed

    private void cbxPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxPaisActionPerformed

    private void btguardarantecedentes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btguardarantecedentes1ActionPerformed

        tfinstituto1.setText("");
        tfprograma1.setText("");
        tfpais1.setText("");
        JDfechagraduacion1.setDate(null);

        tfinstituto2.setText("");
        tfprograma2.setText("");
        tfpais2.setText("");
        JDfechagraduacion2.setDate(null);

        tfinstituto3.setText("");
        tfprograma3.setText("");
        tfpais3.setText("");
        JDfechagraduacion3.setDate(null);

        //variables para validar datos
        prueba = false;
        prueba1 = false;
        prueba2 = false;
        prueba3 = false;
        prueba4 = false;
        prueba5 = false;
        prueba6 = false;
        //Volver a poner los BG de blanco

        tfinstituto1.setBackground(Color.white);
        tfprograma1.setBackground(Color.white);
        tfpais1.setBackground(Color.white);
        JDfechagraduacion1.setBackground(Color.white);

        tfinstituto2.setBackground(Color.white);
        tfprograma2.setBackground(Color.white);
        tfpais2.setBackground(Color.white);
        JDfechagraduacion2.setBackground(Color.white);

        tfinstituto3.setBackground(Color.white);
        tfprograma3.setBackground(Color.white);
        tfpais3.setBackground(Color.white);
        JDfechagraduacion3.setBackground(Color.white);

    }//GEN-LAST:event_btguardarantecedentes1ActionPerformed

    private void txtBuscar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar1KeyReleased
       String prueba12="a";
        filtrarDatosSolicitud(prueba12,tabla1); 
        mostrarDatos();
         
    }//GEN-LAST:event_txtBuscar1KeyReleased

    private void txtBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar1ActionPerformed

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
            java.util.logging.Logger.getLogger(menuprueba.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menuprueba.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menuprueba.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuprueba.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menuprueba().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser JDfechagraduacion1;
    private com.toedter.calendar.JDateChooser JDfechagraduacion2;
    private com.toedter.calendar.JDateChooser JDfechagraduacion3;
    private javax.swing.JLabel SUBTITULO10;
    private javax.swing.JLabel SUBTITULO12;
    private javax.swing.JLabel SUBTITULO13;
    private javax.swing.JLabel SUBTITULO15;
    private javax.swing.JLabel SUBTITULO16;
    private javax.swing.JLabel SUBTITULO3;
    private javax.swing.JLabel SUBTITULO5;
    private javax.swing.JLabel SUBTITULO6;
    private javax.swing.JLabel SUBTITULO7;
    private javax.swing.JLabel SUBTITULO8;
    private javax.swing.JLabel SUBTITULO9;
    private javax.swing.JButton btguardarantecedentes;
    private javax.swing.JButton btguardarantecedentes1;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneditarante1;
    private javax.swing.JButton btneditarante2;
    private javax.swing.JButton btneditarante3;
    private javax.swing.JButton btnguardarycontinuar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxPais;
    private javax.swing.JButton jButton1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JComboBox<String> jcprograma;
    private com.toedter.calendar.JDateChooser jdfechanaci;
    private javax.swing.JRadioButton rbespecialidad;
    private javax.swing.JRadioButton rbfeme;
    private javax.swing.JRadioButton rbmaestria;
    private javax.swing.JRadioButton rbmasc;
    private javax.swing.JTable tabla1;
    private javax.swing.JTable tabla2;
    private javax.swing.JTable tabla3;
    private javax.swing.JTable tabla4;
    private javax.swing.JTable tabla5;
    private javax.swing.JTable tabla6;
    private javax.swing.JTextField tfapem;
    private javax.swing.JTextField tfapep;
    private javax.swing.JTextField tfcalle;
    private javax.swing.JTextField tfcolonia;
    private javax.swing.JTextField tfcorreo1;
    private javax.swing.JTextField tfcorreo2;
    private javax.swing.JTextField tfcp;
    private javax.swing.JTextField tfdiscapacidad;
    private javax.swing.JTextField tfinstituto1;
    private javax.swing.JTextField tfinstituto2;
    private javax.swing.JTextField tfinstituto3;
    private javax.swing.JTextField tflenguaext;
    private javax.swing.JTextField tfmunicipioalca;
    private javax.swing.JTextField tfnoext;
    private javax.swing.JTextField tfnoint;
    private javax.swing.JTextField tfnom22;
    private javax.swing.JTextField tfpais1;
    private javax.swing.JTextField tfpais2;
    private javax.swing.JTextField tfpais3;
    private javax.swing.JTextField tfprograma1;
    private javax.swing.JTextField tfprograma2;
    private javax.swing.JTextField tfprograma3;
    private javax.swing.JTextField tftelcasa;
    private javax.swing.JTextField tftelcelular;
    private javax.swing.JTextField txtBuscar1;
    // End of variables declaration//GEN-END:variables
}
