package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getManageProductPage(Model model,
            @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1;
            }
        } catch (Exception e) {
            // page = 1;
        }
        // client: page
        // database : offset + limit

        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<Product> prs = this.productService.getAllProducts(pageable);
        List<Product> listProducts = prs.getContent();

        model.addAttribute("allProducts", listProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());

        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(@ModelAttribute("newProduct") @Valid Optional<Product> newProduct,
            BindingResult newProductBindingResult, @RequestParam("fileImage") MultipartFile file) {

        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }
        String avatar = this.uploadService.handleSaveUpLoadFile(file, "product");
        newProduct.get().setImage(avatar);
        this.productService.handleSaveProduct(newProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getDetailProduct(@PathVariable long id, Model model) {
        Optional<Product> product1 = this.productService.getProductById(id);
        Product product = product1.get();
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdatePage(@PathVariable long id, Model model) {
        Optional<Product> product = this.productService.getProductById(id);
        String tmp = "http://localhost:8081/images/product/" + product.get().getImage();
        model.addAttribute("oldProduct", product);
        model.addAttribute("image", tmp);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute("oldProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("fileImage") MultipartFile file) {

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Optional<Product> currentProduct = this.productService.getProductById(product.getId());

        if (currentProduct != null) {
            currentProduct.get().setName(product.getName());
            currentProduct.get().setPrice(product.getPrice());
            currentProduct.get().setQuantity(product.getQuantity());
            currentProduct.get().setDetailDesc(product.getDetailDesc());
            currentProduct.get().setShortDesc(product.getShortDesc());
            currentProduct.get().setFactory(product.getFactory());
            currentProduct.get().setTarget(product.getTarget());
            if (!file.isEmpty()) {
                String avatar = this.uploadService.handleSaveUpLoadFile(file, "product");
                currentProduct.get().setImage(avatar);
            }

        }
        this.productService.handleSaveProduct(currentProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProduct(@PathVariable long id, Model model) {
        Optional<Product> product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteById(product.getId());
        return "redirect:/admin/product";
    }

}
