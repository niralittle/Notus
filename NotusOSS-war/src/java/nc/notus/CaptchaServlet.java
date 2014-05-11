package nc.notus;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Generate png image with captcha.
 * 
 * @author Panchenko Dmytro
 * @version 1.0
 *
 */
public class CaptchaServlet extends HttpServlet {

	private static final String ABC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();
	
	private final static int WIDTH = 140;
	private final static int HEIGHT = 50;
	private final static int CAPTCHA_LENGHT = 6;
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		char[] test = generateCaptcha(CAPTCHA_LENGHT);
		String captcha = String.copyValueOf(test);
		request.getSession().setAttribute("captcha", captcha);

		BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bufferedImage.createGraphics();

		Font font = new Font("Georgia", Font.BOLD, 18);
		g2d.setFont(font);

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		GradientPaint gp = new GradientPaint(0, 0, Color.white, 0, HEIGHT / 2,
				Color.blue, true);

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);

		g2d.setColor(Color.white);

		int x = 0;
		int y = 0;
		Random r = new Random();
		
		for (int i = 0; i < test.length; i++) {
			x += 10 + (Math.abs(r.nextInt()) % 15);
			y = 20 + Math.abs(r.nextInt()) % 20;
			g2d.drawChars(test, i, 1, x, y);
		}

		g2d.dispose();

		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ImageIO.write(bufferedImage, "png", os);
		os.close();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private char[] generateCaptcha(int lenght) {
		char[] rndCaptcha = new char[lenght];
		for (int i = 0; i < lenght; i++) {
			rndCaptcha[i] = ABC.charAt(rnd.nextInt(ABC.length()));
		}
		return rndCaptcha;
	}
}
