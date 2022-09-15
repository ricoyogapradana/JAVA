/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.siekeusekolah;

import com.mycompany.siekeusekolah.form.DialogLogin;
import com.mycompany.siekeusekolah.form.FormUtama;
/**
 *
 * @author rico
 */
public class SikeuSekolah {
    public static void main(String[] args) {
        System.out.println("Menjalankan aplikasi SIKEU - Sekolah");
        
        FormUtama tampilanUtama = new FormUtama();
        DialogLogin tampilanLogin = new DialogLogin(tampilanUtama, true);
        tampilanLogin.setVisible(true);
    }
}
