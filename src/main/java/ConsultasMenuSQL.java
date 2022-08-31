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
public class ConsultasMenuSQL extends conexionSQL {

    //Preparamos el codigo para las consultas
    PreparedStatement ps = null;
    ResultSet rs = null;
    Statement st = null;
    static int valorId;
    Connection cn = conexionSQL.conexion();
//Aqui estan todas las consultas de Menu Prueba

//metodo para datos del solicitante
    public void solicitante(String tfnom22, String tfapep, String tfapem, String tfcalle, String tfnoext, String tfnoint, String tfcolonia,
            String tfmunicipioalca, String tfcp, String tfestado, String tfpais, String tftelcasa, String tftelcelular, int sexo,
            String tfcorreo1, String tfcorreo2, String tfdiscapacidad, String tflenguaext, String tfnomacade, Date jdfechanaci,String especialidad) {
          //ESTO ES UNICAMENTE A LA TABLA SOLICITANTE

        String sql = "INSERT INTO solicitante (Nom_Solic, AP_Solic , AM_Solic,FN_Solic,Sexo_Solic,Clv_Prog,Ins_Solic,Egr_Solic,PF_Solic,PI_Solic,EM_Solic) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {

            ps = cn.prepareStatement(sql);
            byte bit = 0;
            String datos[] = new String[4];
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String dateToStr = dateFormat.format(jdfechanaci);

            ps.setString(1, tfnom22);
            ps.setString(2, tfapep);
            ps.setString(3, tfapem);
            ps.setString(4, dateToStr);
            ps.setInt(5, sexo);
            ps.setInt(6, especialidadJC(especialidad));
            //Validar bien, esta dispuesto a modificaciones
            ps.setByte(7, bit);
            ps.setByte(8, bit);
            ps.setString(9, "Nada");
            ps.setString(10, "Nada");
            ps.setByte(11, bit);

            int sd = ps.executeUpdate();
            if (sd > 0) {

                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado!");
            }
            sql = "SELECT MAX(ID_Solic) FROM solicitante";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                valorId = Integer.parseInt(rs.getString("MAX(ID_Solic)"));
            }


        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        } //cierre del try //TERMINAMOS CON LA TABLA SOLICTANTE EN LA BD

        
            //ESTO ES UNICAMENTE A LA TABLA DOMICILIO 
        String sql2 = "INSERT INTO domicilio (ID_Solic,Call_Domic, NE_Domic , NI_Domic,Col_Domic,MD_Domic,CP_Domic,Est_Domic,Pai_Domic) VALUES (?,?,?,?,?,?,?,?,?)";
        try {

            ps = cn.prepareStatement(sql2);
            ps.setInt(1, valorId);
            ps.setString(2, tfcalle);
            ps.setString(3, tfnoext);
            ps.setString(4, tfnoint);
            ps.setString(5, tfcolonia);
            ps.setString(6, tfmunicipioalca);
            ps.setString(7, tfcp);
            ps.setString(8, tfestado);
            ps.setString(9, tfpais);

            int sd = ps.executeUpdate();
            if (sd > 0) {

                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado 2!");
            }
            //int sd = ps.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error 2 :(" + ex);
        }

