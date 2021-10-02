package com.company;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
        public static void main(String[] args) throws MalformedURLException {
                final int width = 600;
                final int height = 800;



                BuildUI calculator = new BuildUI();
                calculator.buildLayout();
                calculator.setSize(width, height);
                calculator.setVisible(true);
        }
}
