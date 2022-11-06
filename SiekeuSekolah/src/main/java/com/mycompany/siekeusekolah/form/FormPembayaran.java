/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.siekeusekolah.form;

import com.mycompany.siekeusekolah.Koneksi;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author rico
 */
public class FormPembayaran extends javax.swing.JInternalFrame {
    Koneksi kon;
    
    /**
     * Creates new form FormAkun
     */
    public FormPembayaran() {
        initComponents();
        kon = new Koneksi(); //Buat Koneksi
        tampilData(""); //menampilkan Data
    }
    
    private void tampilData(String filter){
       Object[] judulKolom = {"No Daftar","NIS","Nama", "Jurusan", "Total", "Keterangan"};
        DefaultTableModel model = new DefaultTableModel(null,judulKolom);
        tableData.setModel(model);
        try{
        String sql="select * from view_bayar where isnull(deletedAt) and ( nis like '%"+filter+"%' or nama like '%"+filter+"%' or keterangan like '%"+filter+"%' ) group by id";
        Statement perintah = kon.konekDB.createStatement();
        ResultSet rs = perintah.executeQuery(sql);
        while (rs.next()) {
        String txtNoDaftar =rs.getString("no_pend");
        String txtNis =rs.getString("nis");
        String txtNama =rs.getString("nama");
        String txtJurusan =rs.getString("nama_jurusan");
        String txtTotal =rs.getString("total");
        String txtKet =rs.getString("keterangan");
        String[] barisBaru = {txtNoDaftar, txtNis, txtNama, txtJurusan, txtTotal, txtKet};
        model.addRow(barisBaru);

        }
        }catch(Exception salahe){
        System.err.println("Gagal Tampil data: "+salahe.getMessage());
        }
    }
    
    private void tampilDataSiswa(String filter){
        Object[] judulKolom = {"No Daftar", "NIS","Nama", "Jurusan", "Kelas"};
        DefaultTableModel modelSiswa = new DefaultTableModel(null,judulKolom);
        tabelDataSiswa.setModel(modelSiswa);
        try{
        String sql="select * from view_siswa where nis like '%"+filter+"%' or nama like '%"+filter+"%' ";
        Statement perintah = kon.konekDB.createStatement();
        ResultSet rsSiswa = perintah.executeQuery(sql);
        while (rsSiswa.next()) {
        String txtNoDaftar =rsSiswa.getString("id");
        String txtNis =rsSiswa.getString("nis");
        String txtNama =rsSiswa.getString("nama");
        String txtJurusan =rsSiswa.getString("nama_jurusan");
        String txtKelas =rsSiswa.getString("kode_kelas");
        String[] barisBaru = {txtNoDaftar, txtNis, txtNama, txtJurusan, txtKelas};
        modelSiswa.addRow(barisBaru);

        }
        }catch(Exception salahe){
        System.err.println("Gagal Tampil data: "+salahe.getMessage());
        }
    }
    
