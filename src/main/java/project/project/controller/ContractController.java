package project.project.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import project.project.controller.form.contract.ContractForm;
import project.project.domain.Contract;
import project.project.dto.contract.ContractDto;
import project.project.dto.contract.SimpleContractDto;
import project.project.repository.contractrepository.ContractRepository;
import project.project.service.ContractService;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    private final ContractRepository contractRepository;

    private final SpringTemplateEngine templateEngine;


    @GetMapping("/contract/{userId}")
    public String contractList(@PathVariable("userId") Long userId, Model model){

        List<SimpleContractDto> contractDtos = contractService.findSimpleContractDtosByUserId(userId);
        model.addAttribute("contractDtos",contractDtos);
        return "contract/management";
    }

    @GetMapping("/contract/form")
    public String contractForm(Model model){
        model.addAttribute("contractDto",new ContractDto());

        return "contract/form";
    }

    @PostMapping("/contract/save")
    public String contractSave(@Valid ContractDto contractDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "/contract/form";
        }

        Long contractId = contractService.contractSave(contractDto);

        redirectAttributes.addAttribute("contractId",contractId);


        return "redirect:/contract/detail/{contractId}";
    }

    @GetMapping("/contract/detail/{contractId}")
    public String contractDetail(@PathVariable("contractId")Long contractId,Model model){
        ContractDto contractDto = contractService.findContractDtoById(contractId);
        model.addAttribute("contractDto",contractDto);

        return "contract/detail";
    }

    @GetMapping("/contract/modify/{contractId}")
    public String contractModifyForm(@PathVariable("contractId")Long contractId,Model model){
        ContractDto contractDto = contractService.findContractDtoById(contractId);
        model.addAttribute("contractDto",contractDto);

        return "contract/modify";

    }

    @PostMapping("/contract/modify/{contractId}")
    public String contractModify(@PathVariable("contractId")Long contractId,ContractDto contractDto,RedirectAttributes redirectAttributes){
        contractService.modifyContract(contractDto);

        redirectAttributes.addAttribute("contractId",contractId);

        return "redirect:/contract/detail/{contractId}";
    }

    @PostMapping("/contract/delete/{contractId}")
    public String deleteContract(@PathVariable("contractId")Long contractId, HttpSession session, RedirectAttributes redirectAttributes){
        contractService.deleteContract(contractId);
        Long user =(Long) session.getAttribute("user");

        redirectAttributes.addAttribute("userId",user);

        return "redirect:/contract/{userId}";

    }


    @PostMapping("/contract/download/{contractId}")
    public void downloadContract(@PathVariable("contractId")Long contractId,HttpServletResponse response) throws IOException {
        Contract contract = contractRepository.findById(contractId).get();
        ContractForm contractForm = new ContractForm(contract);
        contractForm.initializeKoreanFields();

        String html = templateEngine.process("/contract/contract", getContext(contractForm));

        generatePdf(html);
    }

    private Context getContext(ContractForm contractForm) {
        Context context = new Context();
        context.setVariable("contractForm",contractForm);
        return context;
    }

    private void generatePdf(String html) throws IOException {
        String font="font/malgun.ttf";
        String path="C:\\intellij\\pdf\\saple.pdf";

        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri("C:/intellij/project/src/main/resources/templates/contract/");
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        FontProgram fontProgram = FontProgramFactory.createFont(font);
        fontProvider.addFont(fontProgram);
        properties.setFontProvider(fontProvider);

        List<IElement> elements= HtmlConverter.convertToElements(html,properties);
        PdfDocument pdf = new PdfDocument(new PdfWriter(path));
        Document document = new Document(pdf);

        document.setMargins(50,0,50,0);
        for (IElement element : elements) {
            document.add((IBlockElement) element);
        }

        document.close();


    }

    @GetMapping("/contract/test/{contractId}")
    public String test(@PathVariable("contractId")Long id,Model model){
        Contract contract = contractRepository.findById(id).get();
        ContractForm contractForm = new ContractForm(contract);
        contractForm.initializeKoreanFields();
        model.addAttribute("contractForm",contractForm  );

        return "contract/contract";


    }


}
