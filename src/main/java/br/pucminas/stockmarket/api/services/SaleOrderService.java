package br.pucminas.stockmarket.api.services;

import br.pucminas.stockmarket.api.dto.SaleOrderDTO;
import br.pucminas.stockmarket.api.entities.Stock;

public interface SaleOrderService 
{
	void sendMessageSalesStock(SaleOrderDTO saleOrderDTO, Stock stock);
}
