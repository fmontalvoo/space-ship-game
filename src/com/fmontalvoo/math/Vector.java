package com.fmontalvoo.math;

import java.util.Objects;

public class Vector {
	public double x;
	public double y;

	public Vector() {
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public Vector add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public static Vector add(Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}

	public Vector sub(Vector vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	public Vector sub(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public static Vector sub(Vector v1, Vector v2) {
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}

	public Vector mult(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}

	public static Vector mult(Vector v, double scalar) {
		return new Vector(v.x * scalar, v.y * scalar);
	}

	public Vector div(double scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}

	public static Vector div(Vector v, double scalar) {
		return new Vector(v.x / scalar, v.y / scalar);
	}

	public double magSq() {
		return (this.x * this.x) + (this.y * this.y);
	}

	/**
	 * Calcula la magnitud del vector.
	 * 
	 * @return magnitud
	 */
	public double mag() {
		return Math.sqrt(magSq());
	}

	public Vector setMag(double len) {
		normalize();
		mult(len);
		return this;
	}

	public Vector limit(double max) {
		if (magSq() > (max * max)) {
			setMag(max);
		}

		return this;
	}

	public Vector normalize() {
		double magnitude = mag();

		if (magnitude != 0 && magnitude != 1) {
			div(magnitude);
		}

		return this;
	}

	public double dist(Vector vector) {
		double dx = this.x - vector.x;
		double dy = this.y - vector.y;
		return Math.sqrt((dx * dx) + (dy * dy));
	}

	public double dot(Vector vector) {
		return (this.x * vector.x) + (this.y * vector.y);
	}

	/**
	 * Retorna el vector modificando su direccion en funcion del angulo que recibe
	 * como parametro.
	 * 
	 * @param angle
	 * @return
	 */
	public Vector dir(double angle) {
		double magnitude = mag();

		this.x = Math.cos(angle) * magnitude;
		this.y = Math.sin(angle) * magnitude;
		return this;
	}

	public double angle() {
		double magnitude = mag();
		
		if (magnitude != 0) {
			return Math.asin(y / magnitude);
		}
		
		return 0;
	}

	public Vector copy() {
		return new Vector(this.x, this.y);
	}

	@Override
	public String toString() {
		return "[ " + this.x + ", " + this.y + " ]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
				&& Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
	}

}
