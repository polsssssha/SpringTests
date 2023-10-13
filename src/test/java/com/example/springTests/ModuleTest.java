package com.example.springTests;

import com.example.springTests.model.Product;
import com.example.springTests.repository.ShoppingListRepository;
import com.example.springTests.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;

public class ModuleTest {

    @InjectMocks
    private ShoppingListService productService;

    @Mock
    private ShoppingListRepository productRepository;

    @Test
    public void addProduct(){
        Product savedProduct = new Product(1L, "milk", false);
        Mockito.when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        productService.save(savedProduct);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("milk");


        Mockito.verify(productRepository).save(savedProduct);
    }

    @Test
    public void deleteProduct(){
        Product product = new Product(1L, "milk", false);
        Long id = product.getId();

        productService.remove(id);
        Mockito.verify(productRepository).deleteProductById(id);
    }

    @Test
    public void updateProduct(){
        Product savedProduct = new Product(1L, "milk", false);
        Mockito.when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        productService.save(savedProduct);

        boolean bought = savedProduct.isBought();
        savedProduct.setBought(!bought);
        productService.save(savedProduct);
        Mockito.doReturn(savedProduct).when(productRepository).findProductById(savedProduct.getId());
        var product = productService.getById(savedProduct.getId());
        assertThat(product.isBought()).isEqualTo(!bought);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}