package br.pucminas.stockmarket.api.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.stockmarket.api.dto.StockDTO;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.services.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")
@Api(value = "stocks", tags={ "stocks"})
@SwaggerDefinition(tags = {
	    @Tag(name = "Stocks", description = "Recurso para gerencimento de Ações. Permite a realização de inclusão e edição de ações para uma empresa.")
	})
public class StockController {
	
	StockService stockService;
	
	public StockController(StockService p_stockService) {
		
		this.stockService = p_stockService;
	}

	@GetMapping(value = "/stocks/{companyId}", produces = "application/json")
	public List<StockDTO> findStocksByCompany(@RequestParam(value = "Identificador da empresa para consulta de sações disponiveis.", required= false) Long companyId)
	{
		return stockService.findStocksByCompanyId(companyId);
	}
}
