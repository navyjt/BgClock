package clock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * һ��������ʱ�� ���Զ������� ����ƽ���ƶ���
 * 
 * ע�⣺����ͼƬ����ڸð���imageĿ¼�£�������1.jpg...5.jpg��
 */
public class BgClock extends JFrame {
	// ��������ڶ���
	private GregorianCalendar now = new GregorianCalendar();
	// ʱ�ӱ�ǩ�����滭����Բ��ʱ��
	private ClockLabel clockLabel = new ClockLabel();
	// ���ڱ�ǩ��ָʾ����
	private JLabel weekLabel = new JLabel();
	// ���ڱ�ǩ��ָʾ����
	private JLabel dateLabel = new JLabel();
	// Ʒ�Ʊ�ǩ
	private JLabel remarkLabel = new JLabel();
	// ʱ���ǩ��ָʾʱ��
	private JLabel timeLabel = new JLabel();
	//����ʱ��
	private Digitclock digitclock = new Digitclock();
	//����ʱ�ӳ�ʼʱ��
	
	private Boolean analogiscontroled = false;
	
	
	private JButton btnStart = new JButton();
	private JButton btnStop = new JButton();
	private JButton btnReset = new JButton();
	private JButton btnReverse = new JButton();
	boolean clockisReseted = false;
	boolean clockisStoped = false;
	boolean clockisReversed = false;
	boolean digiclockisReseted = true;
	boolean digiclockisStoped = false;
	boolean digiclockisReversed = false;
	boolean digiclockisStarted = true;
	   
	boolean Run = true;
	 private float time=0;
	
	
/*	//С���ߣ��ṩ������ʱ�ӵ�ʱ��ת��Ϊ�ַ�����ʾ��
    private String time2str(float t) {
        int h = (int)t/36000;
        int m = ((int)t-h*36000)/600;
        double s = (t%600)/10.00;
        return String.format("%02d : %02d : %04.1f", h,m,s);
    }*/
	
