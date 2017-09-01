package br.com.bergamin.restWs.http;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cotacao")
public class Cotacao {

	private List<Sacaria> sacariaList;
	private double dolarCotacao;

	/**
	 * @return the dolarCotacao
	 */
	@XmlElement
	public double getDolarCotacao() {
		return dolarCotacao;
	}

	/**
	 * @param dolarCotacao
	 *            the dolarCotacao to set
	 */
	public void setDolarCotacao(double dolarCotacao) {
		this.dolarCotacao = dolarCotacao;
	}

	/**
	 * @return the sacariaList
	 */
	@XmlElementWrapper(name = "sarariaList")
	@XmlElement(name = "sacaria")
	public List<Sacaria> getSacariaList() {
		if (sacariaList == null)
			sacariaList = new ArrayList<Sacaria>();

		return sacariaList;
	}

	/**
	 * @param sacariaList
	 *            the sacariaList to set
	 */
	public void setSacariaList(List<Sacaria> sacariaList) {
		this.sacariaList = sacariaList;
	}

}
