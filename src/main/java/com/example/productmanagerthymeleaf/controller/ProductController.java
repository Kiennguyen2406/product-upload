package com.example.productmanagerthymeleaf.controller;


import com.example.productmanagerthymeleaf.model.Product;
import com.example.productmanagerthymeleaf.model.ProductForm;
import com.example.productmanagerthymeleaf.service.IProductService;
import com.example.productmanagerthymeleaf.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
@PropertySource("classpath:upload_file.properties")
public class ProductController {
    private final IProductService productService = new ProductService();

    @GetMapping("")
    public String index(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "/create";
    }

    @Value("${file-upload}")
    private String upload;

    //    @PostMapping("/save")
//    public String save(Product product) {
//
//
//
//        productService.addProduct(product);
//        return "redirect:/products";
//    }
    @PostMapping("/save")
    public String save(ProductForm productForm) {
        MultipartFile file = productForm.getImage();

        String fileName = file.getOriginalFilename();

        try {
            FileCopyUtils.copy(file.getBytes(),new File(upload+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Product product = new Product();
        product.setId(productForm.getId());
        product.setDescribe(productForm.getDescribe());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setManufacturer(productForm.getManufacturer());
        product.setImage(fileName);

        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showFormEdit(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/edit";
    }

//    @PostMapping("/update")
//    public String update(Product product) {
//        productService.updateProduct(product.getId(), product);
//        return "redirect:/products";
//    }

    @PostMapping("/update")
    public String update(ProductForm productForm) {
        MultipartFile file = productForm.getImage();
        String filename = file.getOriginalFilename();

        try {
            FileCopyUtils.copy(filename.getBytes(),new File(upload+filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Product product = new Product();
        product.setId(productForm.getId());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setDescribe(productForm.getDescribe());
        product.setManufacturer(productForm.getManufacturer());
        product.setImage(filename);

        productService.updateProduct(product.getId(), product);
         return "redirect:/products";
    }

    @GetMapping("/{id}/delete")
    public String showFormDelete(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/delete";
    }

    @PostMapping("/delete")
    public String delete(int id, RedirectAttributes redirect) {
        productService.deleteProduct(id);
        redirect.addFlashAttribute("success", "Removed customer successfully!");
        return "redirect:/products";
    }

    @GetMapping("{id}/view")
    public String showView(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/view";
    }

    @GetMapping("search")
    public String search(@RequestParam String name, Model model) {
        List<Product> products = productService.searchByName(name);
        model.addAttribute("productList", products);
        return "/index";
    }
}
