package br.pucminas.stockmarket.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

import br.pucminas.stockmarket.api.dto.AddressDTO;
import br.pucminas.stockmarket.api.dto.InvestmentDTO;
import br.pucminas.stockmarket.api.dto.InvestorDTO;
import br.pucminas.stockmarket.api.entities.Address;
import br.pucminas.stockmarket.api.entities.InvestmentWallet;
import br.pucminas.stockmarket.api.entities.Investor;
import br.pucminas.stockmarket.api.mappers.AddressMapper;
import br.pucminas.stockmarket.api.mappers.InvestmentMapper;
import br.pucminas.stockmarket.api.mappers.InvestorMapper;
import br.pucminas.stockmarket.api.services.AddressService;
import br.pucminas.stockmarket.api.services.InvestmentWalletService;
import br.pucminas.stockmarket.api.services.InvestorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")
@Api(value = "investors", tags={ "investors"} ,description = "Recurso para gerencimento de investidores. Permite a realização de cadastro de novos investidores, a atualização de dados cadastrais de investidores já existentes e obtenção consolidada dos investimentos de um investidor.")
public class InvestorController
{	
	InvestorService investorService;
	InvestmentWalletService investmentWalletService;
	AddressService adressService;
	InvestorMapper investorMapper;
	AddressMapper addressMapper;
	InvestmentMapper investmentMapper;
	
	public InvestorController(InvestorService p_investorService, InvestmentWalletService p_investmentWalletService,
			AddressService p_adressService,InvestorMapper p_investorMapper, AddressMapper p_addressMapper, InvestmentMapper p_investmentMapper) 
	{
		this.investorService = p_investorService;
		this.investmentWalletService = p_investmentWalletService;
		this.adressService = p_adressService;
		this.investorMapper = p_investorMapper;
		this.addressMapper = p_addressMapper;
		this.investmentMapper = p_investmentMapper;
	}
	
