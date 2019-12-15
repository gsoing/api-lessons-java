package fr.esipe.documentManagerAPI.controller;


import fr.esipe.documentManagerAPI.exceptions.ApiException;
import fr.esipe.documentManagerAPI.exceptions.ApiRequestException;
import fr.esipe.documentManagerAPI.model.DocumentList;
import fr.esipe.documentManagerAPI.model.DocumentSummary;
import fr.esipe.documentManagerAPI.service.DocumentListService;
import fr.esipe.documentManagerAPI.service.DocumentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/documents/list")
public class pageController
{
    @Autowired
    DocumentSummaryService service;

    @GetMapping
    public ResponseEntity<DocumentList> getPage(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        DocumentList l = new DocumentList();
        List<DocumentSummary> list = service.getAllSummary(pageNo, pageSize, sortBy);
        l.setList(list);
        l.setNbElements(pageSize);
        l.setPage(pageNo);
        //throw new ApiRequestException("Cannot get all employees");
        return new ResponseEntity<DocumentList>(l, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
