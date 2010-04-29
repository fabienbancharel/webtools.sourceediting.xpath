/*******************************************************************************
 * Copyright (c) 2005, 2009 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.ast;

import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.internal.types.*;

/**
 * Support for Schema Attribute test.
 */
public class SchemaAttrTest extends KindTest {
	private QName _arg;

	/**
	 * Constructor for SchemaAttrTest.
	 * 
	 * @param arg
	 *            QName argument.
	 */
	public SchemaAttrTest(QName arg) {
		_arg = arg;
	}

	/**
	 * Support for Visitor interface.
	 * 
	 * @return Result of Visitor operation.
	 */
	@Override
	public Object accept(XPathVisitor v) {
		return v.visit(this);
	}

	/**
	 * Support for QName interface.
	 * 
	 * @return Result of QName operation.
	 */
	public QName arg() {
		return _arg;
	}

	@Override
	public AnyType createTestType(ResultSequence rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QName name() {
		return _arg;
	}

	@Override
	public boolean isWild() {
		return false;
	}

	@Override
	public Class getXDMClassType() {
		// TODO Auto-generated method stub
		return null;
	}
}