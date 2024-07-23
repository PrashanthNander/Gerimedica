package com.gerimedica.assignment.service;

import com.gerimedica.assignment.exception.ResourceNotFoundException;
import com.gerimedica.assignment.model.Product;
import com.gerimedica.assignment.repository.ProductRepository;
import com.gerimedica.assignment.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Prashanth Nander
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    Product product;

    @BeforeEach
    public void initMethod() {
        product = Product.builder()
                .id(10L)
                .codeListCode("ZIB001")
                .code("271636001")
                .displayValue("Polsslag regelmatig")
                .longDescription("The long description is necessary")
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .sortingPriority(1)
                .build();
    }

    @Test
    public void returnsAllProduct() {
        Mockito.lenient().when(productRepository.saveAll(Mockito.any())).thenReturn(productList());
        productService.fetchAllProducts();
    }

    @Test
    public void deletesAllProduct() {
        Mockito.lenient().doNothing().when(productRepository).deleteAll();
        productService.deleteAllProducts();
    }

    @Test
    public void whenValidCode_returnsProduct() {
        Mockito.when(productRepository.findByCode(product.getCode())).thenReturn(Optional.ofNullable(product));
        Product productByCode = productService.fetchProductByCode(product.getCode());
        Assertions.assertNotNull(productByCode);
        Assertions.assertEquals("271636001", product.getCode());
    }


    @Test
    public void whenInValidCode_throwsException() {
        Mockito.when(productRepository.findByCode(Mockito.any())).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.fetchProductByCode(product.getCode()));
    }

    public List<Product> productList() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        return products;
    }



}
