package org.gfg.dartearth;

import javax.swing.*;
public class TT extends JFrame {
    ImageIcon test = new ImageIcon(new String("F:\\zxb\\wordwind\\DartEarth\\src\\org\\gfg\\dartearth\\2.png"));//第一句
    public TT() {
        super("演示自定义标题栏的图标的方法");
        this.setIconImage(test.getImage()); //第二句
        this.setSize(400,200);
        this.show();
}
    public static void main(String[] args) {
    	TT jIcon = new TT();       
    }
}