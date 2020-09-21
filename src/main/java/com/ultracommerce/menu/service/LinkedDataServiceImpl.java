/*-
 * #%L
 * UltraCommerce Menu
 * %%
 * Copyright (C) 2009 - 2017 Ultra Commerce
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ultracommerce.menu.domain.Menu;
import com.ultracommerce.menu.domain.MenuItem;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Mitash
 */
@Service("ucMenuLinkedDataService")
public class LinkedDataServiceImpl implements LinkedDataService {

    private final Log LOG = LogFactory.getLog(this.getClass());

    @Override
    public String getLinkedData(Menu menu) {
        //Add linked data as attribute
        JSONArray schemaObjects = new JSONArray();
        try {
            for(MenuItem menuItem : menu.getMenuItems()) {
                JSONObject navElement = new JSONObject();
                navElement.put("@context", "http://schema.org/");
                navElement.put("@type", "SiteNavigationElement");
                navElement.put("url", menuItem.getActionUrl());
                navElement.put("name", menuItem.getLabel());
                schemaObjects.put(navElement);
            }
        } catch (JSONException je) {
            LOG.warn("A JSON error occurred while generating linked data for menu items", je);
        }

        return "<script type=\"application/ld+json\">\n" +
                schemaObjects.toString() +
                "\n</script>";
    }
}
