package br.pucminas.stockmarket.api.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.stockmarket.api.dto.CompanyDTO;
import br.pucminas.stockmarket.api.dto.StockDTO;
import br.pucminas.stockmarket.api.entities.Address;
import br.pucminas.stockmarket.api.entities.Company;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.enums.StockTypeEnum;
import br.pucminas.stockmarket.api.mappers.AddressMapper;
import br.pucminas.stockmarket.api.mappers.CompanyMapper;
import br.pucminas.stockmarket.api.mappers.StockMapper;
import br.pucminas.stockmarket.api.services.AddressService;
import br.pucminas.stockmarket.api.services.CompanyService;
import br.pucminas.stockmarket.api.services.HistoricalStockPriceService;
import br.pucminas.stockmarket.api.services.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")
@Api(value = "companies", tags={ "companies"}, description="Recurso para gerencimento de Empresas e suas ações. Permite a realização de inclusão e edição de uma empresa e gestão de suas ações.",
consumes = "application/json",  produces = "application/json")
public class CompanyController
{
	
	CompanyService companyService;
	StockService stockService;
	AddressService adressService;
	HistoricalStockPriceService historicalStockPriceService;
	CompanyMapper companyMapper;
	AddressMapper addressMapper;
	StockMapper stockMapper;
	
	public CompanyController(CompanyService p_companyService, StockService p_stockService, AddressService p_adressService, 
			HistoricalStockPriceService p_historicalStockPriceService, CompanyMapper p_companyMapper,
			AddressMapper p_addressMapper, StockMapper p_stockMapper) 
	{
		this.companyService = p_companyService;
		this.stockService = p_stockService;
		this.adressService = p_adressService;
		this.historicalStockPriceService = p_historicalStockPriceService;
		this.companyMapper = p_companyMapper;
		this.addressMapper = p_addressMapper;
		this.stockMapper = p_stockMapper;
	}

