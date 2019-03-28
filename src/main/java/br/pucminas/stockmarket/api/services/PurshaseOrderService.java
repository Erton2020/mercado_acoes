package br.pucminas.stockmarket.api.services;

import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;

public interface PurshaseOrderService 
{
	void sendMessagePurchaseStock(PurchaseOrderDTO purchaseOrderDTO);
}
