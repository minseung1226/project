package project.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;
import project.project.domain.Contract;
import project.project.domain.User;
import project.project.dto.contract.ContractDto;
import project.project.dto.contract.SimpleContractDto;
import project.project.repository.contractrepository.ContractRepository;
import project.project.repository.UserRepository;
import project.project.repository.contractrepository.DslContractRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    private final DslContractRepository dslContractRepository;

    @Transactional
    public Long contractSave(ContractDto contractDto){

        User user = userRepository.findById(contractDto.getUserId()).get();
        Contract contract = new Contract(contractDto, user);

        contractRepository.save(contract);
        
        return contract.getId();
    }

    public ContractDto findContractDtoById(Long contractId){
        return dslContractRepository.findContractDtoById(contractId);
    }

    public List<SimpleContractDto> findSimpleContractDtosByUserId(Long userId){
        return dslContractRepository.findSimpleContractDtosByUserId(userId);
    }

}
