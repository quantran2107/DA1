/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.DoiHang;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import util.DBConnect;

/**
 *
 * @author Admin
 */
public class DoiHangRepository {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    List<DoiHang> listDoiHang = new ArrayList<>();

    public List<DoiHang> getAll() {
        listDoiHang.clear();
        try {
            con = DBConnect.getConnection();
            sql = "SELECT DH.MaDoiHang,DH.NgayDoiTra,DH.TrangThai,\n"
                    + "HD.MaHoaDon,NV.MaNV,NV.HoTen,KH.MaKH,KH.HoTen\n"
                    + "From DoiHang DH join HoaDon HD ON DH.MaHoaDon = HD.MaHoaDon\n"
                    + "join KhachHang KH ON KH.MaKH = HD.MaKH\n"
                    + "join NhanVien NV ON NV.MaNV = DH.MaNV";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(rs.getString(7), rs.getString(8));
                NhanVien nv = new NhanVien(rs.getString(5), rs.getString(6));
                HoaDon hd = new HoaDon(rs.getString(4), kh);
                DoiHang dh = new DoiHang(rs.getString(1), hd, nv,
                        rs.getDate(2), rs.getString(3));
                listDoiHang.add(dh);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listDoiHang;
    }

    public List<DoiHang> getList(String name) {
        List<DoiHang> listDH = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            sql = "SELECT DH.MaDoiHang,DH.NgayDoiTra,DH.TrangThai,\n"
                    + "                             HD.MaHoaDon,NV.MaNV,NV.HoTen,KH.MaKH,KH.HoTen\n"
                    + "                            From DoiHang DH join HoaDon HD ON DH.MaHoaDon = HD.MaHoaDon\n"
                    + "                 join KhachHang KH ON KH.MaKH = HD.MaKH\n"
                    + "    join NhanVien NV ON NV.MaNV = DH.MaNV WHERE KH.HoTen Like ? or NV.HoTen Like ?";
            ps = con.prepareStatement(sql);
            ps.setObject(1, '%' + name + '%');
            ps.setObject(2, '%' + name + '%');
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(rs.getString(7), rs.getString(8));
                NhanVien nv = new NhanVien(rs.getString(5), rs.getString(6));
                HoaDon hd = new HoaDon(rs.getString(4), kh);
                DoiHang dh = new DoiHang(rs.getString(1), hd, nv,
                        rs.getDate(2), rs.getString(3));
                listDH.add(dh);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listDH;
    }

    public int themDonDoiHang(DoiHang dh) {
        try {
            sql = "Insert into DoiHang Values(?,?,?,GETDATE(),N'Đang đổi hàng')";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, dh.getMaDoiHang());
            ps.setObject(2, dh.getNhanVien().getMaNhanVien());
            ps.setObject(3, dh.getHoaDon().getMaHoaDon());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countDoiHang() {
        int tongDoiHang = 0;
        try {
            sql = "Select COUNT(*) From DoiHang";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                tongDoiHang = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return tongDoiHang;
    }

    public List<DoiHang> getAllDangDoiHang() {
        listDoiHang.clear();
        try {
            con = DBConnect.getConnection();
            sql = "SELECT DH.MaDoiHang,DH.NgayDoiTra,DH.TrangThai,\n"
                    + "HD.MaHoaDon,NV.MaNV,NV.HoTen,KH.MaKH,KH.HoTen\n"
                    + "From DoiHang DH join HoaDon HD ON DH.MaHoaDon = HD.MaHoaDon\n"
                    + "join KhachHang KH ON KH.MaKH = HD.MaKH\n"
                    + "join NhanVien NV ON NV.MaNV = DH.MaNV\n"
                    + "Where DH.TrangThai like N'Đang đổi hàng'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(rs.getString(7), rs.getString(8));
                NhanVien nv = new NhanVien(rs.getString(5), rs.getString(6));
                HoaDon hd = new HoaDon(rs.getString(4), kh);
                DoiHang dh = new DoiHang(rs.getString(1), hd, nv,
                        rs.getDate(2), rs.getString(3));
                listDoiHang.add(dh);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listDoiHang;
    }
    
    public int capNhatTrangThai(String maDH){
        try {
            sql = "Update DoiHang set TrangThai = N'Đổi thành công' Where MaDoiHang = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDH);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int huyDonDoiHang(String maDH){
        try {
            sql = "Update DoiHang set TrangThai = N'Hủy' Where MaDoiHang = ?";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maDH);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
