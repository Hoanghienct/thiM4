package org.example.thim4.controller;

import jakarta.validation.Valid;
import org.example.thim4.entity.GiaoDich;
import org.example.thim4.repository.GiaoDichRepository;
import org.example.thim4.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


import java.util.List;

@Controller
@RequestMapping("/giao-dich")
public class GiaoDichController {

    @Autowired
    private GiaoDichRepository giaoDichRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    // 1. Hiển thị danh sách giao dịch và xử lý tìm kiếm
    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) String tenKhachHang,
                       @RequestParam(required = false) String loaiDichVu) {
        List<GiaoDich> giaoDichList;

        // Chuẩn hóa dữ liệu tìm kiếm
        if (tenKhachHang != null) {
            tenKhachHang = tenKhachHang.trim();
            if (tenKhachHang.isEmpty()) tenKhachHang = null;
        }
        if (loaiDichVu != null && loaiDichVu.isEmpty()) {
            loaiDichVu = null;
        }

        // Tìm kiếm
        if (tenKhachHang != null && loaiDichVu != null) {
            giaoDichList = giaoDichRepository.findByKhachHangTenKhachHangContainingAndLoaiDichVu(tenKhachHang, loaiDichVu);
        } else if (tenKhachHang != null) {
            giaoDichList = giaoDichRepository.findByKhachHangTenKhachHangContaining(tenKhachHang);
        } else if (loaiDichVu != null) {
            giaoDichList = giaoDichRepository.findByLoaiDichVu(loaiDichVu);
        } else {
            giaoDichList = giaoDichRepository.findAll();
        }

        model.addAttribute("giaoDichList", giaoDichList);
        model.addAttribute("tenKhachHang", tenKhachHang);
        model.addAttribute("loaiDichVu", loaiDichVu);
        if (giaoDichList.isEmpty() && (tenKhachHang != null || loaiDichVu != null)) {
            model.addAttribute("message", "Không tìm thấy giao dịch nào!");
            model.addAttribute("alertClass", "alert-warning");
        }

        return "giao-dich/list"; // Trả về view giao-dich/list
    }

    // 2. Hiển thị form thêm mới
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("giaoDich", new GiaoDich());
        model.addAttribute("khachHangList", khachHangRepository.findAll());
        return "giao-dich/add";
    }

    // 3. Xử lý thêm mới giao dịch với validation
    @PostMapping("/new")
    public String addGiaoDich(@Valid @ModelAttribute GiaoDich giaoDich,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("khachHangList", khachHangRepository.findAll());
            return "giao-dich/add";
        }
        giaoDichRepository.save(giaoDich);
        redirectAttributes.addFlashAttribute("message", "Thêm giao dịch thành công!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/giao-dich";
    }

    // 4. Xem chi tiết giao dịch
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String id, Model model) {
        GiaoDich giaoDich = giaoDichRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid giao dich Id:" + id));
        model.addAttribute("giaoDich", giaoDich);
        return "giao-dich/detail";
    }

    // 5. Xóa giao dịch
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        giaoDichRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa giao dịch thành công!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/giao-dich";
    }
}