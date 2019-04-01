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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")
@Api(value = "stocks", tags={ "stocks"}, description = "Recurso para gerencimento de Ações. Permite a consulta de valores históricos de uma ação além de execução de compra e vendas.",
consumes = "application/json",  produces = "application/json")
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

    @ApiOperation(value = "Recupera o valor corrente unitário para uma ação", nickname = "findCurrentStockValueByStockId", notes = "", tags={ "stocks"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma ação na base de dados para o stockId informado!")})
	@GetMapping(value = "/stocks/{stockId}/currentValues", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<HistoricalStockPriceDTO> findCurrentStockValueByStockId(@ApiParam(value = "Identificador da ação para consulta de seu valor unitário corrente.", required = true) @PathVariable("stockId") Long stockId)
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
	
    @ApiOperation(value = "Recupera os valores históricos unitários para uma ação", nickname = "findAllHistoricalStockPriceByStockId", notes = "", tags={ "stocks"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma ação na base de dados para o stockId informado!")})
	@GetMapping(value = "/stocks/{stockId}/historicalValues", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<List<HistoricalStockPriceDTO>> findAllHistoricalStockPriceByStockId(@ApiParam(value = "Identificador da ação para consulta de seus valores unitários históricos.", required = true) @PathVariable("stockId") Long stockId)
	{

		Optional<Stock> stockOptional = stockService.findStockById(stockId);
		if(stockOptional.isPresent())
		{
			List<HistoricalStockPriceDTO> historicalValues = historicalStockPriceService.findAllHistoricalStockPriceDTOByStockId(stockId);
			new ResponseEntity<List<HistoricalStockPriceDTO>>(historicalValues, HttpStatus.OK);
		}
			
		return new ResponseEntity<List<HistoricalStockPriceDTO>>(HttpStatus.NOT_FOUND);
	}
	
    @ApiOperation(value = "Envia para processamento uma solicitação de ordem de compra de uma ação especifica.", nickname = "purchaseStock", notes = "", tags={ "stocks"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Operação recebida para processamento!"),
			@ApiResponse(code = 400, message = "O stockId informado não corresponde as informações enviadas para a ordem de compra!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma ação na base de dados para o stockId informado!")})
	@PostMapping(value = "/stocks/{stockId}/purchases", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<PurchaseOrderDTO> purchaseStock(@ApiParam(value = "Identificador da ação para compra", required = true) @PathVariable("stockId") Long stockId, 
			@ApiParam(value = "Objeto com as informações da solicitação de ordem de compra de um ação especifica.", required = true) @Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO)
	{	
		Optional<Stock> stockOptional = stockService.findStockById(stockId);
		if(!stockOptional.isPresent())
		{
			return new ResponseEntity<PurchaseOrderDTO>(HttpStatus.NOT_FOUND);
		}
		if(!purchaseOrderDTO.getStockId().equals(stockId))
		{
			return new ResponseEntity<PurchaseOrderDTO>(HttpStatus.BAD_REQUEST);
		}
		
		purchaseOrderService.sendMessagePurchaseStock(purchaseOrderDTO, stockOptional.get());
		
		return new ResponseEntity<PurchaseOrderDTO>(HttpStatus.ACCEPTED);
	}
	
    @ApiOperation(value = "Envia para processamento uma solicitação de ordem de venda de uma ação especifica.", nickname = "salesStock", notes = "", tags={ "stocks"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Operação recebida para processamento!"),
			@ApiResponse(code = 400, message = "O stockId informado não corresponde as informações enviadas para a ordem de venda!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma ação na base de dados para o stockId informado!")})
	@PostMapping(value = "/stocks/{stockId}/sales", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<SaleOrderDTO> salesStock(@ApiParam(value = "Identificador da ação para compra", required = true) @PathVariable("stockId") Long stockId, 
			@ApiParam(value = "Objeto com as informações da solicitação de ordem de venda de um ação especifica.", required = true) @Valid @RequestBody SaleOrderDTO saleOrderDTO)
	{
		Optional<Stock> stockOptional = stockService.findStockById(stockId);
		if(!stockOptional.isPresent())
		{
			return new ResponseEntity<SaleOrderDTO>(HttpStatus.NOT_FOUND);
		}
		if(!saleOrderDTO.getStockId().equals(stockId))
		{
			return new ResponseEntity<SaleOrderDTO>(HttpStatus.BAD_REQUEST);
		}
		
		saleOrderService.sendMessageSaleStock(saleOrderDTO, stockOptional.get());
		
		return new ResponseEntity<SaleOrderDTO>(HttpStatus.ACCEPTED);
	}
}
 