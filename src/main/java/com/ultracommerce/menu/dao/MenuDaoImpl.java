/*
 * #%L
 * UltraCommerce Menu
 * %%
 * Copyright (C) 2009 - 2016 Ultra Commerce
 * %%
 * Licensed under the Ultra Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.ultracommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Ultra in which case
 * the Ultra End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.ultracommerce.org/commercial_license-1.1.txt)
 * shall apply.
 *
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Ultra Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
package com.ultracommerce.menu.dao;

import com.ultracommerce.common.persistence.HibernateBridgingQueryHints;
import com.ultracommerce.common.util.dao.TypedQueryBuilder;
import com.ultracommerce.menu.domain.Menu;
import com.ultracommerce.menu.domain.MenuImpl;
import com.ultracommerce.menu.domain.MenuItem;
import com.ultracommerce.menu.domain.MenuItemImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository("ucMenuDao")
public class MenuDaoImpl implements MenuDao {

    @PersistenceContext(unitName = "ucPU")
    protected EntityManager em;

    @Override
    public List<Menu> readAllMenus() {
        TypedQuery<Menu> q = new TypedQueryBuilder<Menu>(Menu.class, "m")
                .toQuery(em);
        return q.getResultList();
    }

    @Override
    public List<MenuItem> readAllMenuItems() {
        TypedQuery<MenuItem> q = new TypedQueryBuilder<MenuItem>(MenuItem.class, "mi")
                .toQuery(em);
        return q.getResultList();
    }

    @Override
    public Menu readMenuById(Long menuId) {
        return em.find(MenuImpl.class, menuId);
    }

    @Override
    public MenuItem readMenuItemById(Long menuItemId) {
        return em.find(MenuItemImpl.class, menuItemId);
    }

    @Override
    public Menu readMenuByName(String menuName) {
        TypedQuery<Menu> query = em.createNamedQuery("UC_READ_MENU_BY_NAME", Menu.class);
        query.setParameter("menuName", menuName);
        query.setHint(HibernateBridgingQueryHints.CACHEABLE, true);
        query.setHint(HibernateBridgingQueryHints.CACHE_REGION, "query.Cms");

        List<Menu> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Menu saveMenu(Menu menu) {
        return em.merge(menu);
    }

    @Override
    public MenuItem saveMenuItem(MenuItem menuItem) {
        return em.merge(menuItem);
    }

}