	public BgClock() {

		setTitle("ʱ��");
		setSize(500, 530);
		setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() - 500) / 2, ((int) Toolkit.getDefaultToolkit()
				.getScreenSize().getHeight() - 480) / 2);
		init();
		setResizable(false);
		
		btnReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (analogiscontroled) {
					clockisReseted = true;
					clockisReversed = false;
				}
				else{
					digiclockisReseted = true;
					digiclockisReversed = false;
					btnReverse.setEnabled(true);;
					btnStart.setEnabled(true);
					btnReset.setEnabled(true);
					btnStop.setEnabled(false);
					
					digitclock.setText("00:00.00");
					digitclock.setForeground(new Color(0, 64, 128));
					Run= false;
					time = 0;
				}
								
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (analogiscontroled) {
				clockisStoped = true;
				clockisReversed = false;
				}
				else {
					Run = false;
					digiclockisStoped = true;
					digiclockisReversed = false;
					btnReverse.setEnabled(true);;
					btnStart.setEnabled(true);
					btnReset.setEnabled(true);
					btnStop.setEnabled(false);
					digitclock.setForeground(new Color(0, 64, 128));
					//digitclock.setText("00:00.0");
					//time = 0;
					
				}
			}
		});
		
		btnReverse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (analogiscontroled) {
				clockisReversed = true;
				clockisReseted = false;
				clockisStoped = false;
				}
				else {
					
					digitclock.setText("00:00.00");
					digitclock.setForeground(new Color(0, 64, 128));
					time = 0;
					
					String inputValue = JOptionPane.showInputDialog("�����뵹��ʱ������"); 
					if ((inputValue != null)&&(!inputValue.equals(""))) {
						Run=false;
						digiclockisReversed = true;
						digiclockisReseted = false;
						digiclockisStoped = false;
						btnReverse.setEnabled(false);;
						btnStart.setEnabled(false);
						btnReset.setEnabled(false);
						btnStop.setEnabled(true);
						time = (Float.parseFloat(inputValue))*10;
						Run= true;
						digitclock.setForeground(new Color(0, 64, 128));
						
					}
					else{
						
					}
				}
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (analogiscontroled) {
				clockisReseted = false;
				clockisStoped = false;
				}
 
		        else{
					digiclockisReseted = false;
					digiclockisStoped = false;
					digiclockisStarted = true;
					btnReverse.setEnabled(false);;
					btnStart.setEnabled(false);
					btnReset.setEnabled(false);
					btnStop.setEnabled(true);
					//digitclock.setText("00:00:00.0");
					//System.out.println(digiclockisStoped+" digitclock.setText��ʼ��ʱ");	
					//����ʱ�Ӳ���
					/*		       DigitTimeRun t=new DigitTimeRun();
		        t.run(); */
					Run = true;
					digitclock.setForeground(new Color(0, 64, 128));
					
		        }

			}
		});
	}
	
	
	private void init() {

		// ��ʼ��Ʒ�Ʊ�ǩ
		remarkLabel.setText("L.M.Z");
		remarkLabel.setLocation(220, 80);
		remarkLabel.setSize(100, 30);
		remarkLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		remarkLabel.setForeground(Color.orange);

		// ��ʼ�����ڱ�ǩ
		weekLabel.setSize(60, 20);
		weekLabel.setLocation(315, 190);
		weekLabel.setForeground(Color.BLUE);
		weekLabel.setFont(new Font("Arial Narrow", Font.BOLD, 12));
		// Ϊ���ڱ�ǩ��ֵ
		int week = now.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case 1:
			weekLabel.setText("SUNDAY");
			break;
		case 2:
			weekLabel.setText("MONDAY");
			break;
		case 3:
			weekLabel.setText("TUESDAY");
			break;
		case 4:
			weekLabel.setText("WEDNESDAY");
			break;
		case 5:
			weekLabel.setText("THURSDAY");
			break;
		case 6:
			weekLabel.setText("FRIDAY");
			break;
		case 7:
			weekLabel.setText("SATURDAY");
			break;
		}

		// ��ʼ�����ڱ�ǩ
		dateLabel.setSize(20, 20);
		dateLabel.setLocation(375, 190);
		dateLabel.setForeground(Color.BLACK);
		dateLabel.setFont(new Font("Fixedsys", Font.BOLD, 12));
		dateLabel.setText("" + now.get(Calendar.DATE));

		// ��ʼ��ʱ���ǩ
		timeLabel.setSize(500, 30);
		timeLabel.setLocation(100, 10);
		timeLabel.setForeground(new Color(0, 64, 128));
		timeLabel.setFont(new Font("Fixedsys", Font.PLAIN, 15));
		
		//��ʼ������ʱ�ӱ�ǩ
		digitclock.setSize(300,50);
		digitclock.setLocation(165,370);
		digitclock.setForeground(new Color(0, 64, 128));
		digitclock.setFont(new Font("DS-Digital", Font.PLAIN, 50));
		digitclock.setText("");
		
		
		//�����߰�ť
		btnStart.setSize(80,30);
		btnStart.setLocation(50,450);
		btnStart.setText("������");

		//�����߰�ť
		btnReverse.setSize(80,30);
		btnReverse.setLocation(150,450);
		btnReverse.setText("������");
		
		//��ͣ��ť
		btnStop.setSize(80,30);
		btnStop.setLocation(250,450);
		btnStop.setText("ֹͣ");
		
		//���ð�ť
		btnReset.setSize(80,30);
		btnReset.setLocation(350,450);
		btnReset.setText("��λ");

		// ����������뵽��������
		Container con = getContentPane();
		con.setBackground(Color.white);
		con.setLayout(null);
		con.add(weekLabel);
		con.add(dateLabel);
		con.add(remarkLabel);
		con.add(timeLabel);
		con.add(clockLabel);
		con.add(btnReset);
		con.add(btnStart);
		con.add(btnStop);
		con.add(btnReverse);
		con.add(digitclock);
	}

	public static void main(String[] args) {
		BgClock clock = new BgClock();
		clock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clock.setVisible(true);
		
		
		
	}

	// �Զ���ʱ�ӱ�ǩ����һ��Բ�ε�ʱ��
	class ClockLabel extends JLabel implements Runnable {
		// ʱ�ӱ�ǩ�Ŀ�Ⱥ͸߶�
		private final int WIDTH = 500;
		private final int HEIGHT = 440;

		// Բ��ʱ�ӵ�X�뾶��Y�뾶
		private final int CIRCLE_X_RADIUS = 155;
		private final int CIRCLE_Y_RADIUS = 155;

		// Բ��ʱ�ӵ�ԭ��
		private final int CIRCLE_X = 250;
		private final int CIRCLE_Y = 200;

		// Բ��ʱ��ָ��ĳ���
		private final int HOUR_LENGTH = 70;
		private final int MIN_LENGTH = 100;
		private final int SEC_LENGTH = 135;

		// ��ǰʱ�������ĽǶ�
		double arcHour = 0;
		// ��ǰ���������ĽǶ�
		double arcMin = 0;
		// ��ǰ���������ĽǶ�
		double arcSec = 0;

		// ��ɫ��͸���ȣ�
		int alpha = 100;
		// ��ʶ��ɫ͸���ȱ仯�ķ���Ϊtrueʱ��ʾԽ��Խ͸����Ϊfalseʱ��ʾԽ��Խ��͸��
		boolean flag = false;
		// ����ͼƬ��id���ֻ���ʾ����ͼƬʱʹ��
		int imageID = 0;
		// ����ͼƬ��������
		Image img[] = new Image[5];
		// ����ͼƬURL���ڸð���imageĿ¼�£�������1.jpg...5.jpg��
		URL url[] = new URL[] { ClockLabel.class.getResource("image/1.jpg"),
				ClockLabel.class.getResource("image/2.jpg"),
				ClockLabel.class.getResource("image/3.jpg"),
				ClockLabel.class.getResource("image/4.jpg"),
				ClockLabel.class.getResource("image/5.jpg"),
			 
				};

		// һ�����л�������ͼ�����
		BufferedImage bufferedImage = null;
		int imageSize = 2 * Math.max(CIRCLE_X_RADIUS, CIRCLE_Y_RADIUS);
		// ΪbufferedImage������Graphics����������bufferedImage�ϻ�ͼ
		Graphics bufferedImageGraphics = null;
		// ʱ���߳�
		Thread clockThread = null;
		// ������
		int count = 0;


		public ClockLabel() {
			System.out.println("��ʼ��ģ��ʱ��");

			// ����ʱ�ӱ�ǩ�Ĵ�С
			this.setSize(WIDTH, HEIGHT);

				// ��ȡʱ�롢���롢���뵱ǰ�ĽǶ�
				arcHour = now.get(Calendar.HOUR) * (360.0 / 12)
						+ now.get(Calendar.MINUTE) * (360.0 / 12 / 60)
						+ now.get(Calendar.SECOND) * (360.0 / 12 / 60 / 60);
				arcMin = now.get(Calendar.MINUTE) * (360.0 / 60)
						+ now.get(Calendar.SECOND) * (360.0 / 60 / 60);
				arcSec = now.get(Calendar.SECOND) * (360.0 / 60);
			
			
			// ����ͼƬURL����ͼƬ����
			Toolkit tk = this.getToolkit();
			img[0] = tk.createImage(url[0]);
			img[1] = tk.createImage(url[1]);
			img[2] = tk.createImage(url[2]);
			img[3] = tk.createImage(url[3]);
			img[4] = tk.createImage(url[4]);
			try {
				// ʹ��MediaTracker����ͼƬ����
				// MediaTracker ����һ�����ٶ���ý�����״̬��ʵ�ù�����,
				// ý�������԰�����Ƶ������ͼ�񣬵�Ŀǰ��֧��ͼ��.
				MediaTracker mt = new MediaTracker(this);
				mt.addImage(img[0], 0);
				mt.addImage(img[1], 0);
				mt.addImage(img[2], 0);
				mt.addImage(img[3], 0);
				mt.addImage(img[4], 0);
				// ����ý������������е�ͼ��
				mt.waitForAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ����һ���л����Image����
			bufferedImage = new BufferedImage(imageSize, imageSize,
					BufferedImage.TYPE_INT_ARGB);
			// ΪBufferedImage����Graphics2D����
			// �Ժ��ø�Graphics2D���󻭵�ͼ���ử��BufferedImage��
			bufferedImageGraphics = bufferedImage.createGraphics();

			// �����߳�
			clockThread = new Thread(this);
			clockThread.start();
			
			
		}

		public void paint(Graphics g1) {
			// Graphics2D�̳�Graphics����Graphics�ṩ���ḻ�ķ���
			Graphics2D g = (Graphics2D) g1;

			/** ***��Բ��ʱ�ӵĿ̶ȣ�ÿ6�ȱ���һ���̶�**** */
			for (int i = 0; i < 360; i = i + 6) {
				g.setColor(Color.gray);
				// ���û��ʵĿ��Ϊ2
				g.setStroke(new BasicStroke(2));

				// ���̶�
				if (i % 90 == 0) {
					// ����0��3��6��9��λ�ã�ʹ��һ����Ŀ̶�
					g.setColor(Color.pink);
					g.setStroke(new BasicStroke(7));// ���ʿ��Ϊ5
					// �������յ�һ��ʱ�����ľ��ǵ�
					g.drawLine(
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				} else if (i % 30 == 0) {
					// ����Ƕȴ���Сʱ��λ�ã����һ�����0��3��6��9��ʱ������ɫ��С�̶�
					g.setColor(Color.orange);
					g.setStroke(new BasicStroke(3));// ���ʿ��Ϊ3
					g.drawLine(
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				} else {
					// ����λ�þͻ�С�̶�
					g.setColor(Color.gray);
					g.drawLine(
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				}
			}
			//���ʱ�ӱ���λ�����������
			if(clockisReseted)
			{
				arcHour = 0;
				arcMin = 0;
				arcSec = 0;
			}
			//System.out.println(String.valueOf(arcHour));
			/** ****** ��ʱ�ӵ�ָ�� ******** */
			// ��ʱ��
			Line2D.Double lh = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcHour - 90) * Math.PI / 180) * HOUR_LENGTH,
					CIRCLE_Y + Math.sin((arcHour - 90) * Math.PI / 180)
							* HOUR_LENGTH);
			// ���û��ʿ�Ⱥ���ɫ
			g.setStroke(new BasicStroke(8));
			g.setColor(Color.GRAY);
			// ����Graphics2D��draw��������
			g.draw(lh);

			// ������
			Line2D.Double lm = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcMin - 90) * Math.PI / 180) * MIN_LENGTH,
					CIRCLE_Y + Math.sin((arcMin - 90) * Math.PI / 180)
							* MIN_LENGTH);
			g.setStroke(new BasicStroke(4));
			g.setColor(Color.orange);
			g.draw(lm);

			// ������
			Line2D.Double ls = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcSec - 90) * Math.PI / 180) * SEC_LENGTH,
					CIRCLE_Y + Math.sin((arcSec - 90) * Math.PI / 180)
							* SEC_LENGTH);
			g.setStroke(new BasicStroke(1));

			g.setColor(Color.lightGray);
			g.draw(ls);

			/** **** ��ʱ�ӱ�����������͸������ ******* */
			// ����ʹ��bufferedImageGraphics����ͼ�񣬶�����bufferedImage�ϣ�
			// drawImage�����Ĳ�������ֱ��ǣ�����ͼƬ����Ŀ��λ�õ�һ���ǵ�X��Y���ꡢĿ��λ�õڶ����ǵ�X��Y���ꡢ
			// Դλ�õ�һ���ǵ�X��Y���ꡢԴλ�õڶ����ǵ�X��Y���ꡢͼ�����ı�ʱ��֪ͨ������
			bufferedImageGraphics.drawImage(img[imageID], 0, 0, imageSize,
					imageSize, 0, 0, imageSize + 10, imageSize + 10, this);
			// ������ͼƬ͸����
			for (int j = 0; j < imageSize; j++) {
				for (int i = 0; i < imageSize; i++) {
					// ��ñ���ͼ����(i, j)�������ɫֵ
					int pix = bufferedImage.getRGB(i, j);
					Color c = new Color(pix);
					int R = c.getRed();
					int G = c.getGreen();
					int B = c.getBlue();
					// ͨ��Color�����alpha��ʹ��ɫ͸����
					int newpix = new Color(R, G, B, alpha).getRGB();
					// �������ñ���ͼ������ص����ɫ
					bufferedImage.setRGB(i, j, newpix);
				}
			}

			/** ���ϻ������������ǻ���bufferedImage�ϵģ�����Ҫ��bufferedImage����ClockLabel **/
			// ����ǰ�û������� Clip ����Բ�����ཻ������ Clip ����Ϊ���õĽ���
			g.clip(new Ellipse2D.Double(95, 45, imageSize, imageSize));
			g.setColor(Color.white);
			// ���û���������bufferedImage
			g.drawImage(bufferedImage, 95, 45, this);
		}

		public void run() {
			try {

				while (clockThread != null) {

					// ����
					count++;
					// ������������ڸ�ʽ
					DateFormat df = DateFormat.getDateTimeInstance(
							DateFormat.FULL, DateFormat.FULL);
					// ��ʽ����ǰʱ��
					String s = df.format(new Date());
					timeLabel.setText(s);
					
					if(!clockisStoped)
					{
						if(!clockisReversed){
						// ÿ��һ�ζ�ʱ��ָ��ĽǶȽ��е���
						arcSec += 360.0 / 60 / 10; // ÿ��ת6��
						arcMin += 360.0 / 60 / 60 / 10; // ÿ60��ת6��
						arcHour += 360.0 / 12 / 60 / 60 / 10; // ÿ3600��ת30��
						
						// ���Ƕ���һȦʱ����0
						if (arcSec >= 360) {
							arcSec = 0;
						}
						if (arcMin >= 360) {
							arcMin = 0;
						}
						if (arcHour >= 360) {
							arcHour = 0;
						}
						}
						else{
							arcSec -= 360.0 / 60 / 10; // ÿ��ת6��
							arcMin -= 360.0 / 60 / 60 / 10; // ÿ60��ת6��
							arcHour -= 360.0 / 12 / 60 / 60 / 10; // ÿ3600��ת30��
							//System.out.println("��ǰʱ��ĽǶ�Ϊ"+String.valueOf(arcSec));
							// ���Ƕ���һȦʱ����0
							if (arcSec <= -360) {
								arcSec = 0;
							}
							if (arcMin <= -360) {
								arcMin = 0;
							}
							if (arcHour <= -360) {
								arcHour = 0;
							}
							
						}
					}
					// ʵ�ֱ���͸���Ƚ���
					// ��ǳ�����������ǳ��
					if (count % 2 == 0) {// ���ڱ����滻����
						if (flag) {
							alpha += 1;
							if (alpha == 100) {
								flag = !flag;
							}
						} else {
							alpha -= 1;
							if (alpha == 0) {
								flag = !flag;
								// ��͸���ȱ仯һ���ֻ�ʱ����һ�ű���ͼ
								imageID++;
								if (imageID == 4) {
									imageID = 0;
								}
							}
						}
						if (count >= 2147483647) { // �����
							count = 0;
						}
					}
					// �ػ�ʱ�ӱ�ǩ
					repaint();

					// �ȴ�0.1����
					Thread.sleep(100);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	class Digitclock extends JLabel implements Runnable{
		// ʱ���߳�
		Thread DigitThread = null;
	    

	     private String time2str(float t) {
	        int h = (int)t/36000;
	        int m = ((int)t-h*36000)/600;
	        double s = (t%600)/10.00;
	        //����10ms��������֣���Ϊϵͳ��ʾ����̫����Դ����Ϊ�����ʾ
	        int l = (int)(Math.random()*10);
	        //return String.format("%02d:%05.2f", m,s);
	        if(t == 0)
	        {
	        	
	        	l = 0;
	        }
	        return String.format("%02d:%04.1f%1d", m,s,l);

	    }
	     
	    public Digitclock(){

	        DigitThread = new Thread(this);
	        DigitThread.start();
	    }
	     public void run() {

	        while(DigitThread.isAlive()&&(!DigitThread.isInterrupted())){
	    	

	            if (Run) {
	            	if (digiclockisReseted) {
		                digitclock.setText("00:00.00");
		                Run = false;
		               
					}
	            	else{
	            		
	            		try {
	            			Thread.sleep(100);
	            		} catch (InterruptedException e1) {
	            		}
	            		if (digiclockisReversed) {
							time -=1;
							
							if(time < 0){
								time = 0;
								digitclock.setForeground(new Color(255, 0, 0));
								Run = false;
								btnStop.setEnabled(false);
								btnStart.setEnabled(false);
								btnReverse.setEnabled(true);
								btnReset.setEnabled(true);
							}
						}
	            		else{
	            			time += 1;
	            			
	            		}
	            		
	            		digitclock.setText(time2str(time));

	            	}

	            }
	           
	         //   System.out.println("");
	        }
	        
	    }

	}
}