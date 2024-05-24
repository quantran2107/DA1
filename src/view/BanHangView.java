/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.ChatLieu;
import model.ChiTietSanPham;
import model.KichThuoc;
import model.MauSac;
import model.SanPham;
import service.servicImp.ChiTietSanPhamServiceImp;
import javax.swing.JTable;
import model.Events;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import service.servicImp.HoaDonChiTietServiceImp;
import service.servicImp.HoaDonServiceImp;
import service.servicImp.KhachHangServiceImp;
import service.servicImp.KhuyenMaiServiceImp;
import service.servicImp.NhanVienServiceImp;
import util.PDFGene;
//
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import service.servicImp.ChatLieuServiceImp;
import service.servicImp.KichThuocServiceImp;
import service.servicImp.MauSacServiceImp;
import service.servicImp.SanPhamServiceImp;

/**
 *
 * @author Admin
 */
public class BanHangView extends javax.swing.JPanel implements Runnable, ThreadFactory {

    DefaultTableModel molGH = new DefaultTableModel();
    DefaultTableModel molCTSP = new DefaultTableModel();
    ChiTietSanPhamServiceImp serviceCTSP = new ChiTietSanPhamServiceImp();
    DefaultComboBoxModel<Events> cbxEvents = new DefaultComboBoxModel<>();
    static int indexHoaDonCho = -1;
    int trangCTSP = 1, soTrangCTSP, tongBanGhiCTSP, index = -1;
    HoaDonServiceImp serviceHD = new HoaDonServiceImp();
    DefaultTableModel molHDC = new DefaultTableModel();
    int so = serviceHD.countHoaDon();
    int so2 = serviceHD.countHoaDon();
    HoaDonChiTietServiceImp serviceHDCT = new HoaDonChiTietServiceImp();
    KhuyenMaiServiceImp serviceKM = new KhuyenMaiServiceImp();
    NhanVienServiceImp serviceNV = new NhanVienServiceImp();
    KhachHangServiceImp serviceKH = new KhachHangServiceImp();
    MauSacServiceImp serviceMS = new MauSacServiceImp();
    ChatLieuServiceImp serviceCl = new ChatLieuServiceImp();
    KichThuocServiceImp serviceKT = new KichThuocServiceImp();
    SanPhamServiceImp serviceSP = new SanPhamServiceImp();
    DefaultComboBoxModel<MauSac> cbxMauSacLoc = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<ChatLieu> cbxChatLieuLoc = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<KichThuoc> cbxKichThuocLoc = new DefaultComboBoxModel<>();
    PDFGene pdf = new PDFGene();
    Random rd = new Random();
    DangNhapView dangNhapView = new DangNhapView();
    String userName = dangNhapView.getTaiKhoan();
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    /**
     * Creates new form BanHangView
     */
    public BanHangView() {
        initComponents();
        this.setSize(1300, 755);
        loadPageCTSP();
        fillTableHDC(serviceHD.getHoaDonCho());
        molGH = (DefaultTableModel) tblGioHang.getModel();
        molGH.setRowCount(0);
        txtMaHDBH2.setEnabled(false);
        txtTongTienBH2.setEnabled(false);
        txtTenEV.setEnabled(false);
        txtMucGiam.setEnabled(false);
              loadLocChatLieu(serviceCl.getAll());
        loadLocMauSac(serviceMS.getAll());
        loadLocKichThuoc(serviceKT.getAll());
//        initWebcam();
    }

    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        if (webcam.isOpen()) {
            webcam.close();
        }
        webcam.setViewSize(size);
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        pnlWebCam.setLayout(new BorderLayout());
        pnlWebCam.add(panel, 0);
        executor.execute(this);
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            try {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                result = new MultiFormatReader().decode(bitmap);
            } catch (Exception e) {

            }

