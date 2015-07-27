package org.defence.domain.entities;

import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public class CatalogGroup implements ICatalogAttributes {
    private int id;
    private String name;
    private String number;
    private String include;
    private String exclude;
    private List<ICatalogAttributes> CatalogClassList;
}
