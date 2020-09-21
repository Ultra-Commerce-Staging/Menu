# Data Model

[![Menu](MenuERD.png)](\_img/MenuERD.png)

### Tables


| Table                            | Related Entity                                                                                 | Description                                                                                         |
| :------------------------------- | :--------------------------------------------------------------------------------------------- | :-------------------------------------------------------------------------------------------------- |
| `UC_CMS_MENU`                   | ^[javadoc:com.ultracommerce.menu.domain.Menu]                                              | Represents a menu, typically to drive the display of navigation on a website.                       |
| `UC_CMS_MENU_ITEM`              | ^[javadoc:com.ultracommerce.menu.domain.MenuItem]                                          | Represents a menu item. Can be of different types: Link, Category, Page, Submenu, Product, Custom...|
