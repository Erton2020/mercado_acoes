package br.pucminas.stockmarket.api.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.stockmarket.api.dto.HistoricalStockPriceDTO;
import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;
import br.pucminas.stockmarket.api.dto.SaleOrderDTO;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.mappers.HistoricalStockPriceMapper;
import br.pucminas.stockmarket.api.services.HistoricalStockPriceService;
import br.pucminas.stockmarket.api.services.PurchaseOrderService;
import br.pucminas.stockmarket.api.services.SaleOrderService;
import br.pucminas.stockmarket.api.services.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")
@Api(value = "stocks", tags={ "stocks"})
@SwaggerDefinition(tags = {
	    @Tag(name = "Stocks", description = "Recurso para gerencimento de Ações.")
	})
public class StockController {
	
	StockService stockService;
	HistoricalStockPriceService historicalStockPriceService;
	PurchaseOrderService purchaseOrderService;
	SaleOrderService saleOrderService;
	HistoricalStockPriceMapper historicalStockPriceMapper;

	
	public StockController(StockService p_stockService, HistoricalStockPriceService p_historicalStockPriceService,
			PurchaseOrderService p_purchaseOrderService, SaleOrderService p_saleOrderService,
			HistoricalStockPriceMapper p_historicalStockPriceMapper) {
		
		this.stockService = p_stockService;
		this.historicalStockPriceService = p_historicalStockPriceService;
		this.purchaseOrderService = p_purchaseOrderService;
		this.saleOrderService = p_saleOrderService;
		this.historicalStockPriceMapper = p_historicalStockPriceMapper;
	}

	@GetMapping(value = "/stocks/{stockId}/currentValues", produces = "application/json")
	public ResponseEntity<HistoricalStockPriceDTO> findCurrentStockValueByStockId(@PathVariable("stockId") Long stockId)
	{

		Optional<Stock> stockOptional = stockService.findStockById(stockId);
		if(stockOptional.isPresent())
		{
			Double currentValue = stockService.calculateStockCurrentValue(stockOptional.get().getHistoricalStockPrices(), CalculationTypeEnum.CURRENT_VALUE);
			
			HistoricalStockPrice historicalStockPrice = new HistoricalStockPrice();
			historicalStockPrice.setStock(stockOptional.get());
			historicalStockPrice.setReferenceDate(Calendar.getInstance());
			historicalStockPrice.setPrice(currentValue);
			
			historicalStockPrice = historicalStockPriceService.insert(historicalStockPrice);
			HistoricalStockPriceDTO historicalStockPriceDTO = historicalStockPriceMapper.hitoricalStockPriceTOHistoricalStockPriceDTO(historicalStockPrice);
			
			return new ResponseEntity<HistoricalStockPriceDTO>(historicalStockPriceDTO, HttpStatus.OK);
		}
			
		return new ResponseEntity<HistoricalStockPriceDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/stocks/{stockId}/historicalValues", produces = "application/json")
	public ResponseEntity<List<HistoricalStockPriceDTO>> findAllHistoricalStockPriceByStockId(@PathVariable("stockId") Long stockId)
	{
		List<HistoricalStockPriceDTO> historicalValues = historicalStockPriceService.findAllHistoricalStockPriceDTOByStockId(stockId);
			
		return new ResponseEntity<List<HistoricalStockPriceDTO>>(historicalValues, HttpStatus.OK);
	}
	
	@PostMapping(value = "/stocks/{stockId}/purchases", produces = "application/json")
	public ResponseEntity<PurchaseOrderDTO> purchaseStock(@PathVariable("stockId") Long stockId, @Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO)
	{	
		Optional<Stock> stockOptional = stockService.findStockById(stockId);
		if(!stockOptional.isPresent())
		{
			return new ResponseEntity<PurchaseOrderDTO>(HttpStatus.NOT_FOUND);
		}
		
		purchaseOrderService.sendMessagePurchaseStock(purchaseOrderDTO);
		
		return new ResponseEntity<PurchaseOrderDTO>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value = "/stocks/{stockId}/sales", produces = "application/json")
	public ResponseEntity<SaleOrderDTO> salesStock(@PathVariable("stockId") Long stockId, @Valid @RequestBody SaleOrderDTO saleOrderDTO)
	{
		Optional<Stock> stockOptional = stockService.findStockById(stockId);
		if(!stockOptional.isPresent())
		{
			return new ResponseEntity<SaleOrderDTO>(HttpStatus.NOT_FOUND);
		}
		
		saleOrderService.sendMessageSalesStock(saleOrderDTO);
		
		return new ResponseEntity<SaleOrderDTO>(HttpStatus.ACCEPTED);
	}
	
	
/*	@GetMapping(value = "/stocks/teste", produces = "application/json")
	public ResponseEntity<String> teste()
	{		
		StringBuilder corpo = new StringBuilder();
		corpo.append("<HTML>");
			corpo.append("<HEAD>");
				corpo.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			corpo.append("</HEAD>");
			corpo.append("<BODY>");
				corpo.append("<SPAN> TESTE </SPAN>");
			corpo.append("</BODY>");
		corpo.append("</HTML>");
		
		emailSenderUtil.sendEmail("cgtamaral@gmail.com", "Teste", corpo.toString());
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}*/
}
 