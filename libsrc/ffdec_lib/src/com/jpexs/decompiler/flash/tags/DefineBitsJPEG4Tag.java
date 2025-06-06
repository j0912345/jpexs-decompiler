/*
 *  Copyright (C) 2010-2025 JPEXS, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.jpexs.decompiler.flash.tags;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.SWFInputStream;
import com.jpexs.decompiler.flash.SWFOutputStream;
import com.jpexs.decompiler.flash.dumpview.DumpInfoSpecialType;
import com.jpexs.decompiler.flash.helpers.ImageHelper;
import com.jpexs.decompiler.flash.tags.base.AloneTag;
import com.jpexs.decompiler.flash.tags.base.HasSeparateAlphaChannel;
import com.jpexs.decompiler.flash.tags.base.ImageTag;
import com.jpexs.decompiler.flash.tags.enums.ImageFormat;
import com.jpexs.decompiler.flash.types.BasicType;
import com.jpexs.decompiler.flash.types.annotations.SWFType;
import com.jpexs.decompiler.flash.types.annotations.SWFVersion;
import com.jpexs.helpers.ByteArrayRange;
import com.jpexs.helpers.JpegFixer;
import com.jpexs.helpers.SerializableImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DefineBitsJPEG4 tag - Contains a JPEG image with alpha channel and deblocking
 * parameter. (optionally a PNG or GIF without the separate alpha channel)
 *
 * @author JPEXS
 */
@SWFVersion(from = 10)
public class DefineBitsJPEG4Tag extends ImageTag implements AloneTag, HasSeparateAlphaChannel {

    public static final int ID = 90;

    public static final String NAME = "DefineBitsJPEG4";

    @SWFType(BasicType.UI16)
    public int deblockParam;

    @SWFType(BasicType.UI8)
    public ByteArrayRange imageData;

    @SWFType(BasicType.UI8)
    public ByteArrayRange bitmapAlphaData;

    /**
     * Constructor
     *
     * @param swf SWF
     */
    public DefineBitsJPEG4Tag(SWF swf) {
        super(swf, ID, NAME, null);
        characterID = swf.getNextCharacterId();
        imageData = new ByteArrayRange(createEmptyImage());
        bitmapAlphaData = ByteArrayRange.EMPTY;
        forceWriteAsLong = true;
    }

    public DefineBitsJPEG4Tag(SWF swf, ByteArrayRange data, int characterID, byte[] imageData) throws IOException {
        super(swf, ID, NAME, data);
        this.characterID = characterID;
        this.imageData = new ByteArrayRange(imageData);
        bitmapAlphaData = ByteArrayRange.EMPTY;
        forceWriteAsLong = true;
    }

    /**
     * Constructor
     *
     * @param sis SWF input stream
     * @param data Data
     * @throws IOException On I/O error
     */
    public DefineBitsJPEG4Tag(SWFInputStream sis, ByteArrayRange data) throws IOException {
        super(sis.getSwf(), ID, NAME, data);
        readData(sis, data, 0, false, false, false);
    }

    @Override
    public final void readData(SWFInputStream sis, ByteArrayRange data, int level, boolean parallel, boolean skipUnusualTags, boolean lazy) throws IOException {
        characterID = sis.readUI16("characterID");
        long alphaDataOffset = sis.readUI32("alphaDataOffset");
        deblockParam = sis.readUI16("deblockParam");
        imageData = sis.readByteRangeEx(alphaDataOffset, "imageData");
        bitmapAlphaData = sis.readByteRangeEx(sis.available(), "bitmapAlphaData", DumpInfoSpecialType.ZLIB_DATA, null);
    }

    /**
     * Gets data bytes
     *
     * @param sos SWF output stream
     * @throws IOException On I/O error
     */
    @Override
    public void getData(SWFOutputStream sos) throws IOException {
        sos.writeUI16(characterID);
        sos.writeUI32(imageData.getLength());
        sos.writeUI16(deblockParam);
        sos.write(imageData);
        sos.write(bitmapAlphaData);
    }

    private byte[] createEmptyImage() {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        ByteArrayOutputStream bitmapDataOS = new ByteArrayOutputStream();
        ImageHelper.write(img, ImageFormat.JPEG, bitmapDataOS);
        return bitmapDataOS.toByteArray();
    }

    @Override
    public void setImage(byte[] data) throws IOException {
        if (ImageTag.getImageFormat(data) == ImageFormat.JPEG) {
            BufferedImage image = ImageHelper.read(data);
            byte[] ba = new byte[image.getWidth() * image.getHeight()];
            for (int i = 0; i < ba.length; i++) {
                ba[i] = (byte) 255;
            }

            bitmapAlphaData = new ByteArrayRange(SWFOutputStream.compressByteArray(ba));
        } else {
            bitmapAlphaData = new ByteArrayRange(SWFOutputStream.compressByteArray(new byte[0]));
        }

        imageData = new ByteArrayRange(data);
        clearCache();
        setModified(true);
    }

