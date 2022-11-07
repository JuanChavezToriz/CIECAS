
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author estme
 */
public class Administracion extends javax.swing.JFrame {
    //Instancia y conexion a la base de datos.
    conexionSQL cc = new conexionSQL();
    Connection con = cc.conexion();
  
    /** 
     * Creates new form Administracion.
     */
    public Administracion() {
        initComponents();
        this.setLocationRelativeTo(null);
        mostrarDatos();
        mostrarDatosInscritos();
        mostrarDatosEgresados();
    }
    //Metodo para mostrar los datos almacenados en la DB en la tabla del panel "Solicitud de Inscripción."
    public void mostrarDatos(){
    
        String[] titulos = {"ID del Solicitante", "Nombre","Programa Academico"};
        String[] registros = new String[3];
        //Se especifica que la información de la tabla no puede ser editada.
       DefaultTableModel modelo = new DefaultTableModel(null,titulos){
           @Override 
           public boolean isCellEditable(int row, int column){
               return false;
           }
       
       };
       //Asignación de la consulta a una String.
       String SQL = "select s.ID_Solic, s.Nom_Solic,s.AP_Solic,s.AM_Solic,s.Ins_Solic ,s.Egr_Solic, p.Nom_prog from solicitante s INNER JOIN programa_academico p  where s.ID_Prog = p.ID_Prog";
       
       try{
           //Se ejecuta la consulta y se almacenan los datos provenientes de la DB en un arreglo
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(SQL);
           while(rs.next()){
               
               if(Integer.parseInt(rs.getString("Ins_Solic")) == 0 && Integer.parseInt(rs.getString("Egr_Solic")) == 0 ){
                   registros[0]= rs.getString("ID_Solic");
                   registros[1]= rs.getString("Nom_Solic")+ " "+ rs.getString("AP_Solic")+ " "+ rs.getString("AM_Solic") ;
                   registros[2]= rs.getString("Nom_prog");
                   modelo.addRow(registros);
               
               
               
               }
               
           }
           //Se llena la tabla
           tablaSolicitud.setModel(modelo);
       }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al Mostrar Datos" + ex.getMessage()); 
        }
       
    }
    //Este método trabaja de forma similar a mostrarDatos, pero para la tabla del panel "Buscar Alumno Inscrito"
        public void mostrarDatosInscritos(){
    
        String[] titulos = {"Boleta del Alumno", "Nombre","Programa Academico"};
        String[] registros = new String[3];
        
       DefaultTableModel modelo = new DefaultTableModel(null,titulos){
           @Override 
           public boolean isCellEditable(int row, int column){
               return false;
           }
       
       };
       
       String SQL = "select s.ID_Solic,s.Bol_Solic , s.Nom_Solic,s.AP_Solic,s.AM_Solic,s.Ins_Solic ,s.Egr_Solic, p.Nom_prog from solicitante s INNER JOIN programa_academico p  on s.ID_Prog = p.ID_Prog where Ins_Solic = 1";
       
       try{
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(SQL);
           while(rs.next()){
               
                   registros[0]= rs.getString("Bol_Solic");
                   registros[1]= rs.getString("Nom_Solic")+ " "+ rs.getString("AP_Solic")+ " "+ rs.getString("AM_Solic") ;
                   registros[2]= rs.getString("Nom_prog");
                   modelo.addRow(registros);
               
               
               
               
               
           }
           
           tablaInscrito.setModel(modelo);
       }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al Mostrar Datos" + ex.getMessage()); 
        }
       
    }
    //Este método trabaja de forma similar a mostrarDatos, pero para la tabla del panel "Egresados"
    public void mostrarDatosEgresados(){
    
        String[] titulos = {"Boleta del Alumno", "Nombre","Programa Academico"};
        String[] registros = new String[3];
        
       DefaultTableModel modelo = new DefaultTableModel(null,titulos){
           @Override 
           public boolean isCellEditable(int row, int column){
               return false;
           }
       
       };
       
       String SQL = "select s.ID_Solic,s.Bol_Solic , s.Nom_Solic,s.AP_Solic,s.AM_Solic,s.Ins_Solic ,s.Egr_Solic, p.Nom_prog from solicitante s INNER JOIN programa_academico p  on s.ID_Prog = p.ID_Prog where Egr_Solic = 1";
       
       try{
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(SQL);
           while(rs.next()){
               
                   registros[0]= rs.getString("Bol_Solic");
                   registros[1]= rs.getString("Nom_Solic")+ " "+ rs.getString("AP_Solic")+ " "+ rs.getString("AM_Solic") ;
                   registros[2]= rs.getString("Nom_prog");
                   modelo.addRow(registros);
               
               
               
               
               
           }
           
           tablaEgresados.setModel(modelo);
       }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al Mostrar Datos" + ex.getMessage()); 
        }
       
    }
    //Metodo para filtrar los valores de la tabla de acuerdo al Nombre o apellidos de solicitante.
    public void filtrarDatosSolicitud(String valor){
    
        String[] titulos = {"ID del Solicitante", "Nombre","Programa Academico"};
        String[] registros = new String[3];
        
       DefaultTableModel modelo = new DefaultTableModel(null,titulos){
           @Override 
           public boolean isCellEditable(int row, int column){
               return false;
           }
       
       };
       //Este metodo trabaja de la misma forma que el método mostrar Datos, solo que reliza una consulta más específica
       String SQL = "select s.ID_Solic, s.Nom_Solic,s.AP_Solic,s.AM_Solic,s.Ins_Solic ,s.Egr_Solic, p.Nom_prog from solicitante s INNER JOIN programa_academico p  on s.ID_Prog = p.ID_Prog where concat(Nom_Solic, ' ',AP_solic, ' ', AM_Solic,' ', Bol_Solic) like '%"+ valor +"%'";
       
       try{
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(SQL);
           while(rs.next()){
               
               if(Integer.parseInt(rs.getString("Ins_Solic")) == 0 && Integer.parseInt(rs.getString("Egr_Solic")) == 0 ){
                   registros[0]= rs.getString("ID_Solic");
                   registros[1]= rs.getString("Nom_Solic")+ " "+ rs.getString("AP_Solic")+ " "+ rs.getString("AM_Solic") ;
                   registros[2]= rs.getString("Nom_prog");
                   modelo.addRow(registros);
               
               
               
               }
               
           }
           
           tablaSolicitud.setModel(modelo);
       }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al Mostrar Datos" + ex.getMessage()); 
        }
       
    }
    //Este metodo es analago a filtrarDatosSolicitantes , pero para la tabla del panel "Buscar Alumno Inscrito"
     public void filtrarDatosInscritos(String valor){
    
        String[] titulos = {"Boleta del Alumno", "Nombre","Programa Academico"};
        String[] registros = new String[3];
        
       DefaultTableModel modelo = new DefaultTableModel(null,titulos){
           @Override 
           public boolean isCellEditable(int row, int column){
               return false;
           }
       
       };
       
       String SQL = "select s.ID_Solic,s.Bol_Solic , s.Nom_Solic,s.AP_Solic,s.AM_Solic,s.Ins_Solic ,s.Egr_Solic, p.Nom_prog from solicitante s INNER JOIN programa_academico p  on s.ID_Prog = p.ID_Prog where Ins_Solic = 1 and concat(Nom_Solic, ' ',AP_solic, ' ', AM_Solic) like '%"+ valor +"%'";
       
       try{
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(SQL);
           while(rs.next()){
               
               
                   registros[0]= rs.getString("Bol_Solic");
                   registros[1]= rs.getString("Nom_Solic")+ " "+ rs.getString("AP_Solic")+ " "+ rs.getString("AM_Solic") ;
                   registros[2]= rs.getString("Nom_prog");
                   modelo.addRow(registros);
               
               
               
               
               
           }
           
           tablaInscrito.setModel(modelo);
       }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al Mostrar Datos" + ex.getMessage()); 
        }
       
    }
     //Este metodo es analago a filtrarDatosSolicitantes , pero para la tabla del panel "Egresados"
     public void filtrarDatosEgresados(String valor){
    
        String[] titulos = {"Boleta del Alumno", "Nombre","Programa Academico"};
        String[] registros = new String[3];
        
       DefaultTableModel modelo = new DefaultTableModel(null,titulos){
           @Override 
           public boolean isCellEditable(int row, int column){
               return false;
           }
       
       };
       
       String SQL = "select s.ID_Solic,s.Bol_Solic , s.Nom_Solic,s.AP_Solic,s.AM_Solic,s.Ins_Solic ,s.Egr_Solic, p.Nom_prog from solicitante s INNER JOIN programa_academico p  on s.ID_Prog = p.ID_Prog where Egr_Solic = 1 and concat(Nom_Solic, ' ',AP_solic, ' ', AM_Solic) like '%"+ valor +"%'";
       
       try{
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(SQL);
           while(rs.next()){
               
               
                   registros[0]= rs.getString("Bol_Solic");
                   registros[1]= rs.getString("Nom_Solic")+ " "+ rs.getString("AP_Solic")+ " "+ rs.getString("AM_Solic") ;
                   registros[2]= rs.getString("Nom_prog");
                   modelo.addRow(registros);
               
               
               
               
               
           }
           
           tablaEgresados.setModel(modelo);
       }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al Mostrar Datos" + ex.getMessage()); 
        }
       
    }

     
    
    //Muestra información mas detallada de cada una de las solicitudes almacenadas
    public void mostrarInformacionSolicitud(String id){
    
        try{
            
            String[] registros = new String[14];
            //Asignación de la consulta a una String
            String SQL = "select s.FN_Solic,s.Sexo_Solic,d.Call_Domic,d.NE_Domic,d.NI_Domic,d.Col_Domic,d.MD_Domic,d.CP_Domic,d.Est_Domic,d.Pai_Domic,c.TEC_Conta,"
                         + "c.TEM_Conta,c.C1_Conta,c.C2_Conta from solicitante s inner join domicilio d on s.ID_Solic = d .ID_Solic inner join contacto c "
                         + "on s.ID_Solic = c .ID_Solic where s.ID_Solic = ?";
            //Se prepara y se ejecuta la consulta
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, id.trim());
            ResultSet rs = pst.executeQuery();
            //Se almacena la información en un arreglo
            if(rs.next()){
                registros[0] = rs.getString("s.FN_Solic");
                registros[1] = rs.getString("s.Sexo_Solic");
                registros[2] = rs.getString("d.Call_Domic");
                registros[3] = rs.getString("d.NE_Domic");
                registros[4] = rs.getString("d.NI_Domic");
                registros[5] = rs.getString("d.Col_Domic");
                registros[6] = rs.getString("d.MD_Domic");
                registros[7] = rs.getString("d.Est_Domic");
                registros[8] = rs.getString("d.Pai_Domic");
                registros[9] = rs.getString("c.TEC_Conta");
                registros[10] = rs.getString("c.TEM_Conta");
                registros[11] = rs.getString("c.C1_Conta");
                registros[12] = rs.getString("c.C2_Conta");
                registros[13] = rs.getString("d.CP_Domic");
            }
            
            
            
            
            //Muesra la información en un JOptionPane
            JOptionPane.showMessageDialog(null, 
                      "Edad: " + calcularEdad(registros[0])+
                      "\nSexo: " + conocerSexo(registros[1])+ 
                      "\nDirección: " + registros[2] + "  " +  registros[3]+ " " + comprobarNull(registros[4]) + ", "+ registros[5] +", "+ registros[6]+
                      ", " + registros[7] + ", " + registros[8]+ 
                      "\nCódigo Postal: " + registros[13]+
                      "\nTeléfono de Casa: "+ registros[9]+
                      "\nTeléfono Móvil: " + registros[10] +
                      "\nCorreo electronico 1: " + registros[11]+
                      "\nCorreo electronico 2: " + registros[12], "DATOS DEL SOLICITANTE",JOptionPane.INFORMATION_MESSAGE);
        
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error:" + ex.getMessage());
        }
        
        
        
    
    
    
    }
    //Este metodo es analago a mostrarInformacinSolicitud , pero para la tabla del panel "Inscritos"
     public void mostrarInformacionInscrito(String id){
    
        try{
            
            String[] registros = new String[14];
            
            String SQL = "select s.FN_Solic,s.Sexo_Solic,d.Call_Domic,d.NE_Domic,d.NI_Domic,d.Col_Domic,d.MD_Domic,d.CP_Domic,d.Est_Domic,d.Pai_Domic,c.TEC_Conta,"
                         + "c.TEM_Conta,c.C1_Conta,c.C2_Conta from solicitante s inner join domicilio d on s.ID_Solic = d .ID_Solic inner join contacto c "
                         + "on s.ID_Solic = c .ID_Solic where s.Bol_Solic = ?";
            
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, id.trim());
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                registros[0] = rs.getString("s.FN_Solic");
                registros[1] = rs.getString("s.Sexo_Solic");
                registros[2] = rs.getString("d.Call_Domic");
                registros[3] = rs.getString("d.NE_Domic");
                registros[4] = rs.getString("d.NI_Domic");
                registros[5] = rs.getString("d.Col_Domic");
                registros[6] = rs.getString("d.MD_Domic");
                registros[7] = rs.getString("d.Est_Domic");
                registros[8] = rs.getString("d.Pai_Domic");
                registros[9] = rs.getString("c.TEC_Conta");
                registros[10] = rs.getString("c.TEM_Conta");
                registros[11] = rs.getString("c.C1_Conta");
                registros[12] = rs.getString("c.C2_Conta");
                registros[13] = rs.getString("d.CP_Domic");
            }
            
            
            
            
            
            JOptionPane.showMessageDialog(null, 
                      "Edad: " + calcularEdad(registros[0])+
                      "\nSexo: " + conocerSexo(registros[1])+ 
                      "\nDirección: " + registros[2] + "  " +  registros[3]+ " " + comprobarNull(registros[4]) + ", "+ registros[5] +", "+ registros[6]+
                      ", " + registros[7] + ", " + registros[8]+ 
                      "\nCódigo Postal: " + registros[13]+
                      "\nTeléfono de Casa: "+ registros[9]+
                      "\nTeléfono Móvil: " + registros[10] +
                      "\nCorreo electronico 1: " + registros[11]+
                      "\nCorreo electronico 2: " + registros[12], "DATOS DEL ALUMNO",JOptionPane.INFORMATION_MESSAGE);
        
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error:" + ex.getMessage());
        }
        
        
        
    
    
    
    }
     //Este metodo es analago a mostrarInformacinSolicitud , pero para la tabla del panel "Egresados"
    public void mostrarInformacionEgresado(String id){
    
        try{
            
            String[] registros = new String[14];
            
            String SQL = "select s.FN_Solic,s.Sexo_Solic,d.Call_Domic,d.NE_Domic,d.NI_Domic,d.Col_Domic,d.MD_Domic,d.CP_Domic,d.Est_Domic,d.Pai_Domic,c.TEC_Conta,"
                         + "c.TEM_Conta,c.C1_Conta,c.C2_Conta from solicitante s inner join domicilio d on s.ID_Solic = d .ID_Solic inner join contacto c "
                         + "on s.ID_Solic = c .ID_Solic where s.Bol_Solic = ?";
            
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, id.trim());
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                registros[0] = rs.getString("s.FN_Solic");
                registros[1] = rs.getString("s.Sexo_Solic");
                registros[2] = rs.getString("d.Call_Domic");
                registros[3] = rs.getString("d.NE_Domic");
                registros[4] = rs.getString("d.NI_Domic");
                registros[5] = rs.getString("d.Col_Domic");
                registros[6] = rs.getString("d.MD_Domic");
                registros[7] = rs.getString("d.Est_Domic");
                registros[8] = rs.getString("d.Pai_Domic");
                registros[9] = rs.getString("c.TEC_Conta");
                registros[10] = rs.getString("c.TEM_Conta");
                registros[11] = rs.getString("c.C1_Conta");
                registros[12] = rs.getString("c.C2_Conta");
                registros[13] = rs.getString("d.CP_Domic");
            }
            
            
            
            
            
            JOptionPane.showMessageDialog(null, 
                      "Edad: " + (registros[0])+
                      "\nSexo: " + conocerSexo(registros[1])+ 
                      "\nDirección: " + registros[2] + "  " +  registros[3]+ " " + comprobarNull(registros[4]) + ", "+ registros[5] +", "+ registros[6]+
                      ", " + registros[7] + ", " + registros[8]+ 
                      "\nCódigo Postal: " + registros[13]+
                      "\nTeléfono de Casa: "+ registros[9]+
                      "\nTeléfono Móvil: " + registros[10] +
                      "\nCorreo electronico 1: " + registros[11]+
                      "\nCorreo electronico 2: " + registros[12], "DATOS DEL EGRESADO",JOptionPane.INFORMATION_MESSAGE);
        
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error:" + ex.getMessage());
        }
        
        
        
    
    
    
    }


    //Método que comprueba si una cadena esta vacia
    public String comprobarNull(String cadena){
        
        if(cadena == null){
            return "";
        }else{
            return cadena;
        }
    
    }
    
    //Método que regresa el sexo segun el valor ingresado
    public String conocerSexo(String Sexo){
        int numero = Integer.parseInt(Sexo);
        if(numero == 0){
            return "Maculino";
        }else{
            return "Femenino";
        }
    }

    //Metodo que calcula una edad a partir de la fecha de nacimiento
    public String calcularEdad(String Fecha){
        
        String[] partes= Fecha.split("-");
        int anyo = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia = Integer.parseInt(partes[2]);
        
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaNacimiento = LocalDate.of(anyo, mes, dia);
        Period p = Period.between(fechaNacimiento, fechaHoy);
        
        String Edad = p.getYears() + " años, " + p.getMonths() + " meses, " + p.getDays() + "días.";
        return Edad;
    }
    
    //Método que nos dice si el elemenot almacenado en una cadena es un numero
    private static boolean esNumero(String n) {
		try {
			Long.parseLong(n);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
    //Método que valida la boleta según su longitud
    private static boolean esBoletaValida(String n) {
		
            if(n.length() == 10 ){
                return true;
            }else{
                return false;
            }
	}
    
    //Realiza una modificación en la base de datos, en este caso para inscribir a un alumno
    public void inscribir(String id){
        String boleta;

        try{
            
            boleta = JOptionPane.showInputDialog("Escribe la Boleta correspondiente al Alumno");
            if(esNumero(boleta) && esBoletaValida(boleta)){
                //Asigna la consulta a una cadena
                String SQL = "update solicitante set Ins_Solic = 1,Bol_Solic = ? where ID_Solic = ?";
                //Prepara y ejecuta la consulta
                PreparedStatement pst = con.prepareStatement(SQL);
                pst.setString(1, boleta);
                pst.setString(2, id);
                pst.execute();
                JOptionPane.showMessageDialog(null,"El alumno ha sido inscrito correctamente");
            }else{
                JOptionPane.showMessageDialog(null,"Introduce correctamente la boleta del alumno");
            }
                
            
            
        
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error:" + ex.getMessage());
        }
        
        
    
    }
    //Realiza una modificación en la base de datos, en este caso para marcar como egresado  a un alumno
    public void egresar(String id){
        try{

            String SQL = "update solicitante set Ins_Solic = 0,Egr_Solic = 1 where Bol_Solic = ?";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, id);
            pst.execute();
            JOptionPane.showMessageDialog(null,"El estado del alumno se actualizo a egresado");
            
            
            
        
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error:" + ex.getMessage());
        }
        
        
    
    }


    //Realiza una modificación en la base de datos, en este caso para eliminar la solicitud
     public void eliminarSolicitud(String id){
        try{
            String SQL ="delete from solicitante where ID_Solic = ? " ;
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, id);
            pst.execute();
            JOptionPane.showMessageDialog(null,"La solicitud ha sido eliminada correctamente");
        }catch (Exception ex) {
             JOptionPane.showMessageDialog(null,"Error:" + ex.getMessage());
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
        tablaSolicitud = new javax.swing.JTable();
        btnInscribir = new javax.swing.JButton();
        btnInfo = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaInscrito = new javax.swing.JTable();
        btnEgresar = new javax.swing.JButton();
        btnInfo2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaEgresados = new javax.swing.JTable();
        btnInfo3 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtBuscar3 = new javax.swing.JTextField();
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

        tablaSolicitud.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tablaSolicitud.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaSolicitud.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSolicitudMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaSolicitud);

        btnInscribir.setText("Inscribir");
        btnInscribir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInscribirActionPerformed(evt);
            }
        });

        btnInfo.setText("Más Información");
        btnInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfoActionPerformed(evt);
            }
        });

        jButton4.setText("Eliminar Registro");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel1.setText("Búsqueda:");

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

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btnInscribir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(0, 550, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(449, 449, 449))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInscribir)
                    .addComponent(btnInfo)
                    .addComponent(jButton4))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Solicitudes de  Inscripción", jPanel1);

        jPanel2.setBackground(new java.awt.Color(111, 27, 70));

        jPanel17.setAutoscrolls(true);

        tablaInscrito.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tablaInscrito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaInscrito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaInscritoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaInscrito);

        btnEgresar.setText("Egresar");
        btnEgresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEgresarActionPerformed(evt);
            }
        });

        btnInfo2.setText("Más Información");
        btnInfo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfo2ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel17.setText("Búsqueda:");

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

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(btnEgresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInfo2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(449, 449, 449))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEgresar)
                    .addComponent(btnInfo2))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Buscar Alumno Inscrito", jPanel2);

        jPanel3.setBackground(new java.awt.Color(111, 27, 70));

        jPanel22.setAutoscrolls(true);

        tablaEgresados.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tablaEgresados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablaEgresados);

        btnInfo3.setText("Más Información");
        btnInfo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfo3ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel16.setText("Búsqueda:");

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

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(btnInfo3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(449, 449, 449))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInfo3)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Egresados", jPanel3);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 910, 580));

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
                .addContainerGap(789, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 513, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed
    //Llama al método mandado como parametro la fila de la tabla que esta seleccionada.
    private void btnInfo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfo3ActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaEgresados.getSelectedRow();
        String valorID= (String) tablaEgresados.getValueAt(filaSeleccionada, 0);
        mostrarInformacionEgresado(valorID);
    }//GEN-LAST:event_btnInfo3ActionPerformed

    private void txtBuscar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar3ActionPerformed

    private void txtBuscar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar1KeyReleased
        // TODO add your handling code here:
        filtrarDatosSolicitud(txtBuscar1.getText());
    }//GEN-LAST:event_txtBuscar1KeyReleased

    private void txtBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtBuscar1ActionPerformed
    //Llama al método mandado como parametro la fila de la tabla que esta seleccionada.
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaSolicitud.getSelectedRow();
        String valorID= (String) tablaSolicitud.getValueAt(filaSeleccionada, 0);
        eliminarSolicitud(valorID);
        mostrarDatos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfoActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaSolicitud.getSelectedRow();
        String valorID= (String) tablaSolicitud.getValueAt(filaSeleccionada, 0);
        mostrarInformacionSolicitud(valorID);

    }//GEN-LAST:event_btnInfoActionPerformed
    //Llama al método mandado como parametro la fila de la tabla que esta seleccionada.
    private void btnInscribirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInscribirActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaSolicitud.getSelectedRow();
        String valorID= (String) tablaSolicitud.getValueAt(filaSeleccionada, 0);
        inscribir(valorID);
        mostrarDatos();
        mostrarDatosInscritos();

    }//GEN-LAST:event_btnInscribirActionPerformed

    private void tablaSolicitudMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitudMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tablaSolicitudMouseClicked

    private void tablaInscritoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaInscritoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaInscritoMouseClicked
    //Llama al método mandado como parametro la fila de la tabla que esta seleccionada.
    private void btnEgresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEgresarActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaInscrito.getSelectedRow();
        String valorBol= (String) tablaInscrito.getValueAt(filaSeleccionada, 0);
        egresar(valorBol);
        mostrarDatosInscritos();
        mostrarDatosEgresados();
    }//GEN-LAST:event_btnEgresarActionPerformed
    //Llama al método mandado como parametro la fila de la tabla que esta seleccionada.
    private void btnInfo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfo2ActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaInscrito.getSelectedRow();
        String valorID= (String) tablaInscrito.getValueAt(filaSeleccionada, 0);
        mostrarInformacionInscrito(valorID);
    }//GEN-LAST:event_btnInfo2ActionPerformed

    private void txtBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar2ActionPerformed

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyReleased
        // TODO add your handling code here:
        filtrarDatosInscritos(txtBuscar2.getText());
    }//GEN-LAST:event_txtBuscar2KeyReleased
    //Metodo que llama al método para filtrar datos, se ejecuta cada vez que una tecla es soltada.
    private void txtBuscar3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar3KeyReleased
        // TODO add your handling code here:
        filtrarDatosEgresados(txtBuscar3.getText());
    }//GEN-LAST:event_txtBuscar3KeyReleased

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
                new Administracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEgresar;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnInfo2;
    private javax.swing.JButton btnInfo3;
    private javax.swing.JButton btnInscribir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tablaEgresados;
    private javax.swing.JTable tablaInscrito;
    private javax.swing.JTable tablaSolicitud;
    private javax.swing.JTextField txtBuscar1;
    private javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtBuscar3;
    // End of variables declaration//GEN-END:variables
}
