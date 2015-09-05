import java.awt.Color;
import javax.swing.*;
import acm.graphics.*;
import acm.program.*;
import javax.swing.event.*;
import java.awt.event.*;

public class ColorSchemer extends GraphicsProgram {

	public static final int APPLICATION_WIDTH = 900;
	public static final int APPLICATION_HEIGHT = 800;


	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Maximum size of textField */
	private static final int max_size = 10;
	
	/** Maximum number of colors allowed */
	private static final int MAXCOLORS = 6;
	

	private static final int SQUARE_SPACE = 20;
	private static final int SQUARE_SIZE = 70;

	/**Offset necessary to center boxes */
	private static final int OFFSET = 10;

	//ivars
	private int numColors = 0;
	private JTextField fileName;
	private JSlider redSlider;
	private JSlider greenSlider;
	private JSlider blueSlider;
	private JComboBox numColorsBox;
	private JCheckBox shading;
	private boolean selectionStage = true;
	
	private int[][] colors = new int[MAXCOLORS][3];
	private int colorSelectionCounter = 0;


	
	public void init(){
		
//		Textbox
		add(new JLabel("File Name:"), EAST);
		fileName = new JTextField(max_size);
		fileName.addActionListener(this);
		add(fileName, EAST);
		
//		Number Box
		String[] colorNumbers = new String[MAXCOLORS + 1];
		for(int i = 0; i<MAXCOLORS + 1; i++){
			colorNumbers[i] = Integer.toString(i);
		}
		numColorsBox = new JComboBox(colorNumbers);
		add(new JLabel("Number of Colors:"), EAST);
		add(numColorsBox, EAST);
		
//		Slider stuff
		redSlider = new JSlider(0, 255, 0);
		greenSlider = new JSlider(0, 255, 0);
		blueSlider = new JSlider(0, 255, 0);
		
		add(new JLabel("Red:"), EAST);
		add(redSlider, EAST);
		
		add(new JLabel("Green:"), EAST);
		add(greenSlider, EAST);
		
		add(new JLabel("Blue:"), EAST);
		add(blueSlider, EAST);
		
		initSliderChanges();
		
//		Button stuff
		add(new JButton("Submit Color"), EAST);
		shading = new JCheckBox("Allow for Shades");
		add(shading, EAST);
		add(new JButton("Run Program"), EAST);
		add(new JButton("Clear"), EAST);

		
		addActionListeners();
		addMouseListeners();

	}
	
