import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main {

    public static long timeDifference = 0;
    public static boolean radioBtnSelected = false;
    public static int countdown;
    public static Timer tmr;
    public static JFrame firstFrame = new JFrame();
    public static JPanel firstFramePanel1 = new JPanel();
    public static JPanel firstFramePanel2 = new JPanel();
    public static JPanel firstFramePanel3 = new JPanel();
    public static JPanel firstFramePanel4 = new JPanel();
    public static JFrame secondFrame = new JFrame();
    public static JPanel secondFramePanel1 = new JPanel();
    public static JColorChooser jcc = new JColorChooser();
    public static SpinnerNumberModel snm = new SpinnerNumberModel(1, 1, 5, 1);
    public static JLabel speedLabel = new JLabel("Speed");
    public static JSpinner js = new JSpinner(snm);
    public static JTextField tf1 = new JTextField();
    public static JTextField tf2 = new JTextField();
    public static JLabel timeLabel = new JLabel();
    public static JRadioButton radioBtn1 = new JRadioButton("On time :");
    public static JRadioButton radioBtn2 = new JRadioButton("Countdown (sec) :");
    public static JButton startBtn = new JButton("Start Countdown");
    public static JButton stopBtn = new JButton("Stop");
    public static JButton colorChooseBtn = new JButton("Choose color");

    public static void main(String[] args) {

        int openWindow = JOptionPane.showOptionDialog(null, "Choose option", "Option Dialog",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Settings", "Close"}, null);

        if (openWindow == JOptionPane.NO_OPTION) {
            System.exit(0);
        }

        firstFramePanel1.setBounds(40, 25, 400, 55);

        ButtonGroup myChoice = new ButtonGroup();

        myChoice.add(radioBtn1);
        myChoice.add(radioBtn2);
        radioBtn1.setSelected(true);
        radioBtn1.setPreferredSize(new Dimension(150,20));
        radioBtn2.setPreferredSize(new Dimension(150,20));
        radioBtn1.setForeground(new Color(247, 158, 122));
        radioBtn2.setForeground(new Color(247, 158, 122));

        tf1.setPreferredSize(new Dimension(200,20));
        tf2.setPreferredSize(new Dimension(200,20));
        tf1.setForeground(new Color(247, 158, 122));
        tf2.setForeground(new Color(247, 158, 122));

        firstFramePanel2.setBounds(120, 120, 260, 40);

        colorChooseBtn.setPreferredSize(new Dimension(200,30));
        colorChooseBtn.setForeground(new Color(247, 158, 122));

        JDialog dialog = jcc.createDialog(null, "Choose background color", true, jcc, null, null);
        dialog.setVisible(false);
        colorChooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
            }
        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        timeLabel.setText(dtf.format(now));
        timeLabel.setForeground(new Color(0, 152, 153));

        firstFramePanel3.setBounds(210, 200, 80, 30);

        speedLabel.setForeground(new Color(247, 158, 122));

        Component c = js.getEditor().getComponent(0);
        c.setForeground(new Color(247, 158, 122));

        firstFramePanel4.setBounds(110, 270, 280, 40);

        startBtn.setPreferredSize(new Dimension(200,30));
        startBtn.setForeground(new Color(247, 158, 122));
        stopBtn.setPreferredSize(new Dimension(60,25));
        stopBtn.setForeground(new Color(247, 158, 122));

        secondFramePanel1.setBounds(0, 0, 400, 370);
        secondFramePanel1.setBackground(new Color(0, 152, 153));

        firstFrame.setTitle("Timer App by Darko Jelic");
        firstFrame.setSize(500, 370);
        firstFrame.setLocation(400, 200);
        firstFrame.setResizable(false);
        firstFrame.setLayout(null);
        firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        secondFrame.setTitle("Color");
        secondFrame.setSize(400, 370);
        secondFrame.setLocation(900, 200);
        secondFrame.setResizable(false);
        secondFrame.setLayout(null);
        secondFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        firstFramePanel1.add(radioBtn1);
        firstFramePanel1.add(tf1);
        firstFramePanel1.add(radioBtn2);
        firstFramePanel1.add(tf2);
        firstFramePanel2.add(colorChooseBtn);
        firstFramePanel2.add(timeLabel);
        firstFramePanel3.add(speedLabel);
        firstFramePanel3.add(js);
        firstFramePanel4.add(startBtn);
        firstFramePanel4.add(stopBtn);
        firstFrame.add(firstFramePanel1);
        firstFrame.add(firstFramePanel2);
        firstFrame.add(firstFramePanel3);
        firstFrame.add(firstFramePanel4);
        secondFrame.add(secondFramePanel1);
        jcc.setColor(new Color(0, 152, 153));
        LocalDateTime minuteLate = LocalDateTime.now().plusMinutes(1);
        tf1.setText(dtf.format(minuteLate));
        tf2.setText("0");

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                lockControls();

                if (radioBtn1.isSelected()) {
                    OnTime n = new OnTime();
                    Thread t = new Thread(n);
                    t.start();
                } else {
                    Countdown n = new Countdown();
                    Thread t = new Thread(n);
                    t.start();
                }
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioBtnSelected = false;
                secondFrame.dispose();
                tf2.setText("0");
                radioBtn1.setSelected(true);
                unlockControls();
            }
        });

        firstFrame.setVisible(true);
        secondFrame.setVisible(false);

        ImageIcon image = new ImageIcon("progrid_round.png");
        firstFrame.setIconImage(image.getImage());
        secondFrame.setIconImage(image.getImage());
        firstFrame.getContentPane().setBackground(new Color(247, 158, 122));

        SimpleDateFormat SimpleTime = new SimpleDateFormat("HH:mm:ss");

        Timer tmr = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText(SimpleTime.format(new Date()));
                timeLabel.setForeground(jcc.getColor());
            }
        });
        tmr.start();
    }

    public static void lockControls() {
        System.out.println("Zaključavanje kontrola");
        radioBtn1.setEnabled(false);
        radioBtn2.setEnabled(false);
        tf1.setEnabled(false);
        tf2.setEnabled(false);
        colorChooseBtn.setEnabled(false);
        jcc.setEnabled(false);
        js.setEnabled(false);
        speedLabel.setEnabled(false);
        startBtn.setEnabled(false);
    }

    public static void unlockControls() {
        System.out.println("Otključavanje kontrola");
        radioBtn1.setEnabled(true);
        radioBtn2.setEnabled(true);
        tf1.setEnabled(true);
        tf2.setEnabled(true);
        colorChooseBtn.setEnabled(true);
        jcc.setEnabled(true);
        js.setEnabled(true);
        speedLabel.setEnabled(true);
        startBtn.setEnabled(true);
    }

    static class OnTime implements Runnable {
        public void run() {

            String Time1 = tf1.getText();
            String Time2 = timeLabel.getText();
            try {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); // 24 satni format
                Date d1 = format.parse(Time1);
                Date d2 = format.parse(Time2);
                timeDifference = (d1.getTime() - d2.getTime()) / 1000;
                if (timeDifference < 0) {
                    System.out.println("Unijeto vrijeme je manje od trenutnog!");
                    lockControls();
                    return;
                }
                Thread.sleep(timeDifference * 1000);

                secondFrame.setVisible(true);
                int value = (Integer) js.getValue();

                radioBtnSelected = true;
                while (radioBtnSelected) {
                    try {
                        secondFramePanel1.setBackground(jcc.getColor());
                        Thread.sleep(value * 1000);
                        secondFramePanel1.setBackground(Color.WHITE);
                        Thread.sleep(value * 1000);
                    } catch (Exception ex) {
                        //System.out.println(ex.toString());
                    }
                }
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }
    }

    static class Countdown implements Runnable {
        public void run() {
            try {
                tmr = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        try {
                            countdown = Integer.parseInt(tf2.getText());
                        } catch (Exception ex) {
                            //System.out.println(ex.toString());
                        }
                        countdown--;
                        tf2.setText("" + countdown);
                        if (countdown == 0) {
                            tmr.stop();
                        }
                    }
                });
                tmr.start();

                Thread.sleep(Integer.parseInt(tf2.getText()) * 1000);

                secondFrame.setVisible(true);
                int value = (Integer) js.getValue();

                radioBtnSelected = true;
                while (radioBtnSelected) {
                    try {
                        secondFramePanel1.setBackground(jcc.getColor());
                        Thread.sleep(value * 1000);
                        secondFramePanel1.setBackground(Color.WHITE);
                        Thread.sleep(value * 1000);
                    } catch (Exception ex) {
                        //System.out.println(ex.toString());
                    }
                }
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }
    }
}
