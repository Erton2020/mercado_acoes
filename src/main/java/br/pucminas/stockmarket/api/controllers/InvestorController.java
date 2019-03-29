package br.pucminas.stockmarket.api.controllers;

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
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")
@Api(value = "investors", tags={ "investors"})
@SwaggerDefinition(tags = {
	    @Tag(name = "Investors", description = "Recurso para gerencimento de investidores.")
	})
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
	

	@GetMapping(value = "/investors", produces = "application/json")
	public ResponseEntity<List<InvestorDTO>> findAllInvestors()
	{
		List<InvestorDTO> investors = investorService.findAllInvestorsDTO();
		if(investors==null || investors.size() == 0)
		{
			return new ResponseEntity<List<InvestorDTO>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<InvestorDTO>>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/investors/{investorId}", produces = "application/json")
	public ResponseEntity<InvestorDTO> findInvestorById(@PathVariable("investorId") Long investorId)
	{
		Optional<Investor> investorOptional = investorService.findInvestorById(investorId);
		if(!investorOptional.isPresent())
		{
			return new ResponseEntity<InvestorDTO>(HttpStatus.NOT_FOUND);
		}
		
		InvestorDTO investorDTO = investorMapper.investorTOInvestorDTO(investorOptional.get());

		return new ResponseEntity<InvestorDTO>(investorDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/investors/{investorId}/investments", produces = "application/json")
	public ResponseEntity<List<InvestmentDTO>> findAllInvestmentsByInvestorId(@PathVariable("investorId") Long investorId)
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
	
	@PostMapping(value = "/investors", produces = "application/json")
	public ResponseEntity<InvestorDTO> insertInvestor(@Valid @RequestBody InvestorDTO investorDTO)
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
	
	@PutMapping(value = "/investors/{investorId}", produces = "application/json")
	public ResponseEntity<InvestorDTO> updateInvestor(@PathVariable("investorId") Long investorId,  @Valid @RequestBody InvestorDTO investorDTO,
			BindingResult result)
	{
		Investor investor = investorService.update(new Investor());
		
		investorDTO = investorMapper.investorTOInvestorDTO(investor);

		return new ResponseEntity<InvestorDTO>(investorDTO, HttpStatus.OK);
	}
	
}
