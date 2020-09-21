package com.ultracommerce.menu.copy;
/*
 * #%L
 * UltraCommerce Pricelist
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


import com.ultracommerce.common.copy.MultiTenantCopier;
import com.ultracommerce.common.copy.MultiTenantCopyContext;
import com.ultracommerce.common.site.domain.Catalog;
import com.ultracommerce.common.site.domain.Site;
import com.ultracommerce.menu.domain.MenuImpl;

/**
 * Clone menu entities
 *
 * @author Jeff Fischer
 */
public class MenuCopier extends MultiTenantCopier {

    @Override
    public void copyEntities(MultiTenantCopyContext context) throws Exception {
        Site fromSite = context.getFromSite();
        Catalog fromCatalog = context.getFromCatalog();

        copyEntitiesOfType(MenuImpl.class, fromSite, fromCatalog, context);

    }

}
