package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Display {
        private final String[] keys = {
                "AC", "(", ")", "Delete",
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "0", ".", "^", "/",
                "sin", "cos", "=", "<--"};
        private final JTextField result = new JTextField();
        private final JButton[] button = new JButton[keys.length];

        public Display() {
                /* initialize the text box */
                result.setText("0");
                result.setCaretColor(Color.white);
                /* text color */
                result.setForeground(Color.white);
                /* background color */
                result.setBackground(Color.black);
        }

        public void setResult(String str) {
                /* set number in the text box */
                result.setText(str);
        }

        public JTextField getResult() {
                /* get numbers from text box */
                return result;
        }

        public JButton[] getButton() {
                return button;
        }

        public void setButton(JButton button, int i) {
                this.button[i] = button;
        }

        public String getKey(int i) {
                return keys[i];
        }
}

class BuildUI extends JFrame implements ActionListener {
        Display dis = new Display();
        String expression;
        final Font resultFont = new Font("Helvetica", Font.PLAIN, 60);
        final Font buttonFont = new Font("Helvetica", Font.PLAIN, 30);
        ResultBuffer rb = new ResultBuffer();

        public void buildLayout() {
                JPanel calFrame = new JPanel();
                calFrame.setBackground(Color.black);

                /* seven rows, four cols */
                calFrame.setLayout(new GridLayout(7, 1));
                calFrame.add(dis.getResult());
                dis.getResult().setFont(resultFont);
                Container[] con = new Container[6];

                /* set each key */
                for (int i = 0; i < con.length; i++) {
                        con[i] = new Container();
                        con[i].setLayout(new GridLayout(1, 4));
                        calFrame.add(con[i]);
                }

                for (int i = 0; i < dis.getButton().length; i++) {
                        JButton newButton = setBtnForm(i);
//                        newButton.setForeground(Color.DARK_GRAY);

                        dis.setButton(newButton, i);
                        con[i / 4].add(newButton);
                }

                getContentPane().add(calFrame);
                expression = "";
        }

        public JButton setBtnForm(int i) {
                /* set font, color, shape of the buttom */
                JButton newButton = new JButton(dis.getKey(i));
                newButton.setBorderPainted(true);
                if (0 == i || 1 == i || 2 == i) {
                        newButton.setForeground(Color.black);
                        newButton.setBackground(Color.lightGray);
//                        Image icon = getDefaultToolkit().getImage("/Users/fengfeitong/IdeaProjects/Calculator/IMG_4018.jpg");
//                        newButton.setIconImage(icon);
                } else if (0 == (i + 1) % 4) {
                        newButton.setForeground(Color.black);
                        newButton.setBackground(Color.orange);
                } else {
                        newButton.setForeground(Color.black);
                        newButton.setBackground(Color.darkGray);
                }

                newButton.addActionListener(this);
                newButton.setFont(buttonFont);

                return newButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();

                if (dis.getKey(0).equals(action)) {
                        /* press AC */
                        pressAC();
                } else if (dis.getKey(3).equals(action)) {
                        /* press Delete */
                        pressDelete();
                } else if (dis.getKey(22).equals(action)) {
                        /* press = */
                        pressCalculate();
                } else if (dis.getKey(23).equals(action)) {
                        /* press <-- */
                        pressResultBuffer();
                } else {
                        /* press 1, 2, 3, +, - ...*/
                        pressExpression(action);
                }

        }

        public void pressAC() {
                /* clear the text box */
                expression = "";
                dis.setResult("0");
                rb.resetPointer();
        }

        public void pressDelete() {
                /* delete the last character in the text box */
                expression = expression.substring(0, expression.length() - 1);
                dis.setResult(expression);
                rb.resetPointer();
        }

        public void pressCalculate() {
                /* compute */
                Calculate cal = new Calculate();

                try {
                        String re = cal.evaluationExpression(expression + '@');
                        dis.setResult(re);
                        rb.addElement(expression + " = "  + re);

                        Log l = new Log();
                        l.writeFile(expression, re);
                } catch (Exception e) {
                        dis.setResult("INPUT ERROR!!!");
                }
                expression = "";
                rb.resetPointer();
        }

        public void pressExpression(String action) {
                /* input... */
                expression += action;
                dis.setResult(expression);
                rb.resetPointer();
        }

        public void pressResultBuffer() {
                dis.setResult(rb.getElement());
                rb.increasePointer();
        }
}
