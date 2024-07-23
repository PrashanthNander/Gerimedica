package com.gerimedica.assignment.service;

import com.gerimedica.assignment.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Prashanth Nander
 */
public interface ProductService {

    void save(MultipartFile file);

    List<Product> fetchAllProducts();

    Product fetchProductByCode(String code);

    void deleteAllProducts();


}
