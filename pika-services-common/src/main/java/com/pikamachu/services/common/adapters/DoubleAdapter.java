package com.pikamachu.services.common.adapters;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class DoubleAdapter.
 */
public class DoubleAdapter extends XmlAdapter<String, Double> {

	/** The pattern. */
	private String pattern = "0.00";

	/** {@inheritDoc} */
	@Override
	public String marshal(Double d) throws Exception {
		return new DecimalFormat(pattern).format(d);
	}

	/** {@inheritDoc} */
	@Override
	public Double unmarshal(String d) throws Exception {
		return Double.parseDouble(d);
	}
}