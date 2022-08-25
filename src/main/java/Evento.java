
import com.toedter.calendar.JTextFieldDateEditor;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
//Importa las librerias necesarias de itext5
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.ImageIcon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author estme
 */
public class Evento extends javax.swing.JFrame {

    /**
     * Creates new form Administracion
     */
    conexionSQL cc = new conexionSQL();
    Connection con = cc.conexion();

    public Evento() {
        initComponents();
        this.setLocationRelativeTo(null);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdFechaEvento.getDateEditor();
        editor.setEditable(false);
        mostrarDatos();
    }
    //Método para guardar la información e imagenes de un evento
    public void guardarEvento(String[] datos) {
        //Asigna la consulta a una cadena
        String SQL = "CALL `sp_guardar_evento`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            //Prepara y ejecuta la consulta
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, datos[3]);
            ps.setString(2, datos[4]);
            ps.setString(3, "0");
            ps.setString(4, datos[0]);
            ps.setString(5, datos[1]);
            ps.setString(6, datos[2]);
            ps.setString(7, datos[5]);
            ps.setString(8, datos[6]);
            ps.setString(9, datos[7]);
            ps.setString(10, datos[8]);

            int res = ps.executeUpdate();

            String[] split = datos[9].split(",");
            File file = null;
            FileInputStream fi = null;
            int res2 = 0;
            try {
                for (int i = 0; i < split.length; i++) {
                    System.out.println(split[i]);
                    file = new File(split[i]);
                    fi = new FileInputStream(file);
                    System.out.println(fi);
                    SQL = "CALL `sp_guardar_imagen`(?)";
                    ps = con.prepareStatement(SQL);
                    ps.setBinaryStream(1, fi);
                    res2 = ps.executeUpdate();

                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar una Imagen");
            }

            if (res > 0 && res2 > 0) {
                JOptionPane.showMessageDialog(null, "Evento Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
        }
        mostrarDatos();

    }
    //Filtra los eventos que aparecen en la tabla de acuerdo a su nombre, nombre del organizador o institución de procedencia
    public void filtrarDatosEventos(String valor) {

        String[] titulos = {"ID del Evento", "Nombre del Evento", "Organizador", "Institución de Procedencia"};
        String[] registros = new String[4];

        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        String SQL = "select e.ID_evento, e.Nom_evento, o.Nom_Organi, o.InP_Organi from evento e INNER JOIN organizador_evento o on e.ID_evento = o.ID_evento where concat(Nom_Organi, ' ',Nom_evento,' ', InP_Organi) like '%" + valor + "%'";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                registros[0] = rs.getString("ID_evento");
                registros[1] = rs.getString("Nom_evento");
                registros[2] = rs.getString("Nom_Organi");
                registros[3] = rs.getString("InP_Organi");
                modelo.addRow(registros);

            }

            tablaEventos.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos" + ex.getMessage());
        }

    }
    
    //Llena la tabla correspondiente a los eventos almacenados en la Base de datos
    public void mostrarDatos() {

        String[] titulos = {"ID del Evento", "Nombre del Evento", "Organizador", "Institución de Procedencia"};
        String[] registros = new String[4];

        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        String SQL = "select e.ID_evento, e.Nom_evento, o.Nom_Organi, o.InP_Organi from evento e INNER JOIN organizador_evento o on e.ID_evento = o.ID_evento ";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {

                registros[0] = rs.getString("ID_evento");
                registros[1] = rs.getString("Nom_evento");
                registros[2] = rs.getString("Nom_Organi");
                registros[3] = rs.getString("InP_Organi");
                modelo.addRow(registros);

            }

            tablaEventos.setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al Mostrar Datos" + ex.getMessage());
        }

    }
    //Muestra información mas detallada de un evento a traves de un JOptionPane
    public void mostrarInformacionEvento(String valorID) {
        try {

            String[] registros = new String[9];

            String SQL = "select e.Fch_evento, e.Sts_evento, i.Tip_infoento, i.Obj_infoento, i.Efe_infoento, i.Dsc_infoento, o.Nom_Organi, o.InP_Organi, o.Tip_Part from"
                    + " evento e inner join informacion_evento i  on e.ID_evento = i.ID_evento inner join organizador_evento o on e.ID_evento = o.ID_evento"
                    + " where e.ID_evento = ?";

            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, valorID.trim());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                registros[0] = rs.getString("e.Fch_evento");
                registros[1] = rs.getString("e.Sts_evento");
                registros[2] = rs.getString("i.Tip_infoento");
                registros[3] = rs.getString("i.Obj_infoento");
                registros[4] = rs.getString("i.Efe_infoento");
                registros[5] = rs.getString("i.Dsc_infoento");
                registros[6] = rs.getString("o.Nom_Organi");
                registros[7] = rs.getString("o.InP_Organi");
                registros[8] = rs.getString("o.Tip_Part");

            }

            JOptionPane.showMessageDialog(null,
                    "Fecha del evento: " + registros[0]
                    + "\nEstado del evento: " + conocerEstado(registros[1])
                    + "\nTipo de evento: " + registros[2]
                    + "\nObjetivo del evento: " + registros[3]
                    + "\nEfe del evento: " + registros[4]
                    + "\nDescripción del evento: " + registros[5]
                    + "\nDatos del Organizador"
                    + "\nNombre del Organizador: " + registros[6]
                    + "\nInstitución de procedencia: " + registros[7]
                    + "\nTipo de participane: " + registros[8], "DATOS DEL EVENTO", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
        }

    }
    //Genera un pdf del evento con la información almacenada en la Base de datos
    public void generarPdf(String valorID) {
        Document documento = new Document();
        String[] registros = new String[11];
        int numeroImagenes = 0;

        String SQL = "select e.ID_evento,e.Nom_evento,e.Fch_evento, e.Sts_evento, i.Tip_infoento, i.Obj_infoento, i.Efe_infoento, i.Dsc_infoento, o.Nom_Organi, o.InP_Organi, o.Tip_Part from"
                + " evento e inner join informacion_evento i  on e.ID_evento = i.ID_evento inner join organizador_evento o on e.ID_evento = o.ID_evento"
                + " where e.ID_evento = ?";
        String SQL2 = "select imagen from imagenes_eventos where ID_evento = ?";

        try {

            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, valorID.trim());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                registros[0] = rs.getString("e.ID_evento");
                registros[1] = rs.getString("e.Nom_evento");
                registros[2] = rs.getString("e.Fch_evento");
                registros[3] = rs.getString("e.Sts_evento");
                registros[4] = rs.getString("i.Tip_infoento");
                registros[5] = rs.getString("i.Obj_infoento");
                registros[6] = rs.getString("i.Efe_infoento");
                registros[7] = rs.getString("i.Dsc_infoento");
                registros[8] = rs.getString("o.Nom_Organi");
                registros[9] = rs.getString("o.InP_Organi");
                registros[10] = rs.getString("o.Tip_Part");

            }
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Documents/Reporte_EventoID_" + registros[0] + ".pdf"));
            Image header = Image.getInstance("src/main/java/imagenes/header.png");
            header.scaleToFit(500, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);

            Paragraph parrafo = new Paragraph();
                        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.setFont(FontFactory.getFont("Arial", 16, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Instituto Politécnico Nacional\n\n");
            parrafo.add("Centro de Investigaciones Económicas, Administrativas y Sociales\n\n");
            parrafo.add("Reporte de evento\n\n");
            parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Datos del Evento\n\nID del Evento: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[0] + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Nombre del Evento: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[1] + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Fecha del Evento: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[2] + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Estado del Evento: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(conocerEstado(registros[3]) + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Tipo de Evento: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[4] + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Objetivo del Evento: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[5] + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("EFE del Evento: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[6] + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Descripción del Evento:\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[7] + "\n\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Datos del organizador\n\nNombre del organizador: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[8] + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Institución de procedencia: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[9] + "\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Tipo de participante: ");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK));
            parrafo.add(registros[10] + "\n\n\n\n\n\n\n\n\n\n\n\n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
            parrafo.add("Imagenes del evento\n\n");

            pst = con.prepareStatement(SQL2);
            pst.setString(1, valorID.trim());
            rs = pst.executeQuery();

            documento.open();
            documento.add(header);
            documento.add(parrafo);
            if (rs.next()) {

                do {
                    int contador = 1;
                    byte[] img = rs.getBytes("imagen");
                    Image imagen = Image.getInstance(img);
                    imagen.scaleToFit(450, 1000);
                    imagen.setAlignment(Chunk.ALIGN_CENTER);
                    documento.add(imagen);
                    System.out.println(contador);
                    contador++;
                } while (rs.next());

            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte creado.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage());
        }

    }
    //Limpia los JTextField
    public void limpiar() {

        txtNomOrganizador.setText(null);
        txtInsProcedencia.setText(null);
        txtNomEvento.setText(null);
        txtObjetivo.setText(null);
        txtDesEvento.setText(null);
        txtImportarImagen.setText(null);

    }
    //Limpia el TextField que almacena las rutas de las imagenes
    public void limpiarImagen() {
        txtImportarImagen.setText(null);
    }
    //Regresa el estado de un evento a través de una cadena
    public String conocerEstado(String estado) {
        int numero = Integer.parseInt(estado);
        if (numero == 0) {
            return "Sin aprobar";
        } else {
            return "Aprobado";
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

        jToggleButton1 = new javax.swing.JToggleButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNomOrganizador = new javax.swing.JTextField();
        txtInsProcedencia = new javax.swing.JTextField();
        cbTipoPar = new javax.swing.JComboBox<>();
        txtNomEvento = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesEvento = new javax.swing.JTextArea();
        txtImportarImagen = new javax.swing.JTextField();
        btnSelecImg = new javax.swing.JButton();
        btnGuarEve = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbEFES = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtObjetivo = new javax.swing.JTextArea();
        jdFechaEvento = new com.toedter.calendar.JDateChooser();
        cbTipoEvent = new javax.swing.JComboBox<>();
        btnLimpiarImagen = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEventos = new javax.swing.JTable();
        btnGenerarPDF = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        btnMasInfo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(111, 27, 70));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane2.setBackground(new java.awt.Color(111, 27, 70));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(111, 27, 70)));
        jTabbedPane2.setForeground(new java.awt.Color(111, 27, 70));

        jPanel1.setBackground(new java.awt.Color(111, 27, 70));

        jPanel16.setAutoscrolls(true);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel1.setText("Institución de Procedencia:");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel2.setText("Nombre del Organizador:");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel3.setText("Tipo de Participante:");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel4.setText("Nombre del Evento:");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel5.setText("Fecha del Evento:");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel6.setText("Tipo de Evento:");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel7.setText("Descripción del Evento:");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel8.setText("Importar Imagen:");

        txtNomOrganizador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomOrganizadorActionPerformed(evt);
            }
        });

        cbTipoPar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alumno", "Profesor", "Administrativo", "Funcionario" }));
        cbTipoPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoParActionPerformed(evt);
            }
        });

        txtNomEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEventoActionPerformed(evt);
            }
        });

        txtDesEvento.setColumns(20);
        txtDesEvento.setRows(5);
        jScrollPane1.setViewportView(txtDesEvento);

        txtImportarImagen.setEditable(false);
        txtImportarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImportarImagenActionPerformed(evt);
            }
        });

        btnSelecImg.setText("Seleccionar");
        btnSelecImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecImgActionPerformed(evt);
            }
        });

        btnGuarEve.setText("Guardar Evento");
        btnGuarEve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuarEveActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel10.setText("Objetivo:");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel11.setText("EFES:");

        cbEFES.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        cbEFES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEFESActionPerformed(evt);
            }
        });

        txtObjetivo.setColumns(20);
        txtObjetivo.setRows(5);
        jScrollPane4.setViewportView(txtObjetivo);

        jdFechaEvento.setToolTipText("Introduce la fecha a llevarse a cabo el evento.");
        jdFechaEvento.setMinSelectableDate(new java.util.Date(-62135744288000L));

        cbTipoEvent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Académico", "Vinculación", "Red de género", "Derechos humanos", "A lo largo de la vida" }));
        cbTipoEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoEventActionPerformed(evt);
            }
        });

        btnLimpiarImagen.setText("Borrar");
        btnLimpiarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarImagenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(352, 352, 352)
                .addComponent(btnGuarEve)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtImportarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnSelecImg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiarImagen)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jdFechaEvento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(cbTipoEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cbTipoPar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNomOrganizador)
                                            .addComponent(txtInsProcedencia)
                                            .addComponent(txtNomEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel11))
                                        .addGap(43, 43, 43)
                                        .addComponent(cbEFES, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(131, 131, 131))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomOrganizador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtInsProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbTipoPar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNomEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jdFechaEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cbTipoEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEFES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtImportarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelecImg)
                    .addComponent(btnLimpiarImagen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuarEve)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Nuevo Evento", jPanel1);

        jPanel2.setBackground(new java.awt.Color(111, 27, 70));

        jPanel23.setAutoscrolls(true);

        tablaEventos.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tablaEventos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaEventos);

        btnGenerarPDF.setText("Generar Archivo PDF");
        btnGenerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarPDFActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel9.setText("Búsqueda:");

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });

        btnMasInfo.setText("Más Información");
        btnMasInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(btnGenerarPDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMasInfo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(38, Short.MAX_VALUE))))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerarPDF)
                    .addComponent(btnMasInfo))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Buscar Evento", jPanel2);

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

    private void txtNomOrganizadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomOrganizadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomOrganizadorActionPerformed

    private void cbTipoParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoParActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoParActionPerformed

    private void btnGenerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarPDFActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaEventos.getSelectedRow();
        String valorID = (String) tablaEventos.getValueAt(filaSeleccionada, 0);
        generarPdf(valorID);
    }//GEN-LAST:event_btnGenerarPDFActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed
    //Valida y llama al metodo guardarEventos mandando como parametros un arreglo con la información
    private void btnGuarEveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuarEveActionPerformed
        // TODO add your handling code here:
        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdFechaEvento.getDateEditor();
        if (txtNomOrganizador.getText().trim().isEmpty() || txtInsProcedencia.getText().trim().isEmpty() || txtNomEvento.getText().trim().isEmpty() || editor.getText().isEmpty()
                || txtObjetivo.getText().trim().isEmpty() || txtDesEvento.getText().trim().isEmpty() || txtImportarImagen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Introduce todos los datos");
        } else {

            String dia = Integer.toString(jdFechaEvento.getCalendar().get(Calendar.DAY_OF_MONTH));
            String mes = Integer.toString(jdFechaEvento.getCalendar().get(Calendar.MONTH) + 1);
            String year = Integer.toString(jdFechaEvento.getCalendar().get(Calendar.YEAR));
            String fecha = (year + "-" + mes + "-" + dia);

            String[] datos = new String[10];
            datos[0] = txtNomOrganizador.getText();
            datos[1] = txtInsProcedencia.getText();
            datos[2] = (String) cbTipoPar.getSelectedItem();
            datos[3] = txtNomEvento.getText();
            datos[4] = fecha;
            datos[5] = (String) cbTipoEvent.getSelectedItem();
            datos[6] = txtObjetivo.getText();
            datos[7] = (String) cbEFES.getSelectedItem();
            datos[8] = txtDesEvento.getText();
            datos[9] = txtImportarImagen.getText();
            guardarEvento(datos);
        }


    }//GEN-LAST:event_btnGuarEveActionPerformed

    private void txtImportarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImportarImagenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImportarImagenActionPerformed
    //Guarda las rutas de las imagenes del evento en un JTextField
    private void btnSelecImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecImgActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PNG", "png", "*.JPG", "jpg", "*.JPEG", "jpeg");
        fc.setFileFilter(filtro);
        fc.setMultiSelectionEnabled(true);

        int seleccion = fc.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            for (int i = 0; i < files.length; i++) {

                String contenido = txtImportarImagen.getText();
                if (i + 1 < files.length) {
                    contenido = contenido + files[i] + ",";
                } else {
                    contenido = contenido + files[i];
                }

                txtImportarImagen.setText(contenido);
            }
        }


    }//GEN-LAST:event_btnSelecImgActionPerformed

    private void cbEFESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEFESActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEFESActionPerformed

    private void cbTipoEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoEventActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoEventActionPerformed

    private void txtNomEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomEventoActionPerformed
    //Llama al método mandando como parametro el ID de la fila seleccionada.
    private void btnMasInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasInfoActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaEventos.getSelectedRow();
        String valorID = (String) tablaEventos.getValueAt(filaSeleccionada, 0);
        mostrarInformacionEvento(valorID);
    }//GEN-LAST:event_btnMasInfoActionPerformed

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        // TODO add your handling code here:
        filtrarDatosEventos(jTextField7.getText());
    }//GEN-LAST:event_jTextField7KeyReleased
    //Llama al metodo para limpiar el TextField que almacena las rutas de las imagenes.
    private void btnLimpiarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarImagenActionPerformed
        // TODO add your handling code here:
        limpiarImagen();
    }//GEN-LAST:event_btnLimpiarImagenActionPerformed

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
                new Evento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarPDF;
    private javax.swing.JButton btnGuarEve;
    private javax.swing.JButton btnLimpiarImagen;
    private javax.swing.JButton btnMasInfo;
    private javax.swing.JButton btnSelecImg;
    private javax.swing.JComboBox<String> cbEFES;
    private javax.swing.JComboBox<String> cbTipoEvent;
    private javax.swing.JComboBox<String> cbTipoPar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JToggleButton jToggleButton1;
    private com.toedter.calendar.JDateChooser jdFechaEvento;
    private javax.swing.JTable tablaEventos;
    private javax.swing.JTextArea txtDesEvento;
    private javax.swing.JTextField txtImportarImagen;
    private javax.swing.JTextField txtInsProcedencia;
    private javax.swing.JTextField txtNomEvento;
    private javax.swing.JTextField txtNomOrganizador;
    private javax.swing.JTextArea txtObjetivo;
    // End of variables declaration//GEN-END:variables
}
