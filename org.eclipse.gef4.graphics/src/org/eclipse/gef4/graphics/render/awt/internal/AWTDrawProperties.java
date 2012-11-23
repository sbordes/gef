/*******************************************************************************
 * Copyright (c) 2012 itemis AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.gef4.graphics.render.awt.internal;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.eclipse.gef4.geometry.convert.awt.Geometry2AWT;
import org.eclipse.gef4.geometry.planar.Path;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.graphics.Color;
import org.eclipse.gef4.graphics.render.AbstractDrawProperties;
import org.eclipse.gef4.graphics.render.IDrawProperties;
import org.eclipse.gef4.graphics.render.IGraphics;
import org.eclipse.gef4.graphics.render.awt.AWTGraphics;

/**
 * The AWT {@link IDrawProperties} implementation.
 * 
 * @author mwienand
 * 
 */
public class AWTDrawProperties extends AbstractDrawProperties {

	/**
	 * The {@link Color draw color} associated with this
	 * {@link AbstractDrawProperties}.
	 */
	protected Color drawColor;

	/**
	 * Creates a new {@link AWTDrawProperties} with the {@link #drawColor} set
	 * to the default color specified by the {@link IDrawProperties} interface.
	 */
	public AWTDrawProperties() {
		drawColor = new Color();
	}

	@Override
	public void applyOn(IGraphics graphics, Path path) {
		prepare(graphics).draw(Geometry2AWT.toAWTPath(path));
	}

	@Override
	public void applyOn(IGraphics graphics, Point point) {
		int x = (int) point.x;
		int y = (int) point.y;
		prepare(graphics).fillRect(x, y, 1, 1);
	}

	@Override
	public void cleanUp(IGraphics g) {
		// set stroke etc.
	}

	private BasicStroke createAWTStroke() {
		float[] dashes = null;
		if (dashArray != null) {
			dashes = new float[dashArray.length];
			for (int i = 0; i < dashArray.length; i++) {
				dashes[i] = (float) dashArray[i];
			}
		}

		return new BasicStroke((float) lineWidth,
				lineCap == LineCap.FLAT ? BasicStroke.CAP_BUTT
						: lineCap == LineCap.ROUND ? BasicStroke.CAP_ROUND
								: BasicStroke.CAP_SQUARE,
				lineJoin == LineJoin.BEVEL ? BasicStroke.JOIN_BEVEL
						: lineJoin == LineJoin.MITER ? BasicStroke.JOIN_MITER
								: BasicStroke.JOIN_ROUND, (float) miterLimit,
				dashes, (float) dashBegin);
	}

	@Override
	public Color getColor() {
		return drawColor.getCopy();
	}

	@Override
	public AWTDrawProperties getCopy() {
		AWTDrawProperties copy = new AWTDrawProperties();
		copy.antialiasing = antialiasing;
		copy.setColor(drawColor);
		copy.setDashArray(dashArray);
		copy.setDashBegin(dashBegin);
		copy.setLineCap(lineCap);
		copy.setLineJoin(lineJoin);
		copy.setMiterLimit(miterLimit);
		copy.setLineWidth(lineWidth);
		return copy;
	}

	@Override
	public void init(IGraphics g) {
		// TODO: read stroke etc.
	}

	/**
	 * @param graphics
	 */
	private Graphics2D prepare(IGraphics graphics) {
		Graphics2D g = ((AWTGraphics) graphics).getGraphics2D();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				antialiasing ? RenderingHints.VALUE_ANTIALIAS_ON
						: RenderingHints.VALUE_ANTIALIAS_OFF);

		java.awt.Color awtColor = AWTGraphicsUtils.toAWTColor(drawColor);
		g.setColor(awtColor);

		g.setStroke(createAWTStroke());

		return g;
	}

	@Override
	public IDrawProperties setColor(Color drawColor) {
		this.drawColor.setTo(drawColor);
		return this;
	}

}
