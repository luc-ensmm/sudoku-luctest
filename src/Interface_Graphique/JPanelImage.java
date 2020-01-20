/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface_Graphique;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author yannE
 */
class JPanelImage extends JPanel {

        private BufferedImage image;
        private Graphics2D graphics;

        public JPanelImage(int width, int height) {
            super();
            setSize(width, height);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);//, BufferedImage.TYPE_INT_RGB);
            graphics = image.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setRenderingHints(rh);

        }
        
        public JPanelImage() {
            super();
            setSize(getWidth(), getHeight());
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);//, BufferedImage.TYPE_INT_RGB);
            graphics = image.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.setColor(Color.BLACK);

            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setRenderingHints(rh);

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

        public Graphics2D getGraphics2D() {
            return graphics;
        }
        
        public void setFontSize(int fontSize){
            Font font = graphics.getFont();
            graphics.setFont(new Font(font.getFontName(),font.getStyle(),fontSize));
        }
        
        public void setLineThickness(int thickness){
            graphics.setStroke(new BasicStroke(thickness));
        }
        
        public void resetLineThickness(){
            graphics.setStroke(new BasicStroke());
        }

    }
