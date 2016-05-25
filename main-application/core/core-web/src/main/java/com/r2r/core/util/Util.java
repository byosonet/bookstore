/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.util;

import com.r2r.core.db.entity.common.UModifListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arturo
 */
public class Util {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UModifListener.class);

    public static byte[] convertImageToByteArray(String path) {
        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            return baos.toByteArray();

        } catch (IOException e) {
            LOGGER.error("Error al convertir image to byte[]: ", e);
            return null;
        }
    }

}
