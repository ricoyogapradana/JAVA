/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.siekeusekolah;

import com.mycompany.siekeusekolah.form.DialogLogin;
import com.mycompany.siekeusekolah.form.FormUtama;
import java.sql.SQLException;

/**
 *
 * @author rico
 */
public class SikeuSekolah {
    
    public static FormUtama tampilanUtama;
    public static DialogLogin tampilanLogin;
    
    public static void main(String[] args) {
        System.out.println("Menjalankan aplikasi SIKEU - Sekolah");
        Koneksi cek = new Koneksi();
        try {
            cek.konekDB.close(); //tutup koneksi
        } catch (SQLException ex) {
            
        }
        
        tampilanUtama = new FormUtama();
        tampilanLogin = new DialogLogin(tampilanUtama, true);
        tampilanLogin.setVisible(true);
    }
}
