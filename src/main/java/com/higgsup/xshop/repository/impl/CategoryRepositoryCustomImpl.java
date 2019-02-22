package com.higgsup.xshop.repository.impl;

import com.higgsup.xshop.common.JpaResultConverter;
import com.higgsup.xshop.dto.BreadcrumbDTO;
import com.higgsup.xshop.repository.CategoryRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

  private final EntityManager entityManager;

  public CategoryRepositoryCustomImpl(
      EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<BreadcrumbDTO> searchBreadcrumbByCategoryId(Integer categoryId) {
    String sql =
        "with recursive cte (id, name, parent_id, `level`) as ("
            + "  select     id,"
            + "             name,"
            + "             ifnull(parent_id, 0),"
            + "             `level`"
            + "  from       category"
            + "  where      id = :categoryId"
            + "  union all"
            + "  select     c.id,"
            + "             c.name,"
            + "             ifnull(c.parent_id, 0),"
            + "             c.`level`"
            + "  from       category c"
            + "  inner join cte"
            + "          on c.id = cte.parent_id"
            + ")"
            + "select * from cte order by `level`;";
    Query query = entityManager.createNativeQuery(sql)
        .setParameter("categoryId", categoryId);
    return new JpaResultConverter().list(query, BreadcrumbDTO.class);
  }
}