package com.catclient.duke.utils.client;

import com.catclient.duke.utils.wrapper.Wrapper;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/1 01:03
 * @Filename：LibraryUtils
 */
public class LibraryUtils implements Wrapper {
    public static void loadNatives() {
    try {
        Path nativeUtils = Files.createTempFile("", ".dll");
        nativeUtils.toFile().deleteOnExit();
        Files.copy(LibraryUtils.class.getResourceAsStream("/Duke/natives/NativeUtils.dll"), nativeUtils, StandardCopyOption.REPLACE_EXISTING);
        System.load(nativeUtils.toString());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error loading native libraries.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    public static URLClassLoader loadLibrary() {
        try {
            URL[] urlArray = new URL[]{};
            return new URLClassLoader(urlArray, LibraryUtils.class.getClassLoader());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading libraries.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }
}
