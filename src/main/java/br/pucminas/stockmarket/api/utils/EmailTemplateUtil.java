package br.pucminas.stockmarket.api.utils;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateUtil 
{
	public String purchaseOrderSolicitationEmailBody()
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
			body.append("<HEAD>");
				body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			body.append("</HEAD>");
			body.append("<BODY>");
				body.append("<SPAN> TESTE </SPAN>");
			body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
		
	public String saleOrderSolicitationInvestorEmailBody(String investorName, 
			String stockDescription, String stockType, String companyName, Double quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
			body.append("<HEAD>");
				body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			body.append("</HEAD>");
			body.append("<BODY>");
				body.append("<SPAN> Prezado(a) ").append(investorName).append("</SPAN>");
				body.append("<BR>");
				body.append("<BR>");
				body.append("<SPAN> Informamos que recebemos a sua solicição de compra de ").append(quantityStockPurchase).append(" ações ")
				.append(stockDescription).append(" do tipo ").append(stockType).append(" da emprasa ").append(companyName)
				.append(", com valor unitário de compra previsto no valor de R$ ").append(currentStockValue).append(". ")
				.append("Por gentileza aguarde novo contato confirmando a transação.").append("</SPAN>");
			body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
	
	public String saleOrderSolicitationCompanyEmailBody(String investorName, String investorEmail, 
			String stockDescription, String stockType, String companyName, Double quantityStockPurchase, Double currentStockValue)
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
			body.append("<HEAD>");
				body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			body.append("</HEAD>");
			body.append("<BODY>");
				body.append("<SPAN> TESTE </SPAN>");
			body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
	
	public String saleOrderConfirmationEmailBody()
	{		
		StringBuilder body = new StringBuilder();
		body.append("<HTML>");
			body.append("<HEAD>");
				body.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			body.append("</HEAD>");
			body.append("<BODY>");
				body.append("<SPAN> TESTE </SPAN>");
			body.append("</BODY>");
		body.append("</HTML>");
		
		return body.toString();
	}
}
