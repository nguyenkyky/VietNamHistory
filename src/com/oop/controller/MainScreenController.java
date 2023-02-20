package com.oop.controller;

import com.oop.model.NhanVatModel;
import com.oop.model.ThoiKyModel;
import com.oop.service.ThoiKyService;
import com.oop.service.impl.ThoiKyServiceImpl;
import com.oop.util.HienThi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainScreenController {

    @FXML
    private HBox linkContainer;

    @FXML
    private VBox main;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchTf;
    @FXML
    private ScrollPane scrollPane;

    private ThoiKyService thoiKyService = ThoiKyServiceImpl.getInstance();

    private void addList(String title, String url) {
        ListController controller = new ListController();
        controller.setTitle(title);
        controller.setLink(url);
        controller.add(main);
//        System.out.println(listContainer.getHeight());
    }

    public void addHomeData() {
        DanhSachController danhSachTinhNang = new DanhSachController();
        danhSachTinhNang.setDanhSachTitle(HienThi.DANH_SACH_TINH_NANG);
        danhSachTinhNang.add(main);
        addList(HienThi.DANH_SACH_THOI_KY, HienThi.getThoiKyUrl());
        addList(HienThi.DANH_SACH_NHAN_VAT, HienThi.getNhanVatUrl());
    }

    private void setLinkContainer(String url) {
        linkContainer.getChildren().clear();
        String[] arr = url.split("/");
        String realUrl = "";
        for (int i = 1; i < arr.length; i++) {
            realUrl += "/" + arr[i];
            UrlController urlController = new UrlController(realUrl);
            urlController.setUrl("/ " + arr[i]);
            urlController.add(linkContainer);
        }
    }

    private void addDanhSachThoiKyData() {
        DanhSachController danhSachThoiKy = new DanhSachController();
        danhSachThoiKy.setDanhSachTitle(HienThi.DANH_SACH_THOI_KY);
        danhSachThoiKy.add(main);
        List<ThoiKyModel> data = thoiKyService.getAllThoiKy();
        for (ThoiKyModel d : data) {
            addList(d.getName(), d.getUrl());
        }
    }

    private void addChiTiet(String title, String value) {
        ChiTietController chiTietController = new ChiTietController();
        chiTietController.setTitle(title);
        chiTietController.setValue(value);
        chiTietController.add(main);
    }

    public void showNhanVatLienQuanThoiKy(String name) {
//        List<NhanVatModel>
    }

    private void showDetailThoiKy(String name) {
        DetailController detailController = new DetailController();
        detailController.setDetailTitle(name);
        detailController.add(main);

        Map<String, String> des = thoiKyService.getThoiKyByName(name);
        for (Map.Entry<String, String> m : des.entrySet()) {
            addChiTiet(m.getKey(), m.getValue());
        }

        showNhanVatLienQuanThoiKy(name);
    }

    public void moveToUrl(String url) {
        setLinkContainer(url);
        main.getChildren().clear();
        if (HienThi.HOME_URL.equalsIgnoreCase(url)) {
            addHomeData();
        }
        if (HienThi.getThoiKyUrl().equalsIgnoreCase(url)) {
            addDanhSachThoiKyData();
        } else if (url.contains(HienThi.getThoiKyUrl())) {
            String thoiKyName = HienThi.getDataFromUrl(url);
            showDetailThoiKy(thoiKyName);
        }
    }

}
