package theone.com.hzit.catchgame.model.data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.Random;

import theone.com.hzit.catchgame.model.inter.Constant;

public class Circle {
	private Point2D point;
	private Color color;
	private double radius;
	private double speed =0.5;
	private Direction direction = Direction.RIGHT;
	private State state = State.RUNNABLE;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Circle() {
		setPoint(randomPoint());
		this.color = randomColor();
		this.radius = 50;
	}

	private Color randomColor() {
		Random rand = new Random();
		return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()).brighter();
	}

	private Point2D randomPoint() {
		Random rand = new Random();
		return new Point2D.Double(rand.nextDouble() * 800, rand.nextDouble() * 500);
	}

	public Circle(Point2D point, Color color, double radius) {
		setPoint(point);
		this.color = color;
		this.setRadius(radius);
	}

	public Circle(double pointX, double pointY, double radius, Color color) {
		setPoint(new Point2D.Double(pointX, pointY));
		this.color = color;
		this.setRadius(radius);
	}

	public Relationship getRelationship(Circle other) {
		if (other.getState() == State.DIE)
			return Relationship.SEPARATION;
		double distance = this.getCenter().distance(other.getCenter());
		double radiusSum = this.getRadius() + other.getRadius();
		if (distance > radiusSum) {
			return Relationship.SEPARATION;
		} else if (distance == radiusSum) {
			return Relationship.TANGENCY;
		} else if (distance > Math.max(this.getRadius(), other.getRadius())) {
			return Relationship.CONFLICTING;
		} else if (this.getRadius() == Math.max(this.getRadius(), other.getRadius())) {
			return Relationship.CANEATING;
		} else {
			return Relationship.ATE;
		}
	}

	public Point2D getCenter() {
		return new Point2D.Double(point.getX() + this.radius, point.getY() + this.radius);
	}

	public double getArea() {
		return radius * radius * 3.14;
	}

	public synchronized Point2D getPoint() {
		return point;
	}

	public synchronized void setPoint(Point2D point) {
		double x = point.getX(), y = point.getY();
		if (x <= -2 * radius)
			x = Constant.WIDTH;
		if (x > Constant.WIDTH + 2 * radius)
			x = -2 * radius;

		if (y <= -2 * radius)
			y = Constant.HEIGHT;
		if (y > Constant.HEIGHT + 2 * radius)
			y = -2 * radius;

		this.point = new Point2D.Double(x, y);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getRadius() {
		return radius;
	}

	public synchronized void setRadius(double radius) {
		if (radius > 0)
			this.radius = radius;
		else {
			System.out.println("raduis小于零");
			this.radius = 5;
		}
	}

	public void move() {
		Point2D p = getPoint();
		double temp = speed;
		switch (direction) {
		case UP:

			setPoint(new Point2D.Double(p.getX(), p.getY() - temp));
			break;
		case DOWN:

			setPoint(new Point2D.Double(p.getX(), p.getY() + temp));
			break;
		case LEFT:

			setPoint(new Point2D.Double(p.getX() - temp, p.getY()));
			break;
		case RIGHT:

			setPoint(new Point2D.Double(p.getX() + temp, p.getY()));
			break;
		default:

			setPoint(new Point2D.Double(p.getX() + temp, p.getY()));
			break;
		}
	}

	public  void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		Stroke stroke = g2D.getStroke();
		g2D.setStroke(stroke);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setColor(getColor());
		int radius = (int) getRadius();
		//对象为State.DIE不画
		if (getState() != State.DIE)
			g2D.fillOval((int) getPoint().getX(), (int) getPoint().getY(), 2 * radius, 2 * radius);
		
	}

	public void dispose() {
		state = State.DIE;
		radius = 0;
		point = null;
		color = null;
		speed = 0;
		direction = null;
	}

	@Override
	public String toString() {
		return "Circle [point=" + point + "radius=" + radius + "]";
	}
}
