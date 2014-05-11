package com.Zsombor.Fuszenecker.Objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

/**
 * Created by Zsombor on 5/3/14.
 */
public class NidaleeSpear extends Texture
{

    public NidaleeSpear(String internalPath)
    {
        super(internalPath);
    }

    public NidaleeSpear(FileHandle file)
    {
        super(file);
    }

    public NidaleeSpear(FileHandle file, boolean useMipMaps)
    {
        super(file, useMipMaps);
    }

    public NidaleeSpear(FileHandle file, Pixmap.Format format, boolean useMipMaps)
    {
        super(file, format, useMipMaps);
    }

    public NidaleeSpear(Pixmap pixmap)
    {
        super(pixmap);
    }

    public NidaleeSpear(Pixmap pixmap, boolean useMipMaps)
    {
        super(pixmap, useMipMaps);
    }

    public NidaleeSpear(Pixmap pixmap, Pixmap.Format format, boolean useMipMaps)
    {
        super(pixmap, format, useMipMaps);
    }

    public NidaleeSpear(int width, int height, Pixmap.Format format)
    {
        super(width, height, format);
    }

    public NidaleeSpear(TextureData data)
    {
        super(data);
    }
}
