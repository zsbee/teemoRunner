package com.Zsombor.Fuszenecker.Teemo.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Zsombor on 5/6/14.
 */
public class TrapSprite extends Sprite
{
    public int _onMap = ObjectConstants.TRAP_OUTOFMAP;

    /** Creates a sprite with width, height, and texture region equal to the size of the texture. */
    public TrapSprite (Texture texture) {
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    public int getOnMap()
    {
        return _onMap;
    }
    public void setOnMap(int y)
    {
        _onMap = y;
    }
}
