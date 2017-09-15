package com.pikamachu.services.common.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class DateAdapter.
 */
public class DateAdapter extends XmlAdapter<String, Date> {

	/** The pattern. */
	private String pattern = "dd/MM/yyyy";

	/** {@inheritDoc} */
	@Override
	public String marshal(Date date) throws Exception {
		return new SimpleDateFormat(pattern).format(date);
	}

	/** {@inheritDoc} */
	@Override
	public Date unmarshal(String dateString) throws Exception {
		return new SimpleDateFormat(pattern).parse(dateString);
	}
}