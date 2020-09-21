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
package com.ultracommerce.menu.demo;

import com.ultracommerce.common.demo.AutoImportPersistenceUnit;
import com.ultracommerce.common.demo.AutoImportSql;
import com.ultracommerce.common.demo.AutoImportStage;
import com.ultracommerce.common.demo.DemoCondition;
import com.ultracommerce.common.demo.ImportCondition;
import com.ultracommerce.common.demo.MTCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jeff Fischer
 */
@Configuration("ucMenuData")
@Conditional(ImportCondition.class)
public class ImportSQLConfig {

    @Bean
    @Conditional({MTCondition.class, DemoCondition.class})
    public AutoImportSql ucMenuLateData() {
        return new AutoImportSql(AutoImportPersistenceUnit.UL_PU,"config/bc/sql/demo/fix_menu_data.sql", AutoImportStage.PRIMARY_LATE);
    }

    @Bean
    @Conditional(DemoCondition.class)
    public AutoImportSql ucMenuSecurity() {
        return new AutoImportSql(AutoImportPersistenceUnit.UL_PU,"config/bc/sql/load_menu_admin_security.sql", AutoImportStage.PRIMARY_MODULE_SECURITY);
    }

    @Bean
    @Conditional(DemoCondition.class)
    public AutoImportSql ucMenuBasicData() {
        return new AutoImportSql(AutoImportPersistenceUnit.UL_PU,"config/bc/sql/demo/load_menu_data.sql", AutoImportStage.PRIMARY_BASIC_DATA);
    }

}
