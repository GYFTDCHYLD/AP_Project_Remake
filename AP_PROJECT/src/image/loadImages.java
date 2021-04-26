package image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class loadImages {
	
	public static BufferedImage formBackground;
	public static BufferedImage CustomerDashboardBackground;
	public static BufferedImage RepresentativeDashboardBackground;
	public static BufferedImage TechnitianDashboardBackground;
	public static BufferedImage mainBackground;
	public static BufferedImage maleAvtar; 
	public static BufferedImage femaleAvtar;  
	
	
	
	
	public void init() {
		mainBackground = imageLoader("/MainBackground.png");
		formBackground = imageLoader("/formBackground.jpg");
		CustomerDashboardBackground = imageLoader("/CustomerDashboardBackground.jpg");
		RepresentativeDashboardBackground = imageLoader("/RepresentativeDashboardBackground.jpg");
		TechnitianDashboardBackground = imageLoader("/TechnitianDashboardBackground.jpg");
		maleAvtar = imageLoader("/male.png");
		femaleAvtar = imageLoader("/female.png");
		
		
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
