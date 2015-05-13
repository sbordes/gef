/******************************************************************************
 * Copyright (c) 2011, 2015 Stephan Schwiebert and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stephan Schwiebert - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.gef4.cloudio.internal.ui;

import org.eclipse.gef4.cloudio.internal.ui.data.Type;
import org.eclipse.swt.graphics.Color;

/**
 * An example to demonstrate how to modify a label provider
 * @author sschwieb
 *
 */
public class CharacterLabelProvider extends TypeLabelProvider {

	@Override
	public Color getColor(Object element) {
		Type t = (Type) element;
		char firstChar = Character.toLowerCase(t.getString().charAt(0));
		if(firstChar < 'g') {
			return colorList.get(2);
		}
		if(firstChar < 'm') {
			return colorList.get(1);
		}
		if(firstChar < 's') {
			return colorList.get(0);
		}
		return colorList.get(3);
	}

}
