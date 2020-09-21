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
package com.ultracommerce.menu.domain;

import com.ultracommerce.common.admin.domain.AdminMainEntity;
import com.ultracommerce.common.copy.CreateResponse;
import com.ultracommerce.common.copy.MultiTenantCopyContext;
import com.ultracommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.ultracommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.ultracommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.ultracommerce.common.extensibility.jpa.copy.ProfileEntity;
import com.ultracommerce.common.i18n.domain.TranslatedEntity;
import com.ultracommerce.common.i18n.service.DynamicTranslationProvider;
import com.ultracommerce.common.presentation.AdminPresentation;
import com.ultracommerce.common.presentation.AdminPresentationClass;
import com.ultracommerce.common.presentation.AdminPresentationCollection;
import com.ultracommerce.common.presentation.client.AddMethodType;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "UC_CMS_MENU")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "ucCMSElements")
@AdminPresentationClass(friendlyName = "MenuImpl")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps = true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class MenuImpl implements Menu, AdminMainEntity, ProfileEntity {

    private static final long serialVersionUID = 1L;

    static {
        new TranslatedEntity("com.ultracommerce.menu.domain.Menu", "Menu");
    }

    @Id
    @GeneratedValue(generator = "MenuId")
    @GenericGenerator(
            name = "MenuId",
            strategy = "com.ultracommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name = "segment_value", value = "MenuImpl"),
                    @Parameter(name = "entity_name", value = "com.ultracommerce.menu.domain.MenuImpl")
            })
    @Column(name = "MENU_ID")
    protected Long id;

    @Column(name = "NAME", nullable = false)
    @AdminPresentation(friendlyName = "MenuImpl_Name",
            order = Presentation.FieldOrder.NAME,
            gridOrder = Presentation.FieldOrder.NAME,
            prominent = true,
            translatable = true)
    protected String name;

    @OneToMany(mappedBy = "parentMenu", targetEntity = MenuItemImpl.class, cascade = { CascadeType.ALL })
    @AdminPresentationCollection(friendlyName = "MenuItemImpl_MenuItems",
            sortProperty = "sequence",
            addType = AddMethodType.PERSIST)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "ucCMSElements")
    @BatchSize(size = 50)
    @OrderBy(value = "sequence")
    protected List<MenuItem> menuItems = new ArrayList<MenuItem>(20);

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return DynamicTranslationProvider.getValue(this, "name", name);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    @Override
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }

    public static class Presentation {
        private Presentation() {
        }

        public static class FieldOrder {
            private FieldOrder() {
            }

            // General Fields
            public static final int NAME = 1000;
        }

    }

    @Override
    public <G extends Menu> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        Menu cloned = createResponse.getClone();
        cloned.setName(name);
        for (MenuItem item : menuItems) {
            cloned.getMenuItems().add(item.createOrRetrieveCopyInstance(context).getClone());
        }
        return createResponse;
    }
}
