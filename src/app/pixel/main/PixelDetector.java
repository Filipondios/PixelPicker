package app.pixel.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PixelDetector {
	
	public static void main(String[] args) throws AWTException {

		Robot robot = new Robot(); // Natural color picker from the desktop
		Point mousePos; // Position of the mouse
		Color pixelColor = Color.white, lastColor = null; // Actual color and last used color
		
		/* Frame that will indicate the HEX notation of the selected color from the desktop */
		JFrame frame = new JFrame();
	    JPanel panel = new JPanel();
	    JLabel infoLabel = new JLabel();	
	    
	    infoLabel.setText("  " + String.format("#%02x%02x%02x", 255, 255, 255) + "  ");
	    panel.setBorder(new LineBorder(Color.blue, 2));
	    panel.add(infoLabel); 		//
	    frame.add(panel); 			// General config for the
	    frame.setUndecorated(true); // Frame and the panel that contains
	    frame.setVisible(true);		// the color label.
	    frame.setSize(100,50);		//
	    frame.pack();				//
	    
	    frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	          System.exit(0); // Exit when dispose the frame
	        } 
	    });	    
	    
	    int r, g, b;
	    
		while (true) {
			mousePos = MouseInfo.getPointerInfo().getLocation(); // Get the current mouse position
			pixelColor = robot.getPixelColor(mousePos.x, mousePos.y); // Get the pixel color
			frame.setLocation(mousePos.x + 20, mousePos.y - 10); // Update the frame position

			// Different color from the last one -> means update the panel & the label
			if(lastColor==null || !lastColor.equals(pixelColor)) {
				r = pixelColor.getRed();
				g = pixelColor.getGreen();
				b = pixelColor.getBlue();
				
				panel.setBackground(pixelColor);
				infoLabel.setText(convertRGBtoHEX(r, g, b));
				infoLabel.setForeground(isLight(r, g, b)? Color.black : Color.white);
			}
			lastColor = new Color(pixelColor.getRGB());
		}
	}
	
	/**Checks if a color is light or dark.
	 * @param r Red component of a RGB color
	 * @param g Green component of a RGB color
	 * @param b Blue component of a RGB color
	 * @return True if the Color is light, false if it isn't.*/
	protected static boolean isLight(int r, int g, int b) {
		return (int)(0.299 * r + 0.587 * g + 0.114 * b) > 127;
	}
	
	/**Returns the hexadecimal code from a RGB color.
	 * @param r Red component of a RGB color
	 * @param g Green component of a RGB color
	 * @param b Blue component of a RGB color
	 * @return Hex code from a color. */
	protected static String convertRGBtoHEX(int r, int g, int b) {
		return String.format("#%02x%02x%02x", r, g, b);
	}
}