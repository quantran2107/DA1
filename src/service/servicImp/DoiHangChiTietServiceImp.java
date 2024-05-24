/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.servicImp;

import java.util.List;
import javax.swing.JTable;
import model.DoiHangChiTiet;
import model.HoaDonChiTiet;
import repository.DoiHangChiTietRepository;
import service.AdamStore;

/**
 *
 * @author Admin
 */
public class DoiHangChiTietServiceImp implements AdamStore<DoiHangChiTiet, String>{
    DoiHangChiTietRepository repo = new DoiHangChiTietRepository();

    @Override
    public List<DoiHangChiTiet> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int them(DoiHangChiTiet k) {
        return repo.them(k);
    }

    @Override
    public int sua(DoiHangChiTiet k, String e) {
        return repo.sua(k, e);
    }

    @Override
    public int xoa(String e) {
        return repo.xoa(e);
    }

    @Override
    public DoiHangChiTiet getOne(String e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DoiHangChiTiet> getList(String e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<DoiHangChiTiet> getDHCT(String maDoiHang) {
        return repo.getDHCT(maDoiHang);
    }
    
    public List<DoiHangChiTiet> getDHCTDangDoi(String maDoiHang) {
        return repo.getDHCTDangDoi(maDoiHang);
    }
    
    public int countDoiHangChiTiet() {
        return repo.countDoiHangChiTiet();
    }
    
    public String getMaDHCT(String maHDCT){
        return repo.getMaDHCT(maHDCT);
    }
    
    public int capNhatTrangThai(String maDH){
        return repo.capNhatTrangThai(maDH);
    }
    
    public List<DoiHangChiTiet> getDHCTFromDH(String maDH) {
        return repo.getDHCTFromDH(maDH);
    }
    public List<HoaDonChiTiet> getJoHang(JTable table) {
        return repo.getJoHang(table);
    }
    
    public int congSoLuong(String maHDCT, int soLuong){
        return repo.congSoLuong(maHDCT, soLuong);
    }
    
    public DoiHangChiTiet checkTrung(String maDH, String maCTSPCu, String maCTSPMoi) {
        return repo.checkTrung(maDH, maCTSPCu, maCTSPMoi);
    }
    
    public int xoaDHCT(String maDoiHang){
        return repo.xoaDHCT(maDoiHang);
    }
}
