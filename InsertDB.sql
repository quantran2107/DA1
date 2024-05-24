INSERT INTO TaiKhoan VALUES
('TK01','hoilamgi1','khongnoidau1','Admin',1),
('TK02','hoilamgi2','khongnoidau2','Staff',1),
('TK03','hoilamgi3','khongnoidau3','Staff',1),
('TK04','hoilamgi4','khongnoidau4','Staff',1),
('TK05','hoilamgi5','khongnoidau5','Staff',0)
INSERT INTO NhanVien VALUES
('NV01','TK01',N'Phạm Ngọc Hải',1,N'Ninh Bình','0334294889','037643953721','2023-09-15',1,'anh1.png'),
('NV02','TK02',N'Trần Anh Quân',1,N'Hà Nội','0958655432','037637459321','2023-09-15',1,'anh2.png'),
('NV03','TK03',N'Nguyễn Khánh Li',0,N'Bắc Ninh','0394747324','0378475845','2023-08-13',1,'anh3.png'),
('NV04','TK04',N'Nguyễn Đình Minh Hiếu',1,N'Hà Nội','0334294653','037643953435','2023-07-15',0,'anh4.png'),
('NV05','TK05',N'Mai Thuỳ Linh',0,N'Hà Nội','0334294878','037643953267','2023-07-19',0,'anh5.png')
INSERT INTO KhachHang Values
('KH01',N'Đoàn Xuân Bằng','2004-11-03','0934832745','bangdx@gmail.com',1,N'Nam Định'),
('KH02',N'Vũ Mạnh Trường','2004-07-09','0934832324','truongvmx@gmail.com',1,N'Đồng Nai'),
('KH03',N'Hoàng Minh Tâm','2004-01-15','0934832234','tamhm@gmail.com',0,N'Nghệ An'),
('KH04',N'Nguyễn Tuấn Khanh','2004-08-22','0934832789','khanhnt@gmail.com',1,N'Hà Tĩnh'),
('KH05',N'Lê Tiến Dũng','2004-07-23','0934832987','dunglt@gmail.com',1,N'Ninh Bình'),
('KH06',N'Trần Linh Dương','2004-12-06','0985667832','duongtl@gmail.com',0,N'Hải Phòng'),
('KH07',N'Nguyễn Cao Thắng','2004-11-23','0912356743','thangnc@gmail.com',1,N'Bắc Giang'),
('KH08',N'Nguyễn Phạm Minh Ánh','2004-07-14','0933461234','anhnpm@gmail.com',1,N'Vĩnh Phúc'),
('KH09',N'Phạm Ngọc Ánh','2004-04-18','0933461233','anhpn@gmail.com',1,N'Cao Bằng'),
('KH10',N'Nguyễn Tâm Như','2004-02-14','0933461232','nhunt@gmail.com',1,N'Lạng Sơn'),
('KH11',N'Nguyễn Văn Phong','2004-04-20','0933461238','phongnv@gmail.com',1,N'Cần Thơ'),
('KH12',N'Nguyễn Văn Quang','2004-01-23','0933461237','quangnv@gmail.com',1,N'Bình Dương'),
('KHNE',null,null,null,null,null,null)
INSERT INTO LoaiSanPham VALUES
('LSP01',N'Áo hoodie nam',1,N'Hàng đẹp'),
('LSP02',N'Áo sơ mi nam',1,N'Hàng đẹp'),
('LSP03',N'Áo khoác nam',1,N'Hàng đẹp'),
('LSP04',N'Áo len nam',1,N'Hàng đẹp'),
('LSP05',N'Áo jeans nam',1,N'Hàng đẹp')
INSERT INTO SanPham VALUES
('SP01',N'Nuker',1,'LSP05',N'Việt Nam'),
('SP02',N'GAP',1,'LSP05',N'Hàn Quốc'),
('SP03',N'LEVI',1,'LSP05',N'Thái Lan'),
('SP04',N'Basic Tee',1,'LSP01',N'Hàn Quốc'),
('SP05',N'Graphic Tee',1,'LSP01',N'Việt Nam'),
('SP06',N'Longline Tee',1,'LSP01',N'Campuchia'),
('SP07',N'Flannel Shirt',1,'LSP03',N'Việt Nam'),
('SP08',N'Inen Shirt',1,'LSP03',N'Việt Nam'),
('SP09',N'Western Shirt',1,'LSP03',N'Nhật Bản'),
('SP10',N'Denim Jacket',1,'LSP02',N'Việt Nam'),
('SP11',N'Down Jacket',1,'LSP02',N'Việt Nam'),
('SP12',N'Pea Coat ',1,'LSP02',N'Đài Loan')
INSERT INTO MauSac VALUES
('MS01',N'Đỏ',1),
('MS02',N'Đen',1),
('MS03',N'Trắng',1),
('MS04',N'Xanh',1),
('MS05',N'Vàng',1),
('MS06',N'Xám',1),
('MS07',N'Be',1),
('MS08',N'Hồng',1),
('MS09',N'Bạc',1),
('MS10',N'Tím',1)
INSERT INTO ChatLieu VALUES
('CL01',N'Vải Canvas',1),
('CL02',N'Vải Bò',1),
('CL03',N'Vải Kaki',1),
('CL04',N'Vải Lụa',1),
('CL05',N'Vải Nỉ',1),
('CL06',N'Vải Len',1)

