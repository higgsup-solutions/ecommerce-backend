package com.higgsup.xshop.repository.impl;

import com.higgsup.xshop.common.JpaResultConverter;
import com.higgsup.xshop.dto.BreadcrumbDTO;
import com.higgsup.xshop.repository.CategoryRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<BreadcrumbDTO> searchBreadcrumbByCategoryId(Integer categoryId) {
    String sql = "with recursive cte (id, name, parent_id, `level`) as ("
        + "  select     id,"
        + "             name,"
        + "             parent_id,"
        + "             `level`"
        + "  from       category"
        + "  where      id = :categoryId"
        + "  union all"
        + "  select     c.id,"
        + "             c.name,"
        + "             c.parent_id,"
        + "             c.`level`"
        + "  from       category c"
        + "  inner join cte"
        + "          on c.id = cte.parent_id"
        + ")"
        + "select * from cte order by `level`;";

    Query query = entityManager.createNativeQuery(sql).setParameter("categoryId", categoryId);
    return new JpaResultConverter().list(query,BreadcrumbDTO.class);
  }
}
