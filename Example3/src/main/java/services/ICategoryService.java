package services;

import model.Category;

import java.awt.*;
import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategory();
    Category findCategoryById(int id);
    boolean insertCategoryBySP(String nameCategory);
}