INSERT INTO KichThuoc VALUES
('KT01','S',1),
('KT02','M',1),
('KT03','L',1),
('KT04','XL',1),
('KT05','XXL',1)
INSERT INTO Events VALUES
('EV01',N'Sinh nhật cửa hàng',1,'20000','2023-03-11','2023-03-15',N'Siêu giảm giá',1,1,'2000000'),
('EV02',N'Quốc tế Nam giới',1,'15000','2023-10-19','2023-10-21',N'Siêu giảm giá',1,0,''),
('EV03',N'Giáng sinh',0,'40%','2023-11-03','2023-11-12',N'Siêu giảm giá',1,1,'2500000'),
('EV04',N'Ngày nhà giáo Việt Nam 20/11',0,'40%','2023-11-12','2023-11-13',N'Siêu giảm giá',1,1,'7500000')
delete from HoaDonChiTiet
delete from HoaDon
INSERT INTO ChiTietSanPham(MaSanPham,MaMauSac,MaChatLieu,MaKichThuoc,SoLuong,Gia,TrangThai,qrCode) VALUES
('SP01','MS10','CL06','KT05',215,410000,1,null),
('SP01','MS08','CL05','KT04',126,420000,1,null),
('SP01','MS07','CL04','KT03',99,430000,1,null),
('SP01','MS06','CL03','KT02',250,440000,1,null),
('SP01','MS05','CL02','KT01',210,450000,0,null),
('SP01','MS04','CL01','KT05',150,400000,1,null),
('SP02','MS03','CL06','KT04',110,650000,1,null),
('SP02','MS02','CL05','KT03',120,630000,1,null),
('SP02','MS01','CL04','KT02',123,640000,1,null),
('SP02','MS10','CL03','KT01',80,620000,1,null),
('SP02','MS09','CL02','KT05',78,610000,1,null),
('SP02','MS08','CL01','KT04',98,600000,1,null),
('SP03','MS07','CL06','KT03',67,980000,1,null),
('SP03','MS06','CL05','KT02',127,970000,1,null),
('SP03','MS05','CL04','KT01',140,960000,1,null),
('SP03','MS04','CL03','KT05',230,950000,1,null),
('SP03','MS03','CL02','KT04',170,940000,1,null),
('SP03','MS02','CL01','KT03',200,930000,1,null),
('SP04','MS10','CL06','KT05',215,410000,1,null),
('SP04','MS08','CL05','KT04',126,420000,0,null),
('SP04','MS07','CL04','KT03',99,430000,1,null),
('SP04','MS06','CL03','KT02',250,440000,1,null),
('SP04','MS05','CL02','KT01',210,450000,1,null),
('SP04','MS04','CL01','KT05',150,400000,1,null),
('SP05','MS03','CL06','KT04',110,650000,1,null),
('SP05','MS02','CL05','KT03',120,630000,1,null),
('SP05','MS01','CL04','KT02',123,640000,1,null),
('SP05','MS10','CL03','KT01',80,620000,1,null),
('SP05','MS09','CL02','KT05',78,610000,1,null),
('SP05','MS08','CL01','KT04',98,600000,1,null),
('SP06','MS07','CL06','KT03',67,980000,1,null),
('SP06','MS06','CL05','KT02',127,970000,1,null),
('SP06','MS05','CL04','KT01',140,960000,1,null),
('SP06','MS04','CL03','KT05',230,950000,1,null),
('SP06','MS03','CL02','KT04',170,940000,1,null),
('SP06','MS02','CL01','KT03',200,930000,1,null),
('SP07','MS03','CL06','KT04',110,650000,1,null),
('SP07','MS02','CL05','KT03',120,630000,1,null),
('SP07','MS01','CL04','KT02',123,640000,1,null),
('SP07','MS10','CL03','KT01',80,620000,1,null),
('SP07','MS09','CL02','KT05',78,610000,1,null),
('SP07','MS08','CL01','KT04',98,600000,1,null),
('SP08','MS10','CL06','KT05',215,410000,1,null),
('SP08','MS08','CL05','KT04',126,420000,1,null),
('SP08','MS07','CL04','KT03',99,430000,1,null),
('SP08','MS06','CL03','KT02',250,440000,1,null),
('SP08','MS05','CL02','KT01',210,450000,1,null),
('SP08','MS04','CL01','KT05',150,400000,1,null),
('SP09','MS07','CL06','KT03',67,980000,1,null),
('SP09','MS06','CL05','KT02',127,970000,1,null),
('SP09','MS05','CL04','KT01',140,960000,1,null),
('SP09','MS04','CL03','KT05',230,950000,1,null),
('SP09','MS03','CL02','KT04',170,940000,1,null),
('SP09','MS02','CL01','KT03',200,930000,1,null),
('SP10','MS07','CL06','KT03',67,980000,1,null),
('SP10','MS06','CL05','KT02',127,970000,1,null),
('SP10','MS05','CL04','KT01',140,960000,1,null),
('SP10','MS04','CL03','KT05',230,950000,1,null),
('SP10','MS03','CL02','KT04',170,940000,1,null),
('SP10','MS02','CL01','KT03',200,930000,1,null),
('SP11','MS10','CL06','KT05',215,410000,1,null),
('SP11','MS08','CL05','KT04',126,420000,1,null),
('SP11','MS07','CL04','KT03',99,430000,1,null),
('SP11','MS06','CL03','KT02',250,440000,1,null),
('SP11','MS05','CL02','KT01',210,450000,1,null),
('SP11','MS04','CL01','KT05',150,400000,1,null),
('SP12','MS10','CL06','KT05',215,410000,1,null),
('SP12','MS08','CL05','KT04',126,420000,1,null),
('SP12','MS07','CL04','KT03',99,430000,1,null),
('SP12','MS06','CL03','KT02',250,440000,1,null),
('SP12','MS05','CL02','KT01',210,450000,1,null),
('SP12','MS04','CL01','KT05',150,400000,1,null)

