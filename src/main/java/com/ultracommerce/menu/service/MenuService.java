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
package com.ultracommerce.menu.service;

import com.ultracommerce.menu.domain.Menu;
import com.ultracommerce.menu.domain.MenuItem;
import com.ultracommerce.menu.dto.MenuItemDTO;
import java.util.List;

public interface MenuService {

    /**
     * Returns the menu matching the passed in id. 
     * @param menuId
     * @return
     */
    public Menu findMenuById(Long menuId);

    /**
     * Returns the menu matching the passed in name. 
     * @param menuName
     * @return
     */
    public Menu findMenuByName(String menuName);

    /**
     * Returns the menu item matching the passed in id.
     * @param menuItemId
     * @return
     */
    public MenuItem findMenuItemById(Long menuItemId);

    /**
     * A Utility method that constructs generic MenuItemDTOs that are not dependent on a Menu Item Type.
     * Allows for ease of use when building the front-end.
     *
     * @param menu
     * @return
     */
    public List<MenuItemDTO> constructMenuItemDTOsForMenu(Menu menu);
}