    @ApiOperation(value = "Recupera os cadastros de todos os investidores existentes na base de dados", nickname = "findAllInvestors", notes = "", tags={ "investors"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrada nenhum investidor na base de dados!")})
	@GetMapping(value = "/investors", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<List<InvestorDTO>> findAllInvestors()
	{
		List<InvestorDTO> investors = investorService.findAllInvestorsDTO();
		if(investors==null || investors.size() == 0)
		{
			return new ResponseEntity<List<InvestorDTO>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<InvestorDTO>>(HttpStatus.OK);
	}
	
    @ApiOperation(value = "Recupera os dados cadastrais de um investidor especifico.", nickname = "findInvestorById", notes = "", tags={ "investors"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrado nenhum investidor para o investorId informado.")})
	@GetMapping(value = "/investors/{investorId}", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<InvestorDTO> findInvestorById(@ApiParam(value = "Identificador do investidor para consulta de informações", required = true)  @PathVariable("investorId") Long investorId)
	{
		Optional<Investor> investorOptional = investorService.findInvestorById(investorId);
		if(!investorOptional.isPresent())
		{
			return new ResponseEntity<InvestorDTO>(HttpStatus.NOT_FOUND);
		}
		
		InvestorDTO investorDTO = investorMapper.investorTOInvestorDTO(investorOptional.get());

		return new ResponseEntity<InvestorDTO>(investorDTO, HttpStatus.OK);
	}
	
    @ApiOperation(value = "Recupera todos os investimentos de um investidor especifico", nickname = "findAllInvestmentsByInvestorId", notes = "", tags={ "investors"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
			@ApiResponse(code = 404, message = "Não foi encontrado nenhum investimento para o investorId informado.")})
	@GetMapping(value = "/investors/{investorId}/investments", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<List<InvestmentDTO>> findAllInvestmentsByInvestorId(@ApiParam(value = "Identificador do investidor para consulta de informações", required = true) @PathVariable("investorId") Long investorId)
	{
		Optional<InvestmentWallet> investmentWalletOptional =  investmentWalletService.findInvestmentWalletByInvestorId(investorId);
		
		if(!investmentWalletOptional.isPresent() || investmentWalletOptional.get().getInvestments()==null 
				|| investmentWalletOptional.get().getInvestments().size() == 0)
		{
			return new ResponseEntity<List<InvestmentDTO>>(HttpStatus.NOT_FOUND);
		}
		
		List<InvestmentDTO> investmentsDTO = investmentMapper.investmentsTOInvestmentsDTO(investmentWalletOptional.get().getInvestments());
		
		return  new ResponseEntity<List<InvestmentDTO>>(investmentsDTO, HttpStatus.OK);
	}
	
    @ApiOperation(value = "Inclui um novo investidor na base de dados", nickname = "insertInvestor", notes = "", tags={ "investors"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!")})
	@PostMapping(value = "/investors", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<InvestorDTO> insertInvestor(@ApiParam(value = "Objeto com as informações cadastrais do investidor a ser incluido.", required = true) @Valid @RequestBody InvestorDTO investorDTO)
	{
		Investor investor = investorMapper.investorDTOTOInvestor(investorDTO);
		List<Address> addresses = StreamSupport.stream(investorDTO.getAddresses()
				.spliterator(), false)
				.map(addressMapper::addressDTOToAddress)
				.collect(Collectors.toList());
		
		addresses = adressService.insertAll(addresses);
		
		investor.setAddresses(addresses);
		investor = investorService.insert(investor);
		
		investorDTO = investorMapper.investorTOInvestorDTO(investor);

		return new ResponseEntity<InvestorDTO>(investorDTO, HttpStatus.OK);
	}
	
    @ApiOperation(value = "Atualiza os dados cadastrais de um investidor especifico na base de dados", nickname = "updateInvestor", notes = "", tags={ "investors"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!")})
	@PutMapping(value = "/investors/{investorId}", consumes = "application/json",  produces = "application/json")
	public ResponseEntity<InvestorDTO> updateInvestor(@ApiParam(value = "Identificador do investidor a ser atualizado na base de dados.", required = true) @PathVariable("investorId") Long investorId, 
			@ApiParam(value = "Objeto com as informações cadastrais do investidor para atualização.", required = true) @Valid @RequestBody InvestorDTO investorDTO,
			BindingResult result)
	{
    	Optional<Investor> investorOptional = investorService.findInvestorById(investorId);
    	if(!investorOptional.isPresent())
		{
    		if(investorOptional.isPresent())
    		{
    			
    			List<Address> addresses = new ArrayList<Address>();
    			if(investorDTO.getAddresses()!=null)
    			{
    				for (AddressDTO addressDTO : investorDTO.getAddresses()) 
    				{
    					Address addressAux = null;
    					if(addressDTO.getId()!=null)
    					{
    						for (Address address : investorOptional.get().getAddresses()) 
    						{
    							if(address.getId().equals(addressDTO.getId()))
								{
    								addressAux = addressMapper.updateAdress(address, addressDTO);
    								addressAux = adressService.update(address);
    								break;
								}
							}
    					}
						if(addressAux==null)
						{
							addressAux = addressMapper.addressDTOToAddress(addressDTO);
							addressAux = adressService.update(addressAux);
							addresses.add(addressAux);
						}
					}
    			}
    			
    			Optional<Investor> investorOptionalAux = investorService.findInvestorById(investorId);
    			Investor investor = investorOptionalAux.get();
    			if(addresses!=null && addresses.size() > 0)
    			{
    				investor.getAddresses().addAll(addresses);
    			}
    			
    			investor = investorMapper.updateInvestor(investor, investorDTO);	
    
   				investor = investorService.update(investor);
					     			
    			investorDTO = investorMapper.investorTOInvestorDTO(investor);
    		}
    		else
    		{
    			return new ResponseEntity<InvestorDTO>(HttpStatus.NOT_FOUND);
    		}
		}


		return new ResponseEntity<InvestorDTO>(investorDTO, HttpStatus.OK);
	}
	
}
