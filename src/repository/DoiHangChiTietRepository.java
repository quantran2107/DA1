/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import model.ChatLieu;
import model.HoaDonChiTiet;
import model.ChiTietSanPham;
import model.DoiHang;
import model.DoiHangChiTiet;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.KichThuoc;
import model.MauSac;
import model.SanPham;
import util.DBConnect;

/**
 *
 * @author Admin
 */
public class DoiHangChiTietRepository {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    List<DoiHangChiTiet> listDHCT = new ArrayList<>();

    public List<DoiHangChiTiet> getDHCT(String maDoiHang) {
        List<DoiHangChiTiet> listDHCT = new ArrayList<>();
        listDHCT.clear();
        try {
            sql = "SELECT DH.MaDoiHang, SP.MaSanPham, SP.TenSanPham, CTSPCu.MaCTSP, CTSPMoi.MaCTSP, DHCT.SoLuong,\n"
                    + "DHCT.TrangThai, DHCT.MoTa, HD.MaHoaDon, DHCT.MaDHCT, HDCT.MaHoaDonChiTiet FROM DoiHangChiTiet DHCT\n"
                    + "JOIN DoiHang DH ON DH.MaDoiHang = DHCT.MaDoiHang  JOIN HoaDon HD ON DH.MaHoaDon = HD.MaHoaDon \n"
                    + "JOIN HoaDonChiTiet HDCT ON HDCT.MaHoaDonChiTiet = DHCT.MaHoaDonChiTiet JOIN ChiTietSanPham CTSPCu\n"
                    + "ON HDCT.MaCTSP = CTSPCu.MaCTSP JOIN ChiTietSanPham CTSPMoi ON DHCT.MaCTSP = CTSPMoi.MaCTSP JOIN SanPham SP ON SP.MaSanPham = CTSPCu.MaSanPham"
                    + "WHERE DH.MaDoiHang = ? and DHCT.TrangThai = 1";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDoiHang);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(rs.getString(9));
                DoiHang dh = new DoiHang(rs.getString(1), hd);
                SanPham sp = new SanPham(rs.getString(2), rs.getString(3));
                ChiTietSanPham ctspCu = new ChiTietSanPham(rs.getString(4), sp);
                ChiTietSanPham ctspMoi = new ChiTietSanPham(rs.getString(5));
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getString(11), ctspCu);
                DoiHangChiTiet dhct = new DoiHangChiTiet(rs.getString(10), dh, hdct,
                        ctspMoi, rs.getInt(6), rs.getBoolean(7), rs.getString(8));
                listDHCT.add(dhct);
            }
            return listDHCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int them(DoiHangChiTiet dhct) {
        try {
            sql = "Insert into DoiHangChiTiet(MaDHCT,MaCTSP,MaDoiHang,MaHoaDonChiTiet,SoLuong,MoTa,TrangThai) Values(?,?,?,?,?,?,0)";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, dhct.getMaDHCT());
            ps.setObject(2, dhct.getChiTietSanPham().getMaChiTietSanPham());
            ps.setObject(3, dhct.getDoiHang().getMaDoiHang());
            ps.setObject(4, dhct.getHoaDonChiTiet().getMaHDCT());
            ps.setObject(5, dhct.getSoLuong());
            ps.setObject(6, dhct.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<DoiHangChiTiet> getDHCTDangDoi(String maDoiHang) {
        List<DoiHangChiTiet> listDHCT = new ArrayList<>();
        listDHCT.clear();
        try {
            sql = "SELECT DH.MaDoiHang, SP.MaSanPham, SP.TenSanPham, CTSPCu.MaCTSP, CTSPMoi.MaCTSP, DHCT.SoLuong,\n"
                    + "                    DHCT.TrangThai, DHCT.MoTa, HD.MaHoaDon, DHCT.MaDHCT, HDCT.MaHoaDonChiTiet, CTSPMoi.Gia FROM DoiHangChiTiet DHCT\n"
                    + "                   full JOIN DoiHang DH ON DH.MaDoiHang = DHCT.MaDoiHang full JOIN HoaDon HD ON DH.MaHoaDon = HD.MaHoaDon\n"
                    + "                   full JOIN HoaDonChiTiet HDCT ON HDCT.MaHoaDonChiTiet = DHCT.MaHoaDonChiTiet full JOIN ChiTietSanPham CTSPCu\n"
                    + "                    ON HDCT.MaCTSP = CTSPCu.MaCTSP full JOIN ChiTietSanPham CTSPMoi ON DHCT.MaCTSP = CTSPMoi.MaCTSP JOIN SanPham SP ON SP.MaSanPham = CTSPCu.MaSanPham\n"
                    + "                    					WHERE DHCT.MaDoiHang = ? and DHCT.TrangThai = 0";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDoiHang);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(rs.getString(9));
                DoiHang dh = new DoiHang(rs.getString(1), hd);
                SanPham sp = new SanPham(rs.getString(2), rs.getString(3));
                ChiTietSanPham ctspCu = new ChiTietSanPham(rs.getString(4), sp);
                ChiTietSanPham ctspMoi = new ChiTietSanPham(rs.getString(5), rs.getDouble(12));
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getString(11), ctspCu);
                DoiHangChiTiet dhct = new DoiHangChiTiet(rs.getString(10), dh, hdct,
                        ctspMoi, rs.getInt(6), rs.getBoolean(7), rs.getString(8));
                listDHCT.add(dhct);
            }
            return listDHCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countDoiHangChiTiet() {
        int tongDHCT = 0;
        try {
            sql = "Select COUNT(*) From DoiHang";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                tongDHCT = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return tongDHCT;
    }

    public int xoa(String maDH) {
        try {
            sql = "Delete From DoiHangChiTiet where MaDoiHang = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDH);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int sua(DoiHangChiTiet dhct, String maDHCT) {
        try {
            sql = "Update DoiHangChiTiet set MaCTSP = ?, SoLuong = ?, MoTa = ? Where MaDHCT = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, dhct.getChiTietSanPham().getMaChiTietSanPham());
            ps.setObject(2, dhct.getSoLuong());
            ps.setObject(3, dhct.getMoTa());
            ps.setObject(4, maDHCT);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getMaDHCT(String maHDCT) {
        String maDHCT = null;
        try {
            sql = "Select MaDHCT From DoiHangChiTiet where MaHoaDonChiTiet = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maHDCT);
            rs = ps.executeQuery();
            while (rs.next()) {
                maDHCT = rs.getString(1);
            }
            return maDHCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int capNhatTrangThai(String maDH) {
        try {
            sql = "Update DoiHangChiTiet set TrangThai = 1 Where MaDoiHang = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDH);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<DoiHangChiTiet> getDHCTFromDH(String maDH) {
        List<DoiHangChiTiet> listDHCT2 = new ArrayList<>();
        try {
            sql = "Select DHCT.MaDHCT,DHCT.SoLuong,SPCu.TenSanPham,SPCu.MaSanPham,CLCu.TenChatLieu,\n"
                    + "CLCu.MaChatLieu,KTCu.TenKichThuoc,KTCu.MaKichThuoc, MSCu.TenMauSac,MSCu.MaMauSac,\n"
                    + "CTSPCu.Gia, SPMoi.TenSanPham, SPMoi.MaSanPham, CLMoi.TenChatLieu,CLMoi.MaChatLieu,\n"
                    + "KTMoi.TenKichThuoc,KTMoi.MaKichThuoc, MSMoi.TenMauSac,MSMoi.MaMauSac,\n"
                    + "CTSPMoi.Gia, DH.MaDoiHang,CTSPCu.MaCTSP, CTSPMoi.MaCTSP, \n"
                    + "HDCT.MaHoaDonChiTiet, DHCT.MoTa From DoiHangChiTiet DHCT\n"
                    + "Join ChiTietSanPham CTSPMoi ON DHCT.MaCTSP = CTSPMoi.MaCTSP\n"
                    + "Join SanPham SPMoi ON SPMoi.MaSanPham = CTSPMoi.MaSanPham\n"
                    + "Join ChatLieu CLMoi ON CLMoi.MaChatLieu = CTSPMoi.MaChatLieu\n"
                    + "Join MauSac MSMoi ON MSMoi.MaMauSac = CTSPMoi.MaMauSac\n"
                    + "Join KichThuoc KTMoi ON KTMoi.MaKichThuoc = CTSPMoi.MaKichThuoc\n"
                    + "Join HoaDonChiTiet HDCT ON HDCT.MaHoaDonChiTiet = DHCT.MaHoaDonChiTiet\n"
                    + "Join ChiTietSanPham CTSPCu ON HDCT.MaCTSP = CTSPCu.MaCTSP\n"
                    + "Join SanPham SPCu ON SPCu.MaSanPham = CTSPCu.MaSanPham\n"
                    + "Join ChatLieu CLCu ON CLCu.MaChatLieu = CTSPCu.MaChatLieu\n"
                    + "Join MauSac MSCu ON MSCu.MaMauSac = CTSPCu.MaMauSac\n"
                    + "Join KichThuoc KTCu ON KTCu.MaKichThuoc = CTSPCu.MaKichThuoc\n"
                    + "Join DoiHang DH ON DH.MaDoiHang = DHCT.MaDoiHang\n"
                    + "where DH.MaDoiHang = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDH);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPham spCu = new SanPham(rs.getString(4), rs.getString(3));
                ChatLieu clCu = new ChatLieu(rs.getString(6), rs.getString(5));
                KichThuoc ktCu = new KichThuoc(rs.getString(8), rs.getString(7));
                MauSac msCu = new MauSac(rs.getString(10), rs.getString(9));
                ChiTietSanPham ctspCu = new ChiTietSanPham(rs.getString(22), spCu, msCu, clCu, ktCu, rs.getDouble(11));
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getString(24), ctspCu);
                SanPham spMoi = new SanPham(rs.getString(13), rs.getString(12));
                ChatLieu clMoi = new ChatLieu(rs.getString(15), rs.getString(14));
                KichThuoc ktMoi = new KichThuoc(rs.getString(17), rs.getString(16));
                MauSac msMoi = new MauSac(rs.getString(19), rs.getString(18));
                ChiTietSanPham ctspMoi = new ChiTietSanPham(rs.getString(23), spMoi, msMoi, clMoi, ktMoi, rs.getDouble(20));
                DoiHang dh = new DoiHang(rs.getString(21));
                DoiHangChiTiet dhct = new DoiHangChiTiet(rs.getString(1), dh, hdct, ctspMoi, rs.getInt(2),true,rs.getString(25));
                listDHCT2.add(dhct);
            }
            return listDHCT2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<HoaDonChiTiet> getJoHang(JTable table) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        int soCot = table.getRowCount();
        if (soCot == 0) {
            return null;
        }
        try {
            con = DBConnect.getConnection();
            sql = "SELECT CTSP.MaCTSP,CTSP.MaSanPham,SP.TenSanPham,MS.MaMauSac,MS.TenMauSac,CL.MaChatLieu,CL.TenChatLieu,\n"
                    + " KT.MaKichThuoc,KT.TenKichThuoc,CTSP.SoLuong,CTSP.Gia,CTSP.TrangThai\n"
                    + " FROM ChiTietSanPham CTSP INNER JOIN ChatLieu CL On CL.MaChatLieu=CTSP.MaChatLieu\n"
                    + " INNER JOIn MauSac MS ON MS.MaMauSac=CTSP.MaMauSac\n"
                    + " INNER JOIN KichThuoc KT ON KT.MaKichThuoc=CTSP.MaKichThuoc \n"
                    + " INNER JOIN SanPham SP ON CTSP.MaSanPham=SP.MaSanPham"
                    + " where maCTSP = ?";
            ps = con.prepareStatement(sql);
            for (int i = 0; i < soCot; i++) {
                String maCTSP = table.getValueAt(i, 3).toString();
                int soLuong = Integer.parseInt(table.getValueAt(i, 4).toString());
                Double gia = Double.parseDouble(table.getValueAt(i, 5).toString());
                ps.setObject(1, maCTSP);
                rs = ps.executeQuery();
                while (rs.next()) {
                    SanPham sp = new SanPham(rs.getString(2), rs.getString(3));
                    MauSac ms = new MauSac(rs.getString(4), rs.getString(5));
                    ChatLieu cl = new ChatLieu(rs.getString(6), rs.getString(7));
                    KichThuoc kt = new KichThuoc(rs.getString(8), rs.getString(9));
                    ChiTietSanPham ctsp = new ChiTietSanPham(rs.getString(1), sp,
                            ms, cl, kt, rs.getInt(10), rs.getDouble(11), rs.getBoolean(12));
                    HoaDonChiTiet hdct = new HoaDonChiTiet(ctsp, soLuong, gia);
                    list.add(hdct);
                }
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public int congSoLuong(String maHDCT, int soLuong){
        try {
            sql = "UPDATE DoiHangChiTiet set SoLuong = SoLuong + ? where MaHoaDonChiTiet = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, soLuong);
            ps.setObject(2, maHDCT);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public DoiHangChiTiet checkTrung(String maDH, String maCTSPCu, String maCTSPMoi) {
        DoiHangChiTiet dhct = null;
        try {
            sql = "Select DHCT.MaDHCT,DHCT.SoLuong,SPCu.TenSanPham,SPCu.MaSanPham,CLCu.TenChatLieu,\n"
                    + "CLCu.MaChatLieu,KTCu.TenKichThuoc,KTCu.MaKichThuoc, MSCu.TenMauSac,MSCu.MaMauSac,\n"
                    + "CTSPCu.Gia, SPMoi.TenSanPham, SPMoi.MaSanPham, CLMoi.TenChatLieu,CLMoi.MaChatLieu,\n"
                    + "KTMoi.TenKichThuoc,KTMoi.MaKichThuoc, MSMoi.TenMauSac,MSMoi.MaMauSac,\n"
                    + "CTSPMoi.Gia, DH.MaDoiHang,CTSPCu.MaCTSP, CTSPMoi.MaCTSP, \n"
                    + "HDCT.MaHoaDonChiTiet, DHCT.MoTa From DoiHangChiTiet DHCT\n"
                    + "Join ChiTietSanPham CTSPMoi ON DHCT.MaCTSP = CTSPMoi.MaCTSP\n"
                    + "Join SanPham SPMoi ON SPMoi.MaSanPham = CTSPMoi.MaSanPham\n"
                    + "Join ChatLieu CLMoi ON CLMoi.MaChatLieu = CTSPMoi.MaChatLieu\n"
                    + "Join MauSac MSMoi ON MSMoi.MaMauSac = CTSPMoi.MaMauSac\n"
                    + "Join KichThuoc KTMoi ON KTMoi.MaKichThuoc = CTSPMoi.MaKichThuoc\n"
                    + "Join HoaDonChiTiet HDCT ON HDCT.MaHoaDonChiTiet = DHCT.MaHoaDonChiTiet\n"
                    + "Join ChiTietSanPham CTSPCu ON HDCT.MaCTSP = CTSPCu.MaCTSP\n"
                    + "Join SanPham SPCu ON SPCu.MaSanPham = CTSPCu.MaSanPham\n"
                    + "Join ChatLieu CLCu ON CLCu.MaChatLieu = CTSPCu.MaChatLieu\n"
                    + "Join MauSac MSCu ON MSCu.MaMauSac = CTSPCu.MaMauSac\n"
                    + "Join KichThuoc KTCu ON KTCu.MaKichThuoc = CTSPCu.MaKichThuoc\n"
                    + "Join DoiHang DH ON DH.MaDoiHang = DHCT.MaDoiHang\n"
                    + "where DH.MaDoiHang = ? and CTSPCu.MaCTSP = ? and CTSPMoi.MaCTSP = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDH);
            ps.setObject(2, maCTSPCu);
            ps.setObject(3, maCTSPMoi);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPham spCu = new SanPham(rs.getString(4), rs.getString(3));
                ChatLieu clCu = new ChatLieu(rs.getString(6), rs.getString(5));
                KichThuoc ktCu = new KichThuoc(rs.getString(8), rs.getString(7));
                MauSac msCu = new MauSac(rs.getString(10), rs.getString(9));
                ChiTietSanPham ctspCu = new ChiTietSanPham(rs.getString(22), spCu, msCu, clCu, ktCu, rs.getDouble(11));
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getString(24), ctspCu);
                SanPham spMoi = new SanPham(rs.getString(13), rs.getString(12));
                ChatLieu clMoi = new ChatLieu(rs.getString(15), rs.getString(14));
                KichThuoc ktMoi = new KichThuoc(rs.getString(17), rs.getString(16));
                MauSac msMoi = new MauSac(rs.getString(19), rs.getString(18));
                ChiTietSanPham ctspMoi = new ChiTietSanPham(rs.getString(23), spMoi, msMoi, clMoi, ktMoi, rs.getDouble(20));
                DoiHang dh = new DoiHang(rs.getString(21));
                dhct = new DoiHangChiTiet(rs.getString(1), dh, hdct, ctspMoi, rs.getInt(2),true,rs.getString(25));
            }
            return dhct;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int xoaDHCT(String maDoiHang){
        try {
            sql = "Delete DoiHangChiTiet where MaDoiHang = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDoiHang);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
