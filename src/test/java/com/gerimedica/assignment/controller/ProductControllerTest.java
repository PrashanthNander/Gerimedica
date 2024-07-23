package com.gerimedica.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerimedica.assignment.model.Product;
import com.gerimedica.assignment.repository.ProductRepository;
import com.gerimedica.assignment.service.ProductService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Prashanth Nander
 */
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

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
    public void onValidProductCode_returnsProduct() throws Exception {
        Mockito.when(productService.fetchProductByCode(Mockito.any())).thenReturn(product);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/{code}", product.getCode())
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(10)));
        Mockito.verify(productRepository    , Mockito.times(0)).findById(Mockito.any());

    }

    @Test
    public void whenValidRequest_thenReturnsAllProducts() throws Exception {
        Mockito.when(productService.fetchAllProducts()).thenReturn(productList());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));

    }

    @Test
    public void whenValidRequest_thenDeletesAllProducts() throws Exception {
        Mockito.doNothing().when(productService).deleteAllProducts();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    public List<Product> productList() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        return products;
    }

}