    private void tampilTagihan(String no_pend){
        Object[] judulKolom = {"ID Biaya", "Nama Biaya", "Jumlah", "Dibayar", "Kekurangan"};
        DefaultTableModel modelTagihan = new DefaultTableModel(null,judulKolom);
        tabelTagihan.setModel(modelTagihan);
        System.out.println(no_pend);
        try{
        Statement perintah = kon.konekDB.createStatement();
        //baca siswa
        String sql="select * from siswa where id = '"+no_pend+"' ";
        ResultSet rsSiswa = perintah.executeQuery(sql);
        rsSiswa.next();
        String txtNoDaftar =rsSiswa.getString("id");
        String txtJurusan =rsSiswa.getString("jurusan");
        String txtAngkatan =rsSiswa.getString("angkatan");
        //baca tagihan biaya psb
        String sqlBiayaPsb="SELECT " +
        "biaya_pmb.id AS id, " +
        "biaya_pmb.nama_biaya AS nama_biaya, " +
        "biaya_pmb.jumlah AS jumlah, " +
        "COALESCE ( sum( view_bayar.jumlah_bayar ), 0 ) AS bayar, " +
        "(biaya_pmb.jumlah - COALESCE ( sum( view_bayar.jumlah_bayar ), 0 )) AS kekurangan " +

        "FROM " +
        "biaya_pmb LEFT JOIN view_bayar ON ( " +
        "view_bayar.biaya_id = biaya_pmb.id AND " +
        "view_bayar.jenis_biaya = 'D' AND " +
        "view_bayar.no_pend = '"+txtNoDaftar+"' " +
        ") " +
        "WHERE " +
        "biaya_pmb.tahun_angkatan= '"+txtAngkatan+"' AND biaya_pmb.jurusan = '"+txtJurusan+"' " +
        "GROUP BY biaya_pmb.id " +
        "ORDER BY biaya_pmb.nama_biaya";

        ResultSet rsBiayaPsb = perintah.executeQuery(sqlBiayaPsb);
        while (rsBiayaPsb.next()) {
        String txtId =rsBiayaPsb.getString("id");

        String txtNamaBiaya =rsBiayaPsb.getString("nama_biaya");
        String txtJumlah =rsBiayaPsb.getString("jumlah");
        String txtBayar =rsBiayaPsb.getString("bayar");
        String txtKekurangan =rsBiayaPsb.getString("kekurangan");
        String[] barisBaru = {txtId, txtNamaBiaya, txtJumlah, txtBayar, txtKekurangan};
        modelTagihan.addRow(barisBaru);

        }
        //baca tagihan biaya tetap
        String sqlBiayaTetap="SELECT " +
        "biaya_tetap.id AS id, " +

        "biaya_tetap.nama_biaya AS nama_biaya, " +
        "biaya_tetap.jumlah AS jumlah, " +
        "COALESCE ( sum( view_bayar.jumlah_bayar ), 0 ) AS bayar, " +

        "(biaya_tetap.jumlah - COALESCE ( sum( view_bayar.jumlah_bayar ), 0 )) AS kekurangan " +
        "FROM " +
        "biaya_tetap LEFT JOIN view_bayar ON ( " +
        "view_bayar.biaya_id = biaya_tetap.id AND " +
        "view_bayar.jenis_biaya = 'P' AND " +
        "view_bayar.no_pend = '"+txtNoDaftar+"' " +
        ") " +
        "WHERE " +
        "biaya_tetap.tahun_angkatan='"+txtAngkatan+"' AND biaya_tetap.jurusan= '"+txtJurusan+"' " +
        "GROUP BY biaya_tetap.id " +
        "ORDER BY biaya_tetap.nama_biaya";
        ResultSet rsBiayaTetap = perintah.executeQuery(sqlBiayaTetap);
        while (rsBiayaTetap.next()) {
        String txtId =rsBiayaTetap.getString("id");
        String txtNamaBiaya =rsBiayaTetap.getString("nama_biaya");
        String txtJumlah =rsBiayaTetap.getString("jumlah");
        String txtBayar =rsBiayaTetap.getString("bayar");

        String txtKekurangan =rsBiayaTetap.getString("kekurangan");

        String[] barisBaru = {txtId, txtNamaBiaya, txtJumlah, txtBayar, txtKekurangan};
        modelTagihan.addRow(barisBaru);

        }
        //baca tagihan biaya lain
        String sqlBiayaLain="SELECT " +
        "biaya_lain.id AS id, " +

        "biaya_lain.nama_biaya AS nama_biaya, " +
        "biaya_lain.jumlah AS jumlah, " +
        "COALESCE ( sum( view_bayar.jumlah_bayar ), 0 ) AS bayar, " +

        "(biaya_lain.jumlah - COALESCE ( sum( view_bayar.jumlah_bayar ), 0 )) AS kekurangan " +
        "FROM " +
        "biaya_lain LEFT JOIN view_bayar ON ( " +
        "view_bayar.biaya_id = biaya_lain.id AND " +
        "view_bayar.jenis_biaya = 'L' AND " +
        "view_bayar.no_pend = '"+txtNoDaftar+"' " +

        ") " +
        "WHERE " +
        "biaya_lain.jurusan = '"+txtJurusan+"' " +
        "GROUP BY biaya_lain.id " +
        "ORDER BY biaya_lain.nama_biaya";

        ResultSet rsBiayaLain = perintah.executeQuery(sqlBiayaLain);
        while (rsBiayaLain.next()) {
        String txtId =rsBiayaLain.getString("id");

        String txtNamaBiaya =rsBiayaLain.getString("nama_biaya");
        String txtJumlah =rsBiayaLain.getString("jumlah");
        String txtBayar =rsBiayaLain.getString("bayar");
        String txtKekurangan =rsBiayaLain.getString("kekurangan");

        String[] barisBaru = {txtId, txtNamaBiaya, txtJumlah, txtBayar, txtKekurangan};
        modelTagihan.addRow(barisBaru);

        }
        }catch(Exception salahe){
        System.err.println("Gagal Tampil data: "+salahe.getMessage());
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

        dialogTransaksi = new javax.swing.JDialog();
        hapusBayar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelTagihan = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelBayar = new javax.swing.JTable();
        tambahBayar = new javax.swing.JButton();
        simpanBayar = new javax.swing.JButton();
        totalBayar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dialogSiswa = new javax.swing.JDialog();
        pilih = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cariSiswa = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelDataSiswa = new javax.swing.JTable();
        pilih2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        tambah = new javax.swing.JButton();
        detail = new javax.swing.JButton();
        cari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        hapusBayar.setText("Hapus");
        hapusBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBayarActionPerformed(evt);
            }
        });

        tabelTagihan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabelTagihan);

        tabelBayar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tabelBayar);

        tambahBayar.setText("Tambah");
        tambahBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahBayarActionPerformed(evt);
            }
        });

        simpanBayar.setText("Bayar");
        simpanBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanBayarActionPerformed(evt);
            }
        });

        totalBayar.setText("0");

        jLabel2.setText("Total Bayar");

        javax.swing.GroupLayout dialogTransaksiLayout = new javax.swing.GroupLayout(dialogTransaksi.getContentPane());
        dialogTransaksi.getContentPane().setLayout(dialogTransaksiLayout);
        dialogTransaksiLayout.setHorizontalGroup(
            dialogTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogTransaksiLayout.createSequentialGroup()
                        .addComponent(tambahBayar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hapusBayar))
                    .addGroup(dialogTransaksiLayout.createSequentialGroup()
                        .addGroup(dialogTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(dialogTransaksiLayout.createSequentialGroup()
                                .addComponent(simpanBayar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dialogTransaksiLayout.setVerticalGroup(
            dialogTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hapusBayar)
                    .addComponent(tambahBayar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(dialogTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanBayar)
                    .addComponent(totalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        pilih.setText("Pilih");
        pilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihActionPerformed(evt);
            }
        });

        jLabel7.setText("Cari");

        cariSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariSiswaActionPerformed(evt);
            }
        });

        tabelDataSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tabelDataSiswa);

        javax.swing.GroupLayout dialogSiswaLayout = new javax.swing.GroupLayout(dialogSiswa.getContentPane());
        dialogSiswa.getContentPane().setLayout(dialogSiswaLayout);
        dialogSiswaLayout.setHorizontalGroup(
            dialogSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogSiswaLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cariSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogSiswaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pilih)
                .addContainerGap())
        );
        dialogSiswaLayout.setVerticalGroup(
            dialogSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cariSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(pilih)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pilih2.setText("Pilih");
        pilih2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilih2ActionPerformed(evt);
            }
        });

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setTitle("FORM PEMBAYARAN");

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableData);

        tambah.setText("Tambah");
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });

        detail.setText("Detail");
        detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailActionPerformed(evt);
            }
        });

        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        jLabel1.setText("Cari");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(detail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tambah)
                    .addComponent(detail)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        getAccessibleContext().setAccessibleName("FORM KELATITS");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        // TODO add your handling code here:
        tampilDataSiswa("");
        dialogTransaksi.pack();
        dialogTransaksi.setLocationRelativeTo(null);
        dialogTransaksi.setModal(true);
        dialogTransaksi.setVisible(true);
    }//GEN-LAST:event_tambahActionPerformed

    private void detailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_detailActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
        String txtCari = cari.getText();
        tampilData(txtCari);
    }//GEN-LAST:event_cariActionPerformed

    private void hapusBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBayarActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_hapusBayarActionPerformed

    private void pilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihActionPerformed
        // TODO add your handling code here:
        int baris_index = tabelDataSiswa.getSelectedRow();
