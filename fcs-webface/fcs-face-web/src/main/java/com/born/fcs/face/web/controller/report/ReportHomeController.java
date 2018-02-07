package com.born.fcs.face.web.controller.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.fcs.face.web.controller.base.BaseController;

@Controller
@RequestMapping("reportMg")
public class ReportHomeController extends BaseController {

    final static String vm_path = "/reportMg/";

    @RequestMapping("index.htm")
    public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
        buildSystemNameDefaultUrl(request, model);
        return vm_path + "index.vm";
    }
}