            if (result != null) {
                indexHoaDonCho = tblHoaDonCho.getSelectedRow();
                if (indexHoaDonCho == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng Tạo hoá đơn");
                    continue;
                } else {
                    List<ChiTietSanPham> list = serviceCTSP.getList(result.getText());
                    txtTest.setText(result.getText());
                    int indexs = tblChiTietSanPham.getSelectedRow();
                    int indexGioHang = -1;
                    if (!serviceCTSP.checkMaQR(result.getText())) {
                        JOptionPane.showMessageDialog(this, "Mã QR không tồn tại");
                        continue;
                    }
                    try {
                        boolean checkInput = true;
                        String input = JOptionPane.showInputDialog(this, "Mời nhập số lượng");
                        if (input == null || input.isEmpty()) {
                            checkInput = false;
                            return;
                        }
                        String maFake = null;
                        int soLuongFake = 0;
                        double giaFake = 0;
                        String tenFake = null;
                        int soLuongTonFake = 0;
                        for (ChiTietSanPham chiTietSanPham : list) {
                            if (chiTietSanPham.getMaChiTietSanPham().equals(result.getText())) {
                                maFake = chiTietSanPham.getMaChiTietSanPham();
                                soLuongFake = Integer.parseInt(input);
                                if (soLuongFake > chiTietSanPham.getSoLuong()) {
                                    JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ, vui lòng nhập lại");   
                                    return;
                                } else if (soLuongFake < 1) {
                                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ, vui lòng nhập lại");
                                    return;
                                }
                                soLuongTonFake = chiTietSanPham.getSoLuong() - soLuongFake;
                                giaFake = chiTietSanPham.getGia();
                                tenFake = chiTietSanPham.getSanPham().getTenSanPham();
                            }
                        }

                        ChiTietSanPham ctsps = serviceCTSP.getOne(maFake);

                        if (tblGioHang.getRowCount() > 0) {
                            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                                if (tblGioHang.getValueAt(i, 0) != null) {
                                    if (tblGioHang.getValueAt(i, 0).toString().equals(maFake)) {
                                        indexGioHang = i;
                                        break;
                                    }
                                }
                            }
                        }
                        if (indexGioHang != -1) {
                            for (ChiTietSanPham chiTietSanPham : list) {
                                if (chiTietSanPham.getMaChiTietSanPham().equals(result.getText())) {
                                    int soLuongHienTai = Integer.parseInt(tblGioHang.getValueAt(indexGioHang, 1).toString());
                                    int soLuonngSauKhiThem = soLuongHienTai + Integer.parseInt(input);
                                    if (soLuonngSauKhiThem > chiTietSanPham.getSoLuong()) {
                                        JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ, vui lòng nhập lại");
                                        return;
                                    }
                                    tblGioHang.setValueAt(soLuonngSauKhiThem, indexGioHang, 1);
                                    double thanhTienSauKhiThem = Math.round((soLuonngSauKhiThem * giaFake) * 100) / 100;
                                    tblGioHang.setValueAt(thanhTienSauKhiThem, indexGioHang, 3);
                                    soLuongTonFake = chiTietSanPham.getSoLuong() - soLuonngSauKhiThem;
                                    serviceCTSP.capNhatSoLuongThanhToanCong(Integer.parseInt(input), maFake);
                                    loadPageCTSP();
                                }
                            }
                        } else {
                            fillTableGioHang(tblGioHang, ctsps, soLuongFake);
                            serviceCTSP.capNhatSoLuongThanhToanCong(Integer.parseInt(input), maFake);
                            loadPageCTSP();
                            indexHoaDonCho = tblHoaDonCho.getSelectedRow();
                            String maHD = tblHoaDonCho.getValueAt(indexHoaDonCho, 1).toString();
                            String parentDirectory = "D:\\PRO1041_DuAn1";
                            String newDirectoryName = "GioHang";
                            luuGioHangVaoFile(maHD, parentDirectory, newDirectoryName);
                        }
                        serviceCTSP.updateTrangThaiSoLuong();
                        loadPageCTSP();
                        fillDonHang2();
                        tinhThua();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }
    
     public void mtam2() {
        if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() == -1) {
            ChatLieu cl = (ChatLieu) cbxChatLieuLoc.getSelectedItem();
            String tenTimCL = cl.toString();
            String tenList ="";
            fillTableChiTietSanPham(serviceCTSP.getListLocCL(tenList, tenTimCL));
        } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() == -1) {
            MauSac ms = (MauSac) cbxMauSacLoc.getSelectedItem();
            String tenTimMS = ms.toString();
            String tenList ="";
            fillTableChiTietSanPham(serviceCTSP.getListLocMS(tenList, tenTimMS));
        } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() != -1) {
            KichThuoc kt = (KichThuoc) cbxKichThuocLoc.getSelectedItem();
            String tenTimKT = kt.toString();
            String tenList ="";
            fillTableChiTietSanPham(serviceCTSP.getListLocKT(tenList, tenTimKT));
        } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() == -1) {
            ChatLieu cl = (ChatLieu) cbxChatLieuLoc.getSelectedItem();
            String tenTimCL = cl.toString();
            MauSac ms = (MauSac) cbxMauSacLoc.getSelectedItem();
            String tenTimMS = ms.toString();
            String tenList ="";
            fillTableChiTietSanPham(serviceCTSP.getListLocCLMS(tenList, tenTimCL, tenTimMS));
        } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
            MauSac ms = (MauSac) cbxMauSacLoc.getSelectedItem();
            String tenTimMS = ms.toString();
            KichThuoc kt = (KichThuoc) cbxKichThuocLoc.getSelectedItem();
            String tenTimKT = kt.toString();
            String tenList ="";
            fillTableChiTietSanPham(serviceCTSP.getListLocMSKT(tenList, tenTimMS, tenTimKT));
        } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() != -1) {
            ChatLieu cl = (ChatLieu) cbxChatLieuLoc.getSelectedItem();
            String tenTimCL = cl.toString();
            KichThuoc kt = (KichThuoc) cbxKichThuocLoc.getSelectedItem();
            String tenTimKT = kt.toString();
            String tenList ="";
            fillTableChiTietSanPham(serviceCTSP.getListLocCLKT(tenList, tenTimCL, tenTimKT));
        } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
            ChatLieu cl = (ChatLieu) cbxChatLieuLoc.getSelectedItem();
            String tenTimCL = cl.toString();
            MauSac ms = (MauSac) cbxMauSacLoc.getSelectedItem();
            String tenTimMS = ms.toString();
            KichThuoc kt = (KichThuoc) cbxKichThuocLoc.getSelectedItem();
            String tenTimKT = kt.toString();
            String tenList ="";
            fillTableChiTietSanPham(serviceCTSP.getListLoc(tenList, tenTimCL, tenTimMS, tenTimKT));
        }

    }

    
    public void loadLocChatLieu(List<ChatLieu> list) {
        cbxChatLieuLoc.removeAllElements();
        for (ChatLieu chatLieu : list) {
            cbxChatLieuLoc.addElement(chatLieu);
        }
        cboLocChat.setModel((ComboBoxModel) cbxChatLieuLoc);
        cboLocChat.setSelectedIndex(-1);
    }

    public void loadLocMauSac(List<MauSac> list) {
        cbxMauSacLoc.removeAllElements();
        for (MauSac mauSac : list) {
            cbxMauSacLoc.addElement(mauSac);
        }
        cboLocMau.setModel((ComboBoxModel) cbxMauSacLoc);
        cboLocMau.setSelectedIndex(-1);
    }

    public void loadLocKichThuoc(List<KichThuoc> list) {
        cbxKichThuocLoc.removeAllElements();
        for (KichThuoc kichThuoc : list) {
            cbxKichThuocLoc.addElement(kichThuoc);
        }
        cboLocKich.setModel((ComboBoxModel) cbxKichThuocLoc);
        cboLocKich.setSelectedIndex(-1);
    }

    public String maTangTuDong(String HD) {
        so++;
        String maTuDong = "";
        String chuHoa = "QWERTYUIOPASDFGHJKLZXCVBNM";
        char[] kyTu = new char[2];
        for (int i = 0; i < 2; i++) {
            kyTu[i] = chuHoa.charAt(rd.nextInt(chuHoa.length()));
            maTuDong += kyTu[i];
        }
        String maHD = HD + String.format("%04d", so) + maTuDong;
        return maHD;
    }

    public String maTangTuDong2(String HD) {
        so2++;
        String maHD = HD + String.format("%04d", so2);;
        return maHD;
    }

    public void fillTableHDC(List<HoaDon> list) {
        molHDC = (DefaultTableModel) tblHoaDonCho.getModel();
        molHDC.setRowCount(0);
        for (HoaDon item : list) {
            molHDC.addRow(new Object[]{
                this.tblHoaDonCho.getRowCount() + 1, item.getMaHoaDon(), item.getNhanVien().getHoTen(),
                item.getNgayTao(), item.getTrangThai()
            });
        }
    }

    public void fillTableChiTietSanPham(List<ChiTietSanPham> list) {
        molCTSP = (DefaultTableModel) tblChiTietSanPham.getModel();
        molCTSP.setRowCount(0);
        for (ChiTietSanPham chiTietSanPham : list) {
            molCTSP.addRow(new Object[]{
                chiTietSanPham.getMaChiTietSanPham(),
                chiTietSanPham.getSoLuong(), chiTietSanPham.getGia(),
                chiTietSanPham.getSanPham(),
                chiTietSanPham.getChatLieu(), chiTietSanPham.getMauSac(),
                chiTietSanPham.getKichThuoc(),
                chiTietSanPham.isTrangThai() ? "Còn hàng" : "Hết hàng"
            });
        }
    }

    public void fillTableGioHang(JTable tbl, ChiTietSanPham ctsp, int SoLuongMua) {
        DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
        Object[] rowData = {
            ctsp.getMaChiTietSanPham(),
            SoLuongMua,
            ctsp.getGia(),
            SoLuongMua * ctsp.getGia(),
            ctsp.getSanPham().getTenSanPham(),
            ctsp.getChatLieu().getTenChatLieu(),
            ctsp.getMauSac().getTenMauSac(),
            ctsp.getKichThuoc().getTenKichThuoc()
        };
        dtm.insertRow(0, rowData);
    }

    public void loadPageCTSP() {
        String tenPage = new SanPhamView().getTenSPs(null, new SanPhamView().getTenSanPham());
        tongBanGhiCTSP = serviceCTSP.tongBanGhi2();
        if (tongBanGhiCTSP % 5 == 0) {
            soTrangCTSP = tongBanGhiCTSP / 5;
        } else {
            soTrangCTSP = tongBanGhiCTSP / 5 + 1;
        }
        lbSoTrang2.setText(trangCTSP + " of " + soTrangCTSP);
        fillTableChiTietSanPham(serviceCTSP.listPageCTSP2(trangCTSP));
    }

    public void fillTableHDC2() {
        DangNhapView dangNhapView = new DangNhapView();
        String maHD = maTangTuDong("HD");
        txtMaHDBH2.setText(maTangTuDong2("HD"));
        LocalDate ngayTao = LocalDate.now();
        molHDC.addRow(new Object[]{
            this.tblHoaDonCho.getRowCount() + 1, maHD, dangNhapView.getTenNV(), ngayTao, "Chờ thanh toán"
        });
    }

    public void luuGioHangVaoFile(String maHD, String parentDirectory, String newDirectoryName) {
        molGH = (DefaultTableModel) tblGioHang.getModel();
        String parentPath = parentDirectory + File.separator;
        File parentDir = new File(parentPath);
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        File childDir = new File(parentPath + newDirectoryName);
        if (!childDir.exists()) {
            childDir.mkdirs();
        }

        String fileName = "GioHang_" + maHD + ".csv";
        String filePath = childDir.getPath() + File.separator + fileName;
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            for (int i = 0; i < molGH.getColumnCount(); i++) {
                fileWriter.append(molGH.getColumnName(i));
                if (i < molGH.getColumnCount() - 1) {
                    fileWriter.append(",");
                }
            }
            fileWriter.append("\n");
            for (int row = 0; row < molGH.getRowCount(); row++) {
                for (int col = 0; col < molGH.getColumnCount(); col++) {
                    fileWriter.append(molGH.getValueAt(row, col).toString());
                    if (col < molGH.getColumnCount() - 1) {
                        fileWriter.append(",");
                    }
                }
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTableDataFromFile(String directory, String fileName) {
        molGH = (DefaultTableModel) tblGioHang.getModel();
        molGH.setRowCount(0);
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean skipFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                molGH.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean validBH() {
        if (txtMaKHBH2.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã KH");
            return false;
        } else if (serviceKH.getOne(txtMaKHBH2.getText()) == null) {
            JOptionPane.showMessageDialog(this, "Mã KH vừa nhập chưa được đăng ký");
            return false;
        }
        return true;
    }

    public String giuSo(String x) {
        String so = x.replaceAll("[^0-9]", "");
        return so;
    }

    public Double fillDonHang() {
        int x = tblGioHang.getRowCount();
        if (x == 0) {
            return 0.0;
        }
        Double sum = 0.0;
        for (int i = 0; i < x; i++) {
            sum += Double.parseDouble(tblGioHang.getValueAt(i, 3).toString());
        }
        return sum;
    }

    public void fillDonHang2() {
        Double sum = fillDonHang();
        Double tongTien;
        List<Events> list = serviceKM.getActive3(sum);
        if (list.isEmpty()) {
            txtTongTienBH2.setText(phanCach(sum));
            txtTenEV.setText("");
            txtMucGiam.setText("");
        } else if (list.size() == 1) {
            txtTenEV.setText(list.get(0).toString());
            txtMucGiam.setText(list.get(0).getMucGiamGia());
            if (list.get(0).isHinhThuc()) {
                tongTien = sum - Double.valueOf(giuSo(list.get(0).getMucGiamGia()));
                txtTongTienBH2.setText(phanCach(tongTien));
            } else {
                tongTien = sum * (1 - (Double.valueOf(giuSo(list.get(0).getMucGiamGia())) / 100));
                txtTongTienBH2.setText(phanCach(tongTien));
            }
        } else {
            Double giamPhanTram = Double.valueOf(giuSo(list.get(0).getMucGiamGia())) / 100 * sum;
            Double giamThang = Double.valueOf(giuSo(list.get(1).getMucGiamGia()));
            if (giamPhanTram < giamThang) {
                txtTongTienBH2.setText(phanCach(sum - giamThang));
                txtTenEV.setText(list.get(1).toString());
                txtMucGiam.setText(list.get(1).getMucGiamGia());
            } else {
                txtTongTienBH2.setText(phanCach(sum - giamPhanTram));
                txtTenEV.setText(list.get(0).toString());
                txtMucGiam.setText(list.get(0).getMucGiamGia());
            }
        }
    }

    public HoaDon getFormBH() {
        String maHD = txtMaHDBH2.getText();
        NhanVien nhanVien = serviceNV.timTheoUserName(userName);
        String maKH = txtMaKHBH2.getText();
        KhachHang khachHang = serviceKH.getOne(maKH);
        Date ngayTao = new Date();
        Double tongTien = Double.valueOf(boPhanCach(txtTongTienBH2.getText()));
        String ghiChu = txtGhiChu.getText();
        Events ev;
        Double sum = fillDonHang();
        List<Events> list = serviceKM.getActive3(sum);
        if (list.size() == 0) {
            ev = null;
        } else if (list.size() == 1) {
            ev = list.get(0);
        } else {
            Double giamPhanTram = Double.valueOf(giuSo(list.get(0).getMucGiamGia())) / 100 * sum;
            Double giamThang = Double.valueOf(giuSo(list.get(1).getMucGiamGia()));
            if (giamPhanTram < giamThang) {
                ev = list.get(1);
            } else {
                ev = list.get(0);
            }
        }
        return new HoaDon(maHD, nhanVien, khachHang, ngayTao, tongTien, "Đã thanh toán", ghiChu, ev);
    }

    public String phanCach(Double x) {
        NumberFormat fm = NumberFormat.getNumberInstance(Locale.US);
        return fm.format(x);
    }

    public String boPhanCach(String x) {
        String so = x.replace(",", "");
        if (so.isBlank()) {
            return "0";
        }
        try {
            Double temp = Double.valueOf(so);
            return String.format("%.0f", temp);
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public void tinhThua() {
        Double tongTien = Double.valueOf(boPhanCach(txtTongTienBH2.getText()));
        txtTienKhachBH2.setText(phanCach(Double.valueOf(boPhanCach(txtTienKhachBH2.getText()))));
        Double tienKhach = Double.valueOf(boPhanCach(txtTienKhachBH2.getText()));
        Double tienThua = tienKhach - tongTien;
        txtTienThuaBH2.setText(phanCach(tienThua));
    }

    public void detailHD(HoaDon hd) {
        txtMaHDBH2.setText(hd.getMaHoaDon());
        if (hd.getKhachHang().getMaKhachHang().equals("KHNE")) {
            txtMaKHBH2.setText("");
        } else {
            txtMaKHBH2.setText(hd.getKhachHang().getMaKhachHang());
        }
        txtTongTienBH2.setText(phanCach(fillDonHang()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDonCho = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        btnXoaSP = new javax.swing.JButton();
        btnXoaTatCaSP = new javax.swing.JButton();
        txtTest = new javax.swing.JTextField();
        btnQR = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietSanPham = new javax.swing.JTable();
        btnThemGioHang = new javax.swing.JButton();
        btnDau2 = new javax.swing.JButton();
        btnLui2 = new javax.swing.JButton();
        lbSoTrang2 = new javax.swing.JLabel();
        btnTien2 = new javax.swing.JButton();
        btnCuoi2 = new javax.swing.JButton();
        cboLocChat = new javax.swing.JComboBox<>();
        cboLocMau = new javax.swing.JComboBox<>();
        cboLocKich = new javax.swing.JComboBox<>();
        btnReset = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnThanhToanBH2 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtMaHDBH2 = new javax.swing.JTextField();
        txtMaKHBH2 = new javax.swing.JTextField();
        txtTongTienBH2 = new javax.swing.JTextField();
        txtTienKhachBH2 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtTienThuaBH2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnTaoHoaDonCho = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTenEV = new javax.swing.JTextField();
        txtMucGiam = new javax.swing.JTextField();
        btnHuyDonHang = new javax.swing.JButton();
        pnlWebCam = new javax.swing.JPanel();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chờ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDonCho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Tên nhân viên", "Ngày tạo", "Trạng thái"
            }
        ));
        tblHoaDonCho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDonCho);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Số lượng", "Giá", "Thành tiền", "Tên sản phẩm", "Chất liệu", "Màu sắc", "Kích thước"
            }
        ));
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);

        btnXoaSP.setText("Xóa sản phẩm");
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        btnXoaTatCaSP.setText("Xóa tất cả");
        btnXoaTatCaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTatCaSPActionPerformed(evt);
            }
        });

        btnQR.setText("Quét QR");
        btnQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQRActionPerformed(evt);
            }
        });

        btnSuaSP.setText("Sửa sản phẩm");
        btnSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnQR, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnXoaSP)
                        .addGap(35, 35, 35)
                        .addComponent(btnXoaTatCaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaSP)
                        .addGap(37, 37, 37)
                        .addComponent(txtTest, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaTatCaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTest)
                    .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblChiTietSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã CTSP", "Số lượng tồn", "Giá", "Tên sản phẩm", "Chất liệu", "Màu sắc", "Kích thước", "Trạng thái"
            }
        ));
        tblChiTietSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblChiTietSanPham);

        btnThemGioHang.setText("Thêm vào giỏ hàng");
        btnThemGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemGioHangActionPerformed(evt);
            }
        });

        btnDau2.setText("Pre");
        btnDau2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDau2ActionPerformed(evt);
            }
        });

        btnLui2.setText("Lùi");
        btnLui2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLui2ActionPerformed(evt);
            }
        });

        lbSoTrang2.setText("Số trang");

        btnTien2.setText("Tiến");
        btnTien2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTien2ActionPerformed(evt);
            }
        });

        btnCuoi2.setText("Next");
        btnCuoi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuoi2ActionPerformed(evt);
            }
        });

        cboLocChat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLocChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboLocChatMouseClicked(evt);
            }
        });
        cboLocChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocChatActionPerformed(evt);
            }
        });

        cboLocMau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLocMau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboLocMauMouseClicked(evt);
            }
        });
        cboLocMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocMauActionPerformed(evt);
            }
        });

        cboLocKich.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLocKich.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboLocKichMouseClicked(evt);
            }
        });
        cboLocKich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocKichActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel5.setText("Chất liệu");

        jLabel6.setText("Màu sắc");

        jLabel7.setText("Kích thước");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnThemGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(132, 132, 132)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cboLocChat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboLocMau, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cboLocKich, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReset))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(btnDau2)
                .addGap(33, 33, 33)
                .addComponent(btnLui2)
                .addGap(41, 41, 41)
                .addComponent(lbSoTrang2)
                .addGap(44, 44, 44)
                .addComponent(btnTien2)
                .addGap(38, 38, 38)
                .addComponent(btnCuoi2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboLocKich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnReset))
                            .addComponent(cboLocMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboLocChat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnThemGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCuoi2)
                    .addComponent(btnTien2)
                    .addComponent(lbSoTrang2)
                    .addComponent(btnLui2)
                    .addComponent(btnDau2))
                .addGap(14, 14, 14))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btnThanhToanBH2.setText("Thanh toán");
        btnThanhToanBH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanBH2ActionPerformed(evt);
            }
        });

        jLabel18.setText("Mã HĐ");

        jLabel20.setText("Mã KH");

        jLabel22.setText("Tổng tiền");

        jLabel23.setText("Tiền khách đưa");

        txtTienKhachBH2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachBH2tienKhachNhap(evt);
            }
        });

        jLabel24.setText("Tiền thừa");

        jLabel2.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        btnTaoHoaDonCho.setText("Tạo hóa đơn");
        btnTaoHoaDonCho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaoHoaDonChoMouseClicked(evt);
            }
        });
        btnTaoHoaDonCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonChoActionPerformed(evt);
            }
        });

        jLabel3.setText("Tên Event");

        jLabel4.setText("Mức giảm");

        btnHuyDonHang.setText("Hủy đơn hàng");
        btnHuyDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuyDonHangMouseClicked(evt);
            }
        });
        btnHuyDonHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyDonHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnThanhToanBH2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnTaoHoaDonCho, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnHuyDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel20)
                            .addComponent(jLabel24)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTienThuaBH2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTienKhachBH2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(txtMaKHBH2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaHDBH2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTongTienBH2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtTenEV, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                        .addComponent(txtMucGiam))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtMaHDBH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtMaKHBH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtTongTienBH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtTienKhachBH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtTienThuaBH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenEV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMucGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(btnThanhToanBH2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoHoaDonCho, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlWebCam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        pnlWebCam.setForeground(new java.awt.Color(0, 51, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlWebCam, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlWebCam, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemGioHangActionPerformed
        int indexs = tblChiTietSanPham.getSelectedRow();
        indexHoaDonCho = tblHoaDonCho.getSelectedRow();
        int indexGioHang = -1;
        if (indexHoaDonCho == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng Tạo hoá đơn");
        } else {
            if (indexs == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm");
                return;
            }
            String input = JOptionPane.showInputDialog(this, "Mời nhập số lượng");
            if (input == null || input.isEmpty()) {
                return;
            }
            try {
                int soLuongMua = Integer.parseInt(input);
                int soLuongTon = Integer.parseInt(tblChiTietSanPham.getValueAt(indexs, 1).toString());
                if (soLuongMua > soLuongTon) {
                    JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ, vui lòng nhập lại");
                    return;
                } else if (soLuongMua < 1) {
                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ, vui lòng nhập lại");
                    return;
                }
                String ma = tblChiTietSanPham.getValueAt(indexs, 0).toString();
                int soLuong = Integer.parseInt(tblChiTietSanPham.getValueAt(indexs, 1).toString());
                double gia = Double.parseDouble(tblChiTietSanPham.getValueAt(indexs, 2).toString());
                String ten = tblChiTietSanPham.getValueAt(indexs, 3).toString();
                double thanhTien = Math.round((soLuong * gia) * 100) / 100;
                ChiTietSanPham ctsps = serviceCTSP.getOne(ma);
                if (tblGioHang.getRowCount() > 0) {
                    for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                        if (tblGioHang.getValueAt(i, 0) != null) {
                            if (tblGioHang.getValueAt(i, 0).toString().equals(ma)) {
                                indexGioHang = i;
                                break;
                            }
                        }
                    }
                }
                if (indexGioHang != -1) {
                    int soLuongHienTai = Integer.parseInt(tblGioHang.getValueAt(indexGioHang, 1).toString());
                    int soLuonngSauKhiThem = soLuongHienTai + Integer.parseInt(input);
                    tblGioHang.setValueAt(soLuonngSauKhiThem, indexGioHang, 1);
                    double thanhTienSauKhiThem = Math.round((soLuonngSauKhiThem * gia) * 100) / 100;
                    tblGioHang.setValueAt(thanhTienSauKhiThem, indexGioHang, 3);
                    serviceCTSP.capNhatSoLuongThanhToanCong(Integer.parseInt(input), ma);
                } else {
                    fillTableGioHang(tblGioHang, ctsps, Integer.parseInt(input));
                    serviceCTSP.capNhatSoLuongThanhToanCong(Integer.parseInt(input), ma);
                    indexHoaDonCho = tblHoaDonCho.getSelectedRow();
                    String maHD = tblHoaDonCho.getValueAt(indexHoaDonCho, 1).toString();
                    String parentDirectory = "D:\\PRO1041_DuAn1";
                    String newDirectoryName = "GioHang";
                    luuGioHangVaoFile(maHD, parentDirectory, newDirectoryName);
                }
                soLuongTon = soLuongTon - soLuongMua;
                tblChiTietSanPham.setValueAt(soLuongTon, indexs, 1);
                serviceCTSP.updateTrangThaiSoLuong();
                loadPageCTSP();
                fillDonHang2();
                tinhThua();
            } catch (Exception e) {
                return;
            }
        }
    }//GEN-LAST:event_btnThemGioHangActionPerformed

    private void tblChiTietSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietSanPhamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblChiTietSanPhamMouseClicked

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void btnTaoHoaDonChoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaoHoaDonChoMouseClicked
        // TODO add your handling code here:
        if (tblHoaDonCho.getRowCount() == 10) {
            JOptionPane.showMessageDialog(this, "Số lượng hóa đơn chờ không được lớn hơn 10");
        } else {
            String maHD = maTangTuDong("HD");
            NhanVien nhanVien = new NhanVien(dangNhapView.getMaNV());
            HoaDon hd = new HoaDon(maHD, nhanVien, null, null);
            KhachHang kh = serviceKH.getOne("KHNE");
            HoaDon hd2 = new HoaDon(maHD, kh);
            serviceHD.themHoaDonCho(hd);
            fillTableHDC(serviceHD.getHoaDonCho());
            molGH = (DefaultTableModel) tblGioHang.getModel();
            if (molGH.getRowCount() > 0) {
                molGH.setRowCount(0);
            }
            int indexDongCuoi = tblHoaDonCho.getRowCount() - 1;
            tblHoaDonCho.setRowSelectionInterval(indexDongCuoi, indexDongCuoi);
            detailHD(hd2);
            fillDonHang2();
            txtTienKhachBH2.setText("0");
            tinhThua();
        }
    }//GEN-LAST:event_btnTaoHoaDonChoMouseClicked

    private void tblHoaDonChoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChoMouseClicked
        // TODO add your handling code here:
        indexHoaDonCho = tblHoaDonCho.getSelectedRow();
        String ma = tblHoaDonCho.getValueAt(indexHoaDonCho, 1).toString();
        HoaDon hd = serviceHD.get1HoaDonCho(ma);
        String fileName = "GioHang_" + tblHoaDonCho.getValueAt(indexHoaDonCho, 1) + ".csv";
        //Nhớ đổi đường dẫn thư mục
        loadTableDataFromFile("D:\\PRO1041_DuAn1\\GioHang", fileName);
        detailHD(hd);
        fillDonHang2();
        txtTienKhachBH2.setText("0");
        tinhThua();
    }//GEN-LAST:event_tblHoaDonChoMouseClicked

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        // TODO add your handling code here:     
        int indexXoaGH = tblGioHang.getSelectedRow();
        if (indexXoaGH == -1) {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm để xoá");
            return;
        }
        int checkXoaGH = JOptionPane.showConfirmDialog(this, "Bạn có chắc mắc muốn xoá sản phẩm");
        if (checkXoaGH == JOptionPane.YES_NO_OPTION) {
            String ma = tblGioHang.getValueAt(indexXoaGH, 0).toString();
            int soLuong = Integer.parseInt(tblGioHang.getValueAt(indexXoaGH, 1).toString());
            int indexSanPham = -1;
            for (int i = 0; i < tblChiTietSanPham.getRowCount(); i++) {
                if (tblChiTietSanPham.getValueAt(i, 0).toString().equals(ma)) {
                    indexSanPham = i;
                    break;
                }
            }
            if (indexSanPham != -1) {
                int soLuongHienTai = Integer.parseInt(tblChiTietSanPham.getValueAt(indexSanPham, 1).toString());
                int soLuongMoi = soLuongHienTai + soLuong;
                tblChiTietSanPham.setValueAt(soLuongMoi, indexSanPham, 1);
            }
            molGH.removeRow(indexXoaGH);
            serviceCTSP.capNhatSoLuongThanhToanTru(soLuong, ma);
        } else {
            loadPageCTSP();
            JOptionPane.showMessageDialog(this, "Xoá sản phẩm thất bại");
        }
        fillDonHang2();
        tinhThua();
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void btnQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQRActionPerformed
        // TODO add your handling code here:
        try {
            if (webcam.isOpen()) {
                webcam.close();
            } else {
                pnlWebCam.removeAll();
                pnlWebCam.revalidate();
                pnlWebCam.repaint();
                initWebcam();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnQRActionPerformed

    private void btnThanhToanBH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanBH2ActionPerformed
        if (txtTienThuaBH2.getText().contains("-")) {
            JOptionPane.showMessageDialog(this, "Số tiền được nhập không đủ để thanh toán");
            return;
        }
        HoaDon hd;
        if (validBH()) {
            try {
                hd = this.getFormBH();
                Double tongSau = hd.getTongTien();
                Double tong = fillDonHang();
                Double dua = Double.valueOf(boPhanCach(txtTienKhachBH2.getText()));
                Double tra = Double.valueOf(boPhanCach(txtTienThuaBH2.getText()));
                serviceHD.them(hd);
                serviceHDCT.insert(serviceHDCT.getJoHang2(tblGioHang, hd));
                pdf.genPDF(serviceHDCT.getJoHang(tblGioHang), hd, tong, tongSau, dua, tra);
                fillTableHDC(serviceHD.getHoaDonCho());
                molGH.setRowCount(0);
                fillDonHang2();
                txtMaHDBH2.setText("");
                txtMaKHBH2.setText("");
                txtTienKhachBH2.setText("0");
                txtTienThuaBH2.setText("0");
                txtTenEV.setText("");
                txtMucGiam.setText("");
            } catch (IOException ex) {
                Logger.getLogger(BanHangView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnThanhToanBH2ActionPerformed

    private void txtTienKhachBH2tienKhachNhap(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachBH2tienKhachNhap
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_DELETE))) {
            String txt = txtTienKhachBH2.getText().replaceAll("[^\\d]", "");
            txtTienKhachBH2.setText(txt);
        }
        tinhThua();
    }//GEN-LAST:event_txtTienKhachBH2tienKhachNhap

    private void btnXoaTatCaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTatCaSPActionPerformed
        // TODO add your handling code here:
        int checkXoaGH = JOptionPane.showConfirmDialog(this, "Bạn có chắc mắc muốn xoá tất cả sản phẩm");
        if (checkXoaGH == JOptionPane.YES_NO_OPTION) {
            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                String ma = tblGioHang.getValueAt(i, 0).toString();
                int soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 1).toString());
                int indexSanPham = -1;
                for (int j = 0; j < tblChiTietSanPham.getRowCount(); j++) {
                    if (tblChiTietSanPham.getValueAt(j, 0).toString().equals(ma)) {
                        indexSanPham = j;
                        break;
                    }
                }
                if (indexSanPham != -1) {
                    int soLuongHienTai = Integer.parseInt(tblChiTietSanPham.getValueAt(indexSanPham, 1).toString());
                    int soLuongMoi = soLuongHienTai + soLuong;
                    tblChiTietSanPham.setValueAt(soLuongMoi, indexSanPham, 1);
                    serviceCTSP.capNhatSoLuongThanhToanTru(soLuong, ma);
                }
            }
            molGH.setRowCount(0);
            loadPageCTSP();
        } else {
            loadPageCTSP();
            JOptionPane.showMessageDialog(this, "Xoá sản phẩm thất bại");
        }
        fillDonHang2();
        tinhThua();
    }//GEN-LAST:event_btnXoaTatCaSPActionPerformed

    private void btnTaoHoaDonChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonChoActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnTaoHoaDonChoActionPerformed

    private void btnDau2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDau2ActionPerformed
        // TODO add your handling code here:
        trangCTSP = 1;
        fillTableChiTietSanPham(serviceCTSP.listPageCTSP2(trangCTSP));
        lbSoTrang2.setText(trangCTSP + " of " + soTrangCTSP);
    }//GEN-LAST:event_btnDau2ActionPerformed

    private void btnLui2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLui2ActionPerformed
        // TODO add your handling code here:
        if (trangCTSP > 1) {
            trangCTSP--;
            fillTableChiTietSanPham(serviceCTSP.listPageCTSP2(trangCTSP));
            lbSoTrang2.setText(trangCTSP + " of " + soTrangCTSP);
        }
    }//GEN-LAST:event_btnLui2ActionPerformed

    private void btnTien2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTien2ActionPerformed
        // TODO add your handling code here:
        if (trangCTSP < soTrangCTSP) {
            trangCTSP++;
            fillTableChiTietSanPham(serviceCTSP.listPageCTSP2(trangCTSP));
            lbSoTrang2.setText(trangCTSP + " of " + soTrangCTSP);
        }
    }//GEN-LAST:event_btnTien2ActionPerformed

    private void btnCuoi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuoi2ActionPerformed
        // TODO add your handling code here:
        trangCTSP = soTrangCTSP;
        fillTableChiTietSanPham(serviceCTSP.listPageCTSP2(trangCTSP));
        lbSoTrang2.setText(trangCTSP + " of " + soTrangCTSP);
    }//GEN-LAST:event_btnCuoi2ActionPerformed

    private void btnSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPActionPerformed
        // TODO add your handling code here:
        int indexXoaGH = tblGioHang.getSelectedRow();
        int indexCTSP = tblChiTietSanPham.getSelectedRow();
        if (indexXoaGH == -1) {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm để sửa");
            return;
        }
        int checkXoaGH = JOptionPane.showConfirmDialog(this, "Bạn có chắc mắc muốn sửa sản phẩm");
        if (checkXoaGH == JOptionPane.YES_NO_OPTION) {
            String productID = tblGioHang.getValueAt(indexXoaGH, 0).toString();
            int quantity = Integer.parseInt(tblGioHang.getValueAt(indexXoaGH, 1).toString());
            ChiTietSanPham ctsps = serviceCTSP.getOne(productID);
            int indexSanPham = -1;
            for (int i = 0; i < tblChiTietSanPham.getRowCount(); i++) {
                if (tblChiTietSanPham.getValueAt(i, 0).toString().equals(productID)) {
                    indexSanPham = i;
                    break;
                }
            }
            if (indexSanPham != -1) {
                String input = JOptionPane.showInputDialog(this, "Mời nhập số lượng");
                int soLuongTon = Integer.parseInt(tblChiTietSanPham.getValueAt(indexSanPham, 1).toString());
                int soLuongGioHangHienTai = Integer.parseInt(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 1).toString());
                if (Integer.parseInt(input) > soLuongGioHangHienTai) {
                    System.out.println("Cộng");
                    int soLuongSauKhiSua = soLuongTon - (Integer.parseInt(input) - soLuongGioHangHienTai);
                    if (Integer.parseInt(input) <= soLuongTon) {
                        serviceCTSP.capNhatSoLuongThanhToan(soLuongSauKhiSua, productID);
                        tblChiTietSanPham.setValueAt(soLuongSauKhiSua, indexSanPham, 1);
                        molGH.removeRow(indexXoaGH);
                        fillTableGioHang(tblGioHang, ctsps, Integer.parseInt(input));
                        indexHoaDonCho = tblHoaDonCho.getSelectedRow();
                        String maHD = tblHoaDonCho.getValueAt(indexHoaDonCho, 1).toString();
                        String parentDirectory = "D:\\PRO1041_DuAn1";
                        String newDirectoryName = "GioHang";
                        luuGioHangVaoFile(maHD, parentDirectory, newDirectoryName);
                    } else if (Integer.parseInt(input) <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng sản phẩm phải lớn hơn 0, vui lòng sửa lại");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "Số lượng sản phẩm không đủ, vui lòng sửa lại");
                        return;
                    }

                } else {
                    System.out.println("Trừ");
                    int soLuongSauKhiSua = soLuongTon + (soLuongGioHangHienTai - Integer.parseInt(input));
                    if (Integer.parseInt(input) <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng sản phẩm phải lớn hơn 0, vui lòng sửa lại");
                        return;
                    }
                    serviceCTSP.capNhatSoLuongThanhToan(soLuongSauKhiSua, productID);
                    tblChiTietSanPham.setValueAt(soLuongSauKhiSua, indexSanPham, 1);
                    molGH.removeRow(indexXoaGH);
                    fillTableGioHang(tblGioHang, ctsps, Integer.parseInt(input));
                    indexHoaDonCho = tblHoaDonCho.getSelectedRow();
                    String maHD = tblHoaDonCho.getValueAt(indexHoaDonCho, 1).toString();
                    String parentDirectory = "D:\\PRO1041_DuAn1";
                    String newDirectoryName = "GioHang";
                    luuGioHangVaoFile(maHD, parentDirectory, newDirectoryName);
                }
            }
        } else {
            loadPageCTSP();
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại");
        }
        fillDonHang2();
        tinhThua();
    }//GEN-LAST:event_btnSuaSPActionPerformed

    private void btnHuyDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyDonHangMouseClicked
        // TODO add your handling code here:
        if (indexHoaDonCho == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn cần xóa");
        } else {
            String maHD = tblHoaDonCho.getValueAt(indexHoaDonCho, 1).toString();
            serviceHD.huyHoaDonCho(maHD);
            fillTableHDC(serviceHD.getHoaDonCho());
        }
    }//GEN-LAST:event_btnHuyDonHangMouseClicked

    private void btnHuyDonHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyDonHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyDonHangActionPerformed

    private void cboLocChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboLocChatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLocChatMouseClicked

    private void cboLocChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocChatActionPerformed
        // TODO add your handling code here:

        if (cboLocChat.getSelectedIndex() != -1) {
            if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            }
        }
    }//GEN-LAST:event_cboLocChatActionPerformed

    private void cboLocMauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboLocMauMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLocMauMouseClicked

    private void cboLocMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocMauActionPerformed
        // TODO add your handling code here:
        if (cboLocMau.getSelectedIndex() != -1) {
            if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            }

        }
    }//GEN-LAST:event_cboLocMauActionPerformed

    private void cboLocKichMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboLocKichMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLocKichMouseClicked

    private void cboLocKichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocKichActionPerformed
        // TODO add your handling code here:
        if (cboLocKich.getSelectedIndex() != -1) {
            if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() == -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() == -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() == -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            } else if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
                mtam2();
            }
        }
    }//GEN-LAST:event_cboLocKichActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        //        if (cboLocChat.getSelectedIndex() != -1 && cboLocMau.getSelectedIndex() != -1 && cboLocKich.getSelectedIndex() != -1) {
            //            return;
            //        }
        cboLocChat.setSelectedIndex(-1);
        cboLocMau.setSelectedIndex(-1);
        cboLocKich.setSelectedIndex(-1);
        cboLocChat.setSelectedItem("Tất cả");
        cboLocMau.setSelectedItem("Tất cả");
        cboLocKich.setSelectedItem("Tất cả");
        loadPageCTSP();
    }//GEN-LAST:event_btnResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCuoi2;
    private javax.swing.JButton btnDau2;
    private javax.swing.JButton btnHuyDonHang;
    private javax.swing.JButton btnLui2;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnTaoHoaDonCho;
    private javax.swing.JButton btnThanhToanBH2;
    private javax.swing.JButton btnThemGioHang;
    private javax.swing.JButton btnTien2;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JButton btnXoaTatCaSP;
    private javax.swing.JComboBox<String> cboLocChat;
    private javax.swing.JComboBox<String> cboLocKich;
    private javax.swing.JComboBox<String> cboLocMau;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbSoTrang2;
    private javax.swing.JPanel pnlWebCam;
    private javax.swing.JTable tblChiTietSanPham;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDonCho;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaHDBH2;
    private javax.swing.JTextField txtMaKHBH2;
    private javax.swing.JTextField txtMucGiam;
    private javax.swing.JTextField txtTenEV;
    private javax.swing.JTextField txtTest;
    private javax.swing.JTextField txtTienKhachBH2;
    private javax.swing.JTextField txtTienThuaBH2;
    private javax.swing.JTextField txtTongTienBH2;
    // End of variables declaration//GEN-END:variables

}
