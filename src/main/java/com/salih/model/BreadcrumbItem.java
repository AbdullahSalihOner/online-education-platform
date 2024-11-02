package com.salih.model;

// this class use for breadcrumb,its mean show the path of the page
public class BreadcrumbItem {
    private String label;
    private String url;

    public BreadcrumbItem(String label, String url) {
        this.label = label;
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "BreadcrumbItem{" +
                "label='" + label + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
