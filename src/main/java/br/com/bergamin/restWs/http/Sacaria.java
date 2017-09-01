package br.com.bergamin.restWs.http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sacaria")
public class Sacaria {

	private double valor = 0;

	/**
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	private String mes;

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

}