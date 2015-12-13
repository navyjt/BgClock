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
 * 一个精美的时钟 （自动换背景 秒针平滑移动）
 * 
 * 注意：背景图片需放在该包的image目录下（命名：1.jpg...5.jpg）
 */
public class BgClock extends JFrame {
	// 今天的日期对象
	private GregorianCalendar now = new GregorianCalendar();
	// 时钟标签，上面画的是圆形时钟
	private ClockLabel clockLabel = new ClockLabel();
	// 星期标签，指示星期
	private JLabel weekLabel = new JLabel();
	// 日期标签，指示日期
	private JLabel dateLabel = new JLabel();
	// 品牌标签
	private JLabel remarkLabel = new JLabel();
	// 时间标签，指示时间
	private JLabel timeLabel = new JLabel();
	//数字时钟
	private Digitclock digitclock = new Digitclock();
	//数字时钟初始时间
	
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
	
	
/*	//小工具，提供给数字时钟的时间转换为字符串显示用
    private String time2str(float t) {
        int h = (int)t/36000;
        int m = ((int)t-h*36000)/600;
        double s = (t%600)/10.00;
        return String.format("%02d : %02d : %04.1f", h,m,s);
    }*/
	
	public BgClock() {

		setTitle("时钟");
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
					
					String inputValue = JOptionPane.showInputDialog("请输入倒计时的秒数"); 
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
					//System.out.println(digiclockisStoped+" digitclock.setText开始计时");	
					//数字时钟部分
					/*		       DigitTimeRun t=new DigitTimeRun();
		        t.run(); */
					Run = true;
					digitclock.setForeground(new Color(0, 64, 128));
					
		        }

			}
		});
	}
	
	
	private void init() {

		// 初始化品牌标签
		remarkLabel.setText("L.M.Z");
		remarkLabel.setLocation(220, 80);
		remarkLabel.setSize(100, 30);
		remarkLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		remarkLabel.setForeground(Color.orange);

		// 初始化星期标签
		weekLabel.setSize(60, 20);
		weekLabel.setLocation(315, 190);
		weekLabel.setForeground(Color.BLUE);
		weekLabel.setFont(new Font("Arial Narrow", Font.BOLD, 12));
		// 为星期标签赋值
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

		// 初始化日期标签
		dateLabel.setSize(20, 20);
		dateLabel.setLocation(375, 190);
		dateLabel.setForeground(Color.BLACK);
		dateLabel.setFont(new Font("Fixedsys", Font.BOLD, 12));
		dateLabel.setText("" + now.get(Calendar.DATE));

		// 初始化时间标签
		timeLabel.setSize(500, 30);
		timeLabel.setLocation(100, 10);
		timeLabel.setForeground(new Color(0, 64, 128));
		timeLabel.setFont(new Font("Fixedsys", Font.PLAIN, 15));
		
		//初始化数字时钟标签
		digitclock.setSize(300,50);
		digitclock.setLocation(165,370);
		digitclock.setForeground(new Color(0, 64, 128));
		digitclock.setFont(new Font("DS-Digital", Font.PLAIN, 50));
		digitclock.setText("");
		
		
		//正着走按钮
		btnStart.setSize(80,30);
		btnStart.setLocation(50,450);
		btnStart.setText("正着走");

		//倒着走按钮
		btnReverse.setSize(80,30);
		btnReverse.setLocation(150,450);
		btnReverse.setText("倒着走");
		
		//暂停按钮
		btnStop.setSize(80,30);
		btnStop.setLocation(250,450);
		btnStop.setText("停止");
		
		//重置按钮
		btnReset.setSize(80,30);
		btnReset.setLocation(350,450);
		btnReset.setText("复位");

		// 将各组件加入到主窗口中
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

	// 自定义时钟标签，画一个圆形的时钟
	class ClockLabel extends JLabel implements Runnable {
		// 时钟标签的宽度和高度
		private final int WIDTH = 500;
		private final int HEIGHT = 440;

		// 圆形时钟的X半径和Y半径
		private final int CIRCLE_X_RADIUS = 155;
		private final int CIRCLE_Y_RADIUS = 155;

		// 圆形时钟的原点
		private final int CIRCLE_X = 250;
		private final int CIRCLE_Y = 200;

		// 圆形时钟指针的长度
		private final int HOUR_LENGTH = 70;
		private final int MIN_LENGTH = 100;
		private final int SEC_LENGTH = 135;

		// 当前时针所处的角度
		double arcHour = 0;
		// 当前分针所处的角度
		double arcMin = 0;
		// 当前秒针所处的角度
		double arcSec = 0;

		// 颜色的透明度，
		int alpha = 100;
		// 标识颜色透明度变化的方向，为true时表示越来越透明，为false时表示越来越不透明
		boolean flag = false;
		// 背景图片的id，轮换显示背景图片时使用
		int imageID = 0;
		// 背景图片对象数组
		Image img[] = new Image[5];
		// 背景图片URL，在该包的image目录下（命名：1.jpg...5.jpg）
		URL url[] = new URL[] { ClockLabel.class.getResource("image/1.jpg"),
				ClockLabel.class.getResource("image/2.jpg"),
				ClockLabel.class.getResource("image/3.jpg"),
				ClockLabel.class.getResource("image/4.jpg"),
				ClockLabel.class.getResource("image/5.jpg"),
			 
				};

		// 一个具有缓冲区的图像对象
		BufferedImage bufferedImage = null;
		int imageSize = 2 * Math.max(CIRCLE_X_RADIUS, CIRCLE_Y_RADIUS);
		// 为bufferedImage创建的Graphics对象，用于在bufferedImage上画图
		Graphics bufferedImageGraphics = null;
		// 时钟线程
		Thread clockThread = null;
		// 计数器
		int count = 0;


		public ClockLabel() {
			System.out.println("初始化模拟时钟");

			// 设置时钟标签的大小
			this.setSize(WIDTH, HEIGHT);

				// 获取时针、分针、秒针当前的角度
				arcHour = now.get(Calendar.HOUR) * (360.0 / 12)
						+ now.get(Calendar.MINUTE) * (360.0 / 12 / 60)
						+ now.get(Calendar.SECOND) * (360.0 / 12 / 60 / 60);
				arcMin = now.get(Calendar.MINUTE) * (360.0 / 60)
						+ now.get(Calendar.SECOND) * (360.0 / 60 / 60);
				arcSec = now.get(Calendar.SECOND) * (360.0 / 60);
			
			
			// 根据图片URL创建图片对象
			Toolkit tk = this.getToolkit();
			img[0] = tk.createImage(url[0]);
			img[1] = tk.createImage(url[1]);
			img[2] = tk.createImage(url[2]);
			img[3] = tk.createImage(url[3]);
			img[4] = tk.createImage(url[4]);
			try {
				// 使用MediaTracker加载图片对象
				// MediaTracker 类是一个跟踪多种媒体对象状态的实用工具类,
				// 媒体对象可以包括音频剪辑和图像，但目前仅支持图像.
				MediaTracker mt = new MediaTracker(this);
				mt.addImage(img[0], 0);
				mt.addImage(img[1], 0);
				mt.addImage(img[2], 0);
				mt.addImage(img[3], 0);
				mt.addImage(img[4], 0);
				// 加载媒体跟踪器中所有的图像。
				mt.waitForAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 创建一个有缓冲的Image对象
			bufferedImage = new BufferedImage(imageSize, imageSize,
					BufferedImage.TYPE_INT_ARGB);
			// 为BufferedImage创建Graphics2D对象，
			// 以后用该Graphics2D对象画的图都会画在BufferedImage中
			bufferedImageGraphics = bufferedImage.createGraphics();

			// 启动线程
			clockThread = new Thread(this);
			clockThread.start();
			
			
		}

		public void paint(Graphics g1) {
			// Graphics2D继承Graphics，比Graphics提供更丰富的方法
			Graphics2D g = (Graphics2D) g1;

			/** ***画圆形时钟的刻度，每6度便有一个刻度**** */
			for (int i = 0; i < 360; i = i + 6) {
				g.setColor(Color.gray);
				// 设置画笔的宽度为2
				g.setStroke(new BasicStroke(2));

				// 画刻度
				if (i % 90 == 0) {
					// 对于0，3，6，9点位置，使用一个大的刻度
					g.setColor(Color.pink);
					g.setStroke(new BasicStroke(7));// 画笔宽度为5
					// 当起点和终点一样时，画的就是点
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
					// 如果角度处于小时的位置，而且还不在0，3，6，9点时，画红色的小刻度
					g.setColor(Color.orange);
					g.setStroke(new BasicStroke(3));// 画笔宽度为3
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
					// 其他位置就画小刻度
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
			//如果时钟被复位，则三针归零
			if(clockisReseted)
			{
				arcHour = 0;
				arcMin = 0;
				arcSec = 0;
			}
			//System.out.println(String.valueOf(arcHour));
			/** ****** 画时钟的指针 ******** */
			// 画时针
			Line2D.Double lh = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcHour - 90) * Math.PI / 180) * HOUR_LENGTH,
					CIRCLE_Y + Math.sin((arcHour - 90) * Math.PI / 180)
							* HOUR_LENGTH);
			// 设置画笔宽度和颜色
			g.setStroke(new BasicStroke(8));
			g.setColor(Color.GRAY);
			// 利用Graphics2D的draw方法画线
			g.draw(lh);

			// 画分针
			Line2D.Double lm = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcMin - 90) * Math.PI / 180) * MIN_LENGTH,
					CIRCLE_Y + Math.sin((arcMin - 90) * Math.PI / 180)
							* MIN_LENGTH);
			g.setStroke(new BasicStroke(4));
			g.setColor(Color.orange);
			g.draw(lm);

			// 画秒针
			Line2D.Double ls = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcSec - 90) * Math.PI / 180) * SEC_LENGTH,
					CIRCLE_Y + Math.sin((arcSec - 90) * Math.PI / 180)
							* SEC_LENGTH);
			g.setStroke(new BasicStroke(1));

			g.setColor(Color.lightGray);
			g.draw(ls);

			/** **** 画时钟背景，并将其透明处理 ******* */
			// 所有使用bufferedImageGraphics画的图像，都画在bufferedImage上，
			// drawImage方法的参数含义分别是：背景图片对象、目标位置第一个角的X、Y坐标、目标位置第二个角的X、Y坐标、
			// 源位置第一个角的X、Y坐标、源位置第二个角的X、Y坐标、图像对象改变时的通知接受者
			bufferedImageGraphics.drawImage(img[imageID], 0, 0, imageSize,
					imageSize, 0, 0, imageSize + 10, imageSize + 10, this);
			// 将背景图片透明化
			for (int j = 0; j < imageSize; j++) {
				for (int i = 0; i < imageSize; i++) {
					// 获得背景图像中(i, j)坐标的颜色值
					int pix = bufferedImage.getRGB(i, j);
					Color c = new Color(pix);
					int R = c.getRed();
					int G = c.getGreen();
					int B = c.getBlue();
					// 通过Color对象的alpha，使颜色透明。
					int newpix = new Color(R, G, B, alpha).getRGB();
					// 重新设置背景图像该象素点的颜色
					bufferedImage.setRGB(i, j, newpix);
				}
			}

			/** 以上画背景操作都是画在bufferedImage上的，这里要将bufferedImage画在ClockLabel **/
			// 将当前用户剪贴区 Clip 与椭圆区域相交，并将 Clip 设置为所得的交集
			g.clip(new Ellipse2D.Double(95, 45, imageSize, imageSize));
			g.setColor(Color.white);
			// 在用户剪贴区画bufferedImage
			g.drawImage(bufferedImage, 95, 45, this);
		}

		public void run() {
			try {

				while (clockThread != null) {

					// 计数
					count++;
					// 获得完整的日期格式
					DateFormat df = DateFormat.getDateTimeInstance(
							DateFormat.FULL, DateFormat.FULL);
					// 格式化当前时间
					String s = df.format(new Date());
					timeLabel.setText(s);
					
					if(!clockisStoped)
					{
						if(!clockisReversed){
						// 每动一次对时钟指针的角度进行调整
						arcSec += 360.0 / 60 / 10; // 每秒转6度
						arcMin += 360.0 / 60 / 60 / 10; // 每60秒转6度
						arcHour += 360.0 / 12 / 60 / 60 / 10; // 每3600秒转30度
						
						// 当角度满一圈时，归0
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
							arcSec -= 360.0 / 60 / 10; // 每秒转6度
							arcMin -= 360.0 / 60 / 60 / 10; // 每60秒转6度
							arcHour -= 360.0 / 12 / 60 / 60 / 10; // 每3600秒转30度
							//System.out.println("当前时间的角度为"+String.valueOf(arcSec));
							// 当角度满一圈时，归0
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
					// 实现背景透明度渐变
					// 从浅入深，再由深入浅。
					if (count % 2 == 0) {// 用于背景替换减速
						if (flag) {
							alpha += 1;
							if (alpha == 100) {
								flag = !flag;
							}
						} else {
							alpha -= 1;
							if (alpha == 0) {
								flag = !flag;
								// 当透明度变化一个轮回时，换一张背景图
								imageID++;
								if (imageID == 4) {
									imageID = 0;
								}
							}
						}
						if (count >= 2147483647) { // 防溢出
							count = 0;
						}
					}
					// 重画时钟标签
					repaint();

					// 等待0.1秒钟
					Thread.sleep(100);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	class Digitclock extends JLabel implements Runnable{
		// 时钟线程
		Thread DigitThread = null;
	    

	     private String time2str(float t) {
	        int h = (int)t/36000;
	        int m = ((int)t-h*36000)/600;
	        double s = (t%600)/10.00;
	        //加入10ms级别的数字，因为系统显示消耗太多资源，改为随机显示
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