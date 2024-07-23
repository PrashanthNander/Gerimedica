package com.gerimedica.assignment.service.impl;

import com.gerimedica.assignment.exception.ResourceNotFoundException;
import com.gerimedica.assignment.model.Product;
import com.gerimedica.assignment.repository.ProductRepository;
import com.gerimedica.assignment.service.ProductService;
import com.gerimedica.assignment.util.CsvUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Prashanth Nander
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(MultipartFile file) {
        try {
            List <Product> productList = CsvUtility.csvToProductList(file.getInputStream());
            productRepository.saveAll(productList);
        } catch (IOException ex) {
            throw new RuntimeException("Error Saving the products :: " + ex.getMessage());
        }
    }

    @Override
    public List<Product> fetchAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product fetchProductByCode(String code) {
        return productRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code :: "+ code));
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
