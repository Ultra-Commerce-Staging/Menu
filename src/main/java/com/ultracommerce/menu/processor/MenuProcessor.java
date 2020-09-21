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

package com.ultracommerce.menu.processor;

import com.ultracommerce.menu.domain.Menu;
import com.ultracommerce.menu.service.LinkedDataService;
import com.ultracommerce.menu.service.MenuService;
import com.ultracommerce.presentation.condition.ConditionalOnTemplating;
import com.ultracommerce.presentation.dialect.AbstractUltraVariableModifierProcessor;
import com.ultracommerce.presentation.model.UltraTemplateContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * A Thymeleaf processor that will add a list of MenuItemDTOs to the model.
 *
 * It accepts a menuName or menuId. The precedence is that a menuId
 * will honored first, followed by a menuName.
 * An extension manager may override the resulting menu if configured to do so.
 *
 * @author bpolster
 */
@Component("ucMenuProcessor")
@ConditionalOnTemplating
public class MenuProcessor extends AbstractUltraVariableModifierProcessor {

    @Resource(name = "ucMenuService")
    protected MenuService menuService;

    @Resource(name = "ucMenuLinkedDataService")
    protected LinkedDataService linkedDataService;

    @Resource(name = "ucMenuProcessorExtensionManager")
    protected MenuProcessorExtensionManager extensionManager;

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    public Map<String, Object> populateModelVariables(String tagName, Map<String, String> tagAttributes, UltraTemplateContext context) {
        String resultVar = tagAttributes.get("resultVar");
        String menuName = tagAttributes.get("menuName");
        String menuId = tagAttributes.get("menuId");

        final Menu menu;

        if (menuId != null) {
            menu = menuService.findMenuById(Long.parseLong(menuId));
        } else {
            menu = menuService.findMenuByName(menuName);
        }

        Map<String, Object> newModelVars = new HashMap<>();
        if (menu != null) {
            newModelVars.put(resultVar, menuService.constructMenuItemDTOsForMenu(menu));
            extensionManager.getProxy().addAdditionalFieldsToModel(tagName, tagAttributes, newModelVars, context);

            newModelVars.put("menuLinkedData", linkedDataService.getLinkedData(menu));
        }

        return newModelVars;
    }
}
