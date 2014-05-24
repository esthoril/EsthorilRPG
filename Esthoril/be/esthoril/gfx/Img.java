package be.esthoril.gfx;

import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Img extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final Image		linkNormal[] = new Image[12];
	public static final Image		linkFighting[][] = new Image[2][4];
	public static final Image		weapon[] = new Image[10];
	public static final Image		NPCimg[][] = new Image[32][3];
	public static final Image		mobimg[][] = new Image[32][3];
	public static final Image		talk[] = new Image[4];
	public static final Image		mobDeath[] = new Image[3];
	public static final Image		item[] = new Image[3];
	public static final Image		actor[] = new Image[3];
	
	public static Image		pointer;
	
	public static final String[]	tilesets = {
		"sander1.png",
		"tilea4.png",
		"tilea5.png",
		"tilec.png",
		"tiled.png",
		"tilee2.png",
		"sander2.png"};
	
	private String path;
	private static Image[]			tilesetPic = new Image[tilesets.length];
	public static int[]				setHeight = new int[tilesets.length];
	public static int[]				setWidth = new int[tilesets.length];
	public static Image[][][]		tiles = new Image[tilesets.length][100][100];
	public static int				thisSet = 0;
	public static final int			tileSize = 32;

	public Img() {
	}
	
	public void init() {
	
		// NPC pics
		Image NPCPic = new ImageIcon("res/sander_chars.png").getImage();
		for(int m=0 ; m<NPCimg.length ; m++) {
			for(int x=0 ; x<NPCimg[0].length ; x++) {
				NPCimg[m][x] = createImage(new FilteredImageSource(NPCPic.getSource(),  new CropImageFilter(0+32*x,0+48*m,32,48)));
			}
		}
		
		//mob pics
		Image mobPic = new ImageIcon("res/sander_mobs.png").getImage();
		for(int m=0 ; m<mobimg.length ; m++) {
			for(int x=0 ; x<mobimg[0].length ; x++) {
				mobimg[m][x] = createImage(new FilteredImageSource(mobPic.getSource(),  new CropImageFilter(0+32*x,0+32*m,32,32)));
			}
		}

		// mob death pics
		Image actorPic = new ImageIcon("res/sander_actor.png").getImage();
		for(int i=0;i<actor.length;i++) {
			actor[i] = createImage(new FilteredImageSource(actorPic.getSource(),  new CropImageFilter(0+96*i, 0, 96, 96)));
		}

		// mob death pics
		Image mobDeathPic = new ImageIcon("res/mobDeath.png").getImage();
		for(int i=0;i<mobDeath.length;i++) {
			mobDeath[i] = createImage(new FilteredImageSource(mobDeathPic.getSource(),  new CropImageFilter(0+17*i, 0, 17, 17)));
		}

		// item pics
		Image itemPic = new ImageIcon("res/pickItems.png").getImage();
		for(int i=0;i<item.length;i++) {
			item[i] = createImage(new FilteredImageSource(itemPic.getSource(),  new CropImageFilter(0+32*i, 0, 32, 32)));
		}
		
		// cropping link fighting pics
		Image linkFightingPic	= new ImageIcon("res/linkFighting.png").getImage();
		for(int y=0;y<linkFighting.length;y++) {
			for(int x=0;x<linkFighting[0].length;x++) {
				linkFighting[y][x] = createImage(new FilteredImageSource(linkFightingPic.getSource(),  new CropImageFilter(0+27*x,0+27*y,27,27)));
			}
		}

		// cropping speechbubble pics
		Image speechPic	= new ImageIcon("res/speech.png").getImage();
		for(int i=0;i<talk.length;i++) {
			talk[i] = createImage(new FilteredImageSource(speechPic.getSource(),  new CropImageFilter(0+32*i,0,32,32)));
		}

		// cropping link normal pics
		Image linkNormalPic	= new ImageIcon("res/linkNormal.png").getImage();
		for(int i=0;i<linkNormal.length;i++) {
			linkNormal[i] = createImage(new FilteredImageSource(linkNormalPic.getSource(),  new CropImageFilter(0+16*i,0,16,16)));
		}		

		pointer = new ImageIcon("res/pointer.png").getImage();

		for(int i=0;i<tilesets.length;i++) {
			path = "res/"+tilesets[i];
			System.out.print(path+"   ");
			setTileset(path, i);
		}
	}
	
	private void setTileset(String path, int set) {

		tilesetPic[set] = new ImageIcon(path).getImage();

		setHeight[set] = tilesetPic[set].getHeight(null)/tileSize;
		setWidth[set] = tilesetPic[set].getWidth(null)/tileSize;
		System.out.println(setHeight[set]+" "+setWidth[set]);

		for(int y=0;y<setHeight[set];y++) {
			for(int x=0;x<setWidth[set];x++) {
				tiles[set][y][x] = createImage(new FilteredImageSource(tilesetPic[set].getSource(),
						new CropImageFilter(0+tileSize*x, 0+tileSize*y, tileSize, tileSize)));				
			}
		}
	}
}
