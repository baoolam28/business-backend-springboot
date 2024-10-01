package com.onestep.business_management.Controller.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onestep.business_management.Service.CategoryService.CategoryService;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryAdminController {
    @Autowired
    private CategoryService categoryService;

    
}
