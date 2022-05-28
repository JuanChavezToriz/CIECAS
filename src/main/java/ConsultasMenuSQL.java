
//import static conexionSQL.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan
 */
public class ConsultasMenuSQL extends conexionSQL{
    
    
    //Preparamos el codigo para las consultas
        PreparedStatement ps = null;
        ResultSet rs =null;  
        Statement st=null;
        Connection cn=conexionSQL.conexion();
//Aqui estan todas las consultas de Menu Prueba
  
//metodo para datos del solicitante
   public void solicitante(String tfnom22,String tfapep,String tfapem,String tfcalle,String tfnoext,String tfnoint,String tfcolonia,
 String tfmunicipioalca,int tfcp,String tfestado,String tfpais, int tftelcasa,int tftelcelular,int sexo,
 String tfcorreo1,String tfcorreo2,String tfdiscapacidad,String tflenguaext,String tfnomacade, Date jdfechanaci){
//ESTO ES UNICAMENTE A LA TABLA SOLICITANTE
      try {   
 ps = cn.prepareStatement("INSERT INTO solicitante (Nom_Solic, AP_Solic , AM_Solic,FN_Solic,Sexo_Solic) VALUES (?,?,?,?,?)");
            String datos[]=new String[4];
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");       
		String dateToStr = dateFormat.format(jdfechanaci); 

               ps.setString(1, tfnom22);
               ps.setString(2, tfapep);
               ps.setString(3, tfapem);
               ps.setString(4, dateToStr);
               ps.setInt(5, sexo); 
               
           int rs = ps.executeUpdate();
            if(rs > 0){
    
                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado!");}
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        } //cierre del try //TERMINAMOS CON LA TABLA SOLICTANTE EN LA BD
       
//ESTO ES UNICAMENTE A LA TABLA DOMICILIO 
        try {   
 ps = cn.prepareStatement("INSERT INTO domicilio (Call_Domic, NE_Domic , NI_Domic,Col_Domic,MD_Domic,CP_Domic,Est_Domic,Pai_Domic) VALUES (?,?,?,?,?,?,?,?)");
 
 if(tfnoext.isEmpty()||tfnoext.equals("")){
     tfnoext="";
 } if(tfnoint.isEmpty()||tfnoint.equals("")){
     tfnoint="";
 }
 
 
               ps.setString(1, tfcalle);
               ps.setString(2, tfnoext);
               ps.setString(3, tfnoint);
               ps.setString(4, tfcolonia);
               ps.setString(5, tfmunicipioalca); 
               ps.setInt(6, tfcp);
               ps.setString(7, tfestado);
               ps.setString(8, tfpais);
           int rs = ps.executeUpdate();
            if(rs > 0){
    
                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado2!");}
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        } //cierre del try //TERMINAMOS CON LA TABLA DOMICILIO EN LA BD
       
//ESTO ES UNICAMENTE A LA TABLA CONTACTO
try {   
 
 
 ps = cn.prepareStatement("INSERT INTO contacto (TEC_Conta, TEM_Conta, C1_Conta, C2_Conta) VALUES (?,?,?,?)");
 
               ps.setInt(1, tftelcasa);
               ps.setInt(2, tftelcelular);
               ps.setString(3, tfcorreo1);
               ps.setString(4, tfcorreo2);
               
           int rs = ps.executeUpdate();
            if(rs > 0){
    
                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado3!");}
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        } //cierre del try //TERMINAMOS CON LA TABLA CONTACTO

//ESTO ES UNICAMENTE A LA TABLA DISCAPACIDAD
try {
 ps = cn.prepareStatement("INSERT INTO discapacidad (Tip_Discap) VALUES (?)");
 
               ps.setString(1, tfdiscapacidad);
             
               
           int rs = ps.executeUpdate();
            if(rs > 0){
    
                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado4!");}
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        } //cierre del try //TERMINAMOS CON LA TABLA DISCAPACIDAD
 
//ESTO ES UNICAMENTE A LA TABLA LENGUA INDIGENA
try {
 ps = cn.prepareStatement("INSERT INTO lengua_indigena (Tip_Lengu) VALUES (?)");
 
               ps.setString(1, tflenguaext);
            
               
           int rs = ps.executeUpdate();
            if(rs > 0){
    
                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado5!");}
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        } //cierre del try //TERMINAMOS CON LA TABLA LENGUA INDIGENA  

//ESTO ES UNICAMENTE A LA TABLA PROGRAMA ACADEMICO
try {
 ps = cn.prepareStatement("INSERT INTO programa_academico (Nom_Prog) VALUES (?)");
 
               ps.setString(1, tfnomacade);
             
               
           int rs = ps.executeUpdate();
            if(rs > 0){
    
                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado6!");}
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        } //cierre del try //TERMINAMOS CON LA TABLA PROGRAMA ACADEMICO 

} //Cierre de metodo solicitante 


//metodo para datos de antecedentes academicos
   public void antecedentes(String tfprograma1,String tfinstituto1,String tfestado1,Date fecha2,
                            String tfprograma2,String tfinstituto2,String tfestado2,Date fecha3,
                            String tfprograma3,String tfinstituto3,String tfestado3,Date fecha4,String nivelaca){
//ESTO ES UNICAMENTE A LA TABLA
      try {   
 ps = cn.prepareStatement("INSERT INTO registro_antecendentes_academicos (Niv_RegisA, PAC_RegisA, Ins_RegisA ,EOP_RegisA,FG_RegisA) VALUES (?,?,?,?,?)");
            String datos[]=new String[4];
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");       
		String dateToStr = dateFormat.format(fecha2);
                String dateToStr2 = dateFormat.format(fecha3);
                String dateToStr3 = dateFormat.format(fecha4);

               ps.setString(1, nivelaca);
               ps.setString(2, tfprograma1);
               ps.setString(3, tfapem);
               ps.setString(4, dateToStr);
               ps.setInt(5, sexo); 
               
           int rs = ps.executeUpdate();
            if(rs > 0){
    
                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado!");}
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        } //cierre del try //TERMINAMOS CON LA TABLA ANTECEDENTES EN LA BD
       

} //Cierre de metodo antecedentes
   
}//LLAVE FINAL DE LA CLASE
       

