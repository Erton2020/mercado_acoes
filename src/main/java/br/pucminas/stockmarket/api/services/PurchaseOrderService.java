package br.pucminas.stockmarket.api.services;

import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;
import br.pucminas.stockmarket.api.entities.PurchaseOrder;
import br.pucminas.stockmarket.api.entities.Stock;

public interface PurchaseOrderService 
{
	void sendMessagePurchaseStock(PurchaseOrderDTO purchaseOrderDTO, Stock stock);

	PurchaseOrder insert(PurchaseOrder purchaseOrder);
}