INSERT INTO HoaDon VALUES
('HD01','NV05','KH03',getDate(),450000,50000,400000,N'Đã thanh toán',N'Thành công','EV03'),
('HD02','NV03','KH02',getDate(),130000,50000,90000,N'Đã thanh toán',N'Thành công','EV02'),
('HD03','NV02','KH01',getDate(),750000,50000,700000,N'Đã đổi hàng',N'Thành công','EV01'),
('HD04','NV03','KH02',getDate(),130000,50000,90000,N'Đã thanh toán',N'Thành công','EV02'),
('HD05','NV02','KH01',getDate(),750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD06','NV03','KH02','2023-11-20',130000,50000,90000,N'Đã đổi hàng',N'Thành công','EV02'),
('HD07','NV01','KH01','2023-11-1',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD08','NV02','KH01','2023-11-25',750000,50000,700000,N'Đã đổi hàng',N'Thành công','EV01'),
('HD09','NV01','KH01','2023-11-25',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD10','NV05','KH03','2023-11-1',450000,50000,400000,N'Đã đổi hàng',N'Thành công','EV03'),
('HD12','NV03','KH02','2023-11-1',130000,50000,90000,N'Đã thanh toán',N'Thành công','EV02'),
('HD11','NV05','KH01','2023-11-1',450000,50000,400000,N'Đã thanh toán',N'Thành công','EV03'),
('HD13','NV02','KH01','2023-11-3',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD14','NV03','KH02','2023-11-3',130000,50000,90000,N'Đã thanh toán',N'Thành công','EV02'),
('HD15','NV02','KH01','2023-11-3',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD16','NV03','KH02','2023-11-5',130000,50000,90000,N'Đã thanh toán',N'Thành công','EV02'),
('HD17','NV02','KH01','2023-11-5',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD18','NV01','KH01','2023-11-5',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD19','NV02','KH01','2023-11-15',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD20','NV03','KH01','2023-11-23',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01'),
('HD21','NV05','KH01','2023-11-23',750000,50000,700000,N'Đã thanh toán',N'Thành công','EV01')



 select * from ChiTietSanPham
INSERT INTO HoaDonChiTiet VALUES
('HDCT01','CTSP03C685','HD03',3,170000,N'Ok',1),
('HDCT02','CTSP046121','HD02',4,170000,N'Ok',1),
('HDCT03','CTSP0632F5','HD01',3,210000,N'Ok',1),
('HDCT04','CTSP07FC0F','HD02',4,170000,N'Ok',1),
('HDCT06','CTSP0A0B40','HD04',3,210000,N'Ok',1),
('HDCT07','CTSP0CD10E','HD05',4,170000,N'Ok',1),
('HDCT08','CTSP107A29','HD06',3,210000,N'Ok',1),
('HDCT09','CTSP11554D','HD08',3,210000,N'Ok',1),
('HDCT10','CTSP169E2D','HD09',3,210000,N'Ok',1),
('HDCT11','CTSP19B02E','HD09',3,210000,N'Ok',1),
('HDCT12','CTSP1A41AF','HD06',3,210000,N'Ok',1),
('HDCT13','CTSP1AEC52','HD08',3,210000,N'Ok',1),
('HDCT14','CTSP257369','HD07',4,170000,N'Ok',1),
('HDCT15','CTSP27CC07','HD10',1,400000,N'Ok',1),
('HDCT16','CTSP32AD09','HD10',2,356000,N'Ok',1),
('HDCT17','CTSP33707E','HD10',3,200000,N'Ok',1),
('HDCT18','CTSP34236F','HD11',4,200000,N'Ok',1),
('HDCT19','CTSP35A981','HD11',5,200000,N'Ok',1),
('HDCT20','CTSP36A266','HD11',5,200000,N'Ok',1),
('HDCT21','CTSP3938DD','HD12',4,400000,N'Ok',1),
('HDCT22','CTSP3967F8','HD12',3,356000,N'Ok',1),
('HDCT23','CTSP3CF530','HD12',2,200000,N'Ok',1),
('HDCT24','CTSP3D4597','HD13',1,200000,N'Ok',1),
('HDCT25','CTSP3DFC2C','HD13',1,200000,N'Ok',1),
('HDCT26','CTSP43E851','HD13',2,200000,N'Ok',1),
('HDCT27','CTSP46024B','HD14',4,200000,N'Ok',1),
('HDCT28','CTSP398F02','HD14',3,320000,N'Ok',1),
('HDCT29','CTSP4000A5','HD14',2,650000,N'Ok',1),
('HDCT30','CTSP425BA7','HD15',1,780000,N'Ok',1),
('HDCT31','CTSP430177','HD15',1,210000,N'Ok',1),
('HDCT32','CTSP49B88B','HD15',2,200000,N'Ok',1),
('HDCT33','CTSP54DD60','HD16',1,400000,N'Ok',1),
('HDCT34','CTSP56E17E','HD16',2,356000,N'Ok',1),
('HDCT35','CTSP589EE9','HD16',3,200000,N'Ok',1),
('HDCT36','CTSP60FCEA','HD17',4,200000,N'Ok',1),
('HDCT37','CTSP624C9D','HD17',5,200000,N'Ok',1),
('HDCT38','CTSP6987E4','HD17',5,200000,N'Ok',1),
('HDCT39','CTSP7273D6','HD18',4,400000,N'Ok',1),
('HDCT40','CTSP74E562','HD18',3,356000,N'Ok',1),
('HDCT41','CTSP761FFD','HD18',2,200000,N'Ok',1),
('HDCT42','CTSP785D35','HD19',1,200000,N'Ok',1),
('HDCT43','CTSP79688E','HD19',1,200000,N'Ok',1),
('HDCT44','CTSP7A2E28','HD19',2,200000,N'Ok',1),
('HDCT45','CTSP84CB93','HD20',4,200000,N'Ok',1),
('HDCT46','CTSP8A36D8','HD20',3,320000,N'Ok',1),
('HDCT47','CTSP8CE662','HD20',2,650000,N'Ok',1),
('HDCT48','CTSP910845','HD21',1,780000,N'Ok',1),
('HDCT49','CTSP93455D','HD21',1,210000,N'Ok',1),
('HDCT50','CTSP95E4F0','HD21',2,200000,N'Ok',1),

('HDCT51','CTSP960EFF','HD18',2,200000,N'Ok',1),
('HDCT52','CTSPA36445','HD19',1,200000,N'Ok',1),
('HDCT53','CTSPA42DB4','HD19',1,200000,N'Ok',1),
('HDCT54','CTSPB242A6','HD19',2,200000,N'Ok',1),
('HDCT55','CTSPB97169','HD20',4,200000,N'Ok',1),
('HDCT56','CTSPB9A3AF','HD20',3,320000,N'Ok',1),
('HDCT57','CTSPBA0386','HD20',2,650000,N'Ok',1),
('HDCT58','CTSPCC7B32','HD21',1,780000,N'Ok',1),
('HDCT59','CTSPD42E5B','HD21',1,210000,N'Ok',1),
('HDCT60','CTSPD60FE1','HD21',2,200000,N'Ok',1),

('HDCT61','CTSPD63D12','HD11',2,200000,N'Ok',1),
('HDCT62','CTSPD8A86C','HD12',1,200000,N'Ok',1),
('HDCT63','CTSPD936CD','HD13',1,200000,N'Ok',1),
('HDCT64','CTSPDC6392','HD14',2,200000,N'Ok',1),
('HDCT65','CTSPDD5FB8','HD20',4,200000,N'Ok',1),
('HDCT66','CTSPE19A09','HD20',3,320000,N'Ok',1),
('HDCT67','CTSPE3AE12','HD20',2,650000,N'Ok',1),
('HDCT68','CTSPE660D2','HD21',1,780000,N'Ok',1),
('HDCT69','CTSPE7166F','HD21',1,210000,N'Ok',1),
('HDCT70','CTSPEAB6CA','HD21',2,200000,N'Ok',1),

('HDCT71','CTSPF50480','HD11',5,200000,N'Ok',1),
('HDCT72','CTSPF58433','HD12',4,400000,N'Ok',1),
('HDCT73','CTSPFC531B','HD12',3,356000,N'Ok',1),
('HDCT74','CTSPFF48FC','HD12',2,200000,N'Ok',1),
('HDCT75','CTSP19B02E','HD13',1,200000,N'Ok',1),
('HDCT76','CTSPD936CD','HD13',1,200000,N'Ok',1),
('HDCT77','CTSPD8A86C','HD13',2,200000,N'Ok',1),
('HDCT78','CTSPF50480','HD14',4,200000,N'Ok',1),
('HDCT79','CTSPE660D2','HD14',3,320000,N'Ok',1),
('HDCT80','CTSP19B02E','HD14',2,650000,N'Ok',1)
--đoạn code dưới để check HDCT nhé nào cảm thấy thiếu thì check có thể not in or in

--delete from HoaDonChiTiet

DECLARE @Counter INT = 1;

WHILE @Counter <= 21
BEGIN
    DECLARE @MaHoaDon NVARCHAR(10) = 'HD' + RIGHT('00' + CAST(@Counter AS VARCHAR(2)), 2);

    INSERT INTO HoaDonChiTiet (MaHoaDonChiTiet, MaCTSP, MaHoaDon, SoLuong, DonGia, GhiChu, TrangThai)
    SELECT @MaHoaDon + '_' + CAST((ROW_NUMBER() OVER (ORDER BY NEWID()) - 1) * 3 + 1 AS VARCHAR(10)),
           MaCTSP,
           @MaHoaDon,
           ROUND(RAND() * (10 - 1) + 1, 0), -- Số lượng ngẫu nhiên từ 1 đến 10 cho từng hàng
           Gia,
           N'OK',
           1
    FROM ChiTietSanPham ctsp
    ORDER BY NEWID()
    OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY; -- Chỉ chọn 1 hàng mỗi lần

    SET @Counter = @Counter + 1;
END

UPDATE HoaDon
SET TongTien = (
    SELECT SUM(HDCT.SoLuong * HDCT.DonGia) AS TongTien
    FROM HoaDonChiTiet HDCT
    WHERE HoaDon.MaHoaDon = HDCT.MaHoaDon
    GROUP BY HDCT.MaHoaDon
)
UPDATE HoaDon SET TongTienSauKM = TongTien - TongTienKM

--delete from HoaDonChiTiet
select * from ChiTietSanPham where MaCTSP = 'CTSP9F828D'
SELECT*FROM HoaDon
select * from HoaDonChiTiet
where MaCTSP = 'CTSP9F828D'


SELECT * FROM ChiTietSanPham where MaCTSP  not in(SELECT MaCTSP FROM HoaDonChiTiet)
SELECT MaHoaDon, COUNT(MaCTSP) FROM HoaDonChiTiet
GROUP BY MaHoaDon
HAVING COUNT(MaCTSP)=0
INSERT INTO DoiHang VALUES
('DH01','NV02','HD03','2023-11-07',N'Đổi thành công'),
('DH02','NV04','HD06','2023-11-14',N'Đổi thành công'),
('DH03','NV05','HD08','2023-11-19',N'Đổi thành công'),
('DH04','NV03','HD10','2023-11-29',N'Đổi thành công')

INSERT INTO DoiHangChiTiet VALUES
('DHCT01','CTSP03C685','DH03','HDCT09',2,N'Áo bị chật',1),
('DHCT02','CTSP0A0B40','DH01','HDCT01',1,N'Áo bị rộng',1),
('DHCT03','CTSP0CD10E','DH02','HDCT08',1,N'Áo bị chật',1),
('DHCT04','CTSP1A41AF','DH03','HDCT13',1,N'Áo bị chật',1),
('DHCT05','CTSP27CC07','DH04','HDCT16',1,N'Áo bị rộng',1)
--Update hai 23g03p 10/12/2023