    @Override
    public byte[] getImageAlpha() throws IOException {
        return SWFInputStream.uncompressByteArray(bitmapAlphaData.getRangeData());
    }

    @Override
    public void setImageAlpha(byte[] data) throws IOException {
        ImageFormat fmt = ImageTag.getImageFormat(imageData);
        if (fmt != ImageFormat.JPEG) {
            throw new IOException("Only Jpeg can have alpha channel.");
        }

        Dimension dimension = getImageDimension();
        if (data == null || data.length != dimension.getWidth() * dimension.getHeight()) {
            throw new IOException("Data length must match the size of the image.");
        }

        bitmapAlphaData = new ByteArrayRange(SWFOutputStream.compressByteArray(data));
        clearCache();
        setModified(true);
    }

    @Override
    public boolean hasAlphaChannel() {
        return bitmapAlphaData.getLength() > 0;
    }

    @Override
    public ImageFormat getImageFormat() {
        ImageFormat fmt = getOriginalImageFormat();
        if (fmt == ImageFormat.JPEG && bitmapAlphaData.getLength() > 0) {
            fmt = ImageFormat.PNG; //transparency
        }
        return fmt;
    }

    @Override
    public ImageFormat getOriginalImageFormat() {
        return ImageTag.getImageFormat(imageData);
    }

    @Override
    public InputStream getOriginalImageData() {
        //if (bitmapAlphaData.getLength() == 0) { // No alpha

        JpegFixer jpegFixer = new JpegFixer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            jpegFixer.fixJpeg(new ByteArrayInputStream(imageData.getArray(), imageData.getPos(), imageData.getLength()), baos);
        } catch (IOException ex) {
            Logger.getLogger(DefineBitsJPEG4Tag.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ByteArrayInputStream(baos.toByteArray());
        //}

        //return null;
    }

    @Override
    protected SerializableImage getImage() {
        try {
            JpegFixer jpegFixer = new JpegFixer();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                jpegFixer.fixJpeg(new ByteArrayInputStream(imageData.getArray(), imageData.getPos(), imageData.getLength()), baos);
            } catch (IOException ex) {
                Logger.getLogger(DefineBitsJPEG3Tag.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedImage image = ImageHelper.read(new ByteArrayInputStream(baos.toByteArray()));
            if (image == null) {
                Logger.getLogger(DefineBitsJPEG4Tag.class.getName()).log(Level.SEVERE, "Failed to load image");
                return null;
            }

            SerializableImage img = new SerializableImage(image);
            if (bitmapAlphaData.getLength() == 0) {
                return img;
            }

            byte[] alphaData = getImageAlpha();
            if (alphaData.length == 0) {
                return img;
            }

            int width = img.getWidth();
            int height = img.getHeight();
            SerializableImage img2 = new SerializableImage(width, height, SerializableImage.TYPE_INT_ARGB);
            int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
            int[] pixels2 = ((DataBufferInt) img2.getRaster().getDataBuffer()).getData();
            for (int i = 0; i < pixels.length; i++) {
                int a = alphaData[i] & 0xff;
                int b = (pixels[i] >> 16) & 0xff;
                int g = (pixels[i] >> 8) & 0xff;
                int r = (pixels[i]) & 0xff;
                r = (int) Math.floor(r * 255.0 / a);
                g = (int) Math.floor(g * 255.0 / a);
                b = (int) Math.floor(b * 255.0 / a);
                if (r > 255) {
                    r = 255;
                }
                if (g > 255) {
                    g = 255;
                }
                if (b > 255) {
                    b = 255;
                }

                pixels2[i] = (a << 24) | (b << 16) | (g << 8) | r;
            }

            return img2;
        } catch (IOException ex) {
            Logger.getLogger(DefineBitsJPEG4Tag.class.getName()).log(Level.SEVERE, "Failed to get image", ex);
        }

        SerializableImage img = new SerializableImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = img.getGraphics();
        g.setColor(SWF.ERROR_COLOR);
        g.fillRect(0, 0, 1, 1);
        return img;
    }

    @Override
    public Dimension getImageDimension() {
        if (cachedImage != null) {
            return new Dimension(cachedImage.getWidth(), cachedImage.getHeight());
        }

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData.getArray(), imageData.getPos(), imageData.getLength());
            return ImageHelper.getDimension(bis);
        } catch (IOException ex) {
            Logger.getLogger(DefineBitsJPEG3Tag.class.getName()).log(Level.SEVERE, "Failed to get image dimension", ex);
        }

        return new Dimension(1, 1);
    }
}
