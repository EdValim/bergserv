package br.com.bergamin.restWs.controller;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import br.com.bergamin.restWs.http.Cotacao;
import br.com.bergamin.restWs.http.Sacaria;

public class HtmlRequestService implements ServletContextListener {

	private static Cotacao cotacao = null;
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public void contextInitialized(ServletContextEvent sce) {
		Runnable periodicTask = new Runnable() {
			public void run() {

				ConsumeHtml();
			}
		};

		executor.scheduleAtFixedRate(periodicTask, 0, 2, TimeUnit.MINUTES);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		executor.shutdown();
	}

	public static Cotacao GetCotacao() {
		try {

			if (cotacao == null) {
				ConsumeHtml();
				return cotacao;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		return cotacao;

	}

	private static void ConsumeHtml() {

		Cotacao localCotacao = new Cotacao();

		if (cotacao == null)
			cotacao = new Cotacao();

		try {

			double cotacaoDolar = 0;

			Document doc = Jsoup.connect("http://www.agnocafe.com.br").get();
			Elements bolsa = doc.select("label#BolsasId");
			for (int j = 0; j < bolsa.size(); j++) {
				if (bolsa.get(j) != null && bolsa.get(j).text().equals("Bolsa de NY")) {
					Node nd = bolsa.get(j).parentNode().parentNode();
					for (Node cNd : nd.childNodes().get(2).childNodes()) {
						for (Node trs : cNd.childNodes()) {
							int count = 0;
							Sacaria sac = new Sacaria();
							for (int k = 1; k < trs.childNodes().size(); k++) {
								Node td = trs.childNodes().get(k);

								if (td.nodeName().equals("td")) {
									if (count < 2) {
										if (count == 0) {
											System.out.println(td.childNodes().get(0).toString());
											sac.setMes(td.childNodes().get(0).toString());
										}

										if (count == 1) {
											sac.setValor(
													new Double(td.childNodes().get(0).toString().replace(",", ".")));
											System.out.println(td.childNodes().get(0).toString());
											localCotacao.getSacariaList().add(sac);
										}
										count++;
									} else {
										break;
									}
								} // td
							} // tr
						}
					}

					for (Node cNd : nd.childNodes().get(8).childNodes()) {
						int count = 0;
						for (Node trs : cNd.childNodes()) {
							for (int k = 1; k < trs.childNodes().size(); k++) {
								Node td = trs.childNodes().get(k);
								if (td.nodeName().equals("td")) {
									if (count < 2) {
										if (count == 1) {
											cotacaoDolar = new Double(
													td.childNodes().get(0).toString().replace(",", "."));

											System.out.println(cotacaoDolar);
										}
										count++;
									}
								}
							} // td
						} // tr
					}
				}
			}

			localCotacao.setDolarCotacao(cotacaoDolar);

			if (localCotacao.getDolarCotacao() > 0 && localCotacao.getSacariaList().size() > 0) {
				cotacao = localCotacao;
			}

		} catch (

		IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