	/** Initializes Sliders to respond to changes */
	private void initSliderChanges(){
		redSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(selectionStage){
					if(numColors > 0){
						updateCurrentColor();
					} else{
						numColors = Integer.parseInt((String) numColorsBox.getSelectedItem());
					}
				}
			}
		});
		
		greenSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(selectionStage){
					if(numColors > 0){
						updateCurrentColor();
					} else{
						numColors = Integer.parseInt((String) numColorsBox.getSelectedItem());
					}
				}
			}
		});
		
		blueSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(selectionStage){
					if(numColors > 0){
						updateCurrentColor();
					} else{
						numColors = Integer.parseInt((String) numColorsBox.getSelectedItem());
					}
				}
			}
		});
	}
	

	
	/** Handles responses to buttons */
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand() == "Submit Color"){
			if(colorSelectionCounter < numColors - 1){
				colorSelectionCounter++;
				redSlider.setValue(0);
				greenSlider.setValue(0);
				blueSlider.setValue(0);
				updateCurrentColor();
			} else{
				selectionStage = false;
			}
			
		}
		
		if(e.getActionCommand() == "Run Program"){

			GImage image = new GImage(fileName.getText());

			int[][] pixelArray = image.getPixelArray();
			approximate(pixelArray);
			image = new GImage(pixelArray);
			if(image.getHeight()/(double)image.getWidth() > HEIGHT/(double)WIDTH){
				image.setSize((HEIGHT - 3*SQUARE_SPACE - SQUARE_SIZE)*image.getWidth()/image.getHeight(),
						HEIGHT - 3*SQUARE_SPACE - SQUARE_SIZE);
			} else{
				image.setSize(WIDTH - 2*SQUARE_SPACE - fileName.getWidth() - OFFSET,
						(WIDTH - 2*SQUARE_SPACE)*image.getHeight()/image.getWidth() );
				
			}
			
			add(image, WIDTH/2 - image.getWidth()/2 - fileName.getWidth()/2 - OFFSET,
					2*SQUARE_SPACE + SQUARE_SIZE);
			
		}
		
		if(e.getActionCommand() == "Clear"){
			colors = new int[MAXCOLORS][3];
			colorSelectionCounter = 0;
			selectionStage = true;
			numColors = 0;
			numColorsBox.setSelectedIndex(0);
			redSlider.setValue(0);
			greenSlider.setValue(0);
			blueSlider.setValue(0);
			removeAll();
		}
	}
	
	/** Allows representation boxes to change in real time with the sliders */
	private void updateCurrentColor(){
		updateColorsArray();
		Color c = new Color(colors[colorSelectionCounter][0], 
				colors[colorSelectionCounter][1], 
				colors[colorSelectionCounter][2]);
		GRect rect = new GRect(SQUARE_SIZE, SQUARE_SIZE);
		double xloc = OFFSET;
		if( numColors%2 == 1){
			xloc += WIDTH/2 - fileName.getWidth()/2
			- SQUARE_SIZE/2 + (colorSelectionCounter - numColors/2)*(SQUARE_SIZE + SQUARE_SPACE);

		} else{
			xloc += WIDTH/2 - fileName.getWidth()/2
			- SQUARE_SPACE/2 + (colorSelectionCounter - numColors/2)*(SQUARE_SIZE + SQUARE_SPACE);
		}
		rect.setFilled(true);
		rect.setFillColor(c);
		add(rect, xloc, SQUARE_SPACE);
		
	}
	
	/** Puts new values into the array keeping track of the colors */
	private void updateColorsArray(){
		colors[colorSelectionCounter][0] = redSlider.getValue();
		colors[colorSelectionCounter][1] = greenSlider.getValue();
		colors[colorSelectionCounter][2] = blueSlider.getValue();
	}
	

	/** 
	 * Treats each color as a vector <r, g, b> and finds the chosen color that is nearest 
	 * to that color (in terms of the length of the difference between the vectors).
	 * Replaces each color with corresponding chosen color.
	 * If shading is allowed, the luminosity of the pixel is used as an alpha value
	 * for the replacement. This is not precise, but it gives a general feel for the
	 * transparency.
	 */
	private void approximate(int[][] array){
		int height = array.length;
		int width = array[0].length;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int pixel = array[i][j];
				int r = GImage.getRed(pixel);
				int g = GImage.getGreen(pixel);
				int b = GImage.getBlue(pixel);
				int[] pixelVector = {r, g, b};
				int[] minVector = colors[0];
				for(int a = 1; a < numColors; a++ ){
					if(vectorDistance(pixelVector, minVector) > vectorDistance(pixelVector, colors[a])){
						minVector = colors[a];
					}
				}
		
				if(shading.isSelected()){
					array[i][j] = GImage.createRGBPixel(minVector[0], minVector[1], minVector[2], 
							255- getLuminosity(pixel));
				} else {
					array[i][j] = GImage.createRGBPixel(minVector[0], minVector[1], minVector[2]);
				}

			}
		}
		
	}
	
	/** Calculates the length of the difference between two vectors */
	private double vectorDistance(int[] v1, int[] v2){
		double total = 0;
		for(int i = 0; i < v1.length; i++){
			total += Math.pow(v1[i] - v2[i], 2);
		}
		return Math.sqrt(total);

	}
	
	/** Calculates perceived luminosity */
	private int getLuminosity(int pixel){
		int r = GImage.getRed(pixel);
		int g = GImage.getGreen(pixel);
		int b = GImage.getBlue(pixel);
		return GMath.round(0.299*r + 0.587*g + 0.114*b);
	}
	

}
