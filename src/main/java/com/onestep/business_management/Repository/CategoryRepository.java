package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
