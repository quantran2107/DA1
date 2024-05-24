/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.servicImp;

import java.util.List;
import model.DoiHang;
import repository.DoiHangRepository;
import service.AdamStore;

/**
 *
 * @author Admin
 */
public class DoiHangServiceImp implements AdamStore<DoiHang, String> {

    DoiHangRepository repo = new DoiHangRepository();

    @Override
    public List<DoiHang> getAll() {
        return repo.getAll();
    }

    @Override
    public int them(DoiHang k) {
        return repo.themDonDoiHang(k);
    }

    @Override
    public int sua(DoiHang k, String e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int xoa(String e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DoiHang getOne(String e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DoiHang> getList(String e) {
        return repo.getList(e);
    }
    
    public int countDoiHang() {
        return repo.countDoiHang();
    }
    
    public List<DoiHang> getAllDangDoiHang() {
        return repo.getAllDangDoiHang();
    }
    
    public int capNhatTrangThai(String maDH){
        return repo.capNhatTrangThai(maDH);
    }
    
    public int huyDonDoiHang(String maDH){
        return repo.huyDonDoiHang(maDH);
    }

}
