package com.it.service;

import com.it.domain.Product;

import java.util.List;

public interface IProductService {

    public List<Product> findAll() throws Exception;

}
