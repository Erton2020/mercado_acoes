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
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")
@Api(value = "companies", tags={ "companies"})
@SwaggerDefinition(tags = {
	    @Tag(name = "Companies", description = "Recurso para gerencimento de Empresas e suas ações. Permite a realização de inclusão e edição de uma empresa e gestão de ações.")
	})
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

	@GetMapping(value = "/companies", produces = "application/json")
	public ResponseEntity<List<CompanyDTO>> findAllCompanies()
	{
		List<CompanyDTO> companiesDTO = companyService.findAllCompaniesDTO();
		if(companiesDTO==null || companiesDTO.size() == 0)
		{
			return new ResponseEntity<List<CompanyDTO>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<CompanyDTO>>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/companies/{companyId}", produces = "application/json")
	public ResponseEntity<CompanyDTO> findCompanyById(@PathVariable("companyId") Long companyId)
	{
		Optional<Company> companyOptional = companyService.findCompanyById(companyId);
		
		if(!companyOptional.isPresent())
		{
			return new ResponseEntity<CompanyDTO>(HttpStatus.NOT_FOUND);
		}

		CompanyDTO companyDTO  = companyMapper.companyToCompanyDTO(companyOptional.get());

		return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/companies/{companyId}/stocks", produces = "application/json")
	public ResponseEntity<List<StockDTO>> findAllStocksByCompanyId(@PathVariable("companyId") Long companyId)
	{
		List<StockDTO> stocksDTO = stockService.findAllStocksDTOByCompanyId(companyId);
		if(stocksDTO==null || stocksDTO.size() == 0)
		{
			return new ResponseEntity<List<StockDTO>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<StockDTO>>(stocksDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/companies", produces = "application/json")
	public ResponseEntity<CompanyDTO> insertCompany(@Valid @RequestBody CompanyDTO companyDTO)
	{
		Company company = companyMapper.companyDTOTOCompany(companyDTO);
		Address address = addressMapper.addressDTOToAddress(companyDTO.getAddress());
		
		address = adressService.insert(address);
		
		company.setAddress(address);
		
		company = companyService.insert(company);
		
		companyDTO = companyMapper.companyToCompanyDTO(company);
		
		return new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/companies/{companyId}", produces = "application/json")
	public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("companyId") Long companyId,  @Valid @RequestBody CompanyDTO companyDTO,
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
	
	@PostMapping(value = "/companies/{companyId}/stocks", produces = "application/json")
	public ResponseEntity<StockDTO> createStockForCompany(@PathVariable("companyId") Long companyId, @Valid @RequestBody StockDTO stockDTO)
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

	@PutMapping(value = "/companies/{companyId}/stocks/{stockId}", produces = "application/json")
	public ResponseEntity<StockDTO> updateStockForCompany(@PathVariable("companyId") Long companyId,
			@PathVariable("stockId") Long stockId,  @Valid @RequestBody StockDTO stockDTO)
	{
		Optional<Company> companyOptional = companyService.findCompanyById(companyId);
		Optional<Stock> stockOptional = stockService.findStockById(stockId);
		if(companyOptional.isPresent())
		{
			if(stockOptional.isPresent())
			{
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
		stock.setStocksForSale(stockDTO.getStocksForSale());
		
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
