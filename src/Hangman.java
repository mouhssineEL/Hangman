

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Hangman extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JTextField placetofindword;
	private JPanel hangmanPanel;
    private int numOfWrongGuesses = 0;
    private static String randomword = null;
    final Font mainFont = new Font("Segoe print", Font.BOLD, 12);
    private BufferedImage hangmanImage = new BufferedImage(400, 550, BufferedImage.TYPE_INT_ARGB);
    private JTextField score;
    private String wordtofind;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Operation operation = new Operation();
			        /*final String wordslist[] = {"science","art", "film", "study", "nothing","computer"};
			        // write a list of words in file txt
			        for (String word : wordslist) {
			            operation.addWordToFile(word);
			        }*/
			        //choice a random word from file in java
			        randomword = Operation.choiceRandomWord();
			       
			       System.out.println(" word chosen randmly is :"+ randomword);
					Hangman frame = new Hangman();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Hangman() throws IOException {		
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        // width will store the width of the screen
        int width = (int)size.getWidth();
        // height will store the height of the screen
        int height = (int)size.getHeight();
       // System.out.println("width :"+width+"height :
		setSize(width,height);
		setTitle("HangManSTI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 800);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setBackground(Color.green);
		setContentPane(mainPane);
		drawHangmanStand(hangmanImage);
		mainPane.setLayout(null);
		
		
		// BufferedImage img = ImageIO.read(new File("images/background.jpg"));
		 //JLabel pic = new JLabel(new ImageIcon(img));
		 //mainPane.add(pic);
		 
		
		JLabel lblInfo = new JLabel("The secret word contains "+randomword.length()+" letters !");
		lblInfo.setBounds(698, 106, 443, 47);
		lblInfo.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		lblInfo.setBackground(Color.green);
		mainPane.add(lblInfo);
		
		wordtofind = "?".repeat(randomword.length());
		placetofindword = new JTextField(wordtofind);
		placetofindword.setBounds(700, 200, 300, 80);
        placetofindword.setEditable(false);
        placetofindword.setHorizontalAlignment(JTextField.CENTER); 
        placetofindword.setBackground(Color.GRAY);
        placetofindword.setFont(new Font("Segoe print", Font.BOLD, 18));
		mainPane.add(placetofindword);
		placetofindword.setColumns(20);
		
		
		hangmanPanel = new JPanel();
		hangmanPanel.setBounds(0, 0, 610, 500);
		mainPane.add(hangmanPanel);
		
		JButton btnNewWord = new JButton("Nouveau Jeu");
		btnNewWord.setBounds(631, 343, 150, 30);
		btnNewWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

			}
		});
		btnNewWord.setEnabled(false);
		mainPane.add(btnNewWord);
		
		
		JButton btnExit = new JButton("Quitter");
		btnExit.setBounds(899, 343, 150, 30);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                System.exit(0);
			}
			
		});
		btnExit.setBackground(Color.RED);
		mainPane.add(btnExit);
		
		JPanel keyBoardPane = new JPanel();
		keyBoardPane.setBounds(610, 424, 476, 339);
		
        ArrayList<JButton>  buttons = new ArrayList<JButton>();
        char letter ;
        for (int i = 0; i < 26; i++) {
            letter = (char) ((char) 65+i);
            JButton button = new  JButton(letter+"");
            button.addActionListener(null);
            buttons.add(button);
        }
        for (JButton jButton : buttons) {
        	keyBoardPane.add(jButton);
        }
        mainPane.add(keyBoardPane);
        
        score = new JTextField();
        score.setBounds(1007, 0, 69, 47);
        score.setColumns(10);
        score.setText(""+(7-numOfWrongGuesses)+"");
        score.setEditable(false);
        score.setHorizontalAlignment(JTextField.CENTER); 
        score.setBackground(Color.orange);
        score.setFont(mainFont);
        mainPane.add(score);
        
        JLabel lblNewLabel = new JLabel("Nombre de tentative restante");
        lblNewLabel.setBounds(926, 50, 150, 23);
        mainPane.add(lblNewLabel);
        
        JPanel noticePanel = new JPanel();
        noticePanel.setBounds(0, 499, 610, 264);
        //noticePanel.add(pic);
        mainPane.add(noticePanel);
        
        
        for (JButton jButton : buttons) {
            jButton.addActionListener(new ActionListener(){
                public void actionPerformed(final ActionEvent e) {
                	 randomword  = randomword.toUpperCase();
                	 if (randomword.indexOf(jButton.getText()) >= 0) {
                         guessRight(jButton);
                     } else {
                         guessWrong();
                        // placetofindword.setText();
                     }
                	 jButton.setEnabled(false);
             		btnNewWord.setEnabled(true);
                	 
                }
            });}
	}
	


    private void guessRight(JButton btn) {
      
    	System.out.println("letter selected :"+btn.getText());

    	 int index = randomword.indexOf(btn.getText());
         List<Integer> allReccurentes = new ArrayList<Integer>(); 
         System.out.println("verification de l'indice"+ index);
         while(index >= 0) {
         System.out.println(index);
         allReccurentes.add(index);
         index = randomword.indexOf(btn.getText(), index+1);
         
        // int index = randomword.indexOf(jButton.getText().toLowerCase());
         if(randomword.contains(btn.getText())){
             System.out.println("bonne selectionne , hope for the next ");
            for (Integer indexcu : allReccurentes) {
             wordtofind = wordtofind.substring(0, indexcu) + btn.getText()+ wordtofind.substring(indexcu + 1);
             placetofindword.setText(wordtofind);
            }}}
         System.out.println("all the reccurents of letter in word is: "+allReccurentes);
         placetofindword.setText(wordtofind);
         if(randomword.equalsIgnoreCase(placetofindword.getText())) {
        	 JOptionPane.showMessageDialog(this, "You Win!");
         }
         
    }

    private void guessWrong() {
        numOfWrongGuesses++;
        score.setText(""+(7-numOfWrongGuesses)+"");
        String guess = placetofindword.getText();
        System.out.println(" le contenu de txtfield is :"+guess);
        System.out.println("nbr of wrong guesses : "+ numOfWrongGuesses);
        Graphics2D g = (Graphics2D) hangmanImage.getGraphics();
      
        int x = 250, y = 200;
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.RED);

        switch (numOfWrongGuesses) {
            case 1: // Head
                g.drawOval(-20 + x, y, 40, 40);
                break;
            case 2: // Body
                g.drawLine(x, y + 40, x, y + 40 + 80);
                break;
            case 3: // R Arm
                g.drawLine(x, y + 40 + 20, x + 20, y + 40 + 60);
                break;
            case 4: // L Arm
                g.drawLine(x, y + 40 + 20, x - 20, y + 40 + 60);
                break;
            case 5: // R Leg
                g.drawLine(x, y + 40 + 80, x + 20, y + 40 + 80 + 40);
                break;
            case 6: // L Leg
                g.drawLine(x, y + 40 + 80, x - 20, y + 40 + 80 + 40);
                break;
            default:
                JOptionPane.showMessageDialog(this, "You Lose!");
                break;
        }
        g.dispose();
        showChangedHangman();
        revalidate();
    }

    private void showChangedHangman() {
        if (hangmanPanel != null)
            remove(hangmanPanel);

        hangmanPanel.add(new JLabel(new ImageIcon(hangmanImage)));
        mainPane.add(hangmanPanel);
        revalidate();

    }

    private static void drawHangmanStand(BufferedImage image) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setStroke(new BasicStroke(10));
        g.setColor(Color.DARK_GRAY);
        g.drawLine(10, 475, 250, 475);
        g.drawLine(100, 475, 100, 100);
        g.drawLine(100, 100, 250, 100);
        g.drawLine(250, 100, 250, 200);

        g.dispose();
    }
	
	
	
}