//ESTO ES UNICAMENTE A LA TABLA CONTACTO
        try {
            String sql3 = "INSERT INTO contacto (ID_Solic,TEC_Conta, TEM_Conta, C1_Conta, C2_Conta) VALUES (?,?,?,?,?)";

            ps = cn.prepareStatement(sql3);
            ps.setInt(1, valorId);
            ps.setString(2, tftelcasa);
            ps.setString(3, tftelcelular);
            ps.setString(4, tfcorreo1);
            ps.setString(5, tfcorreo2);

            int sd = ps.executeUpdate();
            if (sd > 0) {

                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado3!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error 3:(");
        } //cierre del try //TERMINAMOS CON LA TABLA CONTACTO

//ESTO ES UNICAMENTE A LA TABLA DISCAPACIDAD
        try {
            String sql4 = "INSERT INTO discapacidad (ID_Solic,Tip_Discap) VALUES (?,?)";

            ps = cn.prepareStatement(sql4);
            ps.setInt(1, valorId);
            ps.setString(2, tfdiscapacidad);

            int sd = ps.executeUpdate();
            if (sd > 0) {
                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado4!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error 4:(");
        } //cierre del try //TERMINAMOS CON LA TABLA DISCAPACIDAD

//ESTO ES UNICAMENTE A LA TABLA LENGUA INDIGENA
        try {

            String sql5 = "INSERT INTO lengua_indigena (ID_Solic,Tip_Lengu) VALUES (?,?)";

            ps = cn.prepareStatement(sql5);
            ps.setInt(1, valorId);
            ps.setString(2, tflenguaext);

            int sd = ps.executeUpdate();
            if (sd > 0) {

                JOptionPane.showMessageDialog(null, "¡Solicitante Agregado 5!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en lengua 5 :(");
        } //cierre del try //TERMINAMOS CON LA TABLA LENGUA INDIGENA  

    } //Cierre de metodo solicitante 

//metodo para datos de antecedentes academicos
    public void antecedentes(String tfprograma1, String tfinstituto1, String tfestado1, Date fecha2,
            String tfprograma2, String tfinstituto2, String tfestado2, Date fecha3,
            String tfprograma3, String tfinstituto3, String tfestado3, Date fecha4, String nivelaca, int especialidad) {
        if (especialidad == 1) { // nivel licenciatura
//ESTO ES UNICAMENTE A LA TABLA
            try {
                ps = cn.prepareStatement("INSERT INTO registro_antecendentes_academicos (Niv_RegisA, PAC_RegisA, Ins_RegisA ,EOP_RegisA,FG_RegisA) VALUES (?,?,?,?,?)");
                String datos[] = new String[4];
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String dateToStr = dateFormat.format(fecha2);
                //String dateToStr2 = dateFormat.format(fecha3);
                //String dateToStr3 = dateFormat.format(fecha4);

                ps.setString(1, nivelaca);
                ps.setString(2, tfprograma1);
                ps.setString(3, tfinstituto1);
                ps.setString(4, tfestado1);
                ps.setString(5, dateToStr);

                int rs = ps.executeUpdate();
                if (rs > 0) {

                    JOptionPane.showMessageDialog(null, "¡licenciatura lista!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
            } //cierre del try //TERMINAMOS CON LA TABLA ANTECEDENTES EN LA BD
        }

        if (especialidad == 2) { // nivel especialidad
//ESTO ES UNICAMENTE A LA TABLA
            try {
                ps = cn.prepareStatement("INSERT INTO registro_antecendentes_academicos (Niv_RegisA, PAC_RegisA, Ins_RegisA ,EOP_RegisA,FG_RegisA) VALUES (?,?,?,?,?)");
                String datos[] = new String[4];
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                //String dateToStr = dateFormat.format(fecha2);
                String dateToStr2 = dateFormat.format(fecha3);
                //String dateToStr3 = dateFormat.format(fecha4);

                ps.setString(1, nivelaca);
                ps.setString(2, tfprograma2);
                ps.setString(3, tfinstituto2);
                ps.setString(4, tfestado2);
                ps.setString(5, dateToStr2);

                int rs = ps.executeUpdate();
                if (rs > 0) {

                    JOptionPane.showMessageDialog(null, "¡especialidad lista!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
            } //cierre del try //TERMINAMOS CON LA TABLA ANTECEDENTES EN LA BD
        }

        if (especialidad == 3) { // nivel maestria
//ESTO ES UNICAMENTE A LA TABLA
            try {
                ps = cn.prepareStatement("INSERT INTO registro_antecendentes_academicos (Niv_RegisA, PAC_RegisA, Ins_RegisA ,EOP_RegisA,FG_RegisA) VALUES (?,?,?,?,?)");
                String datos[] = new String[4];
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                //String dateToStr = dateFormat.format(fecha2);
                //String dateToStr2 = dateFormat.format(fecha3);
                String dateToStr3 = dateFormat.format(fecha4);

                ps.setString(1, nivelaca);
                ps.setString(2, tfprograma3);
                ps.setString(3, tfinstituto3);
                ps.setString(4, tfestado3);
                ps.setString(5, dateToStr3);

                int rs = ps.executeUpdate();
                if (rs > 0) {

                    JOptionPane.showMessageDialog(null, "¡maestria listo!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
            } //cierre del try //TERMINAMOS CON LA TABLA ANTECEDENTES EN LA BD
        }

    } //Cierre de metodo antecedentes

    public int especialidadJC(String especialidad) {
        
        String SQL = "select Clv_Prog from programa_academico where Nom_Prog= '" + especialidad + "'";
        int clv_programa=0;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(SQL);
            
      
            while (rs.next()) {
                clv_programa = Integer.parseInt(rs.getString("Clv_Prog"));
                 
            }
            JOptionPane.showMessageDialog(null, "¡Esta es la CLAVE del CB!" +clv_programa);
        } catch (Exception ex) {
            Logger.getLogger(menuprueba.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error :(");
        }
        return clv_programa;
    } //cierre del try //TERMINAMO

}//LLAVE FINAL DE LA CLASE
