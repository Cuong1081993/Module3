package services;

import model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProduct();

    List<Product> getAllProductByKwAndIdCategory(String kw, int idCategory);

    void addProduct(Product product);

    Product findProductById(int id);

    void updateProduct(Product product);

    void deleteProduct(int id);

    List<Product> getAllProductByIdCategory(int idCategory);
    int getNoOfRecords();
    List<Product> getAllProductByIdCategoryPagging(String kw, int idCategory,int page, int pageNumber);
}
