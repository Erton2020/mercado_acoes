package br.pucminas.stockmarket.api.services;

import br.pucminas.stockmarket.api.dto.SaleOrderDTO;

public interface SaleOrderService 
{
	void sendMessageSalesStock(SaleOrderDTO saleOrderDTO);
}
