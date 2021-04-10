module test {
    requires javafx.base;
    requires javafx.media;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.swt;
    requires javafx.controls;
    requires JFlex;
    requires com.jfoenix;

    opens sample;
    opens sample.models to javafx.base;
}