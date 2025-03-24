package org.example.thim4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "giao_dich")
public class GiaoDich {
    @Id
    @NotBlank(message = "Mã giao dịch không được để trống")
    @Pattern(regexp = "MGD-\\d{4}", message = "Mã giao dịch phải có định dạng MGD-XXXX (XXXX là số)")
    private String maGiaoDich;

    @ManyToOne
    @JoinColumn(name = "ma_khach_hang", nullable = false) // Đảm bảo tên cột khớp với cơ sở dữ liệu
    @NotNull(message = "Vui lòng chọn khách hàng")
    private KhachHang khachHang;

    @NotNull(message = "Ngày giao dịch không được để trống")
    @PastOrPresent(message = "Ngày giao dịch phải nhỏ hơn hoặc bằng ngày hiện tại") // Sửa từ @Future thành @PastOrPresent
    private LocalDate ngayGiaoDich;

    @NotBlank(message = "Loại dịch vụ không được để trống")
    private String loaiDichVu;

    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "500000.00", message = "Đơn giá phải lớn hơn 500,000 VND")
    private BigDecimal donGia;

    @NotNull(message = "Diện tích không được để trống")
    @DecimalMin(value = "20.00", message = "Diện tích phải lớn hơn 20 m²")
    private BigDecimal dienTich;

    // Constructors
    public GiaoDich() {}

    // Getters and Setters
    public String getMaGiaoDich() { return maGiaoDich; }
    public void setMaGiaoDich(String maGiaoDich) { this.maGiaoDich = maGiaoDich; }
    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }
    public LocalDate getNgayGiaoDich() { return ngayGiaoDich; }
    public void setNgayGiaoDich(LocalDate ngayGiaoDich) { this.ngayGiaoDich = ngayGiaoDich; }
    public String getLoaiDichVu() { return loaiDichVu; }
    public void setLoaiDichVu(String loaiDichVu) { this.loaiDichVu = loaiDichVu; }
    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
    public BigDecimal getDienTich() { return dienTich; }
    public void setDienTich(BigDecimal dienTich) { this.dienTich = dienTich; }
}