/*******************************************************************************
 * Copyright (c) 2009 Mukul Gandhi, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mukul Gandhi - bug 281822 - initial API and implementation 
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.StaticContext;
import org.eclipse.wst.xml.xpath2.processor.internal.*;
import org.eclipse.wst.xml.xpath2.processor.internal.types.*;

import java.util.*;

/**
  * A special constructor function for constructing a xs:dateTime value from a xs:date
  * value and a xs:time value.
  * ref: Section 5.2 of the F&O spec, http://www.w3.org/TR/xpath-functions/.
 */
public class FnDateTime extends Function {
	private static Collection _expected_args = null;

	/**
	 * Constructor for FnDateTime.
	 */
	public FnDateTime() {
		super(new QName("dateTime"), 2);
	}

	/**
	 * Evaluate arguments.
	 * 
	 * @param args
	 *            argument expressions.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of evaluation.
	 */
	@Override
	public ResultSequence evaluate(Collection args) throws DynamicError {
		return dateTime(args, static_context());
	}

	/**
	 * Evaluate the function using the arguments passed.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @param sc
	 *            Result of static context operation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of the fn:dateTime operation.
	 */
	public static ResultSequence dateTime(Collection args, StaticContext sc)
			throws DynamicError {

		Collection cargs = Function.convert_arguments(args, expected_args());

		ResultSequence rs = ResultSequenceFactory.create_new();

		// get args
		Iterator argiter = cargs.iterator();
		ResultSequence arg1 = (ResultSequence) argiter.next();
		ResultSequence arg2 = (ResultSequence) argiter.next();
		XSDate param1 = (XSDate)arg1.first();
		XSTime param2 = (XSTime)arg2.first();
		
		// if either of the parameter is an empty sequence, the result
		// is an empty sequence
		if (param1 == null || param2 == null) {
		  return rs;	
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(param1.year(), param1.month() - 1, param1.day());
		cal.set(Calendar.HOUR_OF_DAY, param2.hour());
		cal.set(Calendar.MINUTE, param2.minute());
		cal.set(Calendar.SECOND, (Double.valueOf(Math.floor(param2.second())).intValue()));
		cal.set(Calendar.MILLISECOND, 0);
		
		XSDayTimeDuration dateTimeZone = param1.tz();
		XSDayTimeDuration timeTimeZone = param2.tz();
		if ((dateTimeZone != null && timeTimeZone != null) &&
		     !dateTimeZone.string_value().equals(timeTimeZone.string_value())) {
		  // it's an error, if the arguments have different timezones
		  DynamicError.throw_type_error();
		} else if (dateTimeZone == null && timeTimeZone != null) {
           rs.add(new XSDateTime(cal, timeTimeZone));
		} else if (dateTimeZone != null && timeTimeZone == null) {
		   rs.add(new XSDateTime(cal, dateTimeZone));
		}
		else if ((dateTimeZone != null && timeTimeZone != null) &&
			     dateTimeZone.string_value().equals(timeTimeZone.string_value())) {
		   rs.add(new XSDateTime(cal, dateTimeZone));
		}
		else {
		   rs.add(new XSDateTime(cal, null));
		}

		return rs;
	}

	/**
	 * Obtain a list of expected arguments.
	 * 
	 * @return Result of operation.
	 */
	public static Collection expected_args() {
		if (_expected_args == null) {
			_expected_args = new ArrayList();
			_expected_args.add(new SeqType(new XSDate(), SeqType.OCC_QMARK));
			_expected_args.add(new SeqType(new XSTime(), SeqType.OCC_QMARK));
		}

		return _expected_args;
	}
}