if (baris_index>=0) {
String no_pend =(tabelDataSiswa.getModel().getValueAt(baris_index, 0).toString());
String nis =(tabelDataSiswa.getModel().getValueAt(baris_index, 1).toString());
String nama =(tabelDataSiswa.getModel().getValueAt(baris_index, 2).toString());
tampilTagihan(no_pend);
dialogSiswa.setVisible(false);
dialogTransaksi.setTitle("Transaksi: "+nis+" - "+nama);
dialogTransaksi.pack();
dialogTransaksi.setLocationRelativeTo(null);
dialogTransaksi.setModal(true);
dialogTransaksi.setVisible(true);
} else {
JOptionPane.showMessageDialog(dialogSiswa, "Belum ada data yang dipilih");
}
    }//GEN-LAST:event_pilihActionPerformed

    private void pilih2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilih2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pilih2ActionPerformed

    private void tambahBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahBayarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_tambahBayarActionPerformed

    private void simpanBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simpanBayarActionPerformed

    private void cariSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariSiswaActionPerformed
        // TODO add your handling code here:
        String txtCari = cariSiswa.getText();
        tampilDataSiswa(txtCari);
    }//GEN-LAST:event_cariSiswaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cari;
    private javax.swing.JTextField cariSiswa;
    private javax.swing.JButton detail;
    private javax.swing.JDialog dialogSiswa;
    private javax.swing.JDialog dialogTransaksi;
    private javax.swing.JButton hapusBayar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton pilih;
    private javax.swing.JButton pilih2;
    private javax.swing.JButton simpanBayar;
    private javax.swing.JTable tabelBayar;
    private javax.swing.JTable tabelDataSiswa;
    private javax.swing.JTable tabelTagihan;
    private javax.swing.JTable tableData;
    private javax.swing.JButton tambah;
    private javax.swing.JButton tambahBayar;
    private javax.swing.JTextField totalBayar;
    // End of variables declaration//GEN-END:variables
}