    @ApiOperation(value = "Recupera os dados cadastrais de todas as empresas existentes.", nickname = "findAllCompanies", notes = "", tags={ "companies"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma empresa na base de dados!")})
	@GetMapping(value = "/companies", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<List<CompanyDTO>> findAllCompanies()
	{
		List<CompanyDTO> companiesDTO = companyService.findAllCompaniesDTO();
		if(companiesDTO==null || companiesDTO.size() == 0)
		{
			return new ResponseEntity<List<CompanyDTO>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<CompanyDTO>>(HttpStatus.OK);
	}
	
    @ApiOperation(value = "Recupera os dados cadastrais de todas as empresas existentes.", nickname = "findCompanyById", notes = "", tags={ "companies"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma empresa na base de dados para o companyId informado!")})
	@GetMapping(value = "/companies/{companyId}", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<CompanyDTO> findCompanyById(@ApiParam(value = "Identificador da empresa para consulta de seus dados cadastrais.", required = true) @PathVariable("companyId") Long companyId)
	{
		Optional<Company> companyOptional = companyService.findCompanyById(companyId);
		
		if(!companyOptional.isPresent())
		{
			return new ResponseEntity<CompanyDTO>(HttpStatus.NOT_FOUND);
		}

		CompanyDTO companyDTO  = companyMapper.companyToCompanyDTO(companyOptional.get());

		return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
	}
	
    @ApiOperation(value = "Recupera as ações existentes para uma empresa especifica.", nickname = "findAllStocksByCompanyId", notes = "", tags={ "companies"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma ação na base de dados para o companyId informado!")})
	@GetMapping(value = "/companies/{companyId}/stocks", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<List<StockDTO>> findAllStocksByCompanyId(@ApiParam(value = "Identificador da empresa para consulta de informações de suas ações no mercado.", required = true) @PathVariable("companyId") Long companyId)
	{
		List<StockDTO> stocksDTO = stockService.findAllStocksDTOByCompanyId(companyId);
		if(stocksDTO==null || stocksDTO.size() == 0)
		{
			return new ResponseEntity<List<StockDTO>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<StockDTO>>(stocksDTO, HttpStatus.OK);
	}
	
    @ApiOperation(value = "Insere uma nova empresa na base de dados", nickname = "insertCompany", notes = "", tags={ "companies"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!")})
	@PostMapping(value = "/companies", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<CompanyDTO> insertCompany(@ApiParam(value = "Objeto com as informações cadastrais da nova empresa para inclusão na base de dados.", required = true) @Valid @RequestBody CompanyDTO companyDTO)
	{
		Company company = companyMapper.companyDTOTOCompany(companyDTO);
		Address address = addressMapper.addressDTOToAddress(companyDTO.getAddress());
		
		address = adressService.insert(address);
		
		company.setAddress(address);
		
		company = companyService.insert(company);
		
		companyDTO = companyMapper.companyToCompanyDTO(company);
		
		return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
	}
	
    @ApiOperation(value = "Atualiza os dados cadastrais de uma empresa existente especifica.", nickname = "updateCompany", notes = "", tags={ "companies"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma empresa na base de dados para o companyId informado!")})
	@PutMapping(value = "/companies/{companyId}", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<CompanyDTO> updateCompany(@ApiParam(value = "Identificador da empresa para atualização de informações cadastrais.", required = true) @PathVariable("companyId") Long companyId, 
			@ApiParam(value = "Objeto com as informações cadastrais da empresa para atualização na base de dados.", required = true) @Valid @RequestBody CompanyDTO companyDTO,
			BindingResult result)
	{
		Optional<Company> companyOptional = companyService.findCompanyById(companyId);
		Company company = null;
		Address address = null;
		if(companyOptional.isPresent())
		{
			company = companyMapper.updateCompany(companyOptional.get(),companyDTO);	
			if(companyDTO.getAddress()!=null)
			{
				address = addressMapper.updateAdress(company.getAddress(), companyDTO.getAddress());
			}
			if(address!=null)
			{
				address = adressService.update(address);
				
				company.setAddress(address);
			}
			
			if(company!=null)
			{
				company = companyService.update(company);
			}
			
			companyDTO = companyMapper.companyToCompanyDTO(company);
		}
		else
		{
			return new ResponseEntity<CompanyDTO>(HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
	}
	
    @ApiOperation(value = "Insere uma nova ação para uma empresa especifica.", nickname = "createStockForCompany", notes = "", tags={ "companies"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma empresa na base de dados para o companyId informado!")})
	@PostMapping(value = "/companies/{companyId}/stocks", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<StockDTO> createStockForCompany(@ApiParam(value = "Identificador da empresa para inclusão de uma nova ação.", required = true) @PathVariable("companyId") Long companyId,
			@ApiParam(value = "Objeto com as informações da ação a ser criada para a empresa.", required = true) @Valid @RequestBody StockDTO stockDTO)
	{
		Optional<Company> companyOptional = companyService.findCompanyById(companyId);
		if(companyOptional.isPresent())
		{
			Stock stock = stockMapper.stockDTOTOStock(stockDTO);
			stock.setCompany(companyOptional.get());
			
			stock = stockService.insert(stock);	
			
			HistoricalStockPrice historicalStockPrice = createHistoricalStockPrice(stock, CalculationTypeEnum.CREATE);
			
			stockDTO = stockMapper.stockToStockDTO(stock);
			stockDTO.setCreationValue(historicalStockPrice.getPrice());
			stockDTO.setCurrentValue(historicalStockPrice.getPrice());
			
		}
		else
		{
			return new ResponseEntity<StockDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<StockDTO>(stockDTO, HttpStatus.OK);
	}

    @ApiOperation(value = "Atualiza uma ação especifica para uma empresa.", nickname = "updateStockForCompany", notes = "", tags={ "companies"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
    		@ApiResponse(code = 400, message = "Quantidade de ações deve ser superior a zero!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhuma empresa ou estoque na base de dados para os parametros informados!")})
	@PutMapping(value = "/companies/{companyId}/stocks/{stockId}", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<StockDTO> updateStockForCompany(@ApiParam(value = "Identificador da empresa para atualização de uma ação.", required = true) @PathVariable("companyId") Long companyId,
			@ApiParam(value = "Identificador da ação a ser atualizada.", required = true) @PathVariable("stockId") Long stockId,  
			@ApiParam(value = "Objeto com as informações da ação para atualização na base de dados. O valor para o campo stocksForSale deve ser superior a 1. O valor informado será incrementado ao valor atual de quantidade de ações disponiveis no mercado.", required = true) @Valid @RequestBody StockDTO stockDTO)
	{
		Optional<Company> companyOptional = companyService.findCompanyById(companyId);
		Optional<Stock> stockOptional = stockService.findStockById(stockId);
		if(companyOptional.isPresent())
		{
			if(stockOptional.isPresent())
			{
				if(stockDTO.getStocksForSale() < 1)
				{
					return new ResponseEntity<StockDTO>(HttpStatus.BAD_REQUEST);
				}
				
				Stock stock = updateStockWithStockDTO(stockOptional.get(), stockDTO);
				
				stock = stockService.update(stock);	
								
				HistoricalStockPrice historicalStockPrice = createHistoricalStockPrice(stock, CalculationTypeEnum.UPDATE);
				
				stockDTO = stockMapper.stockToStockDTO(stock);
				stockDTO.setCreationValue(stock.getHistoricalStockPrices().get(0).getPrice());
				stockDTO.setCurrentValue(historicalStockPrice.getPrice());
			}
			else
			{
				return new ResponseEntity<StockDTO>(HttpStatus.NOT_FOUND);
			}
		}
		else
		{
			return new ResponseEntity<StockDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<StockDTO>(stockDTO, HttpStatus.OK);
	}
	
	private Stock updateStockWithStockDTO(Stock stock, StockDTO stockDTO)
	{
		stock.setDescription(stockDTO.getDescription());
		stock.setStockType(StockTypeEnum.valueOf(stockDTO.getStockType()));
		stock.setStocksForSale(stock.getStocksForSale() + stockDTO.getStocksForSale());
		
		return stock;
	}
	
	private HistoricalStockPrice createHistoricalStockPrice(Stock stock, CalculationTypeEnum calcType)
	{
		Double actualValue =  stockService.calculateStockCurrentValue(stock.getHistoricalStockPrices(), calcType);
		HistoricalStockPrice historicalStockPrice = new HistoricalStockPrice();
		historicalStockPrice.setStock(stock);
		historicalStockPrice.setReferenceDate(Calendar.getInstance());
		historicalStockPrice.setPrice(actualValue);
		
		historicalStockPrice = historicalStockPriceService.insert(historicalStockPrice);
		
		return historicalStockPrice;
	}
	

}
