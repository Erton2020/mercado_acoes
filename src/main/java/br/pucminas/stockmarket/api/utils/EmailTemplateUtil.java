package br.pucminas.stockmarket.api.utils;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateUtil 
{
	public String purchaseOrderSolicitationInvestorEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Long quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
		body.append("<HEAD>");
			body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		body.append("</HEAD>");
		body.append("<BODY>");
			body.append("<SPAN> Prezado(a) ").append(investorName).append(",</SPAN>");
			body.append("<BR>");
			body.append("<BR>");
			body.append("<SPAN> Informamos que recebemos a sua solicição de compra de ").append(quantityStockPurchase).append(" ações ")
			.append(stockDescription).append(" do tipo ").append(stockType).append(" da empresa ").append(companyName)
			.append(", com valor unitário de compra previsto no valor de R$ ").append(currentStockValue).append(". ")
			.append("Por gentileza aguarde novo contato confirmando a sua transação de compra.").append("</SPAN>");
		body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
	
	public String purchaseOrderConfirmationInvestorEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Long quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
		body.append("<HEAD>");
			body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		body.append("</HEAD>");
		body.append("<BODY>");
			body.append("<SPAN> Prezado(a) ").append(investorName).append(",</SPAN>");
			body.append("<BR>");
			body.append("<BR>");
			body.append("<SPAN> Informamos com grande satisfação que a sua solicição de compra de ").append(quantityStockPurchase).append(" ações ")
			.append(stockDescription).append(" do tipo ").append(stockType).append(" da empresa ").append(companyName)
			.append("foi realizada com sucesso! Cada ação foi comprada com valor unitário de R$ ").append(currentStockValue).append(". ").append("</SPAN>");
		body.append("</BODY>");
		body.append("</HTML>");

		
		return body.toString();
	}
	
	public String purchaseOrderSolicitationCompanyEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Long quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
		body.append("<HEAD>");
			body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		body.append("</HEAD>");
		body.append("<BODY>");
			body.append("<SPAN>").append(companyName).append(",</SPAN>");
			body.append("<BR>");
			body.append("<BR>");
			body.append("<SPAN> Informamos que recebemos uma solicição de compra de ").append(quantityStockPurchase).append(" ações ")
			.append(stockDescription).append(" do tipo ").append(stockType).append(" pelo(a) Sr.(a) ").append(investorName)
			.append(", com valor unitário de compra previsto no valor de R$ ").append(currentStockValue).append(". ")
			.append("Por gentileza aguarde novo informativo com a confirmação da transação de compra.").append("</SPAN>");
		body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
	
	public String purchaseOrderConfirmationCompanyEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Long quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
		body.append("<HEAD>");
			body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		body.append("</HEAD>");
		body.append("<BODY>");
			body.append("<SPAN>").append(companyName).append(",</SPAN>");
			body.append("<BR>");
			body.append("<BR>");
			body.append("<SPAN> Informamos que a solicição de compra de ").append(quantityStockPurchase).append(" ações ")
			.append(stockDescription).append(" do tipo ").append(stockType).append(" pelo(a) Sr.(a) ").append(investorName)
			.append("foi concluida com Sucesso! Cada ação foi vendida com valor unitário de R$ ").append(currentStockValue).append(". ").append("</SPAN>");
		body.append("</BODY>");
		body.append("</HTML>");
		
		
		return body.toString();
	}
	
	public String saleOrderSolicitationInvestorEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Long quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
		body.append("<HEAD>");
			body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		body.append("</HEAD>");
		body.append("<BODY>");
			body.append("<SPAN> Prezado(a) ").append(investorName).append(",</SPAN>");
			body.append("<BR>");
			body.append("<BR>");
			body.append("<SPAN> Informamos que recebemos a sua solicição de venda de ").append(quantityStockPurchase).append(" ações ")
			.append(stockDescription).append(" do tipo ").append(stockType).append(" da empresa ").append(companyName)
			.append(", com valor unitário de venda previsto no valor de R$ ").append(currentStockValue).append(". ")
			.append("Por gentileza aguarde novo contato confirmando a sua transação de venda.").append("</SPAN>");
		body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
	
	public String saleOrderConfirmationInvestorEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Long quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
		body.append("<HEAD>");
			body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		body.append("</HEAD>");
		body.append("<BODY>");
			body.append("<SPAN> Prezado(a) ").append(investorName).append(",</SPAN>");
			body.append("<BR>");
			body.append("<BR>");
			body.append("<SPAN> Informamos que a sua solicição de venda de ").append(quantityStockPurchase).append(" ações ")
			.append(stockDescription).append(" do tipo ").append(stockType).append(" da empresa ").append(companyName)
			.append(" foi concluida com sucesso! Cada ação foi vendida com valor unitário de R$ ").append(currentStockValue).append(". ").append("</SPAN>");
		body.append("</BODY>");
		body.append("</HTML>");

		
		return body.toString();
	}
	
	public String saleOrderSolicitationCompanyEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Long quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
		body.append("<HEAD>");
			body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		body.append("</HEAD>");
		body.append("<BODY>");
			body.append("<SPAN>").append(companyName).append(",</SPAN>");
			body.append("<BR>");
			body.append("<BR>");
			body.append("<SPAN> Informamos que recebemos uma solicição de venda de ").append(quantityStockPurchase).append(" ações ")
			.append(stockDescription).append(" do tipo ").append(stockType).append(" pelo(a) Sr.(a) ").append(investorName)
			.append(", com valor unitário de recompra previsto no valor de R$ ").append(currentStockValue).append(". ")
			.append("Por gentileza aguarde novo informativo com a confirmação da transação de recompra das ações.").append("</SPAN>");
		body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
	
	public String saleOrderConfirmationCompanyEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Double quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
		body.append("<HEAD>");
			body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		body.append("</HEAD>");
		body.append("<BODY>");
			body.append("<SPAN>").append(companyName).append(",</SPAN>");
			body.append("<BR>");
			body.append("<BR>");
			body.append("<SPAN> Informamos que a solicição de venda de ").append(quantityStockPurchase).append(" ações ")
			.append(stockDescription).append(" do tipo ").append(stockType).append(" pelo(a) Sr.(a) ").append(investorName)
			.append("foi concluida! As ações foram recompradas com valor unitário de R$ ").append(currentStockValue).append(". ").append("</SPAN>");
		body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
}
