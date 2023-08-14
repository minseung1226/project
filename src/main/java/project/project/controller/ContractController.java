package project.project.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
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
import org.xhtmlrenderer.pdf.ITextRenderer;
import project.project.dto.contract.ContractDto;
import project.project.dto.contract.SimpleContractDto;
import project.project.service.ContractService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

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
    public void downloadContract(@PathVariable("contractId")Long contractId,HttpServletResponse response) throws DocumentException, IOException {
        ContractDto contractDto = contractService.findContractDtoById(contractId);

        String html = templateEngine.process("/contract/contract", getContext(contractDto));

        generatePdf(html,response);
    }

    private Context getContext(ContractDto contractDto) {
        Context context = new Context();
        context.setVariable("contractDto",contractDto);
        return context;
    }

    private void generatePdf(String html, HttpServletResponse response)throws IOException, DocumentException{
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(writer.getDirectContent().getInternalBuffer());
        document.close();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","attachment; filename=\"example.pdf\"");


    }


}
