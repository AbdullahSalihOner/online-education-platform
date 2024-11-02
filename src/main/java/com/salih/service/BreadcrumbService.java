package com.salih.service;

import com.salih.model.BreadcrumbItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BreadcrumbService {

    public List<BreadcrumbItem> generateBreadcrumb(String currentPath) {
        List<BreadcrumbItem> breadcrumbs = new ArrayList<>();

        // Ana sayfa her zaman ilk breadcrumb
        breadcrumbs.add(new BreadcrumbItem("Home", "/"));

        // Diğer yol adımlarını ekle
        String[] pathSegments = currentPath.split("/");

        StringBuilder currentUrl = new StringBuilder();
        for (String segment : pathSegments) {
            if (!segment.isEmpty()) {
                currentUrl.append("/").append(segment);
                breadcrumbs.add(new BreadcrumbItem(capitalize(segment), currentUrl.toString()));
            }
        }

        return breadcrumbs;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
