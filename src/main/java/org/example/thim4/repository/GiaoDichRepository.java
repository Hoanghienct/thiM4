package org.example.thim4.repository;

import org.example.thim4.entity.GiaoDich;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiaoDichRepository extends JpaRepository<GiaoDich, String> {
    List<GiaoDich> findByKhachHangTenKhachHangContaining(String tenKhachHang);
    List<GiaoDich> findByLoaiDichVu(String loaiDichVu);
    List<GiaoDich> findByKhachHangTenKhachHangContainingAndLoaiDichVu(String tenKhachHang, String loaiDichVu);
}