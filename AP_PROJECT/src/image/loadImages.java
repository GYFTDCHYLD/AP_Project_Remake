package image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class loadImages {
	
	public static BufferedImage formBackground;
	public static BufferedImage mainBackground;
	
	
	
	
	public void init() {
		mainBackground = imageLoader("/NikolaTesla.jpg");
		formBackground = imageLoader("/formBackground.jpg");
		
		
	}
	
	public static BufferedImage imageLoader(String path) {
		try {
			return ImageIO.read(loadImages.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static BufferedImage rotateCw(BufferedImage img, int radiant) {
	    AffineTransform transform = new AffineTransform();
	    transform.rotate(radiant, img.getWidth()/2, img.getHeight()/2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    img = op.filter(img, null);
	    return img;
	}
	
	
}
