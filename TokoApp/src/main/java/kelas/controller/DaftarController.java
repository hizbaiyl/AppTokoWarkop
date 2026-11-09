package kelas.controller;

import java.io.IOException;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.model.Produk;

public class DaftarController {

    @FXML
    private TableView<Produk> tabel;
    @FXML
    private TableColumn<Produk, String> kodeCol;
    @FXML
    private TableColumn<Produk, String> namaCol;
    @FXML
    private TableColumn<Produk, String> kategoriCol;
    @FXML
    private TableColumn<Produk, Long> hargaCol;
    @FXML
    private TextField cariField;
    @FXML
    private Label pesanLabel;

    private Navigator navigator;
    private DataProduk data;
    private final ObservableList<Produk> dataTampilan
            = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // KOLOM KODE
        PropertyValueFactory<Produk, String> sumberKode
                = new PropertyValueFactory<Produk, String>("kode");

        kodeCol.setCellValueFactory(sumberKode);

        // KOLOM NAMA PRODUK
        PropertyValueFactory<Produk, String> sumberNama
                = new PropertyValueFactory<Produk, String>("nama");

        namaCol.setCellValueFactory(sumberNama);

        // KOLOM KATEGORI
        PropertyValueFactory<Produk, String> sumberKategori
                = new PropertyValueFactory<Produk, String>("kategori");

        kategoriCol.setCellValueFactory(sumberKategori);

        // KOLOM HARGA
        PropertyValueFactory<Produk, Long> sumberHarga
                = new PropertyValueFactory<Produk, Long>("harga");

        hargaCol.setCellValueFactory(sumberHarga);

        tabel.setItems(dataTampilan);
        tabel.setPlaceholder(new Label("Belum ada data yang sesuai."));
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        cari();
    }

    @FXML
    private void cari() {
        if (data == null) {
            return;
        }
        dataTampilan.clear();
        String kata = cariField.getText().trim().toLowerCase(Locale.ROOT);

        for (Produk produk : data.getDaftar()) {
            String nama = produk.getNama().toLowerCase(Locale.ROOT);
            if (nama.contains(kata)) {
                dataTampilan.add(produk);
            }
        }
        tabel.sort();
        pesanLabel.setText("Jumlah data yang tampil: " + dataTampilan.size());
    }

    @FXML
    private void resetPencarian() {
        cariField.clear();
        cari();
    }

    @FXML
    private void tambah() {
        try {
            navigator.bukaForm();
        } catch (IOException e) {
            pesanLabel.setText("Form gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void detail() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();
        if (produk == null) {
            pesanLabel.setText("Pilih satu baris dahulu.");
            return;
        }
        try {
            navigator.bukaDetail(produk);
        } catch (IOException e) {
            pesanLabel.setText("Detail gagal dibuka.");
            e.printStackTrace();
        }
    }
}
