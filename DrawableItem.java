package model;
import java.awt.Image;
    
/**
 * An item that is able to return an image of itself.
 * 
 * @author David J. Barnes and Michael Kolling. Modified ZHENG yujie, SU yu
 * @version 2017.03.25
 */

public interface DrawableItem extends Item
{
    public Image getImage();
}
